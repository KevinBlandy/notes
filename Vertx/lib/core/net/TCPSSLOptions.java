---------------------
TCPSSLOptions
---------------------
	# TCP协议相关的配置类： abstract class TCPSSLOptions extends NetworkOptions 


---------------------
构造
---------------------
	public TCPSSLOptions()
	public TCPSSLOptions(TCPSSLOptions other)


---------------------
实例
---------------------
	public TCPSSLOptions(JsonObject json)
	public JsonObject toJson()
	public boolean isTcpNoDelay()
	public TCPSSLOptions setTcpNoDelay(boolean tcpNoDelay)
	public boolean isTcpKeepAlive()
	public TCPSSLOptions setTcpKeepAlive(boolean tcpKeepAlive)
	public int getSoLinger()
	public TCPSSLOptions setSoLinger(int soLinger)
	public TCPSSLOptions setIdleTimeout(int idleTimeout)
	public int getIdleTimeout()
	public TCPSSLOptions setIdleTimeoutUnit(TimeUnit idleTimeoutUnit)
	public TimeUnit getIdleTimeoutUnit()
	public boolean isSsl()
	public TCPSSLOptions setSsl(boolean ssl)
	public KeyCertOptions getKeyCertOptions()
	public TCPSSLOptions setKeyCertOptions(KeyCertOptions options)
	public JksOptions getKeyStoreOptions() 
	public TCPSSLOptions setKeyStoreOptions(JksOptions options)
	public PfxOptions getPfxKeyCertOptions()
	public TCPSSLOptions setPfxKeyCertOptions(PfxOptions options)
	public PemKeyCertOptions getPemKeyCertOptions()
	public TCPSSLOptions setPemKeyCertOptions(PemKeyCertOptions options)
	public TrustOptions getTrustOptions()
	public TCPSSLOptions setTrustOptions(TrustOptions options)
		* 可信任证书配置
	public JksOptions getTrustStoreOptions()
	public TCPSSLOptions setTrustStoreOptions(JksOptions options)
	public PfxOptions getPfxTrustOptions()
	public TCPSSLOptions setPfxTrustOptions(PfxOptions options)
	public PemTrustOptions getPemTrustOptions()
	public TCPSSLOptions setPemTrustOptions(PemTrustOptions options)
	public TCPSSLOptions addEnabledCipherSuite(String suite)
	public Set<String> getEnabledCipherSuites()
	public List<String> getCrlPaths()
	public TCPSSLOptions addCrlPath(String crlPath) throws NullPointerException
	public List<Buffer> getCrlValues()
	public TCPSSLOptions addCrlValue(Buffer crlValue) throws NullPointerException
	public boolean isUseAlpn()
	public TCPSSLOptions setUseAlpn(boolean useAlpn)
	public SSLEngineOptions getSslEngineOptions()
	public TCPSSLOptions setSslEngineOptions(SSLEngineOptions sslEngineOptions)
	public JdkSSLEngineOptions getJdkSslEngineOptions()
	public TCPSSLOptions setJdkSslEngineOptions(JdkSSLEngineOptions sslEngineOptions)
	public OpenSSLEngineOptions getOpenSslEngineOptions()
	public TCPSSLOptions setOpenSslEngineOptions(OpenSSLEngineOptions sslEngineOptions)
	public TCPSSLOptions setEnabledSecureTransportProtocols(Set<String> enabledSecureTransportProtocols)
	public TCPSSLOptions addEnabledSecureTransportProtocol(String protocol)
	public TCPSSLOptions removeEnabledSecureTransportProtocol(String protocol
	public boolean isTcpFastOpen()
	public TCPSSLOptions setTcpFastOpen(boolean tcpFastOpen)
	public boolean isTcpCork()
	public TCPSSLOptions setTcpCork(boolean tcpCork)
	public boolean isTcpQuickAck()
	public TCPSSLOptions setTcpQuickAck(boolean tcpQuickAck)
	public Set<String> getEnabledSecureTransportProtocols()
	public long getSslHandshakeTimeout()
	public TCPSSLOptions setSslHandshakeTimeout(long sslHandshakeTimeout)
	public TCPSSLOptions setSslHandshakeTimeoutUnit(TimeUnit sslHandshakeTimeoutUnit)
	public TimeUnit getSslHandshakeTimeoutUnit()
	public TCPSSLOptions setLogActivity(boolean logEnabled)
	public TCPSSLOptions setSendBufferSize(int sendBufferSize)
	public TCPSSLOptions setReceiveBufferSize(int receiveBufferSize)
	public TCPSSLOptions setReuseAddress(boolean reuseAddress)
	public TCPSSLOptions setTrafficClass(int trafficClass)
	public TCPSSLOptions setReusePort(boolean reusePort)



---------------------
静态
---------------------
  public static final boolean DEFAULT_TCP_NO_DELAY = true;
  public static final boolean DEFAULT_TCP_KEEP_ALIVE = false;
  public static final int DEFAULT_SO_LINGER = -1;
  public static final boolean DEFAULT_SSL = false;
  public static final int DEFAULT_IDLE_TIMEOUT = 0;
  public static final TimeUnit DEFAULT_IDLE_TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;
  public static final boolean DEFAULT_USE_ALPN = false;
  public static final SSLEngineOptions DEFAULT_SSL_ENGINE = null;
  public static final List<String> DEFAULT_ENABLED_SECURE_TRANSPORT_PROTOCOLS = Collections.unmodifiableList(Arrays.asList("TLSv1", "TLSv1.1", "TLSv1.2"));
  public static final boolean DEFAULT_TCP_FAST_OPEN = false;
  public static final boolean DEFAULT_TCP_CORK = false;
  public static final boolean DEFAULT_TCP_QUICKACK = false;
  public static final long DEFAULT_SSL_HANDSHAKE_TIMEOUT = 10L;
  public static final TimeUnit DEFAULT_SSL_HANDSHAKE_TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;