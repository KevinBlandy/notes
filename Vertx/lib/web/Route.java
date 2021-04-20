--------------------
Route
--------------------
	# 路由接口 interface Route 

	Route method(HttpMethod method);
		* 设置允许的方法，可以多次调用

	Route path(String path);
	Route pathRegex(String path);
		* 设置路由路径，支持正则

	Route produces(String contentType);
	Route consumes(String contentType);
		* 内容协商，可以使用通配符: *
			consumes("*/json");
			consumes("text/*");

	Route virtualHost(String hostnamePattern);
		* 虚拟路径的路由
			router.route().virtualHost("*.vertx.io").handler(ctx -> {
			  // do something if the request is for *.vertx.io
			});

	Route order(int order);
		* 设置路由的执行顺序，默认顺序是按照添加顺序
		* 路由在创建时被分配一个与添加到路由器的顺序相对应的顺序，第一个路由编号0，第二个路由编号1，依此类推。
		* 可以复写，也可以设置为负数越小越先执行，这个api必须在handler设置之前调用

	Route last();

	Route handler(Handler<RoutingContext> requestHandler);
		* 非阻塞处理器

	Route blockingHandler(Handler<RoutingContext> requestHandler);
		* 阻塞处理器，默认情况下，所有阻塞处理器都是顺序执行的

	Route subRouter(Router subRouter);
	Route blockingHandler(Handler<RoutingContext> requestHandler, boolean ordered);
		* 阻塞处理器，自己决定要不要按照顺序执行。
		* 不关心执行的顺序，并且不介意阻塞式处理器以并行的方式执行，可以设置阻塞式处理器的 ordered 为 false。
		* 如果需要在一个阻塞处理器中处理一个 multipart 类型的表单数据，需要首先使用一个非阻塞的处理器来调用 setExpectMultipart(true) 
			router.post("/some/endpoint").handler(ctx -> {
			  ctx.request().setExpectMultipart(true);
			  ctx.next();
			}).blockingHandler(ctx -> {
			  // ... 执行某些阻塞操作
			});

	Route failureHandler(Handler<RoutingContext> failureHandler);
		* 当前路由的异常处理，可以通过 failureHandler.failure() 获取到异常信息
		* 可以多次调用添加多个
		* 它的执行顺序，会从最顶端的route的failureHandler挨个执行
		* 如果在failureHandler中执行了next()还会往下继续执行其他route的failureHandler()

	Route remove();
		* 移除路由

	Route disable();
	Route enable();
		* 启用/禁用路由

	@Deprecated
	default Route useNormalisedPath(boolean useNormalizedPath)
	Route useNormalizedPath(boolean useNormalizedPath);
	String getPath();
	boolean isRegexPath();
	boolean isExactPath();
	Set<HttpMethod> methods();
	Route setRegexGroupsNames(List<String> groups);
	Route setName(String name);
	String getName();

	default <T> Route respond(Function<RoutingContext, Future<@Nullable T>> function)
		* 快捷的处理器，会把 Future 的返回结果编码为json
		* 如果处理过程中发生错误，会响应合适的状态码
		router.get("/").respond(ctx -> {
			return Future.succeededFuture(new JsonObject().put("success", true));
		});

