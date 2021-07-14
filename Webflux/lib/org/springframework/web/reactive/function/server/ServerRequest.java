----------------------
ServerRequest
----------------------
	# Request类接口
		interface ServerRequest

----------------------
this
----------------------
	@Nullable
	HttpMethod method()
	String methodName();
	URI uri();
	UriBuilder uriBuilder();
	String path()
	PathContainer pathContainer()
	RequestPath requestPath()
	Headers headers();
	MultiValueMap<String, HttpCookie> cookies();
	Optional<InetSocketAddress> remoteAddress();
	Optional<InetSocketAddress> localAddress();
	List<HttpMessageReader<?>> messageReaders();
	<T> T body(BodyExtractor<T, ? super ServerHttpRequest> extractor);
	<T> T body(BodyExtractor<T, ? super ServerHttpRequest> extractor, Map<String, Object> hints);
	<T> Mono<T> bodyToMono(Class<? extends T> elementClass);
	<T> Mono<T> bodyToMono(ParameterizedTypeReference<T> typeReference);
	<T> Flux<T> bodyToFlux(Class<? extends T> elementClass);
	<T> Flux<T> bodyToFlux(ParameterizedTypeReference<T> typeReference);
	Optional<Object> attribute(String name)
	Map<String, Object> attributes();
	Optional<String> queryParam(String name)
	MultiValueMap<String, String> queryParams();
	String pathVariable(String name) 
	Map<String, String> pathVariables();
	Mono<WebSession> session();
	Mono<? extends Principal> principal();
	Mono<MultiValueMap<String, String>> formData();
	Mono<MultiValueMap<String, Part>> multipartData();
	ServerWebExchange exchange();
	Mono<ServerResponse> checkNotModified(Instant lastModified)
	Mono<ServerResponse> checkNotModified(String etag)
	Mono<ServerResponse> checkNotModified(Instant lastModified, String etag) 

----------------------
static
----------------------
	static ServerRequest create(ServerWebExchange exchange, List<HttpMessageReader<?>> messageReaders)
	static Builder from(ServerRequest other)

----------------------
ServerRequest$Headers
----------------------
	# 内部接口
			interface Headers
	# 方法
		List<MediaType> accept();
		List<Charset> acceptCharset();
		List<Locale.LanguageRange> acceptLanguage();
		OptionalLong contentLength();
		Optional<MediaType> contentType();
		InetSocketAddress host();
		List<HttpRange> range();
		List<String> header(String headerName);
		String firstHeader(String headerName)
		HttpHeaders asHttpHeaders();


----------------------
ServerRequest$Builder
----------------------
	# 内部接口
		interface Builder 
	
	# 方法
		Builder method(HttpMethod method);
		Builder uri(URI uri);
		Builder header(String headerName, String... headerValues);
		Builder headers(Consumer<HttpHeaders> headersConsumer);
		Builder cookie(String name, String... values);
		Builder cookies(Consumer<MultiValueMap<String, HttpCookie>> cookiesConsumer);
		Builder body(Flux<DataBuffer> body);
		Builder body(String body);
		Builder attribute(String name, Object value);
		Builder attributes(Consumer<Map<String, Object>> attributesConsumer);
		ServerRequest build();
