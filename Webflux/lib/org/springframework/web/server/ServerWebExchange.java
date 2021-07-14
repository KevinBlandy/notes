-----------------------------
ServerWebExchange
-----------------------------
	# 封装了req/resp的接口
		interface ServerWebExchange 
	
	# 类似于Context角色
		

-----------------------------
this
-----------------------------
	ServerHttpRequest getRequest();
	ServerHttpResponse getResponse();
		* 获取req/resp对象

	Map<String, Object> getAttributes();
		* 返回当前exchange的请求属性，返回结果是一个可变的Map

	default <T> T getAttribute(String name)
		* 根据key获取属性，返回值可能是null
	
	default <T> T getRequiredAttribute(String name)
		* 根据KEY获取属性，如果值不存在，抛出异常: IllegalArgumentException

	default <T> T getAttributeOrDefault(String name, T defaultValue)
		* 根据KEY获取属性，如果值不存在，返回默认值

	Mono<WebSession> getSession();
		* 返回当前的Session

	<T extends Principal> Mono<T> getPrincipal()
		* 返回当前请求的认证用户，如果存在的话

	Mono<MultiValueMap<String, String>> getFormData();
		* 获取表单数据
		* 只有Content-Type为application/x-www-form-urlencoded的时候这个方法才会返回一个非空的Map

	Mono<MultiValueMap<String, Part>> getMultipartData();
		* 获取Multipart数据
		* 只有Content-Type为multipart/form-data的时候这个方法才会返回一个非空的Map
		* 可以使用 @RequestBody Flux<Part> 来封装
			

	LocaleContext getLocaleContext();

	ApplicationContext getApplicationContext();
		* 返回Spring的上下文
	
	boolean isNotModified();
	boolean checkNotModified(Instant lastModified);
	boolean checkNotModified(String etag);
	boolean checkNotModified(@Nullable String etag, Instant lastModified);
		* lastModified属性相关

	String transformUrl(String url);
		*  URL转换
	
	void addUrlTransformer(Function<String, String> transformer);
		* URL转换映射

	String getLogPrefix();
		* 获取日志前缀

	default Builder mutate()
		* “变异”，本质就是把当前的WebExchange属性copy出来封装到Builder里面，可以通过Builder修改一些属性后重写build一个WebExchange
		* 默认实现
			new DefaultServerWebExchangeBuilder(this);

------------------------
static
------------------------
	String LOG_ID_ATTRIBUTE = ServerWebExchange.class.getName() + ".LOG_ID";
		* 日志前缀属性的KEY，值: 为org.springframework.web.server.ServerWebExchange.LOG_ID
		* 可以理解为: attributes.set("org.springframework.web.server.ServerWebExchange.LOG_ID", "日志前缀的具体值");
		* 作用是打印日志的时候会拼接这个KEY对应的前缀值，默认值为: ""
	
