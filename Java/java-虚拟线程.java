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
	
	# OfPlatform 获取
		OfPlatform ofPlatform = Thread.ofPlatform();

--------------------------------
OfVirtual 接口方法
--------------------------------
	Builder name(String name);
	Builder name(String prefix, long start);
	Builder inheritInheritableThreadLocals(boolean inherit);
	Builder uncaughtExceptionHandler(UncaughtExceptionHandler ueh);
	Thread unstarted(Runnable task);
		* 返回 Thread 对象，调用 start 执行

	Thread start(Runnable task);
		* 返回 Thread 对象

	ThreadFactory factory();

--------------------------------
OfVirtual 接口方法
--------------------------------
