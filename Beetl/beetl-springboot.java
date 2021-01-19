-------------------------------
Spring-boot						|
-------------------------------
	# Spring Boot 通过java config来配置 beetl
	# 需要的 BeetlGroupUtilConfiguration，和 BeetlSpringViewResolver，
		* BeetlGroupUtilConfiguration，和
		* BeetlSpringViewResolver，

	# spring boot集成需要注意的是要添加spring-devtools.properties文件,并配置如下选项
		restart.include.beetl=/beetl-xxx.jar
		restart.include.beetlsql=/beetlsql-xxx..jar
		* spring-devtools.properties 为spring boot的配置文件,位于META-INF目录下

	# 也可以添加 beetl.properties 去完成一些配置
		* 需要注意的是,尽量就不要在 beetl.properties 去配置 RESOURCE.root=/templates/ 属性,也许要出事

-------------------------------
Spring-boot		官方			|
-------------------------------
	1,配置类
		@Configuration
		public class BeetlConfiguration {

			/**
			 * 模板根目录
			 */
			@Value("${beetl.templatesPath}")
			private String templatesPath;

			@Bean(initMethod = "init", name = "beetlConfig")
			public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
				BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
				//获取Spring Boot 的ClassLoader
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				if(classLoader == null){
					classLoader = BeetlConfiguration.class.getClassLoader();
				}
				//beetlGroupUtilConfiguration.setConfigProperties(extProperties);
				ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader(classLoader, templatesPath);
				beetlGroupUtilConfiguration.setResourceLoader(classpathResourceLoader);
				beetlGroupUtilConfiguration.init();
				//如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
				beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(classLoader);
				return beetlGroupUtilConfiguration;
			}

			@Bean(name = "beetlViewResolver")
			public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
				BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
				beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
				beetlSpringViewResolver.setOrder(0);
				//beetlSpringViewResolver.setPrefix("/");
				beetlSpringViewResolver.setSuffix(".html");
				beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
				return beetlSpringViewResolver;
			}
		}
	
	2,配置
		beetl.templatesPath=templates
	
	3,Controller
		return new ModelAndView("/index/index");
	
	4,include
		include('/common/common_header.html',{}){}

-------------------------------
Spring-boot		我的			|
-------------------------------
	1,配置类
		/**
		 * Created by KevinBlandy on 2017/3/2 11:24
		 */
		@Configuration
		public class BeetlConfiguration {
			
			/**
			 * 模板根目录 
			 */
			@Value("${beetl.templatesPath}") 
			private String templatesPath;		
			
			@Bean(initMethod = "init", name = "beetlConfig")
			public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
				BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
				try {
					//使用 ClasspathResourceLoader
					System.err.println(templatesPath);
					ClasspathResourceLoader cploder = new ClasspathResourceLoader(BeetlConfiguration.class.getClassLoader(),templatesPath);
					beetlGroupUtilConfiguration.setResourceLoader(cploder);
					return beetlGroupUtilConfiguration;
				} catch (Exception e) {
						throw new RuntimeException(e);
				}

			}
			@Bean(name = "beetlViewResolver")
			public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
					BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
					beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
					beetlSpringViewResolver.setOrder(0);
					//beetlSpringViewResolver.setPrefix("/");
					beetlSpringViewResolver.setSuffix(".html");
					beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
					return beetlSpringViewResolver;
			}
		}
	
	2,配置
		beetl.templatesPath=classpath:/templates/
	
	3,Controller
		return new ModelAndView("index/index");
	
-------------------------------
Spring-boot-starter		注意	|
-------------------------------
	<dependency>
		<groupId>com.ibeetl</groupId>
		<artifactId>beetl-framework-starter</artifactId>
		<version>1.1.16.RELEASE</version>
	</dependency>

	* starter				自动处理以btl结尾的视图,模板根目录是Spring Boot默认的templates目录,如下配置可以修改beetl部分属性
	* beetl-beetlsql.dev	默认为true,即自动检查模板变化
	* beetl.suffix			默认为btl,以btl结尾的视图交给Beetl引擎渲染

	|-templates
		|-index
			|-index.btl

	ModelAndView m = new ModelAndView("/index/index.btl");


-------------------------------
Spring-boot		注意			|
-------------------------------
	# Spring boot 在war模式下模版文件无法加载的问题
		* application.properties
			beetl.templatesPath=templates/

		* controller
			ModelAndView INDEX = new ModelAndView("index/index");
		
		* template
			@ include('/common/common_header.html'){}

-------------------------------
beetl-2.7.26集成方式			|
-------------------------------

    /**
     * 模板根目录
     */
    @Value("${beetl.templatesPath}")
    private String templatesPath;

    @Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        //获取Spring Boot 的ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null){
            classLoader = BeetlConfiguration.class.getClassLoader();
        }
        //beetlGroupUtilConfiguration.setConfigProperties(extProperties);
        ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader(classLoader, templatesPath);
        beetlGroupUtilConfiguration.setResourceLoader(classpathResourceLoader);
        
        Map<String,Function> funcMap = new HashMap<>();
        
        beetlGroupUtilConfiguration.setFunctions(funcMap);
        beetlGroupUtilConfiguration.setConfigFileResource(new ClassPathResource("/beetl.properties"));
        beetlGroupUtilConfiguration.init();
        //如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
        beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(classLoader);
        return beetlGroupUtilConfiguration;
    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        //beetlSpringViewResolver.setPrefix("/");
        beetlSpringViewResolver.setSuffix(".html");
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

-------------------------------
Spring-boot	IDEA下模版的热更新	|
-------------------------------
	1,添加依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
	
	2,插件
		 <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                	<executable>true</executable>
					<fork>true</fork>		//目前我不知道这个fork有啥用?
                </configuration>
            </plugin>
        </plugins>
	
	3,配置
		# devtools,指定模版的目录
		spring.devtools.restart.exclude=templates/**									*/
	
	4,快捷键重新加载
		ctrl + shift + F9
		
		