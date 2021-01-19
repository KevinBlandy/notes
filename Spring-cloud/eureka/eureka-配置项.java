----------------------------
Eureka的配置项				|
----------------------------
	# 端点信息的配置
		* 主要是对端点进行描述的元数据

	# 服务中心的配置
		* Eureka服务中心的相关配置

	# 客户端的配置
		* 端点的一些配置
	
	# 配置类
		EurekaServerConfigBean
		EurekaInstanceConfigBean
		EurekaClientConfigBean

----------------------------
Eureka的配置项				|
----------------------------

	eureka.instance.name
		# 节点的名称
	eureka.instance.hostname
		# 节点的主机名称
	eureka.instance.instance-id
		# 节点的id,区分同一个服务中的不同节点
			* 同一个服务中节点的id不能相同,不然会出现只有一个节点能用的情况
	eureka.instance.lease-renewal-interval-in-seconds
		# 服务续约任务的调用时间间隔,默认30s
	eureka.instance.lease-expiration-duration-in-seconds
		# 服务时效的时间,默认90s,就是说多少秒没有收到心跳算是服务失效
	eureka.instance.statusPageUrlPath
		# 修改访问实例的元信息的地址路径,默认:/info
	eureka.instance.healthCheckUrlPath
		# 修改访问实例健康状态的地址路径,默认:/health

	eureka.client.fetch-registry
		# 是否从注册中心发现服务
	eureka.client.registry-fetch-interval-seconds
		# 每隔多少秒从注册中心获取一个服务提供者列表信息,默认30s
	eureka.client.register-with-eureka
		# 是否注册服务到注册中心
	eureka.client.service-url.defaultZone
		# 注册中心的地址
		# service-url 是一个 名为 serviceUrl 的 HashMap
			* 默认的 key:defaultZone ,value:http://localhost:8761/eureka/
	eureka.client.healthcheck.enabled
		#  是否通过 healthcheck 来检查服务提供者的状态


	eureka.server.enable-self-preservation
		# 是否开启自我保护模式,默认 true

----------------------------
对应的配置Bean				|
----------------------------
	# 服务端的相关配置参考bean
		org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean

		* 该Bean里面的属性都是以:eureka.server 开头的


	# 客户端的相关配置参考bean
		org.springframework.cloud.netflix.eureka.EurekaClientConfigBean

		* 该Bean里面的属性都是以:eureka.client 开头的
	
	# 端点信息的配置Bean
		org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean

		* 该Bean里面的属性都是以:eureka.instance 开头的
		* 该Bean里面包含了一个属性名为: metadataMap 的 HashMap,也就是说支持,自定义的键值参数
			eureka.instance.metadataMap.name=KevinBlandy
