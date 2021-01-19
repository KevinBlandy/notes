----------------------------
Spring-Cloud				|
----------------------------
	# spring cloud 为开发人员提供了快速构建分布式系统的一些工具
		配置管理
		服务发现
		断路器
		路由
		微代理
		事件总线
		全局锁
		决策竞选
		分布式会话

	# 学习地址
		http://www.cnblogs.com/skyblog/p/5127690.html
		http://projects.spring.io/spring-cloud/#quick-start
		http://blog.csdn.net/forezp/article/details/70148833
		http://blog.didispace.com/Spring-Cloud%E5%9F%BA%E7%A1%80%E6%95%99%E7%A8%8B/
		http://www.guan2ye.com/
	
	# 征途
		Eureka				服务注册与发现
		Ribbon				负载均衡
		Feign				负载均衡
		Hystrix				断路器
		Zuul				路由网关
		SpringCloud-Config	分布式配置中心
	
	# 分布式服务
		服务治理
		服务注册
		服务调用
		服务负载均衡
		服务监控

----------------------------
Spring-Cloud微服务技术栈	|
----------------------------
	服务开发
		* springboot,spring,springmvc
	配置与管理
		* Archaius,Diamond
	注册与发现
		* Eureka,Consul,Zookeeper
	调用
		* Rest,Rpc,Grpc
	熔断器
		* Hystrix,Envoy
	负载均衡
		* Ribbon,Nginx
	接口调用(客户端调用服务的简化工具)
		* Feign
	消息队列
		* MQ系列
	配置中心管理
		* SpringCloudConfig,Chef
	服务路由(API网关)
		* Zuul
	全链路追踪
		* Zipkin,Brave,Dapper
	服务部署
		* Docker,OpenStack,Kubernetes
	数据流操作开发包
		* SpringCloud Stream
	事件消息总线
		* SpringCloud Bus

----------------------------
Spring-微服务原则			|
----------------------------
	单一职责
	服务自治
	接口明确

----------------------------
Spring-家族体系				|
----------------------------
	spring-cloud-aws
	spring-cloud-bus
	spring-cloud-cli
	spring-cloud-commons
	spring-cloud-contract
	spring-cloud-config
	spring-cloud-netflix	
	spring-cloud-security
	spring-cloud-cloudfoundry
	spring-cloud-consul
	spring-cloud-sleuth
	spring-cloud-stream
	spring-cloud-zookeeper
	spring-boot
	spring-cloud-task
	spring-cloud-vault
	spring-cloud-gateway


----------------------------
Spring-maven				|
----------------------------
	<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>