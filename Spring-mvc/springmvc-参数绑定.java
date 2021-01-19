--------------------------------
自动绑定						|
--------------------------------
	* 不多解释


--------------------------------
自定义绑定						|
--------------------------------
	1,自定义类实现:HandlerMethodArgumentResolver
	2,配置该类到IOC
		<mvc:annotation-driven>
			<mvc:argument-resolvers>
				<bean class="resolver.MyResolver"/>
				<bean class="resolver.MyModelAttributeResolver"/>
			</mvc:argument-resolvers>
		</mvc:annotation-driven>