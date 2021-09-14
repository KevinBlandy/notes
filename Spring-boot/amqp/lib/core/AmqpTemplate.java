-------------------------
AmqpTemplate
-------------------------
	# AMQP 消息发送接口
		public interface AmqpTemplate 

	# 方法
		void send(Message message) throws AmqpException;
		void send(String routingKey, Message message) throws AmqpException;
		void send(String exchange, String routingKey, Message message) throws AmqpException;
		void convertAndSend(Object message) throws AmqpException;
		void convertAndSend(String routingKey, Object message) throws AmqpException;
		void convertAndSend(String exchange, String routingKey, Object message) throws AmqpException;
		void convertAndSend(Object message, MessagePostProcessor messagePostProcessor) throws AmqpException;
		void convertAndSend(String routingKey, Object message, MessagePostProcessor messagePostProcessor) throws AmqpException;
		void convertAndSend(String exchange, String routingKey, Object message, MessagePostProcessor messagePostProcessor) throws AmqpException;

		Message receive() throws AmqpException;
		Message receive(String queueName) throws AmqpException;
		Message receive(long timeoutMillis) throws AmqpException;
		Message receive(String queueName, long timeoutMillis) throws AmqpException;

		Object receiveAndConvert() throws AmqpException;
		Object receiveAndConvert(String queueName) throws AmqpException;
		Object receiveAndConvert(long timeoutMillis) throws AmqpException;
		Object receiveAndConvert(String queueName, long timeoutMillis) throws AmqpException;
		<T> T receiveAndConvert(ParameterizedTypeReference<T> type) throws AmqpException;
		<T> T receiveAndConvert(String queueName, ParameterizedTypeReference<T> type) throws AmqpException;
		<T> T receiveAndConvert(long timeoutMillis, ParameterizedTypeReference<T> type) throws AmqpException;
		<T> T receiveAndConvert(String queueName, long timeoutMillis, ParameterizedTypeReference<T> type) throws AmqpException;

		<R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback) throws AmqpException;
		<R, S> boolean receiveAndReply(String queueName, ReceiveAndReplyCallback<R, S> callback) throws AmqpException;
		<R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback, String replyExchange, String replyRoutingKey) throws AmqpException;
		<R, S> boolean receiveAndReply(String queueName, ReceiveAndReplyCallback<R, S> callback, String replyExchange, String replyRoutingKey) throws AmqpException;
		<R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback, ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException;
		<R, S> boolean receiveAndReply(String queueName, ReceiveAndReplyCallback<R, S> callback, ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException;
		
		Message sendAndReceive(Message message) throws AmqpException;
		Message sendAndReceive(String routingKey, Message message) throws AmqpException;
		Message sendAndReceive(String exchange, String routingKey, Message message) throws AmqpException;

		Object convertSendAndReceive(Object message) throws AmqpException;
		Object convertSendAndReceive(String routingKey, Object message) throws AmqpException;
		Object convertSendAndReceive(String exchange, String routingKey, Object message) throws AmqpException;
		Object convertSendAndReceive(Object message, MessagePostProcessor messagePostProcessor) throws AmqpException;
		Object convertSendAndReceive(String routingKey, Object message, MessagePostProcessor messagePostProcessor) throws AmqpException;
		Object convertSendAndReceive(String exchange, String routingKey, Object message, MessagePostProcessor messagePostProcessor) throws AmqpException;

		<T> T convertSendAndReceiveAsType(Object message, ParameterizedTypeReference<T> responseType) throws AmqpException;
		<T> T convertSendAndReceiveAsType(String routingKey, Object message, ParameterizedTypeReference<T> responseType) throws AmqpException;
		<T> T convertSendAndReceiveAsType(String exchange, String routingKey, Object message, ParameterizedTypeReference<T> responseType) throws AmqpException;
		<T> T convertSendAndReceiveAsType(Object message, MessagePostProcessor messagePostProcessor, ParameterizedTypeReference<T> responseType) throws AmqpException;
		<T> T convertSendAndReceiveAsType(String routingKey, Object message, MessagePostProcessor messagePostProcessor, ParameterizedTypeReference<T> responseType) throws AmqpException;
		<T> T convertSendAndReceiveAsType(String exchange, String routingKey, Object message, MessagePostProcessor messagePostProcessor, ParameterizedTypeReference<T> responseType) throws AmqpException;




		




