--------------------------------
AioServer						|
--------------------------------
	# AioServer<SessionContext, P extends Packet, R>
	# 服务端对象


--------------------------------
AioServer-属性					|
--------------------------------
	
	private ServerGroupContext<SessionContext, P, R> serverGroupContext;

	private AsynchronousServerSocketChannel serverSocketChannel;

	private Node serverNode;

	private boolean isWaitingStop = false;

--------------------------------
AioServer-方法					|
--------------------------------
	# 成员方法
		ServerGroupContext<SessionContext, P, R> getServerGroupContext();

		setServerGroupContext(ServerGroupContext<SessionContext, P, R> serverGroupContext);

		AsynchronousServerSocketChannel getServerSocketChannel();
		
		start(String serverIp, int serverPort);

		boolean stop();

		Node getServerNode();

		boolean isWaitingStop();

		void setWaitingStop(boolean isWaitingStop);


	# 构造方法
		AioServer(ServerGroupContext<SessionContext, P, R> serverGroupContext){
			super();
			this.serverGroupContext = serverGroupContext;
		}