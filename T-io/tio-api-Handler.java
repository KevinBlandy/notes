----------------------------
AioHandler					|
----------------------------
	# AioHandler<SessionContext, P extends Packet, R>
	# 接口,是客户端和服务端 Hanlder 的顶层抽象
	# 抽象方法
		R handler(P packet, ChannelContext<SessionContext, P, R> channelContext) throws Exception;
			* 处理消息包

		ByteBuffer encode(P packet, GroupContext<SessionContext, P, R> groupContext, ChannelContext<SessionContext, P, R> channelContext);
			* 编码,把对象转换为 Buffer

		P decode(ByteBuffer buffer, ChannelContext<SessionContext, P, R> channelContext) throws AioDecodeException;
			* 解码,把 Buffer 转换为对象
	
	# 子接口	
		ClientAioHandler <SessionContext, P extends Packet, R>
			* 客户端Handler接口
		ServerAioHandler <SessionContext, P extends Packet, R> 
			* 服务端Handler接口
		
	
----------------------------
ClientAioHandler			|
----------------------------
	# 客户端Hander
	# 在 AioHandler 的基础上,仅仅添加了一个关于心跳的抽象方法
	# 抽象方法
		P heartbeatPacket();
			* 创建心跳包

----------------------------
ServerAioHandler			|
----------------------------
	# 服务端Handler
	# 在 AioHandler 的基础上,未添加任何的抽象方法
