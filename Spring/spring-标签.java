<bean 
	id=""
	name=""
	class=""
	init-method=""
	scope=""
	abstract=""
	depends-on="" 
	autowire="" 
	autowire-candidate="" 
	destroy-method="" 
	factory-bean="" 
	factory-method="" 
	lazy-init="" 
	parent="" 
	primary=""
/>
	# 基础的Bean组件配置

<context:component-scan base-package="com.tedi" use-default-filters="false">
	<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
	<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"
</context:component-scan>
	# 扫描

<aop:aspectj-autoproxy proxy-target-class="true" />
	# 使用CGLIB动态代理

<tx:advice id="txAdvice" transaction-manager="trans">
	<tx:attributes>
		<tx:method name="get*" read-only="true"/>
		<tx:method name="find*" read-only="true"/>
		<tx:method name="*" />					
	</tx:attributes>
</tx:advice>
	# 配置事务策略

<aop:config>
	<aop:pointcut expression="execution(* com.tedi.community.web.service..*(..))" id="community-service"/>

	<aop:advisor advice-ref="txAdvice" pointcut-ref="community-service"/>

	<aop:aspect ref="dataSourceAspect" order="-9999">
		<aop:before method="before" pointcut-ref="community-service" />
	</aop:aspect>
</aop:config>
	# AOP配置
		

<context:property-placeholder location="classpath:jdbc.properties"/>
	# 导入外部配置文件

<context:annotation-config/>
	# 让xml配置bean中的自动装配注解生效
	# component-scan 标签会自动的注册这个