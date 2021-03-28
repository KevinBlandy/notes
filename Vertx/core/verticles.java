------------------
Verticles
------------------
	# Verticle 的实现类必须实现 Verticle 接口,但是通常直接继承抽象类 AbstractVerticle 

		interface Verticle
			Vertx getVertx();
			void init(Vertx vertx, Context context);
			void start(Promise<Void> startPromise) throws Exception;
				* Vert.x 部署 Verticle 时，它的 start 方法将被调用，这个方法执行完成后 Verticle 就变成已启动状态。

			void stop(Promise<Void> stopPromise) throws Exception;
				* 当Vert.x 撤销一个 Verticle 时它会被调用， 这个方法执行完成后 Verticle 就变成已停止状态了。
	
		abstract class AbstractVerticle implements Verticle 
			public Vertx getVertx() 
			public void init(Vertx vertx, Context context)
			public String deploymentID()
			public JsonObject config()
			public List<String> processArgs(
			public void start(Promise<Void> startPromise) throws Exception
				* 异步版本 的 start 方法来实现，它接收一个 Promise 参数。
				* 方法执行完时，Verticle 实例并没有部署好（状态不是 deployed），只有 startPromise.complete(); 的时候，才部署好了

			public void stop(Promise<Void> stopPromise) throws Exception
				* 异步版本的stop方法，只有 startPromise.complete(); 的时候，才stop了

			public void start() throws Exception
			public void stop() throws Exception

	
