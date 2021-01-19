----------------------------
zuul						|
----------------------------
	# Zuul包含了对请求的:路由,过滤,代理  ...等核心的主要功能
		
	# 路由
		* 负责把外部请求转发到具体的微服务实例上
		* 是实现外部访问统一入口的基础

	# 过滤
		* 负责对请求的处理过程进行干预
		* 是实现请求校验,服务聚合等功能的基础
	
	# Zuul和Eureka整合,将Zuul自身注册为Eureka服务治理下的应用,同时从Eureka中获取其他微服务的消息
		* 以后访问微服务,都是通过Zuul跳转后获得
	
	# 'Zuul服务最终还是会注册进Eureka'

	# Maven
		<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-zuul -->
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>

----------------------------
zuul-入门体验				|
----------------------------
	# 配置
		* 它也要当作一个服务,注册到Eureka中
		* 所以,它也需要服务提供者的那一套eureka配置

		spring:
		  application:
			name: zuul
		  security:
			user:
			  name: springcloud
			  password: 123456
		eureka:
		  client:
		    # 需要从注册中心获取服务
			fetch-registry: true
			# 需要把自己注册到注册中心
			register-with-eureka: true
			service-url:
			  defaultZone: http://${spring.security.user.name}:${spring.security.user.password}@localhost:8081/eureka/
	
	# 驱动注解
		@EnableZuulProxy

		* 标识注解,没有任何的属性
	
	# 通过路由进行访问
		* 协议:网关主机:端口:服务名称/接口

		http://localhost:8081/user-service/user/info/1
	
	# 通过Feign访问
		// ZUUL也是注册到eureka的服务
		@FeignClient(value = "ZUUL")
		// 通过 /{服务名}/{uri} 调用服务
		@RequestMapping("/user-service/user")
		public interface UserService {
			
			@GetMapping(value = "/info/{userId}")
			Object userInfo(@PathVariable("userId")Integer userId);
		}
				

----------------------------
hystrix 和 Ribbon的支持		|
----------------------------	
	# zuul本身就包含了hystrix和Ribbon,它天生就有线程隔离和断路器的自我保护功能,以及对服务端负载均衡调用的功能
		* 当使用 path 与 url 的映射关系来配置路由的时候,不会有熔断和负载均衡的功能
		* 所以要尽量的使用 path 和 service-id 来配置路由
	
	# 配置
		hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds
			* 配置服务调用的超时时间,单位是毫秒
			* 如果超时,会把该执行命令标记为TIMEOUT,并且抛出异常,响应异常JSON给调用方
		
		ribbon.ConnecTimeout
			* 设置路由转发请求的时候,创建连接的超时时间
			* 该值应该小于服务调用的超时时间,因为一旦出现创建链接超时,系统会尝试进行重试
			* 如果重试失败,则响应失败信息给调用方
		
		ribbon.readTimeout
			* 设置路由建立后,读取服务响应的超时时间
			* 该值应该小于服务调用的超时时间,因为一旦出现读取接超时,系统会尝试进行重试
		
		zuul.retryable=true
			* 可以通过该配置关闭/开启重试机制
		
		zuul.routes.<route>.retryable=false
			* 可以针对路由映射,设置是否要开启失败重试机制
	

------------------------
动态路由				|
------------------------
	# 路由的配置规则,几乎都是通过config来配置的
	# 结合 springcloud-config ,的动态刷新机制就可以动态的刷新路由规则