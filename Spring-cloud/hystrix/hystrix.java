-------------------------
hystrix					 |
-------------------------
	# 熔断器,主要处理服务提供者, 服务的熔断,服务的降级
	# 多个微服务之间的连续调用,称之为为:扇出
		* 如果扇出链中,某个微服务响应时间过长,或者不可用,那么系统的执行过程就会漫长,进而引起系统雪崩
		
	# Hystrix是专门用于处理分布式系统的延迟和容错
		* Hyxstri保证在一个依赖出现问题的情况下,不会导致整体服务失败,避免级联故障
	
	
	# 断路器本身是一种开关控制
		* 当某个服务单元发生故障之后,通过断路器的故障监控(类似于保险丝),向调用方返回一个符合预期,可处理的备选响应
		* 而不是长时间的等待或者抛出调用方无法处理的异常
		* 从而避免了线程不会被长时间,不必要的占用,从而避免了故障在分布式系统中的蔓延,乃至雪崩


	# 服务熔断
		* 在某个微服务不可用,或者响应时间过长,会进行服务降级,进而熔断该服务的调用,快速响应错误信息
		* 当检测到该节点微服务响应正常后恢复调用链路
	
	# 默认5秒内,20次调用失败,就会启动熔断机制

	# 默认的超时时间是 2s

-------------------------
初试					 |
-------------------------
	# 坐标(服务消费者依赖)
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

	# 驱动注解
		@EnableCircuitBreaker

		* 服务消费者端开启
		* 默认超时时间:2000 毫秒 (2秒)

	
	# 定义熔断处理
		//在映射处理器上通过@HystrixCommand的fallbackMethod来指定熔断方法
		//响应该方法的返回值给服务调用端
		@HystrixCommand(fallbackMethod = "processHystrix_Get")
		@GetMapping("/user/{id}")
		public User get(@PathVariable("id")Integer id){
			
		}

		* 不仅仅是执行超时,如果该方法执行过程抛出了异常,也会触发熔断方法
		

		/*-----------------------------------------------------------*/

		//熔断方法,权限修饰符没特别的要求
		public User processHystrix_Get (@PathVariable("id")Integer id){
		
		}

		* 熔断方法如果不稳定,也可能会抛出异常的话,它也是可以添加:@HystrixCommand 注解来进行熔断加持的、
		* 可以在熔断方法的形参中添加一个参数:Throwable,以此来获取到目标方法中抛出的异常
		
	# @HystrixCommand
		String groupKey() default "";
		String commandKey() default "";
		String threadPoolKey() default "";

		String fallbackMethod() default "";
			* 指定熔断处理方法名称(熔断方法在当前handler类中)

		HystrixProperty[] commandProperties() default {};
			

		HystrixProperty[] threadPoolProperties() default {};
		Class<? extends Throwable>[] ignoreExceptions() default {};
			* 忽略目标方法中抛出的异常,不会触发熔断方法

		ObservableExecutionMode observableExecutionMode() default ObservableExecutionMode.EAGER;
			* 控制执行方式
			* 枚举值
				LAZY		使用 observe() 模式
				EAGER		使用 toObservable() 模式

		HystrixException[] raiseHystrixExceptions() default {};
		String defaultFallback() default "";

-------------------------
通用的熔断处理			 |
-------------------------
	# 自定义类实现接口:FallbackFactory

		public interface FallbackFactory<T> {
			T create(Throwable cause);
		}
		
		* T泛型,便是需要熔断的点
		* T可以理解为是Service,那么在这里会返回一个T,返回的这个T的所有方法,都是熔断点T的熔断方法
		* 通俗理解:你给我个T,我返回一个T给你,当你给我的T发生熔断事件,则触发我返回给你的T的对应的方法
		
		* 该实现类不要忘记添加: @Component 注解,反正就是要给Spring的ioc管理
	
		