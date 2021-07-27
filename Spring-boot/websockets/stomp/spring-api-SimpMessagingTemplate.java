---------------------------
SimpMessagingTemplate
---------------------------
	# 用于发送消息的工具类，可以注入获取到

---------------------------
SimpMessagingTemplate
---------------------------
	public SimpMessagingTemplate(MessageChannel messageChannel) 
	public MessageChannel getMessageChannel() 

	public void setUserDestinationPrefix(String prefix)
	public String getUserDestinationPrefix() 
	public void setSendTimeout(long sendTimeout)
	public long getSendTimeout()
	public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer)
	public MessageHeaderInitializer getHeaderInitializer()
	public void send(Message<?> message) 

	protected void doSend(String destination, Message<?> message) 

	public void convertAndSendToUser(String user, String destination, Object payload) throws MessagingException 
	public void convertAndSendToUser(String user, String destination, Object payload, @Nullable Map<String, Object> headers) throws MessagingException 
	public void convertAndSendToUser(String user, String destination, Object payload, @Nullable MessagePostProcessor postProcessor) throws MessagingException
	public void convertAndSendToUser(String user, String destination, Object payload, @Nullable Map<String, Object> headers, @Nullable MessagePostProcessor postProcessor)throws MessagingException 
		* 把消息发送给指定的用户，点对点

	protected Map<String, Object> processHeadersToSend(@Nullable Map<String, Object> headers)


---------------------------
MessagePostProcessor
---------------------------
	# 一个消息处理接口
		@FunctionalInterface
		public interface MessagePostProcessor {
			Message<?> postProcessMessage(Message<?> message);
		}