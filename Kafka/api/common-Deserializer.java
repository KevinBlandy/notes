--------------------
Deserializer<T>		|
--------------------
	# 消费者的解码接口
	# 抽象方法
		void configure(Map<String, ?> configs, boolean isKey);
			* 配置,在创建消费者实例的时候调用

		T deserialize(String topic, byte[] data);
			* 解码消息

		default T deserialize(String topic, Headers headers, byte[] data) {
			return deserialize(topic, data);
		}	
			* 可以获取到消息头的解码方法

		@Override
		void close();
	
	# 提供的实现类
		ByteArrayDeserializer
		ByteBufferDeserializer
		BytesDeserializer
		DoubleDeserializer
		FloatDeserializer
		IntegerDeserializer
		LongDeserializer
		ShortDeserializer
		StringDeserializer
		UUIDDeserializer
