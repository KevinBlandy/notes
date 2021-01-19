-------------------------
GroupContext			 |
-------------------------
	# GroupContext<SessionContext, P extends Packet, R>
	# 上下文,抽象类
	# 子类
		ClientGroupContext<SessionContext, P extends Packet, R> 
		ServerGroupContext<SessionContext, P extends Packet, R>

	# 属性
		//final属性
		public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 1;

		public static final long DEFAULT_HEARTBEAT_TIMEOUT = 1000 * 120;		
			* 默认的心跳超时时间,单位,毫秒
		
		public static final int READ_BUFFER_SIZE = Integer.getInteger("tio.default.read.buffer.size", 2048);
			* 默认接收数据的 BufferSize
			* 从 System.setProperty("tio.default.read.buffer.size",2048);中读取
			* 如果没有值,或者转换异常,就使用默认值:2048
		
		public static final long KEEP_ALIVE_TIME = 9000000L;
			* 保持连接的时间
		
		private final static AtomicInteger ID_ATOMIC = new AtomicInteger();
		
		
		//可配置属性
		private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
			* Buffer排序
		
		protected long heartbeatTimeout = DEFAULT_HEARTBEAT_TIMEOUT;
			* 心跳超时时间
		
		protected int readBufferSize = READ_BUFFER_SIZE;
			* 接收数据的BuffereSize
		
		protected ReconnConf<SessionContext, P, R> reconnConf;
			* 重连配置
		
		private SynThreadPoolExecutor<SynRunnableIntf> handlerExecutorNormPrior = null;
			* 低优先级的'业务处理'线程池
		
		private SynThreadPoolExecutor<SynRunnableIntf> sendExecutorNormPrior = null;
			* 低优先级的'消息发送'线程池
		
		private ThreadPoolExecutor closePoolExecutor = null;
		
		protected ClientNodes<SessionContext, P, R> clientNodes = new ClientNodes<>();
		protected ChannelContextSetWithLock<SessionContext, P, R> connections = new ChannelContextSetWithLock<>();
		protected ChannelContextSetWithLock<SessionContext, P, R> connecteds = new ChannelContextSetWithLock<>();
		protected ChannelContextSetWithLock<SessionContext, P, R> closeds = new ChannelContextSetWithLock<>();
		protected Groups<SessionContext, P, R> groups = new Groups<>();
		protected Users<SessionContext, P, R> users = new Users<>();
		protected ChannelContextMapWithLock<SessionContext, P, R> syns = new ChannelContextMapWithLock<>();
		 
		private boolean isEncodeCareWithChannelContext  = true;
			* packet编码成bytebuffer时，是否与ChannelContext相关
			* false: packet编码与ChannelContext无关
		
		protected String id;
	
		private boolean isStopped = false;

		

-----------------------------
ClientGroupContext			 |
-----------------------------
	# ClientGroupContext<SessionContext, P extends Packet, R>
	# 属性
		private ExecutorService groupExecutor = null;

		private ClientAioHandler<SessionContext, P, R> clientAioHandler = null;

		private ClientAioListener<SessionContext, P, R> clientAioListener = null;

		private ClientGroupStat clientGroupStat = new ClientGroupStat();
		
		private ConnectionCompletionHandler<SessionContext, P, R> connectionCompletionHandler = new ConnectionCompletionHandler<>();
	
	# 方法
		

	# 构造方法
		public ClientGroupContext(ClientAioHandler<SessionContext, P, R> aioHandler, ClientAioListener<SessionContext, P, R> aioListener)
		public ClientGroupContext(ClientAioHandler<SessionContext, P, R> aioHandler, ClientAioListener<SessionContext, P, R> aioListener, ReconnConf<SessionContext, P, R> reconnConf)
		public ClientGroupContext(ClientAioHandler<SessionContext, P, R> aioHandler, ClientAioListener<SessionContext, P, R> aioListener, ReconnConf<SessionContext, P, R> reconnConf, ExecutorService groupExecutor)
	

-----------------------------
ServerGroupContext			 |
-----------------------------
	# ServerGroupContext<SessionContext, P extends Packet, R>
	# 属性
		private ThreadPoolExecutor groupExecutor = null;

		private AcceptCompletionHandler<SessionContext, P, R> acceptCompletionHandler = null;

		private ServerAioHandler<SessionContext, P, R> serverAioHandler = null;

		private ServerAioListener<SessionContext, P, R> serverAioListener = null;

		protected ServerGroupStat serverGroupStat = new ServerGroupStat();

		private Thread checkHeartbeatThread = null;
	
	# 方法
		
		
	# 构造方法
		public ServerGroupContext(ServerAioHandler<SessionContext, P, R> aioHandler, ServerAioListener<SessionContext, P, R> aioListener);
		public ServerGroupContext(ServerAioHandler<SessionContext, P, R> aioHandler, ServerAioListener<SessionContext, P, R> aioListener, ThreadPoolExecutor groupExecutor);



	