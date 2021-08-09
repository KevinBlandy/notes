----------------------------------------
websockets 配置
----------------------------------------
	# 实现 WebSocketConfigurer 配置类，添加端点方法会返回 WebSocketHandlerRegistration 对象
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
			* 设置允许的域，必须是明确的域，不能是通配符
		
		WebSocketHandlerRegistration setAllowedOriginPatterns(String... originPatterns);
			* 设置允许的域，可以是通配符

		SockJsServiceRegistration withSockJS();
			* 支持 socketJs


		
		
----------------------------------------
websockets HandshakeInterceptor
----------------------------------------
	# HandshakeInterceptor 握手拦截器
	
	# 抽象方法
		boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception;
			* 在握手处理之前执行，beforeHandshake 返回 false, 会终止握手
			* request/response 可以转换为 
				ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
				ServletServerHttpResponse response = (ServletServerHttpResponse) serverHttpResponse;

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
				|-DefaultHandshakeHandler
			|-WebSocketTransportHandler
				* 基于WebSocket的TransportHandler。使用SockJsWebSocketHandler和WebSocketServerSockJsSession来添加SockJS处理。
				* 还实现了HandshakeHandler以支持SockJS URL "/websocket"的原始WebSocket通信。
		
		
		* request/response 可以转换为 
				ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
				ServletServerHttpResponse response = (ServletServerHttpResponse) serverHttpResponse;

	# AbstractHandshakeHandler的几个核心方法
		protected void handleInvalidUpgradeHeader(ServerHttpRequest request, ServerHttpResponse response) throws IOException
			* 可以处理，“直接在浏览器GET”请求端点导致的异常

		protected void handleInvalidConnectHeader(ServerHttpRequest request, ServerHttpResponse response) throws IOException
			* 客户端的非法链接处理

		protected boolean isWebSocketVersionSupported(WebSocketHttpHeaders httpHeaders)
		protected String[] getSupportedVersions()
		protected void handleWebSocketVersionNotSupported(ServerHttpRequest request, ServerHttpResponse response)
		protected boolean isValidOrigin(ServerHttpRequest request)
		protected String selectProtocol(List<String> requestedProtocols, WebSocketHandler webSocketHandler) 
		protected final List<String> determineHandlerSupportedProtocols(WebSocketHandler handler)
		protected List<WebSocketExtension> filterRequestedExtensions(ServerHttpRequest request, List<WebSocketExtension> requestedExtensions, List<WebSocketExtension> supportedExtensions) 
		protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) 

	

	# 抽象方法
		boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException;
			* 返回握手是否成功

	
