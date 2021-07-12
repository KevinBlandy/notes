-----------------------------
ServerWebExchange
-----------------------------
	# 封装了req/resp的接口
		interface ServerWebExchange 

-----------------------------
this
-----------------------------
	ServerHttpRequest getRequest();
	ServerHttpResponse getResponse();
	Map<String, Object> getAttributes();
	default <T> T getAttribute(String name)
	default <T> T getRequiredAttribute(String name)
	default <T> T getAttributeOrDefault(String name, T defaultValue)
	Mono<WebSession> getSession();
	<T extends Principal> Mono<T> getPrincipal()

	Mono<MultiValueMap<String, String>> getFormData();
		* 获取表单数据

	Mono<MultiValueMap<String, Part>> getMultipartData();
		* 获取Multipart数据

	LocaleContext getLocaleContext();
	ApplicationContext getApplicationContext();
	boolean isNotModified();
	boolean checkNotModified(Instant lastModified);
	boolean checkNotModified(String etag);
	boolean checkNotModified(@Nullable String etag, Instant lastModified);
	String transformUrl(String url);
	void addUrlTransformer(Function<String, String> transformer);
	String getLogPrefix();
	default Builder mutate()

------------------------
static
------------------------
	String LOG_ID_ATTRIBUTE = ServerWebExchange.class.getName() + ".LOG_ID";