-----------------------------
WebSocketMessageBrokerConfigurer
-----------------------------
	# 配置接口类
	
	# 抽象方法
		void registerStompEndpoints(StompEndpointRegistry registry)
		void configureWebSocketTransport(WebSocketTransportRegistration registry)
		void configureClientInboundChannel(ChannelRegistration registration)
		void configureClientOutboundChannel(ChannelRegistration registration)
		void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
		void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers)
		boolean configureMessageConverters(List<MessageConverter> messageConverters) 
		void configureMessageBroker(MessageBrokerRegistry registry)
	

		