------------------------
eureka					|
------------------------
	# 介绍
		* 由 Netflix 开发的一个服务发现框架
		* 本身是一个基于REST的服务
		* 主要用于定位运行在AWS(亚马逊云服务)域中的中间层服务,以达到负载均衡和中间层故障转移的目的
		* spring cloud 将它集成在子项目 spring-cloud-netflix 中,以实现spring cloud的服务发现功能

	# 优点
		* eureka 来自于生存环境
		* spring cloud 对 eureka 支持良好
		* 项目活跃,迭代频繁,目前最新版本:1.3.5
		
	
	# 原理
	

	# wiki
		https://github.com/Netflix/eureka/wiki
		

------------------------
注册中心				|
------------------------
	# maven依赖
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
			<version>1.3.5.RELEASE</version>
		</dependency>
	
	# 开启 eureka server
		@SpringBootApplication
		@EnableEurekaServer			//通过 @EnableEurekaServer 注解开启 eureka 注册中心
		public class RegisterApplication {
			public static void main(String[] args)throws Exception{
				SpringApplication.run(RegisterApplication.class,args);
			}
		}


	# 配置项
		
		eureka.client.fetch-registry=false
			# 当前eureka 仅仅作为注册中心(server),不会去检索服务
		eureka.client.register-with-eureka=false
			# 当前eureka仅仅充当注册中心,忽略自己作为服务提供者的注册行为
			

		eureka.client.service-url.defaultZone=http://localhost:${server.port}/eureka
			# 服务提供者进行注册的地址,它是具备默认值的

------------------------
服务提供者				|
------------------------
	# maven依赖
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
			<version>1.3.5.RELEASE</version>
		</dependency>
	
	
	# 开启服务自动注册到注册中心
		@SpringBootApplication
		@EnableEurekaClient		//当前为eureka的客户端
		public class UserServiceApplication {
			
			public static void main(String[] args) {
				SpringApplication.run(UserServiceApplication.class, args);
			}
		}
		* @EnableEurekaClient 表示了当前微服务是通过 eureka 框架进行服务注册的,不能通过其他的
		* 可以使用:@EnableDiscoveryClient 注解,该注解是一个接口,可以适用于所有服务治理的框架

	# 配置项
		spring.application.name=example-user-service
			# 当前微服务的名称,会以大写的形式出现在 eureka 的控制台
		eureka.client.service-url.defaultZone=http://localhost:10086/eureka
			# 注册中心的地址
		eureka.instance.prefer-ip-address=true
			# 在eurake管理控制台中,该服务连接的地址以ip的形式出现,默认为主机名
		eureka.instance.instance-id=${spring.cloud.client.ipAddress}:${server.port}
			# 在管控台中,实例连接的名称
------------------------
服务消费者				|
------------------------
	# maven依赖
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
			<version>1.3.5.RELEASE</version>
		</dependency>
	
	# 配置项


------------------------
服务认证				|
------------------------
	# 保证只有可信任的服务提供者,才能注册到注册中心
	# 简单的认证配置
		security.basic.enabled=true
			# 开启安全配置
		security.user.name=root
			# 配置用户名
		security.user.password=root
			# 配置密码
		eureka.client.service-url.defaultZon=http://${security.user.name}:${security.user.password}@localhost:8761/eureka
			# 服务注册时候,使用配置的用户名和密码来进行注册
		
		* 需要添加依赖					
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-security</artifactId>
			</dependency>
		* 该配置成功后,不仅仅是服务实例需要通过账户名密码来进行连接
			eureka.client.service-url.defaultZon=http://root:root@localhost:8761/eureka
		* 登录eurake管理控制台也需要该账户名和密码来完成登录

	# 通过组件来完成服务的认证 TODO
		DiscoveryClientOptionArgs

------------------------
实例监控接口			|
------------------------
	# 该接口会返回JSON格式的实例状态信息
	# 需要添加依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
		* 不多解释,spring-boot的一个适用于生产环境的监控组件

	# 配置项
		eureka.instance.status-page-url=${management.context-path}/info
			# 实例状态页面地址
		eureka.instance.health-check-url=${management.context-path}/health
			# 运行状况指示器地址

------------------------
HTTPS					|
------------------------
	TODO

------------------------
健康检查				|
------------------------
	# Spring Boot Actuator提供了/health端点,该端点可展示应用程序的健康信息
	# 只有将服务实例的健康状态传播到Eureka Server就可以了,实现这点很简单
	# 服务实例配置项
		eureka.client.healthcheck.enabled=true			
			# 开启健康检查(需要spring-boot-starter-actuator依赖)


------------------------
注解					|
------------------------
	@EnableEurekaServer
		# 开启 eureka 注册中心服务

	@EnableEurekaClient
		# 开启 eureka 微服务,并且自动注册到注册中心

------------------------
与zookeeper比较			|
------------------------
	# Eurake保证AP
		* eurak设计时,先保证可用性,eurak各个节点都是平等的,几个节点挂掉,不会影响到正常节点的工作
		* Eurake服务提供者向注册中心注册服务时,如果链接失败,会自动切换到其他的节点
		* 只要有一台Eureka还在,就能保证注册服务可用(保证可用性)
		* 只不过查询到的数据可能不是最新的(不保证强一致性)
		* Eurake还有一种自我保护机制,15分钟内超过85的节点都没有正常的心跳,那么Eurake会认为注册中心与服务提供者出现了网络故障
			1,Eureka不再从注册列表中移除,因为长时间没有收到心跳而过期的服务
			2,Eureka仍然可以接收新的服务注册,和查询请求,但是不会同步到其他节点(保证当前节点依然可用)
			3,当网络稳定时,当前实例新注册的信息会被同步到其他节点中
		
		* Eurake可以很好的应对,因网络异常导致部分节点失去联系的情况,而不会像zookeeper那样直接瘫痪整个节点

	# Zookeeper保证CP
		* 从节点查询服务信息的时候,能够接受返回的可能是几分钟前的注册信息,但是不能接受返回宕掉的服务信息
		* 也就是说,服务注册功能,对可用性的要求要高于一致性
		* 但是zk会出现一种情况,当master节点与其他节点失去联系,剩余节点会重新进行leader选举
		  选举leader的时间太长,30s - 120s,而且选举期间整个集群不可用,这个就导致在选举期间注册服务瘫痪
		* 在云部署环境下,因为网络问题导致的zk集群失去master节点的几率很大,虽然服务能够恢复,但是漫长的选举时间导致服务注册长期不能使用是不可容忍的

	