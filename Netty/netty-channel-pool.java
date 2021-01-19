---------------------
pool				 |
---------------------
	# 给客户端的的东西,给客户端的的东西,给客户端的的东西
	# 目的是解决, 与多个服务端交互以及与单个服务端建立连接池的问题

	# 相关类库
		ChannelPool
			|-AbstractChannelPoolHandler
				|-SimpleChannelPool
					|-FixedChannelPool
		ChannelPoolHandler
			
		ChannelHealthChecker
			
		ChannelPoolMap
			|-AbstractChannelPoolMap
	
	# 类库
		ChannelPool
			* 连接池接口

		SimpleChannelPool
			* 实现ChannelPool接口, 简单的连接池实现

		FixedChannelPool
			* 继承SimpleChannelPool, 有大小限制的连接池实现

		ChannelPoolMap
			* 管理host与连接池映射的接口

		AbstractChannelPoolMap
			* 抽象类,实现ChannelPoolMap接口

----------------------
ChannelPool			  |
----------------------
	# 连接池的抽象接口, 实现了 Closeable 接口
	# 抽象方法
		Future<Channel> acquire();

		Future<Channel> acquire(Promise<Channel> promise);

		Future<Void> release(Channel channel);

		Future<Void> release(Channel channel, Promise<Void> promise);

		@Override
		void close();



----------------------
SimpleChannelPool	  |
----------------------
	# 简单的连接池实现
	# 构造函数
		SimpleChannelPool(Bootstrap bootstrap, final ChannelPoolHandler handler)
		SimpleChannelPool(Bootstrap bootstrap, final ChannelPoolHandler handler, ChannelHealthChecker healthCheck)
		SimpleChannelPool(Bootstrap bootstrap, final ChannelPoolHandler handler, ChannelHealthChecker healthCheck, boolean releaseHealthCheck)
		SimpleChannelPool(Bootstrap bootstrap, final ChannelPoolHandler handler, ChannelHealthChecker healthCheck, boolean releaseHealthCheck, boolean lastRecentUsed)


----------------------
FixedChannelPool	  |
----------------------
	# 固定数量的连接池实现, 继承 SimpleChannelPool
	# 构造函数
		FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections)
		FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections, int maxPendingAcquires)
		FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, final long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires)
		FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, final long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires, final boolean releaseHealthCheck)
		FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, final long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires, boolean releaseHealthCheck, boolean lastRecentUsed)
	
		
		action
			* 内部静态枚举类:FixedChannelPool.AcquireTimeoutAction
				NEW
					* 检测到超时时创建新连接
				FAIL
					* 抛出异常:TimeoutException
