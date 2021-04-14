---------------------
EventBusOptions
---------------------
	# 消息总线的配置类： class EventBusOptions extends TCPSSLOptions

---------------------
构造
---------------------
	public EventBusOptions()
	public EventBusOptions(EventBusOptions other)
	public EventBusOptions(JsonObject json)


---------------------
实例
---------------------
	public JsonObject toJson()
	public ClientAuth getClientAuth()
	public EventBusOptions setClientAuth(ClientAuth clientAuth)
	public int getAcceptBacklog()
	public EventBusOptions setAcceptBacklog(int acceptBacklog)
	public String getHost()
	public EventBusOptions setHost(String host)
	public int getPort()
	public EventBusOptions setPort(int port)
	public int getReconnectAttempts()
	public EventBusOptions setReconnectAttempts(int attempts)
	public long getReconnectInterval() 
	public EventBusOptions setReconnectInterval(long interval)
	public EventBusOptions addCrlPath(String crlPath) throws NullPointerException
	public EventBusOptions addCrlValue(Buffer crlValue) throws NullPointerException
	public EventBusOptions addEnabledCipherSuite(String suite)
	public EventBusOptions setIdleTimeout(int idleTimeout)
	public EventBusOptions setKeyCertOptions(KeyCertOptions options)
	public EventBusOptions setKeyStoreOptions(JksOptions options)
	public EventBusOptions setPemKeyCertOptions(PemKeyCertOptions options)
	public EventBusOptions setPemTrustOptions(PemTrustOptions options)
	public EventBusOptions setPfxKeyCertOptions(PfxOptions options)
	public EventBusOptions setPfxTrustOptions(PfxOptions options)
	public EventBusOptions setSoLinger(int soLinger)
	public EventBusOptions setSsl(boolean ssl)
	public EventBusOptions setTcpKeepAlive(boolean tcpKeepAlive)
	public EventBusOptions setTcpNoDelay(boolean tcpNoDelay)
	public EventBusOptions setTrustOptions(TrustOptions options)
	public EventBusOptions setTrustStoreOptions(JksOptions options)
	public EventBusOptions setReceiveBufferSize(int receiveBufferSize)
	public EventBusOptions setReuseAddress(boolean reuseAddress)
	public EventBusOptions setReusePort(boolean reusePort)
	public EventBusOptions setSendBufferSize(int sendBufferSize)
	public EventBusOptions setTrafficClass(int trafficClass)
	public EventBusOptions setUseAlpn(boolean useAlpn)
	public EventBusOptions setSslEngineOptions(SSLEngineOptions sslEngineOptions)
	public EventBusOptions setJdkSslEngineOptions(JdkSSLEngineOptions sslEngineOptions)
	public EventBusOptions setOpenSslEngineOptions(OpenSSLEngineOptions sslEngineOptions)
	public EventBusOptions setEnabledSecureTransportProtocols(Set<String> enabledSecureTransportProtocols)
	public EventBusOptions addEnabledSecureTransportProtocol(String protocol)
	public EventBusOptions removeEnabledSecureTransportProtocol(String protocol)
	public EventBusOptions setTcpFastOpen(boolean tcpFastOpen)
	public EventBusOptions setTcpCork(boolean tcpCork)
	public EventBusOptions setTcpQuickAck(boolean tcpQuickAck)
	public EventBusOptions setLogActivity(boolean logEnabled)
	public EventBusOptions setSslHandshakeTimeout(long sslHandshakeTimeout) 
	public EventBusOptions setSslHandshakeTimeoutUnit(TimeUnit sslHandshakeTimeoutUnit)
	public EventBusOptions setTrustAll(boolean trustAll)
	public boolean isTrustAll()
	public int getConnectTimeout()
	public EventBusOptions setConnectTimeout(int connectTimeout)
	public long getClusterPingInterval()
	public EventBusOptions setClusterPingInterval(long clusterPingInterval)
	public long getClusterPingReplyInterval()
	public EventBusOptions setClusterPingReplyInterval(long clusterPingReplyInterval)
	public String getClusterPublicHost()
	public EventBusOptions setClusterPublicHost(String clusterPublicHost)
	public int getClusterPublicPort()
	public EventBusOptions setClusterPublicPort(int clusterPublicPort)
	public JsonObject getClusterNodeMetadata()
	public EventBusOptions setClusterNodeMetadata(JsonObject clusterNodeMetadata) 


---------------------
静态
---------------------
	public static final String DEFAULT_CLUSTER_HOST = null;
	public static final int DEFAULT_CLUSTER_PORT = 0;
	public static final String DEFAULT_CLUSTER_PUBLIC_HOST = null;
	public static final int DEFAULT_CLUSTER_PUBLIC_PORT = -1;
	public static final long DEFAULT_CLUSTER_PING_INTERVAL = TimeUnit.SECONDS.toMillis(20);
	public static final long DEFAULT_CLUSTER_PING_REPLY_INTERVAL = TimeUnit.SECONDS.toMillis(20);
	public static final int DEFAULT_ACCEPT_BACKLOG = -1;
	public static final ClientAuth DEFAULT_CLIENT_AUTH = ClientAuth.NONE;
	public static final int DEFAULT_RECONNECT_ATTEMPTS = 0;
	public static final long DEFAULT_RECONNECT_INTERVAL = 1000;
	public static final int DEFAULT_CONNECT_TIMEOUT = 60 * 1000;
	public static final boolean DEFAULT_TRUST_ALL = true;


