
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/jsp/"/>
		<property name="suffix" value=".jsp"/>
	</bean>

	不论目标方法返回任何类型:String,ModelAndView... ...都会被框架转换为ModelAndView
	View
		* 视图接口,有很多的具体实现
	ViewResolver
		* 视图解析器!

	如果项目中使用了JSTL标签.框架会自动把视图InternalResourceView 转换为JstlView
	如果使用到了JSTL的fmt标签进行国际化,就需要在springmvc的配置文件中配置'国际化资源文件'

	