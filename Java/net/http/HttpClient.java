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
		 public static Builder newBuilder()
	
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

		abstract <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)
			* 同步请求

		abstract <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler)
		abstract <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler, PushPromiseHandler<T> pushPromiseHandler)
			* 异步请求, 返回 CompletableFuture

	
	# 实例方法
		WebSocket.Builder newWebSocketBuilder()

			* 创建一个 WebScoket 的Builder


