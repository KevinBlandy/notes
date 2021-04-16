-----------------------------
HttpServerOptions
-----------------------------
	# Http服务器配置类： class HttpServerOptions extends NetServerOptions


-----------------------------
构造函数
-----------------------------
	public HttpServerOptions()
	public HttpServerOptions(HttpServerOptions other)
	public HttpServerOptions(JsonObject json)

-----------------------------
this
-----------------------------
	public JsonObject toJson()
	public HttpServerOptions setSendBufferSize(int sendBufferSize) 
	public HttpServerOptions setReceiveBufferSize(int receiveBufferSize)
	public HttpServerOptions setReuseAddress(boolean reuseAddress)
	public HttpServerOptions setReusePort(boolean reusePort)
	public HttpServerOptions setTrafficClass(int trafficClass) 
	public HttpServerOptions setTcpNoDelay(boolean tcpNoDelay)
	public HttpServerOptions setTcpKeepAlive(boolean tcpKeepAlive)
	public HttpServerOptions setSoLinger(int soLinger)
		* TCP相关的配置

	public HttpServerOptions setIdleTimeout(int idleTimeout)
	public HttpServerOptions setIdleTimeoutUnit(TimeUnit idleTimeoutUnit)
		* vert.x 不会自动关闭 keep-alive 的连接
		* 若您想要在一段空闲时间之后让 Vert.x 自动关闭 keep-alive 的连接，则使用这个方法进行配置。

	public HttpServerOptions setSsl(boolean ssl)
		* 开启SSL
	public HttpServerOptions setUseAlpn(boolean useAlpn)
		* 开起HTT2
		* 使用TLS应用层协议协商 (ALPN)协议来协商的 HTTP/2 协议

	public HttpServerOptions setKeyCertOptions(KeyCertOptions options) 
	public HttpServerOptions setKeyStoreOptions(JksOptions options)
	public HttpServerOptions setPfxKeyCertOptions(PfxOptions options)
	public HttpServerOptions setPemKeyCertOptions(PemKeyCertOptions options) 
		* 设置服务器端证书信息

	public HttpServerOptions setTrustOptions(TrustOptions options)
	public HttpServerOptions setTrustStoreOptions(JksOptions options)
	public HttpServerOptions setPemTrustOptions(PemTrustOptions options)
	public HttpServerOptions setPfxTrustOptions(PfxOptions options) 
		* 客户端信任证书的配置信息

	public HttpServerOptions addEnabledCipherSuite(String suite)
	public HttpServerOptions addEnabledSecureTransportProtocol(final String protocol)
	public HttpServerOptions removeEnabledSecureTransportProtocol(String protocol) 
	public HttpServerOptions setTcpFastOpen(boolean tcpFastOpen)
	public HttpServerOptions setTcpCork(boolean tcpCork)
	public HttpServerOptions setTcpQuickAck(boolean tcpQuickAck)

	public HttpServerOptions addCrlPath(String crlPath) throws NullPointerException
	public HttpServerOptions addCrlValue(Buffer crlValue) throws NullPointerException 
		* 设置吊销证书的路径

	public HttpServerOptions setAcceptBacklog(int acceptBacklog)

	public HttpServerOptions setPort(int port)
	public HttpServerOptions setHost(String host)
		* 监听的端口和IP

	public HttpServerOptions setClientAuth(ClientAuth clientAuth)
		* 设置客户的的校验模式

	public HttpServerOptions setSslEngineOptions(SSLEngineOptions sslEngineOptions)
	public HttpServerOptions setJdkSslEngineOptions(JdkSSLEngineOptions sslEngineOptions)
	public HttpServerOptions setOpenSslEngineOptions(OpenSSLEngineOptions sslEngineOptions)
		* 设置SSL引擎

	public HttpServerOptions setEnabledSecureTransportProtocols(Set<String> enabledSecureTransportProtocols)
	public HttpServerOptions setSslHandshakeTimeout(long sslHandshakeTimeout)
	public HttpServerOptions setSslHandshakeTimeoutUnit(TimeUnit sslHandshakeTimeoutUnit)
		* 设置ssl握手超时时间

	public boolean isCompressionSupported()
	public HttpServerOptions setCompressionSupported(boolean compressionSupported)
		* 是否开启压缩

	public int getCompressionLevel()
	public HttpServerOptions setCompressionLevel(int compressionLevel)
		* 压缩级别

	public boolean isAcceptUnmaskedFrames()
	public HttpServerOptions setAcceptUnmaskedFrames(boolean acceptUnmaskedFrames)

	public int getMaxWebSocketFrameSize()
	public HttpServerOptions setMaxWebSocketFrameSize(int maxWebSocketFrameSize)
		* ws最大消息帧体积

	public int getMaxWebSocketMessageSize()
	public HttpServerOptions setMaxWebSocketMessageSize(int maxWebSocketMessageSize)
		* ws最大消息体积

	public HttpServerOptions addWebSocketSubProtocol(String subProtocol)
	public HttpServerOptions setWebSocketSubProtocols(List<String> subProtocols)
	public List<String> getWebSocketSubProtocols()
		* ws子协议

	public boolean isHandle100ContinueAutomatically()
	public HttpServerOptions setHandle100ContinueAutomatically(boolean handle100ContinueAutomatically)
		* 101协议自动处理

	public HttpServerOptions setMaxChunkSize(int maxChunkSize)
	public int getMaxChunkSize()
		

	public int getMaxInitialLineLength()
	public HttpServerOptions setMaxInitialLineLength(int maxInitialLineLength)

	public int getMaxHeaderSize()
	public HttpServerOptions setMaxHeaderSize(int maxHeaderSize)
		* 最大Header体积

	public int getMaxFormAttributeSize()
	public HttpServerOptions setMaxFormAttributeSize(int maxSize)

	public Http2Settings getInitialSettings()
	public HttpServerOptions setInitialSettings(Http2Settings settings)
		* 当服务器接受 HTTP/2 连接时，它会向客户端发送这个初始设置

	public List<HttpVersion> getAlpnVersions()
	public HttpServerOptions setAlpnVersions(List<HttpVersion> alpnVersions)
	public int getHttp2ConnectionWindowSize()
	public HttpServerOptions setHttp2ConnectionWindowSize(int http2ConnectionWindowSize)

	public HttpServerOptions setLogActivity(boolean logEnabled)
		* 是否记录活动日志

	public HttpServerOptions setSni(boolean sni)
	public HttpServerOptions setUseProxyProtocol(boolean useProxyProtocol)
	public HttpServerOptions setProxyProtocolTimeout(long proxyProtocolTimeout)
	public HttpServerOptions setProxyProtocolTimeoutUnit(TimeUnit proxyProtocolTimeoutUnit)

	public boolean isDecompressionSupported()
	public HttpServerOptions setDecompressionSupported(boolean decompressionSupported)
		* 是否自动解压缩客户端的压缩请求体，默认是 false

	public int getDecoderInitialBufferSize()
	public HttpServerOptions setDecoderInitialBufferSize(int decoderInitialBufferSize)

	public HttpServerOptions setPerFrameWebSocketCompressionSupported(boolean supported)
	public boolean getPerFrameWebSocketCompressionSupported()
		* 是否自动压缩ws消息

	public HttpServerOptions setPerMessageWebSocketCompressionSupported(boolean supported)
	public boolean getPerMessageWebSocketCompressionSupported()
		* 是支持否预压缩ws消息

	public HttpServerOptions setWebSocketCompressionLevel(int compressionLevel)
	public int getWebSocketCompressionLevel()
		* ws消息压缩级别

	public HttpServerOptions setWebSocketAllowServerNoContext(boolean accept)
	public boolean getWebSocketAllowServerNoContext()
	public HttpServerOptions setWebSocketPreferredClientNoContext(boolean accept)
	public boolean getWebSocketPreferredClientNoContext()
	public int getWebSocketClosingTimeout()
	public HttpServerOptions setWebSocketClosingTimeout(int webSocketClosingTimeout)
	public TracingPolicy getTracingPolicy()
	public HttpServerOptions setTracingPolicy(TracingPolicy tracingPolicy)



-----------------------------
static
-----------------------------
	public static final int DEFAULT_PORT = 80;  // Default port is 80 for HTTP not 0 from HttpServerOptions
	public static final boolean DEFAULT_COMPRESSION_SUPPORTED = false;
	public static final int DEFAULT_COMPRESSION_LEVEL = 6;
	public static final int DEFAULT_MAX_WEBSOCKET_FRAME_SIZE = 65536;
	public static final int DEFAULT_MAX_WEBSOCKET_MESSAGE_SIZE = 65536 * 4;
	public static final int DEFAULT_MAX_CHUNK_SIZE = 8192;
	public static final int DEFAULT_MAX_INITIAL_LINE_LENGTH = 4096;
	public static final int DEFAULT_MAX_HEADER_SIZE = 8192;
	public static final int DEFAULT_MAX_FORM_ATTRIBUTE_SIZE = 2048;
	public static final boolean DEFAULT_HANDLE_100_CONTINE_AUTOMATICALLY = false;
	public static final List<HttpVersion> DEFAULT_ALPN_VERSIONS = Collections.unmodifiableList(Arrays.asList(HttpVersion.HTTP_2, HttpVersion.HTTP_1_1));
	public static final long DEFAULT_INITIAL_SETTINGS_MAX_CONCURRENT_STREAMS = 100;
	public static final int DEFAULT_HTTP2_CONNECTION_WINDOW_SIZE = -1;
	public static final boolean DEFAULT_DECOMPRESSION_SUPPORTED = false;
	public static final boolean DEFAULT_ACCEPT_UNMASKED_FRAMES = false;
	public static final int DEFAULT_DECODER_INITIAL_BUFFER_SIZE = 128;
	public static final boolean DEFAULT_PER_FRAME_WEBSOCKET_COMPRESSION_SUPPORTED = true;
	public static final boolean DEFAULT_PER_MESSAGE_WEBSOCKET_COMPRESSION_SUPPORTED = true;
	public static final int DEFAULT_WEBSOCKET_COMPRESSION_LEVEL = 6;
	public static final boolean DEFAULT_WEBSOCKET_ALLOW_SERVER_NO_CONTEXT = false;
	public static final boolean DEFAULT_WEBSOCKET_PREFERRED_CLIENT_NO_CONTEXT = false;
	public static final int DEFAULT_WEBSOCKET_CLOSING_TIMEOUT = 10;
	public static final TracingPolicy DEFAULT_TRACING_POLICY = TracingPolicy.ALWAYS;