---------------------
HttpServerRequest
---------------------
	# HttpRequest 请求接口： interface HttpServerRequest extends ReadStream<Buffer>

---------------------
构造
---------------------

---------------------
实例
---------------------
	HttpServerRequest exceptionHandler(Handler<Throwable> handler);
	HttpServerRequest handler(Handler<Buffer> handler);
		* 当部分请求体到达的时候，它会执行
		* 可能会被调用多次

	HttpServerRequest pause();
	HttpServerRequest resume();
	HttpServerRequest fetch(long amount);
	HttpServerRequest endHandler(Handler<Void> endHandler);
		* 当整个请求（包括所有请求体）已经被完全读取时，它会被调用

	HttpVersion version();
	HttpMethod method();
		* HTTP版本号，方法

	default boolean isSSL()
	String scheme();
		* 协议
	String uri();
		* 请求URI，包含查询字符串
	String path();
		* 请求路径，不包含查询字符串
	String query();
		* 查询字符串，不包含?
	String host();
		* 请求的主机名称
		* 对于 HTTP/1.x 请求返回请求头中的 host 值，对于 HTTP/2 请求则返回伪头中的 :authority 的值。
	long bytesRead();

	HttpServerResponse response();
		* 获取响应对象

	MultiMap headers();
	default String getHeader(String headerName)
	default String getHeader(CharSequence headerName)
		* 获取Header

	MultiMap params();
	default String getParam(String paramName)
		* 查询参数，它不会包含Body参数

	default SocketAddress remoteAddress()
	default SocketAddress localAddress()
		* 远程地址和本地地址

	default SSLSession sslSession()
	X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException;
	String absoluteURI();
		* 读取请求中和相对URI对应的绝对URI

	default HttpServerRequest bodyHandler(@Nullable Handler<Buffer> bodyHandler)
	default HttpServerRequest body(Handler<AsyncResult<Buffer>> handler)
	Future<Buffer> body();
		* 用于聚合所有请求体，在所有请求体到达后，这个方法会被调用
		* 如果请求体过大，可能会导致内存溢出
		* bodyHandler 只会在成功的时候调用，body 需要自己判断是否成功

	default void end(Handler<AsyncResult<Void>> handler)
	Future<Void> end();

	default void toNetSocket(Handler<AsyncResult<NetSocket>> handler)
	Future<NetSocket> toNetSocket();

	HttpServerRequest setExpectMultipart(boolean expect);
	boolean isExpectMultipart();
		* 在读取表单之前，调用，表示设置允许提交表单

	HttpServerRequest uploadHandler(@Nullable Handler<HttpServerFileUpload> uploadHandler);
		* 处理多部件表单体上传的数据

	MultiMap formAttributes();
	String getFormAttribute(String attributeName);
		* body表单参数，只有在body读取完毕之后才能使用

	default int streamId() 
	default void toWebSocket(Handler<AsyncResult<ServerWebSocket>> handler)
	Future<ServerWebSocket> toWebSocket();
	boolean isEnded();
	HttpServerRequest customFrameHandler(Handler<HttpFrame> handler);
		* 接受http2的自定义帧

	HttpConnection connection();
	default StreamPriority streamPriority()
	HttpServerRequest streamPriorityHandler(Handler<StreamPriority> handler)
	default @Nullable Cookie getCookie(String name)
	default int cookieCount()
	Map<String, Cookie> cookieMap()
	default HttpServerRequest routed(String route)

---------------------
静态
---------------------