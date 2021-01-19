---------------------------
ExecutorService				|
---------------------------
	# 线程池工具
	# 体系
		Executor(接口)
			|-ExecutorService(接口)
				|-ThreadPoolExecutor(实现)
				|-ScheduledExecutorService(接口,负责线程调度)
					|-ScheduledThreadPoolExecutor(继承了 ThreadPoolExecutor 又实现了 ScheduledExecutorService)
	# 创建
		ExecutorService pool = Executors.newFixedThreadPool(2);		
			* 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。
		
		ExecutorService pool = Executors.newCachedThreadPool();
			* 创建一个可根据需要创建新线程的线程池,但是在以前构造的线程可用时将重用它们。
			* 对于执行很多短期异步任务的程序而言,这些线程池通常可提高程序性能。
			* 线程数量不固定,可以根据需求更新线程数量

		ExecutorService pool =  Executors.newSingleThreadExecutor()
			* 创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
			* 里面只有一个线程
		
		ScheduledExecutorService es =  Executors.newScheduledThreadPool(10);
			* 创建调度线程池
			* 创建固定大小的线程池,可以延时/重复的执行任务调度

	# 使用线程执行任务
		Future<?> submit(Runnable task);

		<T> Future<T> submit(Callable<T> task);

		void execute(Runnable command);

		* execute和submit的区别
			1. 接收的参数不一样;
			2. submit()有返回值，而execute()没有;
				例如，有个validation的task，希望该task执行完后告诉我它的执行结果，是成功还是失败，然后继续下面的操作。
			3. submit()可以进行Exception处理;
				例如，如果task里会抛出checked或者unchecked exception，而你又希望外面的调用者能够感知这些exception并做出及时的处理，那么就需要用到submit，通过对Future.get()进行抛出异常的捕获，然后对其进行处理。

	
	# 线程池调度
		ScheduledExecutorService es =  Executors.newScheduledThreadPool(10);

		ScheduledFuture<?> schedule(Runnable command,long delay, TimeUnit unit);
			* 在指定延迟后,执行command

		<V> ScheduledFuture<V> schedule(Callable<V> callable,long delay, TimeUnit unit);
			* 在指定延迟后,执行callable



	# api
		boolean isShutdown()
		void shutdown();
			* 会等到所有任务完成才会关闭

		List<Runnable> shutdownNow();
			* 立即关闭线程池

		boolean isTerminated();
			* 如果队列中的所有任务都处理完毕后返回 true