----------------------------
访问映射规则				|
----------------------------
	# 单实例配置(传统)
		zuul:
		 routes:
		  user-api:
		   path:/api/**					**/
		   url:http://springboot.io/
		
		*  /api/user/1 会被转发到 -> http://springboot.io/user/1
	
	#  多实例配置(面向服务)
		zuul:
		 routes:
		  user-api:
		   serviceId: USER
		   path:/api/**					**/
		
		ribbon.eureka.enabled=false
		USER.ribbon.listOfServers=http://localhost:8080/,http://localhost:8081/

		* 就是把对path的访问,转发到服务名为USER的服务列表
		* USER是个自定义的服务名称


	# 服务路由配置
		zuul:
		 ignored-service: USER
		 routes:
		  user:
		   serviceId: USER
		   path: /myuser/**								**/

		
		* ignored-service 属性非必须,它的存在就是废除指定服务的原有访问地址(/{服务名}/{uri})
			* 也就是说,必须使用routes定义的新地址
			* 如果该值为: * (星号),则表示废除所有的微服务原始访问地址

		* routes 像是一个Map
		* 里面定义了:
			路由名称.serviceId: 服务名称
			路由名称.path: 服务新的访问地址
		
		* http://网关ip:网关端口/路由名称/接口地址
	

	# 设置统一公共前缀
		zuul:
		 predix: /api
	
		* 所有的微服务前面都要加: /api
	
----------------------------
自定义路由映射规则			|
----------------------------
	# 实例化:PatternServiceRouteMapper
		
		@Bean
		public PatternServiceRouteMapper patternServiceRouteMapper(){
			return new PatternServiceRouteMapper("<name>","${name}");
		}

		* 可以使用正则来匹配出转发的路径
	
	# 构造函数
		public PatternServiceRouteMapper(String servicePattern, String routePattern)
			servicePattern
				* 服务名称匹配规则

			routePattern
				* 路由的匹配规则
	
----------------------------
本地跳转					|
----------------------------
	# 本地跳转
	zuul:
	  ignored-services: "*"
	  routes:
		api-user:
		  path: /user-api/****/
		  # 转发到网关的: /local/demo 上
		  url: forward:/local/demo

----------------------------
Cookie 与头信息				|
----------------------------
	# zuul在请求路由的时候,会过滤掉请求头信息中的一些敏感信息,防止它们被传递到下游服务器
	# 配置(下列出来的就是默认值):
		zuul:
		  sensitive-headers:
			- Cookie
			- Set-Cookie
			- Authorization
	
		* 可以自己覆写它
	
	# 也可以针对服务进行设置
		zuul.routes.<router>.customSensitiveHeaders=true
			* 对指定的路由开启自定义敏感头

		zuul.routes.<router>.sensitiveHeaders=
			* 把指定路由的敏感头设置为空


