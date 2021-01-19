----------------------------
hystrix配置					|
----------------------------
	# feign会为所有fegin客户端的方法都封装到Hystrix命令中进行服务保护

	# 开启,关闭Hystrix
		fegin.hystrix.enabled=true/false
	
	# 关闭熔断功能
		hystrix.command.default.execution.timeout.enabled=false
	
	# 全局配置
		hystrix.command.default.<key>=<value>
		
		* 在对hystrix进行配置的时候,必须保证它是启用状态
			fegin.hystrix.enabled=true

----------------------------
禁用hystrix					|
----------------------------
	# 全局关闭
		fegin.hystrix.enabled=false
	
	# 针对于某个服务进行关闭
		* 需要通过 @Scope("prototype") 注解来为客户端指定 Feign.Builder 实例
			
			@Configuration
			public DisableHystrixConfigration{
				@Bean
				@Scope("prototype")
				public Feign.Builder feignBuilder(){
					return Feign.Builder();
				}
			}
		
		* 接口配置
			@Feign(value = "USER-SERVICE",configuration = {DisableHystrixConfigration.class})


----------------------------
指定命令配置				|
----------------------------
----------------------------
服务降级配置				|
----------------------------

