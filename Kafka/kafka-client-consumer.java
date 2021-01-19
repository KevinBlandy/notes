------------------------
consumer				|
------------------------
	# 每个消费者都有一个消费组,一个消费组有N多个消费者

	# 一个消费组可以消费多个分区的消息

	# 每一个分区的消息,只能被一个消费组中的一个消费者消费

	# 如果消费组中的消费者数量大于了分区的数量,这样的会导致多出去的消费者一直处于空闲状态
	
	# 通俗的理解
		* 一个topic可以被多个消费组消费,并且这些消费组消费的都是相同的数据
		* topic对于消费组来说,是广播,一条消息会发送到多个消费者组

		* 消费组消费的是topic
		* 消费topic的时候,topic里面的所有分区,都会均匀的分布到消费组里面的消费者身上
		* 一条来自于分区的消息,只能被一个消费者消费
		* 消息对于消费组来说,是单播,一条消息只能被消费组中的一个消费者消费
	
	# Maven
		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-clients</artifactId>
			<version>2.1.1</version>
		</dependency>
	
	# 消费逻辑
		1. 配置消费者客户端的参数
		2. 根据参数创建消费者实例对象
		3. 订阅主题
		4. 拉取消息进行消费
		5. 提交消费位移
		6. 关闭消费者实例

	# 基本的消费
		Properties properties = new Properties();
		properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("group.id", "group.demo");

		try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties)) {
			kafkaConsumer.subscribe(Arrays.asList("test"));
			while (true) {
				ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
				for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
					String value = consumerRecord.value();
					System.out.println(value);
				}
			}
		}
	
	# 消费者客户端是非线程安全的
	
	# 消费者必须属于一个消费者
		* 通过 group.id 属性设置
			properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group.demo");
		* 如果不设置,默认为空字符串
		* 一般而言这个值的设置要有一定的业务意义
	
	# 主题订阅
		* 相关api
			Set<String> subscription()
			void subscribe(Collection<String> topics)
			void subscribe(Collection<String> topics, ConsumerRebalanceListener listener)
			void subscribe(Pattern pattern)
			void subscribe(Pattern pattern, ConsumerRebalanceListener listener)
		
		* 订阅主题,如果方法被重复的调用,那么以最后一次调用的为准
		* 如果使用正则表达式的方法(Pattern)订阅了主题,就算是主题不存在也可以,在主题被创建后,符合条件的主题会被自动的订阅
		* 负载均衡监听器:ConsumerRebalanceListener 
			 void onPartitionsRevoked(Collection<TopicPartition> partitions);
			 void onPartitionsAssigned(Collection<TopicPartition> partitions);

		* 使用这种方式进行订阅消息具有自动负载均衡的功能
		* 在多个消费者的情况下,可以根据分区分配策略来自动分配各个消费者与分区的关系
		* 在消费组内消费者的增加/减少,分区分配关系会自动的跳转,以及实现故障的自动转移

		* 监听了主题后,需要调用 poll() 后才能获取到分区的分配信息
		* 可以通过:Set<TopicPartition> assignment() 获取到订阅的主题,已经分配的分区信息
	
	# 分区订阅
		* 相关api
			void assign(Collection<TopicPartition> partitions)

		* TopicPartition 对象用于描述分区和主题
			private int hash = 0;			//hash值
			private final int partition;	// 分区编号
			private final String topic;		// 主题信息
			TopicPartition(String topic, int partition)

		* 这种方式订阅,不具备自动的负载均衡功能
	
	# 主题订阅模式的互斥性
		* 集合的订阅方式:AUTO_TOPICS
			subscribe(Collection<String> topics)
		* 正则表达式的订阅方式:AUTO_PATTERN
			subscribe(Pattern pattern)
		* 指定分区的订阅方法:AUTO_ASSIGNED
			assign(Collection<TopicPartition> partitions)

		* 这三种订阅方法不能同时存在,只能选择一种,不然会抛出异常
	
	# 消息的解码器
		* 通过 key.deserializer/value.deserializer 设置key/value的解码器
			properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"");
			properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"");
		
		* 解码器接口:Deserializer
			void configure(Map<String, ?> configs, boolean isKey);
				* 配置
			T deserialize(String topic, byte[] data);
				* 解码消息
			default T deserialize(String topic, Headers headers, byte[] data) {
				return deserialize(topic, data);
			}	
				* 可以获取到消息头的解码方法

			@Override
			void close();

		* 如果有多个,使用逗号分隔
	
	# 消息消费
		* 消息消费模式为主动从topic拉取消息,这是一个不断轮询的过程
		* 相关api
			ConsumerRecords<K, V> poll(final Duration timeout)
		* 该值的设置取决于程序对响应速度的要求
		* 如果设置为0,poll会立即返回而不管是否拉取到了数据
		* 如果线程的工作仅仅是为了拉取数据,那么该值可以设置为 Long.MAX_VALUE

	# 控制消费
		* 有时候需要主动的暂停消费某些分区,在一定的时机又恢复对这些分区的消费
		* 可以达到控制消费速度的行为
			Set<TopicPartition> paused()
				* 返回被暂停的分区集合
			void pause(Collection<TopicPartition> partitions)
				* 暂停
			void resume(Collection<TopicPartition> partitions)
				* 恢复
	# 关闭消费者
		* 一般使用一个 while 循环来包裹住 poll()方法及相应的消费逻
		* 退出循环,有一种方式是调用 KafkaConsumer 的 wakeup()方法 
			void wakeup()

			* 该方法是唯一可以从其他线程里面调用的安全的方法
			* 该方法被调用后,可以退出 poll() 逻辑,并且抛出:WakeupException
			* 并不需要处理该异常,它只是退出 poll() 的一种机制
		
		* 跳出循环以后一定要显式地执行关闭动作以释放运行过程中占用的各种系统资源
		* 客户端提供了一个 close() api来对占用的系统资源进行释放
		* 释放的过程会比价缓慢,可能涉及到跟broker通信,提交消费位移等信息
			void close()
			void close(Duration timeout)

			* timeout 设置超时时间,默认是 30s

		* 一个完整的优雅关闭
			boolean run = true; // 确定服务是否还要继续运行
			KafkaConsumer<Void, String> kafkaConsumer = new KafkaConsumer<>(properties);
			kafkaConsumer.subscribe(Arrays.asList("test"));
			try{
				while(run) {
					ConsumerRecords<Void, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000L));
					for(ConsumerRecord<Void, String> consumerRecord : consumerRecords) {
						// TODO 消费消息
						System.out.println(consumerRecord);
					}
				}
			}catch (WakeupException e) {
				e.printStackTrace(); // 被主动唤醒,忽略该异常
			}catch (Exception e) {	// 处理该异常
				e.printStackTrace();
			}finally {
				if(kafkaConsumer != null) {
					kafkaConsumer.close(); // 关闭资源
				}
			}
		
	# 负载均衡
		* 当消费组中有消费者加入/宕机,或者是topic有partition新增/删除
		* 都可能触发消费组中,消费者的负载均衡

		* 在执行负载均衡计算的时候,消费组会变得不可用
		* 可能会发生重复消费的情况
			1. 消费者A消费了partition-1,还没来得及提交消费偏移度
			2. 发生了负载均衡计算,partition-1 被分配给了消费者B
			3. 因为消费者A没有提交消费偏移,所以消费者B会重复消费A已经消费过的记录
		
		* 可以在订阅topic的时候,监听负载均衡的发生
			void subscribe(Collection<String> topics, ConsumerRebalanceListener listener)
			void subscribe(Pattern pattern, ConsumerRebalanceListener listener)
		
		* ConsumerRebalanceListener 接口具有两个抽象方法
			void onPartitionsRevoked(Collection<TopicPartition> partitions);
				* 该方法会在负载均衡被计算之前,消费停止poll()消息之后执行
				* 参数表示原始的分区
				* 可以通过它来处理消费位移的提交,尽可能的避免重复消费的问题

			void onPartitionsAssigned(Collection<TopicPartition> partitions);
				* 该方法会在负载均衡计算之后(重新分配分区),消费者开始读取消息之前执行
				* 参数表示重新分配的分区
		
		* demo
			try (KafkaConsumer<Void, String> kafkaConsumer = new KafkaConsumer<>(properties)) {
				// 记录实时的消费偏移度
				Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
				// 监听主题
				kafkaConsumer.subscribe(Arrays.asList("demo3"), new ConsumerRebalanceListener() {
					@Override
					public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
						// 在重新计计算分区之前,同步提交消费偏移度,尽可能的保证不会出现重复消费的情况
						kafkaConsumer.commitSync(offsets);
					}
					@Override
					public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
						// 经过计计算，分配到的分区
					}
				});
				// 开始消费
				while (true) {
					ConsumerRecords<Void, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(Long.MAX_VALUE));
					
					for (ConsumerRecord<Void, String> consumerRecord : consumerRecords) {
						
						// 消费每一条消息后,实时的记录下该分区的消费偏移度
						String topic = consumerRecord.topic();		// 当前消息的topic
						int partition = consumerRecord.partition();	// 当前消息的partition
						long offset = consumerRecord.offset();		// 当前消息的偏移度
						offsets.put(new TopicPartition(topic, partition), new OffsetAndMetadata(offset + 1));
					}

					// 每次消费完毕数据，异步的提交消费偏移度
					kafkaConsumer.commitAsync(offsets, null);
				}
			}
	
	# 消费者拦截器
		* 主要作用是在读取到消息,以及提交消费位移时进行一些定制化的操作
		* 实现接口:ConsumerInterceptor<K, V>
			ConsumerRecords<K, V> onConsume(ConsumerRecords<K, V> records);
				* 在消费消息(poll() 方法返回之前)前调用
				* 如果它发生异常,会被捕获记录到日志,但是不会向上传递,会跳过当前这个发生了异常的拦截器,把上一次正常的消息提交给下个拦截去处理(如果存在)
				* 参数就是新的消息

			void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets);
				* 在提交消费位移之后调用
				* 参数就是要提交的消费位移信息

				* 可以使用该方法来记录,跟踪所提交的位移信息
				* 比如,消费者调用了:commitAsync() 方法,我们就没法直接获取到提交的消费偏移信息
				* 但是通过这种拦截器的方式,就可以获取到

			void close();
				* 一般没啥用,空实现

		* 配置拦截器使用属性:interceptor.classes
			properties.setProperty(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, LogConsumerInterceptor.class.getName());
			
	# 消费者对于并发操作的限制
		* 消费者实例对象不是线程安全的,这个跟消息生产者不一样
		* 如果有其他线程操作消费者对象,会抛出异常:ConcurrentModificationException
		* 消费者实例内部提供了一个方法(private)

			private static final long NO_CURRENT_THREAD = -1L;
			private final AtomicLong currentThread = new AtomicLong(NO_CURRENT_THREAD);
			private final AtomicInteger refcount = new AtomicInteger(0);

			private void acquire() {
				long threadId = Thread.currentThread().getId();
				if (threadId != currentThread.get() && !currentThread.compareAndSet(NO_CURRENT_THREAD, threadId))
					throw new ConcurrentModificationException("KafkaConsumer is not safe for multi-threaded access");
				refcount.incrementAndGet();
			}
			
			private void release() {
				if (refcount.decrementAndGet() == 0)
					currentThread.set(NO_CURRENT_THREAD);
			}
		
		* 在每次执行动作之前,都会调用这个方法(acquire)
		* 它是一个轻量级的锁,用线程操作计数的方法来检测是否发生了并发操作,以此保证只有一个线程在使用客户端

		* release() 方法的存在是为了释放锁
	
	# 使用多线程消费
		* 使用多线程的目的,是为了提高消费的速度
		* 多线程的实现有很多种

		* 线程封闭,也就是一个线程实例化一个消费者客户端
		* 所有的消费者线程,都属于同一个消费组,一个分区创建一个线程(分区的数量可以提前知道)
		* 优点就是,每个线程可以按照顺序去消费各个分区里面的数据
		* 缺点就是,同一台机器要开启多个tcp连接,系统资源消耗大
		
		* 线程池的模式
		* 一般而言,消费的瓶颈在于消费,而不是拉取(poll())
		* 所以可以使用线程池来消费 poll() 拉取下来的消息
		* 缺点就是对于顺序消息的处理会比较的麻烦

		* 这个名堂还是有点儿多,单独列了个笔记

			
			