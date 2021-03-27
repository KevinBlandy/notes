-----------------------
Vertx
-----------------------
	# 实现了 io.vertx.core.metrics.Measured 的接口
		interface Vertx extends Measured

-----------------------
静态
-----------------------
	static Vertx vertx()
	static Vertx vertx(VertxOptions options) 
	static void clusteredVertx(VertxOptions options, Handler<AsyncResult<Vertx>> resultHandler)
	static Future<Vertx> clusteredVertx(VertxOptions options)
	static @Nullable Context currentContext() 

-----------------------
实例
-----------------------
	Context getOrCreateContext()
	NetServer createNetServer(NetServerOptions options)
	NetServer createNetServer()
	NetClient createNetClient(NetClientOptions options)
	NetClient createNetClient()
	HttpServer createHttpServer(HttpServerOptions options)
	HttpServer createHttpServer()
	HttpClient createHttpClient(HttpClientOptions options)
	HttpClient createHttpClient()
	DatagramSocket createDatagramSocket(DatagramSocketOptions options)
	DatagramSocket createDatagramSocket()
	@CacheReturn
	FileSystem fileSystem();
	@CacheReturn
	EventBus eventBus();
	DnsClient createDnsClient(int port, String host);
	DnsClient createDnsClient();
	DnsClient createDnsClient(DnsClientOptions options);
	@CacheReturn
	SharedData sharedData();
	long setTimer(long delay, Handler<Long> handler);
	TimeoutStream timerStream(long delay);
	long setPeriodic(long delay, Handler<Long> handler);
		* 每隔delay毫秒后，就会执行 handler一次

	TimeoutStream periodicStream(long delay);
	boolean cancelTimer(long id);
	void runOnContext(Handler<Void> action);
	Future<Void> close();
	void close(Handler<AsyncResult<Void>> completionHandler);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	Future<String> deployVerticle(Verticle verticle);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	void deployVerticle(Verticle verticle, Handler<AsyncResult<String>> completionHandler);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	Future<String> deployVerticle(Verticle verticle, DeploymentOptions options);
	@GenIgnore
	Future<String> deployVerticle(Class<? extends Verticle> verticleClass, DeploymentOptions options);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	Future<String> deployVerticle(Supplier<Verticle> verticleSupplier, DeploymentOptions options);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	void deployVerticle(Verticle verticle, DeploymentOptions options, Handler<AsyncResult<String>> completionHandler);
	@GenIgnore
	void deployVerticle(Class<? extends Verticle> verticleClass, DeploymentOptions options, Handler<AsyncResult<String>> completionHandler);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	void deployVerticle(Supplier<Verticle> verticleSupplier, DeploymentOptions options, Handler<AsyncResult<String>> completionHandler);
	Future<String> deployVerticle(String name);
	void deployVerticle(String name, Handler<AsyncResult<String>> completionHandler);
	Future<String> deployVerticle(String name, DeploymentOptions options);
	void deployVerticle(String name, DeploymentOptions options, Handler<AsyncResult<String>> completionHandler);
	Future<Void> undeploy(String deploymentID);
	void undeploy(String deploymentID, Handler<AsyncResult<Void>> completionHandler);
	Set<String> deploymentIDs();
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	void registerVerticleFactory(VerticleFactory factory);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	void unregisterVerticleFactory(VerticleFactory factory);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	Set<VerticleFactory> verticleFactories();
	boolean isClustered();
	<T> void executeBlocking(Handler<Promise<T>> blockingCodeHandler, boolean ordered, Handler<AsyncResult<@Nullable T>> resultHandler);
	<T> void executeBlocking(Handler<Promise<T>> blockingCodeHandler, Handler<AsyncResult<@Nullable T>> resultHandler);
	<T> Future<@Nullable T> executeBlocking(Handler<Promise<T>> blockingCodeHandler, boolean ordered);
	<T> Future<T> executeBlocking(Handler<Promise<T>> blockingCodeHandler);
	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	EventLoopGroup nettyEventLoopGroup();
	WorkerExecutor createSharedWorkerExecutor(String name);
	WorkerExecutor createSharedWorkerExecutor(String name, int poolSize);
	WorkerExecutor createSharedWorkerExecutor(String name, int poolSize, long maxExecuteTime);
	WorkerExecutor createSharedWorkerExecutor(String name, int poolSize, long maxExecuteTime, TimeUnit maxExecuteTimeUnit);
	@CacheReturn
	boolean isNativeTransportEnabled();
	@Fluent
	Vertx exceptionHandler(@Nullable Handler<Throwable> handler);
	@GenIgnore
	@Nullable Handler<Throwable> exceptionHandler();