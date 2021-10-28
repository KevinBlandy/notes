----------------------------
Spring-Boot 基本配置		|
----------------------------
	# 入口类配置
		* 一般应用都会有一个 XxxxApplication 类,该就一个 main 方法,并且该类被 @SpringBootApplication 标注
		* @SpringBootApplication 这是一个组合注解,组合了以下的注解
			* @Configuration 
			* @EnableAutoConfiguration 
			* @ComponentScan 

		* spring boot 还会自动扫描该注解标识类所在包,同级包,以及子包中Bean
			* 如果是JPA项目,还可以扫描标注@Entity的实体类

		* 建议入口类放置的位置在 grouId + arctifactId 组合的包名下
	
	# 关闭特定的自动配置
		* @SpringBootApplication 的一个属性
			Class<?>[] exclude() default {};

		* 该属性中的 Class 配置,会被关闭自动配置
			@SpringBootApplication(exclude = {DataSourceConfiguration.class});
	
	# 定制Banner
		* spring 启动的时候会有一个默认的启动图案
			  .   ____          _            __ _ _
			 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
			( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
			 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
			  '  |____| .__|_| |_|_| |_\__, | / / / /
			 =========|_|==============|___/=/_/_/_/
			 :: Spring Boot ::        (v1.5.1.RELEASE)
		* 在 src/main/resources 下新建文件:banner.txt
		* 打开网站,创作出喜欢的样式:http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20 
		* 复制到 banner.txt中,重启程序
		* 关闭banner,有两张方法
			1,在main里面进行修改
				SpringApplication springApplication = new SpringApplication(SampleController.class);
				//设置Banner为关闭模式
				springApplication.setBannerMode(Banner.Mode.OFF);
				springApplication.run(args);

			2,使用 fluent API 进行修改
				new SpringApplicationBuilder(SampleController.class).bannerMode(Banner.Mode.OFF).run(args);
		* 而且,Banner里面是可以使用一些特殊的变量 ${name}
				
				
	# 配置文件
		* spring boot 使用一个全局的配置文件:application.properties / application.yml
		* 该文件应该是在 src/main/resources 目录,或者是classpath:/config 目录下
		* 可选配置项(很多)
			server.port				//端口
			server.context-path		//上下文路径


	# 使用 xml 配置
		* spring boot 提倡无xml配置,但是在实际项目中,可能有特殊的需求
		* 使用 @ImportResource 来加载xml配置,该注解是 spring 的注解
		* @ImportResource({"classpath:xxx.xml","classpath:yyy.xml"});
				
	
	# 随机值的生成
		* 有时候我们项目需要生成随机的一些值,我们并不用手动的去写代码
		* RandomValuePropertySource 可以用来生成测试所需要的各种不同类型的随机值，从而免去了在代码中生成的麻烦。
		* RandomValuePropertySource 可以生成数字和字符串。数字的类型包含 int 和 long，可以限定数字的大小范围。
		* 在配置文件中以"random."作为前缀的配置属性
			user.id=${random.value}
			user.count=${random.int}
			user.max=${random.long}
			user.number=${random.int(100)}
			user.range=${random.int[100, 1000]}

----------------------------
Spring-Boot 外部配置		|
----------------------------
	# spring boot 允许开发人员使用属性文件、YAML 文件、环境变量和命令行参数来定义优先级不同的配置值。
	# Spring Boot 提供的 SpringApplication 类会搜索并加载 application.properties 文件来获取配置属性值。SpringApplication 类会在下面位置搜索该文件。
		1,当前目录的"/config"子目录。
		2,当前目录。
		3,classpath 中的"/config"包。
		4,classpath
		* 上面的顺序也表示了该位置上包含的属性文件的优先级(从高到低)

	# 配置优先级顺序比较复杂。按照优先级从高到低的顺序
		1,命令行参数。
		2,通过 System.getProperties() 获取的 Java 系统参数。
		3,操作系统环境变量。
		4,从 java:comp/env 得到的 JNDI 属性。
		5,通过 RandomValuePropertySource 生成的“random.*”属性。
		6,应用 Jar 文件之外的属性文件。
		7,应用 Jar 文件内部的属性文件。
		8,在应用配置 Java 类（包含"@Configuration"注解的 Java 类）中通过"@PropertySource注解声明的属性"文件。
		9,通过 "SpringApplication.setDefaultPrope"ties"声明的默认属性。

	# 命名行参数配置
		* spring boot 可以基于jar包运行,打包成jar后可以在运行命令后添加参数
			java -jar xxx.jar --server.port=9090
			java -jar xxx.jar --spring.profiles.active=remote
		* SpringApplication 类默认会把以"--"开头的命令行参数转化成应用中可以使用的配置参数，如 "--name=Alex" 会设置配置参数 "name" 的值为 "Alex"。
		* 如果不需要这个功能，可以通过  SpringApplication.setAddCommandLineProperties(false) 禁用解析命令行参数。

	# 常规属性配置
		* 在应用程序中,直接使用 @Value("${key}"),就可以读取到 application.properties 中的数据
	
	# 类型安全的配置
		* 使用 @ConfigurationProperties 和一个bean及其属性进行关联
		* Demo
			1,在 application.properties 中添加属性
				user.name=kevin

			2,使用注解,默认的使用 application.properties
				@ConfigurationProperties(prefix="user")
				class User{
					private String name;
				}

	# 日志配置
		* spring boot 支持 java.util.Logging,Log4j,lo4j2和logback作为日志框架
		* 默认,使用logback作为日志框架

		* 默认的配置文件配置(不建议使用,不够灵活,对log4j2不够友好)
			> 配置日志文件地址
				logging.file=d:/mylog/log.log
			> 配置日志输出级别
				logging.level.root=INFO
				logging.level.org.springframework.web=DEBUG		
				

		* 引用外部文件的配置(推荐使用)
			> spring boot 会默认加载:classpath:logback-spring.xml或者classpath:logback-spring.groovy

			> 使用自定义配置文件,指定配置文件的地址
				logging.config=classpath:logback-xxx.xml
				* 注意：不要使用logback这个来命名，否则spring boot将不能完全实例化
				* 配置文件有两种方式创建

			> 1,使用基于spring boot的配置
				<?xml version="1.0" encoding="UTF-8"?>
					<configuration>
					<include resource="org/springframework/boot/logging/logback/base.xml"/>
					<logger name="org.springframework.web" level="DEBUG"/>
				</configuration>

			> 2,自定义的配置
				* 略,自己去看logback的配置
								
			
	# profile 环境配置
		* 不同的环境,使用不同的profile
		* 每个profile的格式固定
			application-{name}.properties

		* application.properties文件中激活指定的文件
			spring.profiles.active={name}

	# 导入多个外部配置文件
		spring.profiles.include[0]=datasource
			|-application-datasource.properties
		spring.profiles.include[1]=redis
			|-application-redis.properties

	# bootstrap.yml
		* bootstrap.yml(bootstrap.properties)用来程序引导时执行,应用于更加早期配置信息读取

		* 可以使用来配置application.yml中使用到参数等

		* application.yml(application.properties) 应用程序特有配置信息,可以用来配置后续各个模块中需使用的公共参数等

		* bootstrap.yml 先于 application.yml 加载

		* 可以通过设置来禁用bootstrap
			spring.cloud.bootstrap.enabled=false
	

------------------------
多环境配置				|
------------------------
	# application.properties属性文件会被SpringBoot应用自动加载, 而且有一个加载顺序
		1. 当前目录的/config子目录下
		2. 当前目录下
		3. classpath目录的/config子目录下
		4. classpath目录下

		* private static final String DEFAULT_SEARCH_LOCATIONS = "classpath:/,classpath:/config/,file:./,file:./config/";
		* 列表高的配置, 覆盖列表低的配置
	
	# 通过Environment属性 spring.config.name 可以自定义applicaiton.properties文件的名称
		ConfigFileApplicationListener.CONFIG_NAME_PROPERTY = "spring.config.name";
	
	# 通过Environment属性 spring.config.location 自定义 applicaiton.properties 文件的位置
		ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY = "spring.config.location";

	# 通过Environment属性 spring.config.additional-location 自定义 applicaiton.properties 文件的位置
		ConfigFileApplicationListener.CONFIG_ADDITIONAL_LOCATION_PROPERTY="spring.config.additional-location";

		* spring.config.location 会覆盖默认的搜索路径
		* 它只是追加, 追加的位置是在默认的位置之前
	
	# 这几个配置要在应用启用之前配置, 所以需要将其配置到'系统环境变量'或者'系统参数'或者'命令行参数'中优先读取


	# 指定配置文件的文件夹
		* 生产环境,和开发环境的配置文件不一定非要存储在项目中
		* 可以存储在本地磁盘, 使用配置设置
			ConfigFileApplicationListener.CONFIG_ADDITIONAL_LOCATION_PROPERTY="spring.config.additional-location";
		
		* 可以在代码里面设置, 如果有多个值, 使用逗号分隔
			String configLocation = "file:${user.home}" + File.separator + "config" + File.separator;
			System.setProperty(ConfigFileApplicationListener.CONFIG_ADDITIONAL_LOCATION_PROPERTY, configLocation);


			System.setProperty("spring.config.additional-location", "optional:file:${user.home}/config/,optional:file:${user.home}/config-dev/");

		
		* 支持使用 spel 表达式
		* 建议项目工程中不要存储生产环境的相关配置, 生产环境的配置, 通过 'spring.config.additional-location' 制定存储在生产服务器的磁盘上

	
	# 也可以通过配置来指定额外的配置文件加载目录
		spring.config.additional-location="optional:file:${user.home}\\config"

		* 但是这个要配置在 boostrap.yml 中才会生效，需要添加spring-cloud-context
			<dependency> 
				<groupId>org.springframework.cloud</groupId> 
				<artifactId>spring-cloud-context</artifactId> 
			</dependency>
		
	
	# springboot2.4后，是通过 EnvironmentPostProcessor 来完成动态配置文件的扩展

		

------------------------
Environment				|
------------------------
	# org.springframework.core.env.Environment
	# 该对象在IOC,表示当前spring的所有配置信息
		
		@Autowired
		private Environment env;
		
		//获取当前激活的的文件名称,也就是:spring.profiles.active 指定的文件
		String[] profiles = env.getActiveProfiles();

		//可以从配置中获取数据
		String dbPass = env.getProperty("bbs.dbPassowrd");