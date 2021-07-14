-----------------------
RouterFunctions
-----------------------
	# 路由器处理器构造类
		public abstract class RouterFunctions

-----------------------
static
-----------------------
	public static final String REQUEST_ATTRIBUTE = RouterFunctions.class.getName() + ".request";
	public static final String URI_TEMPLATE_VARIABLES_ATTRIBUTE = RouterFunctions.class.getName() + ".uriTemplateVariables";
	public static final String MATCHING_PATTERN_ATTRIBUTE = RouterFunctions.class.getName() + ".matchingPattern";
	
	public static Builder route()
	public static <T extends ServerResponse> RouterFunction<T> route(RequestPredicate predicate, HandlerFunction<T> handlerFunction)
	public static <T extends ServerResponse> RouterFunction<T> nest(RequestPredicate predicate, RouterFunction<T> routerFunction)
	public static RouterFunction<ServerResponse> resources(String pattern, Resource location)
	public static Function<ServerRequest, Mono<Resource>> resourceLookupFunction(String pattern, Resource location)
	public static RouterFunction<ServerResponse> resources(Function<ServerRequest, Mono<Resource>> lookupFunction)
	public static HttpHandler toHttpHandler(RouterFunction<?> routerFunction)
	public static HttpHandler toHttpHandler(RouterFunction<?> routerFunction, HandlerStrategies strategies)
	public static WebHandler toWebHandler(RouterFunction<?> routerFunction)
	public static WebHandler toWebHandler(RouterFunction<?> routerFunction, HandlerStrategies strategies)
	public static <T extends ServerResponse> RouterFunction<T> changeParser(RouterFunction<T> routerFunction, PathPatternParser parser)


-----------------------
RouterFunctions$Builder
-----------------------
	# 内部接口
		public interface Builder 
	
	# 方法
		Builder GET(HandlerFunction<ServerResponse> handlerFunction);
		Builder GET(String pattern, HandlerFunction<ServerResponse> handlerFunction);
		Builder GET(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder GET(String pattern, RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder HEAD(HandlerFunction<ServerResponse> handlerFunction);
		Builder HEAD(String pattern, HandlerFunction<ServerResponse> handlerFunction);
		Builder HEAD(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder HEAD(String pattern, RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder POST(HandlerFunction<ServerResponse> handlerFunction);
		Builder POST(String pattern, HandlerFunction<ServerResponse> handlerFunction);
		Builder POST(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder POST(String pattern, RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder PUT(HandlerFunction<ServerResponse> handlerFunction);
		Builder PUT(String pattern, HandlerFunction<ServerResponse> handlerFunction);
		Builder PUT(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder PUT(String pattern, RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder PATCH(HandlerFunction<ServerResponse> handlerFunction);
		Builder PATCH(String pattern, HandlerFunction<ServerResponse> handlerFunction);
		Builder PATCH(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder PATCH(String pattern, RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder DELETE(HandlerFunction<ServerResponse> handlerFunction);
		Builder DELETE(String pattern, HandlerFunction<ServerResponse> handlerFunction);
		Builder DELETE(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder DELETE(String pattern, RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder OPTIONS(HandlerFunction<ServerResponse> handlerFunction);
		Builder OPTIONS(String pattern, HandlerFunction<ServerResponse> handlerFunction);
		Builder OPTIONS(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder OPTIONS(String pattern, RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder route(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction);
		Builder add(RouterFunction<ServerResponse> routerFunction);
		Builder resources(String pattern, Resource location);
		Builder resources(Function<ServerRequest, Mono<Resource>> lookupFunction);
		Builder nest(RequestPredicate predicate, Supplier<RouterFunction<ServerResponse>> routerFunctionSupplier);
		Builder nest(RequestPredicate predicate, Consumer<Builder> builderConsumer);
		Builder path(String pattern, Supplier<RouterFunction<ServerResponse>> routerFunctionSupplier);
		Builder path(String pattern, Consumer<Builder> builderConsumer);
		Builder filter(HandlerFilterFunction<ServerResponse, ServerResponse> filterFunction);
		Builder before(Function<ServerRequest, ServerRequest> requestProcessor);
		Builder after(BiFunction<ServerRequest, ServerResponse, ServerResponse> responseProcessor);
		Builder onError(Predicate<? super Throwable> predicate, BiFunction<? super  Throwable, ServerRequest, Mono<ServerResponse>> responseProvider)
		<T extends Throwable> Builder onError(Class<T> exceptionType, BiFunction<? super T, ServerRequest, Mono<ServerResponse>> responseProvider)
		Builder withAttribute(String name, Object value);
		Builder withAttributes(Consumer<Map<String, Object>> attributesConsumer);
		RouterFunction<ServerResponse> build();




-----------------------
RouterFunctions$Visitor
-----------------------
	# 内部接口
		public interface Visitor
	
	# 方法
		void startNested(RequestPredicate predicate);
		void endNested(RequestPredicate predicate);
		void route(RequestPredicate predicate, HandlerFunction<?> handlerFunction);
		void resources(Function<ServerRequest, Mono<Resource>> lookupFunction);
		void attributes(Map<String, Object> attributes);
		void unknown(RouterFunction<?> routerFunction);
	
