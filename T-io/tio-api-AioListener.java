-----------------------
AioListener				|
-----------------------
	# AioListener<SessionContext, P extends Packet, R>
	# 接口
	# 子接口
		 ClientAioListener <SessionContext, P extends Packet, R> 
			* 客户端监听
		 ServerAioListener <SessionContext, P extends Packet, R>
			* 服务器监听

	# 抽象方法
		void onAfterConnected(ChannelContext<SessionContext, P, R> channelContext, boolean isConnected, boolean isReconnect) throws Exception;
			* 连接建立后触发,连接不一定是建立成功的,需要关注参数 isConnected
			* channelContext	
			* isConnected		是否连接成功,true:表示连接成功，false:表示连接失败
			* isReconnect 是否是重连, true: 表示这是重新连接，false: 表示这是第一次连接

		void onAfterSent(ChannelContext<SessionContext, P, R> channelContext, P packet, boolean isSentSuccess) throws Exception;
			* 消息包发送后触发该方法
			* channelContext
			* packet
			* isSentSuccess	true:发送成功，false:发送失败

		void onAfterReceived(ChannelContext<SessionContext, P, R> channelContext, P packet, int packetSize) throws Exception;
			* 解码成功后触发本方法
			* channelContext
			* packet
			* packetSize

		void onAfterClose(ChannelContext<SessionContext, P, R> channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception;
			* 连接关闭前后触发本方法
			* channelContext
			* throwable		有可能为空
			* remark		有可能为空
			* isRemove		是否是删除

-----------------------
ClientAioListener		|
-----------------------
	# 客户端监听接口
	# 当前版本未新增任何抽象方法
	
-----------------------
ServerAioListener		|
-----------------------
	# 服务端端监听接口
	# 当前版本未新增任何抽象方法
