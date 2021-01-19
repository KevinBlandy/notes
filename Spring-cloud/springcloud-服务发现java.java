------------------------
服务发现				|
------------------------
	# 其实就是,可以通过一个接口来获取到服务的信息
	# DiscoveryClient
	# 伪代码

		@Autowired
		private DiscoveryClient discoveryClient;
		
		//所有服务提供者
		List<String> services = discoveryClient.getServices();
		
		services.forEach(System.out::println);
		
		//获取指定服务的所有提供者信息
		List<ServiceInstance> instances = discoveryClient.getInstances("USER-SERVICE");

		for(ServiceInstance serviceInstance : instances){
			serviceInstance.getServiceId();
			serviceInstance.getHost();
			serviceInstance.getPort();
			serviceInstance.getUri();
		}