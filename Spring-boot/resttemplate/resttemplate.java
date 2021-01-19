-----------------------
RestTemplate		   |
-----------------------
	# http客户端
	# 基础自: InterceptingHttpAccessor 实现: RestOperations
	# 核心的组件
		private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			* 消息转换器
			
		private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();
			* 异常处理器

		private final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
			* 请求拦截器

		private ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			* http请求工厂

	# 构造函数
		public RestTemplate(ClientHttpRequestFactory requestFactory)
		public RestTemplate()
		public RestTemplate(List<HttpMessageConverter<?>> messageConverters)

	
	# 设置消息转换器
		* 用于序列化/反序列化对象为http消息
		* 消息转换器就是:HttpMessageConverter<T> 接口的实现类
		
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConfig.setCharset(StandardCharsets.UTF_8);
		fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);

		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		fastJsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

		
		restTemplate.setMessageConverters(Arrays.asList(fastJsonHttpMessageConverter));
	
	# 设置拦截器
		* 很简单,就是一个责任链模式

		restTemplate.setInterceptors(Arrays.asList(new ClientHttpRequestInterceptor(){
			/**
			 * @param request		请求对象
			 * @param body			请求体
			 * @param execution		执行器
			 * @return
			 * @throws IOException
			 */
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
				// 执行下一个拦截器,并且获取到响应
				ClientHttpResponse clientHttpResponse = execution.execute(request,body);
				// 返回响应到上一个拦截器
				return clientHttpResponse;
			}
		}));
	
	# 设置异常处理器
		* ResponseErrorHandler 实现类

		restTemplate.setErrorHandler(new ResponseErrorHandler(){
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {

			}

			@Override
			public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {

			}
		});

-------------------------
ClientHttpRequestFactory |
-------------------------
	# 连接工厂对象它是一个接口
		ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException;

	# 已经提供的实现
		SimpleClientHttpRequestFactory
		OkHttp3ClientHttpRequestFactory


	# RestTemplate 操作 ClientHttpRequestFactory 的api
		ClientHttpRequestFactory getRequestFactory()
		void setRequestFactory(ClientHttpRequestFactory requestFactory)
	
	
	# 设置自定义的Factory
		OkHttpClient okHttpClient = new OkHttpClient();
		OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(okHttpClient);

		restTemplate.setRequestFactory(okHttp3ClientHttpRequestFactory);
