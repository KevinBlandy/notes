----------------------------
ReconnConf					|
----------------------------
	# 重连对象

----------------------------
ReconnConf-属性				|
----------------------------
	private long interval = 5000;
		* 重连的间隔时间，单位毫秒

	private int retryCount = 0;
		*  连续重连次数，当连续重连这么多次都失败时，不再重连。0和负数则一直重连

	LinkedBlockingQueue<ChannelContext<SessionContext, P, R>> queue = new LinkedBlockingQueue<ChannelContext<SessionContext, P, R>>();

	private ThreadPoolExecutor threadPoolExecutor = null;
		* 用来重连的线程池

----------------------------
ReconnConf-方法				|
----------------------------
	# 静态方法
		void put(ClientChannelContext<SessionContext, P, R> clientChannelContext);
		boolean isNeedReconn(ClientChannelContext<SessionContext, P, R> clientChannelContext, boolean putIfTrue);
	
	# 成员方法
		long getInterval();
		void setInterval(long interval);
		
		void setRetryCount(int retryCount);
		int getRetryCount();

		ThreadPoolExecutor getThreadPoolExecutor();
		LinkedBlockingQueue<ChannelContext<SessionContext, P, R>> getQueue();

		
	# 构造方法
		public ReconnConf(){
			if (threadPoolExecutor == null){
				synchronized (ReconnConf.class){
					if (threadPoolExecutor == null){
						threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 60L, TimeUnit.SECONDS,
								new LinkedBlockingQueue<Runnable>(), DefaultThreadFactory.getInstance("t-aio-client-reconn"));
					}
				}

			}

		}

		public ReconnConf(long interval){
			this();
			this.setInterval(interval);
		}
		public ReconnConf(long interval, int retryCount){
			this();
			this.interval = interval;
			this.retryCount = retryCount;
		}