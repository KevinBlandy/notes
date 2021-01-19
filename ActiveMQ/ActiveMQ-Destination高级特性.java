-------------------------------------
ActiveMQ-Destination高级特性		 |
-------------------------------------


-------------------------------------
ActiveMQ-Wildcards					 |
-------------------------------------
	# Wildcards 通配符
	# 这东西并不是JMS的规范,而是ActiveMQ的扩展
	# ActiveMQ支持一下三种:wildcards
	①:".",用于作为路径上名字间的分隔符
	②:"*",用于匹配任何名字
	③:">",用于递归匹配任何以这个名字开始的 Destination


-------------------------------------
ActiveMQ-Composite Destinations		 |
-------------------------------------
	# 组合队列
	# 组合队列,允许用一个虚拟的 Destination 代表多个 Destination
	# 说白了就是,同时往多个队列,或者 topic 推送消息
	# 代码
		/**
			此处就是创建了带有三个队列的一个 Queue
			多个Queue用逗号隔开
		*/
		Queue queue = new ActiveMQQueue("queue#1,queue#2,queue#3");
		/**
			通过这个,创建消息生产者
		*/
		MessageProducer producer = session.createProducer(queue);
	
	# 也可以混合发送,就是一个 Queue 里面不进有队列,还有 Topic
	# 代码
		Queue queue = new ActiveMQQueue("queue#1,topic://topic#1");
		MessageProducer producer = session.createProducer(queue);
		* 跟上面其实一个德行,无非就是.话题的格式是用: topic://   
	
	# 也可以通过配置来完成...不过很傻逼,不学


-------------------------------------
ActiveMQ-Delete Inactive Destination |
-------------------------------------
	# 自动删除队列
	# 一般清空下,ActiveMQ,的 Queue,在不使用之后,可以通过WEB控制,或者JMX的方式来进行删除
	# 也可以通过配置,让MQ自动探测到无用的队列(一定时间内为空的队列),进行删除.释放资源

		<broker schedulePeriodForDestinationPurge="10000">
			<destinationPolicy>
				<policyMap>
					<policyEntries>											 
						<policyEntry queue=">" gcInactiveDestinations="true" inactiveTimoutBeforeGC="30000"/>
					</policyEntries>
				</policyMap>
			</destinationPolicy>
		</broker>
		
		* schedulePeriodForDestinationPurge		:多长时间检查一次,这里设置为10S,默认为0
		* inactiveTimeoutBeforGC				:当 Destination 为空后,多长时间被删除,这里是30s,默认为60
		gcInactiveDestinations					:设置删除掉不活动的队列,默认为 false

-------------------------------------
ActiveMQ- Destination Options		|
-------------------------------------
	# 队列选项,这东西也并不是JMS规范中的东西
	# 其实就是说可以在队列名称后面向HTTP的URL传参一样传递一些参数
	# 参数有
		consumer.prefetchSize
			* consumer持久的未ack的最大消息数量,默认值为 variable
		consumer.maximumPendingMessageLimit
			* 用来控制非持久化的topic在存在慢消费者的情况下丢弃的数量
		consumer.noLocal
			* 默认为 false
		consumer.dispatchAsync
			* 是否异步分发,默认为 true
		consumer.retroactive
			* 是否回溯消费者,默认为 false
		consumer.selector
			* JMS的selector,默认为 null
		consumer.exclusive
			* 是否为独占消费者,默认为 false
		consumer.priority
			* 设置消费者的优先级别,默认 0
	
	# Demo
		Queue queue = new ActiveMQQueue("queue#1?consumer.exclusive=false&consumer.priority=4");
		MessageConsumer messageConsumer = session.createConsumer(queue);