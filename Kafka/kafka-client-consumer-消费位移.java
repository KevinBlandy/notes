------------------------
位移提交				|
------------------------
	# 每次poll返回的是还没有被消费过的消息
		* 因为这一点,所以需要记录消费者上一次消费的位移,而且必须持久化保存
		* 如果不持久化保存,可能会丢失,而且新加入进来的消费者也不知道从哪里开始消费

		* 旧版本的消费者客户端,消费位移存放在 zookeeper中
		* 新版本的消费者客户端,消费位移存放在Kafka内部的主题 _consumer_offsets 中

		* 把消息持久化的动作称之为提交,消费者在消费完消息之后,需要执行消费位移的提交
	
	# 模拟图
		   0     1     2     3     4     5     6     7
		+-----+-----+-----+-----+-----+-----+-----+-----+
		|  A  |  B  |  C  |  D  |  E  |  F  |  G  |  x  |
		+-----+-----+-----+-----+-----+-----+-----+-----+

		已经消费的消息(消费到D)
		————————————————————————>  ^ 消费偏移量为 4
							
	
	# 获取到消费位移值
		long position(TopicPartition partition)
		long position(TopicPartition partition, final Duration timeout)
			* 获取到下一条需要拉取的消息位置
			* 其实就是自己消费的最后一条记录值 + 1
		
		OffsetAndMetadata committed(TopicPartition partition)
		OffsetAndMetadata committed(TopicPartition partition, final Duration timeout)
			* 获取已经提交过的消费位移
			* 就是自己消费的最后一条记录值
		
		* 这些API都需要先进行 poll()操作,成功的获得了分区后才能执行成功
	
	# Kafka默认的消费位移提交方式为自动提交
		* 可以通过参数:enable.auto.commit 设置,默认值为 true
			properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
		
		* 设置两次位移记录提交的间隔:auto.commit.interval.ms,默认值为: 50000 (ms )
			properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"50000");

		* 默认的方式下,消费者每隔5s就会把拉取到的每个分区中的最大消费位移进行提交
		* 这个操作是在poll()方法的逻辑里面完成的,每次向服务器发起拉取请求之前都会检查是否可以进行位移提交
		* 如果可以,那么就会提交上一次轮询的位移

		* 自动提交的方式非常简单,省心,但是可能会带来重复消费的问题(消费位移未提交时,系统宕了)
		* 可以适当的减少auto.commit.interval.ms值,来避免重复消费的问题,但是不能完全避免,而且还会使消费位移的提交更加的频繁

		* 消息的丢失可能会发生在多线程消费的情况下
			1. A线程从topic拉取消息缓存到本地的队列: BlockingQueue,并且自动提交消费位移
			2. 线程B从本地队列消费消息
			3. 系统宕机,线程B还没消费完毕本地队列中的消息,但是线程A已经提交了消费位移
	

	# 关闭自动提交后,可以使用手动提交消费位移的API
		void commitAsync()
		void commitAsync(final Map<TopicPartition, OffsetAndMetadata> offsets, OffsetCommitCallback callback)
		void commitAsync(OffsetCommitCallback callback)
			* 异步提交
			* OffsetCommitCallback 在提交成功后的回调
				void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception);

		void commitSync()
		void commitSync(Duration timeout)
			* 同步提交
			* 只要没有发生不可恢复的错误,它就会阻塞消费者线程,直到提交成功
			* 对于不可恢复的错误,我们需要捕获并且处理

		void commitSync(final Map<TopicPartition, OffsetAndMetadata> offsets)
		void commitSync(final Map<TopicPartition, OffsetAndMetadata> offsets, final Duration timeout)
			* 提供了更加细粒度的消费位移
			* 可以精准的设置指定主题的指定分区,的消费位移值
		
		* 无參提交,会根据poll()拉取的最新位移进行提交
		* 无參提交,它提交消费位移的频率和拉取批次消息,处理批次消息的频率是一样的

		* offsets 提供了更加细粒度的消费位移控制
			* TopicPartition 表示topic和分区信息
				private int hash = 0;
				private final int partition;
				private final String topic;

			* OffsetAndMetadata 表示位移信息
				private final long offset;
				private final String metadata;
				private final Integer leaderEpoch;

		
	# 异步提交也可能导致重复消费的问题
		* 如果异步提交失败,一般会采取重试的方法
		* 如果在多线程环境下,也可能会导致重复消费的问题
			1. A线程异步提交失败,进行重试
			2. B线程进行异步提交,成功
			3. A线程重试成功,覆盖了B线程的消费位移提交,导致重复消费
		
		* 可以设置一个递增的序列号(AtomicLong)来维护异步提交的顺序
		* 每次位移提交之后就递增序列号,并且记录
		* 如果位移提交失败,需要重试,就检查当前序列号是否与上一次提交后记录的值一样
		* 如果当前值大于上一次提交后的值了,说明有更大的位置消费提交了,当前不需要重试
		* 如果相等,说明还可以进行重试提交
	
	# 位移提交失败的情况一般极少发生
		* 不进行重试也没关系,后面的提交,也会有成功的
		* 如果重试,会增加编码难度,不重试又会增加重复消费的可能

		* 如果消费者异常退出,那么重复消费的问题就不可避免
		* 如果消费者正常退出或发生再均衡的情况,那么可以在退出或再均衡执行之前使用同步提交的方式做最后的把关
			try{
				while(true) {
					// 拉取消息消费后执行异步的提交
					kafkaConsumer.commitAsync();
				}
			}finally {
				try {
					// 稳妥的再同步提交一次
					kafkaConsumer.commitSync();
				}finally {
					kafkaConsumer.close();
				}
			}
	

	# 每次消费一条记录就提交一次消费位移(十分消耗性能)
		// 消息的主题
		String topic = consumerRecord.topic();
		// 消息的分区
		int partition = consumerRecord.partition();
		kafkaConsumer.commitSync(Collections.singletonMap(new TopicPartition(topic, partition), new OffsetAndMetadata(consumerRecord.offset() + 1)));
	
	# 按照分区粒度同步提交消费位移(消费完毕一个分区的消息后才提交一次)
		ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
		// 遍历消息的所有主题分区
		for(TopicPartition topicPartition : consumerRecords.partitions()) {
			// 获取分区下的消息集合
			List<ConsumerRecord<String,String>> topicPartitionConsumerRecords = consumerRecords.records(topicPartition);
			// 遍历消费消息
			for(ConsumerRecord<String,String> consumerRecord : topicPartitionConsumerRecords) {
				// TODO 消费消息
			}
			// 获取到最后一条消息的消费位移
			long offset =  topicPartitionConsumerRecords.get(topicPartitionConsumerRecords.size() - 1).offset();
			// 同步的提交当前分区的消费位移
			kafkaConsumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(offset + 1)));
		}
	

------------------------
指定位移消费			|
------------------------
	# 有了消费位移的持久化,才使消费者在关闭,崩溃或者在遇到再均衡的时候,可以让接替的消费者能够根据存储的消费位移继续进行消费

	# 每当消费者查找不到所记录的消费位移时,就会根据消费者客户端参数 auto.offset.reset 的配置来决定从何处开始进行消费
		auto.offset.reset
			* 当消费者找不到消费偏移量记录的时候,从哪里开始进行消费
			* 枚举值:
				earliest	重置为最早的偏移量,从头开始消费
				latest		将偏移重置为最新偏移量,通俗的说就是不消费以前的消息了,从下条消息开始消费(默认)
				none		如果没有找到偏移量记录,抛出异常
		
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "none");
	
	# 通过程序来控制消费偏移量
		void seek(TopicPartition partition, long offset)
		void seekToBeginning(Collection<TopicPartition> partitions)
		void seekToEnd(Collection<TopicPartition> partitions)

		* 在执行 seek 系列的方法之前需要先执行一次 poll()方法
		* 确保分配到分区之后才可以重置消费位置
			try(KafkaConsumer<Void, String> kafkaConsumer = new KafkaConsumer<>(properties)){
				// 监听主题
				kafkaConsumer.subscribe(Arrays.asList("test"));
				
				// 必须确定消费者已经分配了分区信息
				while(kafkaConsumer.assignment().size() == 0) {
					// 执行poll() 获取到分区信息
					kafkaConsumer.poll(Duration.ofMillis(1000L));	
				}
				
				// 遍历分区,并且设置消费偏移量
				for (TopicPartition topicPartition : kafkaConsumer.assignment()) {
					kafkaConsumer.seek(topicPartition, 0);
				}
				
				// 开始消费
				while(true) {
					ConsumerRecords<Void, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(Long.MAX_VALUE));
					for(ConsumerRecord<Void, String> consumerRecord : consumerRecords ) {
						System.out.println(consumerRecord);
					}
				}
			}
		
		* 如果设置偏移量越界了(设置了一不存在的值),那么会根据 auto.offset.reset 策略来重置消费偏移量

	# 获取偏移量信息
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
		
		long position(TopicPartition partition)
		long position(TopicPartition partition, final Duration timeout)
			* 获取自己在指定分区的消费偏移量
			* 其实就是自己消费的最后一条记录值 + 1
	
