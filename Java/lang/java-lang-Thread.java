---------------------------
Thread						|
---------------------------
	# 多线程启动对象
	# 构造方法
		Thread t = new Thread();
		Thread t = new Thread(Runnable r);
	
	# 新构造的线程对象是由其parent线程来进行空间分配的
		* child线程继承了parent是否为Daemon、优先级和加载资源的contextClassLoader以及可继承的ThreadLocal
		* 同时还会分配一个唯一的ID来标识这个child线程


---------------------------
Thread-属性					|
---------------------------
	# 静态属性
	
	# 实例属性

---------------------------
Thread-方法					|
---------------------------
	# 静态方法
		public static Thread currentThread();
			* 返回当前的线程对象

		public static void sleep(long l);
			* 当前线程停止 l 毫秒
		
		public static Map<Thread, StackTraceElement[]> getAllStackTraces()
			* 获取到JVM中所有线程的执行栈
		
		public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler eh)
			* 设置默认的线程中未被捕获异常


	# 实例方法
		
		getName();
			* 返回线程名称
		
		void setPriority(int newPriority)
			* 设置线程的优先级， 1-0,线程优先级就是决定线程需要多或者少分配一些处理器资源的线程属性
			* Thread 类提供了N多的静态变量值
				Thread.MAX_PRIORITY = 10;
				Thread.MIN_PRIORITY = 1;
				Thread.NORM_PRIORITY = 5;
			* 有些操作系统甚至会忽略对线程优先级的设定
		
		int getPriority()
			* 获取线程的优先级
		
		void interrupt()
			* 中断线程
		
		boolean isInterrupted()
			* 线程是否被中断
		
		void join();
		void join(long millis)
		void join(long millis, int nanos)
			* 调用该方法的线程会一直阻塞,直到该线程(join 方法的 Thread 线程)执行完毕后才往下执行
			* 也可以设置超时时间

		void setDaemon(boolean on)
			* 设置为当前线程的守护线程
			* 必须在调用 start() 方法之前设置
		
		void stop();
			* 暴力停止该线程
		
		boolean isAlive()
			* 判断线程是否还存活
		
		void setContextClassLoader(ClassLoader cl)
		ClassLoader getContextClassLoader()
			* 设置/获取当前线程程序中使用的 classloader

		public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh)
			* 用于处理线程执行过程中，那些未被catch的异常
			* 函数接口
				 void uncaughtException(Thread t, Throwable e);
			


---------------------------
Thread 的中断机制			|
---------------------------
	# 每个线程都有一个 "中断" 标志,这里分两种情况
	
	# 线程在sleep或wait(阻塞),join ....
		* 此时如果别的进程调用此进程(Thread 对象)的 interrupt()方法,此线程会被唤醒并被要求处理 InterruptedException
		* (thread在做IO操作时也可能有类似行为,见java thread api)
		* InterruptedException 异常发生后，并不会修改线程的中断标识位，需要自己手动修改
	
	# 此线程在运行中
		* 此时如果别的进程调用此进程(Thread 对象)的 interrupt()方法,不会收到提醒,但是此线程的 "中断" 会被设置为 true
		* 可以通过 isInterrupted() 查看并作出处理

	# 总结
		interrupt()		实例方法	返回 void		中断调用该方法的当前线程
		interrupted()	静态方法	返回 boolean	检测当前线程是否被中断，如已被中断过则清除中断状态
			* 注意, 这是一个静态方法，只能对当前进程进行操作

			* 测试当前线程是否已被中断。本方法将清除线程的中断状态。
			* 换句话说，如果连续调用此方法两次，则第二次调用将返回 false（除非当前线程在第一次调用清除中断状态后、第二次调用检查中断状态前再次中断）。


		isInterrupted()	实例方法	返回 boolean	检测调用该方法的线程是否被中断，不清除中断标记
	
	# 优雅的通知线程结束
		public class MainTest {
			public static void main(String[] args) throws Exception {
				Thread thread = new Thread(() -> {
					while (!Thread.interrupted()) {
						try {

							// TODO 业务处理
							
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// 线程被中断，手动重置标识位为: true
							// 如果不手动重置，线程中断标识不会修改（仍然是false），则while循环不会退出
							Thread.currentThread().interrupt();
						}
					}
				});
				
				thread.start();

				Thread.sleep(2000);
				
				thread.interrupt();
			}
		}


---------------------------
Thread 状态
---------------------------
	NEW
		* 新创建，但是还没调用 start(); 方法
	
	RUNNABLE
		* 运行状态，Java线程把操作系统中的就绪和运行状态都成为RUNNABLE
	
	BLOCKED
		* 阻塞状态，表示线程阻塞于锁
	
	WAITING
		* 等待状态，表示线程进入等待状态，进入该状态表示当前线程需要等待其他线程做出一些特定动作（通知/中断）
	
	TIME_WAITING
		* 超时等待，这个状态和WAITING不同，它可以在指定时间自行返回，例如：Sleep

	TERMINATED
		* 终止状态，线程已经执行完毕
	
---------------------------
Thread 等待唤醒机制
---------------------------
	# 所有对象都可以当做锁，都有几个方法
		void notify();
		void notifyAll();
			* 唤醒一个/所有线程
			* 线程状态进入: BLOCKED

		void wait() throws InterruptedException
		void wait(long timeout)
		void wait(long timeout, int nanos)
			* 进入等待状态，可以设置超时时间
			* 线程状态进入: WAITING
	
		
		* 调用这些方法，需要枷锁，在sync...代码块中调用
	
	# 一般工作模式
		* 生产者
			synchronized ([lock]) {
				while ([condition不满足]) {
					[lock].wait();
				}
				// 处理业务逻辑
			}
		
		* 消费者
			synchronized ([lock]) {
				// 改变条件
				[lock].notifyAll();
			}