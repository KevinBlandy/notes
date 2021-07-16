------------------------------
Predicate 的配置
------------------------------
	# 在配置文件中多个 Predicate 之间的关系是 & 都满足才会执行

	# 快捷配置
		spring:
		  cloud:
			gateway:
			  routes:
			  - id: after_route
				uri: https://example.org
				predicates:
				  - Cookie=mycookie,mycookievalue
		
		* 过滤器名称识别，后跟等号 ( =)，后跟由逗号 ( ,)分隔的参数值

	
	# 完整配置
		spring:
		  cloud:
			gateway:
			  routes:
			  - id: after_route
				uri: https://example.org
				predicates:
				  - name: Cookie
				    args:
					  name: mycookie
					  regexp: mycookievalue
		

		* name 指定名称
		* args 是路由工厂的配置类

------------------------------
系统预定义的 Predicate 
------------------------------
	ZoneDateTime
	Cookie
	Header
	Host
	Method
	Path
	Query
	RemoreAddr
