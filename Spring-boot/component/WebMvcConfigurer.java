----------------------------
WebMvcConfigurer			|
----------------------------
	# WEBMVC的配置接口

	public interface WebMvcConfigurer {

		default void configurePathMatch(PathMatchConfigurer configurer) {}

		default void configureContentNegotiation(ContentNegotiationConfigurer configurer) {}

		default void configureAsyncSupport(AsyncSupportConfigurer configurer) {}

		default void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {}

		default void addFormatters(FormatterRegistry registry) {}

		default void addInterceptors(InterceptorRegistry registry) {}
			* 配置拦截器

		default void addResourceHandlers(ResourceHandlerRegistry registry) {}

		default void addCorsMappings(CorsRegistry registry) {}
			* cors跨域配置

		default void addViewControllers(ViewControllerRegistry registry) {}
			* 添加视图控制器
				@Override
				public void addViewControllers(ViewControllerRegistry registry) {
					// resources/static/index.html
					registry.addViewController("/").setViewName("/index.html");
				}
			
			* 如果是添加了 static-path-pattern 配置的话，需要在添加前缀
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
