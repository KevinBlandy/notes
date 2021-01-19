1,Spring框架原理
	> 前端控制器,处理器映射器,处理器适配器,视图解析器
2,Spring入门
	> 非注解的处理器映射器,处理器适配器
	> 注解的处理器映射器,处理器适配器
3,Spring和mybatis/Hibernate/struts2整合
4,Spring注解开发
	> 常用注解
	> 参数绑定(简单类型,POJO,集合)
	> 自定义参数绑定
5,SpringMVC和struts2区别
6,Spring高级应用
	> 参数绑定
	> 数据回显
	> 上传图片
	> json数据交互
	> RESTful支持
	> 拦截器
——————————————————————————————————————————
		小结							  |
——————————————————————————————————————————
	['XML体系']
		1,前端控制器
			DispatcherServlet
				* 应该为它提供一个局部初始化参数:contextConfigLocation
				* 它的值是类加载路径下的配置文件地址:classpath:xxx.xml
				* 默认加载文件问题:DispatcherServlet.properties

		2,处理器映射器
			* 所有的处理器映射都是实现了'HandlerMapping'接口
			BeanNameUrlHandlerMapping
				* 单个映射,要求Controller使用id或者name给出url路径
			SimpleUrlHandlerMapping
				* 多个映射,要求Controller标注id或者name,该映射器会根据其in或者name来表示url路径

		3,处理器适配器
			SimpleControllerHandlerAdapter
				* 该适配器调用的Handler要求实现'Controller'接口.
				* 接口方法返回一个ModelAndView
			HttpRequestHandlerAdapter
				* 该适配器调用的handler要求实现'HttpRequestHandler'接口.
				* 接口方法并无返回值,提供了request,response.可以进行ajax返回json等操作

		4,视图解析器
			InternalResourceViewResolver

	
	['注解体系']
		1,前端控制器
			DispatcherServlet
				* 应该为它提供一个局部初始化参数:contextConfigLocation
				* 它的值是类加载路径下的配置文件地址:classpath:xxx.xml
				* 默认加载文件问题:DispatcherServlet.properties

		2,处理器映射器
			DefaultAnnotationHandlerMapping
				* 3.1之前使用的注解映射器
			RequestMappingHandlerMapping
				* '3.1之后使用的注解映射器'

		3,处理器适配器
			AnnotationMethodHandlerAdapter
				* 3.1之前使用的注解适配器
			RequestMappingHandlerAdapter
				* '3.1之后使用的注解适配器'

		<mvc:annotation-driven/>
			* 一个顶俩,直接配置它!可以完全替代'映射器'以及'适配器'!
			* 实际开发更倾向于这个.它还默认的加载了很多参数的绑定方法,比如json的转换解析器,它就默认加载了
			* 使用这个,不用配置映射器,适配器.而且还加载了更多的东西.
			
		4,视图解析
			InternalResourceViewResolver
	