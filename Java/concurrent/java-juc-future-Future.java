-------------------------------
Future<V>						|
-------------------------------
	# 异步执行结果的接口
	# 方法
		boolean cancel(boolean mayInterruptIfRunning);
			* 取消执行
			* mayInterruptIfRunning 设置是否要抛出线程中断异常

		boolean isCancelled();
			* 是否取消执行

		boolean isDone();
			* 是否执行完毕

		V get() throws InterruptedException, ExecutionException;
			* 获取到执行的结果,会阻塞当前的线程

		V get(long timeout, TimeUnit unit)throws InterruptedException, ExecutionException, TimeoutException;
			* 获取到执行的结果,会阻塞当前的线程
			* 可以设置一个超时时间,超时后抛出 TimeoutException

