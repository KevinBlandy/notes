------------------------
Bootstrap				|
-----------------------
	# 客户端一般配置代码
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup);
			bootstrap.channel(NioSocketChannel.class);
			// 连接的远端地址
			bootstrap.remoteAddress(new InetSocketAddress("127.0.0.1", 1024));
			bootstrap.option(ChannelOption.SO_REUSEADDR, true);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					
				}
			});
			// 执行连接
			ChannelFuture channelFuture = bootstrap.connect().sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}

		* 没有childOption和childHandler配置
		* channel为:NioSocketChannel