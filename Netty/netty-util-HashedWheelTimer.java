---------------------------------
HashedWheelTimer				 |
---------------------------------
	# io.netty.util.HashedWheelTimer
		* 时间轮算法的实现

		public class HashedWheelTimer implements Timer
	
	# 静态变量 
	    public static final int WORKER_STATE_INIT = 0;
		public static final int WORKER_STATE_STARTED = 1;
		public static final int WORKER_STATE_SHUTDOWN = 2;

	# 构造方法
		public HashedWheelTimer() 
		public HashedWheelTimer(long tickDuration, TimeUnit unit) 
		public HashedWheelTimer(long tickDuration, TimeUnit unit, int ticksPerWheel)
		public HashedWheelTimer(ThreadFactory threadFactory)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection, long maxPendingTimeouts)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection, long maxPendingTimeouts, Executor taskExecutor)

		* threadFactory	线程工厂
			* 用于创建专用于 TimerTask 执行的后台线程。

		* tickDuration	时针每次 tick 的时间，相当于时针间隔多久走到下一个 slot；
			* 默认：100
		
		* unit			表示 tickDuration 的时间单位
			* 默认： TimeUnit.MILLISECONDS
		
		* ticksPerWheel	时间轮上一共有多少个 slot，默认 512 个。分配的 slot 越多，占用的内存空间就越大

		* leakDetection			是否开启内存泄漏检测
			* 如果为 true，则应始终启用泄漏检测
			* 如果为 false，则只有当工作线程不是守护进程线程时才会启用泄漏检测。

		* maxPendingTimeouts	最大允许等待任务数
			* 最大待处理超时次数，调用 newTimeout 后将导致抛出 java.util.concurrent.RejectedExecutionException 异常。
			* 如果此值为 0 或负数，则不假定最大等待超时限制。

		* taskExecutor			执行器
			*  用于执行已提交的定时任务的执行器，调用者有责任在不再需要执行器时将其关闭。
	

	# 实例方法
		public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) 
			* task，要执行的任务，函数式接口
				 void run(Timeout timeout) throws Exception;
				
			* delay 延迟多久时间
			* unit 时间单位
				
		public long pendingTimeouts()

		public void start()
			* 启动时间轮，不需要特意调用
			* 每次添加任务的时候都会调用

		public Set<Timeout> stop()
			* 释放该 Timer 获取的所有资源，并取消所有已调度但尚未执行的任务。
			* 返回与本方法取消的任务相关的句柄

	# Timeout 接口
		Timer timer();
			* 返回 Timer, 本质上就是 HashedWheelTimer 实例

		TimerTask task();
		boolean isExpired();
		boolean isCancelled();
		boolean cancel();



-------------------------
Demo
-------------------------
	# 基本用法
		ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("timer#", 1).factory());
		
		HashedWheelTimer timer = new HashedWheelTimer(Thread.ofVirtual().name("fac#", 1).factory(), 100, TimeUnit.MILLISECONDS, 512, true, -1, executor);
		
		timer.newTimeout(timeout -> {
			log.info("任务执行");
		}, 1, TimeUnit.SECONDS);
		
		timer.start();
		
		System.in.read();