------------------------
实例监控接口			|
------------------------
	# 该接口会返回JSON格式的实例状态信息
	# 需要添加依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
		* 不多解释,spring-boot的一个适用于生产环境的监控组件

	# 配置项
		eureka.instance.status-page-url=${management.context-path}/info
			# 实例状态页面地址

		eureka.instance.health-check-url=${management.context-path}/health
			# 运行状况指示器地址