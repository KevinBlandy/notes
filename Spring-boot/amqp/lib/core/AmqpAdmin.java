------------------------
AmqpAdmin
------------------------
	# Amqp����MQϵͳ�Ĺ���ӿ�
	# ����ͨ��IOC�Զ���ȡ��
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