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

	
	# ʵ������
		WebSocket.Builder newWebSocketBuilder()

			* ����һ�� WebScoket ��Builder


