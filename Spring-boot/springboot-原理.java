--------------------------
Springboot-原理				|
--------------------------


--------------------------
Springboot-自动配置原理		|
--------------------------
	# 关于spring-boot自动配置的源码都在:spring-boot-autoconfigure-1.5.1.RELEASE.jar 中
	# 如果想看到spring-boot自动为我们做了哪些配置,可以在启动的时候添加参数,也可以在全局配置文件中添加参数
		java -jar xxx.jar --debug
		debug=true
		* Positive matches: 以下为启用的自动配置
		* Negative matches:	以下为未启用的自动配置
		
	0,@SpringBootApplication 注解	EnableAutoConfigurationImportSelector
	1,@EnableAutoConfiguration 注解 与 @AutoConfigurationPackage
		* @AutoConfigurationPackage 自动配置包

	2,@EnableAutoConfiguration 中的 @Import(EnableAutoConfigurationImportSelector.class) 注解
		* 2.0中已经修改:AutoConfigurationImportSelector
	3,EnableAutoConfigurationImportSelector 类
		# 使用 SpringFactoriesLoader.loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader);方法来扫描具有 META-INF/spring.factories 的jar包
			* public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader)
			* spring-boot-autoconfigure-1.5.1.RELEASE.jar 中就有 META-INF/spring.factories
		# META-INF/spring.factories 里面声明有一些自动配置项
			* 包含了N多的组件配置,包括自动配置类,事件监听等等....

	
	4,随便打开一个 XxxxAutoConfiguration 类,一般都会有以下注解,在 org.springframework.boot.autoconfigure.condition 包下
		@ConditionalOnBean
			* 当容器有指定的bean的条件下
		@ConditionalOnClass
			* 当容器路径下有指定类的条件下
		@ConditionalOnCloudPlatform
		@ConditionalOnExpression
			* 基于SpEL表达式作为判断条件
		@ConditionalOnJava
			* 基于JVM版本作为判断条件
		@ConditionalOnJndi
			* 在JNDI存在的条件下查找指定的位置
		@ConditionalOnMissingBean
			* 当容器里面没有指定Bean的情况下
		@ConditionalOnMissingClass
			* 当类路径下没有指定的类条件下
		@ConditionalOnNotWebApplication
			* 当前项目不是Web项目的条件下
		@ConditionalOnProperty
			* 指定的属性是否有指定的值
		@ConditionalOnResource
			* 类路径是否有指定的值
		@ConditionalOnSingleCandidate
			* 当指定的 Bean 在容器中只有一个,或者虽然有多个,但是指定首选的Bean
		@ConditionalOnWebApplication
			* 当前项目是WEB项目的条件下

		* 这堆注解都是组合了 @Conditional(spring) 元注解,只是使用了不同的条件
	
--------------------------
Springboot-自定义AutoConfiguration |
--------------------------
	1,准备配置Bean,并且读取properties文件
		* 一般都是以: xxxProperties 命名
		* 如果是单独抽离出来模块(maven),仅仅需要依赖spring boot的 autoconfigure 模块:

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-autoconfigure</artifactId>
				<version>XXXXX</version>
				<scope>compile</scope>
			</dependency>

			* 最佳实践,直接继承:spring-boot-starters
				<parent>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starters</artifactId>
					<version>2.0.2.RELEASE</version>
				</parent>
				
				<dependencies>
					<dependency>
						<groupId>org.springframework.boot</groupId>
						<artifactId>spring-boot-autoconfigure</artifactId>
						<scope>compile</scope>
					</dependency>
				</dependencies>

	2,创建 XxxxAutoConfiguration 类,会通过该类的一些注解来完成是否要注册一些组件
		* 标识该类是一个配置类 @Configuration
		* 把 创建的 xxxProperties 加入到IOC EnableConfigurationProperties(xxxProperties.class) ,就可以在页面中 @Autowired 注入使用
		* 某个注册 @Bean 的方法上去使用注解进行判断(上面的那一堆了就是)
		* 把返回的Bean注入到IOC
			
		
	3,在 src/main/resource 目录下创建文件夹和文件
		META-INF/spring.factories


		org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
		com...xxxx,\
		com...x...,\
		
		* 多个以逗号分割
		
		org.springframework.context.ApplicationContextInitializer
			* 配置 ApplicationContextInitializer 的实现,在初始化完成后获取到ApplicatioContext


		
		org.springframework.boot.SpringApplicationRunListener
			* 配置 SpringApplicationRunListener 的实现,处理事件


--------------------------
Springboot-自动配置最佳实践|
--------------------------
	# 一般定义两个比较重要的组件
		xxx-starter
		xxx-autoconfigure
	
	# starter 只是一个空的maven工程,没有任何的代码,配置
		* 在 starter工程jar中除了maven的pom以外,还存在一个文件: META-INF/spring.providers
		* 该文件只用描述依赖

		provides: xxx-autoconfigure, beetl-core
	
	# autoconfigure 是核心的自动装配工程
		

--------------------------
元数据					  |
--------------------------
	# SpringBoot的starter(jar)包含元数据文件
		* 元数据文件提供所有支持的配置属性的详细信息
		* 这些文件旨在让 IDE 开发人员使用 application.properties 或 application.yml 文件像用户一样提供上下文帮助和"代码完成"

		* 通俗理解来说,这个东西就是为了让idea可以读取到,从而在 yml/properties 编辑的时候,可以给出提示
	
	# 参考文档
		https://docs.spring.io/spring-boot/docs/current/reference/html/configuration-metadata.html

	# 元数据文件的路径
		META-INF/spring-configuration-metadata.json

		* 它是一个json文件
	
		
	# groups 属性
	# properties 属性
	# hints 属性

