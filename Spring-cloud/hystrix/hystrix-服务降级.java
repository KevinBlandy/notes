--------------------
服务降级			|
--------------------
	# 服务的降级处理,是在客户端完成的

	# 自定义类实现接口:FallbackFactory

		public interface FallbackFactory<T> {
			T create(Throwable cause);
		}
		
		* T泛型,便是需要熔断的点
		* T可以理解为是Service,那么在这里会返回一个T,返回的这个T的所有方法,都是熔断点T的熔断方法
		* 通俗理解:你给我个T,我返回一个T给你,当你给我的T发生熔断事件,则触发我返回给你的T的对应的方法
		
		* 该实现类不要忘记添加: @Component 注解,反正就是要给Spring的ioc管理
	
	# 在 @FeignClient 添加fallbackFactory的属性值
		@FeignClient(value = "USER",fallbackFactory = UserFallbackFactoryImpl.class)

		*  fallbackFactory的属性值为,FallbackFactory的实现类
	
	# 在application配置文件添加配置
		feign.hystrix.enabled=true
	
	
	# 其实这种方式,是在客户端完成的熔断,发现服务端都没法响应了,所以进行这个响应
	# 如果客户端执行了熔断,那么该服务会被降级
	
		
	
