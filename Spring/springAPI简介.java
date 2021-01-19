————————————————————————
一,IOC容器实现			|
————————————————————————
读取Bean配置,创建Bean实例之前,必须对IOC容器进行实例化才可以从IOC容器里面获取Bean实例使用
Spring提供了两种类型的IOC容器实现
1,BeanFactory
	* ICO容器的基本实现
	* 是Spring框架的基础设施,面向Spring本身
2,ApplicationContext
	* 提供了更多的高级特性,是BeanFactory的子接口
	* 面向使用Spring框架的开发者'几乎所有的应用场合,都直接使用ApplicationContext,而非底层的BeanFactory'
> 无论是以上哪种方式,配置文件都是相同的


————————————————————————
二,ApplicationContext	|
————————————————————————
> 它很显然是一个接口,代表IOC容器,在它初始化上下文时,就实例化所有'单例的Bean'
================【主要的实现类】
ConfigurableApplicationContext[interface]
	* 扩展于ApplicationContext,新增两个主要的方法
	* 这哥们儿还是一个接口,只是更强大.
		> refresh();//启动或者刷新上下文
		> close();//关闭上下文
ClassPathXmlApplicationContext
	* 从类路径下加载配置文件
	* 可以同时加载多个配置文件
		new ClassPathXmlApplicationContext("beans1.xml","beans2.xml");
FileSystemXmlApplicationContext
	* 从文件系统加载配置文件
WebApplicationContext
	* 是专门为WEB应用而准备的,它允许从相对于WEB根目录的路径中完成初始化工作
================【主要的方法】
* 一些是继承自BeanFactory父接口的方法
Object		getBean(String);
				|--创建配置文件中,指定id属性的Bean,返回一个Object!需要自己手动强转
<T>			getBean(String,Class<T>);
				|--给出Bean的Class类型,并且指定其id属性.如果不指定,且xml中有多个同类类型的Bean存在,'抛出异常'!
<T>			getBean(Class<T>);
				|--直接给出Bean的类类型,自动去xml中进行匹配!要注意的是,必须要保证xml只有一个指定类型的Bean配置存在.如果有多个就会抛出异常!
Object		getBean(String,Object);
				
String		getId();
String		getApplicationName();
String		getDisplayName();
long		getStartupDate();
			getParent();
			getAutowireCapableBeanFactory();
boolean		containsBean(String);
boolean		isSingleton(String);
boolean		isPrototype(String);
boolean		siTypeMatch(String,Class<?>);
Class<?>	getType(String);
String[]	getAliases(String);