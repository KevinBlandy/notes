------------------------
ribbon配置				|
------------------------
	# 全局的配置方式
		ribbon.ConnectTimeout=500
		ribbon.ReadTimeout=5000

		* 配置语法:ribbon.<key>=<value>
	
	# 针对服务的配置方式
		USER-SERVICE.ribbon.ConnectTimeout=500
		USER-SERVICE.ribbon.ReadTimeout=500

		* 配置语法:<服务名称>.ribbon.<key>=<value>

------------------------
ribbon配置-项			|
------------------------

ribbon:
  eureka:
    # 是否禁用Eureka对Ribbon服务实例的实现
    enabled: true