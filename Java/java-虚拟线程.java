----------------
虚拟线程
----------------
	# 类似于 go 中的协程，由程序调度的异步单元

	# 通过 Thread 类的 startVirtualThread 方法，立即运行协程
		Thread.startVirtualThread(() -> {});
	
	# 创建协程类，手动运行
		// 创建虚拟线程
		OfVirtual ofVirtual = Thread.ofVirtual();
		// 设置任务，返回线程
		Thread thread = ofVirtual.unstarted(() -> {});
		// 执行线程
		thread.start();

	# 通过 ExecutorService 执行
		ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
		executorService.execute(() -> {});;
	
	# 通过 ThreadFactory 运行
		// 获取 Factory
		ThreadFactory factory = Thread.ofVirtual().factory();
		// 创建协程
		// VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1
		Thread thread = factory.newThread(() -> System.out.println(Thread.currentThread()));
		// 运行
		thread.start();
		
		// 等待协程执行完毕
		thread.join();
	

	# 限制

		* 注意到只有以虚拟线程方式运行的代码，才会在执行IO操作时自动被挂起并切换到其他虚拟线程。
		* 普通线程的IO操作仍然会等待，例如，我们在main()方法中读写文件，是不会有调度和自动挂起的。
		* 可以自动引发调度切换的操作包括：
				文件IO；
				网络IO；
				使用Concurrent库引发等待；
				Thread.sleep()操作。
		
		* 在虚拟线程中，如果绕过JDK的IO接口，直接通过 JNI 读写文件或网络是无法实现调度的。此外，在 synchronized 块内部也无法调度。
		* 推荐使用 ReentrantLock


--------------------------------
Builder
--------------------------------
	Builder name(String name);
	Builder name(String prefix, long start);
	Builder inheritInheritableThreadLocals(boolean inherit);
	Builder uncaughtExceptionHandler(UncaughtExceptionHandler ueh);
	Thread unstarted(Runnable task);
	Thread start(Runnable task);
	ThreadFactory factory();

--------------------------------
OfVirtual 虚拟线程
--------------------------------
	interface OfVirtual extends Builder
	
	Builder name(String name);
	Builder name(String prefix, long start);
	Builder inheritInheritableThreadLocals(boolean inherit);
	Builder uncaughtExceptionHandler(UncaughtExceptionHandler ueh);
	Thread unstarted(Runnable task);
		* 返回 Thread 对象，调用 start 执行

	Thread start(Runnable task);
		* 创建直接启动

	ThreadFactory factory();


--------------------------------
OfPlatform 平台线程
--------------------------------
	interface OfPlatform extends Builder

	OfPlatform name(String name);
	OfPlatform name(String prefix, long start);
	OfPlatform inheritInheritableThreadLocals(boolean inherit);
	OfPlatform uncaughtExceptionHandler(UncaughtExceptionHandler ueh);

	group(ThreadGroup group);
	daemon(boolean on);
	OfPlatform daemon()
	OfPlatform priority(int priority);
	OfPlatform stackSize(long stackSize);