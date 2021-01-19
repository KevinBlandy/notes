---------------------------
RabbitMQ-消息确认模式		|
---------------------------
	# 在RabbitMQ中,消费者从队列中获取消息,服务端如果知道消息已经被消费?
	# 在消费者监听的时候,一个设置决定了模式
	    channel.basicConsume(QUEUE_NAME, false, consumer);
		* 第二个参数如果是 false,则是手动模式,需要手写代码向服务通知消费完成
			 channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

		* 第二个参数如果是 true ,则是自动模式


---------------------------
RabbitMQ-自动模式			|
---------------------------
	1,自动确定
		* 只要是消息从队列中取出,无论消费者是否获取到消息.都认为消息已经被成功的消费
	
---------------------------
RabbitMQ-手动模式			|
---------------------------
	2,手动确定
		* 消费者从队列中获取消息后,服务会把该消息标记为不可用状态,如果消费者没有反馈.那么该消息就一直处于不可用状态.这种状态下.其他消费者不能消费
		   channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);


	

