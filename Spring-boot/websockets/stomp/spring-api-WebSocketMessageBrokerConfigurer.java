-----------------------------
WebSocketMessageBrokerConfigurer
-----------------------------
	# ���ýӿ���
	
	# ���󷽷�
		void registerStompEndpoints(StompEndpointRegistry registry)
		void configureWebSocketTransport(WebSocketTransportRegistration registry)

		void configureClientInboundChannel(ChannelRegistration registration)
		void configureClientOutboundChannel(ChannelRegistration registration)
			* ����������Ϣ��վ����վ������

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
			* �̳߳�ִ������taskExecutor ��Ҫ���Ƿ�װ���̳߳ض���������

		public ChannelRegistration interceptors(ChannelInterceptor... interceptors) 
			* ������Ϣ������
		
		protected boolean hasTaskExecutor()
		protected boolean hasInterceptors()
		protected List<ChannelInterceptor> getInterceptors()

-----------------------------
ChannelInterceptor
-----------------------------
	# public interface ChannelInterceptor 
		Message<?> preSend(Message<?> message, MessageChannel channel)
			* ��Ϣ����֮ǰ������

		void postSend(Message<?> message, MessageChannel channel, boolean sent)
			* ��Ϣ����֮����

		void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex)
			* ��Ϣ������Ϻ���

		boolean preReceive(MessageChannel channel)
			* ��Ϣ����֮ǰ����

		Message<?> postReceive(Message<?> message, MessageChannel channel)
			* ��Ϣ����֮����

		void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex)
			* ��Ϣ�������֮����
