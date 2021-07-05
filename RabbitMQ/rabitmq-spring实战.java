----------------------------
消费者配置					|
----------------------------
	<!-- 定义RabbitMQ的连接工厂 -->
	<rabbit:connection-factory  id="connectionFactory" host="127.0.0.1" port="5672" username="kevin" password="a12551255" virtual-host="/kevinblandy" />
	
	<!-- 
		注册管理
	 -->
	<rabbit:admin connection-factory="connectionFactory"/>
	<!--
		定义队列
		自动声明
		持久化
	-->
	<rabbit:queue name="taotao-web-item" auto-declare="true" durable="true">
	</rabbit:queue>
	<!--
		监听
		配置工厂
		执行bean,及其执行的方法,和队列名称
	-->
	<rabbit:listener-container connection-factory="connectionFactory">
		<rabbit:listener ref="bean" method="demo" queue-names="taotao-web-item" />
	</rabbit:listener-container>
	<!--
		业务Bean
	-->
	<bean id="bean" cass="xx.xx.xx.xx.Handler"/>


----------------------------
生产者配置					|
----------------------------
	# 把消息发送到交换机

	<!-- 定义RabbitMQ的连接工厂 -->
	<rabbit:connection-factory  id="connectionFactory" host="127.0.0.1" port="5672" username="kevin" password="a12551255" virtual-host="/kevinblandy" />
	
	<!-- 
		注册管理
	 -->
	<rabbit:admin connection-factory="connectionFactory"/>
	
	<!-- 
		定义交换机 (通配类型)
		名称
		自动声明
		持久化
	-->
	<rabbit:topic-exchange name="kevin-exchange" auto-declare="true" durable="true"/>
	
	<!-- 
		定义模版
		指定连接工厂与交换机
	 -->
	<rabbit:template id="template" connection-factory="connectionFactory" exchange="kevin-exchange"/>
