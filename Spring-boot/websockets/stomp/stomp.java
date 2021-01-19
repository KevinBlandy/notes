------------------------
stomp
------------------------
	# STOMP 协议
		* stomp和websocket的关系就像是, Http和tcp的关系
		* 它描述了应用之间所发送消息的语义, 还需要确保连接的两端都能遵循这些语义
	
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
	
