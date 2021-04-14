---------------------------
MessageProducer
---------------------------
	# 消息生产接口：  interface MessageProducer<T> 


---------------------------
抽象
---------------------------
	MessageProducer<T> deliveryOptions(DeliveryOptions options);
		* 更新配置

	String address();
		* 返回地址

	void write(T body, Handler<AsyncResult<Void>> handler);
	Future<Void> write(T body);
		* 发送消息，监听发送结果

	Future<Void> close();
	void close(Handler<AsyncResult<Void>> handler);
		* 关闭，不再发送

---------------------------
静态
---------------------------
	