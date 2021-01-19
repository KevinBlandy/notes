---------------------------
RocketMQ-集群消费			|
---------------------------
	# '如果Consumer没有指定消费模式,那么默认就是集群消费'
	# 一条消息,只能被集群中的一个Consumer消费.
	# 通过负载均衡策略,轮询或者其他算法决定
	# Consumer设置集群消费
		consumer.setMessageModel(MessageModel.CLUSTERING);

---------------------------
RocketMQ-广播消费			|
---------------------------
	# 一条消息,集群中的所有的Consumer都要消费
	# Consumer设置广播消费
		consumer.setMessageModel(MessageModel.BROADCASTING);
	

	# 简单代码

		public static void main(String[] args) throws MQClientException{  
			DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("quickstart_consumer");
			consumer.setNamesrvAddr("192.168.250.199:9876;192.168.250.277:9876");
			consumer.setMessageModel(MessageModel.BROADCASTING);
			consumer.subscribe("TopicQuickstart", "*");
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
					//消费逻辑
				}
			});
			consumer.start();		
			System.out.println("消费者启动");
		}
