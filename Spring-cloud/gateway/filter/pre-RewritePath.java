-----------------------------
RewritePath
-----------------------------
	# 重写URI
	# 工厂类
		RewritePathGatewayFilterFactory

		* 配置属性
			private String regexp;
			private String replacement;
		
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
		* 由于YAML规范，需要把:$ 需要替换为: $\ ，如果是使用字符串包裹，则不需要，例如：replacement: "/${segment}"
