-------------------------
Capability
-------------------------
	# 能力支持
		* Feign 功能公开了核心 Feign 组件，以便可以修改这些组件
			例如，功能可以获取Client，装饰它，并将装饰后的实例返回给 Feign
		
			@Configuration
			public class FooConfiguration {
				@Bean
				Capability customCapability() {
					return new CustomCapability();
				}
			}
		
		* 创建一个或多个Capability bean 并将它们放置在 @FeignClient 配置中，让它修改客户端的行为
	
	

