


1,数据类型转换
	* 框架其实自备了N多的类型转换器,其实是不需要我们自己去定义的
	* 自定义数据类型转换器
	* ConversionService 是Spring类型转换体系的核心接口
	  可以利用ConversionServiceFactoryBean在Spring的IOC容器中定一个ConversionService
	  Spring会自动识别出OIC容器中的ConversonService,并在Bean属性配置及Spring MVC处理方法形参绑定等场合使用它进行数据的转换
	* 可以通过ConversionServiceFactoryBean的converters属性注册自定义类型转换器

	<bean id="conversionService" class="org.springfamework.context.support.ConversionFactoryBean">
		<property name="converters">
			<list>
				<bean class="com.kevin.converte.MyConverter"/>
			</list>
		</property>
	</bean>

2,数据类型格式化
3,数据校验