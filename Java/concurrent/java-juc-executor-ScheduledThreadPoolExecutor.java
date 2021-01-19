--------------------------------
ScheduledThreadPoolExecutor		|
--------------------------------
	# 处理定时任务的线程池,继承:ThreadPoolExecutor 实现:ScheduledExecutorService
	# 构造方法
		ScheduledThreadPoolExecutor(int corePoolSize)
		ScheduledThreadPoolExecutor(int corePoolSize,RejectedExecutionHandler handler)
		ScheduledThreadPoolExecutor(int corePoolSize,ThreadFactory threadFactory)
		ScheduledThreadPoolExecutor(int corePoolSize,ThreadFactory threadFactory,RejectedExecutionHandler handler)
	
	# 实例方法
		void execute(Runnable command)
		boolean getContinueExistingPeriodicTasksAfterShutdownPolicy()
		boolean getExecuteExistingDelayedTasksAfterShutdownPolicy()
		BlockingQueue<Runnable> getQueue()
		boolean getRemoveOnCancelPolicy()

		ScheduledFuture<?> schedule(Runnable command,long delay,TimeUnit unit)
		<V> ScheduledFuture<V> schedule(Callable<V> callable,long delay, TimeUnit unit)
			* * 在指定延迟后,执行command/callable

		ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit) 
		ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,long initialDelay,long delay,TimeUnit unit)

		void setContinueExistingPeriodicTasksAfterShutdownPolicy(boolean value)
		void setExecuteExistingDelayedTasksAfterShutdownPolicy(boolean value)
		void setRemoveOnCancelPolicy(boolean value)

		void shutdown() 
		List<Runnable> shutdownNow()
		Future<?> submit(Runnable task)
		<T> Future<T> submit(Runnable task, T result)
		<T> Future<T> submit(Callable<T> task)
