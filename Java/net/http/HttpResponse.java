-----------------------
HttpResponse
-----------------------
	# 描述http响应的接口类

	# 抽象方法
		public int statusCode();
			* 状态码

		public HttpRequest request();
			* Request对象

		public Optional<HttpResponse<T>> previousResponse();

		public HttpHeaders headers();
			* 请求头

		public T body();
			* 请求体

		public Optional<SSLSession> sslSession();
			* SSL Session

		public URI uri();
			* 请求URI

		public HttpClient.Version version();
			* HTTP 版本

-----------------------
ResponseInfo
-----------------------
	# 响应信息的接口
		public int statusCode();
		public HttpHeaders headers();
		public HttpClient.Version version();

-----------------------------
BodyHandler
-----------------------------
	# 响应体处理器, 函数接口, 唯一的抽象方法
		BodySubscriber<T> apply(ResponseInfo responseInfo);
	
	
	# BodySubscriber 是 Flow.Subscriber 的实现
		public interface BodySubscriber<T> extends Flow.Subscriber<List<ByteBuffer>> {
			public CompletionStage<T> getBody();
		}

-----------------------------
ResponseBodyHandlers
-----------------------------
	# 提供了响应体 BodyHandler 的很多静态实现

	class PathBodyHandler implements BodyHandler<Path>
		static PathBodyHandler create(Path file,List<OpenOption> openOptions)

	class PushPromisesHandlerWithMap<T> implements HttpResponse.PushPromiseHandler<T>
		public PushPromisesHandlerWithMap(Function<HttpRequest,BodyHandler<T>> pushPromiseHandler, ConcurrentMap<HttpRequest,CompletableFuture<HttpResponse<T>>> pushPromisesMap)

	class FileDownloadBodyHandler implements BodyHandler<Path>
		public static FileDownloadBodyHandler create(Path directory,  List<OpenOption> openOptions)


-----------------------------
BodyHandlers
-----------------------------
	# 基于 BodyHandler 的实现, 提供了N多现成的处理方法

	static BodyHandler<Void> fromSubscriber(Subscriber<? super List<ByteBuffer>> subscriber)
	static <S extends Subscriber<? super List<ByteBuffer>>,T> BodyHandler<T> fromSubscriber(S subscriber, Function<? super S,? extends T> finisher)
	static BodyHandler<Void> fromLineSubscriber(Subscriber<? super String> subscriber)
	static <S extends Subscriber<? super String>,T> BodyHandler<T> fromLineSubscriber(S subscriber, Function<? super S,? extends T> finisher, String lineSeparator)

	static BodyHandler<Void> discarding()
		* 丢弃请求体

	static <U> BodyHandler<U> replacing(U value)

	static BodyHandler<String> ofString()
	static BodyHandler<String> ofString(Charset charset)
		* 把请求体转换为string

	static BodyHandler<Path> ofFile(Path file, OpenOption... openOptions)
	static BodyHandler<Path> ofFile(Path file)
	static BodyHandler<Path> ofFileDownload(Path directory, OpenOption... openOptions)
		* 转换为文件下载

	static BodyHandler<InputStream> ofInputStream()
		* 转换为读取流

	static BodyHandler<Void> ofByteArrayConsumer(Consumer<Optional<byte[]>> consumer)
	static BodyHandler<byte[]> ofByteArray()
		* 转换为字节数组
	
	static BodyHandler<Publisher<List<ByteBuffer>>> ofPublisher()
	static <T> BodyHandler<T> buffering(BodyHandler<T> downstreamHandler, int bufferSize)



-----------------------------
PushPromiseHandler
-----------------------------
	# 接口
	# 静态方法
		public static <T> PushPromiseHandler<T> of(Function<HttpRequest,BodyHandler<T>> pushPromiseHandler, ConcurrentMap<HttpRequest,CompletableFuture<HttpResponse<T>>> pushPromisesMap) {
            return new PushPromisesHandlerWithMap<>(pushPromiseHandler, pushPromisesMap);
        }
	
	# 抽象方法
		public void applyPushPromise(HttpRequest initiatingRequest,HttpRequest pushPromiseRequest,Function<HttpResponse.BodyHandler<T>,CompletableFuture<HttpResponse<T>>> acceptor);
	

-----------------------------
其他的类
-----------------------------
	BodySubscriber
	BodySubscribers