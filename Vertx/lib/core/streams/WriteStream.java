-----------------------------
WriteStream
-----------------------------
	# 写流： interface WriteStream<T> extends StreamBase

	WriteStream<T> exceptionHandler(Handler<Throwable> handler);
		* 异常处理

	Future<Void> write(T data);
	void write(T data, Handler<AsyncResult<Void>> handler);	
		* 写入数据，永远不会阻塞

	default Future<Void> end()
	void end(Handler<AsyncResult<Void>> handler);
	default Future<Void> end(T data)
	default void end(T data, Handler<AsyncResult<Void>> handler)
		* 完成写入，写入最后一个消息，发送就断开

	WriteStream<T> setWriteQueueMaxSize(int maxSize);
		* 设置写队列大小

	boolean writeQueueFull();
		* 判断写队列，是否满了

	WriteStream<T> drainHandler(@Nullable Handler<Void> handler);
		* 空闲监听，当队列有空闲位置后，会触发
