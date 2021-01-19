-------------------------
服务器优雅的启动		 |
-------------------------
	EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
	EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();
	
	ServerBootstrap serverBootstrap = new ServerBootstrap();
	// 省略N多配置
	ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(1024)).sync();
	// 在关闭回调里面处理堆积消息以及释放线程池资源
	channelFuture.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
		@Override
		public void operationComplete(Future<? super Void> future) throws Exception {
			System.out.println("服务器停止...");
			bossEventLoopGroup.shutdownGracefully();
			workerEventLoopGroup.shutdownGracefully();
		}
	});
	
	// 线程不阻塞,继续往下执行
	System.out.println("服务器启动成功....");


-------------------------
服务器优雅的退出		 |
-------------------------
	