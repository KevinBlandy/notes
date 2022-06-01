----------------------------------------
ExecutorCompletionService
----------------------------------------
	# 构造函数
		 public ExecutorCompletionService(Executor executor) 
		 public ExecutorCompletionService(Executor executor, BlockingQueue<Future<V>> completionQueue)

	# 实例方法
		public Future<V> poll()
		public Future<V> poll(long timeout, TimeUnit unit)
			* 获取一个已经完成的任务，如果没有则阻塞
		
		public Future<V> submit(Runnable task, V result)
		public Future<V> submit(Callable<V> task)
			* 提交任务，通过 result 可以把 Runnable 封装为 Callable
		
		public Future<V> take() throws InterruptedException
			* 移除下一个执行完毕的任务，如果没有则阻塞
		
