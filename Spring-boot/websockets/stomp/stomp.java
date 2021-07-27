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
Augmented BNF
------------------------

LF                  = <US-ASCII new line (line feed) (octet 10)>
OCTET               = <any 8-bit sequence of data>
NULL                = <octet 0>

frame-stream        = 1*frame

frame               = command LF
                      *( header LF )
                      LF
                      *OCTET
                      NULL
                      *( LF )

command             = client-command | server-command

client-command      = "SEND"
                      | "SUBSCRIBE"
                      | "UNSUBSCRIBE"
                      | "BEGIN"
                      | "COMMIT"
                      | "ABORT"
                      | "ACK"
                      | "NACK"
                      | "DISCONNECT"
                      | "CONNECT"
                      | "STOMP"

server-command      = "CONNECTED"
                      | "MESSAGE"
                      | "RECEIPT"
                      | "ERROR"

header              = header-name ":" header-value
header-name         = 1*<any OCTET except LF or ":">
header-value        = *<any OCTET except LF or ":">