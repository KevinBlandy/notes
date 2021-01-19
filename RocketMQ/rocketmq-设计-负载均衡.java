--------------------------------
负载均衡						|
--------------------------------
	# Producer的负载均衡
		* Producer端在发送消息的时候，会先根据Topic找到指定的TopicPublishInfo
		* 在获取了TopicPublishInfo路由信息后，RocketMQ的客户端在默认方式下 selectOneMessageQueue() 方法会从TopicPublishInfo中的messageQueueList中选择一个队列（MessageQueue）进行发送消息。
			public MessageQueue selectOneMessageQueue() {
				int index = this.sendWhichQueue.getAndIncrement();
				int pos = Math.abs(index) % this.messageQueueList.size();
				if (pos < 0)
					pos = 0;
				return this.messageQueueList.get(pos);
			}
		
		* 具体的容错策略均在 MQFaultStrategy 这个类中定义
		* 这里有一个 "latencyFaultTolerance" 开关变量，如果开启(默认 false)，在随机递增取模的基础上，再过滤掉not available(不可用)的Broker
		* 所谓的"latencyFaultTolerance"，是指对之前失败的，按一定的时间做退避
			* 例如，如果上次请求的 latency(延迟) 超过550Lms，就退避3000Lms
			* 超过1000L，就退避60000L

		* 如果关闭，采用随机递增取模的方式选择一个队列（MessageQueue）来发送消息，latencyFaultTolerance机制是实现消息发送高可用的核心关键所在。
	

	# Consumer的负载均衡
		
