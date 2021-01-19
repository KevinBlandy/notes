-----------------------------
ConsumerConfig				 |
-----------------------------
	# 消息消费者配置
	# 配置项
		bootstrap.servers
			* kafka集群的节点
			* ip:port
			* 连接多个使用逗号分隔

		client.dns.lookup
		group.id
			* 设置消费组,默认值为空字符串

		session.timeout.ms
			* 组管理协议中用来检测消费者是否失效的时间
			* 默认:1000

		heartbeat.interval.ms
			* 使用Kafka分组管理功能的时候
			* 消费者与协调器之间的心跳间隔时间,默认值为:3000ms
			* 通过心跳感知新的消费者加入/离开组,从而重新进行分区的分配(负载均衡)

			* 该值必须比: session.timeout.ms 小(通常不高于它的 1/3)
		
		partition.assignment.strategy
			* 消费者与topic之间的分区分配策略实现类
			* 接口:PartitionAssignor
			* 默认:org.apache.kafka.clients.consumer.RangeAssignor

		metadata.max.age.ms
			* 更新元数据的间隔,默认为:300000ms,5分钟
			* 如果超过这个时间元数据没有被更新,那么就会主动的进行更新

		enable.auto.commit
			* 是否开启自动提交消费位移,默认值为:true

		auto.commit.interval.ms
			* * 设置两次位移记录提交的间隔,默认: 50000(ms) 也就是5秒

		client.id
			* 当前消费者的id
			* 如果不设置,会自动的生成一个非空字符串(consumer-[编号])
				consumer-1....consumer-x

		max.partition.fetch.bytes
			* 配置从每个分区返回给消费者的最大数据量
			* 默认为:1MB,该参数与:fetch.max.bytes 相似
			* 不过该参数限制的是一次拉取中整体消息的大小

			* 同样的,它也不是一个强硬的限制
			* 如果这个参数比消息大小要小,消息还是可以被消费的

		send.buffer.bytes
			* 用来设置 Socket 发送消息缓冲区(SO_SNDBUF)的大小,默认值为 131072B,也就是128KB
			* 如果设置为 -l,则使用操作系统的默认值

		receive.buffer.bytes
			* 用来设置 Socket 接收消息缓冲区(SO_RECBUF )的大小
			* 默认值为 32768B,也就是32 kb
			* 如果设置为 -l,则使用操作系统的默认值
			* 如果 Producer 与 Kafka 处于不同的机房 ,则可以适地调大这个参数值 
		
		fetch.min.bytes
			* 消费者一次poll()能从一个分区可以拉取的最小数据值,默认为 1Byte
			* 如果消息不足该值,那么就会阻塞,直到大于等于该值,才会返回
			* 适当的调大这个参数,可以带来吞吐量,但是也会造成额外的延迟
			* 对于延迟敏感的应用就不适合

		fetch.max.bytes
			* 消费者一次能从一个分区中拉取的最大数据值,默认为 50MB

			* 该参数设置的值,不是绝对的最大值
			* 如果在第一个非空的分区中拉取的第一条消息大于该值,那么消息仍然会返回
			* 以确保消费者继续工作
			
			* 它不是一个强硬的限制
			* 就算这个参数比消息大小要小,消息还是可以被消费的
			* Kafak中所能接受的消息最大值,是通过服务端的参数:message.max.bytes 来设置

		fetch.max.wait.ms
			* 在数据不足 fetch.min.bytes 的时候,kafka会阻塞
			* 该参数指定kafka的最大等待时间,默认为 500ms
			* 超时后会自动的返回
			* 如果应用对延迟敏感,那可以适当的调小该参数

		reconnect.backoff.ms
			* 配置尝试重新连接指定主机之前的等待时间
			* 避免频繁地连接主机,默认值为: 50ms
			* 这种机制适用于消费者向broker发送的所有请求

		reconnect.backoff.max.ms
		retry.backoff.ms
			* 配置尝试重新发送失败的请求到指定的主题分区之前的等待时间
			* 避免在某些故障情况下频繁地发送,默认值为:100 ms

		auto.offset.reset
			* 当消费者找不到消费偏移量记录的时候,从哪里开始进行消费
			* 枚举值:
				earliest	重置为最早的偏移量,从头开始消费
				latest		将偏移重置为最新偏移量,通俗的说就是不消费以前的消息了,从下条消息开始消费(默认)
				none		如果没有找到偏移量记录,抛出异常

			* 如果设置偏移量越界了,也会通过该配置的策略来重置消费偏移量


		check.crcs
		metrics.sample.window.ms
		metrics.num.samples
		metrics.recording.level
		metric.reporters
		key.deserializer
		value.deserializer
			* 设置value和key的解码器

		request.timeout.ms
			* 默认的网络请求超时时间
			* 消费者等待broker的响应时间

		default.api.timeout.ms
		connections.max.idle.ms
			* 设置在多久之后关闭限制的连接,默认值是:540000 ms = 9分钟
			
		interceptor.classes
			* 配置一个或者多个消费者拦截器
			* 如果是多个使用逗号分隔

		max.poll.records
			* 一次最多可以拉取多少条消息
			* 默认值为 500 条,如果消息的体积都比较小的话,可以调大该值

		max.poll.interval.ms
			* 通过消费者管理消费者的时候,该配置指定拉取消息线程的最大空闲时间
			* 如果超过这个时间还没发起poll()操作,则消费组认为该消费者已经离开了消费组
			* 将会再次触发负载均衡(分区计算)
			* 默认值:300000

		exclude.internal.topics
			* Kafka中有两个内部主题:_consumer_offsets 和 __transaction_state
			* 该参数指定这俩内部主题是否对消费者公开,默认值为:true

			* 如果设置为true,那么消费者只能使用:subsribe(Collection<> ) 来订阅到内部Topic
			
		internal.leave.group.on.close
		isolation.level
			* 配置事务的隔离级别
			* 枚举值:
				READ_UNCOMMITTED(默认)
					* 可以读取到HW(High Watermark)处的位置
					* 可以读取到消费者未commit的事务消息
				
				READ_COMMITTED
					* 读事务已经提交的消息
					* 只能消费到 LSO(Last Stable Offset)的位置

		security.protocol
		ssl.protocol
		ssl.provider
		ssl.cipher.suites
		ssl.enabled.protocols
		ssl.keystore.type
		ssl.keystore.location
		ssl.keystore.password
		ssl.key.password
		ssl.truststore.type
		ssl.truststore.location
		ssl.truststore.password
		ssl.keymanager.algorithm
		ssl.trustmanager.algorithm
		ssl.endpoint.identification.algorithm
		ssl.secure.random.implementation
		sasl.kerberos.service.name
		sasl.kerberos.kinit.cmd
		sasl.kerberos.ticket.renew.window.factor
		sasl.kerberos.ticket.renew.jitter
		sasl.kerberos.min.time.before.relogin
		sasl.login.refresh.window.factor
		sasl.login.refresh.window.jitter
		sasl.login.refresh.min.period.seconds
		sasl.login.refresh.buffer.seconds
		sasl.mechanism
		sasl.jaas.config
		sasl.client.callback.handler.class
		sasl.login.callback.handler.class
		sasl.login.class

	# 构造函数
		ProducerConfig(Properties props)
		ProducerConfig(Map<String, Object> props)
	
	# 静态方法
		Map<String, Object> addSerializerToConfig(Map<String, Object> configs,Serializer<?> keySerializer, Serializer<?> valueSerializer)
		Properties addSerializerToConfig(Properties properties,Serializer<?> keySerializer,Serializer<?> valueSerializer)
		Set<String> configNames()
			* 返回可配置的key

		void main(String[] args)
			* main函数,以html格式打印配置和说明
		