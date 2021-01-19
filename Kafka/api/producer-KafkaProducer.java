------------------------
KafkaProducer<K, V>		|
------------------------
	# 消息生产者
		* 它是一个线程安全的对象
	
	# 构造函数
		KafkaProducer(final Map<String, Object> configs)
		KafkaProducer(Map<String, Object> configs, Serializer<K> keySerializer, Serializer<V> valueSerializer)
		KafkaProducer(Properties properties)
		KafkaProducer(Properties properties, Serializer<K> keySerializer, Serializer<V> valueSerializer)
		
	# 实例方法
		void close()
		void close(long timeout, TimeUnit timeUnit)
			* 关闭,释放资源
			* 如果不指定超时时间,那么会阻塞,直到所有的消息都发送完毕
			* 如果设置了超时时间,一旦超时就会强行执行退出

		void flush()
		
		Map<MetricName, ? extends Metric> metrics()
		List<PartitionInfo> partitionsFor(String topic)
		Future<RecordMetadata> send(ProducerRecord<K, V> record)
			* send 方法是异步的,可以通过 Future 来获取到执行的结果(RecordMetadata),或者阻塞线程直到成功

		Future<RecordMetadata> send(ProducerRecord<K, V> record, Callback callback)
			* 同上,可以添加一个回调 Callback 接口实现:void onCompletion(RecordMetadata metadata, Exception exception);
			* 如果异常则metadata为null,exception不为null,反之metadata不为null,exception为null
			* 对于同一个分区而言,回调函数(Callback)的调用可以保证分区有序,先执行调用的 Callback 肯定会先执行
		
		void initTransactions()
		void abortTransaction() 
		void beginTransaction()
		void commitTransaction()
		void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets,String consumerGroupId)

------------------------
RecordMetadata			|
------------------------
	# 记录的元数据
	# 静态属性
		int UNKNOWN_PARTITION = -1;

	# 构造方法
		RecordMetadata(TopicPartition topicPartition, long baseOffset, long relativeOffset, long timestamp,Long checksum, int serializedKeySize, int serializedValueSize)
	
	# 实例属性
		private final long offset;
		private final long timestamp;
		private final int serializedKeySize;
		private final int serializedValueSize;
		private final TopicPartition topicPartition;

	# 实例方法
		boolean hasOffset()
		boolean hasTimestamp()
		String topic()
