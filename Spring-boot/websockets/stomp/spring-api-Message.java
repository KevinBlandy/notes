------------------------
Message
------------------------
	# 消息接口
		public interface Message<T> 
	
	# 方法
		T getPayload();
		MessageHeaders getHeaders();
	

------------------------
MessageBuilder
------------------------
	# 消息构建器
		public MessageBuilder<T> setHeaders(MessageHeaderAccessor accessor) 
		public MessageBuilder<T> setHeader(String headerName, @Nullable Object headerValue)
		public MessageBuilder<T> setHeaderIfAbsent(String headerName, Object headerValue)
		public MessageBuilder<T> removeHeaders(String... headerPatterns) 
		public MessageBuilder<T> removeHeader(String headerName) 
		public MessageBuilder<T> copyHeaders(@Nullable Map<String, ?> headersToCopy)
		public MessageBuilder<T> copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy)
		public MessageBuilder<T> setReplyChannel(MessageChannel replyChannel)
		public MessageBuilder<T> setReplyChannelName(String replyChannelName)
		public MessageBuilder<T> setErrorChannel(MessageChannel errorChannel)
		public MessageBuilder<T> setErrorChannelName(String errorChannelName)


		public Message<T> build() 

		public static <T> MessageBuilder<T> fromMessage(Message<T> message)
		public static <T> MessageBuilder<T> withPayload(T payload) 
		public static <T> Message<T> createMessage(@Nullable T payload, MessageHeaders messageHeaders) 
