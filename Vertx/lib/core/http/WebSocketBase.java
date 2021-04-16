--------------------
WebSocketBase
--------------------
	# WebSocket 基类抽象： interface WebSocketBase extends ReadStream<Buffer>, WriteStream<Buffer> 

	WebSocketBase exceptionHandler(Handler<Throwable> handler);
	WebSocketBase handler(Handler<Buffer> handler);
	WebSocketBase pause();
	WebSocketBase resume();
	WebSocketBase fetch(long amount);
	WebSocketBase endHandler(Handler<Void> endHandler);
	WebSocketBase setWriteQueueMaxSize(int maxSize);
	WebSocketBase drainHandler(Handler<Void> handler);
	String binaryHandlerID();
	String textHandlerID();
		* WebSocket都会在事件总线上自动注册两个处理器，当此处理器中接收到任何数据时， 它会将数据写入WebSocket。
		* 这两个处理器是本地订阅，不会路由到集群上
		* 这两个就是获取监听的地址


	String subProtocol();
	Short closeStatusCode();
	String closeReason();
	MultiMap headers();

	Future<Void> writeFrame(WebSocketFrame frame);
	WebSocketBase writeFrame(WebSocketFrame frame, Handler<AsyncResult<Void>> handler);
	Future<Void> writeFinalTextFrame(String text);
	WebSocketBase writeFinalTextFrame(String text, Handler<AsyncResult<Void>> handler);
	Future<Void> writeFinalBinaryFrame(Buffer data);
	WebSocketBase writeFinalBinaryFrame(Buffer data, Handler<AsyncResult<Void>> handler);
	Future<Void> writeBinaryMessage(Buffer data);
	WebSocketBase writeBinaryMessage(Buffer data, Handler<AsyncResult<Void>> handler);
	Future<Void> writeTextMessage(String text);
	WebSocketBase writeTextMessage(String text, Handler<AsyncResult<Void>> handler);
	WebSocketBase writePing(Buffer data, Handler<AsyncResult<Void>> handler);
	Future<Void> writePing(Buffer data);
	WebSocketBase writePong(Buffer data, Handler<AsyncResult<Void>> handler);
	Future<Void> writePong(Buffer data);

	WebSocketBase closeHandler(@Nullable Handler<Void> handler);
	WebSocketBase frameHandler(@Nullable Handler<WebSocketFrame> handler);
	WebSocketBase textMessageHandler(@Nullable Handler<String> handler);
	WebSocketBase binaryMessageHandler(@Nullable Handler<Buffer> handler);
	WebSocketBase pongHandler(@Nullable Handler<Buffer> handler);

	Future<Void> end();
	void end(Handler<AsyncResult<Void>> handler);

	Future<Void> close();
	void close(Handler<AsyncResult<Void>> handler);
	Future<Void> close(short statusCode);
	void close(short statusCode, Handler<AsyncResult<Void>> handler);
	Future<Void> close(short statusCode, @Nullable String reason);
	void close(short statusCode, @Nullable String reason, Handler<AsyncResult<Void>> handler);
		* 关闭链接，设置状态码和原因

	SocketAddress remoteAddress();
	SocketAddress localAddress();
		* 获取本机，远程的地址

	boolean isSsl();
	boolean isClosed();
	SSLSession sslSession();
	X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException;
