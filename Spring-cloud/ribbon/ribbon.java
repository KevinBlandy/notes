----------------------------
ribbon						|
----------------------------
	# Spring Cloud Ribbon 是基于Netflix Ribbon实现的一套'客户端,负载均衡'工具
	# Ribbon是netflix发布的开源项目,主要提供客户端的软件负载均衡算法
	# LB(Load Balance)在微服务或者分布式集群中经常用的一种应用
	# 常见的负载均衡Nginx,LVS,硬件F5
	
	# 负载均衡的类型
		* 硬件LB
			* F5之类的,买不起
		* 进程内LB
			* 把LB逻辑集成到消费方,消费方从注册中心获取服务地址,再从地址中选择一个进行调用
	
	# ribbon在工作时分为两步
		1,先选择EurekaServer,会选择同一区域内负载较少的EurekaServer
		2,根据用户指定的策略,从Server取到服务提供者(多个)的地址信息中获取一个进行远程调用
	
	# ribbon提供了多种策略:轮询,随机,根据响应时间加权

	# 默认算法是-轮询


----------------------------
整合						|
----------------------------
	# 坐标
		 <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
	
	# 开启负载均衡
		@Configuration
		public class RestTemplateConfig{

			@Bean
			@LoadBalanced
			public RestTemplate restTemplate(){
				return new RestTemplate();
			}
		}

		* 给 RestTemplate 注册Ioc时,添加 @LoadBalanced 注解
		* @LoadBalanced 是Springcloud定义的接口注解
		
----------------------------
LoadBalancerClient			|
----------------------------
	# 负载均衡器
	# @LoadBalanced 用来给 RestTemplate做标记,以使用 LoadBalancerClient 来配置他

		public interface LoadBalancerClient extends ServiceInstanceChooser {
			
			/*
				来自于ServiceInstanceChooser接口
				从负载均衡器中挑一个指定名称的服务实例
			*/
			ServiceInstance choose(String serviceId);
			
			/*
				使用从负载均衡器中挑出来的实例执行请求
			*/
			<T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException;

			<T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException;

			
			/*
				为系统构建一个合适的URI:	host:port
				就是把服务名称翻译为了:		host:port
			*/
			URI reconstructURI(ServiceInstance instance, URI original);
		}

----------------------------
重试机制					|
----------------------------
	//TODO
