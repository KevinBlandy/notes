------------------------
服务提供者				|
------------------------
	# maven依赖
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
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

	# 配置项(配置类:EurekaInstanceConfigBean)
		spring.application.name=example-user-service
			# 当前微服务的名称,会以大写的形式出现在 eureka 的控制台

		eureka.client.service-url.defaultZone=http://localhost:10086/eureka/
			# 注册中心的地址
			# 如果要同时注册到多个注册中心,那么多个注册中心地址以逗号隔开

		eureka.instance.prefer-ip-address=true
			# 在eurake管理控制台中,该服务连接的地址以ip的形式出现,默认为主机名

		eureka.instance.instance-id=${spring.cloud.client.ipAddress}:${server.port}
			# 在管控台中,实例连接的名称
		
------------------------
服务提供者-info信息		|
------------------------
	# 添加依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
	
	# pom.xml 配置
		* 可以直接配置在prarent/pom.xml 中,所有子级模块都能使用
		<build>
			<!-- 允许src/main/resources资源访问 -->
			<resources>
				<resource>
					<directory>src/main/resources</directory>
					<filtering>true</filtering>
				</resource>
			</resources>
			<plugins>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-resources-plugin</artifactId>
					<configuration>
						<delimiters>
							<!-- 变量的取值边界符 -->
							<delimit>$</delimit>
						</delimiters>
					</configuration>
				</plugin>
			</plugins>
		</build>
	
	# yml
		info: 
		 app.name: javaweb-community
		 company.name: javaweb
		 build.artifactId: $project.artifactId$
		 build.version: $project.version$
		
		* info 其实就是一个map,可以在其下自定义的kv
		* 因为maven插件的配置,可以使用 $$ 来获取构建版本等信息

	# 在管理控制台,就可以以json的形式来通过 ..../info 来获取到以上配置的info信息(但是这个演失败了... ...)
		{"app":{"name":"javaweb-community"},"company":{"name":"javaweb"},"build":{"artifactId":"$project.artifactId$","version":"$project.version$"}}
	
	# eurek控制台中 info 的访问地址是可以修改的
		eureka.instance.statusPageUrlPath=/info

		* 如果服务提供者带有 context-path 属性的话,或者修改了健康检查的地址,就必须要配置它了
		* 该配置支持绝对路径,也就是说,服务提供者假设以以https提供服务:
			eureka.instance.statusPageUrlPath=https://localhost/info
------------------------
服务提供者-健康检查		|
------------------------
	# 默认情况下注册到eureka server的服务是通过心跳来告知自己是UP还是DOWN
	# 默认的方式只能知道服务提供者是否有心跳而已,不能知道服务是否还能正常的提供服务

	# 所以,可以修改心跳检查为健康检查的方式,通过在eureka客户端中配置
		eureka.client.healthcheck.enabled=true

		* 就可以改变eureka server对客户端健康检测的方式，改用actuator的/health端点来检测。
		* 必须导入maven依赖
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-actuator</artifactId>
			</dependency>

	# 修改eureka server访问端点的健康检查地址
		eureka.instance.healthCheckUrlPath=/health

		* 必须保证该地址可以访问,否则注册中心不会根据端点的健康检查来修改端点的状态
		* 当然,前提是客户端开启了健康检查:eureka.client.healthcheck.enabled=true

		* 如果服务提供者带有 context-path 属性的话,或者修改了健康检查的地址,就必须要配置它了
		* 该配置支持绝对路径,也就是说,服务提供者假设以以https提供服务:
			eureka.instance.healthCheckUrlPath=https://localhost/info


--------------------------------
服务提供者-服务的续约与失效控制	|
-------------------------------
	# 默认服务提供者会跟注册中心保持30s一次的心跳
	# 如果超过90s都没收到心跳,那么注册中心就会任务该服务凉了
	# 以上俩属性都可以通过配置来修改
		eureka.instance.lease-renewal-interval-in-seconds
			* 服务续约任务的调用时间间隔,默认30s
		eureka.instance.lease-expiration-duration-in-seconds
			* 服务时效的时间,默认90s,就是说多少秒没有收到心跳算是服务失效,就会把服务从列表移除

------------------------
服务提供者-服务下线		|
------------------------
	# 服务正常的关闭,会发送一个rest请求给注册中心,告知注册中心当前节点下线的信息
	# 注册中心收到消息后会吧该节点下线,并且把该事件广播出去
