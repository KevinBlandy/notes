-----------------------------
WebSocketSession
-----------------------------
	# session会话接口 WebSocketSession extends Closeable
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
	
	# 抽象方法
		String getId();

		@Nullable
		URI getUri();

		HttpHeaders getHandshakeHeaders();

		Map<String, Object> getAttributes();
			* 一个连接级别的Map存储，可以存储很多东西
			* 可以在握手的时候把数据存储在这里

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
			* 设置/读取 最大的文本/二进制消息大小

		List<WebSocketExtension> getExtensions();

		void sendMessage(WebSocketMessage<?> message) throws IOException;

		boolean isOpen();

		@Override
		void close() throws IOException;

		void close(CloseStatus status) throws IOException;
	

	# 转换Session为原始的WebSocketSession
		javax.websocket.Session webSocketSession = ((StandardWebSocketSession) session).getNativeSession();
