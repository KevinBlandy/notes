-----------------------------
HttpServerResponse
-----------------------------
	# http响应接口：  interface HttpServerResponse extends WriteStream<Buffer> 

	HttpServerResponse exceptionHandler(Handler<Throwable> handler);
	HttpServerResponse setWriteQueueMaxSize(int maxSize);
	HttpServerResponse drainHandler(Handler<Void> handler);

	int getStatusCode();
	HttpServerResponse setStatusCode(int statusCode);
		* 状态码

	String getStatusMessage();
	HttpServerResponse setStatusMessage(String statusMessage);
		* 状态消息，HTTP2协议不应该写这个
	
	HttpServerResponse setChunked(boolean chunked);
	boolean isChunked();
		* 开启分块响应，每一次write都会让新的http块写出
		* 分块响应在 HTTP/2 流中无效。
	
	MultiMap headers();
	HttpServerResponse putHeader(String name, String value);
	HttpServerResponse putHeader(CharSequence name, CharSequence value);
	HttpServerResponse putHeader(String name, Iterable<String> values);
	HttpServerResponse putHeader(CharSequence name, Iterable<CharSequence> values);
		* 设置header
	
	MultiMap trailers();
	HttpServerResponse putTrailer(String name, String value);
	HttpServerResponse putTrailer(CharSequence name, CharSequence value);
	HttpServerResponse putTrailer(String name, Iterable<String> values);
	HttpServerResponse putTrailer(CharSequence name, Iterable<CharSequence> value);
		* 在分块模式下，可以将响应的 HTTP 响应附加尾部(trailers)写入响应
		* 这种方式实际上是在写入响应的最后一块。



	HttpServerResponse closeHandler(@Nullable Handler<Void> handler);
	HttpServerResponse endHandler(@Nullable Handler<Void> handler);

	Future<Void> write(String chunk, String enc);
	void write(String chunk, String enc, Handler<AsyncResult<Void>> handler);
	Future<Void> write(String chunk);
	void write(String chunk, Handler<AsyncResult<Void>> handler);
		* 往客户端写入数据，
		* 第一次写入操作会触发响应头的写入，因此，如果不使用 HTTP 分块，那么必须在写入响应之前设置 Content-Length 头， 否则不会生效。
		* 如果使用 HTTP 分块则不需要担心这点。


	HttpServerResponse writeContinue();

	Future<Void> end(String chunk);
	void end(String chunk, Handler<AsyncResult<Void>> handler);
	Future<Void> end(String chunk, String enc);
	void end(String chunk, String enc, Handler<AsyncResult<Void>> handler);
	Future<Void> end(Buffer chunk);
	void end(Buffer chunk, Handler<AsyncResult<Void>> handler);
	Future<Void> end();
		* 完成响应，可以向客户端输出最后1条数据

	default void send(Handler<AsyncResult<Void>> handler)
	default Future<Void> send()
	default void send(String body, Handler<AsyncResult<Void>> handler)
	default Future<Void> send(String body)
	default void send(Buffer body, Handler<AsyncResult<Void>> handler)
	default Future<Void> send(Buffer body)
	default void send(ReadStream<Buffer> body, Handler<AsyncResult<Void>> handler)
	default Future<Void> send(ReadStream<Buffer> body)

	default Future<Void> sendFile(String filename)
	default Future<Void> sendFile(String filename, long offset)
	Future<Void> sendFile(String filename, long offset, long length)
	default HttpServerResponse sendFile(String filename, Handler<AsyncResult<Void>> resultHandler)
	default HttpServerResponse sendFile(String filename, long offset, Handler<AsyncResult<Void>> resultHandler)
	HttpServerResponse sendFile(String filename, long offset, long length, Handler<AsyncResult<Void>> resultHandler)
		* 直接把本地文件响应给客户端
		* 可以设置读取部分body的偏移位置

	void close();
	boolean ended();
	boolean closed();
	boolean headWritten();
		* 是否已经响应客户端header

	HttpServerResponse headersEndHandler(@Nullable Handler<Void> handler);
	HttpServerResponse bodyEndHandler(@Nullable Handler<Void> handler);
	long bytesWritten();
	int streamId();

	default HttpServerResponse push(HttpMethod method, String host, String path, Handler<AsyncResult<HttpServerResponse>> handler)
	default Future<HttpServerResponse> push(HttpMethod method, String host, String path)
	default HttpServerResponse push(HttpMethod method, String path, MultiMap headers, Handler<AsyncResult<HttpServerResponse>> handler)
	default Future<HttpServerResponse> push(HttpMethod method, String path, MultiMap headers)
	default HttpServerResponse push(HttpMethod method, String path, Handler<AsyncResult<HttpServerResponse>> handler)
	default Future<HttpServerResponse> push(HttpMethod method, String path)
	default HttpServerResponse push(HttpMethod method, String host, String path, MultiMap headers, Handler<AsyncResult<HttpServerResponse>> handler)
	Future<HttpServerResponse> push(HttpMethod method, String host, String path, MultiMap headers)
		* http2的服务器push
		* 必须在响应结束之前调用 push方法，但是在推送响应过后依然可以写响应。

	default boolean reset()
	boolean reset(long code)
		* 充值流
		* 默认会发送 NO_ERROR (0)错误代码，也可以发送另外一个错误代码
		* 可用的错误码表
			http://httpwg.org/specs/rfc7540.html#ErrorCodes
		

	HttpServerResponse writeCustomFrame(int type, int flags, Buffer payload)
	default HttpServerResponse writeCustomFrame(HttpFrame frame)
		* 写入http2的帧

	default HttpServerResponse setStreamPriority(StreamPriority streamPriority)
	HttpServerResponse addCookie(Cookie cookie)
	default @Nullable Cookie removeCookie(String name)
	@Nullable Cookie removeCookie(String name, boolean invalidate)


	
