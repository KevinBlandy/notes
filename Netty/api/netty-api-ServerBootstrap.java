-------------------------
ServerBootstrap			 |
-------------------------
	# ServerBootstrap extends AbstractBootstrap<ServerBootstrap, ServerChannel>
	# 构造函数
		public ServerBootstrap()


-------------------------
方法					 |
-------------------------
	ServerBootstrap handler(ChannelHandler handler)
		* 添加服务端的handler

	ServerBootstrap childHandler(ChannelHandler childHandler)
		* 设置一个或者多个客户端处理器
		* 一般可以设置 ChannelInitializer 实例,添加一个handler处理链
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override		// 唯一的抽象方法
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeServerHandler());	// 在此添加一个或者多个Handler处理
				}
			});

	<T> ServerBootstrap childOption(ChannelOption<T> childOption, T value)
		* 设置客户端连接的配置

	ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)
		* parentGroup处理连接事件
		* childGroup 处理IO事件
	
	ChannelFuture bind()
	ChannelFuture bind(int inetPort)
	ChannelFuture bind(String inetHost, int inetPort)
	ChannelFuture bind(InetAddress inetHost, int inetPort)
	ChannelFuture bind(SocketAddress localAddress)
		* 绑定操作		

	B channel(Class<? extends C> channelClass)
		* 设置io类型

	<T> B option(ChannelOption<T> option, T value)
		* 设置服务器端的属性

