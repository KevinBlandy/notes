------------------------
stomp
------------------------
	# 地址
		http://stomp.github.io/
		http://stomp.github.io/stomp-specification-1.2.html

		https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp

		https://blog.csdn.net/a617137379/article/details/78765025

	# STOMP 协议(Simple (or Streaming) Text Orientated Messaging Protocol)
		* stomp和websocket的关系就像是, Http和tcp的关系
		* 它描述了应用之间所发送消息的语义, 还需要确保连接的两端都能遵循这些语义
	
		* stomp协议并不是为websocket设计的, 它是属于消息队列的一种协议, 和amqp, jms平级.
		* 只不过由于它的简单性恰巧可以用于定义websocket的消息体格式.


	# Maven
		<!--websocket-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
        <!--jackson,由于SockJs与Spring WebSocket之间采用JSON通讯，需要引入jackson的相关jar包-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
	
	# 概念
		* client
			消费者client
			生产者client
		
		* server
			broker, 消息队列的管理者
	
	# 消息组成
		[command]
		header:value
		header:value

		Body^@
		
		* ^@ 代表null结尾.
		* 3个部分组成: 命令, Header, Body
		* body可以是二进制或者是字符串
	

------------------------
stomp 命令
------------------------
	CONNECT
		* 创建链接
			CONNECT
			accept-version:1.2
			host:stomp.github.org

			^@
		* 响应
			CONNECTED
			version:1.2

			^@



