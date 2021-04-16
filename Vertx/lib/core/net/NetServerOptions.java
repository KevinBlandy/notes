------------------------
NetServerOptions
------------------------
	# NetServer 服务的配置类： class NetServerOptions extends TCPSSLOptions

------------------------
构造
------------------------
	public NetServerOptions()
	public NetServerOptions(NetServerOptions other)
	public NetServerOptions(JsonObject json)

------------------------
实例
------------------------
	public JsonObject toJson()
	public NetServerOptions setSendBufferSize(int sendBufferSize)
	public NetServerOptions setReceiveBufferSize(int receiveBufferSize)
	public NetServerOptions setReuseAddress(boolean reuseAddress)
	public NetServerOptions setReusePort(boolean reusePort)
	public NetServerOptions setTrafficClass(int trafficClass)
	public NetServerOptions setTcpNoDelay(boolean tcpNoDelay)
	public NetServerOptions setTcpKeepAlive(boolean tcpKeepAlive)
	public NetServerOptions setSoLinger(int soLinger)
	public NetServerOptions setIdleTimeout(int idleTimeout)
	public NetServerOptions setIdleTimeoutUnit(TimeUnit idleTimeoutUnit)
	public NetServerOptions setSsl(boolean ssl)
		* 开启SSL，设置后，需要设置证书信息

	public NetServerOptions setUseAlpn(boolean useAlpn)

	public NetServerOptions setSslEngineOptions(SSLEngineOptions sslEngineOptions)
	public NetServerOptions setJdkSslEngineOptions(JdkSSLEngineOptions sslEngineOptions)
	public NetServerOptions setOpenSslEngineOptions(OpenSSLEngineOptions sslEngineOptions)
		* 设置ssl引擎

	public NetServerOptions setKeyCertOptions(KeyCertOptions options)
		* 设置其他的类型的证书信息

	public NetServerOptions setKeyStoreOptions(JksOptions options)
		* 设置JKS证书信息，Java专属格式

	public NetServerOptions setPfxKeyCertOptions(PfxOptions options)
		* 设置P12证书信息，通用格式

	public NetServerOptions setPemKeyCertOptions(PemKeyCertOptions options)
		* 设置pemKey证书信息，Nginx用的证书

	public NetServerOptions setTrustOptions(TrustOptions options)

	public NetServerOptions setTrustStoreOptions(JksOptions options)
	public NetServerOptions setPfxTrustOptions(PfxOptions options)
	public NetServerOptions setPemTrustOptions(PemTrustOptions options)
		* 设置验证客户端的证书信息，SSL双向验证
		
	public NetServerOptions addEnabledCipherSuite(String suite)
		* 配置密码套件

	public NetServerOptions addEnabledSecureTransportProtocol(final String protocol)
	public NetServerOptions removeEnabledSecureTransportProtocol(String protocol)
		* 添加/删除TLS协议版本

	public NetServerOptions setTcpFastOpen(boolean tcpFastOpen)
	public NetServerOptions setTcpCork(boolean tcpCork)
	public NetServerOptions setTcpQuickAck(boolean tcpQuickAck)

	public NetServerOptions addCrlPath(String crlPath) throws NullPointerException
	public NetServerOptions addCrlValue(Buffer crlValue) throws NullPointerException
		* 配置证书吊销列表（CRL）来吊销不再被信任的证书机构

	public NetServerOptions setEnabledSecureTransportProtocols(Set<String> enabledSecureTransportProtocols)
	public NetServerOptions setSslHandshakeTimeout(long sslHandshakeTimeout)
	public NetServerOptions setSslHandshakeTimeoutUnit(TimeUnit sslHandshakeTimeoutUnit)
	public int getAcceptBacklog()
	public NetServerOptions setAcceptBacklog(int acceptBacklog)
	public int getPort()
	public NetServerOptions setPort(int port)
	public String getHost()
	public NetServerOptions setHost(String host)
	public ClientAuth getClientAuth()
	public NetServerOptions setClientAuth(ClientAuth clientAuth)
		* 设置客户端验证模式，clientAuth 是个枚举
			NONE		不验证
			REQUEST		需要证书，不验证
			REQUIRED	必须验证

	public NetServerOptions setLogActivity(boolean logEnabled)
	public boolean isSni()
	public NetServerOptions setSni(boolean sni)
	public boolean isUseProxyProtocol()
	public NetServerOptions setUseProxyProtocol(boolean useProxyProtocol)
	public long getProxyProtocolTimeout()
	public NetServerOptions setProxyProtocolTimeout(long proxyProtocolTimeout)
	public NetServerOptions setProxyProtocolTimeoutUnit(TimeUnit proxyProtocolTimeoutUnit)
	public TimeUnit getProxyProtocolTimeoutUnit()



------------------------
静态
------------------------
  public static final int DEFAULT_PORT = 0;
  public static final String DEFAULT_HOST = "0.0.0.0";
  public static final int DEFAULT_ACCEPT_BACKLOG = -1;
  public static final ClientAuth DEFAULT_CLIENT_AUTH = ClientAuth.NONE;
  public static final boolean DEFAULT_SNI = false;
  public static final boolean DEFAULT_USE_PROXY_PROTOCOL = false;
  public static final long DEFAULT_PROXY_PROTOCOL_TIMEOUT = 10L;
  public static final TimeUnit DEFAULT_PROXY_PROTOCOL_TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;