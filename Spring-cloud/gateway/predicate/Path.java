---------------
Path
---------------
	# 根据URI来匹配路由，用的最多的一个了

	# 配置项
		patterns
			* 是一个数组，配置一个或者多个URI

		matchTrailingSlash: true
			* 是否匹配尾部斜线(/)
			* matchTrailingSlash设置为false，则 /red/1/ 请求不会匹配请求路径 /red/{segment} 

	
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
		

		* 一般都要配合 RewritePath 这个过滤器使用，用来重写Path，把路由前缀删除后再去请求服务 **/
	

	# 支持模板变量
		spring:
		  cloud:
			gateway:
			  routes:
			    - id: path_route
				  uri: https://example.org
				  predicates:
				    - Path=/red/{segment},/blue/{segment}
		

		* 这个 segment 会存储在 ServerWebExchange.getAttributes() 中， key就是:ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE
		* 可以快捷获取
			Map<String, String> uriVariables = ServerWebExchangeUtils.getPathPredicateVariables(exchange);
			String segment = uriVariables.get("segment");
			
