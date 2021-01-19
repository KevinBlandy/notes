-----------------------
Index					|
-----------------------
	# IOC容器
		ApplicationContext
			|-ConfigurableApplicationContext
				* 扩展于ApplicationContext,新增两个主要的方法
				* 这哥们儿还是一个接口,只是更强大.
				* refresh();	//启动或者刷新上下文
				* close();		//关闭上下文
			|-ClassPathXmlApplicationContext
				* 从类路径下加载配置文件
				* 可以同时加载多个配置文件
				* new ClassPathXmlApplicationContext("beans1.xml","beans2.xml");
			|-FileSystemXmlApplicationContext
				* 从文件系统加载配置文件
			|-WebApplicationContext
				* 是专门为WEB应用而准备的,它允许从相对于WEB根目录的路径中完成初始化工作
	
	# Bean生命周期相关
		ApplicationContextAware
			# Bean生命周期接口,实现该接口可以获取ApplicationContext
		BeanNameAware
			# Bean生命周期接口,会把当前Bean的Bean名称通过抽象方法注入进来
		InitializingBean
			# Bean生命周期接口,实现该接口,会在属性注入完毕后调用抽象方法
		BeanPostProcessor
			# Bean生命周期接口,实现该接口,会在Bean初始化的'前后'调用对应的抽象方法
			|-Object postProcessBeforeInitialization(Object var1, String var2) throws BeansException;
			|-Object postProcessAfterInitialization(Object var1, String var2) throws BeansException;
		DisposableBean
			# Bean生命周期接口,实现该接口,会在IOC关闭的时候调用抽象方法
	
	# 静态工具类
		Environment
			# 环境变量

		Resource
			# 资源加载器
			|-ClassPathResource

		ClassUtils
			# 操作Class的工具类

		StringUtils
			# 操作String的工具类

		ReflectionUtils
			#反射工具类

		CollectionUtils
			# 操作集合的工具类
	
-----------------------
ApplicationContextAware	|
-----------------------


-----------------------
Environment				|
-----------------------
	# org.springframework.core.env.Environment
	# 可以在任何地方注入,里面有当前环境的一些变量,包含了外部配置文件的变量

	environment.getProperty("bbs.dbPassowrd");

---------------------
Resource			 |
---------------------
	# spring 的东西,是一个接口
	# org.springframework.core.io.Resource
	# 子类
		ClassPathResource	 
			# classpath下的资源加载器
				Resource resource = new ClassPathResource("/xxx.properties");
				Properties props = PropertiesLoaderUtils.loadProperties(resource);
				* 把加载到的资源,映射进 Properties

---------------------
ClassUtils			 |
---------------------
	

---------------------
StringUtils			 |
---------------------
	# 静态方法
	String trimAllWhitespace(String str);
		* 去除字符串中的所有空格
	
---------------------
ReflectionUtils		 |
---------------------
	# 静态方法
	Field ReflectionUtils.findField(Clazz class, String fieldName);
		* 反射获取到指定 Class 的指定字段,可以获取到私有的字段

---------------------
CollectionUtils		 |
---------------------
	
