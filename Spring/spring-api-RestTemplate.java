----------------------------
RestTemplate				|
----------------------------
	# RestTemplate
		* Spring RestTemplate 是 Spring 提供的用于访问 Rest 服务的客户端
		* RestTemplate 提供了多种便捷访问远程Http服务的方法,能够大大提高客户端的编写效率
		* 很客户端比如 Android或者第三方服务商都是使用 RestTemplate 请求 restful 服务
	
	# 参考资料
			http://www.jianshu.com/p/c9644755dd5e

	# 主要构成
		HttpMessageConverter
			* 对象转换器

		ClientHttpRequestFactory 
			* HTTP请求工厂,默认是JDK的HttpURLConnection,可以通过使用ClientHttpRequestFactory指定不同的HTTP请求方式,设置ssl等

		ResponseErrorHandler
			* 异常错误处理

		ClientHttpRequestInterceptor
			*  请求拦截器

----------------------------
RestTemplate-实例创建		|
----------------------------
	# 直接创建
		new RestTemplate()
	
	# 工厂创建
		new RestTemplateBuilder().build();
	

			


----------------------------
RestTemplate-api			|
----------------------------
	# 属性设置相关
		public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters)
			* 设置HTTP消息解析器
		
		public List<HttpMessageConverter<?>> getMessageConverters()
			* 获取默认的HTTP消息解析器
			* 默认注册
				org.springframework.http.converter.ByteArrayHttpMessageConverter
				org.springframework.http.converter.StringHttpMessageConverter
				org.springframework.http.converter.ResourceHttpMessageConverter
				org.springframework.http.converter.xml.SourceHttpMessageConverter
				org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
				org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
				org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
	
	# HTTP请求相关的api
		* RestTemplate定义了36个与REST资源交互的方法,其中的大多数都对应于HTTP的方法
		* 其实,这里面只有11个独立的方法,其中有十个有三种重载形式,而第十一个则重载了六次,这样一共形成了36个方法

			delete() 
				* 在特定的URL上对资源执行HTTP DELETE操作
			exchange() 
				* 在URL上执行特定的HTTP方法,返回包含对象的ResponseEntity,这个对象是从响应体中 映射得到的
			execute()
				* 在URL上执行特定的HTTP方法，返回一个从响应体映射得到的对象
			getForEntity() 
				* 发送一个HTTP GET请求,返回的ResponseEntity包含了响应体所映射成的对象
			getForObject()
				* 发送一个HTTP GET请求,返回的请求体将映射为一个对象
			postForEntity() 
				* POST 数据到一个URL,返回包含一个对象的ResponseEntity,这个对象是从响应体中映射得 到的
			postForObject() 
				* POST 数据到一个URL,返回根据响应体匹配形成的对象
			headForHeaders()
				* 发送HTTP HEAD请求,返回包含特定资源URL的HTTP头
			optionsForAllow()
				* 发送HTTP OPTIONS请求,返回对特定URL的Allow头信息
			postForLocation()
				* POST 数据到一个URL,返回新创建资源的URL
			put() 
				* PUT 资源到特定的URL

----------------------------
RestTemplate-POST			|
----------------------------
	RestTemplate restTemplate = new RestTemplate();
	//请求头
	HttpHeaders httpHeaders = new HttpHeaders();
	httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
	//构建请求体
	HttpEntity<String> httpEntity = new HttpEntity<>("{\"name\":\"KevinBlandy\"}",httpHeaders);
	//执行REST请求,获取结果
	ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost/user", httpEntity, String.class);
	