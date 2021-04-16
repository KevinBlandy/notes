-----------------------
NetClientOptions
-----------------------
	# NetClient 配置类： class NetClientOptions extends ClientOptionsBase


-----------------------
构造
-----------------------
	public NetClientOptions()
	public NetClientOptions(NetClientOptions other)
	public NetClientOptions(JsonObject json)

-----------------------
实例
-----------------------
	public NetClientOptions setSendBufferSize(int sendBufferSize)
	public NetClientOptions setReceiveBufferSize(int receiveBufferSize)
	public NetClientOptions setReuseAddress(boolean reuseAddress)
	public NetClientOptions setReusePort(boolean reusePort)
	public NetClientOptions setTrafficClass(int trafficClass)
	public NetClientOptions setTcpNoDelay(boolean tcpNoDelay)
	public NetClientOptions setTcpKeepAlive(boolean tcpKeepAlive)
	public NetClientOptions setSoLinger(int soLinger)
	public NetClientOptions setIdleTimeout(int idleTimeout)
	public NetClientOptions setIdleTimeoutUnit(TimeUnit idleTimeoutUnit)
	public NetClientOptions setSsl(boolean ssl)
		* 开起SSL

	public NetClientOptions setKeyCertOptions(KeyCertOptions options)
	public NetClientOptions setKeyStoreOptions(JksOptions options)
	public NetClientOptions setPfxKeyCertOptions(PfxOptions options)
	public NetClientOptions setPemKeyCertOptions(PemKeyCertOptions options)
		* 设置客户端的信任证书

	public NetClientOptions setTrustOptions(TrustOptions options)

	public NetClientOptions setTrustStoreOptions(JksOptions options)
	public NetClientOptions setPemTrustOptions(PemTrustOptions options)
	public NetClientOptions setPfxTrustOptions(PfxOptions options)
		* 设置客户端的信任证书

	public NetClientOptions addEnabledCipherSuite(String suite)
		* 配置密码套件
		* 默认情况下，TLS配置将使用运行Vert.x的JVM 密码套件，该密码套件可以 配置一套启用的密码

	public NetClientOptions addEnabledSecureTransportProtocol(final String protocol)
	public NetClientOptions removeEnabledSecureTransportProtocol(String protocol)
	public NetClientOptions setUseAlpn(boolean useAlpn)
	public NetClientOptions setSslEngineOptions(SSLEngineOptions sslEngineOptions)
	public NetClientOptions setJdkSslEngineOptions(JdkSSLEngineOptions sslEngineOptions)
	public NetClientOptions setTcpFastOpen(boolean tcpFastOpen)
	public NetClientOptions setTcpCork(boolean tcpCork)
	public NetClientOptions setTcpQuickAck(boolean tcpQuickAck)
	public ClientOptionsBase setOpenSslEngineOptions(OpenSSLEngineOptions sslEngineOptions)

	public NetClientOptions addCrlPath(String crlPath) throws NullPointerException
	public NetClientOptions addCrlValue(Buffer crlValue) throws NullPointerException	
		* 配置证书吊销列表（CRL）来吊销不再被信任的证书机构

	public NetClientOptions setTrustAll(boolean trustAll)
		* 客户端将信任所有服务端证书

	public NetClientOptions setConnectTimeout(int connectTimeout)
	public NetClientOptions setMetricsName(String metricsName)
	public NetClientOptions setReconnectAttempts(int attempts)
	public int getReconnectAttempts()
		* 设置链接重试次数

	public NetClientOptions setReconnectInterval(long interval)
		* 设置链接重试间隔

	public String getHostnameVerificationAlgorithm()
	public NetClientOptions setHostnameVerificationAlgorithm(String hostnameVerificationAlgorithm)
		* 默认情况下，客户端禁用主机验证。 
		* 要启用主机验证，在这设置使用的算法（目前仅支持HTTPS和LDAPS）

	public List<String> getApplicationLayerProtocols()
	public NetClientOptions setApplicationLayerProtocols(List<String> protocols)
	public long getReconnectInterval()
	public NetClientOptions setLogActivity(boolean logEnabled)
		* 记录网络日志

	public NetClientOptions setProxyOptions(ProxyOptions proxyOptions)
	public NetClientOptions setLocalAddress(String localAddress)
	public NetClientOptions setEnabledSecureTransportProtocols(Set<String> enabledSecureTransportProtocols)
	public NetClientOptions setSslHandshakeTimeout(long sslHandshakeTimeout)
	public NetClientOptions setSslHandshakeTimeoutUnit(TimeUnit sslHandshakeTimeoutUnit)


-----------------------
静态
-----------------------
  public static final int DEFAULT_RECONNECT_ATTEMPTS = 0;
  public static final long DEFAULT_RECONNECT_INTERVAL = 1000;
  public static final String DEFAULT_HOSTNAME_VERIFICATION_ALGORITHM = "";