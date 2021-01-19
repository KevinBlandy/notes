---------------------------
AioClient					|
---------------------------
	# AIO客户端对象


---------------------------
AioClient-属性				|
---------------------------

	private AsynchronousChannelGroup channelGroup;

	private ClientGroupContext<SessionContext, P, R> clientGroupContext;

---------------------------
AioClient-方法				|
---------------------------
	# 成员方法
		void asynConnect(Node serverNode);
			* 异步连接

		void asynConnect(Node serverNode, Integer timeout);
			* 异步连接

		void asynConnect(Node serverNode, String bindIp, Integer bindPort, Integer timeout);
			* 异步连接

		ClientChannelContext<SessionContext, P, R> connect(Node serverNode);
			* 同步连接

		ClientChannelContext<SessionContext, P, R> connect(Node serverNode, String bindIp, Integer bindPort, Integer timeout);
			* 同步连接

		AsynchronousChannelGroup getChannelGroup();
			* 获取异步通道Group
			
		ClientGroupContext<SessionContext, P, R> getClientGroupContext();
			* 获取客户端Group

		reconnect(ClientChannelContext<SessionContext, P, R> channelContext, Integer timeout);
			* 重新连接

		void setClientGroupContext(ClientGroupContext<SessionContext, P, R> clientGroupContext);
			* 设置客户端Group

		boolean stop();
			* 停止
			

	# 构造方法
		public AioClient(final ClientGroupContext<SessionContext, P, R> clientGroupContext) throws IOException{
			super();
			this.clientGroupContext = clientGroupContext;
			ExecutorService groupExecutor = clientGroupContext.getGroupExecutor();
			this.channelGroup = AsynchronousChannelGroup.withThreadPool(groupExecutor);
			startHeartbeatTask();
			startReconnTask();
		}