--------------------
ReadStream
--------------------
	# 读取流接口： interface ReadStream<T> extends StreamBase

	ReadStream<T> exceptionHandler(Handler<Throwable> handler);
		* 异常处理器
		
	ReadStream<T> handler(@Nullable Handler<T> handler);
		* 消息处理器

	ReadStream<T> pause();
		* 暂停读取，设置ReadStream 为 fetch 模式 并设置demand值为0

	ReadStream<T> resume();
		* 继续读取，设置ReadStream 为 flowing 模式
		* 恢复处理器，若任何对象到达目的地则handler将被触发；等价于 fetch(Long.MAX_VALUE)

	ReadStream<T> fetch(long amount);
		* 请求指定数量的stream元素并将该数量加到目前的demand值当中
		* 从stream中抓取指定数量的对象，任意对象抵达stream时，都会触发handler， fetch操作是累积的。

	ReadStream<T> endHandler(@Nullable Handler<Void> endHandler);
		* 流结束处理器，读取到了EOF

	default Pipe<T> pipe()
		* 获取管道流

	default Future<Void> pipeTo(WriteStream<T> dst)
	default void pipeTo(WriteStream<T> dst, Handler<AsyncResult<Void>> handler
		* IO到指定流，会自动处理背压问题