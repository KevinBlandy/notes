------------------------
ProducerConfig			|
------------------------
	# 消息生产者配置
	# 提供了N多的配置项(它们都静态类变量存在,大写,.转换为下划线,并且以 CONFIG 结尾)
		bootstrap.servers (BOOTSTRAP_SERVERS_CONFIG)
			* Kafka集群的列表,多个使用逗号分隔
			* ip:port

		client.dns.lookup
		buffer.memory
			* 消息累加器(RecordAccumulator)的缓存大小
			* 默认值为:33554432kb = 32MB

	
		
		acks
			* 指定分区中必须要有多少个副本接收了消息,才返回成功
			* 它涉及消息的可靠性和吞吐量之间的平衡
			* 枚举值
				0
					* 消息发生就认为是成功了,不管分区是否写入成功(吞吐量最大,消息可能会丢失)
				1
					* 只要消息写入了分区的leader副本就算成功(折中,一般用这个)
				-1
					* 值也可以是:all
					* 必须是分区的所有副本(ISR)都写入了,才算成功
					* 但这并不意味着消息就一定可靠,因为 ISR 中可能只有 leader 副本,这样就退化成了 acks= l 的情况
					* 要获得更高的消息可靠性需要配合 min.insync.replicas 等参数的联动

		compression.type
			* 设置消息的压缩方式,默认为:none 也就是不压缩
			* 支持的算法(值)
				gzip
				snappy
				lz4

		batch.size
			* 在 RecordAccumulator 中 BufferPool 会缓存的 ByteBuffer 大小
			* BufferPool 只针对特定大小的 ByteBuffer 进行管理,而其他大小的 ByteBuffer 不会缓存进 BufferPool 中
			* 默认:16384B,即 16KB

		linger.ms
			* 设定生产者发送 ProducerBatch 之前等待更多消息(ProducerRecord)加入ProducerBatch 的时间
			* 默认值为 0(只有有消息加入了ProducerBatch就发送,不等待)
			* 生产者客户端会在 ProducerBatch 被填满或等待时间超过 linger.ms 值时发迭出去
			* 增大这个参数的值会增加消息的延迟,但是同时能提升一定的吞吐量
			* 这个参数与 TCP 协议中的 Nagle 算法有异 曲同工之妙

		delivery.timeout.ms
		client.id
			* 设置客户端的id

		send.buffer.bytes
			* 用来设置 Socket 发送消息缓冲区(SO_SNDBUF)的大小,默认值为 131072B,也就是128KB
			* 如果设置为 -l,则使用操作系统的默认值

		receive.buffer.bytes
			* 用来设置 Socket 接收消息缓冲区(SO_RECBUF )的大小
			* 默认值为 32768B,也就是32 kb
			* 如果设置为 -l,则使用操作系统的默认值
			* 如果 Producer 与 Kafka 处于不同的机房 ,则可以适地调大这个参数值 

		max.request.size
			* 限制消息生产者能够发送的消息最大体积默认为:1048576kb = 1MB
			* 一般不建议去修改
			* 也要注意,broker也是可以设置接受的消息最大体积

		reconnect.backoff.ms
		reconnect.backoff.max.ms
		retries
			* 对于可重试的异常,如果配置了 retries 参数,那么只要在规定的重试次数内自行恢复了,就不会抛出异常
			* 默认值为 0,也就是说不会重试,立即抛出异常

		retry.backoff.ms
			* 对于可重试异常发生后,两次重复调用的时间间隔
			* 默认为:100(毫秒)
			
		max.block.ms
			* 在send()api被阻塞的时候(缓冲区满了,没有空间存放消息,一般发生在消息生产速度,大于消息的发送速度)
			* 最多阻塞多少时间(毫秒),超过该时间就会抛出异常
			* 默认为:60000(60s)

		request.timeout.ms
			* Producer 等待请求响应的最长时间,默认值为 30000 (ms)
			* 请求超时之后可以选择进行重试
			* 注意这个参数需要 比 broker 端参数 replica.lag.time.max.ms 的值要大
			* 这样可以减少因客户端重试而引起的消息重复的概率

		metadata.max.age.ms
			* 超过该时间未更新元数据,就会选择负载最小的broker,发送MetadataRequest来更新元数据信息
			* 默认300000(ms) = 5分钟

		metrics.sample.window.ms
		metrics.num.samples
		metrics.recording.level
		metric.reporters
		max.in.flight.requests.per.connection
			* 消息从RecordAccumulator发送到broker的请求,会被缓存到 InFlightRequests 中,直到响应
			* 该配置限制每个连接(也就是客户端与 Node 之间的连接)最多缓存的请求数
			* 该请求就是,把消息已经从缓存中发出去了,但是还没收到响应的请求
			* 默认值为5,也就是说,最多缓存5个未响应的请求,一旦超过该值,就不能往这个连接发送更多的请求了
			* 除非缓存中的请求,收到了响应

		key.serializer
		value.serializer
			* 设置key/value的编码器
			* 值是编码器的类路径

		connections.max.idle.ms
			* 指定在多久之后关闭限制的连接,默认值是 540000(ms)即 9 分钟

		partitioner.class
			* 设置分区器的实现类

		interceptor.classes
			* 设置拦截器的实现类

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
		enable.idempotence
			*  是否开启幕等性功能

		transaction.timeout.ms
		transactional.id
			* 设置事务的id,必须唯一

	
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