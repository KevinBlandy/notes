----------------------------
ConfigServer 集群			|
----------------------------
	# 配置服务器高可用

	# 传统模式
		* 这种模式,就是把所有的配置都放在一个Git仓库
		* 然后使用多台配置服务器,在应用层负载均衡

	# 服务模式
		* 把配置服务器作为服务,注册到Eureka
		* 客户端通过Eureka获取到注册的配置服务

----------------------------
ConfigServer 服务化			|
----------------------------
	# 服务端添加Eureka的客户端依赖
		 <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
	
	# 服务端注册到注册中心
		eureka:
		  client:
		    # 配置服务器不需要从注册中心检索服务
			fetch-registry: false
			# 需要把自己注册到注册中心
			register-with-eureka: true
			service-url:
			  defaultZone: http://username:password@localhost:8081/eureka/
	
	# 客户端添加Eureka依赖(作为微服务消费者,肯定已经有了)
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

	# 客户端配置
		* 使用俩配置,代替原来的uri配置
		* 一个开启配置服务器的发现,一个设置配置服务器的id
			spring.cloud.config.discovery.enabled
			spring.cloud.config.discovery.service-id

		* 需要把 eureka和config的配置都一同的写在 : boostrap.yml 配置中
			spring:
			  application:
				name: springcloud
			  # 如果eureka存在账户名密码保护的话
			  security:
				user:
				  name: springcloud
				  password: 123456
			  cloud:
				config:
				  # 开启服务发现机制
				  discovery:
					# 配置服务器的id
					service-id: CONFIG-SERVER
					enabled: true
				  profile: test
				  label: master

			eureka:
			  client:
				service-url:
				  defaultZone: http://${spring.security.user.name}:${spring.security.user.password}@localhost:8081/eureka/
				register-with-eureka: false
