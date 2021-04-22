----------------------
SockJSHandler
----------------------
	# Socketjs处理器接口： interface SockJSHandler extends Handler<RoutingContext> 

	static SockJSHandler create(Vertx vertx)
	static SockJSHandler create(Vertx vertx, SockJSHandlerOptions options)
		* 创建SocketjsHandler

	Router socketHandler(Handler<SockJSSocket> handler)
		* 处理链接

	default Router bridge(SockJSBridgeOptions bridgeOptions)
	Router bridge(AuthorizationProvider authorizationProvider, SockJSBridgeOptions bridgeOptions, Handler<BridgeEvent> bridgeEventHandler);
	default Router bridge(SockJSBridgeOptions bridgeOptions, Handler<BridgeEvent> bridgeEventHandler)


	void handle(RoutingContext routingContext);
		* 过时方法

