-----------------------------
ssl							 |
-----------------------------
	#　一个使用 SslHandler 数据流程
		* 加密的入站数据被拦截,并被解密为平常数据
		* 平常数据传过 SslHandler
		* SslHandler 加密数据并它传递出站
	
	# 在大多数情况下,SslHandler 将成为 ChannelPipeline 中的第一个 ChannelHandler 
		* 这将确保所有其他 ChannelHandler 应用他们的逻辑到数据后加密后才发生,从而确保他们的变化是安全的
	
	# 涉及类库
		SslHandler

	#  服务端的Handler创建
		protected void initChannel(SocketChannel ch) throws Exception {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
			SslHandler sslHandler = sslCtx.newHandler(ch.alloc());
			ch.pipeline().addLast(sslHandler); // 把sslHandler添加到第一个
		}
	
	# 客户端的Handler创建
		String host = "127.0.0.1";	// 服务器地址
		int port = 1024;			// 服务器端口
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
			SslHandler sslHandler = sslContext.newHandler(ch.alloc(), host, port);
			ch.pipeline().addLast(sslHandler);	 // 把sslHandler添加到第一个
		}
	
	# SslHandler 的一些常用方法
		void setHandshakeTimeout(long handshakeTimeout, TimeUnit unit)
			* 设置握手超时时间,ChannelFuture 将得到通知
		
		void setHandshakeTimeoutMillis(long handshakeTimeoutMillis)
			* 设置握手超时时间,ChannelFuture 将得到通知
		
		long getHandshakeTimeoutMillis()
			* 获取握手超时时间值


		void setHandshakeTimeoutMillis(long handshakeTimeoutMillis)
			* 设置关闭通知超时时间,若超时, ChannelFuture 会关闭失败

		Future<Channel> handshakeFuture()
			* 返回完成握手后的 ChannelFuture(握手结果)

