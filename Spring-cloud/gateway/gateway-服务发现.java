---------------------
服务自动路由
---------------------
	# 自动为服务添加路由
		spring.cloud.gateway.discovery.locator.enabled=true
			* 为每个服务注册一个路由，请求带有服务名称前缀：/<service-name>  就会路由到指定的服务
			* 执行远程请求之前会自动删除前面的 <service-name>
			
		spring.cloud.gateway.discovery.locator.lower-case-service-id=true
			* 服务名称是小写
	
	# Demo
		* 有个服务名称叫做: user-service 在端口 8080 提供服务
		* 请求网关
			/user-service/demo ---> 转发 ---> /demo:8080
	
	# 配置谓词和过滤器
		spring.cloud.gateway.discovery.locator.predicates[0].name: Path
		spring.cloud.gateway.discovery.locator.predicates[0].args[pattern]: "'/'+serviceId+'/**'"
		spring.cloud.gateway.discovery.locator.predicates[1].name: Host
		spring.cloud.gateway.discovery.locator.predicates[1].args[pattern]: "'**.foo.com'"

		spring.cloud.gateway.discovery.locator.filters[0].name: CircuitBreaker
		spring.cloud.gateway.discovery.locator.filters[0].args[name]: serviceId
		spring.cloud.gateway.discovery.locator.filters[1].name: RewritePath
		spring.cloud.gateway.discovery.locator.filters[1].args[regexp]: "'/' + serviceId + '/?(?<remaining>.*)'"
		spring.cloud.gateway.discovery.locator.filters[1].args[replacement]: "'/${remaining}'"


		
---------------------
服务手动路由
---------------------
	# 通过配置路由
		spring:
		  cloud:
			gateway:
			  routes:
				- id: user_router
				  uri: lb://user-service		# 通过 lb 指定服务ID
				  predicates:
					- Path=/api/user/**			# 通过前缀，来路由到服务
				  filters:
					- RewritePath=/api/user/?(?<segment>.*), /$\{segment} # 使用Filter重写路径，目的是删除前面的路由前缀
