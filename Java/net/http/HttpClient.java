-------------------------
HttpClient
-------------------------
	# 抽象类, 主要负责管理如下组件
		* 验证器
		* cookie管理器
		* 执行器
		* 重定向策略
		* 请求优先级
		* 代理选择器
		* SSL上下文
		* SSL参数
		* HTTP版本
		

		* 没有setter方法, 它是不可变的
	
	# 静态方法
		 public static HttpClient newHttpClient()
		 	* 创建默认的执行器
		 public static Builder newBuilder()
		 	* 创建Builder
	
	# 抽象方法
		Optional<CookieHandler> cookieHandler()
		abstract Optional<Duration> connectTimeout()
		abstract Redirect followRedirects()
			* 重定向策略枚举
				NEVER
					* 永远也不重定向

				ALWAYS
					* 总是自动重定向

				NORMAL
					* 除了从HTTPS重定向到HTTP以外, 都会自动重定向


		abstract Optional<ProxySelector> proxy()
		abstract SSLContext sslContext()
		abstract SSLParameters sslParameters()
		abstract Optional<Authenticator> authenticator()
		abstract HttpClient.Version version()
			* 枚举HTTP版本
				HTTP_1_1
				HTTP_2

		abstract Optional<Executor> executor()
			* 返回执行器

		abstract <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)
			* 同步请求

		abstract <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler)
		abstract <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler, PushPromiseHandler<T> pushPromiseHandler)
			* 异步请求, 返回 CompletableFuture
		
		public void shutdown()
			* 关闭 HTTP 客户端
	
		public void shutdownNow()
		
		public boolean awaitTermination(Duration duration) throws InterruptedException
		public boolean isTerminated()

	
	# 实例方法
		WebSocket.Builder newWebSocketBuilder()

			* 创建一个 WebScoket 的Builder


---------------------
Builder
---------------------
	# 构建接口
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