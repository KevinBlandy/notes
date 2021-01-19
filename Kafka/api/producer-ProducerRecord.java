------------------------------
ProducerRecord				  |
------------------------------
	# 生产者生产的消息对象
	# 构造函数
		ProducerRecord(String topic, Integer partition, Long timestamp, K key, V value)
		ProducerRecord(String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers)
		ProducerRecord(String topic, Integer partition, K key, V value)
		ProducerRecord(String topic, Integer partition, K key, V value, Iterable<Header> headers)
		ProducerRecord(String topic, K key, V value)
		ProducerRecord(String topic, V value)
	

	# 实例属性
		private final String topic;			// 主题
		private final Integer partition;	// 分区
		private final Headers headers;		// 消息头
		private final K key;				// k
		private final V value;				// v
		private final Long timestamp;		// 时间戳
