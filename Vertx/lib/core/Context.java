------------------------
Context
------------------------
	# Verticle部署上下文接口： interface Context

------------------------
抽象
------------------------
	void runOnContext(Handler<Void> action)
		* 在context中异步执行代码

	<T> void executeBlocking(Handler<Promise<T>> blockingCodeHandler, boolean ordered, Handler<AsyncResult<@Nullable T>> resultHandler)
	<T> void executeBlocking(Handler<Promise<T>> blockingCodeHandler, Handler<AsyncResult<@Nullable T>> resultHandler)
	<T> Future<@Nullable T> executeBlocking(Handler<Promise<T>> blockingCodeHandler, boolean ordered)
	<T> Future<T> executeBlocking(Handler<Promise<T>> blockingCodeHandler)
	String deploymentID()
	@Nullable JsonObject config()
	List<String> processArgs()

	boolean isEventLoopContext()
	boolean isWorkerContext()
		* 判断Context类型
	
	<T> T get(String key)
	void put(String key, Object value)
	boolean remove(String key)
		* 在同一个Context中传递/读取/删除数据

	<T> T getLocal(String key)
	void putLocal(String key, Object value)
	boolean removeLocal(String key)
		
	Vertx owner();
	int getInstanceCount();
	Context exceptionHandler(@Nullable Handler<Throwable> handler);
	Handler<Throwable> exceptionHandler();

------------------------
静态
------------------------
	static boolean isOnWorkerThread() 
	static boolean isOnEventLoopThread() 
	static boolean isOnVertxThread()
		* 判断是否是关联到Vertx的Context
