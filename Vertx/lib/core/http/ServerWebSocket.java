------------------------------
ServerWebSocket
------------------------------
	# 服务端WebSocket：  interface ServerWebSocket extends WebSocketBase 

	ServerWebSocket exceptionHandler(Handler<Throwable> handler);
		* 异常处理器

	ServerWebSocket handler(Handler<Buffer> handler);
		* 消息处理器

	ServerWebSocket pause();
	ServerWebSocket resume();
	ServerWebSocket fetch(long amount);

	ServerWebSocket endHandler(Handler<Void> endHandler);
	ServerWebSocket setWriteQueueMaxSize(int maxSize);
		* 写队列大小

	ServerWebSocket drainHandler(Handler<Void> handler);

	ServerWebSocket writeFrame(WebSocketFrame frame, Handler<AsyncResult<Void>> handler);
	ServerWebSocket writeFinalTextFrame(String text, Handler<AsyncResult<Void>> handler);
	ServerWebSocket writeFinalBinaryFrame(Buffer data, Handler<AsyncResult<Void>> handler);
	ServerWebSocket writeBinaryMessage(Buffer data, Handler<AsyncResult<Void>> handler);
	ServerWebSocket writeTextMessage(String text, Handler<AsyncResult<Void>> handler);
		* 发送特定的消息
		
	ServerWebSocket closeHandler(Handler<Void> handler);
		* 链接关闭事件

	ServerWebSocket frameHandler(Handler<WebSocketFrame> handler);

	String scheme();
	String host();
	String uri();
	String path();
	String query();
	void accept();

	void reject();
	void reject(int status);
		* 拒绝链接，响应状态码

	void setHandshake(Future<Integer> future, Handler<AsyncResult<Integer>> handler);
	Future<Integer> setHandshake(Future<Integer> future);
		* 设置异步握手处理
		* 除非手动设置了WebSocket握手处理器，否则调用（webSocketHandler传入的）处理器后，将自动接受WebSocket握手。

	Future<Void> close();
	SSLSession sslSession();
	X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException;
