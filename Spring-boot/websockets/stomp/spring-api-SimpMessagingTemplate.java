---------------------------
SimpMessagingTemplate
---------------------------
	# ���ڷ�����Ϣ�Ĺ����࣬����ע���ȡ��

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
		* ����Ϣ���͸�ָ�����û�����Ե�

	protected Map<String, Object> processHeadersToSend(@Nullable Map<String, Object> headers)


---------------------------
MessagePostProcessor
---------------------------
	# һ����Ϣ����ӿ�
		@FunctionalInterface
		public interface MessagePostProcessor {
			Message<?> postProcessMessage(Message<?> message);
		}