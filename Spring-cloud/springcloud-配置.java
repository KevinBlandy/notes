------------------------------------
预定义配置							|
------------------------------------
	spring.cloud.client.ipAddress
		# 服务提供端的IP地址
	
	spring.cloud.client.hostname
		# 服务提供端的host name
	

spring:
  cloud:
    loadbalancer:
      retry:
		# 是否开启客户端的异常重试机制(配置类:LoadBalancerRetryProperties)
        enabled: true