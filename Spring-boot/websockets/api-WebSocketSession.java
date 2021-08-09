-----------------------------
WebSocketSession
-----------------------------
	# session�Ự�ӿ� WebSocketSession extends Closeable
		WebSocketSession
			|-AbstractClientSockJsSession
				|-WebSocketClientSockJsSession
				|-XhrClientSockJsSession
			|-WebSocketSessionDecorator
				|-ConcurrentWebSocketSessionDecorator
			|-NativeWebSocketSession
				|-AbstractWebSocketSession
					|-JettyWebSocketSession
					|-StandardWebSocketSession
				|-WebSocketClientSockJsSession
				|-WebSocketServerSockJsSession
			|-SockJsSession
				|-AbstractSockJsSession
					|-AbstractHttpSockJsSession
						|-PollingSockJsSession
						|-StreamingSockJsSession
					|-WebSocketServerSockJsSession
	
	# ���󷽷�
		String getId();

		@Nullable
		URI getUri();

		HttpHeaders getHandshakeHeaders();

		Map<String, Object> getAttributes();
			* һ�����Ӽ����Map�洢�����Դ洢�ܶණ��
			* ���������ֵ�ʱ������ݴ洢������

		@Nullable
		Principal getPrincipal();

		@Nullable
		InetSocketAddress getLocalAddress();

		@Nullable
		InetSocketAddress getRemoteAddress();

		@Nullable
		String getAcceptedProtocol();

		void setTextMessageSizeLimit(int messageSizeLimit);
		int getTextMessageSizeLimit();
		void setBinaryMessageSizeLimit(int messageSizeLimit);
		int getBinaryMessageSizeLimit();
			* ����/��ȡ �����ı�/��������Ϣ��С

		List<WebSocketExtension> getExtensions();

		void sendMessage(WebSocketMessage<?> message) throws IOException;

		boolean isOpen();

		@Override
		void close() throws IOException;

		void close(CloseStatus status) throws IOException;
	

	# ת��SessionΪԭʼ��WebSocketSession
		javax.websocket.Session webSocketSession = ((StandardWebSocketSession) session).getNativeSession();
