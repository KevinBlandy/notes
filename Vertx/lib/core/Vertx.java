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
		* 若已经有一个context和当前线程关联，那么它直接重用这个context对象， 如果没有则创建一个新的返回

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
		* 设置一次性延迟操作

	TimeoutStream timerStream(long delay);
	long setPeriodic(long delay, Handler<Long> handler);
		* 每隔delay毫秒后，就会执行 handler一次

	TimeoutStream periodicStream(long delay);
	boolean cancelTimer(long id);
		* 清除定时器

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
		* 部署Verticle，可以设置类名称，类全路径，配置信息等等

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
		* 不能在 Event Loop 中直接调用阻塞式操作，因为这样做会阻止 Event Loop 执行其他有用的任务
		* 应该在 Vert.x 应用中安全调用"传统"阻塞API的方法
		* 若不关心调用 executeBlocking 的顺序， 可以将 ordered 参数的值设为 false，这样任何 executeBlocking 都会在 Worker Pool 中并行执行。
			vertx.executeBlocking(promise -> {
				try {
					Thread.sleep(1000);
					promise.complete("ok");
				} catch (InterruptedException e) {
					promise.fail(e);
				}
			}).onComplete(result -> {
				if (result.succeeded()) {
					System.out.println("异步结果:" + result.result());
				} else {
					System.out.println("异步异常:" + result.cause().getMessage() );
				}
			});
		
		* 当一个阻塞的操作持续超过10秒，blocked thread checker将会在控制台上打印一条消息。 
		* 长时间阻塞的操作应该由程序使用一个专用的线程管理， 他需要能够使用event-bus 或 runOnContext 与verticles交互




	@GenIgnore(GenIgnore.PERMITTED_TYPE)
	EventLoopGroup nettyEventLoopGroup();

	WorkerExecutor createSharedWorkerExecutor(String name);
	WorkerExecutor createSharedWorkerExecutor(String name, int poolSize);
	WorkerExecutor createSharedWorkerExecutor(String name, int poolSize, long maxExecuteTime);
	WorkerExecutor createSharedWorkerExecutor(String name, int poolSize, long maxExecuteTime, TimeUnit maxExecuteTimeUnit);
		* 另外一种运行阻塞式代码的方式是使用 worker verticle
		* 一个 Worker Verticle 始终会使用 Worker Pool 中的某个线程来执行。
		* 默认情况下，阻塞式代码会在 Vert.x 的 Worker Pool 中执行，通过 setWorkerPoolSize 配置。
			WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");
			executor.executeBlocking(promise -> {
			  // 调用阻塞的、需要消耗显著执行时间的API
			  String result = someAPI.blockingMethod("hello");
			  promise.complete(result);
			}, res -> {
			  System.out.println("The result is: " + res.result());
			});
		
		* 当使用同一个名字创建了许多 worker 时，它们将共享同一个 pool
		* Worker Executor 在不需要的时候必须被关闭，当所有的 worker executor 调用了 close 方法被关闭过后，对应的 worker pool 会被销毁。
			executor.close();
		* 如果 Worker Executor 在 Verticle 中创建，那么 Verticle 实例销毁的同时 Vert.x 将会自动关闭这个 Worker Executor。
		* Worker Executor 可以在创建的时候配置
			int poolSize = 10;
			// 2分钟
			long maxExecuteTime = 2;
			TimeUnit maxExecuteTimeUnit = TimeUnit.MINUTES;
			WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool", poolSize, maxExecuteTime, maxExecuteTimeUnit);
			
		
	
	@CacheReturn
	boolean isNativeTransportEnabled();
	@Fluent
	Vertx exceptionHandler(@Nullable Handler<Throwable> handler);
	@GenIgnore
	@Nullable Handler<Throwable> exceptionHandler();