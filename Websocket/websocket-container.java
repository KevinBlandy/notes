-----------------
Container
-----------------
	# 通过 ContainerProvider SPI获取到Container
		ContainerProvider
			public static WebSocketContainer getWebSocketContainer()
				* 静态方法，从SPI机制, 获取到 Container

			protected abstract WebSocketContainer getContainer();
	
	# WebSocketContainer
		long getDefaultAsyncSendTimeout();
		void setAsyncSendTimeout(long timeoutmillis);

		Session connectToServer(Object annotatedEndpointInstance, URI path) throws DeploymentException, IOException;             
		Session connectToServer(Class<?> annotatedEndpointClass, URI path) throws DeploymentException, IOException;
		Session connectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException;
		Session connectToServer(Class<? extends Endpoint> endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException;
			* 链接到指定的服务器

		void setDefaultMaxSessionIdleTimeout(long timeout);
		long getDefaultMaxSessionIdleTimeout();
		
		int getDefaultMaxBinaryMessageBufferSize();
		void setDefaultMaxBinaryMessageBufferSize(int max);

		int getDefaultMaxTextMessageBufferSize();
		void setDefaultMaxTextMessageBufferSize(int max);

		Set<Extension> getInstalledExtensions();
	

	# ServerContainer extends WebSocketContainer
		* 专门给服务器端提供的Container

		public void addEndpoint(Class<?> endpointClass) throws DeploymentException;
		public void addEndpoint(ServerEndpointConfig serverConfig) throws DeploymentException;
	


	
