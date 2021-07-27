--------------------------
MessageHeaderAccessor
--------------------------
	# 请求头的工具类
		public class MessageHeaderAccessor
	
	# 方法
		public MessageHeaderAccessor()
		public MessageHeaderAccessor(@Nullable Message<?> message) 

		protected MessageHeaderAccessor createAccessor(Message<?> message)
		public void setLeaveMutable(boolean leaveMutable) 
		public void setImmutable() 
		public boolean isMutable() 
		protected void setModified(boolean modified) 
		public boolean isModified() 
		public MessageHeaders getMessageHeaders()
		public MessageHeaders toMessageHeaders()
		public Map<String, Object> toMap() 


		public Object getHeader(String headerName) 
		public void setHeader(String name, @Nullable Object value) 
		
		protected void verifyType(@Nullable String headerName, @Nullable Object headerValue)
		public void setHeaderIfAbsent(String name, Object value) 
		public void removeHeader(String headerName)
		public void removeHeaders(String... headerPatterns) 
		public void copyHeaders(@Nullable Map<String, ?> headersToCopy)
		public void copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy)
		protected boolean isReadOnly(String headerName)
		
		public UUID getId() 
		public Long getTimestamp()
		public void setContentType(MimeType contentType) 
		public MimeType getContentType() 
		public void setReplyChannelName(String replyChannelName) 
		public void setReplyChannel(MessageChannel replyChannel)
		public Object getReplyChannel() 
		public void setErrorChannelName(String errorChannelName) 
		public void setErrorChannel(MessageChannel errorChannel) 
		public Object getErrorChannel() 
		public String getShortLogMessage(Object payload)
		public String getDetailedLogMessage(@Nullable Object payload) 
		protected String getShortPayloadLogMessage(Object payload)
		protected String getDetailedPayloadLogMessage(@Nullable Object payload)
		protected boolean isReadableContentType() 

		public static MessageHeaderAccessor getAccessor(Message<?> message)

		public static <T extends MessageHeaderAccessor> T getAccessor(Message<?> message, @Nullable Class<T> requiredType)
		public static <T extends MessageHeaderAccessor> T getAccessor(MessageHeaders messageHeaders, @Nullable Class<T> requiredType)
			* 获取指定类型的 MessageHeaderAccessor 
				NativeMessageHeaderAccessor
				SimpMessageHeaderAccessor
				StompHeaderAccessor


		public static MessageHeaderAccessor getMutableAccessor(Message<?> message)


--------------------------
NativeMessageHeaderAccessor
--------------------------
	# 本地消息Header访问器
		public class NativeMessageHeaderAccessor extends MessageHeaderAccessor

	# 方法
		protected Map<String, List<String>> getNativeHeaders()
	
--------------------------
SimpMessageHeaderAccessor
--------------------------
	# Simp消息Header访问
		public class SimpMessageHeaderAccessor extends NativeMessageHeaderAccessor
	


--------------------------
StompHeaderAccessor
--------------------------
	# Stomp消息Header访问器
		public class StompHeaderAccessor extends SimpMessageHeaderAccessor
	
	# 方法
		
		public void setMessageTypeIfNotSet(SimpMessageType messageType)
		public SimpMessageType getMessageType()	

		public void setSessionAttributes(@Nullable Map<String, Object> attributes)
		public Map<String, Object> getSessionAttributes() 
			* Session 存储
		
		public void setUser(@Nullable Principal principal)
		public Principal getUser()
			* 获取用户信息
		
		public StompCommand getCommand()
			* 获取命令

		public boolean isHeartbeat()
			* 判断是否是心跳

		public long[] getHeartbeat() 
			* 获取心跳间隔
	

	# Header KEY
		public static final String DESTINATION_HEADER = "simpDestination";
		public static final String MESSAGE_TYPE_HEADER = "simpMessageType";
		public static final String SESSION_ID_HEADER = "simpSessionId";
		public static final String SESSION_ATTRIBUTES = "simpSessionAttributes";
		public static final String SUBSCRIPTION_ID_HEADER = "simpSubscriptionId";
		public static final String USER_HEADER = "simpUser";
		public static final String CONNECT_MESSAGE_HEADER = "simpConnectMessage";
		public static final String DISCONNECT_MESSAGE_HEADER = "simpDisconnectMessage";
		public static final String HEART_BEAT_HEADER = "simpHeartbeat";
		
