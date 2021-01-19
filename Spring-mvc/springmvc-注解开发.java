————————————————
一,处理器适配器 |
————————————————

关于适配器的注解配置

1,在xml中添加
	<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"/>
	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
		> 这个是spring3.1之后,使用的注解类!在3.1之前不是使用的这个!

2,使用这个东西,可以替代上面的
	<mvc:annotation-driven>
				
	</mvc:annotation-driven>

		> 这个东西可以完全的替代'映射器'以及'适配器'的配置,而且默认提供了更多的参数绑定的方法!
		> 比如:json的转换的解析器--这个就默认加载了!
		> 使用了这个就不用配置上面的'1'!不管是映射器还是适配器!这东西一个顶俩!
		> 实际开发,还是用这种!


————————————————
二,处理器适配器 |
————————————————
关于映射器的注解配置

	1,在xml文件中配置
		<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/>
		org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
			> 这个类就是spring3.1之后注解使用的新类!3.1之前不是用的这个

	2,使用这个东西,可以替代上面的
	<mvc:annotation-driven>
				
	</mvc:annotation-driven>

		> 这个东西可以完全的替代'映射器'以及'适配器'的配置,而且默认提供了更多的参数绑定的方法!
		> 比如:json的转换的解析器--这个就默认加载了!
		> 使用了这个就不用配置上面的'1'!不管是映射器还是适配器!这东西一个顶俩!
		> 实际开发,还是用这种!

<mvc:annotation-driven/> 配置详解
	* 这东西可以参考一下帮助文档!
	* 这东西会自动的注册几个Bean
		RequestMappingHandlerMapping			//注解映射器
		RequestMappingHandlerAdapter			//注解适配器
		ExceptionHandlerExceptionResolver(异常解析器)
	* 这东西还提供以下支持
		支持使用 ConversionService 实例对表单参数进行类型转换
		支持使用 @NumberFormatannotation,@DateTimeFormat 注解完成数据类型的格式转换
		支持使用 @Valid 注解对javaBean实例进行JSR303验证
		支持使用 @RequestBody 和 @ResponseBody 注解			//json解析支持
				
	