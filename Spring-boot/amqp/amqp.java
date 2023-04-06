-------------------
rabbitmq
-------------------
	# 参考
		https://docs.spring.io/spring-amqp/docs/current/reference/html/
		https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.messaging
	
	# 依赖
		 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
	
	
	# spring-amqp 提供了更高级的抽象，底层实现可以是Rabbit或者其他实现了amqp的协议的消息队列
		
	
	# 核心组件
		CachingConnectionFactory 
			* 连接工厂
		RabbitAdmin 
			* RabbitMQ的管理客户端实现
		RabbitTemplate 
			* 消息发送Template
		
		Queue 
			* 队列
		Message
			* 消息对象
		Exchange
			* 交换机
		Binding
			* 绑定关系
	
	# AMQP 注解
		@EnableRabbit
		@RabbitListener
		@Exchange
		@Queue
		@QueueBinding
		@RabbitListeners
		@RabbitHandler
			boolean isDefault() default false;
				* 当为 true 时，如果 payload 类型与其他 RabbitHandler 方法不匹配，指定这是默认的 fallback 方法。
				* 只有一个方法可以被如此指定。
			
			* 注解在方法上，与注解在类上的 @RabbitListener 进行配合
			* 收到来自于 @RabbitListener 指定队列的消息时，从当前类所有注解了 @RabbitHandler 方法中，根据消息数据类型和方法参数进行匹配，选择一个方法进行调用。
			* 参数要么是单个，如果是多个，则需要使用 @Payload 指定消息绑定的参数

		@Argument
	
	# 通用的Message注解
		@Header
			* 消息头

		@Headers
		@DestinationVariable
		@MessageExceptionHandler
		@MessageMapping
		@Payload
			* 消息体
		
		@SendTo


-------------------
rabbitmq 配置
-------------------
	# 配置类
		RabbitProperties

spring:
  rabbitmq:
    # 这种配置方式可以连接到集群: host1:5672,host2:5672,host3:5672
    addresses: "amqp://admin:secret@localhost" 
    host: localhost
    port: 5672
    username: admin
    password: secret



