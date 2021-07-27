-----------------------
MessageHeaders
-----------------------
	# 消息头封装
		public class MessageHeaders implements Map<String, Object>, Serializable
	

	public static final UUID ID_VALUE_NONE = new UUID(0,0);
	public static final String ID = "id";
	public static final String TIMESTAMP = "timestamp";
	public static final String CONTENT_TYPE = "contentType";
	public static final String REPLY_CHANNEL = "replyChannel";
	public static final String ERROR_CHANNEL = "errorChannel";

	public MessageHeaders(@Nullable Map<String, Object> headers)
	protected MessageHeaders(@Nullable Map<String, Object> headers, @Nullable UUID id, @Nullable Long timestamp) 
	protected Map<String, Object> getRawHeaders()
	protected static IdGenerator getIdGenerator() 
	public UUID getId()
	public Long getTimestamp() 
	public Object getReplyChannel() 
	public Object getErrorChannel()
	public <T> T get(Object key, Class<T> type) 

