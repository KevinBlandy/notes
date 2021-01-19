# Mvc整合
	1, web.xml配置 Filter
		<filter>
			<filter-name>springSecurityFilterChain</filter-name>
			<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		</filter>

		* 该filter的名称必须是: springSecurityFilterChain
		* 拦截路径: '/*'
	
	2, context扫描的时候, 添加扫描配置:classpath*:spring-security.xml
		<context-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath*:application-*.xml,classpath*:spring-security.xml</param-value>
		</context-param>

	
	3, 添加配置: spring-security.xml
		
	
	