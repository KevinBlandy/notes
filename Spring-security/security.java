----------------------------
spring-security				|
----------------------------
	# 链接
		https://docs.spring.io/spring-security/site/docs/current/reference/html5/
		https://github.com/spring-projects/spring-security
	
	# Maven
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
	

	# 模块
		spring-security-core
		spring-security-remoting
		spring-security-ldap
		spring-security-web
		spring-security-config
		
		spring-security-oauth2-core
		spring-security-oauth2-client
		spring-security-oauth2-jose
		spring-security-oauth2-resource-server

		spring-security-acl
		spring-security-cas
		spring-security-openid
		spring-security-test
	
	
	# Spring Boot 的自动配置
		* 创建一个名为 springSecurityFilterChain 的 servlet Filter Bean。这个Bean负责应用程序中的所有安全（保护应用程序的URL，验证提交的用户名和密码，重定向到登录表单，等等）。
		* 创建一个 UserDetailsService Bean，它的用户名是 user，密码是随机生成的，会被输出到控制台。
		* 在每个请求中，用一个名为 springSecurityFilterChain 的 Bean 向 Servlet 容器注册 Filter。
		
		* 与以下servlet方法集成
			HttpServletRequest#getRemoteUser()
			HttpServletRequest.html#getUserPrincipal()
			HttpServletRequest.html#isUserInRole(java.lang.String)
			HttpServletRequest.html#login(java.lang.String, java.lang.String)
			HttpServletRequest.html#logout()
		
	
