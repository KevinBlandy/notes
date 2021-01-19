------------------------
RocketMQ-API-Producer	|
------------------------
	DefaultMQProducer
		# 默认消息生产者对象
		# new DefaultMQProducer(String producerGroupName);
		void setNamesrvAddr(String address);
			* 设置nameserver地址,多个地址用分号(;)分割
		
		void start();
			* 启动
		
		SendResult send(Message message);
			* 发送消息,返回SendResult
		
		void send(Message message, long timeout);
			* 执行消息发送,'并且指定超时时间',没有返回值.
			* 如果超时,消息还未发送.则认为消息失败.会根据 retryTimesWhenSendFailed 进行消息重发

		void setRetryTimesWhenSendFailed(int type);
			* 设置当消息发送失败的时候,重发次数

	Message
		# 普通消息对象
		# new Message(String topic,String tag,byte[] message);

	
	SendResult
		# 消息发送结果对象

------------------------
RocketMQ-API-Consumer	|
------------------------
	DefaultMQPushConsumer
		# 默认消息接收对象
		# new DefaultMQPushConsumer("quickstart_consumer");
		void setNamesrvAddr(String address);
			* 设置nameserver地址,多个用 ';' 分号分割

		void consumer.setConsumeFromWhere(ConsumeFromWhere where);
			* 设置Consumer的'第一次启动',是从头部开始消费,还是从尾部开始消费
			* 如果'并非是第一次启动',那么会按照上次消费的位置进行消费
		
		void consumer.subscribe(String topic, String tags);
			* 设置队列,以及标签
			* 多个标签之间用 || 分割("tag1 || tag2 || tag3")
			* 标签也可以使用通配符 "*",表示所有
		
		void registerMessageListener(MessageListenerConcurrently listener);
			* 注册监听器
		
		void setConsumeThreadMax(int max);
			* 默认 20
			* 设置消费,消费消息线程池的最大线程数
		
		void setConsumeThreadMin(int min);
			* 默认 10
			* 设置消费,消费消息线程池的最小线程数
	
		void setConsumeMessageBatchMaxSize(int size);
			* 默认 1
			* 设置一次性从服务器拉取多少条消息
			* 批量拉取的情况,仅仅存在于.customer端重启,broker存在消息堆积,此时.进行批量拉取消息
			* 如果是正常的监听状态,broker没有堆积,那么是正常的即时消费,有一条消费一条
			* 是最大限制,但是实际上每次拉取的数量,是最大限制内的随机数

		void setAllocateMessageQueueStrategy(AllocateMessageQueueStrategy allocateMessageQueueStrategy);
			* 负载均衡策略,有N多中
				AllocateMessageQueueAveragely			
					* 负载均衡策略
				AllocateMessageQueueAveragelyByCircle
					* 轮询策略

				AllocateMessageQueueByConfig
				AllocateMessageQueueByMachineRoom
				

		
		void setMessageModel(MessageMode model);
			* 设置消费模式
			* MessageModel.CLUSTERING,集群消费('默认')
			* MessageModel.BROADCASTING,广播消费

		void setPullInterval(long time);
			* 默认为 0,单位是毫秒
			* 消息拉取线程,每隔多久拉取一次消息
		
		void setPullThresholdForQueue(int max);
			* 默认 1000
			* 设置,拉取消息时,本地队列缓存消息的最大数量
		
		void setPullBatchSize(int size);
			* 默认 32 
	
	MessageListenerConcurrently
		# 消息监听对象,是一个接口.需要自己实现
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context);
			* 消息事件触发
			* 返回值
				ConsumeConcurrentlyStatus.CONSUME_SUCCESS
					> 表示消息消费成功
				ConsumeConcurrentlyStatus.RECONSUME_LATER
					> 表示消息消费失败'并且要求Broker一段时间后重发消息'
					> 如果是即时消费,则只重发当前消息
					> 如果是批量消费,则 msgs 里面所有消息都会被重发

	MessageListenerOrderly
		# 消息监听对象,是一个接口.需要自己实现
		public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) ;
		# 它是'顺序消费Consumer'的监听,只有这个监听才能保证顺序消费




------------------------
RocketMQ-API-Other		|
------------------------
	
	