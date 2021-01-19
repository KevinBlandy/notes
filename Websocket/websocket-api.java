---------------------------
注解						|
---------------------------
	@ServerEndpoint
		# 表示当前类是一个 WebSocket 服务类
		# 对于每个WebSocket的连接,都会创建新的对象去维护(意味着,可以通过成员变量来保存一些东西,而不用担心并发问题)
		# 属性
			value
				* 映射路径,尽量以"/"k开头
			subprotocols
				* 子协议,是一个 String[]  的参数
			decoders
				* 解码器,把响应给客户端的对象,解析为消息
			encoders
				* 编码器,把客户端传递来的消息,解析为对象
			configurator
				* 该参数默认值为:ServerEndpointConfig.Configurator.class;
				* 可以是:ServerEndpointConfig.Configurator 的任意子类
			
	@OnMessage
		# 表示该方法为接收客户端消息的方法
		# 该方法的返回值,可以被作为响应给服务器的数据,也可以在该方法中调用特定的API来进行消息的推送
		# 属性
			public long maxMessageSize() default -1;
				* 消息的缓冲区大小

	@OnOpen
		# 该方法会在连接时触发

	@OnClose
	@OnError
	@PathParam
		# 可以注入URI模版参数的值,可以对基础数据类型进行自动的转换
		# demo
			@ServerEndpoint("/ws/{demo}")
			..
			@OnMessage
			public void onMessage(@PathParam("demo") Integer demoId){
				//可以把URI模版参数的值映射到形参
			}
			


	@ClientEndpoint



---------------------------
类库索引					|
---------------------------
	ServerApplicationConfig
	EndpointConfig
		|-ServerEndpointConfig
			ServerEndpointConfig.Builder
			ServerEndpointConfig.Configurator
		|-ClientEndpointConfig
			ClientEndpointConfig.Builder
			ClientEndpointConfig.Configurator
	Endpoint
	RemoteEndpoint
		|-RemoteEndpoint.Basic
		|-RemoteEndpoint.Async
	Session
	MessageHandler
		|-MessageHandler.Whole<T>
		|-MessageHandler.Partial<T>
	SendHandler
	CloseReason
	PongMessage
		|-WsPongMessage
	Encoder
		|-Text<T>
		|-TextStream<T>
		|-Binary<T>
		|-BinaryStream<T>
	Decoder
		|-Text<T>
		|-TextStream<T>
		|-Binary<T>
		|-BinaryStream<T>
	HandshakeRequest
	HandshakeResponse
	Extension
		|-Extension.Parameter

---------------------------
类库						|
---------------------------
	ServerApplicationConfig
		# 是一个接口,项目启动时,会自动启动而且执行其实现类,类似:ContentListenner
		# 是WebSocket的核心配置类
		# 可以用来过滤
		# 方法
			public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> scanned);
				* scanned中有,所有以接口形式存在(Endpoint)的WebSocket Class
				* 我们需要在这里进行过滤,以及配置他们的URI
				* 代码
					Set<ServerEndpointConfig> result = new HashSet<>();
					if (scanned.contains(EchoEndpoint.class)) {
						result.add(ServerEndpointConfig.Builder.create(EchoEndpoint.class,"/websocket/echoProgrammatic").build());
					}
					if (scanned.contains(DrawboardEndpoint.class)) {
						result.add(ServerEndpointConfig.Builder.create(DrawboardEndpoint.class,"/websocket/drawboard").build());
					}
					return result;

			Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned);
				* scanned中有,所有以注解形式(@ServerEndpoint)存在的WebSocket Class
				* 代码
					Set<Class<?>> results = new HashSet<>();
					for (Class<?> clazz : scanned) {
						if (clazz.getPackage().getName().startsWith("websocket.")) {
							results.add(clazz);
						}
					}
					return results;
		# 这俩返回值,才会确定当前系统中的所有 SocketServer
		
	EndpointConfig
		# 端点的一些配置信息 
		# 它与多客户端共享的端点相关联
		# 使用该类的信息与算法来配置端点
		# 它是一个单例,所有的端点实例只会有一个EndpointCongfig
		# 服务端的配置
			ServerEndpointConfig
				# 比较重要的一点就是,用来作为编程式端点的URI配置
				# ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(Xxxxx.class,"/websocket/echoProgrammatic").build()
				|-Builder
						create(Class clazz,String path);
							* 创建一个端点服务
							* clazz 为 Endpoint 的实现类,path表示映射的路径
						configurator(ServerEndpointConfig.Configurator configurator);
							* 创建一个 configurator,如果未创建,则会使用默认的
		# 客户端的配置
			ClientEndpointConfig
				# 

	Endpoint
		# 抽象类,应该被所有的 WebSocket 服务类实现
		# 方法
			public abstract void onOpen(Session session, EndpointConfig config);
				* 抽象方法,在连接打开的时候触发
			public void onClose(Session session, CloseReason closeReason) {
			}
				* 空实现方法,在关闭的时候触发

			public void onError(Session session, Throwable thr) {
			}
				* 空实现方法,在异常的时候触发
	
	RemoteEndpoint
		# 处理服务端(发送给客户端)的消息,一般仅仅只有一个,由session维护
		# 是一个接口,有一些方法.里面声明了俩接口,并且这俩接口都实现了 RemoteEndpoint 接口
		# 方法
			void setBatchingAllowed(boolean allowed) throws IOException;
			boolean getBatchingAllowed();
			void flushBatch() throws IOException;
			void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
				# 发送ping数据,最大125字节
			void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
				# 发送pong数据,最大125字节
		# RemoteEndpoint.Basic
			* 同步发送的 RemoteEndpoint
			* 执行这些方法的方法,要等待方法消息发送OK后,才会 return
			void sendText(String message);
				* 向客户端响应文本信息
			 void sendText(String partialMessage, boolean isLast);
				* 想客户端发送消息,但是这个消息是分片的,通过 isLast 值来确定当前消息是否是最后一个
			void sendBinary(ByteBuffer data);
				* 向客户端响应二进制数据
			void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException; 
				* 向客户端响应二进制数据,但是这个消息是分片的,通过 isLast 值来确定当前消息是否是最后一个
			Writer getSendWriter();
				* 获取客户端的writer
			OutputStream getSendStream();
				* 获取客户端的二进制Out流,完成消息写入后需要关闭流,一旦流关闭.消息就会被发送到客户端
			void sendObject(Object data);
				* 直接响应一个对象

		# RemoteEndpoint.Async
			* 异步发送的 RemoteEndpoint
			* 他们的方法仅仅初始化消息的发送行为,定义OK后,操作这些方法的方法就 return,而不会阻塞直到发送OK
	

	Session
		# 一个连接会话,当socket关闭的时候,它也会关闭
		# 方法
			String getId();
				* 获取当前Session的唯一ID值
				* '同一个浏览器打开多个相同页面id值不会相同,甚至.同一个页面,F5刷新后,ID值也会不同,也就是说连接不同了,F5之前的连接会被关闭掉'
				* '0-9','a-f','1a-1f','20-29','2a-2f','30-39','3a-3f'... ...
			RemoteEndpoint.Basic getBasicRemote();
				* 获取 RemoteEndpoint.Basic 对象
			RemoteEndpoint.Async getAsyncRemote();
				* 获取 RemoteEndpoint.Async 对象
			addMessageHandler(MessageHandler handler);
				* 添加一个messsage Handler,一般都是在编程式端点,连接打开(onopen)的时候添加
				* 只一种类型(二进制/文本/pong)的消息处理器,只能添加一个
			
	MessageHandler
		# 处理客户端(发送给服务端)的消息,可以有一个或者多个.由session维护
		# 该接口定义了编程式端点,接收入站消息时,注册其兴趣的所有方式.
		# 可以使用该接口来选择是接收文本信息,还是二进制信息
		# 也可以接收整个消息,或者是当消息到达时接收较小的片段(消息特别大的时候)
		# 该接口,内部是两个接口(都实现了MessageHandler),我们需要实现该接口中,的接口实例
			interface Whole<T> extends MessageHandler {
				void onMessage(T message);		//每次客户端消息到底的时候,都会调用该方法
			}
			interface Partial<T> extends MessageHandler {

				void onMessage(T partialMessage, boolean last);
			}
		# Demo
			new MessageHandler.Whole<String>(){		//处理文本数据
				public void onMessage(String message){
					System.out.println(message);
				}
			};
			new MessageHandler.Whole<ByteBuffer>(){	//处理二进制数据
				public void onMessage(ByteBuffer message){
					System.out.println(message);
				}
			};

	CloseReason
		#
	
	
	HandshakeRequest
		# 客户端请求握手时携带的信息对象
		# API
			HttpSession getHttpSession();
				* 获取HTTP的Session
			
	
		