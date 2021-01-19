---------------------------------
ActiveMQ-整合Spring框架总结	-组件|
---------------------------------
	# 连接工厂,由第三方厂商提供.最底层的连接工厂
		<bean id="targetConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
			<!-- borker地址 -->
			<property name="brokerURL" value="tcp://123.207.122.145:61616"/>
			<!--用户名-->
			<property name="userName" value="kevin"/>
			<!--密码 -->
			<property name="password" value="a12551255"/>
		</bean>
		* 用户名和密码如果不配置,则使用默认的
	
	# 连接池,把厂商的工厂进行池化.提高性能
		<bean id="pooledConnectionFactory" class="org.apache.activemq.pool.PooledConnectionFactory" destroy-method="stop">
			<!-- 注入连接工厂 -->
			<property name="connectionFactory" ref="targetConnectionFactory"/>	
			<!-- 最大连接数 -->
			<property name="maxConnections" value="100"/>
		</bean>
	
	# spring提供的连接工厂,它其实是正常的工厂.注入给其他组件使用.配置连接池.相当于一个代理的连接工厂,用于管理真正的 ConnectionFactory 的 ConnectionFactory
		<bean id="connectionFactory" class="org.springframework.jms.connection.SingleConnectionFactory">
			<property name="targetConnectionFactory" ref="pooledConnectionFactory"/>
		</bean>

	# Template,Spring提供的工具类,可以对消息进行收发,可以配置N多个.哪里需要.哪里注入
		<!-- JMSTemplate -->
		<bean class="org.springframework.jms.core.JmsTemplate">
			<!-- 注入连接工厂 -->
			<property name="connectionFactory" ref="connectionFactory"/>
			<!-- 注入默认队列/话题 -->
			<property name="defaultDestination" ref="topic"/>
			<!-- 直接设置队列的名称,不注入.会默认的生成一个指定名称的队列-->
			<property name="defaultDestinationName" value="ququ#1"/>
			<!-- 注入消息转换器 -->
			<property name="messageConverter">
				<bean class="org.springframework.jms.support.converter.SimpleMessageConverter"></bean>
			</property>
		</bean>
	
	# 这个组件,是专门为消费者准备的.是一个容器.里面配置了队列/话题,连接工厂,监听等信息
		<bean id="defaultMessageListenerContainer" class="org.springframework.jms.listener.DefaultMessageListenerContainer">
			<!-- 注入连接工厂 -->
			<property name="connectionFactory" ref="connectionFactory"/>
			<!-- 监听的队列/话题-->
			<property name="destination" ref="queue"/>
			<!-- 消费bean,也就是逻辑处理-->
			<property name="messageListener" ref="listenner"/>
		</bean>
		* 有了它,只要启动IOC即可.当消费者收到消息.就会去调用 listenner 类的 onMessage 方法
		* 可以配置多个,也就是说一个应用,监听了N多的队列或者话题
	
	# 监听,也就是实际上的业务逻辑处理Bean
		<bean id="listenner" class="com.kevin.demo.activemq.spring.listener.Listenner"></bean>
		* 普通的bean而已,但是必须要实现 MessageListener 接口.或者是Spring 的

	# Topic,其实就是一个Bean,配置在IOC里面就好.哪个Template 需要,就给它
		<bean id="topic" class="org.apache.activemq.command.ActiveMQTopic">
			<!-- 构造注入话题名 -->
			<constructor-arg index="0" value="spring-topic"/>
		</bean>
	
	# Queue,其实就是一个Bean,配置在IOC里面就好.哪个Template 需要,就给它
		<bean id="queue" class="org.apache.activemq.command.ActiveMQQueue">
			<!-- 构造注入队列名称 -->
			<constructor-arg index="0" value="spring-queue"/>	
		</bean>
	



	# 关于这个连接池之间的关系,只是感觉有点复杂
		ActiveMQConnectionFactory
					| 注入
		PooledConnectionFactory(connectionFactory)
					| 注入
		SingleConnectionFactory(targetConnectionFactory)		//这个其实是最终的 ConnectionFactory,注入给需要 ConnectionFactory的组件


---------------------------------
ActiveMQ-PTP-Provider			 |
---------------------------------
	public static void main(String[] args) {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		try{
			//从IOC中获取tmplate模版
			JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
			/**
			 * 调用模版的send方法执行发送
			 * 	send方法有N多重载,例如:可以在执行发送的时候指定队列/话题
			 * 参数是 MessageCreator 接口的子类,一般都是直接匿名内部类了
			 * */
			jmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					/**
					 * message可以通过形参session创建,也可以直接new出实例.
					 * TextMessage message = new ActiveMQTextMessage();
					 * */
					TextMessage message = session.createTextMessage("Hello Spring");
					return message;		//返回这个message
				}
			});
		}finally{
			if(applicationContext != null){
				applicationContext.close();
			}
		}
	}

---------------------------------
ActiveMQ-PTP-Consumer			 |
---------------------------------
	# 配置OK,直接启动IOC就好.有消息.就会通知监听bean




---------------------------------
ActiveMQ-总结					 |
---------------------------------
	# Spring  与 ActiveMQ 的整合,其实整个过程都是相当的简单,而且比较自由
		* 可以把队列在xml中配置给模版,也可以把队列注入到业务类,在模板执行send方法的时候,手动的指定队列
	
