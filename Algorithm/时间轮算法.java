------------------------
时间轮算法				|
------------------------
	# 定时器大概有两种,一种是开阻塞线程,另一种是开一个任务队列然后定期扫描
		* 显而易见这两种方式的弊端很明显,前者对线程消耗过大,后者对时间消耗过大(很多未到时间的任务会被多次重复扫描消耗性能)



------------------------
固定时间的时间轮算法	|
------------------------
	# 创建时间轮对象的时候，指定时间轮的过期时间等属性
	# 添加任务，到达过期时间后，触发过期监听

package io.javaweb.example.algorithm.timewheel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class TimingWheel<E> {

	// 过期监听
	public static interface ExpirationListener <E> {
		void expired(E expiredObject);
	}

	
	// 一个tick的持续时间
	private final long tickDuration;
	
	// tick数量
	private final int ticksPerWheel;
	
	// 当前tick的指针
	private volatile int currentTickIndex = 0;

	// 过期的任务
	private final CopyOnWriteArrayList<ExpirationListener<E>> expirationListeners = new CopyOnWriteArrayList<ExpirationListener<E>>();
	
	// 刻度插槽
	private final ArrayList<Slot<E>> wheel;
	
	// 任务 和 任务所在插槽的映射。全局指针
	private final Map<E, Slot<E>> indicator = new ConcurrentHashMap<E, Slot<E>>();

	// 是否shutdown
	private final AtomicBoolean shutdown = new AtomicBoolean(false);
	
	// 读写锁
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	// 工作线程
	private Thread workerThread;

	/**
	 * @param tickDuration 每一刻度时间
	 * @param ticksPerWheel 有多少个刻度
	 * @param timeUnit tickDuration 的时间单位
	 */
	public TimingWheel(int tickDuration, int ticksPerWheel, TimeUnit timeUnit) {
		if (timeUnit == null) {
			throw new NullPointerException("unit");
		}
		if (tickDuration <= 0) {
			throw new IllegalArgumentException("tickDuration must be greater than 0: " + tickDuration);
		}
		if (ticksPerWheel <= 0) {
			throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
		}
		
		// 实例化插槽
		this.wheel = new ArrayList<Slot<E>>();
		
		// 转换时间为毫秒
		this.tickDuration = TimeUnit.MILLISECONDS.convert(tickDuration, timeUnit);

		// 刻度数量 + 1
		this.ticksPerWheel = ticksPerWheel + 1;

		for (int i = 0; i < this.ticksPerWheel; i++) {
			
			// 实例化插槽
			wheel.add(new Slot<E>(i));
		}
		
		// 移除空白的单位
		wheel.trimToSize();

		// 实例化工作线程
		workerThread = new Thread(new TickWorker(), "Timing-Wheel");
	}

	public void start() {
		if (shutdown.get()) {
			throw new IllegalStateException("Cannot be started once stopped");
		}

		if (!workerThread.isAlive()) {
			// 启动工作线程
			workerThread.start();
		}
	}

	public boolean stop() {
		if (!shutdown.compareAndSet(false, true)) {
			// 停止失败
			return false;
		}

		// 当前执行线程中断标识
		boolean interrupted = false;
		
		while (workerThread.isAlive()) {
			// 尝试中断任务线程
			workerThread.interrupt();
			try {
				// 当前线程阻塞100毫秒，直到任务线程处理完毕了中断异常
				workerThread.join(100);
			} catch (InterruptedException e) {
				interrupted = true;
			}
		}
		if (interrupted) {
			Thread.currentThread().interrupt();
		}

		return true;
	}

	public void addExpirationListener(ExpirationListener<E> listener) {
		expirationListeners.add(listener);
	}

	public void removeExpirationListener(ExpirationListener<E> listener) {
		expirationListeners.remove(listener);
	}

	public long add(E e) {
		synchronized (e) {
			
			// 判断是否已经添加过,如果已经添加过，就先移除
			checkAdd(e);
			
			// 上一个指针的位置（因为是新添加任务，所以添加到最后面）
			int previousTickIndex = getPreviousTickIndex();
			
			// 获取上一个指针位置的队列
			Slot<E> slot = wheel.get(previousTickIndex);
			
			// 添加元素到队列
			slot.add(e);
			
			// 添加任务和插槽的映射关系
			indicator.put(e, slot);
			
			// 插槽数量 * 每个插槽的执行时间 = 执行需要等待的时间
			return (this.ticksPerWheel - 1) * this.tickDuration;
		}
	}

	private void checkAdd(E e) {
		Slot<E> slot = indicator.get(e);
		if (slot != null) {
			slot.remove(e);
		}
	}

	/**
	 * 获取上一次刻度指针的位置（也就是当前时间轮，最后执行的一个插槽）
	 * @return
	 */
	private int getPreviousTickIndex() {
		// 读上锁
		lock.readLock().lock();
		try {
			int cti = this.currentTickIndex;
			if (cti == 0) {
				// 如果当前指针的位置是0，则指向最后一个刻度
				return this.ticksPerWheel - 1;
			}
			return cti - 1;
		} finally {
			lock.readLock().unlock();
		}
	}

	public boolean remove(E e) {
		synchronized (e) {
			Slot<E> slot = indicator.get(e);
			if (slot == null) {
				return false;
			}

			indicator.remove(e);
			return slot.remove(e) != null;
		}
	}

	private void notifyExpired(int idx) {
		
		// 获取当前插槽的Slot
		Slot<E> slot = wheel.get(idx);
		
		// 获取当期Slot中的任务Set
		Set<E> elements = slot.elements();
		
		for (E e : elements) {
			// 从Slot中移除任务
			slot.remove(e);
			synchronized (e) {
				// 从指示符获取到任务对象
				Slot<E> latestSlot = indicator.get(e);
				if (latestSlot.equals(slot)) {
					// 移出关联关系
					indicator.remove(e);
				}
			}
			
			for (ExpirationListener<E> listener : expirationListeners) {
				// 循环触发过期事件
				listener.expired(e);
			}
		}
	}

	/**
	 * 时间轮线程
	 * @author Administrator
	 */
	private class TickWorker implements Runnable {

		// 开是时间
		private long startTime;

		// 时间轮移动次数
		private long tick;

		@Override
		public void run() {
			// 时间轮开始时间
			this.startTime = System.currentTimeMillis();
			
			this.tick = 1;

			// 开始转动时间轮
			for (int i = 0; !shutdown.get(); i++) {
				if (i == wheel.size()) {
					// 指针到了末尾，重置为0
					i = 0;
				}
				lock.writeLock().lock();
				try {
					// 同步设置指针位置
					currentTickIndex = i;
				} finally {
					lock.writeLock().unlock();
				}
				
				// 唤醒当前指针的到期任务
				notifyExpired(currentTickIndex);
				
				// 等待下一次循环
				waitForNextTick();
			}
		}
		
		// 移动到下一个刻度
		private void waitForNextTick() {
			for (;;) {
				// 当前时间
				long currentTime = System.currentTimeMillis();
				
				/*
				 	需要减去线程的执行耗时
					每个刻度耗时 * 时间轮移动次数 = 从第一次执行，到现在所需要的暂停时间 
					当前时间 - 开始的时间		= 从第一次执行，到现在消耗的时间
				*/
				// 线程暂停时间 = 每个刻度耗时 * 时间轮移动次数 - (当前时间 - 开始的时间)
				long sleepTime = tickDuration * this.tick - (currentTime - this.startTime);

				if (sleepTime <= 0) {
					break;
				}

				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					return;
				}
			}

			// 转动次数 + 1
			this.tick++;
		}
	}

	private static class Slot<E> {

		private int id;
		//　安全的HashMap构建Set容器
		private Map<E, E> elements = new ConcurrentHashMap<E, E>();

		public Slot(int id) {
			this.id = id;
		}

		public void add(E e) {
			elements.put(e, e);
		}

		public E remove(E e) {
			return elements.remove(e);
		}

		public Set<E> elements() {
			return elements.keySet();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("rawtypes")
			Slot other = (Slot) obj;
			if (id != other.id)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Slot [id=" + id + ", elements=" + elements + "]";
		}
	}
}
