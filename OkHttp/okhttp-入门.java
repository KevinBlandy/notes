---------------------------
okhttp-入门					|
---------------------------
	# 可以代替 HttpClient 工具包
	# 项目地址
		http://square.github.io/okhttp/
	# maven仓库
		<dependency>
			<groupId>com.squareup.okhttp3</groupId>
			<artifactId>okhttp</artifactId>
			<version>3.7.0</version>
		</dependency>
	
	
	# 在线doc文档
		https://square.github.io/okhttp/3.x/okhttp/
	

	# 组件
		OkHttpClient 
			* 相当于浏览器

		RequestBody
			* 请求体

		Request
			* 一次请求

		Response
			* 一次响应

		ResponseBody
			* 响应体

---------------------------
okhttp-client				|
---------------------------
	# 相当于浏览器的配置
		OkHttpClient client = new OkHttpClient.Builder()
			.followRedirects(false)					//禁止OkHttp的重定向操作,自己处理重定向
			.followSslRedirects(false)				//禁止ssl重定向(80 -> 443)
			.cookieJar(new CookieJar() {			//cookie的序列化与反序列化接口,需要自己实现
				@Override
				public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
				}
				@Override
				public List<Cookie> loadForRequest(HttpUrl url) {
					return null;
				}
			})
			.build();
	
		
		* 它应该是以单例的形式存在于应用中

---------------------------
okhttp-Request				|
---------------------------
	Request.Builder builder = new Request.Builder();
	# URL相关
		url(URL url);
		url(String url);

	# 请求方法相关
		delete(RequestBody body);
		get();
		post(RequestBody body);

	# 请求头相关
		addHeader(String name, String value);
		removeHeader(String name);
		headers(Headers headers);
	
	# 创建 Request 对象
		build();

---------------------------
okhttp-RequestBody			|
---------------------------
	

---------------------------
okhttp-Response				|
---------------------------
	# 获取响应体
		ResponseBody body();
	
	# 判断相关
		boolean isRedirect();
			* 是否是重定向
		boolean isSuccessful();
			* 是否请求OK

	# 响应头相关
		String header(String headerName);
			* 获取指定名称的响应头

		String header(String name, String defaultValue);
			* 获取指定名称的响应头
			* 如果不存在,则返回默认值

		Headers headers();
			* 返回所有响应头

		List<String> headers(String name);
			* 获取指定响应头的所有值


---------------------------
okhttp-ResponseBody			|
---------------------------
	byte[] bytes();
	InputStream byteStream();
	Reader charStream();
	long contentLength();
	MediaType contentType();
	String string();

