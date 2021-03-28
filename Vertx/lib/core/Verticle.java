--------------------------
Verticle
--------------------------
	# interface Verticle

--------------------------
method
--------------------------
	Vertx getVertx();
	void init(Vertx vertx, Context context);
	void start(Promise<Void> startPromise) throws Exception;
	void stop(Promise<Void> stopPromise) throws Exception;

