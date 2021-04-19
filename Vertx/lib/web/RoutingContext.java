---------------------------------
RoutingContext
---------------------------------
	# 上下文接口： interface RoutingContext


	HttpServerRequest request();
	HttpServerResponse response();
	void next();

	void fail(int statusCode);
	void fail(Throwable throwable);
	void fail(int statusCode, Throwable throwable);
		* 快速的触发失败，会触发在 Route 的 failureHandler 方法

	RoutingContext put(String key, Object obj);
	<T> T get(String key);
	<T> T remove(String key);
	Map<String, Object> data();
		* context的上下文数据，类似于Servet中的 requestScope

	Vertx vertx();
	@Nullable String mountPoint();
	Route currentRoute();
		* 获取当前路由对象

	@Deprecated
	default String normalisedPath()
	String normalizedPath();

	@Nullable Cookie getCookie(String name);
	RoutingContext addCookie(io.vertx.core.http.Cookie cookie);
	default @Nullable Cookie removeCookie(String name)
	@Nullable Cookie removeCookie(String name, boolean invalidate)
	int cookieCount();
	Map<String, io.vertx.core.http.Cookie> cookieMap();
		* cookie设置/读取/删除

	@Nullable String getBodyAsString();
	@Nullable String getBodyAsString(String encoding);
	@Nullable JsonObject getBodyAsJson(int maxAllowedLength);
	@Nullable JsonArray getBodyAsJsonArray(int maxAllowedLength);
	default @Nullable JsonObject getBodyAsJson() 
	default @Nullable JsonArray getBodyAsJsonArray()
		* 解析body为json

	@Nullable Buffer getBody();
		* 获取原始的body

	Set<FileUpload> fileUploads();
		* 获取到上传的文件

	@Nullable Session session();
	boolean isSessionAccessed();
	

	@Nullable User user();
	Throwable failure();
		* 获取fail抛出的异常

	int statusCode();
		* 获取已经设置的状态码

	@Nullable String getAcceptableContentType();
		* 根据Accept头获取客户端支持的ContentType
		* 谁的q值高，返回谁

	ParsedHeaderValues parsedHeaders();
	int addHeadersEndHandler(Handler<Void> handler);
	boolean removeHeadersEndHandler(int handlerID);
	int addBodyEndHandler(Handler<Void> handler);
	boolean removeBodyEndHandler(int handlerID);
	int addEndHandler(Handler<AsyncResult<Void>> handler);
	default Future<Void> addEndHandler()
	boolean removeEndHandler(int handlerID);
	boolean failed();
	void setBody(Buffer body);
	void setSession(Session session);
	void setUser(User user);
	void clearUser();
	void setAcceptableContentType(@Nullable String contentType);

	default void reroute(String path)
	void reroute(HttpMethod method, String path)
		* 重写路由，相当于Servlet中的forward了
		* 重新可以添加额外的查询参数，不过，旧的查询参数会被丢弃
			router.get().handler(ctx -> ctx.reroute("/final-target?variable=value"));
		
	default List<LanguageHeader> acceptableLanguages()
	default LanguageHeader preferredLanguage()
		* 获取客户端支持的所有语言，或者第一个语言


	Map<String, String> pathParams()
	String pathParam(String name)
		* 获取路由参数，包括path参数和正则匹配到的参数

	MultiMap queryParams()
	MultiMap queryParams(Charset encoding)
	List<String> queryParam(String name)

	default RoutingContext attachment(String filename)
		* 设置附件名称，需要在接下来往客户端写入数据

	default Future<Void> redirect(String url
	default RoutingContext redirect(String url, Handler<AsyncResult<Void>> handler)
		* 重定向
		* "back" 是一个特殊值，会重定向到请求的 referrer 地址
		* 如果没referrer，则重定向到根路径: /

	default Future<Void> json(Object json)
	default RoutingContext json(Object json, Handler<AsyncResult<Void>> handler)
		* 快速的响应json。可以多次调用(邪乎)

	default boolean is(String type)
		* 判断请求的ContentType是否为指定类型
			is("html")
			is("text/html")

	default boolean isFresh()
	default RoutingContext etag(String etag)

	default RoutingContext lastModified(Instant instant)
	default RoutingContext lastModified(String instant)
		* etag头相关
		
	default Future<Void> end(String chunk)
	default RoutingContext end(String chunk, Handler<AsyncResult<Void>> handler)
	default Future<Void> end(Buffer buffer)
	default RoutingContext end(Buffer buffer, Handler<AsyncResult<Void>> handler)
	default Future<Void> end()
	default RoutingContext end(Handler<AsyncResult<Void>> handler)