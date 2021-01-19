————————————————————————————————
一,Spring 如何在WEB应用中使用	|
————————————————————————————————
1,Spring 如何在WEB应用中使用
	> jar包会有一丢丢不同,我们需要导入俩跟跟WEB相关的包(其实前面我们一直都在用)
	> spring 配置文件,没什么不同!
	> 创建IOC容器(AppicationContext)
		①,非WEB应用在main方法中创建
		②,WEB应用,应该在WEB应用被服务器加载时就创建IOC容器
	* 也就是说跟随服务器的启动就创建容器————ServletContextListener ,监听器
	> IOC随着服务器启动而创建之后,在WEB应用中其他组建,怎么访问它?
	* 把IOC容器,放置到Application域中!也就是ServletContext中
总结
	> 在appication生死监听中初始化方法中创建IOC容器
	> 把IOC容器放至,application域
	* 可以把 Spring 配置文件的位置,以初始化参数的形式放置在web.xml中!
	* 在监听器中,获取ServletContext,再获取初始化参数,然后给 ClassPathXmlApplicationContext();去解析,得到ApplicationContext

拓展
	其实, Spring 是一个聪明的框架!他早已为我们提供了一个 ContextLoaderListener
	* Spring-web-x.x.x.RELEASE.jar 包下的东西
	* org.springframework.web.context.ContextLoaderListener
	* 而且,这个 Spring 提供的监听器,代码设计更为严谨!可以考虑使用它!
	这个类已经是实现了 SerlvetContextListener(application生死监听),我们只需要在web.xml中注册它就好
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	> 注册成功后,它就会帮我创建IOC容器,并且放置到application域,但是名称有点长,我们可以同它提供的方法取出它

	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);

	> 你给我一个ServletContext对象,我就还你一个IOC容器对象!当然前提是:你是用 Spring 提供的监听器
	* 使用这个方法需要导入的包
		org.springframework.web.context.support.WebApplicationContextUtils
		org.springframework.context.ApplicationContext

	* 默认加载的就是WEB-INF下的:applicationContext.xml文件,如果你的xml文件在其他的位置,那么你需要在web.xml中配置全局初始化参数告诉,这个监听器!
	contextConfigLoaction
		> 该参数是监听器中的一个成员变量,指定了xml的配置文件地址.可以通过初始化参数来配置xml文件的地址
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:beans.xml</param-value>
	</context-param>

	也可以这样

	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/classes/applictionContext.xml</param-value>
	</context-param>
	

————————————————————————————————
二,Spring 如何整合Struts2		|
————————————————————————————————
['整合目标']
	1,使用IOC容器,来管理struts2的Action
['整合步骤']
	1,正常加入struts2,以及配置
	2,在 Spring 的IOC容器中配置struts2的Action
		* 在 Spring 中配置struts2的action时,要注意修改scope="prototype"!action,不能是单例的!
	3,配置struts2的配置文件
		* 既然是Spring创建对象,所有action不能是struts2创建
	<action name="demo" class="Spring配置文件中action的bena的id值" method="execute">
		<result name="success">/index.jsp</result>
	</action>
	4,添加一个jar包,对不起.之前没说!希望你没有因为做实验挂彩而砸电脑
	struts2-spring-plugin.jar
	5,OK就没问题了.其他的东西可以正常配置!

['拓展']
	struts.xml文件的一个配置:
	<constant name="struts.objectFactory" value="spring"/>
	struts.objectFactory这个属性用于说明Struts2的 对象池创建工厂，Struts2也有自己的对象池，就像Spring那样，在配置文件中你可以引用对象池中的对象，你可以借助于Spring中的对象池，
	当想要得到Spring中的对象池时，申明struts.objectFactory为Spring的对象池构建工厂....
	* 一般别去设它!

————————————————————————————————
三,Spring 如何整合Struts2  总结 |
————————————————————————————————
Spring 
	1,Spring 的IOC容器随着服务器启动而启动,注册 Spring 提供的监听器.
	2,添加 Spring 中关于WEB的jar包
	3,Spring 配置文件正常配置
	4,在 Spring 里面配置 struts2的action 的时候要注意.不能是单例
struts2
	1,除了核心jar包以外再添加一个'struts2-spring-plugin.jar'
	2,在配置<action></action>的时候要注意,class 属性是'spring 配置文件中,对应的action的bean的id属性值'!也就是说,action是由spring创建的
	3,那么在action,里面就不要有 new 之类的操作!统统的交给 Spring 吧!进行配置
	

————————————————————————————————
四,Spring 整合Struts2  原理     |
————————————————————————————————
	Struts2的这个jar包非常神奇,里面提供了一个xml文件
	struts2-spring-plugin.jar ----> struts.plugin.xml
	该配置文件有个配置
	<constant name="struts.objectFactory" value="spring"/>
	> 对象工厂就变成了org.apache.struts2.spring.StrutsSpringObjectFactory
	* 默认的并没有开启,在struts-default.properties中可以找到这个常量

	开启的作用:
		会引发下面的常量(引发问题,参考:启动了开发模式后,其他的两个常量也会true,详细打印错误信息等)
	struts.objectFactory.spring.autoWire=name
		* 默认是按照名称来装配
		* name
		* type			//类型
		* auto			//构造器
		* constructor	//自动

	自动装配,不用提供get/set!

	<constant name="struts.objectFactory" value="spring"/>
	<constant name="struts.objectFactory" value="spring"/>




	





	
	