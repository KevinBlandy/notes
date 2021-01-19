----------------------------
hystrix 监控				|
----------------------------
	# Hystrix 提供了实时调用监控:Hystrix Dashboard
	# 可以持续的记录所有通过 Hystrix发起的请求执行信息,并且以统计报表和图形的形式展示给用户
		* 包括每秒执行多少次请求,多少次请求执行成功,多少次失败
	
	# maven坐标
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
        </dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
	
	# 添加注解
		@EnableHystrixDashboard
	
	# 访问
		${ctx}/hystrix
	
