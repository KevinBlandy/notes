--------------------
ServerHttpRequest
--------------------
	# HttpRequest接口
		public interface ServerHttpRequest extends HttpRequest, ReactiveHttpInputMessage


--------------------
this
--------------------
	
	HttpHeaders getHeaders();
		* 获取header，注意，这个header是只读的，不能修改
		* 如果需要修改，必选通过 mutate 返回的Builder来修改后重写Build一个新的Request

	default HttpMethod getMethod()
	String getMethodValue();
		* 请求方法

	URI getURI();
		* 获取请求的URL

	Flux<DataBuffer> getBody();
		* 获取请求体
	
	String getId();

	RequestPath getPath();
		* 获取请求path

	MultiValueMap<String, String> getQueryParams();
		* 查询参数

	MultiValueMap<String, HttpCookie> getCookies();
		* 请求cookie

	default InetSocketAddress getLocalAddress()
	default InetSocketAddress getRemoteAddress()
		* 本地/远程地址

	default SslInfo getSslInfo()

	default ServerHttpRequest.Builder mutate()
		* “变异”，copy当前数据，构建一个Builder，可以通过Builder修改一些数据后，新Builde一个新的Request
		* 默认实现
			return new DefaultServerHttpRequestBuilder(this);

