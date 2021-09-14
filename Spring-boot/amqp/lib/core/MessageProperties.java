----------------------
MessageProperties
----------------------
	# 消息属性类
		public class MessageProperties implements Serializable 
	
	# 方法
		public void setHeader(String key, Object value)
		public <T> T getHeader(String headerName)
		public Map<String, Object> getHeaders()

		public void setTimestamp(Date timestamp) 
		public Date getTimestamp() 

		public void setMessageId(String messageId)
		public String getMessageId()

		public void setUserId(String userId)
		public String getUserId()

		public String getReceivedUserId()
		public void setReceivedUserId(String receivedUserId)

		public void setAppId(String appId)
		public String getAppId() 

		public void setClusterId(String clusterId) 
		public String getClusterId() 

		public void setType(String type) 
		public String getType()

		public void setCorrelationId(String correlationId)
		public String getCorrelationId()

		public void setReplyTo(String replyTo) 
		public String getReplyTo() 

		public void setReplyToAddress(Address replyTo)
		public Address getReplyToAddress()

		public void setContentType(String contentType) 
		public String getContentType()

		public void setContentEncoding(String contentEncoding)
		public String getContentEncoding()

		public void setContentLength(long contentLength)
		public long getContentLength()

		protected final boolean isContentLengthSet()
		public void setDeliveryMode(MessageDeliveryMode deliveryMode) 

		public MessageDeliveryMode getDeliveryMode()
		public MessageDeliveryMode getReceivedDeliveryMode()

		public void setReceivedDeliveryMode(MessageDeliveryMode receivedDeliveryMode)
		public void setExpiration(String expiration)

		public String getExpiration()
		public void setPriority(Integer priority) 

		public Integer getPriority()
		public void setReceivedExchange(String receivedExchange)

		public String getReceivedExchange() 
		public void setReceivedRoutingKey(String receivedRoutingKey) 

		public String getReceivedRoutingKey() 
		public Integer getReceivedDelay()

		public void setReceivedDelay(Integer receivedDelay)
		public void setRedelivered(Boolean redelivered)

		public Boolean isRedelivered()
		public void setDeliveryTag(long deliveryTag)

		public long getDeliveryTag() 
		protected final boolean isDeliveryTagSet()

		public void setMessageCount(Integer messageCount)
		public Integer getMessageCount()

		public String getConsumerTag() 
		public void setConsumerTag(String consumerTag) 

		public String getConsumerQueue() 
		public void setConsumerQueue(String consumerQueue) 

		public Integer getDelay() 
		public void setDelay(Integer delay) 

		public boolean isFinalRetryForMessageWithNoId()
		public void setFinalRetryForMessageWithNoId(boolean finalRetryForMessageWithNoId)

		public long getPublishSequenceNumber()
		public void setPublishSequenceNumber(long publishSequenceNumber) 

		public Type getInferredArgumentType()
		public void setInferredArgumentType(Type inferredArgumentType)

		public Method getTargetMethod()
		public void setTargetMethod(Method targetMethod)

		public Object getTargetBean() 
		public void setTargetBean(Object targetBean)

		public boolean isLastInBatch() 
		public void setLastInBatch(boolean lastInBatch)

		public List<Map<String, ?>> getXDeathHeader() 
		

