------------------------------
Predicate 的配置
------------------------------

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
		* args 本质上就是一个 Map<String, String>, 用于自定义一些 Predicate 需要的属性

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
