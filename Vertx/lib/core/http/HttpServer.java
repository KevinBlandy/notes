----------------------
HttpServer
----------------------
	# http服务器接口：  interface HttpServer extends Measured


----------------------
this
----------------------
	ReadStream<HttpServerRequest> requestStream();

	HttpServer requestHandler(Handler<HttpServerRequest> handler);
	Handler<HttpServerRequest> requestHandler();
		* 读取/设置处理器
		* 在Header读取完毕后就会调用这个方法

	HttpServer connectionHandler(Handler<HttpConnection> handler);	
		* 监听客户端连接事件

	HttpServer exceptionHandler(Handler<Throwable> handler);
		* 异常处理器
		* 接收 连接传递给 requestHandler 之前发生的异常， 或者是传递给  webSocketHandler 之前发生的异常
		* 如TLS握手期间发生的异常

	ReadStream<ServerWebSocket> webSocketStream();

	HttpServer webSocketHandler(Handler<ServerWebSocket> handler);
	Handler<ServerWebSocket> webSocketHandler();
		* Websocket处理器
		
	Future<HttpServer> listen();
	Future<HttpServer> listen(int port, String host);
	HttpServer listen(int port, String host, Handler<AsyncResult<HttpServer>> listenHandler);
	HttpServer listen(SocketAddress address, Handler<AsyncResult<HttpServer>> listenHandler);
	Future<HttpServer> listen(SocketAddress address);
	Future<HttpServer> listen(int port);
	HttpServer listen(int port, Handler<AsyncResult<HttpServer>> listenHandler);
	HttpServer listen(Handler<AsyncResult<HttpServer>> listenHandler);
	Future<Void> close();
	void close(Handler<AsyncResult<Void>> completionHandler);
	int actualPort();