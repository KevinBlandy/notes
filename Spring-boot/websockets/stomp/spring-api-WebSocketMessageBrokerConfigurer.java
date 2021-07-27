-----------------------------
WebSocketMessageBrokerConfigurer
-----------------------------
	# 配置接口类
	
	# 抽象方法
		void registerStompEndpoints(StompEndpointRegistry registry)
		void configureWebSocketTransport(WebSocketTransportRegistration registry)

		void configureClientInboundChannel(ChannelRegistration registration)
		void configureClientOutboundChannel(ChannelRegistration registration)
			* 可以设置消息出站，入站拦截器

		void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
		void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers)
		boolean configureMessageConverters(List<MessageConverter> messageConverters) 
		void configureMessageBroker(MessageBrokerRegistry registry)
	

-----------------------------
ChannelRegistration 
-----------------------------
	# public class ChannelRegistration
		public TaskExecutorRegistration taskExecutor()
		public TaskExecutorRegistration(ThreadPoolTaskExecutor taskExecutor)
			* 线程池执行器，taskExecutor 主要就是封装了线程池饿几个参数

		public ChannelRegistration interceptors(ChannelInterceptor... interceptors) 
			* 设置消息拦截器
		
		protected boolean hasTaskExecutor()
		protected boolean hasInterceptors()
		protected List<ChannelInterceptor> getInterceptors()

-----------------------------
ChannelInterceptor
-----------------------------
	# public interface ChannelInterceptor 
		Message<?> preSend(Message<?> message, MessageChannel channel)
			* 消息发送之前处理器

		void postSend(Message<?> message, MessageChannel channel, boolean sent)
			* 消息发送之后处理

		void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex)
			* 消息发送完毕后处理

		boolean preReceive(MessageChannel channel)
			* 消息接收之前处理

		Message<?> postReceive(Message<?> message, MessageChannel channel)
			* 消息接收之后处理

		void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex)
			* 消息接收完毕之后处理
