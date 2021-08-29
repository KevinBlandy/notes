------------------------
AmqpAdmin
------------------------
	# Amqp对于MQ系统的管理接口
	# 可以通过IOC自动获取到
		void declareExchange(Exchange exchange);
		boolean deleteExchange(String exchangeName);
		@Nullable
		Queue declareQueue();
		@Nullable
		String declareQueue(Queue queue);
		boolean deleteQueue(String queueName);
		void deleteQueue(String queueName, boolean unused, boolean empty);
		void purgeQueue(String queueName, boolean noWait);
		int purgeQueue(String queueName);
		void declareBinding(Binding binding);
		void removeBinding(Binding binding);
		Properties getQueueProperties(String queueName);
		QueueInformation getQueueInfo(String queueName);
		void initialize()