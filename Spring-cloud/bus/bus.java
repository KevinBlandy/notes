---------------------
bus					 |
---------------------
	# 文档
		https://cloud.spring.io/spring-cloud-bus/spring-cloud-bus.html
	
	# 需要依赖到MQ中间件
		RabbitMQ
		Kafka
	
	# 模型
		* 客户端添加,bus依赖,连接到 mq
		* 配置内容被修改后,触发任意客户端的 /actuator/bus-refresh
		* 此时,所有连接到bus的客户端都会发生更新操作

	# 客户端 Maven,bus的实现,二选一
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
			* amqp就是Rabbitmq
		
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-kafka</artifactId>
        </dependency>
			* 使用Kafka
		
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
	
		* actuator 模块的作用是提供 /actuator/bus-refresh ,bus-env 端点,用于触发任意客户端的刷新,从而通知到所有连接到Bus的客户端
		* 一般而言,负责执行刷新操作的客户端一般都是 : config-server
		* 其他的微服务客户端不必承担刷新配置的工作,从而简化了集群的一些维护工作
		* 其他的微服务客户端也许并不需要 actuator 依赖

	# RabbitMQ客户端配置
spring:
  rabbitmq:
    host: 58.87.75.64
    port: 5671
    username: guest
    password: guest
    ssl:
      enabled: true


	# Kafka客户端配置
		TODO

	# 负责触发刷新事件的节点,需要配置 actuator,提供 /bus-refresh,bus-env 端点
		management.endpoints.web.exposure.include=bus-refresh
		management.endpoints.web.exposure.include=bus-env
		
		* /actuator/bus-refresh
			* 触发配置更新(手动修改了内容),会更新bus中所有标识了 @RefreshScope 的配置
			* @RefreshScope,可以标识在方法,字段上,它们一般都是通过 @Value 注入了属性值
			* 该注解有一个属性:proxyMode,用于设置代理模式(动态更新依赖于动态代理)
				ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;
				DEFAULT,
				NO,
				INTERFACES,
				TARGET_CLASS;


		* /actuator/bus-env
			* 修改配置,并且触发更新,该端点直接提供了通过HTTP修改配置项,并且广播更新的功能
			* POST请求,请求体为JSON,内容为
				{
					"name": "key1",
					"value": "value1"
				}
							
		

---------------------
跟踪消息总线事件	 |
---------------------
	# 配置开启
		spring.cloud.bus.trace.enabled=true
	
	# 访问端点(actuator)
		/trace


---------------------
客户端监听消息事件	 |
---------------------
	# 继承类,并且实现
		RemoteApplicationEvent 
	
	# 注解驱动 @RemoteApplicationEventScan
		* 指定实现类的扫描包 @RemoteApplicationEventScan(basePackages = {"com.acme", "foo.bar", "fizz.buzz"})
	