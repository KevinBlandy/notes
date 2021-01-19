--------------------------------
Aio								|
--------------------------------
	# 核心AIP,私有化构造函数,无法创建实例
	# 都是静态方法

--------------------------------
Aio-静态方法					|
--------------------------------	
	# 消息发送相关
		void send(ChannelContext<SessionContext, P, R> channelContext, P packet);
			* 基础推送

		void send(GroupContext<SessionContext, P, R> groupContext, String ip, int port, P packet);
			* 指定端点推送

		void sendToAll(GroupContext<SessionContext, P, R> groupContext, P packet,ChannelContextFilter<SessionContext, P, R> channelContextFilter);
			* 发消息到所有连接
			* 通过 channelContextFilter 来过滤通道

		void sendToGroup(GroupContext<SessionContext, P, R> groupContext, String groupid, P packet);
			* 发送消息到所有连接
			* 仅仅是 绑定了 groupId 为 groupid 的连接

		void sendToGroup(GroupContext<SessionContext, P, R> groupContext, String groupid, P packet,ChannelContextFilter<SessionContext, P, R> channelContextFilter);
			* 发送消息到所有连接
			* 仅仅是 绑定了 groupId 为 groupid 的连接
			* channelContextFilter 再次过滤连接

		void sendToSet(GroupContext<SessionContext, P, R> groupContext,ObjWithLock<Set<ChannelContext<SessionContext, P, R>>> setWithLock, P packet, ChannelContextFilter<SessionContext, P, R> channelContextFilter);
			* 发消息到指定集合

		void sendToUser(GroupContext<SessionContext, P, R> groupContext, String userid, P packet);
			* 发送消息到指定的连接
			* 该连接绑定用户 userid 为,userid

		<SessionContext, P extends Packet, R> P synSend(ChannelContext<SessionContext, P, R> channelContext, P packet, long timeout);
			* 同步消息发送
	
	# 获取通道上下文相关
		ChannelContext<SessionContext, P, R> getChannelContextByClientNode(GroupContext<SessionContext, P, R> groupContext,String clientIp, Integer clientPort);
			* 根据端点信息(ip&port)获取通道

		ChannelContext<SessionContext, P, R> getChannelContextByUserid(GroupContext<SessionContext, P, R> groupContext,String userid);
			* 根据用户ID获取通道

		ObjWithLock<Set<ChannelContext<SessionContext, P, R>>> getChannelContextsByGroup(GroupContext<SessionContext, P, R> groupContext, String groupid);
			* 根据用户组ID,获取一组通道

		ChannelContext<?, ?, ?> getChannelContextById(String id);
			* 根据通道ID获取通道
		
		 
			

	# 移除通道上下文相关
		void remove(ChannelContext<SessionContext, P, R> channelContext, String remark);
			
		void remove(ChannelContext<SessionContext, P, R> channelContext, Throwable throwable, String remark);
			
		void remove(GroupContext<SessionContext, P, R> groupContext, String clientIp, Integer clientPort, Throwable throwable,String remark);
			

	# 资源关闭相关
		void close(ChannelContext<SessionContext, P, R> channelContext, String remark);
			* 关闭资源,带备注信息

		void close(ChannelContext<SessionContext, P, R> channelContext, Throwable throwable, String remark);
			* 关闭资源,带备注&异常

		void close(GroupContext<SessionContext, P, R> groupContext, String clientIp, Integer clientPort, Throwable throwable,String remark);
			* 关闭资源信息,带备注,客户端IP,端口,异常...等信息
	
	# 绑定相关
		void bindGroup(ChannelContext<SessionContext, P, R> channelContext, String groupid);
			* 绑定指定的通道到指定的群组

		void bindUser(ChannelContext<SessionContext, P, R> channelContext, String userid);
			* 绑定指定的通道到指定的用户
	
	# 解除绑定相关
		void unbindGroup(String group, ChannelContext<SessionContext, P, R> channelContext);
			* 解除'指定通道与指定群组'的绑定

		void unbindGroup(ChannelContext<SessionContext, P, R> channelContext);
			* 解除'指定通道的所有群组'绑定信息

		void unbindUser(ChannelContext<SessionContext, P, R> channelContext);
			* 解除'指定通道与用户'绑定信息
		
	



	



	





