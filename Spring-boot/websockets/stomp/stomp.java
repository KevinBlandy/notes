------------------------
stomp
------------------------
	# ��ַ
		http://stomp.github.io/
		http://stomp.github.io/stomp-specification-1.2.html

		https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp

		https://blog.csdn.net/a617137379/article/details/78765025

	# STOMP Э��(Simple (or Streaming) Text Orientated Messaging Protocol)
		* stomp��websocket�Ĺ�ϵ������, Http��tcp�Ĺ�ϵ
		* ��������Ӧ��֮����������Ϣ������, ����Ҫȷ�����ӵ����˶�����ѭ��Щ����
	
		* stompЭ�鲢����Ϊwebsocket��Ƶ�, ����������Ϣ���е�һ��Э��, ��amqp, jmsƽ��.
		* ֻ�����������ļ���ǡ�ɿ������ڶ���websocket����Ϣ���ʽ.


	# Maven
		<!--websocket-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
        <!--jackson,����SockJs��Spring WebSocket֮�����JSONͨѶ����Ҫ����jackson�����jar��-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
	
	# ����
		* client
			������client
			������client
		
		* server
			broker, ��Ϣ���еĹ�����
	
	# ��Ϣ���
		[command]
		header:value
		header:value

		Body^@
		
		* ^@ ����null��β.
		* 3���������: ����, Header, Body
		* body�����Ƕ����ƻ������ַ���
	

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