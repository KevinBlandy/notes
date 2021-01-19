----------------------------
事务和幕等					|
----------------------------
	# 消息中间件的消息传输保障有 3 个层级
		* at most once,至多一次,消息可能会丢失,但绝对不会重复传输

		* at least once,最少一次,消息绝不会丢失,但可能会重复传输(Kafka默认)

		* exactly once,恰好一次,每条消息肯定会被传输一次且仅传输一次
	
----------------------------
幕等						|
----------------------------
	# 生产者在进行重试的时候有可能会重复写入消息
		* 消息发送后遇到了网络问题而造成通信中断,那么生产者就无法判断该消息是否己经提交
		* 生产者进行多次重试来确保消息已经写入Kafka,这个重试的过程中有可能会造成消息的重复写入

	# 使用 Kafka 的幕等性功能之后就可以避免这种情况
	# 开启幕等使用配置项:enable.idempotence
		properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

	# 确保幕等功能正常,还需要以下配置的正确性
		retries
		acks=
		max.in.flight.requests.per.connection

		* 实际上在使用 幕等性功能的时候,完全可以不用配置(也不建议配置)这几个参数
		* 如果强行配置了,可能会因为配置不当导致幕等消息功能异常

	# 原理
		* 每个新的生产者实例在初始化的时候都会被分配一个 PID , 这个 PID 对用户而言是完全透明的 
		* 对于每个 PID,消息发送到的每一个分区都有对应的序列号
		* 这些序列号从 0 开始单调递增,生产者每发送一条消息就会将 <PID 分区> 对应的序列号的值加 l

		* broker对所有的 <PID 分区号> 都维护了一个序列号
		* 接收到消息时候,只有它的序列号值 == (<PID 分区号> +  1),broker才会接受
		
		* 如果消息序列号 < (<PID 分区号> +  1),说明消息被重复的写入,broker会丢弃这条消息
		* 如果消费序列号 > (<PID 分区号> +  1),说明中途有消息丢失未写入到broker,出现了乱序,此时就会抛出非常严重的异常:OutOfOrderSequenceException
	
	# Kafka的幕等只能保证单个生产者会话(session )中单分区的幕等等

----------------------------
幕等						|
----------------------------
	# 幕等性并不能跨多个分区运作,而事务可以弥补这个缺陷,事务可以保证对多个分区写入操作的原子性
	# 开启事务:transactional.id
		properties.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "1");
		
		* 事务要求必须开启幕等:enable.idempotence=true
		* transactionalld值由程序提供
		* transactionalld与 PID一一对应,不同的是 transactionalld 由用户显式设置 ,而PID 是由 Kafka 内 部分配的 
	
	# 如果两个消息生产者都有相同的 transactionalld ,先启动的那个会抛出异常:ProducerFencedExcept
		* 每个生产者通过 transactionalld 获取 PID 的 同时,还会获取一个单调递增的producer epoch
		* 先启动的那个拥有相同 transactionalld 的生产者实例将不再工作

	# 事务相关的方法

		void initTransactions()
			* 初始化事务,前提是必须配置了transactionalld,否则抛出异常
		void beginTransaction()
			* 开启事务
		void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets,String consumerGroupId)
			* 为消费者提供在事务内的位移提交的操作
		void abortTransaction() 
			* 回滚事务
		void commitTransaction()
			* 提交事务
	
	# 一个事务的操作
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		// 开启幕等
		properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		// 设置事务id
		properties.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "1");
		
		try(KafkaProducer<Void, String> kafkaProducer = new KafkaProducer<>(properties)){
			// 初始化事务
			kafkaProducer.initTransactions();
			// 开始事务
			kafkaProducer.beginTransaction();
			try {
				ProducerRecord<Void, String> producerRecord1 = new ProducerRecord<>("topic_1", "Message-1");
				ProducerRecord<Void, String> producerRecord2 = new ProducerRecord<>("topic_1", "Message-2");
				kafkaProducer.send(producerRecord1);
				kafkaProducer.send(producerRecord2);
				
				//TODO 其他业务操作
				
				// 提交事务
				kafkaProducer.commitTransaction();
			}catch (Exception e) {
				// 回滚事务
				kafkaProducer.abortTransaction();
			}
		}
	
	# sendOffsetsToTransaction ???
		public class Transactional {
			public static Properties getConsumerConfig() {
				Properties properties = new Properties();
				properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
				properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
				properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
				properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_1");
				properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
				return properties;
			}

			public static Properties getProducerConfig() {
				Properties properties = new Properties();
				properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
				properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
				properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
				properties.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "1");
				return properties;
			}

			public static void main(String[] args) {
				KafkaConsumer<Void, String> kafkaConsumer = new KafkaConsumer<>(getConsumerConfig());
				kafkaConsumer.subscribe(Collections.singletonList("topic_1"));

				KafkaProducer<Void, String> kafkaProducer = new KafkaProducer<>(getProducerConfig());
				kafkaProducer.initTransactions();
				while (true) {
					ConsumerRecords<Void, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
					if (!consumerRecords.isEmpty()) {
						Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
						kafkaProducer.beginTransaction();
						try {
							for(TopicPartition topicPartition : consumerRecords.partitions()) {
								List<ConsumerRecord<Void,String>> consumerRecordList = consumerRecords.records(topicPartition);
								for(ConsumerRecord<Void,String> consumerRecord : consumerRecordList) {
									// 消费消息
									System.out.println(consumerRecord.value());
									
									// 生产消息
									ProducerRecord<Void, String> producerRecord = new ProducerRecord<Void, String>("topic_1",consumerRecord.value());
									kafkaProducer.send(producerRecord);
								}
								long lastConsumedOffset = consumerRecordList.get(consumerRecordList.size() - 1).offset();
								offsets.put(topicPartition,new OffsetAndMetadata(lastConsumedOffset + 1));
							}
							kafkaProducer.sendOffsetsToTransaction(offsets, "group_1");
							kafkaProducer.commitTransaction();
						}catch (Exception e) {
							kafkaProducer.abortTransaction();
						}
					}
				}
			}
		}
