Connection
	* JMS定义的接口,代表跟MQ服务器的一个连接,通常一个服务器只用定义一个接口
	* 当一个 Connection 定义的时候,默认是关闭状态.需要手动开启
	* 一个 Connection 可以创建N多个 Session
	* 当程序执行完毕,应该关闭 Connection,它会自动的去关闭 Session,MessageProducer 和 MessageConsumer
	

Session
	* JMS定义的接口,由 Connection 生成,一个或者多个. Session 是一个收发消息的线程
	* 可以使用 Session 创建 MessageProducer,MessageConsumer和Message
	* Session 可以被事务化,或者非事务化,通过向创建 Session 的方法,传递 boolean 参数控制
		Session session = connection.createSession(boolean transacted, int acknowledgeMode);
			transacted:是否创建事务session
				'本地事务'
				结束事务有两种方法,提交或者回滚
				当一个事务提交,消息被处理,如果事务中有一个步骤失败,事务就回滚.这个事务中已执行的动作将会被撤销.
				在发送消息最后也必须要用 session.commit();方法表示提交事务
				'所谓的事务回滚,其实就是---不发送消息到message,在回滚/提交之前.你可以进行大量的消息写入队列或者其他操作,提交就去了服务器,回滚就啥也没了'

			acknowledgeMode:消息签收模式
				Session.AUTO_ACKNOWLEDGE
					当消费者从receive或者onMessage成功返回时,Session自动签收消费者这条消息的收条
					'自动的签收消息'

				Session.CLIENT_ACKNOWLEDGE
					消费者通过调用消息(Message)的acknowledge方法签收消息
					这种情况下,签收发生在 Session 层面
					签收一个已经消费的消息,会自动的签收这个 Session 所有已经消息的收条
					'手动的签收消息'

				Session.DUPS_OK_ACKNOWLEDGE
					这参数,Session不会确保对传送消息的签收,它可能引起消息的重复,但是降低了Session的开销
					只有消费者允许重复的消息,才使用
					'不执行签收,N个消费者可能会消费同一条消息,或者一个消费者重复消费同一条消息'


MessageProducer
	* 是一个由 Session 创建的对象,用来向 Destination 发送消息
	* session创建
		MessageProducer createProducer(Destination destination)
	* 发送消息
		void send(Message message)
		void send(Message message, int deliveryMode, int priority, long timeToLive)
		void send(Destination destination,Message message)
		void send(Destination destination,Message message,int deliveryMode,int priority,long timeToLive)
		* 参数介绍
			deliveryMode
				* 消息传送模式(是否持久化)
					DeliveryMode.NON_PERSISTENT			//不持久化,性能好.数据易丢失
					DeliveryMode.PERSISTENT(默认)		//持久化,性能差.数据不易丢失

			priority
				* 消息优先级
					优先级从 0-9 一共十个级别(默认值=4),JMS不要求严格按照10个优先级发送消息
					0-4	普通消息		//
					5-9	加急消息		//该消息,理论会比普通消息先到达.注意哟,是理论

			timeToLive
				* 过期时间
					默认清空下,消息永远不会过期,如果需要消息在一定时间内失去意义,那么可以设置该选项
					单位是毫秒
	

MessageConsumer
	* 是一个由 Session 创建的对象,用来从 Destination 接收消息
	* session创建
		MessageConsumer createConsumer(Destination destination);
		MessageConsumer createConsumer(Destination destination,String messageSelector);
		MessageConsumer createConsumer(Destination destination,String messageSelector,boolean NoLocal);
		* 参数介绍
			messageSelector
				* 消息选择器(过滤器)
				* 是基于 SQL92的语法,其实就是某种规则的字符串(例如:正则)

			NoLocal
				* 默认为 false
				* 为 true 的话,只能接收和自己相同连接(Connection)所发布的消息,此标识仅仅'适用于主题,不适用于队列'
			name
				* 明确,订阅主题的时候,声明的订阅名称.持久订阅时需要此参数
			

		TopicSubscriber createDurableSubscriber(Topic topic,String name);
		TopicSubscriber createDurableSubscriber(Topic topic,String name,String messageSelector,boolean noLocal)
	
