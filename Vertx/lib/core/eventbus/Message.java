---------------------
Message
---------------------
	# 消息对象接口： interface Message<T> 

---------------------
抽象
---------------------
	String address();
		* 消息地址

	MultiMap headers();
		* header信息

	T body();
		* body数据

	String replyAddress();
	boolean isSend();
	default void reply(@Nullable Object message)
	void reply(@Nullable Object message, DeliveryOptions options)
		* 将一个应答消息返回给发送者并调用发送者的应答处理器

	default <R> void replyAndRequest(@Nullable Object message, Handler<AsyncResult<Message<R>>> replyHandler)
	default <R> Future<Message<R>> replyAndRequest(@Nullable Object message) 
	default <R> void replyAndRequest(@Nullable Object message, DeliveryOptions options, Handler<AsyncResult<Message<R>>> replyHandler)
	<R> Future<Message<R>> replyAndRequest(@Nullable Object message, DeliveryOptions options)
		* 发送应答消息给生产者，并且监听生产者的回复

	default void fail(int failureCode, String message)
		* 指定这个消息异常，设置状态码和消息
		* 会触发发送者的监听程序失败逻辑，异常对象是：io.vertx.core.eventbus.ReplyException
			
			MessageConsumer<Message<String>> consumer = eventBus.consumer("demo");
			consumer.handler(message -> {
				message.fail(999, "系统异常");
			});

			eventBus.request("demo", "Hello", reply -> {
				Message<Object> result = reply.result();
				if (reply.succeeded()) {
					System.out.println("收到回复:" + result.body());
				} else {
					ReplyException throwable = (ReplyException) reply.cause();
					System.out.println("回复异常了：" + throwable.failureCode() + ":" + throwable.getMessage() + ":" + throwable.failureType());
				}
				
			});