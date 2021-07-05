---------------------------------------
BasicProperties
---------------------------------------
	# public static class BasicProperties extends com.rabbitmq.client.impl.AMQBasicProperties
	
	# 消息的属性，可以通过Builder模式创建

---------------------------------------
static
---------------------------------------


---------------------------------------
this
---------------------------------------
	public BasicProperties()
	public BasicProperties(DataInputStream in)
	public BasicProperties(
            String contentType,
				* 消息body的类型
            String contentEncoding,
            Map<String,Object> headers,
				* 带有消息header
            Integer deliveryMode,
				* 消息投递模式
            Integer priority,
				* 消息优先级
            String correlationId,
            String replyTo,
            String expiration,	
				* 过期时间，单位是毫秒
            String messageId,
            Date timestamp,
            String type,
            String userId,
            String appId,
            String clusterId)


	public boolean equals(Object arg0)
	public int hashCode()
	public Integer getPriority()
	public String getType()
	public String getClassName()
	public String getContentType()
	public String getContentEncoding()
	public String getExpiration()
	public Map getHeaders()
	public Date getTimestamp()
	public void appendPropertyDebugStringTo(StringBuilder arg0)
	public void writePropertiesTo(ContentHeaderPropertyWriter arg0)
	public Builder builder()
	public int getClassId()
	public String getCorrelationId()
	public String getReplyTo()
	public Integer getDeliveryMode()
	public String getClusterId()
	public String getMessageId()
	public String getAppId()
	public String getUserId()
