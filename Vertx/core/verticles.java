------------------
Verticles
------------------
	# Verticle 的实现类必须实现 Verticle 接口,但是通常直接继承抽象类 AbstractVerticle 

		interface Verticle
			Vertx getVertx();
				* 获取部署当前Verticle的Vertx实例

			void init(Vertx vertx, Context context);
				* 初始化

			void start(Promise<Void> startPromise) throws Exception;
				* Vert.x 部署 Verticle 时，它的 start 方法将被调用，这个方法执行完成后 Verticle 就变成已启动状态。

			void stop(Promise<Void> stopPromise) throws Exception;
				* 当Vert.x 撤销一个 Verticle 时它会被调用， 这个方法执行完成后 Verticle 就变成已停止状态了。
	
		abstract class AbstractVerticle implements Verticle 
			public Vertx getVertx() 
			public void init(Vertx vertx, Context context)
			public String deploymentID()
				* 部署ID
			public JsonObject config()
			public List<String> processArgs(
			public void start(Promise<Void> startPromise) throws Exception
				* 异步版本 的 start 方法来实现，它接收一个 Promise 参数。
				* 方法执行完时，Verticle 实例并没有部署好（状态不是 deployed），只有 startPromise.complete(); 的时候，才部署好了

			public void stop(Promise<Void> stopPromise) throws Exception
				* 异步版本的stop方法，只有 startPromise.complete(); 的时候，才stop了

			public void start() throws Exception
			public void stop() throws Exception
	
	# 部署方式
		* 直接部署实例对象
			vertx.deployVerticle(mainVerticle);
		
		* 部署实现类
			vertx.deployVerticle(MainVerticle.class, new DeploymentOptions());
		
		* 部署类类型
			vertx.deployVerticle("com.mycompany.MainVerticle");
		
		* 部署其他语言
			vertx.deployVerticle("verticles/myverticle.js");
			vertx.deployVerticle("verticles/my_verticle.rb");
	
	# Context 
		* 每个 Verticle 在部署的时候都会被分配一个 Context
			* 根据配置不同，可以是Event Loop Context 或者 Worker Context
		
		* 之后此 Verticle 上所有的普通代码都会在此 Context 上执行（即对应的 Event Loop 或Worker 线程）
		* 一个 Context 对应一个 Event Loop 线程（或 Worker 线程），但一个 Event Loop 可能对应多个 Context


------------------
总结
------------------
	# Verticle 就是一个类，一个Vertx程序由多个Verticle组成
	# 必须有一个main Verticle 作为程序的入口
	# 在Verticle中可以任意部署，卸载Verticle，会触发生命周期方法
	# 可以在部署的时候传递参数，也可以通过EventBus在任意Verticle中收发消息
	# Verticle的代码，永远只会被一个线程访问，不会被并发执行，除非
		1. 自己在代码中新建线程执行  new Thread()
		2. 部署Verticle的时候，设置了： setMultiThread(true)，已经被标识为过期
		3. 通过 vertx.executeBlocking(..) 执行阻塞代码时，传递 false 参数
	
	# 按照Vertx的规范编写代码，不会有线程安全的问题
	# Verticle分为2种
		1. 普通版本
			* 线程池使用的是 EventLoop，部署的时候，分配一个线程给它，以后都不会变，一直由它执行

		2. Work版本
			* 线程池使用的是 Work-Thread ，每次执行都会把任务交给线程池中某个线程执行
			* 比较耗时的代码，应该在Work版本中执行

	
