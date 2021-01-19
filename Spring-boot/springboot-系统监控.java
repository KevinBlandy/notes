-------------------------
Spring boot 系统监控	 |
-------------------------
	# spring boot提供了运行时应用监控和管理功能
	# 可以通过HTTP,JMX,SSH协议来进行操作,审计,健康和指标信息会自动得到
	# Spring boot 提供了监控和管理端点
		actuator		所有 EndPoint 列表,需要加入 Spring-HATEOAS 支持
		autoconfig		当前应用所有自动配置
		beans			当前应用中所有Bean信息
		configprops		当前应用中所有的配置属性
		dump			显示当前应用的线程状态
		env				显示当前应用的环境信息
		health			显示当前应用的健康状态
		info			显示当前应用的信息
		metrics			显示当前应用的各项指标
		mappings		显示当前应用的所有 @RequestMapping 路径
		shutdowm		关闭当前应用(默认关闭)
		trace			显示追踪信息(默认最新HTTP请求)
	

-------------------------
Spring boot HTTP		 |
-------------------------	
	# 只需要在依赖中添加
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		* 既然是HTTP监控,那么项目必然需要WEB的依赖
	
	

	# 监控路径
		http://{ip}:{port}/{端点名}						/*/
		* http://localhost:8222/info
		* 返回的都是JSON数据
	# 关于 shutdown端点
		* 不支持GET请求,可以用POST

	# 修改端点ID
		* 定制都是通过 endpoints+端点名+属性名 来进行配置的,每段使用,隔开
		* Demo
			endpoints.beans.id=mybeans
			此时beans端点访问路径: http://localhost:8222/mybeans
		
	
	# 开启端点
		endpoints.shutdown.enabled=true
	
	# 关闭端点
		endpoints.beans.enabled=false
	
	# 仅仅开启所需要的端点
		* 可以先关闭所有,然后再开启所需的
		endpoints.enabled=false
		endpoints.beans.enabled=true
		
	# 定制端点的访问路径
		* 端点的访问路径默认是: /
		* 可以修改配置
			management.context-path=/manager
		* 访问地址为
			http://localhost:8222/manager/beans
		
	# 定制端点访问端口
		* 为了安全,一般都会配置
		management.port=8222
	
	# 关闭HTTP端点
		management.port=-1
	
	# 自定义端点
		* 继承 AbstractEndpoint,并且注册为 bean

---------------------------------
Spring boot 自定义HealthIndicator |
---------------------------------	
	# health 信息都是从 ApplicationContext 中所有的 HealthIndicator 的 Bean 中收集的
	# spring 内置了一些 HealthIndicator
	
	# 实现 HealthIndicator 接口,覆写 health();方法

	