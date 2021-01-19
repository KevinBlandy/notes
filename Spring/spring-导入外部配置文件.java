
1,第一种方式,简写完成
	<context:property-placeholder location="classpath:jdbc.properties"/>
	<context:property-placeholder location="classpath:upload.properties"/>
	
	# 配置文件中出现了多个<context:property-placeholder/>
		所有都要加上ignore-unresolvable="true"，一个不加也是不行的

2,第二种传统方式
<!-- 使用spring自带的占位符替换功能 ,楼上是简写-->
	<bean
		class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<!-- 允许覆盖JVM参数 -->
		<property name="systemPropertiesModeName" value="SYSTEM_PROPERTIES_MODE_OVERRIDE"/>	
		<!-- 忽略未找到的文件,就是说如果文件不存在,不会报错 -->
		<property name="ignoreResourceNotFound" value="true" />									
		<property name="locations">
			<list>
				<value>classpath:jdbc.properties</value>
				<value>classpath:upload.properties</value>
			</list>
		</property>
	</bean>
		
	

	不仅仅是可以在xml中使用'${...}'对bean的属性进行注值(jdbc)

	在JAVA代码中可以使用注解直接进行注入值
		* 使用 @Value 注解
		* value属性的值,就是配置文件中的'${key}',

			@Value(value="${userName}")
			private String userName;		

			@Value(value="${passWord}")
			private String passWord;		
		
		* @Value详解
			
			1,什么时候注入相应的值
				> 在spring容器中所有bean完成初始化后执行

			2,有个非常特殊的地方
				> '只会在当前spring容器中查找配置参数'
				> 啥意思呢
					* 这个 @Value 注解标识的类,被注册到了哪个IOC容器?
					  所以,就会在这个IOC容器中到加载的properties文件,再找它的值
					  其他IOC加载的.properties文件,一律不鸟
			3,添加默认值
				@Value( "${jdbc.url:aDefaultUrl}" )
			
