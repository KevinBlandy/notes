----------------------------------------
websockets 配置
----------------------------------------
	# 实现WebSocketConfigurer配置类，添加端点方法会返回 WebSocketHandlerRegistration 对象
		public interface WebSocketHandlerRegistry {
			WebSocketHandlerRegistration addHandler(WebSocketHandler webSocketHandler, String... paths);
		}

	
	# WebSocketHandlerRegistration
		WebSocketHandlerRegistration addHandler(WebSocketHandler handler, String... paths);
			* 添加一个或者多个handler

		WebSocketHandlerRegistration setHandshakeHandler(HandshakeHandler handshakeHandler);
			* 添加握手处理器
		
		WebSocketHandlerRegistration addInterceptors(HandshakeInterceptor... interceptors);
			* 添加拦截器
		
		WebSocketHandlerRegistration setAllowedOrigins(String... origins);
			* 设置允许的域

		SockJsServiceRegistration withSockJS();
			* 支持 socketJs


		
		
----------------------------------------
websockets HandshakeInterceptor
----------------------------------------
	# HandshakeInterceptor 握手拦截器
	
	# 抽象方法
		boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception;
			* 在握手处理之前执行，beforeHandshake 返回 false, 会终止握手

		void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception);
	
	# 在握手之前执行, 可以用于一些处理, 校验等等
	# 系统预定义的 Interceptor
		HttpSessionHandshakeInterceptor
			* 用于把httpSession绑定到WebSocketSession的拦截器
		
		OriginHandshakeInterceptor
			* 用于限制客户端来源的拦截器

----------------------------------------
websockets HandshakeHandler
----------------------------------------
	# HandshakeHandler 握手处理器
		HandshakeHandler
			|-AbstractHandshakeHandler
			|-WebSocketHandlerDecorator

	# 抽象方法
		boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException;
			* 返回握手是否成功


