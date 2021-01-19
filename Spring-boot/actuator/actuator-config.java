------------------ 
全局通用配置	  |
------------------ 
	management.endpoints.enabled-by-default=true
		* 是否启用默认的端点(默认 true)
		* 设置为 false 的话, 禁用(未启用)的端点将从应用程序上下文中完全删除

		* 可以把它设置为false, 然后再手动的设置app需要暴露的端点, 从而避免加载了所有默认端点

	management.endpoints.jmx.exposure.exclude
	management.endpoints.jmx.exposure.include=*
	management.endpoints.web.exposure.exclude
	management.endpoints.web.exposure.include=info, health
		* 设置以jmx/http形式暴露, 不暴露的端点信息
		* 可以写多个, 使用逗号分隔
		* 支持使用通配符: * 表示所有

	management.endpoints.web.base-path=/actuator
		* 访问端点的根路径
	management.endpoints.web.path-mapping.<id>=/path
		* 配置指定id端点的访问路径
	

	management.endpoint.<id>.enabled=true
		* 启用/禁用指定id的端点

	management.endpoint.beans.cache.time-to-live=10s
		* 端点对不带任何参数的读取操作的响应自动缓存
		* 通过该配置，配置端点的缓存时间

------------------ 
CORS的配置		  |
------------------ 
	# CorsEndpointProperties

	management.endpoints.web.cors.allowed-origins=https://example.com
	management.endpoints.web.cors.allowed-methods=*
	management.endpoints.web.cors.allowed-headers=*
	management.endpoints.web.cors.exposed-headers=
	management.endpoints.web.cors.allow-credentials=true
	management.endpoints.web.cors.max-age=1800s




