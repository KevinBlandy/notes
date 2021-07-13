-----------------------------
ServerWebExchangeUtils
-----------------------------
	# ServerWebExchange 工具类
		final class ServerWebExchangeUtils

		* 封装了大量的属性名称，以及快捷操作方式

-----------------------------
static
-----------------------------
	public static final String PRESERVE_HOST_HEADER_ATTRIBUTE = qualify("preserveHostHeader");
		* 是否保存Host属性，值是布尔值类型，写入位置是 PreserveHostHeaderGatewayFilterFactory 使用的位置是 NettyRoutingFilter
		* 作用是如果设置为true，HTTP请求头中的Host属性会写到底层Reactor-Netty的请求Header属性中。

	public static final String URI_TEMPLATE_VARIABLES_ATTRIBUTE = qualify("uriTemplateVariables");	
		* PathRoutePredicateFactory解析路径参数完成之后，把解析完成后的占位符KEY-路径Path映射存放在ServerWebExchange的属性中
		* KEY就是 URI_TEMPLATE_VARIABLES_ATTRIBUTE

	public static final String CLIENT_RESPONSE_ATTR = qualify("gatewayClientResponse");
		* 保存底层Reactor-Netty的响应对象，类型是 reactor.netty.http.client.HttpClientResponse。

	public static final String CLIENT_RESPONSE_CONN_ATTR = qualify("gatewayClientResponseConnection");
		* 保存底层Reactor-Netty的连接对象，类型是 reactor.netty.Connection。

	public static final String CLIENT_RESPONSE_HEADER_NAMES = qualify("gatewayClientResponseHeaderNames");
		* 保存底层Reactor-Netty的响应Header的名称集合。

	public static final String GATEWAY_ROUTE_ATTR = qualify("gatewayRoute");
		* 用于存放RoutePredicateHandlerMapping中匹配出来的具体的路由(org.springframework.cloud.gateway.route.Route)实例
		* 通过这个路由实例可以得知当前请求会路由到下游哪个服务。

	public static final String GATEWAY_REQUEST_URL_ATTR = qualify("gatewayRequestUrl");
		* java.net.URI 类型的实例，这个实例代表直接请求或者负载均衡处理之后需要请求到下游服务的真实URI

	public static final String GATEWAY_ORIGINAL_REQUEST_URL_ATTR = qualify("gatewayOriginalRequestUrl");
		* java.net.URI类型的实例，需要重写请求URI的时候，保存原始的请求URI

	public static final String GATEWAY_HANDLER_MAPPER_ATTR = qualify("gatewayHandlerMapper");
		* 保存当前使用的HandlerMapping具体实例的类型简称(一般是字符串"RoutePredicateHandlerMapping")

	public static final String GATEWAY_SCHEME_PREFIX_ATTR = qualify("gatewaySchemePrefix");
		* 确定目标路由URI中如果存在schemeSpecificPart属性，则保存该URI的scheme在此属性中，路由URI会被重新构造，见 RouteToRequestUrlFilter 

	public static final String GATEWAY_PREDICATE_ROUTE_ATTR = qualify("gatewayPredicateRouteAttr");
		* 用于存放RoutePredicateHandlerMapping中匹配出来的具体的路由(org.springframework.cloud.gateway.route.Route)实例的ID

	public static final String WEIGHT_ATTR = qualify("routeWeight");
		* 实验性功能(此版本还不建议在正式版本使用)存放分组权重相关属性，见WeightCalculatorWebFilter

	public static final String ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR = "original_response_content_type";
		* 存放响应Header中的ContentType的值

	public static final String CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR = qualify("circuitBreakerExecutionException");
		* Throwable的实例，存放的是断路器Hystrix执行异常时候的异常实例，见 HystrixGatewayFilterFactory

	public static final String GATEWAY_ALREADY_ROUTED_ATTR = qualify("gatewayAlreadyRouted");
		* 布尔值，用于判断是否已经进行了路由，见 NettyRoutingFilter

	public static final String GATEWAY_ALREADY_PREFIXED_ATTR = qualify("gatewayAlreadyPrefixed");
		* 布尔值，用于判断请求路径是否被添加了前置部分，见 PrefixPathGatewayFilterFactory

	public static final String CACHED_SERVER_HTTP_REQUEST_DECORATOR_ATTR = "cachedServerHttpRequestDecorator";
	public static final String CACHED_REQUEST_BODY_ATTR = "cachedRequestBody";
	public static final String GATEWAY_LOADBALANCER_RESPONSE_ATTR = qualify("gatewayLoadBalancerResponse");

	public static void setAlreadyRouted(ServerWebExchange exchange)
	public static void removeAlreadyRouted(ServerWebExchange exchange)
	public static boolean isAlreadyRouted(ServerWebExchange exchange)
	public static boolean setResponseStatus(ServerWebExchange exchange, HttpStatus httpStatus) 
	public static void reset(ServerWebExchange exchange) 
	public static boolean setResponseStatus(ServerWebExchange exchange, HttpStatusHolder statusHolder)
	public static boolean containsEncodedParts(URI uri) 
	public static HttpStatus parse(String statusString) 
	public static void addOriginalRequestUrl(ServerWebExchange exchange, URI url)
	public static AsyncPredicate<ServerWebExchange> toAsyncPredicate(Predicate<? super ServerWebExchange> predicate)
	public static String expand(ServerWebExchange exchange, String template) 
	public static void putUriTemplateVariables(ServerWebExchange exchange, Map<String, String> uriVariables)
	public static Map<String, String> getUriTemplateVariables(ServerWebExchange exchange)
	public static <T> Mono<T> cacheRequestBodyAndRequest(ServerWebExchange exchange, Function<ServerHttpRequest, Mono<T>> function) 
	public static <T> Mono<T> cacheRequestBody(ServerWebExchange exchange, Function<ServerHttpRequest, Mono<T>> function)
