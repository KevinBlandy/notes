---------------------
feign
---------------------
	# Open Feign
		* spring cloud 自己开发的东西

	# 地址
		https://github.com/spring-cloud/spring-cloud-openfeign
		https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/
		https://github.com/OpenFeign/feign
	
	# Maven
		<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
			<version>3.0.3</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-loadbalancer -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-loadbalancer</artifactId>
			<version>3.0.3</version>
		</dependency>
		 <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
		<dependency>
			<groupId>com.github.ben-manes.caffeine</groupId>
			<artifactId>caffeine</artifactId>
		</dependency>

		
		* 多出来的几个依赖都建议用
		* 一般都要添加这个负载均衡的依赖
		* 负载均衡又需要缓存服务在本地，所以推荐使用caffeine

	
	# 启用注解
		@EnableFeignClients
			String[] value() default {};
			String[] basePackages() default {};
				* 指定 Feign接口所在的包

			Class<?>[] basePackageClasses() default {};
			Class<?>[] defaultConfiguration() default {};
				* 指定 Feign 标识接口

			Class<?>[] clients() default {};
				* 手工指定 feign 接口类
		
	

	# 在 Feign 接口声明注解
		@FeignClient
			String value() default "";
			String name() default "";
				* 指定主机名, 或者是服务名

			String contextId() default "";
			
			@Deprecated
			String qualifier() default "";
			String[] qualifiers() default {};
			String url() default "";
			boolean decode404() default false;
			Class<?>[] configuration() default {};
				* 自定义配置类
				* 这个配置类 Configuration 中可以配置这些 @Bean
					feign.codec.Decoder
					feign.codec.Encoder
					feign.Contract.

			Class<?> fallback() default void.class;
				* 指定服务降级类(就是失败的重试方法)
				* 该类应该实现当前的Client接口,并且覆写接口方法,这些方法就是接口的降级方法
			
			Class<?> fallbackFactory() default void.class;
				* 服务降级工厂类，需要实现接口: FallbackFactory<T>

			String path() default "";
				* 绝对路径URL

			boolean primary() default true;
				* 和断路器一起使用的时候，IOC会存在多个当前接口的实现，使用 @Autowired 注入的时候就会导致不知道注入哪个，从而导致异常

				* 默认为 true ，spring 把所有的 Feign 实现都标记了: @Primary 那么使用 接口进行注入的时候，就默认会选择 Feign 实现而不是断路器实现
				* 如果要取消 @Primary 标注，那么可以把这个设置为 false

---------------------
核心组件
---------------------

---------------------
feign一些点
---------------------
	# 参数都需要使用注解声明, 并且只支持最原始的springmvc注解, 并且需要声明value参数
		@RequestMapping
		@RequestHeader
		@RequestParam
		@RequestBody
		@RequestPart
		
		* 其实从2.2以后，就不是必选的了，但还是建议写
		* 该有的声明，都建议写上，包括 consumer/producer 属性

		* 尽量使用Spring的注解，好像使用 Feign 原生的注解有一些问题
	

		

	

	
