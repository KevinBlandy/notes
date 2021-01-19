----------------------------
EndpointConfig				|
----------------------------
	# 该类是一个接口,表示配置
	# 该类在实际的应用中以单例的形式存在,每个 @Endpoint 仅仅只会有一个实例
	# 服务端的配置	
		ServerEndpointConfig

	# 客户端的配置
		ClientEndpointConfig
	
	# 方法
		List<Class<? extends Encoder>> getEncoders();
			* 获取编码器列表
		List<Class<? extends Decoder>> getDecoders();
			* 获取解码器列表
		Map<String, Object> getUserProperties();
			* 获取一个开发人员对象的属性map,可以理解为所有连接的Session域,可以存放一些东西

----------------------------
ServerEndpointConfig		|
----------------------------
	# 服务器端的配置
	# 比较重要的一点就是,用来作为编程式端点的URI配置
		* ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(Xxxxx.class,"/websocket/echoProgrammatic").build()

	# 在两种开发方式中的使用(获取)
		1,注解式开发
			* 直接把在任意的生命周期方法(一般都是 @Onopen ,连接打开就完成配置)形参中声明该对象(传递接口)EndpointConfig,就可以使用

		2,编程式开发
			* 在 ServerApplicationConfig 实现类的 getEndpointConfigs 方法中构造使用
			* Endpoint 的 onMessage 已经传递了该参数

	# API
		serverEndpointConfig.getPath();
			* 获取端点的路径 ,也就是 @ServerEndpoint 的value值

        serverEndpointConfig.getEndpointClass();
			* 获取端点类

        serverEndpointConfig.getSubprotocols();
			* 获取子协议列表

        serverEndpointConfig.getExtensions();
			* 获取端点支持的扩展列表

        serverEndpointConfig.getConfigurator();
			* 获取 ServerEndpointConfig.Configurator(该类定义了客户端与服务端如何握手) 配置类

	
		
	# ServerEndpointConfig.Configurator
		# 该类定义了客户端如何与服务端进行握手
		# 如果没有显示的给应用进行配置,则会创建默认的
			* ServerEndpointConfig.Configurator
			* 可以自己去实现该类,进行一些特点的配置
		# 注解式配置
			@ServerEndpoint(configurator=...)
				* public Class<? extends ServerEndpointConfig.Configurator> configurator() default ServerEndpointConfig.Configurator.class;)
		# 编程式配置
			ServerEndpointConfig.Builder.create(Xxx.class, "/socket").configurator(new ServerEndpointConfig.Configurator());

		# 方法
			public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
				return this.getContainerDefaultConfigurator().getNegotiatedSubprotocol(supported, requested);
			} 
				* 第一个参数,是当前服务端支持的子协议列表
				* 第二个参数,是当前客户端发起握手请求时支持的子协议列表
				* 如果没有重写该方法,websocket会在 supported 与 requested 中选一个都支持的子协议,如果一个都没,返回空字符串
				* 如果有多个相同的子协议,则客户端谁排在前,就选谁
			
			public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
				return this.getContainerDefaultConfigurator().getNegotiatedExtensions(installed, requested);
			}
				* 跟上面差不多,在握手期间.会调用该方法来确定使用websocket的扩展
				* 如果没有重写该方法,会在客户端请求的扩展列表,和服务端已经安装的服务扩展中选择共有的
				* 以请求的扩展列表顺序为准,扩展的返回顺序很重要

			public boolean checkOrigin(String originHeaderValue) {
				return this.getContainerDefaultConfigurator().checkOrigin(originHeaderValue);
			}
				* 所有的浏览器客户端在和服务端进行请求握手的时候,都会携带HTTP请求头,用于判断客户端是否来自HTTP页面
				* 一般不用管它
			
			public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
				return this.getContainerDefaultConfigurator().getEndpointInstance(endpointClass);
			} 
				* 获取到Endpoint端点实例
				* 该方法会返回一个新的 Endpoint 实例
			
			public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
				// nothing.
			}
				* '最牛逼的覆写了
				* 该方法可以完整的读取客户端发起的握手请求
				* 也可以在响应给客户端之前,对这些数据进行完整的读写
