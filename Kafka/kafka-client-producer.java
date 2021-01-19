------------------------
producer				|
------------------------
	# Maven
		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-clients</artifactId>
			<version>2.1.1</version>
		</dependency>
	
	# 消息生产者的逻辑
		1. 配置客户端参数,以及根据参数创建相应的消息生产者实例
		2. 构建消息
		4. 发送消息
		5. 关闭客户端(生产者实例)

	# 基本的发送演示
		Properties properties = new Properties();
		properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("client.id", "producer.client.id.demo");
		
		try(KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)){
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("test", "你好啊");
			kafkaProducer.send(producerRecord);
		}
	
	# 生产者客户端是线程安全的

	# 异常的可重试机制
		* KafkaProducer 中 一般会发生两种类型的异常 : 可重试的异常和不可重试的异常 
		* 常见的可重试异常
			NetworkException(网络异常,这个有可能是由于网络瞬时故障而导致的异常,可以通过重试解决)
			LeaderNotAvailableException(分区的 leader 副本不可用,这个异常通常发生在 leader 副本下线而新的 leader 副本选举完成之前,重试之后可以重新恢复)
			UnknownTopicOrPartitionException
			NotEnoughReplicasException
			NotCoordinatorException

		* 常见的不可重试异常
			RecordTooLargeException(发送的消息太大,不会执行重试,直接抛出)

		* 如果配置了 retries 参数,那么只要在规定的重试次数内自行恢复了,就不会抛出异常(retries 参数的默认值为 0)
			props.put(ProducerConfig.RETRIES_CONFIG, "10");
		
		* 对于可重试异常发生后,可以设置两次重复调用的时间间隔,retry.backoff.ms(默认100毫秒)
			props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "100");

		* 在配置 retries 和 retry.backoff.ms 之前,最好先估算一下可能的异常恢复时间
		* 这样可以设定总的重试时间大于这个异常恢复时间,以此来避免生产者过早地放弃重试 
	
	# 消息的序列化(编码器)
		* 需要用到序列化,把消息(key和value)序列化为byte[]
		* 最顶层的序列化接口:Serializer<T>
			byte[] serialize(String topic, T data);
		* 设置消息编码器(必须设置的属性)
			properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
			properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

	
	# 分区器
		* 消息send()到broker时可能会经过拦截器,序列化器(编码器),分区器之后才会被发送到broker
		* 如果消息对象(ProducerRecord)指定了partition属性值,那么就不需要分区器了,因为已经指定好了
		* 如果未指定partition属性值,那么就要依赖于分区器,根据key字段来计算出partition值
		* 分区器接口:Partitioner
			void configure(Map<String, ?> configs)
			int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster);
			void close();
		
		* Kafka提供默认的分区器实现
			DefaultPartitioner
				* 如果key不为null,那么会对key进行hash(采用MurmurHash2算法,具备高运算性能和低碰撞率)计算
				* 根据最终的hash值来计算分区号
				* 也就是说相同的key会被发送到同一个分区(如果扩展了partition的数量那么就不能保证了)
				* 计算得到的分区号会是所有分区中的任意一个,可能会选择到不可用的分区
			
				* 如果Key为null,那么消息会被以轮询的方式发送到每个可用的分区
				* 计算得到的分区号仅为可用分区中的任意一个,不会选择到不可用的分区
				
		* 使用自定义的分区器
			properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
			
	
	# 拦截器
		* 拦截器可以在消息被发送之前做一些其他的工作,例如:过滤,数据的修改
		* 也可以用来在发送回调逻辑前做一些定制化的需求,例如:统计
		* 拦截器接口:ProducerInterceptor<K,V>
			void configure(Map<String, ?> configs);
			ProducerRecord<K, V> onSend(ProducerRecord<K, V> record);
			void onAcknowledgement(RecordMetadata metadata, Exception exception);
			void close();
		
		* Kafka会在消息编码,分区计算之前调用拦截器的onSend方法

		* 一般来说最好不要修改消息的:topic,partition,key等信息
		* 除非确保有准确的判断,否则可能会出现于预想结果出现偏差的可能
		* 不仅可能会影响分区的计算,还可能印象broker端日志的压缩功能
		
		* Kafka会在消息被应答(send api返回)之前或者消息发送失败时调用拦截器的onAcknowledgement方法
		* 一样的,需要判断 exception 是否为null,从而确定消息是否发送成功
		* 它会优先于 Callback 执行
		* 这个方法运行在Producer的I/O线程中,所以越简单越好,否则会影响消息的发送速度

		* close()方法主要用在关闭拦截器时执行一些资源的清理工作

		* 拦截器中几个方法,在执行中抛出的异常,都会被捕获并记录到日志
		* 但是不会向上传递(不会抛出)
		
		* 拦截器可以配置多,形成一个责任链(责任链不多解释)
		* 如果拦截链中某一个拦截器抛出了异常,它会被跳过
		* 下一个拦截器会接着上一个执行成功的拦截器开始执行(略过执行异常的拦截器)
		* 多个拦截器使用逗号分隔

		* 配置拦截器
			properties.setProperty(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "io.javaweb.kafka.interceptor.SimpleInterceptor");
		
		* 配置拦截器链
			properties.setProperty(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "SimpleInterceptor1,SimpleInterceptor1");
	
	# 执行过程
		* 生产者客户端由两个线程组成:主线程,发送线程
		* 主线程创建消息 -> 拦截器 —> 编码器 -> 分区计算器 -> 消息累加器(RecordAccumulator)
		* 发送线程负责从消息累加器中获取消息,发送到broker

	# 消息的累加器(缓存)
		* 主线程调用了 send() api并不会立即执行消息的发送,会先把消息添加到累加器 (RecordAccumulator)
		* RecordAccumulator 的作用就是缓存消息
		* 以便sender线程批量的进行发送,提高效率(减少网络传输)
		* 默认缓存的大小为:33554432kb = 32MB
		* 缓存的大小可以设置
			properties.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, String.valueOf(1024 * 1024 * 50));
		
		* 如果生产者的消息发送速度超过了发送消息到服务器的速度
		* 从而占满了缓冲区,在这种情况下,send() api 要么阻塞直到缓冲区有新的空间,要么抛出异常
		* 可以通过配置来设置send()操作,最长阻塞时间(ms),超过时间就会抛出异常
			properties.setProperty(ProducerConfig.MAX_BLOCK_MS_CONFIG, String.valueOf(60000));
		
		* 主线程发送的消息都会被追击到 RecordAccumulator 的某个双端队列(Deque)中
		* RecordAccumulator 为每个分区都维护了一个双端队列,队列的内容就是:ProducerBatch(Deque<ProducerBatch>)
			ConcurrentMap<TopicPartition, Deque<ProducerBatch>> batches;
		* 一个ProducerBatch起码包含了一个消息记录(ProducerRecord)
		* 消息写入缓存的时候,追加到双端队列的尾部,sender线程读取消息的时候,从双端队列的头部读取

		* 在 RecordAccumulator 的内部还有一个 BufferPool
		* 它主要用来实现 ByteBuffer 的复用,以实现缓存的高效利用 
		* 不过 BufferPool 只针对特定大小的 ByteBuffer 进行管理,而其他大小的 ByteBuffer 不会缓存进 BufferPool 中
		* 这个特定的大小由 batch.size 参数来指定,默认值为 16384B,即 16KB
			properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, String.valueOf(16384));
		
		* 当一条消息send()到RecordAccumulator的时候,会先找到其对应的分区队列(Deque),如果不存在,则先创建
		* 再从队列尾部获取一个ProducerBatch,如果不存在则新建
		* 判断ProducerBatch是否有足够的空间写入当前的消息,如果可以则直接写入
		* 如果不可以,则创建一个新的ProducerBatch,在新建 ProducerBatch 时评估这条消息的大小是否超过 batch.size 参数的大小
		* 如果不超过,那么就以 batch.size 参数的大小来创建 ProducerBatch,这样在使用完这段内存区域之后,可以通过 BufferPool 的管理来进行复用
		* 如果超过,那么就以评估的大小来创建 ProducerBatch,这段内存区域不会被复用
	
	# 请求响应的缓存
		* 消息从RecordAccumulator发送到broker的请求,会被缓存到 InFlightRequests 中,直到响应
		* 它的缓存格式为 -> broker节点id:Deque<Request>
			Map<String, Deque<NetworkClient.InFlightRequest>> requests = new HashMap<>(); 
		
		* 可以通过配置限制每个连接(也就是客户端与 Node 之间的连接)最多缓存的请求数
		* max.in.flight.requests.per.connection: 默认值为5,也就是说,最多缓存5个未响应的请求,一旦超过该值,就不能往这个连接发送更多的请求了
		* 除非缓存中的请求,收到了响应
			properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, String.valueOf(5));

		* 可以通过 Deque<ProducerBatch>.size() 和 max.in.flight.requests.per.connection 进行比较
		* 从而判读是否堆积了N多已经发送但是未响应的消息,如果真的存在很多,那么该broker能网络负载比较大,或者连接有问题

	
	# 元数据的更新
		* 元数据,其实就是除了我们业务数据以外的一些其他数据
		* 对于我们而言是透明的,但是又是不可缺少的,例如:kafka集群中的主题数量,每个主题的分区数量,分区的副本分配等等信息
		* 当客户端中没有需要使用的元数据信息时,比如没有指定的主题信息
		* 或者超过 metadata.max.age.ms 时间没有更新元数据都会引起元数据的更新操作,metadata.max.age.ms 的默认值为 300000,即 5 分钟
			properties.setProperty(ProducerConfig.METADATA_MAX_AGE_CONFIG, String.valueOf(300000));
		
		* 更新的操作对于客户端来说是透明的

		* 当需要更新元数据的时候,会先挑选出:leastLoadedNode
		* leastLoadedNode其实就是所有Node中负载最小的Node(判断InFlightRequest中的未响应的请求数量值,数量越少,节点的负载越小)
		* 然后向这个 Node 发送 MetadataRequest 请求来获取具体的元数据信息,这个更新操作是由 Sender 线程发起 的
		* 在创建完 MetadataRequest 之后 同样会存入 InFlightRequest 之后的步骤就和发送消息时的类似
		* 元数据虽然由 Sender 线程负责更新,但是主线程也需要读取这些信息,这里的数据同步通过 synchronized 和 final 关键字来保障
	
	# 消息的有序性问题
		* Kafka只能保证一个分区中的消息是有序性的

		* 如果将 acks 参数配置为非零值,并且 max.in.flight.requests.per.connection 参数配置为大于 l 的值,那么就会出现错序的现象
		* 如果第一批次消息写入失败,而第二批次消息写入成功,那么生产者会重试发送第一批次的消息
		* 此时如果第一批次的消息写入成功,那么这两个批次的消息就出现了错序
		
		* 一般而言,在需要保证消息顺序的场合建议把参数 max.in.flight.requests.per.connection 配置为 1,而不是把 acks 配置为 0
		* 这样会影响整体的吞吐