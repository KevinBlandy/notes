-----------------------------
Channel						 |
-----------------------------
	# 实现的接口
		Channel extends AttributeMap, ChannelOutboundInvoker, Comparable<Channel

	ChannelId id();
		* 通道id

    EventLoop eventLoop();
		* eventLoop

    Channel parent();
    ChannelConfig config();
		* 配置
    
    boolean isOpen();
		* 是否开启

    boolean isRegistered();
		* 是否已经注册

    boolean isActive();
		* 是否已经注册
 
    ChannelMetadata metadata();
		* 元数据
  
    SocketAddress localAddress();
		* 本地addr

    SocketAddress remoteAddress();
		* 远程addr

    ChannelFuture closeFuture();
		* 获取到closeFuture
  
    boolean isWritable();
		* 是否可写
  
    long bytesBeforeUnwritable();
    long bytesBeforeWritable();
    
    Unsafe unsafe();
    
    ChannelPipeline pipeline();

    ByteBufAllocator alloc();

    @Override
    Channel read();

    @Override
    Channel flush();

-----------------------------
Unsafe						 |
-----------------------------
	SocketAddress localAddress();

	SocketAddress remoteAddress();

	void register(EventLoop eventLoop, ChannelPromise promise);

	void bind(SocketAddress localAddress, ChannelPromise promise);

	void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise);

	void disconnect(ChannelPromise promise);

	void close(ChannelPromise promise);

	void closeForcibly();

	void deregister(ChannelPromise promise);
	void beginRead();

	void write(Object msg, ChannelPromise promise);

	void flush();

	ChannelPromise voidPromise();

	ChannelOutboundBuffer outboundBuffer();