----------------------------
DeliveryOptions
----------------------------
	# 发布消息的配置 class DeliveryOptions 

----------------------------
构造
----------------------------
	public DeliveryOptions()
	public DeliveryOptions(DeliveryOptions other)
	public DeliveryOptions(JsonObject json)


----------------------------
实例
----------------------------
	public JsonObject toJson()

	public long getSendTimeout() 
	public DeliveryOptions setSendTimeout(long timeout)
		* 发送带有应答处理器的消息时，可以设置超时时间
		* 如果在这个时间之内没有收到应答，则会以“失败的结果”为参数调用应答处理器。默认超时是 30 秒。

	public String getCodecName()
	public DeliveryOptions setCodecName(String codecName)
		* 设置/读取，这条消息实用的 Codec 名称
		* 如果不设置，则使用默认的

	public DeliveryOptions addHeader(String key, String value)
	public DeliveryOptions setHeaders(MultiMap headers)
		* 添加请求Header

	public MultiMap getHeaders()
	private void checkHeaders()
	public boolean isLocalOnly()
	public DeliveryOptions setLocalOnly(boolean localOnly)
	public TracingPolicy getTracingPolicy()
	public DeliveryOptions setTracingPolicy(TracingPolicy tracingPolicy)


----------------------------
静态
----------------------------
	public static final long DEFAULT_TIMEOUT = 30 * 1000;
	public static final boolean DEFAULT_LOCAL_ONLY = false;
	public static final TracingPolicy DEFAULT_TRACING_POLICY = TracingPolicy.PROPAGATE;