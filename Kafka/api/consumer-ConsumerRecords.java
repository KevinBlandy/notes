--------------------------------
ConsumerRecords<K,V>				|
--------------------------------
	# 消费者消费的消息对象的集合
	# 它实现了迭代器:Iterable<ConsumerRecord<K, V>,表示多个ConsumerRecord
	# 静态属性
		ConsumerRecords<Object, Object> EMPTY = new ConsumerRecords<>(Collections.EMPTY_MAP);

	# 静态方法
		<K, V> ConsumerRecords<K, V> empty()

	# 实例方法
		int count();
			* 消息总数量

		boolean isEmpty();
			* 是否为空

		Iterator<ConsumerRecord<K, V>> iterator();
			* 返回包含了所有消息的迭代器

		Set<TopicPartition> partitions();
			* 返回当前主题消息所在的所有主题以及分区

		Iterable<ConsumerRecord<K, V>> records(String topic);
			* 返回包含了指定主题消息的迭代器
			* 当前消费者可能订阅了多个主题

		List<ConsumerRecord<K, V>> records(TopicPartition partition);
			* 返回包含了指定主题,指定分区消息的集合
			* 当前消费者可能订阅了多个主题,或者多个分区

