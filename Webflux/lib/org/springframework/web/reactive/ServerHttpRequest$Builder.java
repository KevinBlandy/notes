--------------------
Builder
--------------------
	# ServerHttpRequest内部接口
		interface Builder
	
--------------------
this
--------------------
	Builder method(HttpMethod httpMethod);
	Builder uri(URI uri);
	Builder path(String path);
	Builder contextPath(String contextPath);
	Builder header(String headerName, String... headerValues);
	Builder headers(Consumer<HttpHeaders> headersConsumer);
	Builder sslInfo(SslInfo sslInfo);
	Builder remoteAddress(InetSocketAddress remoteAddress);
	ServerHttpRequest build();

