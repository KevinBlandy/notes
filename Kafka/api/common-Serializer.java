-------------------
Serializer<T>	   |
-------------------
	# 消息的序列化接口,把对象序列化为字节数组(编码器)
	# 抽象方法
		void configure(Map<String, ?> configs, boolean isKey);
			* 配置当前类,在KafkaProducer实例创建的时候调用

		byte[] serialize(String topic, T data);
			*  执行序列化操作

		default byte[] serialize(String topic, Headers headers, T data) {
			return serialize(topic, data);
		}

		@Override
		void close();
			* 执行关闭,一般来说都是空实现
			* 如果要自己实现的话,必须确保该方法的幕等性
			* 因为close()可能会被KafkaProducer调用多次
	
	# 提供了N多序列化实现类
		ByteArraySerializer
			* byte[]
		
		ByteBufferSerializer
			* ByteBuffer
		
		BytesSerializer
			* Bytes
		
		DoubleSerializer
		FloatSerializer
		IntegerSerializer
		LongSerializer
		ShortSerializer
			* 基本数据类型(包装类)的编码器

		StringSerializer
			* String,默认编码 UTF-8

		UUIDSerializer
			* uuid
			* 其实本质上也是把 UUID 对象 toStirng()后 getBytes(),默认使用UTF-8编码

