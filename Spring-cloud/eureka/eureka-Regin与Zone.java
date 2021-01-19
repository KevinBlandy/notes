----------------------
Regin与Zone			  |
----------------------
	# 一个Regin可以包含多个Zone
	# 每个客户端都要注册到一个Zone中,所以每个客户端对应一个Zone一个Regin
	# 服务调用的时候,优先访问同一个Zone中的服务提供方,如果访问不到就尝试访问其他的Zone

	# 为应用指定Zone
		eureka.client.availability-zones