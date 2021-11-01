---------------
MQTT
---------------
	# MQTT（Message Queuing Telemetry Transport，消息队列遥测传输协议）
		 * 物联网界的HTTP协议

	# 地址
		https://mqtt.org/
		https://github.com/mqtt
		
		http://mqtt.p2hp.com/
		https://mcxiaoke.gitbooks.io/mqtt-cn/content/
		https://www.runoob.com/w3cnote/mqtt-intro.html


---------------
MQTT - 协议
---------------
	# Protocal
		[fixed header 1byte][header][body]

	
	# fixed header
		[7 - 4 bit]	控制报文
					名字		报文流动		描述
			0x00	Reserved	禁止			保留
			0x01	CONNECT		客户端到服务端	客户端请求连接服务端
			0x02	CONNACK		服务端到客户端	连接报文确认
			0x03	PUBLISH		两个方向都允许	发布消息
			0x04	PUBACK		两个方向都允许	QoS 1 消息发布收到确认
			0x05	PUBREC		两个方向都允许	发布收到（保证交付第一步）
			0x06	PUBREL		两个方向都允许	发布释放（保证交付第二步）
			0x07	PUBCOMP		两个方向都允许	QoS 2 消息发布完成（保证交互第三步）
			0x08	SUBSCRIBE	客户端到服务端	客户端订阅请求
			0x09	SUBACK		服务端到客户端	订阅请求报文确认
			0xa		UNSUBSCRIBE	客户端到服务端	客户端取消订阅请求
			0xb		UNSUBACK	服务端到客户端	取消订阅报文确认
			0xc		PINGREQ		客户端到服务端	心跳请求
			0xd		PINGRESP	服务端到客户端	心跳响应
			0xe		DISCONNECT	客户端到服务端	客户端断开连接
			0xf		Reserved	禁止			保留

		[3 - 0 bit]	标志位
			