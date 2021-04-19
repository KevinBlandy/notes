---------------------------
WorkerExecutor
---------------------------
	# 异步任务接口： interface WorkerExecutor extends Measured 


---------------------------
抽象
---------------------------
	<T> void executeBlocking(Handler<Promise<T>> blockingCodeHandler, boolean ordered, Handler<AsyncResult<@Nullable T>> resultHandler);
	default <T> void executeBlocking(Handler<Promise<T>> blockingCodeHandler, Handler<AsyncResult<@Nullable T>> resultHandler)
	<T> Future<@Nullable T> executeBlocking(Handler<Promise<T>> blockingCodeHandler, boolean ordered)
	default <T> Future<T> executeBlocking(Handler<Promise<T>> blockingCodeHandler)
		* 执行阻塞任务

	void close(Handler<AsyncResult<Void>> handler)
	Future<Void> close()
		* 释放资源
	


