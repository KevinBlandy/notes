	

<context:component-scan base-package="com.tedi" use-default-filters="false">
	<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
	<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"
</context:component-scan>
	* 全局扫描

<context:property-placeholder location="classpath:jdbc.properties"/>
	# 导入外部配置文件

<mvc:annotation-driven conversion-service="conversionService">
	<mvc:argument-resolvers>	
		<bean class="resolver.MyResolver"/>
	</mvc:argument-resolvers>
		* 配置自定义参数绑定器(HandlerMethodArgumentResolver)
	<mvc:message-converters register-defaults="true">
		<bean class="com.tedi.community.common.converter.CallbackJsonConverter"/>
	</mvc:message-converters>
		* 配置自定义的消息转换器
		* register-defaults:是否要载入默认的消息转换器
</mvc:annotation-driven>
	* 注解驱动
	* 会默认的注册一些Bean(消息转换器,处理器映射器,处理器适配器)
	* conversion-service:注册请求参数类型转换器(String -> Date /...)

<mvc:interceptors>
	<mvc:interceptor>
		<mvc:mapping path="/**"/>
		<mvc:exclude-mapping path="/login"/>
		<bean class="com.tedi.community.web.interceptor.LoginInterceptor"/>
	</mvc:interceptor>
	<mvc:interceptor>
		<mvc:mapping path="/**"/>
		<mvc:exclude-mapping path="/login"/>
		<bean class="com.tedi.community.web.interceptor.AuthInterceptor"/>
	</mvc:interceptor>
</mvc:interceptors>
	* 拦截器配置

<mvc:resources mapping="/static/**" location="/static/"/>
	* 静态资源的处理方式

<mvc:cors>
	<mvc:mapping path="/**"
				 allowed-origins="*"
				 allowed-methods="GET, PUT,POST"
				 allow-credentials="true"/>
</mvc:cors>
	* 跨域配配置
	* 一个 cors 允许有多个 mvc:mapping,来配置不同的URL以及对应的跨域配置


