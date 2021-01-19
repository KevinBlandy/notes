--------------------
IRule				|
--------------------
	# 负载均衡的算法都需要实现 IRule 接口
	# 自定义轮询实现,一般都直接继承: AbstractLoadBalancerRule
	# 默认的算法(实现)
		RoundRobinRule
			* 轮询,当轮询到的服务不能调用时,会抛出异常

		RandomRule
			* 随机

		AvailabilityFilteringRule
			* 会先过滤由于多次访问故障处于断路器跳闸状态的服务
			* 还有并发连接数量超过阈值的服务,然对剩余服务列表按照轮询策略进行访问

		WeightedResponseTimeRule
			* 根据平均响应时间计算所有服务的权重,响应时间越快服务权重越大
			* 服务刚启动,统计信息不足,则使用 RoundRobinRule 策略,在统计信息足够后,会切换回此Rule

		RetryRule
			* 先按照 RoundRobinRule 策略获取服务,如果获取服务失败则在指定时间内进行重试
			* 其实就跟 RoundRobinRule 一样的,不过当服务不能进行调用的时候,会重新选择一个服务

		BestAvailableRule
			* 会优先过滤掉由于多次访问故障儿处于断路器跳闸状态的服务,然后选择一个并发量最小的服务

		ZoneAvoidanceRule
			* 复合判断Server所在区域的性能和Server的可用性选择服务器


--------------------
修改负载算法		|
--------------------
	# 注册全局通用的算法实现,只需要往ioc中注册指定的算法实现类即可
		@Bean
		public IRule iRule(){
			//覆盖默认的负载均衡算法
			return RandomRule();
		}
	
	# 针对指定的服务,定义轮询算法
		1,定义配置类,该类装载自定义的算法实现到ioc
			//不要添加@Configuration
			public class MyRule(){
				@Bean
				public IRule myRule(){
					return new RandomRule();
				}
			}
		
		2,使用 @RibbonClient 来为指定的服务设置负载算发的实现
			@RibbonClient(name = "USER",configuration = MyRule.class)
			@SpringBootApplication
			class Application{
				
			}

		* 注意,来自于官方的警告
		* 自定义的配置类(MyRule),不能放在 @CompentScan 所扫描的包以及子包下
		* 否则自定义的这个配置类,就会被所有 Ribbon 客户端所共享,也就达不到特殊定制化的目的了

		* 很好理解:'这个特殊的MyRule如果被扫描进了ioc,那么就跟上面的配置一样,直接就是Ribbon的默认算法实现了'
		* 反正就是针对于某个服务特定的算法实现类,不能被ioc加载(直接删除 @Configuration 注解,然后这个自定义算法实现放哪里都行)

		
		* @RibbonClient 源码
			public @interface RibbonClient {
				String value() default "";
				String name() default "";
				Class<?>[] configuration() default {};
				//有点想不通,为啥这里的configuration不直接是 IRule 的子类呢,非要是 Confuguration 类?
			}
	
	# 针对一批的服务,定义不同的轮询算法
		* 使用 @RibbonClients 注解
		* 源码,一看就懂怎么操作
			public @interface RibbonClients {
				//定义多个 @RibbonClient 
				RibbonClient[] value() default {};
				//定义默认的算法实现,如果 @RibbonClient 没有算法实现,则会从这里获取
				Class<?>[] defaultConfiguration() default {};
			}
	




