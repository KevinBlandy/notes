------------------------
rabbitmq
------------------------
	# 地址
		http://www.rabbitmq.com/download.html
		http://rabbitmq.mr-ping.com/
		https://github.com/rabbitmq
	
	# 默认端口信息
		amqp		:5672			//MQ协议
		web			:15672			//WEB管理端口
		dustering	:25672			//集群端口


------------------------
核心的概念
------------------------
	RabbitMQ Server
		* 他的角色就是维护一条从Producer到Consumer的路线，保证数据能够按照指定的方式进行传输。但是这个保证也不是100%的保证，但是对于普通的应用来说这已经足够了。
		* 整个模式
			producer -> connection(channels) -> Broker(exchanges[queues]) -> connection(channels) -> consumer
	
	Producer(core)
		* 消息生产者
	
	Consumer(core)
		* 消息消费者
	
	Message(core)
		* 一个消息有两部分
			1, payload 传输的数据
			2, label 标签,描述数据
	
	Queues(core)
		* 队列
		
	Exchanges(core)
		* 交换机
		
	Connection
		* 就是一个TCP连接,生产者和消费者都通过TCP连接到RabbitMQ服务器
		* 程序的起始,就是创建这个东西
	
	Channels
		* 通道,虚拟连接.它是建立在TCP连接中,数据流动都是在 Channel 中进行的
		* 程序起始,先建立TCP连接,然后就建立这个通道
		* 为什么不直接使用TCP,而是要基于TCP使用 Channel (通道)?
			> 对于系统来说,建立和关闭TCP是有代价的,而且TCP连接数量有限制.但是,在TCP上建立通道 Channel 是没有代价的
			> 可以并发使用多个 Producer 进行 Push 或 Consumer 进行 Receive
		
		* 通俗理解就是，通过一个TCP连接可以往N个channel发送数据
	

	Bindings 
		* 绑定

	
	Virtual Host
		* 多租户，类似于namespace一样，一个Server可以划分出多个Virtual Host
		* 可以让不同的用户维护自己的exchange/queue
	
