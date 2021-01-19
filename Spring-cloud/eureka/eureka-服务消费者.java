------------------------
服务消费者				|
------------------------
	# maven依赖
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
	
	# 注解启动
		@EnableEurekaClient
		* @EnableEurekaClient 表示了微服务是通过 eureka 框架进行服务注册的,不能通过其他的
		* 可以使用:@EnableDiscoveryClient 注解,该注解是一个接口,可以适用于所有服务治理的框架

	# 配置项
		eureka.client.register-with-eureka:false
			# 服务消费者,不注册
		eureka.client.service-url.defaultZone:http://localhost10086.com:10086/eureka,http://localhost10087.com:10087/eureka,http://localhost10088.com:10088/eureka
			# 注册中心的地址,如果Eureka注册中心是集群,则配置进所有的集群节点信息
		
	
	# 调用消费者
		
		//微服务的地址(其实就是名称),要大写
		private static final String SERVICE_NAME = "http://USER-SERVICE";
		
		@Autowired
		private RestTemplate restTemplate;

		@GetMapping
		public ResponseEntity<Void> test(User user){
			this.restTemplate.postForObject(SERVICE_NAME + "/add",user,JSONObject.class);
		}
		

		* 注册 RestTemplate 的时候要添加 @LoadBalanced,表示启动负载均衡
			@Bean
			@LoadBalanced
			public RestTemplate restTemplate() {
				return new RestTemplate();
			}

			* 而且我发现如果没有该注解,那么微服务将会调用失败

------------------------
服务提供者列表缓存		|
------------------------
	# 服务消费者每30s从注册中心获取最新的服务提供者列表,并且进行缓存
	# 可以通过配置修改该时间值
	
		eureka.client.registry-fetch-interval-seconds

		* 该值默认30s,每隔30s从注册中心获取一次服务提供者的列表
