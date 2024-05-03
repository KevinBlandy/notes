-------------------------
HttpClient
-------------------------
	# ������, ��Ҫ��������������
		* ��֤��
		* cookie������
		* ִ����
		* �ض������
		* �������ȼ�
		* ����ѡ����
		* SSL������
		* SSL����
		* HTTP�汾
		

		* û��setter����, ���ǲ��ɱ��
	
	# ��̬����
		 public static HttpClient newHttpClient()
		 	* ����Ĭ�ϵ�ִ����
		 public static Builder newBuilder()
		 	* ����Builder
	
	# ���󷽷�
		Optional<CookieHandler> cookieHandler()
		abstract Optional<Duration> connectTimeout()
		abstract Redirect followRedirects()
			* �ض������ö��
				NEVER
					* ��ԶҲ���ض���

				ALWAYS
					* �����Զ��ض���

				NORMAL
					* ���˴�HTTPS�ض���HTTP����, �����Զ��ض���


		abstract Optional<ProxySelector> proxy()
		abstract SSLContext sslContext()
		abstract SSLParameters sslParameters()
		abstract Optional<Authenticator> authenticator()
		abstract HttpClient.Version version()
			* ö��HTTP�汾
				HTTP_1_1
				HTTP_2

		abstract Optional<Executor> executor()
			* ����ִ����

		abstract <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)
			* ͬ������

		abstract <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler)
		abstract <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler, PushPromiseHandler<T> pushPromiseHandler)
			* �첽����, ���� CompletableFuture
		
		public void shutdown()
			* �ر� HTTP �ͻ���
	
		public void shutdownNow()
		
		public boolean awaitTermination(Duration duration) throws InterruptedException
		public boolean isTerminated()

	
	# ʵ������
		WebSocket.Builder newWebSocketBuilder()

			* ����һ�� WebScoket ��Builder


---------------------
Builder
---------------------
	# �����ӿ�
		interface Builder 

		public static final ProxySelector NO_PROXY = ProxySelector.of(null);
		public Builder cookieHandler(CookieHandler cookieHandler);
		public Builder connectTimeout(Duration duration);
		public Builder sslContext(SSLContext sslContext);
		public Builder sslParameters(SSLParameters sslParameters);
		public Builder executor(Executor executor);
		public Builder followRedirects(Redirect policy);
		public Builder version(HttpClient.Version version);
		public Builder priority(int priority);
		public Builder proxy(ProxySelector proxySelector);
		public Builder authenticator(Authenticator authenticator);
		public Builder localAddress(InetAddress localAddr);
		public HttpClient build();