-------------------------
ConnectionFactory
-------------------------


-------------------------
static
-------------------------
    private static final int MAX_UNSIGNED_SHORT = 65535;

    public static final String DEFAULT_USER = "guest";
    public static final String DEFAULT_PASS = "guest";
    public static final String DEFAULT_VHOST = "/";
    public static final int    DEFAULT_CHANNEL_MAX = 2047;
    public static final int    DEFAULT_FRAME_MAX = 0;
    public static final int    DEFAULT_HEARTBEAT = 60;
    public static final String DEFAULT_HOST = "localhost";
    public static final int    USE_DEFAULT_PORT = -1;
    public static final int    DEFAULT_AMQP_PORT = AMQP.PROTOCOL.PORT;
    public static final int    DEFAULT_AMQP_OVER_SSL_PORT = 5671;
    public static final int    DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final int    DEFAULT_HANDSHAKE_TIMEOUT = 10000;
    public static final int    DEFAULT_SHUTDOWN_TIMEOUT = 10000;
    public static final int    DEFAULT_CHANNEL_RPC_TIMEOUT = (int) MINUTES.toMillis(10);
    public static final long   DEFAULT_NETWORK_RECOVERY_INTERVAL = 5000;
    public static final int    DEFAULT_WORK_POOL_TIMEOUT = -1;

	public static int portOrDefault(int port, boolean ssl)
	public static String computeDefaultTlsProtocol(String[] supportedProtocols)
	public static int ensureUnsignedShort(int value) 


-------------------------
this
-------------------------


	public String getHost()
	public void setHost(String host)
	public int getPort()
	public void setPort(int port)
	public String getUsername()
	public void setUsername(String username)
	public String getPassword()
	public void setPassword(String password)
	public void setCredentialsProvider(CredentialsProvider credentialsProvider)
	public String getVirtualHost()
	public void setVirtualHost(String virtualHost)
	public void setUri(URI uri)
	public void setUri(String uriString)
	public int getRequestedChannelMax()
	public void setRequestedChannelMax(int requestedChannelMax)
	public int getRequestedFrameMax()
	public void setRequestedFrameMax(int requestedFrameMax)
	public int getRequestedHeartbeat()
	public void setConnectionTimeout(int timeout)
	public int getConnectionTimeout()
	public int getHandshakeTimeout()
	public void setHandshakeTimeout(int timeout)
	public void setShutdownTimeout(int shutdownTimeout)
	public int getShutdownTimeout()
	public void setRequestedHeartbeat(int requestedHeartbeat)
	public Map<String, Object> getClientProperties()
	public void setClientProperties(Map<String, Object> clientProperties)
	public SaslConfig getSaslConfig()
	public void setSaslConfig(SaslConfig saslConfig)
	public SocketFactory getSocketFactory()
	public void setSocketFactory(SocketFactory factory)
	public SocketConfigurator getSocketConfigurator()
	public void setSocketConfigurator(SocketConfigurator socketConfigurator)
	public void setSharedExecutor(ExecutorService executor)
	public void setShutdownExecutor(ExecutorService executor)
	public void setHeartbeatExecutor(ScheduledExecutorService executor)
	public ThreadFactory getThreadFactory()
	public void setThreadFactory(ThreadFactory threadFactory)
	public ExceptionHandler getExceptionHandler()
	public void setExceptionHandler(ExceptionHandler exceptionHandler)
	public boolean isSSL()
	public void useSslProtocol()
	public void useSslProtocol(String protocol)
	public void useSslProtocol(String protocol, TrustManager trustManager)
	public void useSslProtocol(String protocol, TrustManager trustManager)
	public void enableHostnameVerification()
	public boolean isAutomaticRecoveryEnabled()
	public void setAutomaticRecoveryEnabled(boolean automaticRecovery) 
	public boolean isTopologyRecoveryEnabled()
	public void setTopologyRecoveryEnabled(boolean topologyRecovery)
	public ExecutorService getTopologyRecoveryExecutor()
	public void setTopologyRecoveryExecutor(final ExecutorService topologyRecoveryExecutor)
	public void setMetricsCollector(MetricsCollector metricsCollector)
	public MetricsCollector getMetricsCollector()
	public void setCredentialsRefreshService(CredentialsRefreshService credentialsRefreshService)
	public Connection newConnection(Address[] addrs)
	public Connection newConnection(AddressResolver addressResolver) 
	public Connection newConnection(Address[] addrs, String clientProvidedName)
	public Connection newConnection(List<Address> addrs)
	public Connection newConnection(List<Address> addrs, String clientProvidedName)
	public Connection newConnection(ExecutorService executor, Address[] addrs)
	public Connection newConnection(ExecutorService executor, Address[] addrs, String clientProvidedName)
	public Connection newConnection(ExecutorService executor, List<Address> addrs) 
	public Connection newConnection(ExecutorService executor, AddressResolver addressResolver)
	public Connection newConnection(ExecutorService executor, List<Address> addrs, String clientProvidedName)
	public Connection newConnection(ExecutorService executor, AddressResolver addressResolver, String clientProvidedName)
	public ConnectionParams params(ExecutorService consumerWorkServiceExecutor)
	public Connection newConnection()
	public Connection newConnection(String connectionName)
	public Connection newConnection(ExecutorService executor)
	public Connection newConnection(ExecutorService executor, String connectionName) 
	public ConnectionFactory clone()
	public ConnectionFactory load(String propertyFileLocation)
	public ConnectionFactory load(String propertyFileLocation, String prefix)
	public ConnectionFactory load(Properties properties)
	public ConnectionFactory load(Properties properties, String prefix)
	public ConnectionFactory load(Map<String, String> properties)
	public ConnectionFactory load(Map<String, String> properties, String prefix)
	public long getNetworkRecoveryInterval()
	public void setNetworkRecoveryInterval(int networkRecoveryInterval)
	public void setNetworkRecoveryInterval(long networkRecoveryInterval)
	public RecoveryDelayHandler getRecoveryDelayHandler() 
	public void setRecoveryDelayHandler(final RecoveryDelayHandler recoveryDelayHandler)
	public void setNioParams(NioParams nioParams)
	public NioParams getNioParams()
	public void useNio()
	public void useBlockingIo()
	public void setChannelRpcTimeout(int channelRpcTimeout)
	public int getChannelRpcTimeout()
	public void setSslContextFactory(SslContextFactory sslContextFactory)
	public void setChannelShouldCheckRpcResponseType(boolean channelShouldCheckRpcResponseType)
	public boolean isChannelShouldCheckRpcResponseType()
	public void setWorkPoolTimeout(int workPoolTimeout)
	public int getWorkPoolTimeout()
	public void setErrorOnWriteListener(ErrorOnWriteListener errorOnWriteListener)
	public void setTopologyRecoveryFilter(TopologyRecoveryFilter topologyRecoveryFilter)
	public void setConnectionRecoveryTriggeringCondition(Predicate<ShutdownSignalException> connectionRecoveryTriggeringCondition)
	public void setTopologyRecoveryRetryHandler(RetryHandler topologyRecoveryRetryHandler)
	public void setTrafficListener(TrafficListener trafficListener)
	




