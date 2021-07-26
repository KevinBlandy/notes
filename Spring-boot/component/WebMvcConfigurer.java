----------------------------
WebMvcConfigurer			|
----------------------------
	# WEBMVC�����ýӿ�

	public interface WebMvcConfigurer {

		default void configurePathMatch(PathMatchConfigurer configurer) {}

		default void configureContentNegotiation(ContentNegotiationConfigurer configurer) {}

		default void configureAsyncSupport(AsyncSupportConfigurer configurer) {}

		default void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {}

		default void addFormatters(FormatterRegistry registry) {}

		default void addInterceptors(InterceptorRegistry registry) {}
			* ����������

		default void addResourceHandlers(ResourceHandlerRegistry registry) {}

		default void addCorsMappings(CorsRegistry registry) {}
			* cors��������

		default void addViewControllers(ViewControllerRegistry registry) {}
			* �����ͼ������
				@Override
				public void addViewControllers(ViewControllerRegistry registry) {
					// resources/static/index.html
					registry.addViewController("/").setViewName("/index.html");
				}
			
			* ���������� static-path-pattern ���õĻ�����Ҫ�����ǰ׺
					// spring.mvc.static-path-pattern=/static/**
					registry.addViewController("/favicon.ico").setViewName("/static/favicon.ico");

		default void configureViewResolvers(ViewResolverRegistry registry) {}

		default void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {}

		default void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {}

		default void configureMessageConverters(List<HttpMessageConverter<?>> converters) {}

		default void extendMessageConverters(List<HttpMessageConverter<?>> converters) {}

		default void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {}

		default void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {}

		@Nullable
		default Validator getValidator() {return null;}

		@Nullable
		default MessageCodesResolver getMessageCodesResolver() {return null;}

	}
