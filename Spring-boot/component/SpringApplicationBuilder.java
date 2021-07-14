---------------------------------
SpringApplicationBuilder
---------------------------------
	# Spring app ≈‰÷√¿‡

		
		public SpringApplicationBuilder(Class<?>... sources)
		protected SpringApplication createSpringApplication(Class<?>... sources)

		public ConfigurableApplicationContext context()
		public SpringApplication application()

		public ConfigurableApplicationContext run(String... args)

		public SpringApplication build()
		public SpringApplication build(String... args)

		public SpringApplicationBuilder child(Class<?>... sources) 
		public SpringApplicationBuilder parent(Class<?>... sources)
		public SpringApplicationBuilder parent(ConfigurableApplicationContext parent)
		public SpringApplicationBuilder sibling(Class<?>... sources)
		public SpringApplicationBuilder sibling(Class<?>[] sources, String... args)
		public SpringApplicationBuilder contextFactory(ApplicationContextFactory factory)
		public SpringApplicationBuilder sources(Class<?>... sources)
		public SpringApplicationBuilder web(WebApplicationType webApplicationType)
		public SpringApplicationBuilder logStartupInfo(boolean logStartupInfo)
		public SpringApplicationBuilder banner(Banner banner) 
		public SpringApplicationBuilder bannerMode(Banner.Mode bannerMode) 
		public SpringApplicationBuilder headless(boolean headless)
		public SpringApplicationBuilder registerShutdownHook(boolean registerShutdownHook)
		public SpringApplicationBuilder main(Class<?> mainApplicationClass)
		public SpringApplicationBuilder addCommandLineProperties(boolean addCommandLineProperties)
		public SpringApplicationBuilder setAddConversionService(boolean addConversionService)
		public SpringApplicationBuilder addBootstrapRegistryInitializer(BootstrapRegistryInitializer bootstrapRegistryInitializer)
		public SpringApplicationBuilder lazyInitialization(boolean lazyInitialization)
		public SpringApplicationBuilder properties(String... defaultProperties) 
		public SpringApplicationBuilder properties(Properties defaultProperties)
		public SpringApplicationBuilder properties(Map<String, Object> defaults)
		public SpringApplicationBuilder profiles(String... profiles)
		public SpringApplicationBuilder beanNameGenerator(BeanNameGenerator beanNameGenerator)
		public SpringApplicationBuilder environment(ConfigurableEnvironment environment)
		public SpringApplicationBuilder environmentPrefix(String environmentPrefix)
		public SpringApplicationBuilder resourceLoader(ResourceLoader resourceLoader)
		public SpringApplicationBuilder initializers(ApplicationContextInitializer<?>... initializers)
		public SpringApplicationBuilder listeners(ApplicationListener<?>... listeners)
		public SpringApplicationBuilder applicationStartup(ApplicationStartup applicationStartup) 

