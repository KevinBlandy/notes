--------------------------------
ribbon配置						|
--------------------------------
	# fegin使用ribbon来完成负载均衡
	# 可以直接配置ribbon的各个参数

	# 全局的配置方式
		ribbon.ConnectTimeout=500
		ribbon.ReadTimeout=5000

		* 配置语法:ribbon.<key>=<value>
	
	# 针对服务的配置方式
		USER-SERVICE.ribbon.ConnectTimeout=500
		USER-SERVICE.ribbon.ReadTimeout=500
		USER-SERVICE.ribbon.MaxAutoRetriesNextServer=2
		USER-SERVICE.ribbon.MaxAutoRetires=1

		* 配置语法:<服务名称>.ribbon.<key>=<value>