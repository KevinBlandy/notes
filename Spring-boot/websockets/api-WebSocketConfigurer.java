----------------------------------------
websockets ����
----------------------------------------
	# ʵ�� WebSocketConfigurer �����࣬��Ӷ˵㷽���᷵�� WebSocketHandlerRegistration ����
		public interface WebSocketHandlerRegistry {
			WebSocketHandlerRegistration addHandler(WebSocketHandler webSocketHandler, String... paths);
		}

	
	# WebSocketHandlerRegistration
		WebSocketHandlerRegistration addHandler(WebSocketHandler handler, String... paths);
			* ���һ�����߶��handler

		WebSocketHandlerRegistration setHandshakeHandler(HandshakeHandler handshakeHandler);
			* ������ִ�����
		
		WebSocketHandlerRegistration addInterceptors(HandshakeInterceptor... interceptors);
			* ���������
		
		WebSocketHandlerRegistration setAllowedOrigins(String... origins);
			* ����������򣬱�������ȷ���򣬲�����ͨ���
		
		WebSocketHandlerRegistration setAllowedOriginPatterns(String... originPatterns);
			* ����������򣬿�����ͨ���

		SockJsServiceRegistration withSockJS();
			* ֧�� socketJs


		
		
----------------------------------------
websockets HandshakeInterceptor
----------------------------------------
	# HandshakeInterceptor ����������
	
	# ���󷽷�
		boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception;
			* �����ִ���֮ǰִ�У�beforeHandshake ���� false, ����ֹ����
			* request/response ����ת��Ϊ 
				ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
				ServletServerHttpResponse response = (ServletServerHttpResponse) serverHttpResponse;

		void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception);
	
	# ������֮ǰִ��, ��������һЩ����, У��ȵ�
	# ϵͳԤ����� Interceptor
		HttpSessionHandshakeInterceptor
			* ���ڰ�httpSession�󶨵�WebSocketSession��������
		
		OriginHandshakeInterceptor
			* �������ƿͻ�����Դ��������

----------------------------------------
websockets HandshakeHandler
----------------------------------------
	# HandshakeHandler ���ִ�����
		HandshakeHandler
			|-AbstractHandshakeHandler
				|-DefaultHandshakeHandler
			|-WebSocketTransportHandler
				* ����WebSocket��TransportHandler��ʹ��SockJsWebSocketHandler��WebSocketServerSockJsSession�����SockJS����
				* ��ʵ����HandshakeHandler��֧��SockJS URL "/websocket"��ԭʼWebSocketͨ�š�
		
		
		* request/response ����ת��Ϊ 
				ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
				ServletServerHttpResponse response = (ServletServerHttpResponse) serverHttpResponse;

	# AbstractHandshakeHandler�ļ������ķ���
		protected void handleInvalidUpgradeHeader(ServerHttpRequest request, ServerHttpResponse response) throws IOException
			* ���Դ�����ֱ���������GET������˵㵼�µ��쳣

		protected void handleInvalidConnectHeader(ServerHttpRequest request, ServerHttpResponse response) throws IOException
			* �ͻ��˵ķǷ����Ӵ���

		protected boolean isWebSocketVersionSupported(WebSocketHttpHeaders httpHeaders)
		protected String[] getSupportedVersions()
		protected void handleWebSocketVersionNotSupported(ServerHttpRequest request, ServerHttpResponse response)
		protected boolean isValidOrigin(ServerHttpRequest request)
		protected String selectProtocol(List<String> requestedProtocols, WebSocketHandler webSocketHandler) 
		protected final List<String> determineHandlerSupportedProtocols(WebSocketHandler handler)
		protected List<WebSocketExtension> filterRequestedExtensions(ServerHttpRequest request, List<WebSocketExtension> requestedExtensions, List<WebSocketExtension> supportedExtensions) 
		protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) 

	

	# ���󷽷�
		boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException;
			* ���������Ƿ�ɹ�

	
