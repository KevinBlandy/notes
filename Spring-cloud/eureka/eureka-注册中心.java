------------------------
注册中心				|
------------------------
	# maven依赖
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
	
	# 开启 eureka server
		@SpringBootApplication
		@EnableEurekaServer			//通过 @EnableEurekaServer 注解开启 eureka 注册中心
		public class RegisterApplication {
			public static void main(String[] args)throws Exception{
				SpringApplication.run(RegisterApplication.class,args);
			}
		}


	# 配置项,配置类(EurekaInstanceConfigBean,EurekaServerConfigBean)
		
		eureka.instance.name=localhost
			# eureka服务端的实例名称
		eureka.client.fetch-registry=false
			# 当前eureka 仅仅作为注册中心(server),不会去检索服务
		eureka.client.register-with-eureka=false
			# 当前eureka仅仅充当注册中心,忽略自己作为服务提供者的注册行为
			

		eureka.client.service-url.defaultZone=http://localhost:${server.port}/eureka/
			# 服务提供者进行注册的地址,它是具备默认值的


------------------------
自我保护策略			|
------------------------
	# 默认情况下,Eureka在一定时间内没有接收到某个微服务实例的心跳,Eureka会注销该实例(默认90秒)
		* 但是,当网络分区故障发生时,微服务与Eureka之间无法正常通信,以上行为可能变得非常危险了
		* 因为微服务本身是健康的,'不应该注销该微服务',Eureka通过'自我保护机制',来解决这个问题
		* 当Eurake节点在短时间内丢失了过多的客户端(服务提供者)时,那么这个节点就会进入自我保护模式
		* 进入该模式后,Eurake会保护服务注册表中的信息,不再进行删除表中的数据(也就是说不会注销任何服务)
		* 在网络故障恢复时,自动退出自我保护模式
		* eureka服务器,默认每隔60s检查一次微服务的实例是否down掉

	# 在自我保护模式中,Eurake会保护注册表中的信息,不再删除任何服务实例
		* 当它收到的心跳数重新恢复到阈值以上时,Eurake Server节点就会自动退出自我保护模式
		* 它的设计哲学就是:'宁可保护错误的服务注册信息,也不盲目注销任何可能健康的服务实例'

	# 自我保护模式是一种对应'网络异常的安全保保护措施'
		* 它的架构哲学是宁可同时保留所有微服务(监控的微服务,和不健康的服务都会保留)
		* 使用自我保护模式,可以让Eurake集群更加健壮文档

	# 禁用自我保护模式
		eureka.server.enable-self-preservation=false
	
	# 这种机制下,客户端能会获取到失效的服务,所以要求客户端必须要有容错机制(重试,熔断)


------------------------
安全的注册中心			|
------------------------
	# 注册中心配置
		1,添加security依赖
			<dependency> 
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-security</artifactId>
			</dependency>

		2,配置用户名和密码
			spring.security.user.name=KevinBlandy
			spring.security.user.password=123456

		3,在访问路径上加入用户名密码
			eureka.client.serviceUrl.defaultZone=http://${spring.security.user.name}:${spring.security.user.password}@localhost:${server.port}/eureka/
			* 注意格式: 用户名:密码@主机名:端口
	
		* 控制台也需要使用该用户名和密码登录
	
	# 客户端的配置
		
		spring.security.user.name=KevinBlandy
		spring.security.user.password=123456

		eureka.client.serviceUrl.defaultZone=http://${spring.security.user.name}:${spring.security.user.password}@localhost:10086/eureka/

		* 客户端不用security的依赖,只用在注册中心的地址中添加用户名和密码
	
	# springboot2.0以后security默认开启了csrf,可能导致客户端的注册失败(响应客户端401状态码)
		* 可以尝试在服务端关闭csrf

		@EnableWebSecurity
		public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

			@Override
			protected void configure(HttpSecurity http) throws Exception {
				http.csrf().disable();
				super.configure(http);
			}
		}