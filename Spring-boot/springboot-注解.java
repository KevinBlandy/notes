----------------------------
Spring-Boot 注解			|
----------------------------
	@EnableAutoConfiguration
		# 属性
			Class<?>[] exclude() default {};
			String[] excludeName() default {};

		# 让 spring boot 根据类路径中的 jar 包依赖,为当前项目进行自动配置
		# 例如
			* 添加了 spring-boot-starter-web 依赖,就会自动添加Tomcat和springmvc进行自动的配置
			* 添加了 spring-boot-statter-data-jpa 依赖,就会自动进行JPA相关的配置
	
	@Configuration (spring)
		# 被@Configuration注解标识的类，通常作为一个配置类，这就类似于一个xml文件，表示在该类中将配置Bean元数据，
		# 其作用类似于Spring里面application-context.xml的配置文件，而 @Bean 标签，则类似于该xml文件中，声明的一个bean实例。 
	
	@SpringBootApplication
		# 部分属性
			Class<?>[] exclude() default {};
				* 该属性中的 Class 配置,会被关闭自动配置

		# 这是一个组合注解,组合了一下的注解
			* @SpringBootConfiguration		
				* 空的注解,该注解上标识了:@Configuration
			* @EnableAutoConfiguration 
			* @ComponentScan (包扫描路径)
		# spring boot 还会自动扫描该注解标识类所在包,同级包,以及子包中Bean
			* 如果是JPA项目,还可以扫描标注@Entity的实体类
		# 建议入口类放置的位置在 grouId + arctifactId 组合的包名下
	

	@ConfigurationProperties
		# 属性
			prefix
				* 指定properties中属性统一的前缀
			ignoreUnknownFields 
				* 是否忽略未匹配到的属性

		# 该注解标识在某个bean,用于把外部配置文件中的数据,注入到bean的属性中
		# 还可以标注在 @Configuration 类中的某个方法上,该方法(该方法同时也要标注@Bean注解才会被spring管理)返回对象的属性值也可以由该注解注入(prefix属性指定前缀)
	

	@EnableConfigurationProperties
		# 属性
			Class<?>[] value() default {};
		# 把一个 被 @ConfigurationProperties 标识的类,加入到IOC容器
	
			
	@ServletComponentScan
		# 在 SpringBootApplication 上使用@ServletComponentScan
		# 注解后，Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。
	


	@AutoConfigureAfter
		# 只有两个属性
			Class<?>[] value() default {};
			String[] name() default {};
		
		# 用在自动配置类上面,表示该自动配置类需要在另外指定的自动配置类配置完之后
		# 例如 Mybatis 的自动配置类,需要在数据源自动配置类之后
			@AutoConfigureAfter(DataSourceAutoConfiguration.class)
			public class MybatisAutoConfiguration {
		
	@AutoConfigureBefore
		# 同上,通过该注解指定的配置类,应该要在当前类之后配置
	

	@ImportResource
		# 属性
			String[] value() default {};
			String[] locations() default {};
			Class<? extends BeanDefinitionReader> reader() default BeanDefinitionReader.class;
	
		# 通过该注解导入spring的xml配置文件
		# 支持从文件系统或者classpath下加载
	
	
	@PropertySource
		# 加载外部配置文件
			String name() default "";
			String[] value();
			boolean ignoreResourceNotFound() default false;
			String encoding() default "";
			Class<? extends PropertySourceFactory> factory() default PropertySourceFactory.class;

		# 跟标签加载一样
			<context:property-placeholder location="classpath:jdbc.properties" />

		# 支持从不同的源加载数据
			@PropertySource(value = { "file:c:/application.properties","classpath:app.properties"})

	@Profile
		# 只有一个属性
		# 可以标识在类,方法上,指定一个或者多个激活的配置文件名
			String[] value();
		# 关联的配置
			spring.profiles.active=dev
		# 使用demo
			@Profile("dev")  // 只有激活了dev配置文件时,才会加载该controller
			@RestController
			public class ProdController
		
		# 在程序中, 通过代码来判断当前的环境
			@Autowired
			Environment environment;

			// 构建2个运行环境
			Profiles profiles = Profiles.of("dev", "test");

			// 判断当前环境, 是否在指定的运行环境中
			boolean accept = environment.acceptsProfiles(profiles);
	
	@RequestPart
		# 在mutipart请求中，代理 @RequestParam 标识mutipart中的请求项
		# 它可以自动的根据ContentType封装数据为对象
			test (@RequestPart("file") MultipartFile file,  @RequestPart("json") RequestBody jsonBody)
			
		String value() default "";
		String name() default "";
		boolean required() default true;