--------------------------------
ConfigClient					|
--------------------------------
	# Maven
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-client</artifactId>
		</dependency>
	
	# 配置
		* 注意,要配置在 : bootstrap.yml 中

		spring:
		  application:
		    # 就是文件的前缀名
			name: springcloud
		  cloud:
			config:
			  # 配置文件服务器
			  uri: http://localhost:8015/
			  # 激活的文件,就是文件的后缀
			  profile: dev
			  # 分支
			  label: master
			
		* 应用启动的时候会根据 application.name 和 config.profile 还有 config.label 去 config.uri 上去加载配置文件信息
			{label} ==> {application}-{profile}.yml

	# 在程序里面使用
		// 直接使用 @Value 注解
		@Value("${config.name}")
		private String configName;
		
		// 使用 environment
		@Autowired
		private Environment environment;
		environment.getProperty("config.name")
	
	# 失败的快速响应与重试
		* 在服务启动的时候,如果比较复杂,可能会在连接配置服务器,加载配置的过程中耗费大量的时间
		* 可以通过一个配置,来避免当ConfigServer配置有误的时候,需要多等待前置的一些加载时间
		* 实现了快速返回失败信息(抛出异常)

			spring.cloud.conifg.fail-fast=true
		
		* 在开启了'快速返回失败信息'的情况下,还提供了一个重试机制
		* 因为配置服务器的异常,可能只是因为网络波动造成的,这是可以恢复的一个异常
		* 在客户端添加依赖
			<dependency>
				<groupId>org.springframework.retry</groupId>
				<artifactId>spring-retry</artifactId>
			</dependency>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-aop</artifactId>
			</dependency>
		
		* 添加了依赖后,不需要做任何的配置
		* 客户端在连接配置服务器失败的情况下,会连续尝试6次的重新连接
		* 如果都失败的情况下,才会抛出异常
		* 可以通过配置重试的次数以及间隔时间(配置类:RetryProperties)
			spring:
			  cloud:
				config:
				  retry:
					# 初始重试的时间,默认为: 1000ms
					initial-interval: 1000
					# 下一间隔的乘数,默认为: 1.1
					multiplier: 1.1
					# 最大重试次数,默认为 6
					max-attempts: 6
					# 最大间隔时间,默认为: 2000 ms
					max-interval: 2000


--------------------------------
动态刷新配置					|
--------------------------------
	# 在客户端添加,acuator监控模块
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
		
		* 需要开放  /refresh 端点权限
			management:
			  endpoints:
				web:
				  base-path: /actuator
				  exposure:
					include:
					  - '*'
	
	# 访问客户端,查看配置
	
	# 尝试修改Git仓库的配置文件(commit)

	# 请求客户端的刷新路径 /refresh
		http://localhost/actuator/refresh
		
			[
				"config.client.version",
				"config.name"
			]

		* 此时当前客户端的配置信息已经被更新为最新修改的配置
		* 目前这种操作只能修改到:Environment 中的数据,不能修改到通过 @Value 注入的变量
	

	# 可以使用Git仓库的钩子程序(web hook),来触发客户端的 /refresh 接口完成自动刷新
		
	# 实际的开发,并不建议使用这种方式来做配置更新,而是通过消息总线来: springcloud-bus

	


