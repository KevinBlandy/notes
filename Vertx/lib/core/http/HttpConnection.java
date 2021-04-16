---------------------------
HttpConnection
---------------------------
	# Http连接，接口： interface HttpConnection 

	default int getWindowSize()
	default HttpConnection setWindowSize(int windowSize) 

	default HttpConnection goAway(long errorCode)
	default HttpConnection goAway(long errorCode, int lastStreamId)

	HttpConnection goAway(long errorCode, int lastStreamId, Buffer debugData)
	HttpConnection goAwayHandler(@Nullable Handler<GoAway> handler)

	HttpConnection shutdownHandler(@Nullable  Handler<Void> handler)

	default void shutdown(Handler<AsyncResult<Void>> handler)
	default Future<Void> shutdown()

	void shutdown(long timeout, Handler<AsyncResult<Void>> handler)
	Future<Void> shutdown(long timeoutMs)

	HttpConnection closeHandler(Handler<Void> handler);
		* 监听断开事件


	Future<Void> close();
	default void close(Handler<AsyncResult<Void>> handler)
		* 断开连接

	Http2Settings settings()

	Future<Void> updateSettings(Http2Settings settings);
	HttpConnection updateSettings(Http2Settings settings, Handler<AsyncResult<Void>> completionHandler);
		* 更新链接设置

	Http2Settings remoteSettings();
	HttpConnection remoteSettingsHandler(Handler<Http2Settings> handler);

	HttpConnection ping(Buffer data, Handler<AsyncResult<Buffer>> pongHandler);
	Future<Buffer> ping(Buffer data);
		* 发送ping
		* 此功能仅适用于 HTTP/2 协议。


	HttpConnection pingHandler(@Nullable Handler<Buffer> handler);
		* 处理ping

	HttpConnection exceptionHandler(Handler<Throwable> handler);
		* 解析异常

	SocketAddress remoteAddress();
	SocketAddress localAddress();

	boolean isSsl();
	SSLSession sslSession();

	X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException;
	String indicatedServerName();


