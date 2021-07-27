---------------------------
Spring Stomp
---------------------------
	# 地址
		https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp
	
	# 核心对象
		Message
			* 消息对象
		MessageHandler
			* 消息处理器
		MessageChannel
			* 消息通道
		SubscribableChannel
			*  MessageChannel 与 MessageHandler订阅关系
		ExecutorSubscribableChannel:
			* 带有Executor的SubscribableChannel