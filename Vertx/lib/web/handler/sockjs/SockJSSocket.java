-------------------------
SockJSSocket
-------------------------
	# Socketjs´¦ÀíÆ÷£º SockJSSocket extends ReadStream<Buffer>, WriteStream<Buffer>

	SockJSSocket exceptionHandler(Handler<Throwable> handler);
	SockJSSocket handler(Handler<Buffer> handler);
	SockJSSocket pause();
	SockJSSocket resume();
	SockJSSocket fetch(long amount);
	SockJSSocket endHandler(Handler<Void> endHandler);
	default Future<Void> write(Buffer data)
	default Future<Void> write(String data)
	default void write(String data, Handler<AsyncResult<Void>> handler)
	void write(Buffer data, Handler<AsyncResult<Void>> handler)
	SockJSSocket setWriteQueueMaxSize(int maxSize)
	SockJSSocket drainHandler(Handler<Void> handler)
	String writeHandlerID();
	Future<Void> end();
	void close();
	default void close(int statusCode, String reason)
	SocketAddress remoteAddress();
	SocketAddress localAddress();
	MultiMap headers();
	String uri();
	RoutingContext routingContext();
	Session webSession();
	User webUser();