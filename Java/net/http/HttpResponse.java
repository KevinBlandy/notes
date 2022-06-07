-----------------------
HttpResponse
-----------------------
	# ����http��Ӧ�Ľӿ���

	# ���󷽷�
		public int statusCode();
			* ״̬��

		public HttpRequest request();
			* Request����

		public Optional<HttpResponse<T>> previousResponse();

		public HttpHeaders headers();
			* ����ͷ

		public T body();
			* ������

		public Optional<SSLSession> sslSession();
			* SSL Session

		public URI uri();
			* ����URI

		public HttpClient.Version version();
			* HTTP �汾

-----------------------
ResponseInfo
-----------------------
	# ��Ӧ��Ϣ�Ľӿ�
		public int statusCode();
		public HttpHeaders headers();
		public HttpClient.Version version();

-----------------------------
BodyHandler
-----------------------------
	# ��Ӧ�崦����, �����ӿ�, Ψһ�ĳ��󷽷�
		BodySubscriber<T> apply(ResponseInfo responseInfo);
	
	
	# BodySubscriber �� Flow.Subscriber ��ʵ��
		public interface BodySubscriber<T> extends Flow.Subscriber<List<ByteBuffer>> {
			public CompletionStage<T> getBody();
		}

-----------------------------
ResponseBodyHandlers
-----------------------------
	# �ṩ����Ӧ�� BodyHandler �ĺܶྲ̬ʵ��

	class PathBodyHandler implements BodyHandler<Path>
		static PathBodyHandler create(Path file,List<OpenOption> openOptions)

	class PushPromisesHandlerWithMap<T> implements HttpResponse.PushPromiseHandler<T>
		public PushPromisesHandlerWithMap(Function<HttpRequest,BodyHandler<T>> pushPromiseHandler, ConcurrentMap<HttpRequest,CompletableFuture<HttpResponse<T>>> pushPromisesMap)

	class FileDownloadBodyHandler implements BodyHandler<Path>
		public static FileDownloadBodyHandler create(Path directory,  List<OpenOption> openOptions)


-----------------------------
BodyHandlers
-----------------------------
	# ���� BodyHandler ��ʵ��, �ṩ��N���ֳɵĴ�����

	static BodyHandler<Void> fromSubscriber(Subscriber<? super List<ByteBuffer>> subscriber)
	static <S extends Subscriber<? super List<ByteBuffer>>,T> BodyHandler<T> fromSubscriber(S subscriber, Function<? super S,? extends T> finisher)
	static BodyHandler<Void> fromLineSubscriber(Subscriber<? super String> subscriber)
	static <S extends Subscriber<? super String>,T> BodyHandler<T> fromLineSubscriber(S subscriber, Function<? super S,? extends T> finisher, String lineSeparator)

	static BodyHandler<Void> discarding()
		* ����������

	static <U> BodyHandler<U> replacing(U value)

	static BodyHandler<String> ofString()
	static BodyHandler<String> ofString(Charset charset)
		* ��������ת��Ϊstring

	static BodyHandler<Path> ofFile(Path file, OpenOption... openOptions)
	static BodyHandler<Path> ofFile(Path file)
		* �洢��Ӧ����Ϊ�����ļ�

	static BodyHandler<Path> ofFileDownload(Path directory, OpenOption... openOptions)
		* �����Content-Disposition ͷ�����ļ����뵽 directoryĿ¼��

	static BodyHandler<InputStream> ofInputStream()
		* ת��Ϊ��ȡ��

	static BodyHandler<Void> ofByteArrayConsumer(Consumer<Optional<byte[]>> consumer)
	static BodyHandler<byte[]> ofByteArray()
		* ת��Ϊ�ֽ�����
	
	static BodyHandler<Publisher<List<ByteBuffer>>> ofPublisher()
	static <T> BodyHandler<T> buffering(BodyHandler<T> downstreamHandler, int bufferSize)



-----------------------------
PushPromiseHandler
-----------------------------
	# �ӿ�
	# ��̬����
		public static <T> PushPromiseHandler<T> of(Function<HttpRequest,BodyHandler<T>> pushPromiseHandler, ConcurrentMap<HttpRequest,CompletableFuture<HttpResponse<T>>> pushPromisesMap) {
            return new PushPromisesHandlerWithMap<>(pushPromiseHandler, pushPromisesMap);
        }
	
	# ���󷽷�
		public void applyPushPromise(HttpRequest initiatingRequest,HttpRequest pushPromiseRequest,Function<HttpResponse.BodyHandler<T>,CompletableFuture<HttpResponse<T>>> acceptor);
	

-----------------------------
��������
-----------------------------
	BodySubscriber
	BodySubscribers