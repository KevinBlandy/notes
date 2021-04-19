------------------------
web
------------------------
	# 开发环境设置：dev
		* 环境变量：VERTXWEB_ENVIRONMENT
		* 系统属性：vertxweb.environment

		* dev模式下
			- 模板引擎缓存被禁用
			- ErrorHandler 不显示异常详细信息
			- StaticHandler 不处理缓存头
			- GraphQL开发工具被禁用

------------------------
路由
------------------------
	# 路由规则
		* 会忽略结尾的 / 所以路径 /some/path 或者 /some/path// 的请求也是匹配的
		* 可以使用正则表达式来实现，更简单的方式是在声明 Route 的路径时使用一个 * 作为结尾。
			Route route = router.route().path("/some/path/*"); // /some/path/foo.html 和 /some/path/otherdir/blah.css 都可以匹配
		
		* 路径参数：/user/:id
			 ctx.pathParam("id");
			
			* 参数名由字母，数字和下划线构成
			* 可以扩展额外的字符，包括：-, $
				-Dio.vertx.web.route.param.extended-pattern=true
		
		* 基于路径，只要是路径匹配，就会执行handler，执行顺序取决于order


	# 拦截器模型
		* 单个路由上的多个执行器
			Router router = Router.router(vertx);
			router.get("/").handler(ctx -> {
				LOGGER.info("下一个");
				ctx.next();
			}).handler(ctx -> {
				LOGGER.info("下一个");
			}).handler(ctx -> {
				ctx.response().setStatusCode(200).end("success");
			});
		
		* 多个路由上的执行器
			Router router = Router.router(vertx);

			Route api1 = router.route("/api");
			Route api2 = router.route("/api");
			Route api3 = router.route("/api");


			api1.method(HttpMethod.GET).handler(ctx -> {
			LOGGER.debug("我是api1");
			ctx.next();
			});
			api2.method(HttpMethod.GET).handler(ctx -> {
			LOGGER.debug("我是api2 - 1");
			ctx.next();
			}).handler(ctx -> {
			LOGGER.debug("我是api2 - 2");
			ctx.next();
			});
			api3.method(HttpMethod.GET).respond(ctx -> {
			return Future.succeededFuture(new JsonObject().put("success", true));
			});
					
			
	# 路由分组

		// 主路由
		Router maiRouter = Router.router(vertx);
		
		// 子路由
		Router userRouter = Router.router(vertx);
		userRouter.get("/:id").respond(ctx -> Future.succeededFuture(new JsonObject().put("id", ctx.pathParam("id"))));
		userRouter.get("/:id/foo").respond(ctx -> Future.succeededFuture(new JsonObject().put("id", ctx.pathParam("id"))));
		userRouter.get("/:id/bar").respond(ctx -> Future.succeededFuture(new JsonObject().put("id", ctx.pathParam("id"))));
		
		// 设置
		maiRouter.mountSubRouter("/api/user", userRouter);


		/api/user/2/bar
		/api/user/2/foo
		/api/user/2
		