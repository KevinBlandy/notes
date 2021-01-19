---------------------
分区分配策略		 |
---------------------
	# 分区分配策略配置:partition.assignment.strategy
		* 参数值为:PartitionAssignor 接口的实现类全路径
		* 系统提供的默认实现
			org.apache.kafka.clients.consumer.RangeAssignor(默认)
			org.apache.kafka.clients.consumer.RoundRobinAssignor
			org.apache.kafka.clients.consumer.StickyAssignor
	
	# PartitionAssignor 接口抽象方法
		Subscription subscription(Set<String> topics);
		Map<String, Assignment> assign(Cluster metadata, Map<String, Subscription> subscriptions);
		void onAssignment(Assignment assignment);
		String name();
	
	
	# RangeAssignor
		* 按照消费者总数和分区总数进行整除运算来获得一个跨度,然后将分区按照跨度进行平均分配,以保证分区尽可能均匀地分配给所有的消费者


	# RoundRobinAssignor
		* 将消费组内所有消费者及消费者订阅的所有主题的分区按照字典序排序
		* 然后通过轮询方式逐个将分区依次分配给每个消费者
		* 如果同一个消费组内所有的消费者的订阅信息都是相同的,那么 RoundRobinAssignor 分配策略的分区分配会是均匀的

	# StickyAssignor
		* 它主要有两个目的 
			分区的分配要尽可能均匀 
			分区的分配尽可能与上次分配的保持相同
		
		* 当两者发生冲突时,第一个目标优先于第二个目标
	
	
	# 按照 Kafka 默认的消费逻辑设定, 一个分区 只能被同一个消费组( ConsumerGroup ) 内的一个消费者消费
		* 但这一设定不是绝对的,可以通过自定义分区分配策略使一个分区可以分配给多个消费者消费
		* 种实现方式会有一个严重的问题,默认的消费位移的提交会失效
		* 所有的消费者都会提交它自身的消费位移到 consumer_offsets 中,后提交的消费位移会覆盖前面提交的消费位移
