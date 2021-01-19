---------------------------
health					   |
---------------------------
	# app的运行状态节点
	
	# 相关的类
		HealthAggregator
			|-OrderedHealthAggregator
		HealthContributorRegistry
				|-DefaultHealthContributorRegistry
		HealthContributor
		Status
	
	
	# 预定义的 HealthIndicator 
		CassandraHealthIndicator
		CouchbaseHealthIndicator
		DiskSpaceHealthIndicator
		ElasticSearchRestHealthContributorAutoConfiguration
		HazelcastHealthIndicator
		InfluxDbHealthIndicator
		InfluxDbHealthIndicator
		JmsHealthIndicator
		MailHealthIndicator
		MongoHealthIndicator
		PingHealthIndicator
		RabbitHealthIndicator
		RedisHealthIndicator
		SolrHealthIndicator
		...
	
---------------------------
自定义HealthIndicator	   |
---------------------------
	# 实现接口: HealthIndicator 
		* 实现类的类名以 HealthIndicator 结尾, 例如: FooHealthIndicator 
		* 开头就是健康项的名称:Foo

		import org.springframework.boot.actuate.health.Health;
		import org.springframework.boot.actuate.health.HealthIndicator;
		import org.springframework.stereotype.Component;

		import java.util.Random;


		@Component
		public class FooHealthIndicator implements HealthIndicator {
			
			@Override
			public Health health() {
				int status = code();
				if ((status % 2)  == 0){
					return Health.down().withDetail("error", status).build();
				}
				return Health.up().build();
			}

			// 随机模拟状态码
			protected int code (){
				return new Random().nextInt();
			}
		}
	
	# 也可以继承:AbstractHealthIndicator
		* 构造方法
			AbstractHealthIndicator() 
			AbstractHealthIndicator(String healthCheckFailedMessage) 
			AbstractHealthIndicator(Function<Exception, String> healthCheckFailedMessage)

		* 覆写一个抽象方法
			void doHealthCheck(Health.Builder builder)

		* 在抽象方法中完成健康项目的检测
		* 通过抛出异常的方式, 来响应异常状态
			return builder.down(ex);



	# Health
		Health.up().build();

		Health.down().withDetail("error", status).build();

		Health.outOfService().build();

		Health.status(Status.DOWN).build();
			* 根据 Status 对象创建健康状态

		Health.status("DOWN").build();
			* 自定义名称的状态

		Health.unknown().build();

	
	# Status
		* 维护了一个 String code 和一个 String description
		* 静态常量
			Status UNKNOWN = new Status("UNKNOWN");
			Status UP = new Status("UP");
			Status DOWN = new Status("DOWN");
			Status OUT_OF_SERVICE = new Status("OUT_OF_SERVICE");、
		
		* 构造函数
			Status(String code){this(code, "");}
			Status(String code, String description)
		

---------------------------
配置项					   |
---------------------------
# 一般配置
management.health.defaults.enabled=false
	* 是否启用默认的健康健康项

management.endpoint.health.show-details=always
	* 是否显示app状态的详情

management.endpoint.health.show-components=
	* 枚举值: 
		never			不会显示
		when-authorized	显示给授权用户
		always			显示给所有用户

management.endpoint.health.roles=
management.endpoint.health.<name>.order=fatal,down,out-of-service,unknown,up
	* 配置指定项目状态的严重性顺序

management.endpoint.health.<name>.http-mapping.<status>=503
	* 配置指定项目, 在指定状态下的http状态码

# 集群配置
management.endpoint.health.group.custom.include=db
management.endpoint.health.group.custom.show-details=when-authorized
management.endpoint.health.group.custom.roles=admin
management.endpoint.health.group.custom.status.order=fatal,up
management.endpoint.health.group.custom.status.http-mapping.fatal=500