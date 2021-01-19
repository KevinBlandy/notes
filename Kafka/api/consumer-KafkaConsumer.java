---------------------------
KafkaConsumer<K, V>		   |
---------------------------
	# 消息消费者
	# 构造函数
		KafkaConsumer(Map<String, Object> configs)
		KafkaConsumer(Map<String, Object> configs,Deserializer<K> keyDeserializer,Deserializer<V> valueDeserializer)
		KafkaConsumer(Properties properties)
		KafkaConsumer(Properties properties,Deserializer<K> keyDeserializer,Deserializer<V> valueDeserializer) 
	
	# 实例方法
		void close()
		void close(Duration timeout)
			* 关闭客户端,释放资源
			* timeout 设置超时时间,默认是 30s

		void commitAsync()
		void commitAsync(final Map<TopicPartition, OffsetAndMetadata> offsets, OffsetCommitCallback callback)
		void commitAsync(OffsetCommitCallback callback)
			* 异步提交消费位移
			* offsets 可以设置指定主题,指定分区的位移值
			* OffsetCommitCallback 设置回调
				void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception);

		void commitSync()
		void commitSync(Duration timeout)
		void commitSync(final Map<TopicPartition, OffsetAndMetadata> offsets)
		void commitSync(final Map<TopicPartition, OffsetAndMetadata> offsets, final Duration timeout)
			* offsets 可以设置指定主题,指定分区的位移值
			* 同步提交消费位移

		OffsetAndMetadata committed(TopicPartition partition)
		OffsetAndMetadata committed(TopicPartition partition, final Duration timeout)
			* 获取已经提交过的消费位移
			* OffsetAndMetadata
				private final long offset;
				private final String metadata;
				private final Integer leaderEpoch;
	
		Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions)
		Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions, Duration timeout)
			* 获取指定分区的开始消费偏移量
			* 这个偏移量不一定是0,因为kafka日志清理动作可能会清理旧的日志

		Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions)
		Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions, Duration timeout)
			* 获取指定分区的末尾消费偏移量(就是下次待写入消息的位置)
			* timeout指定超时时间,如果不指定,使用:request.timeout.ms 配置
		
		Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch)
		Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch, Duration timeout)
			* 获取指定时间的指定分区的消费偏移量
			* Map 里面的 value 字段就表示时间戳值
			* OffsetAndTimestamp
				private final long timestamp;			// 时间戳
				private final long offset;				// 消费偏移量
				private final Optional<Integer> leaderEpoch;

		Map<String, List<PartitionInfo>> listTopics()
		Map<String, List<PartitionInfo>> listTopics(Duration timeout)

		Map<MetricName, ? extends Metric> metrics()


		List<PartitionInfo> partitionsFor(String topic)
		List<PartitionInfo> partitionsFor(String topic, Duration timeout)
			* 返回指定主题的所有分区信息,timeout 指定等待返回元数据的超时时间
			* PartitionInfo 描述一个分区信息
					private final String topic;					// 主题
					private final int partition;				// 当前节点编号
					private final Node leader;					// leader节点
					private final Node[] replicas;				// ar列表
					private final Node[] inSyncReplicas;		// isr列表
					private final Node[] offlineReplicas;		// osr列表
			* Node 描述节点信息
				private final int id;
				private final String idString;
				private final String host;			//主机名
				private final int port;				// 端口
				private final String rack;


		Set<TopicPartition> paused()
		void pause(Collection<TopicPartition> partitions)
		void resume(Collection<TopicPartition> partitions)
			* 暂停/恢复对某些主题,分区的消费

		ConsumerRecords<K, V> poll(final Duration timeout)
			* 从broker拉取消息
			* timeout控制阻塞时间

		long position(TopicPartition partition)
		long position(TopicPartition partition, final Duration timeout)
			* 获取到下一条需要拉取的消息位置
			* timeout控制阻塞时间,因为要获取元数据,可能会阻塞

		void seek(TopicPartition partition, long offset)
		void seekToBeginning(Collection<TopicPartition> partitions)
		void seekToEnd(Collection<TopicPartition> partitions)
			* 移动指定topic的指定partition的消费偏移量
		
		Set<String> subscription()
		void subscribe(Collection<String> topics)
		void subscribe(Collection<String> topics, ConsumerRebalanceListener listener)
		void subscribe(Pattern pattern)
		void subscribe(Pattern pattern, ConsumerRebalanceListener listener)
			* 订阅主题,如果方法被重复的调用,那么以最后一次调用的为准
			* 如果使用正则表达式的方法订阅了主题,就算是主题不存在也可以,在主题被创建后,符合条件的主题会被自动的订阅
			* 负载均衡监听器:ConsumerRebalanceListener 
				 void onPartitionsRevoked(Collection<TopicPartition> partitions);
				 void onPartitionsAssigned(Collection<TopicPartition> partitions);

			* 使用这种方式进行订阅消息具有自动负载均衡的功能
			* 在多个消费者的情况下,可以根据分区分配策略来自动分配各个消费者与分区的关系
			* 在消费组内消费者的增加/减少,分区分配关系会自动的跳转,以及实现故障的自动转移

		
		void assign(Collection<TopicPartition> partitions)
			* 还可以直接订阅指定主题的指定分区
			* TopicPartition 对象用于描述分区和主题
				private int hash = 0;			//hash值
				private final int partition;	// 分区编号
				private final String topic;		// 主题信息
				TopicPartition(String topic, int partition)
			* 这种方式订阅,不具备自动的负载均衡功能

		Set<TopicPartition> assignment()
			* 获取订阅的主题和分配的partition

		void unsubscribe()
			* 取消订阅主题
			* 也可以通过 subscribe 去订阅一个空的主题集合来达到取消订阅的效果
				subscribe(new ArrayList<String>());
		
		void wakeup()
			* 该方法是唯一可以从其他线程里面调用的安全的方法
			* 该方法被调用后,可以退出 poll() 逻辑,并且抛出:WakeupException
			* 并不需要处理该异常,它只是退出 poll() 的一种机制
			* 跳出循环以后一定要显式地执行关闭动作以释放运行过程中占用的各种系统资源
