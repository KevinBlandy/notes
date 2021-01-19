----------------------------
ActiveMQ-整合Spring框架		|
----------------------------
	# 需要的依赖
		<dependency>
	    	<groupId>org.apache.activemq</groupId>
		    <artifactId>activemq-all</artifactId>
		    <version>5.14.1</version>
		</dependency>

		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-jms</artifactId>
		    <version>4.3.3.RELEASE</version>
		</dependency>

----------------------------
ActiveMQ-xml配置			|
----------------------------
	<!-- 连接工厂 -->
	<bean id="connectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
		<!-- borker地址 -->
		<property name="brokerURL" value="tcp://123.207.122.145:61616"/>
	</bean>
	
	<!-- JMS 工厂 -->
	<bean id="jmsFactory" class="org.apache.activemq.pool.PooledConnectionFactory" destroy-method="stop">
		<!-- 注入连接工厂 -->
		<property name="connectionFactory" ref="connectionFactory"/>	
		<!-- 最大连接数 -->
		<property name="maxConnections" value="100"/>
	</bean>
	
	<!-- JMSTemplate -->
	<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
		<!-- 注入 JMS 工厂 -->
		<property name="connectionFactory" ref="jmsFactory"/>
		<!-- 注入默认队列 -->
		<property name="defaultDestination" ref="destination"/>
		<!-- 注入消息转换器 -->
		<property name="messageConverter">
			<bean class="org.springframework.jms.support.converter.SimpleMessageConverter"></bean>
		</property>
	</bean>
	
	<!-- Queue -->
	<bean id="destination" class="org.apache.activemq.command.ActiveMQQueue">
		<!-- 构造注入队列名称 -->
		<constructor-arg index="0" value="spring-queue"/>	
	</bean>


----------------------------
ActiveMQ-Provider			|
----------------------------
	public static void main(String[] args) {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		try{
			//获取tmplate模版
			JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
			//调用send方法执行发送
			jmsTemplate.send(new MessageCreator() {
				//匿名内部类
				public Message createMessage(Session session) throws JMSException {
					//创建文本信息,可以用会话创建,也可以自己new实例
					TextMessage message = session.createTextMessage();
					message.setText("Hello,Spring");
					return message;
				}
			});
		}finally{
			if(applicationContext != null){
				applicationContext.close();
			}
		}
	}

----------------------------
ActiveMQ-Consumer			|
----------------------------
	public static void main(String[] args) {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		try{
			//获取tmplate模版
			JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
			while(true){
				String message = (String) jmsTemplate.receiveAndConvert();
				System.err.println(message);
			}
		}finally{
			if(applicationContext != null){
				applicationContext.close();
			}
		}
	}