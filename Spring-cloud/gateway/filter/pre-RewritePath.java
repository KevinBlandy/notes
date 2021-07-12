-----------------------------
RewritePath
-----------------------------
	# 重写URI
	# 工厂类
		RewritePathGatewayFilterFactory
	
	# 配置
		spring:
		  cloud:
			gateway:
			  routes:
				- id: user_router
				  uri: lb://user-service
				  predicates:
					- Path=/api/user/**
				  filters:
					- RewritePath=/api/user/?(?<segment>.*), /$\{segment}
		

		* 一般都是配合 Path 断言使用的 **/
		* 由于YAML规范，需要把:$ 需要替换为: $\ 