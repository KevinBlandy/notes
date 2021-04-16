---------------------
ClientOptionsBase
---------------------
	# øÕªßµƒ≈‰÷√£∫ abstract class ClientOptionsBase extends TCPSSLOptions

---------------------
constructor
---------------------
	public ClientOptionsBase()
	public ClientOptionsBase(ClientOptionsBase other)
	public ClientOptionsBase(JsonObject json)

---------------------
this
---------------------
	public JsonObject toJson()
	private void init()
	public boolean isTrustAll()
	public ClientOptionsBase setTrustAll(boolean trustAll)
	public int getConnectTimeout()
	public ClientOptionsBase setConnectTimeout(int connectTimeout)
	public String getMetricsName()
	public ClientOptionsBase setMetricsName(String metricsName)
	public ClientOptionsBase setProxyOptions(ProxyOptions proxyOptions)
	public ProxyOptions getProxyOptions()
	public String getLocalAddress()
	public ClientOptionsBase setLocalAddress(String localAddress)
	public ClientOptionsBase setLogActivity(boolean logEnabled) 
	public ClientOptionsBase setTcpNoDelay(boolean tcpNoDelay) 
	public ClientOptionsBase setTcpKeepAlive(boolean tcpKeepAlive)
	public ClientOptionsBase setSoLinger(int soLinger)
	public ClientOptionsBase setIdleTimeout(int idleTimeout)
	public ClientOptionsBase setIdleTimeoutUnit(TimeUnit idleTimeoutUnit)
	public ClientOptionsBase setSsl(boolean ssl)
	public ClientOptionsBase setKeyCertOptions(KeyCertOptions options)
	public ClientOptionsBase setKeyStoreOptions(JksOptions options)
	public ClientOptionsBase setPfxKeyCertOptions(PfxOptions options)
	public ClientOptionsBase setPemKeyCertOptions(PemKeyCertOptions options)
	public ClientOptionsBase setTrustOptions(TrustOptions options)
	public ClientOptionsBase setTrustStoreOptions(JksOptions options)
	public ClientOptionsBase setPfxTrustOptions(PfxOptions options)
	public ClientOptionsBase setPemTrustOptions(PemTrustOptions options)
	public ClientOptionsBase setUseAlpn(boolean useAlpn) 
	public ClientOptionsBase setSslEngineOptions(SSLEngineOptions sslEngineOptions)
	public ClientOptionsBase setJdkSslEngineOptions(JdkSSLEngineOptions sslEngineOptions)
	public ClientOptionsBase setOpenSslEngineOptions(OpenSSLEngineOptions sslEngineOptions)
	public ClientOptionsBase setSendBufferSize(int sendBufferSize)
	public ClientOptionsBase setReceiveBufferSize(int receiveBufferSize)
	public ClientOptionsBase setReuseAddress(boolean reuseAddress)
	public ClientOptionsBase setReusePort(boolean reusePort)
	public ClientOptionsBase setTrafficClass(int trafficClass)
	public ClientOptionsBase addEnabledCipherSuite(String suite)
	public ClientOptionsBase addCrlPath(String crlPath) throws NullPointerException
	public ClientOptionsBase addCrlValue(Buffer crlValue) throws NullPointerException
	public ClientOptionsBase addEnabledSecureTransportProtocol(String protocol)
	public ClientOptionsBase removeEnabledSecureTransportProtocol(String protocol)
	public ClientOptionsBase setTcpFastOpen(boolean tcpFastOpen)
	public ClientOptionsBase setTcpCork(boolean tcpCork)
	public ClientOptionsBase setTcpQuickAck(boolean tcpQuickAck)

---------------------
static
---------------------
  public static final int DEFAULT_CONNECT_TIMEOUT = 60000;
  public static final boolean DEFAULT_TRUST_ALL = false;
  public static final String DEFAULT_METRICS_NAME = "";