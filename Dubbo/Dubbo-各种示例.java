--------------------------------
1,启动检查						|
--------------------------------
	# Dubbo'缺省会在启动时检查依赖的服务是否可用'，不可用时会抛出异常，阻止Spring初始化完成，以便上线时，能及早发现问题，默认check=true。
	# 关闭一个服务的启动时检查(没有提供者报错)
		<dubbo:reference check="false" />

	# 关闭所有服务的启动时检查(没有提供者时报错)
		<dubbo:consumer check="false" />

	# 关闭注册中心启动时检查(注册订阅失败时报错)
		<dubbo:registry check="false" />

	# 引用缺省是延迟初始化的，只有引用被注入到其它Bean，或被getBean()获取，才会初始化。
		* 如果需要饥饿加载，即没有人引用也立即生成动态代理，可以配置：
			<dubbo:reference init="true" />
		* init=true  :就是这个服务没有被注入到其他的bean中,或者是没有被getBean获取,都会执行初始化
		* init=false :就是这个服务,只有被注入到其他的bean中,或者是被getBean获取,才会初始化

	# dubbo.properties 配置
		dubbo.reference.com.foo.BarService.check=false
		dubbo.reference.check=false
		dubbo.consumer.check=false
		dubbo.registry.check=false

	# JVM启动参数 配置
		java -Ddubbo.reference.com.foo.BarService.check=false
		java -Ddubbo.reference.check=false
		java -Ddubbo.consumer.check=false 
		java -Ddubbo.registry.check=false
					
	# 注意
		dubbo.reference.check=false，强制改变所有reference的check值，就算配置中有声明，也会被覆盖。
		dubbo.consumer.check=false，是设置check的缺省值，如果配置中有显式的声明，如：<dubbo:reference check="true"/>，不会受影响。
		dubbo.registry.check=false，前面两个都是指订阅成功，但提供者列表是否为空是否报错，如果注册订阅失败时，也允许启动，需使用此选项，将在后台定时

--------------------------------
2,集群容错						|
--------------------------------
	# 提供者配置
		* 全局粗粒度配置
			<dubbo:provider cluster="failover"/>	
		* 单个服务配置
			<dubbo:service cluster="failover"/>
		* 精确到方法
			<dubbo:service>
				<dubbo:method name="getUser" retries="2"></dubbo:method>
			</dubbo:service>

	# 消费者配置
		* 全局粗粒度控制
			<dubbo:consumer cluster="failover"/>		
		* 单个接口控制
			<dubbo:reference cluster="failover"/>
		* 细粒度到方法
			<dubbo:reference>
				<dubbo:method name="getUser" retries="2"></dubbo:method>
			</dubbo:reference>

	# 可选容错类型
		failover 
			* 失败自动切换，当出现失败，重试其它服务器。('缺省')
			* 通常用于读操作，但重试会带来更长延迟。
			* 可通过 retries="2" 来设置重试次数(不含第一次)。如果该值 = 0,那么不进行重试.调用失败,直接抛出异常

		failfast 
			* 快速失败，只发起一次调用，失败立即报错。
			* 通常用于非幂等性的写操作，比如新增记录。

		failsafe 
			* 失败安全，出现异常时，直接忽略。
			* 通常用于写入审计日志等操作。

		failback 
			* 失败自动恢复，后台记录失败请求，定时重发。
			* 通常用于消息通知操作。

		forking 
			* 并行调用多个服务器，只要一个成功即返回。
			* 通常用于实时性要求较高的读操作，但需要浪费更多服务资源。
			* 可通过 forks="2" 来设置最大并行数。

		broadcast 
			* 广播调用所有提供者，逐个调用，任意一台报错则报错。(2.1.0开始支持)
			* 通常用于通知所有提供者更新缓存或日志等本地资源信息。
		

--------------------------------
3,负载均衡						|
--------------------------------
	# Dubbo指定了N多负载均衡策略
		random (默认)
			* 随机，按权重设置随机概率。
			* 在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。
			* 权重配置属性 : weight

		roundRobin 
			* 轮循，按公约后的权重设置轮循比率。
			* 存在慢的提供者累积请求问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。
	
		leastActive 
			* 最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差。
			* 使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大。

		consistentHash 
			* 一致性Hash，相同参数的请求总是发到同一提供者。
			* 当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。
			* 算法参见：http://en.wikipedia.org/wiki/Consistent_hashing。
			* 缺省只对第一个参数Hash，如果要修改，请配置	<dubbo:parameter key="hash.arguments" value="0,1" />
			* 缺省用160份虚拟节点，如果要修改，请配置		<dubbo:parameter key="hash.nodes" value="320" />
	
	# 服务者配置
		* 全局负载均衡策略
			<dubbo:provider loadbalance="random" weight="5"/>
		* 服务级别负载均衡策略
			<dubbo:service loadbalance="random" weight="5"/>
		* 方法级别负载均衡策略
			<dubbo:service >
				<dubbo:method loadbalance="random" weight="5"/>
			</dubbo:service>

	# 消费者配置
		* 全局负载均衡策略
			<dubbo:consumer loadbalance="random"/>
		* 服务级别负载均衡策略
			<dubbo:reference loadbalance="random"/>
		* 方法级别负载均衡策略
			<dubbo:reference>
				<dubbo:method loadbalance="random"/>
			</dubbo:reference>
	
	# 负载均衡策略可以自定义,Dubbo有提供接口,自己去看文档了

--------------------------------
4,线程模型						|
--------------------------------

	# 事件处理线程说明
		* 如果事件处理的逻辑能迅速完成，并且不会发起新的IO请求，比如只是在内存中记个标识，则直接在IO线程上处理更快，因为减少了线程池调度。
		* 通俗理解:服务端收到调用请求,很快能执行完,而且后面没请求了.行,我自己搞定,不给其他线程或者是线程池

		* 但如果事件处理逻辑较慢，或者需要发起新的IO请求，比如需要查询数据库，则必须派发到线程池，否则IO线程阻塞，将导致不能接收其它请求。
		* 通俗理解:服务端收到调用请求,要执行很久,必须要把这个请求给线程池执行了,不然我进行执行的话.就不能执行其他的远程调用请求了

		* 如果用IO线程处理事件，又在事件处理过程中发起新的IO请求，比如在连接事件中发起登录请求，会报“可能引发死锁”异常，但不会真死锁。
		* 通俗理解:自己理解... ...

	# dispatcher
		all		
			* 所有消息都派发到线程池，包括请求，响应，连接事件，断开事件，心跳等。
		direct	
			* 所有消息都不派发到线程池，全部在IO线程上直接执行。
		message	
			* 只有请求响应消息派发到线程池，其它连接断开事件，心跳等消息，直接在IO线程上执行。
		execution 
			* 只请求消息派发到线程池，不含响应，响应和其它连接断开事件，心跳等消息，直接在IO线程上执行。
		connection 
			* 在IO线程上，将连接断开事件放入队列，有序逐个执行，其它消息派发到线程池。
	
	# threadpool
		fixed 
			* 固定大小线程池，启动时建立线程，不关闭，一直持有。(缺省)
		cached 
			* 缓存线程池，空闲一分钟自动删除，需要时重建。
		limited 
			* 可伸缩线程池，但池中的线程数只会增长不会收缩。(为避免收缩时突然来了大流量引起的性能问题)。

	# 配置信息
		<dubbo:protocol name="dubbo" 
			dispatcher="all" 
			threadpool="fixed" 
			threads="100" />
		
		<dubbo:provider 
			threadpool="fixed" 
			threads="100"/>

--------------------------------
5,直连提供者					|
--------------------------------
	# 绕过注册中心,直接连接服务提供者.很显然,仅仅在测试环境下玩玩

	# 消费者配置中,配置url指向提供者，将绕过注册中心，多个地址用分号隔开，配置如下：(1.0.6及以上版本支持)
		<dubbo:reference id="xxxService" interface="com.alibaba.xxx.XxxService" url="dubbo://localhost:20890" />
	
	# 也可以在properties和通过java -D参数来进行配置,详情看API

--------------------------------
6,只订阅						|
--------------------------------
	# 为方便开发测试，经常会在线下共用一个所有服务可用的注册中心，这时，如果一个正在开发中的服务提供者注册，可能会影响消费者不能正常运行。

	# 可以让服务提供者开发方，只订阅服务(开发的服务可能依赖其它服务)，而不注册正在开发的服务，通过直连测试正在开发的服务。

	# Dubbo的一个服务,可以在注册中心,订阅的其他的服务.但是,自己本身这个服务.如果需要测试,直连方式,来调用本服务
		<dubbo:registry address="10.20.153.10:9090" register="false" />
		<dubbo:registry address="10.20.153.10:9090?register=false" />
	
	# 该模式,指的是需要做开发调试的服务提供者,仅仅向注册中心订阅自己需要的服务.但不向注册中心注册自己本身可以提供的服务
	# 该模式,需要结合"直连提供者"配置,来进行调试


--------------------------------
7,只注册						|
--------------------------------
	# 如果有两个镜像环境，两个注册中心，有一个服务只在其中一个注册中心有部署，另一个注册中心还没来得及部署，而两个注册中心的其它应用都需要依赖此服务，所以需要将服务同时注册到两个注册中心，但却不能让此服务同时依赖两个注册中心的其它服务。
	# 可以让服务提供者方，只注册服务到另一注册中心，而不从另一注册中心订阅服务。
	# 禁用订阅配置：
		<dubbo:registry id="hzRegistry" address="10.20.153.10:9090" />
		<dubbo:registry id="qdRegistry" address="10.20.141.150:9090" subscribe="false" />

		<dubbo:registry id="hzRegistry" address="10.20.153.10:9090" />
		<dubbo:registry id="qdRegistry" address="10.20.141.150:9090?subscribe=false" />
	
	# 说白了就是,我本身,仅仅注册到某一个注册中心,提供服务.但是我本身依赖的服务,不能会从这个注册中心获取


--------------------------------
8,静态服务						|
--------------------------------
--------------------------------
9,多协议						|
--------------------------------
	# 不同的服务,在性能上适用不同的协议来传输,比如大数据用短连接协议，小数据大并发用长连接协议。
	# 服务端配置
		<dubbo:application name="world"  />
		<dubbo:registry id="registry" address="10.20.141.150:9090" username="admin" password="hello1234" />
		<!-- 多协议配置 -->
		<dubbo:protocol name="dubbo" port="20880" />
		<dubbo:protocol name="rmi" port="1099" />
		<!-- 使用dubbo协议暴露服务 -->
		<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="demoService" protocol="dubbo" />
		<!-- 使用rmi协议暴露服务 -->
		<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="demoService" protocol="rmi" />
		<!-- 使用多个协议暴露服务(暴露了同一个类型,不同协议的N个服务) -->
		<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="demoService" protocol="dubbo,hessian" />

	
	# 消费端配置
		* <dubbo:reference protocol="dubbo"/>		
		* '只调用指定协议的服务提供方，其它协议忽略。'
	

--------------------------------
10,多注册中心					|
--------------------------------
	# 一个服务,注册到多个注册中心
		<!-- 多注册中心配置 -->
		<dubbo:registry id="hangzhouRegistry" address="10.20.141.150:9090" />
		<dubbo:registry id="qingdaoRegistry" address="10.20.141.151:9010" default="false" />
		<!-- 向多个注册中心注册 -->
		<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService" registry="hangzhouRegistry,qingdaoRegistry" />

	# 多个服务,注册到多个注册中心
		<!-- 多注册中心配置 -->
		<dubbo:registry id="chinaRegistry" address="10.20.141.150:9090" />
		<dubbo:registry id="intlRegistry" address="10.20.154.177:9010" default="false" />
		<!-- 向中文站注册中心注册 -->
		<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService" registry="chinaRegistry" />
		<!-- 向国际站注册中心注册 -->
		<dubbo:service interface="com.alibaba.hello.api.DemoService" version="1.0.0" ref="demoService" registry="intlRegistry" />
	
	# 消费端引用
		<!-- 多注册中心配置 -->
		<dubbo:registry id="chinaRegistry" address="10.20.141.150:9090" />
		<dubbo:registry id="intlRegistry" address="10.20.154.177:9010" default="false" />
		<!-- 引用中文站服务 -->
		<dubbo:reference id="chinaHelloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0" registry="chinaRegistry" />
		<!-- 引用国际站站服务 -->
		<dubbo:reference id="intlHelloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0" registry="intlRegistry" />

--------------------------------
11,服务分组						|
--------------------------------
	# 服务提供端,一个接口有N个实现.可以用group区分
		<dubbo:service group="feedback" interface="com.xxx.IndexService" />
		<dubbo:service group="member"   interface="com.xxx.IndexService" />
	
	# 消费端调用
		<dubbo:reference id="feedbackIndexService" group="feedback" interface="com.xxx.IndexService" />
		<dubbo:reference id="memberIndexService"   group="member"   interface="com.xxx.IndexService" />
	
		<!-- 任意组：(2.2.0以上版本支持，总是只调一个可用组的实现) -->
		<dubbo:reference id="barService" interface="com.foo.BarService" group="*" />
		
--------------------------------
12,多版本						|
--------------------------------
	# 当一个接口实现，出现不兼容升级时，可以用版本号过渡，版本号不同的服务相互间不引用。
	# 在低压力时间段，先升级一半提供者为新版本
	# 再将所有消费者升级为新版本
	# 然后将剩下的一半提供者升级为新版本
	# 提供者配置
		<dubbo:service interface="com.foo.BarService" version="1.0.0" />
		<dubbo:service interface="com.foo.BarService" version="2.0.0" />
	# 消费者配置
		<dubbo:reference id="barService" interface="com.foo.BarService" version="1.0.0" />
		<dubbo:reference id="barService" interface="com.foo.BarService" version="2.0.0" />

	# 不区分版本：(2.2.0以上版本支持)
		<dubbo:reference id="barService" interface="com.foo.BarService" version="*" />

--------------------------------
13,分组聚合						|
--------------------------------
--------------------------------
14,参数验证						|
--------------------------------
--------------------------------
15,结果缓存						|
--------------------------------
--------------------------------
16,泛化引用						|
--------------------------------
--------------------------------
17,泛化实现						|
--------------------------------
--------------------------------
18,回声测试						|
--------------------------------
--------------------------------
19,上下文信息					|
--------------------------------
--------------------------------
20,隐式传参						|
--------------------------------
--------------------------------
21,异步调用						|
--------------------------------
--------------------------------
22,本地调用						|
--------------------------------
--------------------------------
23,参数回调						|
--------------------------------
--------------------------------
24,事件通知						|
--------------------------------
--------------------------------
25,本地存根						|
--------------------------------
--------------------------------
26,延迟暴露						|
--------------------------------
	# 如果你的服务需要Warmup时间，比如初始化缓存，等待相关资源就位等，可以使用delay进行延迟暴露。

	# 延迟5秒暴露服务：
		<dubbo:service delay="5000" />

	# 延迟到Spring初始化完成后，再暴露服务：(基于Spring的ContextRefreshedEvent事件触发暴露)
		<dubbo:service delay="-1" />


--------------------------------
27,并发控制						|
--------------------------------
	# 限制服务端某个服务并发执行数(占用线程池数)
		<dubbo:service interface="com.foo.BarService" executes="10" />
	
	# 限制服务端某个方法并发数(占用线程池数)
		<dubbo:service interface="com.foo.BarService">
			<dubbo:method name="sayHello" executes="10" />
		</dubbo:service>
	

	# 限制某个服务,每个客户端的最大并发(占用连接的请求数)
		* 服务端限制
			<dubbo:service interface="com.foo.BarService" actives="10" />
		* 消费端限制
			<dubbo:reference interface="com.foo.BarService" actives="10" />
	
	# 限制某个服务的某个方法,客户端最大并发(占用连接的请求数)
		* 服务端限制
			<dubbo:service interface="com.foo.BarService">
				<dubbo:method name="sayHello" actives="10" />
			</dubbo:service>
		* 消费端限制
			<dubbo:reference interface="com.foo.BarService">
				<dubbo:method name="sayHello" actives="10" />
			</dubbo:service>
	
	# 如果<dubbo:service>和<dubbo:reference>都配了actives，<dubbo:reference>优先

--------------------------------
28,连接控制						|
--------------------------------
	# 限制服务端的接收的连接,不能超过10个
		<dubbo:provider protocol="dubbo" accepts="10" />
		<dubbo:protocol name="dubbo" accepts="10" />

	# 限制客户端服务使用连接连接数：(如果是长连接，比如Dubbo协议，connections表示该服务对每个提供者建立的长连接数)

		<dubbo:service interface="com.foo.BarService" connections="10" />	
		<dubbo:reference interface="com.foo.BarService" connections="10" />
	# 如果<dubbo:service>和<dubbo:reference>都配了connections，<dubbo:reference>优先
	
--------------------------------
29,延迟连接						|
--------------------------------
	# 延迟连接，用于减少长连接数，当有调用发起时，再创建长连接。
	# 只对使用长连接的dubbo协议生效。
		<dubbo:protocol name="dubbo" lazy="true" />

--------------------------------
30,粘滞连接						|
--------------------------------
	# 粘滞连接用于有状态服务，尽可能让客户端总是向同一提供者发起调用，除非该提供者挂了，再连另一台。
	# 粘滞连接将自动开启延迟连接，以减少长连接数，参见：延迟连接 (+)
		<dubbo:protocol name="dubbo" sticky="true" />

--------------------------------
31,令牌验证						|
--------------------------------
	# 作用
		防止消费者绕过注册中心访问提供者
		在注册中心控制权限，以决定要不要下发令牌给消费者
		注册中心可灵活改变授权方式，而不需修改或升级提供者
	
	# 全局开启
		<!--随机token令牌，使用UUID生成-->
		<dubbo:provider interface="com.foo.BarService" token="true" />
	
	# 针对于某个服务开启
		<dubbo:protocol name="dubbo" token="true" />
	
	# 针对于协议级别
		<!--随机token令牌，使用UUID生成-->
		<dubbo:protocol name="dubbo" token="true" />
		<!--固定token令牌，相当于密码-->
		<dubbo:protocol name="dubbo" token="123456" />
	

--------------------------------
32,路由规则						|
--------------------------------
--------------------------------
33,配置规则						|
--------------------------------
--------------------------------
34,服务降级						|
--------------------------------
--------------------------------
35,优雅停机						|
--------------------------------
	# Dubbo是通过JDK的ShutdownHook来完成优雅停机的，所以如果用户使用"kill -9 PID"等强制关闭指令，是不会执行优雅停机的，只有通过"kill PID"时，才会执行。
	原理：
		服务提供方
			停止时，先标记为不接收新请求，新请求过来时直接报错，让客户端重试其它机器。
			然后，检测线程池中的线程是否正在运行，如果有，等待所有线程执行完成，除非超时，则强制关闭。
		服务消费方
			停止时，不再发起新的调用请求，所有新的调用在客户端即报错。
			然后，检测有没有请求的响应还没有返回，等待响应返回，除非超时，则强制关闭。
	
	# 设置优雅停机超时时间，缺省超时时间是10秒：(超时则强制关闭)
		
		<dubbo:application ...>
			<dubbo:parameter key="shutdown.timeout" value="60000" /> <!-- 单位毫秒 -->
		</dubbo:application>
	
	# 如果ShutdownHook不能生效，可以自行调用：
		ProtocolConfig.destroyAll();

--------------------------------
36,主机绑定						|
--------------------------------
	# 默认的主机IP查找顺序
		* 通过LocalHost.getLocalHost()获取本机地址。
		* 如果是127.*等loopback地址，则扫描各网卡，获取网卡IP。
	
	#　注册的地址如果获取不正确，比如需要注册公网地址,可以进行几个配置

		1,在 /etc/hosts中配置
			test1 205.182.23.201
		
		2,在dubbo.xml中配置主机地址
			<dubbo:protocol host="http://10.20.160.198/wiki/display/dubbo/205.182.23.201">
		
		3,在dubbo.properties中配置
			dubbo.protocol.host=205.182.23.201
	
	# 默认端口与协议
		dubbo: 20880
		rmi: 1099
		http: 80
		hessian: 80
		webservice: 80
		memcached: 11211
		redis: 6379
	
	# 修改端口
		1,在dubbo.xml中配置
			<dubbo:protocol name="dubbo" port="20880">
		
		2,在dubbo.properties中配置
			dubbo.protocol.dubbo.port=20880
		

--------------------------------
37,日志适配器					|
--------------------------------
	# 默认自动查找顺序
		log4j
		slf4j
		jcl
		jdk
	
	# 配置日志的输出策略
		
		java -Ddubbo.application.logger=log4j

		dubbo.application.logger=log4j

		<dubbo:application logger="log4j" />
	
--------------------------------
38,访问日志						|
--------------------------------
	# 记录每次的调用
	# 日志量会很大

	<!-- 把日志输出到当前应用的log4j日志 -->
	<dubbo:protocol accesslog="true" />
	
	<!--—把日志输出到指定文件,可以是本地路径—->
	<dubbo:protocol accesslog="http://10.20.160.198/wiki/display/dubbo/foo/bar.log" />

--------------------------------
39,服务容器						|
--------------------------------
	# 不建议是WEB容器,直接使用Dubbo仅仅加载Spring容器
	Spring 
		自动加载META-INF/spring目录下的所有Spring配置。
		配置：(配在java命令-D参数或者dubbo.properties中)
		dubbo.spring.config=classpath*:META-INF/spring/*.xml ----配置spring配置加载位置																*/
	
	Jetty
		启动一个内嵌Jetty，用于汇报状态。
		配置：(配在java命令-D参数或者dubbo.properties中)
		dubbo.jetty.port=8080 ----配置jetty启动端口
		dubbo.jetty.directory=/foo/bar ----配置可通过jetty直接访问的目录，用于存放静态文件
		dubbo.jetty.page=log,status,system ----配置显示的页面，缺省加载所有页面
	
	Log4j 
		自动配置log4j的配置，在多进程启动时，自动给日志文件按进程分目录。
		配置：(配在java命令-D参数或者dubbo.properties中)
		dubbo.log4j.file=/foo/bar.log ----配置日志文件路径
		dubbo.log4j.level=WARN ----配置日志级别
		dubbo.log4j.subdirectory=20880 ----配置日志子目录，用于多进程启动，避免冲突
	
	# 容器启动
		1,仅仅加载spring(默认)
			java com.alibaba.dubbo.container.Main
		
		2,通过main函数参数,指定要加载的容器
			java com.alibaba.dubbo.container.Main spring jetty log4j
		
		3,通过JVM启动参数指定
			java com.alibaba.dubbo.container.Main -Ddubbo.container=spring,jetty,log4j
		
		4,通过classpath下的dubbo.proerties加载
			dubbo.container=spring,jetty,log4j

--------------------------------
40,Reference Config缓存			|
--------------------------------
--------------------------------
41,分布式事务					|
--------------------------------
	# Dubbo未实现
