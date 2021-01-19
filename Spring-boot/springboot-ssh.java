---------------------------
Spring-boot	可以让SSH管理	|
---------------------------
	# 仅仅需要在依赖中添加依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-remote-shell</artifactId>
		</dependency>
	
	# 启动的时候,系统会提供SSH的访问密码
		


---------------------------
Spring-boot	常用命令		|
---------------------------
	help
	metrics
	endpoint
	
	endpoint invoke health

---------------------------
Spring-boot	定制登录用户	|
---------------------------
	# 在 application.properties 中配置
		shell.auth.simple.user=kevin
		shell.auth.simple.password=wyf

---------------------------
Spring-boot	扩展命令		|
---------------------------
	# 可以在spring-boot-starter-remote-shell.jar 中看到 Spring boot 为我们定制的命令
		commands.crash
			autoconfig.groovy
			beans.groovy
			endpoint.groovy
			login.groovy
			metrics.groovy
		
	# 使用Groovy脚本来进行 