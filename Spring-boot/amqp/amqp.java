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
		

-------------------
rabbitmq 配置
-------------------
	# 配置类
		RabbitProperties

spring:
  rabbitmq
    # 这种配置方式可以连接到集群: host1:5672,host2:5672,host3:5672
    addresses: "amqp://admin:secret@localhost" 
    host: localhost
    port: 5672
    username: admin
    password: secret


