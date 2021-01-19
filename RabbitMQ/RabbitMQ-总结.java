-------------------------------
RabbitMQ-总结					|
-------------------------------
	# 关键字详解
		
		RabbitMQ Server
			* 他的角色就是维护一条从Producer到Consumer的路线，保证数据能够按照指定的方式进行传输。但是这个保证也不是100%的保证，但是对于普通的应用来说这已经足够了。
		
		Producer
			* 消息生产者
		
		Consumer
			* 消息消费者
		
		Message
			* 一个消息有两部分
			1,payload
				> 传输的数据
			2,label
				> 标签,描述数据
			
		Exchanges 
			
		Connection
			* 就是一个TCP连接,生产者和消费者都通过TCP连接到RabbitMQ服务器
			* 程序的起始,就是创建这个东西
		
		Channels
			* 通道,虚拟连接.它是建立在TCP连接中,数据流动都是在 Channel 中进行的
			* 程序起始,先建立TCP连接,然后就建立这个通道
			* 为什么不直接使用TCP,而是要基于TCP使用 Channel (通道)?
				> 对于系统来说,建立和关闭TCP是有代价的,而且TCP连接数量有限制.但是,在TCP上建立通道 Channel 是没有代价的
				> 可以并发使用多个 Producer 进行 Push 或 Consumer 进行 Receive
		Queues
			* 队列

		Bindings 
			* 绑定


-------------------------------
RabbitMQ-消息确认机制			|
-------------------------------
	ACK(acknowledged)机制,确定Message的正确传递

		* 默认情况下, Consumer 正确的收到了消息,那么这个消息就会从 Queue 中移除,
		* 当然也可以让同一个 Message 发送到更多的 Consumer
		* 如果一个 Queue 没有被任何的 Consumer 订阅,如果这个 Queue 有数据到达,那么这个数据会被缓存,不会被丢弃, 当有 Consumer 订阅的时候,这个数据会立即发送给 Consumer,Consumer正确收到的时候,Message 从 Queue 中删除
		
	# Consumer 正确的收到消息
		通过 ACK,每个 Message 都要被确定,可以在程序中去ACK,也可以自动的ACK

	# 如果有 Message 没有被ACK
		* RabbitMQ Server 会把这个信息发送到下一个 Consumer
		* 如果是应用BUG导致,忘记ACK.那么 RabbitMQ Server 将不会发送数据给它,因为RabbitMQ Server 认为它的处理能力有限
	
	# 'ACK',机制,'可以起到限流的作用'
		* 在 Consumer 处理完成数据后,发送ACK.甚至延迟发送.都会有效的控制消息的处理.


-------------------------------
RabbitMQ-队列					|
-------------------------------
	Queue
		
		* Producer 和 Consumer都可以创建 Queue
		* 对于某个 Channel (通道)来说,Consumer 不可能创建一个 Queue,却监听了另一个 Queue,当然也可以建立私有的 Queue,只有当前应用和可以使用
		* Queue 也可以被自动的删除,auto-delete 标记 true 的 Queue,在最后一个 消费者退订的时候,自动的删除
		* '创建一个已经存在的Queue,是没有任何影响的.也就是说第二次,创建的参数和第一次的参数不一样.虽然创建OK,但实际上 Queue的参数不会被修改'
	
	# 到底该谁来创建 Queue
		* 如果 Queue 不存在,消费者不会得到任何的消息,而且,生产者的消息也会被丢弃
		* 所以,为了数据不会被丢失,生产者和消费者都应该尝试创建 Queue,反正不管怎么样！这个接口都不会出现问题
	


-------------------------------
RabbitMQ-交换机					|
-------------------------------
	Exchanges
		
		* 消息生产者发布的消息,进入了交换机.通过:routing keys,RabbitMQ会找到应该把这个Message放到哪个queue里
		* queue也是通过这个routing keys来做的绑定。
	
	# 有三种类型的 Exchanges
		1,Direct exchange
			* 如果 routing key 匹配, 那么Message就会被传递到相应的queue中。
			* 其实在queue创建时,它会默认自动的以queue的名字作为routing key来绑定那个exchange。
		
		2,Fanout exchange
			* 会向响应的 Queue 广播
			* 只要你订阅我了,我就会广播
		
		3, Topic exchange
			* 匹配模式.这种模式的 routing key 可以通过占位符来表示.

	
-------------------------------
RabbitMQ-虚拟主机				|
-------------------------------
	Virtual hosts
		* 每个virtual host本质上都是一个RabbitMQ Server，拥有它自己的queue，exchagne，和bings rule等等。
		* 这保证了你可以在多个不同的application中使用RabbitMQ。
	



	