----------------------
RequestSize
----------------------
	# 限制请求的大小，单位是byte，超过设定值返回413 Payload Too Large
	
	
	# 配置
		spring:
		  cloud:
			gateway:
			  routes:
			  - id: request_size_route
				uri: http://localhost:8080/upload
				predicates:
				  - Path=/upload
				filters:
				  - name: RequestSize
					args:
					  maxSize: 5000000
		

		* 默认配置单位是字节
		* 可以使用字符串: KB/MB
			maxSize: 5KB
		
		* 如果一个路由没有配置这个Filter的话，默认最大请求为: 5MB
	
		


