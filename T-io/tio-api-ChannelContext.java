----------------------------
ChannelContext				|
----------------------------
	# ChannelContext<SessionContext, P extends Packet, R>
	# 通道上下文,抽象类
	# 子类
		ClientChannelContext<SessionContext, P extends Packet, R> 
		ServerChannelContext<SessionContext, P extends Packet, R> 

	# 属性

	# 构造方法
		public ChannelContext(GroupContext<SessionContext, P, R> groupContext, AsynchronousSocketChannel asynchronousSocketChannel);
		

		
----------------------------
ClientChannelContext		|
----------------------------
	# 客户端
	# 属性
	# 方法
	# 构造方法
		public ClientChannelContext(GroupContext<SessionContext, P, R> groupContext, AsynchronousSocketChannel asynchronousSocketChannel);


		
----------------------------
ServerChannelContext		|
----------------------------
	# 服务端
	# 属性
	# 方法
	# 构造方法
		public ServerChannelContext(GroupContext<SessionContext, P, R> groupContext, AsynchronousSocketChannel asynchronousSocketChannel);
