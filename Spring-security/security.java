----------------------------
spring-security				|
----------------------------
	# ����
		https://docs.spring.io/spring-security/site/docs/current/reference/html5/
		https://github.com/spring-projects/spring-security
	
	# Maven
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
	

	# ģ��
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
	
	
	# Spring Boot ���Զ�����
		* ����һ����Ϊ springSecurityFilterChain �� servlet Filter Bean�����Bean����Ӧ�ó����е����а�ȫ������Ӧ�ó����URL����֤�ύ���û��������룬�ض��򵽵�¼�����ȵȣ���
		* ����һ�� UserDetailsService Bean�������û����� user��������������ɵģ��ᱻ���������̨��
		* ��ÿ�������У���һ����Ϊ springSecurityFilterChain �� Bean �� Servlet ����ע�� Filter��
		
		* ������servlet��������
			HttpServletRequest#getRemoteUser()
			HttpServletRequest.html#getUserPrincipal()
			HttpServletRequest.html#isUserInRole(java.lang.String)
			HttpServletRequest.html#login(java.lang.String, java.lang.String)
			HttpServletRequest.html#logout()
		
	
