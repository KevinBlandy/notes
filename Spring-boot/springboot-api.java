SpringApplication


WebMvcConfigurerAdapter
	# Web系列的配置
	# 注册,Spring Interceptor
		public void addInterceptors(InterceptorRegistryregistry);
	# 添加,静态资源映射路径
		public void addResourceHandlers(ResourceHandlerRegistry registry);
	# 配置 Converter 消息转换器
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters);
	# 添加视图映射
		public void addViewControllers(ViewControllerRegistry registry) 
	# 添加cors映射
		public void addCorsMappings(CorsRegistry registry) { registry.addMapping("/api/*").allowedOrigins("*"); }

SpringBootServletInitializer
	# 可以以war形式运行在Servlet容器的支持
	# 覆写方法
		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
			//指定 @SpringBootApplication 所在类
			return application.sources(Main.class);
		}

ServletContextInitializer
	# Servlet初始化的配置Bean
	# 可以进行配置初始化 context-param 

EmbeddedServletContainerCustomizer
	# 统一的 Servlet 容器配置

EmbeddedServletContainerFactory
	# 容器特别配置类的父类
	# 子类
		TomcatEmbeddedServletContainerFactory
		JettyEmbeddedServletContainerFactory


