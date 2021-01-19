----------------------------
顺序消息					|
----------------------------
	# 在默认的情况下消息发送会采取Round Robin轮询方式把消息发送到不同的queue(分区队列)
		* 而消费消息的时候从多个queue上拉取消息，这种情况发送和消费是不能保证顺序
		* 连续几条消息，后面的几条可能会被先消费，因为它们不在一个Queue
	
	# 控制发送的顺序消息(多条消息)只依次发送到同一个queue中，消费的时候只从这个queue上依次拉取，则就保证了顺序
		* 当发送和消费参与的queue只有一个，则是全局有序
		* 如果多个queue参与，则为分区有序，即相对每个queue，消息都是有序的
	
	# 核心的思想
		* 一批需要被顺序消费的消息，保证它们在发送的时候，先后都发送到了同一个 MessageQueue
		* 可以设置一个共同的标识。ID，然后通过ID % MessageQueue.size() 来保证发送到相同的MessageQueue

----------------------------
顺序消息生产者				|
----------------------------
	DefaultMQProducer producer = new DefaultMQProducer("CONSUMER-GROUP-NAME");
	producer.setNamesrvAddr("127.0.0.1:9876");
	producer.start();

	// 多条顺序消息都有一个相同的订单id
	Integer orderId = 1;

	// 消息1
	Message message1 = new Message("TopicTest", "创建订单消息", "Hello".getBytes());
	// 自己实现MessageQueueSelector，选择发送消息的MessageQueue
	SendResult sendResult1 = producer.send(message1, new MessageQueueSelector() {
		@Override
		public MessageQueue select(List<MessageQueue> messageQueues, Message msg, Object arg) {
			// 通过取模，保证订单id相同的消息都发送到同一个Queue
			Integer id = (Integer) arg;
			int index = id % messageQueues.size();
			return messageQueues.get(index);
		}
	}, orderId);
	System.out.println(sendResult1);


	// 消息2
	Message message2 = new Message("TopicTest", "付款消息", "Hello".getBytes());
	SendResult sendResult2 = producer.send(message2, new MessageQueueSelector() {
		@Override
		public MessageQueue select(List<MessageQueue> messageQueues, Message msg, Object arg) {
			Integer id = (Integer) arg;
			int index = id % messageQueues.size();
			return messageQueues.get(index);
		}
	}, orderId);
	System.out.println(sendResult2);


	// 消息3
	Message message3 = new Message("TopicTest", "推送消息", "Hello".getBytes());
	SendResult sendResult3 = producer.send(message3, new MessageQueueSelector() {
		@Override
		public MessageQueue select(List<MessageQueue> messageQueues, Message msg, Object arg) {
			Integer id = (Integer) arg;
			int index = id % messageQueues.size();
			return messageQueues.get(index);
		}
	}, orderId);
	System.out.println(sendResult3);

	producer.shutdown();