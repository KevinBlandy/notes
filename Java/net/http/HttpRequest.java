---------------------------
HttpRequest
---------------------------
	# 表示一个 http 请求, 抽象类

	# 静态方法
		HttpRequest.Builder newBuilder(URI uri) 
		HttpRequest.Builder newBuilder()

	# 实例方法
		abstract Optional<BodyPublisher> bodyPublisher()
		abstract String method();
		abstract Optional<Duration> timeout();
			* 如果在指定的超时时间内未收到响应, 则会抛出HttpTimeoutException异常。

		abstract boolean expectContinue();
			* HTTP请求可能包含名为expect的首部字段, 其值为 "100-Continue",
			* 如果设置了此首部字段, 则客户端只会向服务器发送头文件, 并且预计服务器将发回错误响应或100-Continue响应
			* 收到此响应后, 客户端将请求主体发送到服务器, 在客户端发送实际请求体之前, 客户端使用此技术来检查服务器是否可以基于请求的首部处理请求
			
			* 默认情况下, 此首部字段未设置, 需要调用请求构建器的expectContinue(true)方法来启用此功能
			* 请注意, 调用请求构建器的header("expect", "100-Continue")方法不会启用此功能, 必须使用expectContinue(true)方法启用它
				
		abstract URI uri();
		abstract Optional<HttpClient.Version> version();
		abstract HttpHeaders headers();
		
	
	# Request 的属性会覆盖 HttpClient 中相同的属性
		HttpClient.Version
	
---------------------------
Builder
---------------------------
	# Builder构建方式
		Builder uri(URI uri);
		Builder expectContinue(boolean enable);
		Builder version(HttpClient.Version version);
		Builder header(String name, String value);
		Builder headers(String... headers);
			* 设置请求头

		Builder timeout(Duration duration);
		Builder setHeader(String name, String value);

		Builder GET();
		Builder POST(BodyPublisher bodyPublisher);
		Builder PUT(BodyPublisher bodyPublisher);
		Builder DELETE();

		Builder method(String method, BodyPublisher bodyPublisher);
		
		HttpRequest build();

		Builder copy();