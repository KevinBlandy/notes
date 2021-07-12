-----------------------
超时控制
-----------------------
	# 全局控制
		spring:
		  cloud:
			gateway:
			  httpclient:
				connect-timeout: 1000
				response-timeout: 5s
		
		* 全局配置，支持添加时间单位字符串

	
	# 针对单个路由控制
		- id: per_route_timeouts
		  uri: https://example.org
		  predicates:
		    - name: Path
			  args:
			    pattern: /delay/{timeout}
		  metadata:
		    response-timeout: 200
		    connect-timeout: 200
		

		* 这种配置方式，response-timeout/connect-timeout 都必选以毫秒的方式配置
	

	
		