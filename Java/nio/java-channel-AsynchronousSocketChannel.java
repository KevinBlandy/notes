-----------------------------------
AsynchronousServerSocketChannel		|
-----------------------------------
	# 异步,非阻塞客户端Socket通道


-----------------------------------
AsynchronousServerSocketChannel-API|
-----------------------------------
	# 静态方法
		AsynchronousSocketChannel open(AsynchronousChannelGroup group);
			* 打开一个异步通道,使用指定的 ChannelGroup
		AsynchronousSocketChannel open();
			* 打开一个异步通道
	
	# 实例方法
		//==========================绑定
		AsynchronousSocketChannel	bind(SocketAddress local);
			* 绑定本机地址
		

		//==========================连接
		Future<Void> connect(SocketAddress remote);
			* 连接远程服务器
			* demo
				 asynchronousSocketChannel.connect(new InetSocketAddress("localhost",55));
		
		<A> void connect(SocketAddress remote,A attachment,CompletionHandler<Void,? super A> handler);
			* 连接远程主机

		//==========================读取
		Future<Integer>	read(ByteBuffer dst);
		<A> void	read(ByteBuffer[] dsts, int offset, int length, long timeout, TimeUnit unit, A attachment, CompletionHandler<Long,? super A> handler);
		<A> void	read(ByteBuffer dst, A attachment, CompletionHandler<Integer,? super A> handler)
		<A> void	read(ByteBuffer dst, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer,? super A> handler)

		//==========================写入
		Future<Integer>	write(ByteBuffer src)
		<A> void	write(ByteBuffer[] srcs, int offset, int length, long timeout, TimeUnit unit, A attachment, CompletionHandler<Long,? super A> handler)
		<A> void	write(ByteBuffer src, A attachment, CompletionHandler<Integer,? super A> handler)
		<A> void	write(ByteBuffer src, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer,? super A> handler)\

		//=========================配置
		<T> AsynchronousSocketChannel setOption(SocketOption<T> name, T value);
			* 设置配置
		
		<T> T getOption(SocketOption<T> name);
			* 获取配置

		Set<SocketOption<?>> supportedOptions();
			* 获取支持的配置
		
		//=========================关闭
		AsynchronousSocketChannel	shutdownInput()
			* 关闭读,并没有关闭连接
		AsynchronousSocketChannel	shutdownOutput()
			* 关闭写,并没有关闭连接
		void close();
			* 关闭连接

		//==========================其他
		boolean isOpen();
			* 连接是否打开
		SocketAddress	getLocalAddress();
			* 本地地址
		SocketAddress	getRemoteAddress()
			* 远程地址
		AsynchronousChannelProvider	provider()
			* 未知
