----------------------------
Sesssion					|
----------------------------
	# 每个新的连接都会创建新的Session
	# 每个Session在当前的Endpoint都有一个唯一的ID值
		* 可以通过 getId();来获取
		* '同一个浏览器打开多个相同页面id值不会相同,甚至.同一个页面,F5刷新后,ID值也会不同,也就是说连接不同了,F5之前的连接会被关闭掉'
		* '0-9','a-f','1a-1f','20-29','2a-2f','30-39','3a-3f'... ...

----------------------------
Sesssion-MessaegHandler		|
----------------------------
	# 显然是针对于编程式开发,注解式开发的MessageHandler是隐式的
	# 添加MessageHandler 到当前Session
		public void addMessageHandler(MessageHandler handler) throws IllegalStateException;
			* 添加一个消息处理器,可以是分片消息或者非分片消息
		public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Whole<T> handler);
			* 添加一个非分片消息处理器
		public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Partial<T> handler);
			* 添加一个消息分片处理器

	# 获取所有的MessageHandler
		Set<MessageHandler> getMessageHandlers();
		
	# 移除一个MessageHandler
		void removeMessageHandler(MessageHandler handler);

----------------------------
Sesssion-RemoteEndpoint		|
----------------------------
	RemoteEndpoint.Basic getBasicRemote();
		* 获取 RemoteEndpoint.Basic 对象

	RemoteEndpoint.Async getAsyncRemote();
		* 获取 RemoteEndpoint.Async 对象

----------------------------
Sesssion-Session管理		|
----------------------------
	boolean session.isOpen();
		* 该方法判断当前连接是否已经关闭
	Set<Session> getOpenSessions();
		* 获取当前端点所有已经打开的所有连接
	void close() throws IOException;
		* 关闭session
		* 在关闭了Session后,调用大多数的SessionAPI都会抛出异常
	void close(CloseReason closeReason) throws IOException;
		* 关闭session,并且带有原因,等描述对象

----------------------------
Sesssion-属性				|
----------------------------
	int session.getMaxBinaryMessageBufferSize();
		* 传入的二进制消息的最大长度
	int session.getMaxTextMessageBufferSize();
		* 传入的文本消息的最大长度
	long session.getMaxIdleTimeout();
		* 最大空闲时间,超时后Socket会被关闭
	List<Extension> session.getNegotiatedExtensions();
		* 在握手期间协商好的WebSocket扩展列表
		* '该值不可修改'
	String session.getNegotiatedSubprotocol();
		* 在握手期间协商好的WebSocket子协议列表
		* '该值不可修改'
	String session.getProtocolVersion();
		* 使用的WebSocket网络协议版本
		* '该值不可修改'
	Principal session.getUserPrincipal();
		* 已经认证端用户的 Java UserPrincipal
		* '该值不可修改'
	boolean session.isSecure();
		* 底层连接是否加密
	Map<String, Object> getUserProperties();
		* 底层连接中存放新的map

	
----------------------------
Sesssion-路径属性			|
----------------------------
	URI getRequestURI();
		* 获取完整的相对于WEB应用上下文根的相对路径
		* @Endpoint(value="/test");
		* ws:/ip/项目名/test?k=v
		* url = "/项目名/test?k=v"
	
	Map<String, String> getPathParameters();
		* 获取模版路径的映射值
		* @Endpoint(value="/test/{k1}/{k2}");
		* ws:/ip/test/v1/v2							
		* map = [k1:v1,k2:v2]
		* 注解式开发的话,模版值可以根据 @PathParam 注解来获取
	
	String getQueryString();
		* 获取请求参数的部分,就是截取问号后面的字符串
		* @Endpoint(value="/test");
		* ws:/ip/test?k=v
		* string = "k=v";
	
	Map<String, List<String>> getRequestParameterMap();
		* 获取请求的参数
		* 其实就是上面那个进行的封装.每个key也许会有多个参数,所以value是 List 形式

	

