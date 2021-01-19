--------------------------------
WebMvcConfigurer				|
--------------------------------
	# 是一个接口,用于对Web项目的配置
	

--------------------------------
WebMvcConfigurer-default 方法	|
--------------------------------
	void configurePathMatch(PathMatchConfigurer configurer) 
	void configureContentNegotiation(ContentNegotiationConfigurer configurer) 
	void configureAsyncSupport(AsyncSupportConfigurer configurer) 
	void configureServletHandling(ServletHandlerConfigurer configurer) 
	void addFormatters(FormatterRegistry registry) 
	void addInterceptors(InterceptorRegistry registry) 
		# 拦截器配置

	void addResourceHandlers(ResourceHandlerRegistry registry) 
		# 静态资源映射配置

	void addCorsMappings(CorsRegistry registry) 
		# cors跨域配置

	void addViewControllers(ViewControllerRegistry registry) 
	void configureViewResolvers(ViewResolverRegistry registry) 
	void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) 
	void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) 
	void configureMessageConverters(List<HttpMessageConverter<?>> converters) 
		# 注册http消息转换器

	void extendMessageConverters(List<HttpMessageConverter<?>> converters) 
	void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) 
	void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) 
	Validator getValidator() 
	MessageCodesResolver getMessageCodesResolver() 

