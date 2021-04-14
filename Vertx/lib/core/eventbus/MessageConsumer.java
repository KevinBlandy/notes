----------------------
MessageConsumer
----------------------
	# 消息消费者接口： interface MessageConsumer<T> extends ReadStream<Message<T>>


----------------------
抽象方法
----------------------
	MessageConsumer<T> exceptionHandler(Handler<Throwable> handler);
	MessageConsumer<T> handler(Handler<Message<T>> handler);
		* 添加一个处理器，重复调用会覆盖

	MessageConsumer<T> pause();
	MessageConsumer<T> resume();
	MessageConsumer<T> fetch(long amount);
	MessageConsumer<T> endHandler(Handler<Void> endHandler);
	ReadStream<T> bodyStream();
	boolean isRegistered();
	String address();
	MessageConsumer<T> setMaxBufferedMessages(int maxBufferedMessages);
	int getMaxBufferedMessages();
	void completionHandler(Handler<AsyncResult<Void>> completionHandler);
		* 在集群的环境下，注册处理器需要一些时间
		* 这个方法会在处理器注册成功后回调

	Future<Void> unregister();
		* 注销处理器

	void unregister(Handler<AsyncResult<Void>> completionHandler);

	default Pipe<T> pipe()
	default Future<Void> pipeTo(WriteStream<T> dst) 
	default void pipeTo(WriteStream<T> dst, Handler<AsyncResult<Void>> handler)


