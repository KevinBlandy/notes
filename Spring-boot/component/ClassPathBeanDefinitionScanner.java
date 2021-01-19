-------------------------------
ClassPathBeanDefinitionScanner |
-------------------------------
	# SpringBoot项目中或者 Spring项目中配置<context:component-scan base-package="com.example.demo" />
	
	# 那么在IOC 容器初始化阶段(调用beanFactoryPostProcessor阶段)就会采用 ClassPathBeanDefinitionScanner 进行扫描包下 所有类

	# 并将符合过滤条件的类注册到IOC 容器内
		* Mybatis 的Mapper注册器(ClassPathMapperScanner) 是同过继承 ClassPathBeanDefinitionScanner, 并且自定义了过滤器规则来实现的
	
	# ClassPathBeanDefinitionScanner 作用就是将指定包下的类通过一定规则过滤后 将Class 信息包装成 BeanDefinition 的形式注册到IOC容器中

	# 一般用于自定义扫描器
		* 把标识了自定义注解的类,添加到IOC中

	# 类构造方法
		ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry);
		ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters);
		ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters,Environment environment);
		ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters,Environment environment, @Nullable ResourceLoader resourceLoader);


		* 子类继承的时候,必须指定要手动声明要调用的父类构造方法: super
	
	
-------------------------------
自定义扫描器				    |
-------------------------------

		