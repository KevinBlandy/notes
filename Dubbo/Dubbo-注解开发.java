--------------------------------
1,服务提供者端配置				|
--------------------------------
	com.alibaba.dubbo.config.annotation.Service

	<dubbo:annotation/>
		* 可以当作spring的 <context:component-scan> 使用,会实例化,注册spring注解标识的bean.
		* 把会把 @Service 标识的类,作为服务,注册到注册中心

	<context:component-scan base-package="">
		<context:include-filter type="annotation" expression="com.alibaba.dubbo.config.annotation.Service" />
	</context:component-scan>
		* spring 原生扫描,include-filter 告诉spring,该注解标识的类也要进行扫描,并且注册到注册中心

--------------------------------
2,服务消费者端配置				|
--------------------------------
	com.alibaba.dubbo.config.annotation.Reference

	<dubbo:annotation />
		* 可以当作spring的 <context:component-scan> 使用,会实例化,注册spring注解标识的bean.
		* 还会自动的注入: @Reference 标识的属性,从注册中心获取服务
	
	<context:component-scan base-package="">
	</context:component-scan>
		* spring原生的扫描, 
		* 因为配置了:<dubbo:annotation /> ,所以 @Reference 标识的属性,从注册中心获取服务来注入