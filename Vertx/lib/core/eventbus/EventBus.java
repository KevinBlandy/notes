--------------------------
EventBus
--------------------------
	# 消息总线接口： interface EventBus extends Measured 


--------------------------
抽象
--------------------------
	EventBus send(String address, @Nullable Object message);
	EventBus send(String address, @Nullable Object message, DeliveryOptions options);
		* 发送点对点消息，只有一个处理器会执行，可以指定DeliveryOptions配置信息

	default <T> EventBus request(String address, @Nullable Object message, Handler<AsyncResult<Message<T>>> replyHandler)
	default <T> Future<Message<T>> request(String address, @Nullable Object message)
	default <T> EventBus request(String address, @Nullable Object message, DeliveryOptions options, Handler<AsyncResult<Message<T>>> replyHandler)
	<T> Future<Message<T>> request(String address, @Nullable Object message, DeliveryOptions options)
		* 发送消息，并且注册响应监听
		* 点对点消息，消费者回应后可以通过 replyHandler 获取到返回的消息

	EventBus publish(String address, @Nullable Object message)
	EventBus publish(String address, @Nullable Object message, DeliveryOptions options)
		* 发布消息，这个地址的所有处理器都会执行

	<T> MessageConsumer<T> consumer(String address)
	<T> MessageConsumer<T> consumer(String address, Handler<Message<T>> handler)
		* 通过返回的MessageConsumer，来注册处理器
		* 也可以直接注册处理器
			vertx.eventBus().consumer("news.uk.sport", message -> {
			  System.out.println("I have received a message: " + message.body());
			});

	<T> MessageConsumer<T> localConsumer(String address)
	<T> MessageConsumer<T> localConsumer(String address, Handler<Message<T>> handler)
		* 本地消息，不会再集群中传播

	<T> MessageProducer<T> sender(String address)
	<T> MessageProducer<T> sender(String address, DeliveryOptions options)
	<T> MessageProducer<T> publisher(String address)
	<T> MessageProducer<T> publisher(String address, DeliveryOptions options)
		* 使用地址和配置，创建消息发送对象

	EventBus registerCodec(MessageCodec codec)
	EventBus unregisterCodec(String name)
		* 注册消息编解码器
		* 通过编解码器的名称取消注册
		
	<T> EventBus registerDefaultCodec(Class<T> clazz, MessageCodec<T, ?> codec)
	EventBus unregisterDefaultCodec(Class clazz);
		* 为指定类，取消注册默认的消息编解码器，发送的时候，不需要指定编解码器的名称了
		* 也可以通过指定类，取消编解码器的注册

	<T> EventBus addOutboundInterceptor(Handler<DeliveryContext<T>> interceptor);
	<T> EventBus removeOutboundInterceptor(Handler<DeliveryContext<T>> interceptor);
	<T> EventBus addInboundInterceptor(Handler<DeliveryContext<T>> interceptor);
	<T> EventBus removeInboundInterceptor(Handler<DeliveryContext<T>> interceptor);


--------------------------
静态
--------------------------