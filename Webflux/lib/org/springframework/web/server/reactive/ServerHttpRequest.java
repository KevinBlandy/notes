--------------------
ServerHttpRequest
--------------------
	# HttpRequest½Ó¿Ú
		public interface ServerHttpRequest extends HttpRequest, ReactiveHttpInputMessage


--------------------
this
--------------------
	
	HttpHeaders getHeaders();
	default HttpMethod getMethod()
	String getMethodValue();
	URI getURI();

	Flux<DataBuffer> getBody();

	
	String getId();
	RequestPath getPath();
	MultiValueMap<String, String> getQueryParams();
	MultiValueMap<String, HttpCookie> getCookies();
	default InetSocketAddress getLocalAddress()
	default InetSocketAddress getRemoteAddress()
	default SslInfo getSslInfo()
	default ServerHttpRequest.Builder mutate()

