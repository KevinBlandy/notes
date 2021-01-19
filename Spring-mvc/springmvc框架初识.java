	SpringMVC,其实就是Spring的一个模块!SpringMVC和Spring是无需通过中间整合层来进行整合的!
		* 这个其实好理解,就是.前面学的spring中其实有mvc组建,只是我们没用.用的struts2而已
	SpringMVC,是一个基于MVC的框架!

	Spring3.0以后全面超越了Struts2,成为了最优秀的MVC框架

---------------------------------------------------------
框架图解...(别看,没用)
---------------------------------------------------------
	MVC是一个设计模式!
		> 在B/S系统下的应用
		> 在C/S...别看了,你不学!
	POJO
	Action
	Servlet
	Service
	Dao	
		---都可以理解成为MVC中的M!

	----SpringMVC框架原理剖析(炒鸡复杂)
	request--->前端控制器(DispatcherServlet)
				|
				|	--> 请求查找Handler
				↓
		|------	处理器映射器(HandlerMapping)------------|
		|			|			|
		|			|			|
	Controller1(Handler)	Controller2(Handler)	Controller3(Handler)
	...(太复杂,很多东西没画!别看图了,看字儿)
---------------------------------------------------------
框架处理流程,文字说明
---------------------------------------------------------
	1,发起请求到前段控制器(DispatcherServlet)
	2,前端控制器,请求HandlerMapping查找Handler
		> 可以根据xml配置
		> 可以根据注解
	3,处理器,映射器HandlerMapping向前端控制器返回Handler
	4,前端控制器调用,处理器适配器去执行Handler
	5,处理器适配器,执行Handler
	6,Handler执行完毕,给处理器适配器,返回ModelAndView
	7,处理器适配器,向前端控制器返回ModelAndView
		> ModelAndView:是SpringMVC框架的一个底层对象,包括了Model和View
	8,前端控制器请求视图解析器,进行视图解析
		> 根据逻辑视图名,来解析成真正的视图(jsp)
	9,视图解析器下,前段控制器返回View
	10,前端控制器,进行视图渲染
		> 把模型数据(ModelAndView),填充到request域
	11,前端控制器,把结果响应给客户端(response)
---------------------------------------------------------
那些重要的组建(重要)
---------------------------------------------------------
	1,前端控制器(DispatcherServlet)
		作用:接收请求,相应结果.相当于一个转发器或者中央处理器
		> 减少了其他组建之间的耦合度
	2,处理器映射器(HandlerMapping)
		作用:根据请求的url,来查找Handler(通过配置/注解)
	3,处理器适配器(HandlerAdapter)
		作用:按照特定的规则(HandlerAdapter要求的规则),执行Handler
		注意:在编写,开发Hanlder的时候,要按照HandlerAdapter的要求去开发(继承/实现一个类,或者接口之类的规则..)
	4,视图解析器()
		作用:进行视图解析,根据逻辑视图名,解析成真正的视图(View)
	5,视图(View)
		View,是一个接口!它的实现类,支持不同的View类型(jsp,freemarker,pdf)

	----需要攻城狮自己进行开发的组建
	Handler	
		> 按照HandlerAdapter的规则去编写
	View
		> 需要开发JSP

---------------------------------------------------------
整体内容
---------------------------------------------------------
	springMVC版本:3.2
	需要Spring3.2所有jar包(一定要包括:spring-webmvc-3.2.0.RELEASE.jar)
	
	1,SpringMVC概述
	2,SpringMVC的HelloWorld
	3,使用 @RequestMapping 映射请求
	4,映射请求参数 & 请求头
	5,处理模型数据
	6,视图和视图解析器
	7,RESTful CRUD
	8,SpringMVC表单标签 & 处理静态资源
	9,数据转换 & 数据格式化 & 数据校验
	10,处理JSON:使用HttpMessageConverter
	11,国际化
	12,文件上传
	13,拦截器
	14,异常处理
	15,SpringMVC运行流程
	16,在Spring环境下使用SpringMVC
	17,SpringMVC对比Struts2

	18,schema
		<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns="http://www.springframework.org/schema/beans" 
			xmlns:aop="http://www.springframework.org/schema/aop"
			xmlns:context="http://www.springframework.org/schema/context" 
			xmlns:tx="http://www.springframework.org/schema/tx"
			xmlns:mvc="http://www.springframework.org/schema/mvc" 
			xmlns:cache="http://www.springframework.org/schema/cache" 
			xmlns:p="http://www.springframework.org/schema/p"
			xsi:schemaLocation="http://www.springframework.org/schema/beans 
				http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
				http://www.springframework.org/schema/aop
				http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
				http://www.springframework.org/schema/mvc 
				http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd 
				http://www.springframework.org/schema/context
				http://www.springframework.org/schema/context/spring-context-4.0.xsd
				http://www.springframework.org/schema/tx
				http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
				http://www.springframework.org/schema/cache http://www.springframework.org/schema/cache/spring-cache-4.0.xsd">
				
		</beans>
	





















































