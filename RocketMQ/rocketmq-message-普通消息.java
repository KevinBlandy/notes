------------------------
普通的消息生产			|
------------------------
	# 同步发送
		DefaultMQProducer producer = new DefaultMQProducer("PRODUCER-GROUP-NAME");
		producer.setNamesrvAddr("localhost:9876");
		producer.start();

		Message message = new Message("TopicTest", "TagA", "HelloWorld".getBytes());

		// 执行同步发送，获取到发送结果
		SendResult sendResult = producer.send(message);
		System.out.printf("%s%n", sendResult);
		producer.shutdown();
	

	# 异步发送
		DefaultMQProducer producer = new DefaultMQProducer("PRODUCER-GROUP-NAME");
		producer.setNamesrvAddr("localhost:9876");
		producer.start();

		// 设置异步模式下，消息发送失败后的重试次数
		producer.setRetryTimesWhenSendAsyncFailed(0);

		Message message = new Message("TopicTest", "TagA", "OrderID188", "Hello world".getBytes());

		producer.send(message, new SendCallback() {
			@Override
			public void onSuccess(SendResult sendResult) {
				System.out.printf("异步发送成功");
			}
			@Override
			public void onException(Throwable e) {
				System.out.printf("同步发送成功");
				e.printStackTrace();
			}
		});

		producer.shutdown();
	
	# 单向发送
		* 只管发，不管成功与否。类似于UDP协议
		* 极大的提升了吞吐量，但是数据可能会丢失
		* 适用于不是特别关心发送结果的场景，例如：日志

		DefaultMQProducer producer = new DefaultMQProducer("PRODUCER-GROUP-NAME");
		producer.setNamesrvAddr("localhost:9876");
		producer.start();
		
		Message message = new Message("TopicTest", "TagA", "HelloWorld".getBytes());
		// 发送单向消息，没有任何返回结果
		producer.sendOneway(message);
		
		producer.shutdown();

------------------------
普通的消息消费			|
------------------------
	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("CONSUMER-GROP-NAME");
	consumer.setNamesrvAddr("localhost:9876");

	// 监听某个Topic，可以监听多个
	consumer.subscribe("TopicTest", "*");

	consumer.registerMessageListener(new MessageListenerConcurrently() {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
			// 返回消息的消费结果
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	});

	consumer.start();

	System.out.printf("Consumer Started.%n");