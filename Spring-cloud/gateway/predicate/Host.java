---------------------
Host 
---------------------
	# 根据请求的HOST进行路由
		* 一般场景就是，使用域名前缀来区分服务
	
	# 实现类
		HostRoutePredicateFactory

		* 配置类属性
			private List<String> patterns = new ArrayList<>();
	
	# 配置
		spring:
		  cloud:
			gateway:
			  routes:
			  - id: host_route
				uri: https://example.org
				predicates:
				  - Host=**.somehost.org,**.anotherhost.org
		

		* 可以使用通配符: **，可以指定多个域，使用逗号分隔
	
	# 支持模板变量，用的时候查，看源码也行
		