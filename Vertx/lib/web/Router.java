----------------------------
Router
----------------------------
	# 路由接口： interface Router extends Handler<HttpServerRequest>


	static Router router(Vertx vertx)
		* 创建Router实例

	Route route();
		* 创建一个路由，默认它会处理所有请求

	Route route(HttpMethod method, String path);
	Route route(String path);
	Route routeWithRegex(HttpMethod method, String regex);
	Route routeWithRegex(String regex);

	Route get();
	Route get(String path);
	Route getWithRegex(String regex);

	Route head();
	Route head(String path);
	Route headWithRegex(String regex);

	Route options();
	Route options(String path);
	Route optionsWithRegex(String regex);

	Route put();
	Route put(String path);
	Route putWithRegex(String regex);

	Route post();
	Route post(String path);
	Route postWithRegex(String regex);

	Route delete();
	Route delete(String path);
	Route deleteWithRegex(String regex);

	Route trace();
	Route trace(String path);
	Route traceWithRegex(String regex);

	Route connect();
	Route connect(String path);
	Route connectWithRegex(String regex);

	Route patch();
	Route patch(String path);
	Route patchWithRegex(String regex);

	List<Route> getRoutes();
	Router clear();
	Route mountSubRouter(String mountPoint, Router subRouter);
		* 设置路由组

	Router errorHandler(int statusCode, Handler<RoutingContext> errorHandler);
		* 设置指定状态码的异常处理器
			Router maiRouter = Router.router(vertx)
				.errorHandler(404, ctx -> {
					ctx.response().setStatusCode(404);
					ctx.json(new JsonObject().put("message", "啥也没"));
				});
		

	void handleContext(RoutingContext context);
	void handleFailure(RoutingContext context);
	Router modifiedHandler(Handler<Router> handler);

	Router allowForward(AllowForwardHeaders allowForwardHeaders);
		* 设置解析代理头(X-Forward)的规则
			NONE			// 默认
			FORWARD			// 只解析 Forward 
			X_FORWARD		// 只解析 X-Forward
			ALL				// 都解析，但是 Forward 优先级比较高
			