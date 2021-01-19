------------------------------------
WebSocketHandler
------------------------------------
	# 接口方法
		void afterConnectionEstablished(WebSocketSession session) throws Exception;
			* 链接打开时执行

		void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception;
			* 消息处理

		void handleTransportError(WebSocketSession session, Throwable exception) throws Exception;
			* 传输异常

		void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception;
			* 链接关闭后执行

		boolean supportsPartialMessages();
			* 是否支持分片消息
			* 如果返回 true, 会导致 WebSocketSession 和 ServletServerContainerFactoryBean 中对于消息大小的限制失效
				void setTextMessageSizeLimit(int messageSizeLimit);
				void setBinaryMessageSizeLimit(int messageSizeLimit);

				void setMaxTextMessageBufferSize(8192);
				void setMaxBinaryMessageBufferSize(8192);
	
	# 核心关系
		WebSocketHandler
			|-AbstractWebSocketHandler
				|-BinaryWebSocketHandler
				|-TextWebSocketHandler
					|-SockJsWebSocketHandler
			|-PerConnectionWebSocketHandler
			|-SubProtocolWebSocketHandler
			|-WebSocketHandlerDecorator
				|-ExceptionWebSocketHandlerDecorator
				|-LoggingWebSocketHandlerDecorator

------------------------------------
WebSocketHandlerDecorator
------------------------------------
	# 它是一个 wrapper 类，通过构造函数传递一个真正干活儿的 WebSocketHandler
		public WebSocketHandlerDecorator(WebSocketHandler delegate)
	
	
	# 实例方法
		public WebSocketHandler getDelegate()
			* 获取到真正干活儿的handler
			* 源码很简单

		WebSocketHandler getLastHandler()
			* 获取到最后的代理对象
			* 层层剥丝, 剔除所以外面的 WebSocketHandlerDecorator 
			* 源码很简单


	# 静态方法
		public static WebSocketHandler unwrap(WebSocketHandler handler)
			* 尝试对一个 WebSocketHandler 进行剥丝，返回它真正干活儿的handler
			* 源码很简单

	
	
	# 系统提供的实现
		ExceptionWebSocketHandlerDecorator
			* 所有的事件方法都被 try catch 
			* 除了 afterConnectionClosed 方法以外。其他的方法，都会在 catch 中尝试关闭session，响应：CloseStatus.SERVER_ERROR

		LoggingWebSocketHandlerDecorator
			* 记录日志。各种事件日志
		
