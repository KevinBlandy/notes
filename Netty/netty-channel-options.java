---------------------------------
ChannelOption					 |
---------------------------------
	# ChannelOption 提供的标准配置项
	# ChannelConfig 对象也提供api,可以对这些属性进行设置

---------------------------------
ALLOCATOR						 |
---------------------------------
	* 设置Channe的buffer分配器,客户端,服务端都能设置的属性
		ByteBufAllocator byteBufAllocator = ctx.alloc();

	* 它的参数是接口实现:ByteBufAllocator
	* 系统预定义了一些默认的实现
		serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		serverBootstrap.childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
		
---------------------------------
RCVBUF_ALLOCATOR				 |
---------------------------------
	* 接收缓冲区的内存分配器,客户端,服务端都能设置的属性
	* 它的参数是接口的实现:RecvByteBufAllocator
	* 已有的实现类
		DefaultMaxBytesRecvByteBufAllocator
		DefaultMaxMessagesRecvByteBufAllocator
			|-AdaptiveRecvByteBufAllocator(默认)
				* 容量动态调整的接收缓冲区分配器,它会根据之前Channel接收到的数据报大小进行计算,如果连续填充满接收缓冲区的可写空间,则动态扩展容量
				* 如果连续2次接收到的数据报都小于指定值,则收缩当前的容量，以节约内存
				* 构造方法
					AdaptiveRecvByteBufAllocator(int minimum, int initial, int maximum)

					minimum 
						* 最小化预期缓冲区大小的包含下限(默认 64)
					initial 
						* 在未收到反馈时初始化初始缓冲区大小(默认 1024)
					maximum 
						* 最大化预期缓冲区大小的包含上限(默认 65536)

		
			|-FixedRecvByteBufAllocator
				* 固定长度的接收缓冲区分配器,由它分配的ByteBuf长度都是固定大小的,并不会根据实际数据报的大小动态收缩
				* 如果容量不足,支持动态扩展,动态扩展是Netty ByteBuf的一项基本功能,与ByteBuf分配器的实现没有关系


	MESSAGE_SIZE_ESTIMATOR
	CONNECT_TIMEOUT_MILLIS
	MAX_MESSAGES_PER_READ

	WRITE_SPIN_COUNT
	WRITE_BUFFER_HIGH_WATER_MARK
	WRITE_BUFFER_LOW_WATER_MARK
	WRITE_BUFFER_WATER_MARK
	ALLOW_HALF_CLOSURE

	AUTO_READ
	AUTO_CLOSE

	SO_BROADCAST
	SO_KEEPALIVE
		* 是否启用心跳保活机制
		* 在双方TCP套接字建立连接后(即都进入ESTABLISHED状态)并且在两个小时左右上层没有任何数据传输的情况下,这套机制才会被激活

	SO_SNDBUF
	SO_RCVBUF
	SO_REUSEADDR
	SO_LINGER
	SO_BACKLOG
		* 用于构造服务端套接字ServerSocket对象,标识当服务器请求处理线程全满时
		* 用于临时存放已完成三次握手的请求的队列的最大长度
		*  如果未设置或所设置的值小于1,Java将使用默认值50
	
	SO_TIMEOUT

	IP_TOS
	IP_MULTICAST_ADDR
	IP_MULTICAST_IF
	IP_MULTICAST_TTL
	IP_MULTICAST_LOOP_DISABLED
	TCP_NODELAY

	DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION
	SINGLE_EVENTEXECUTOR_PER_GROUP

	