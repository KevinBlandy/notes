--------------------
Dubbo-入门			|
--------------------
	# 阿里巴巴的东西,业界里面很牛逼的框架
	# 官网
		http://dubbo.io/
	# HTTP协议与RPC
		HTTP
			* 短连接,请求 -> 响应 = 完事儿
		RPC
			* 长连接,请求 -> 响应 = 连接不断开,等待下次请求继续使用(性能更叼)
	
	
	# maven 
		* 这东西依赖 spring2.5,现在都用4了
		<dependency>
		    <groupId>com.alibaba</groupId>
		    <artifactId>dubbo</artifactId>
		    <version>2.5.3</version>
		    <exclusions>
				<exclusion>
					<!-- 排除传递spring依赖 -->
					<groupId>org.springframework</groupId>
					<artifactId>spring</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	
	# spring 配置约束头
		xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
		
--------------------
Dubbo-maven主要模块	|
--------------------
	模块			二进制包			

	核心框架		dubbo-2.5.3				
	管理控制台		dubbo-admin-2.5.3.war
	简易监控中心	dubbo-monitor-simple-2.5.3-assembly.tar.gz
	简易注册中心	dubbo-registry-simple-2.5.3-assembly.tar.gz
	示例提供者		dubbo-demo-provider-2.5.3-assembly.tar.gz
	示例消费者		dubbo-demo-consumer-2.5.3-assembly.tar.gz


--------------------
Dubbo-maven构建dubbo|
--------------------
	# 源码构建
	# 如果说有必要,自己修改框架源码.就可以通过如下方法来进行重新编译和打包


	dubbo-dubbo-2.5.3.zip		//核心源码(从github获取)
	hessian-lite.zip			//编译依赖
	opensesame.zip				//编译依赖
	
	1,解压 dubbo-dubbo-2.5.3.zip 到 workspace
	2,解压 hessian-lite.zip 到 workspace
	3,解压 opensesame.zip 到 workspace
	4,Eclipse maven导入 hessian-lite 和 opensesame
	5,把这俩安装到本地仓库(install)
	6,maven 导入 dubbo-dubbo-2.5.3 源码
	/**以上步骤OK,就可以修改源码**/
	7,选择:dubbo-dubbo-2.5.3,执行maven 命令:clean package
		* 目的是清空编译,再编译打包
		* 右击 --> runas --> maven build
		* 输入 clean package
		* 记得勾选:Skip Tests,目的是跳过测试
		* run
	8,编译 package OK 后就可以获取到对应的东西
		dubbo 核心 在 workspace下 dubbo-dubbo-2.5.3 的target目录下获取到
		dubbo 管理控制台 在 workspace 下  dubbo-admin 的 target 目录下获取到
		dubbo 简易监控中心 在 workspace 下 dubbo-simple 下 dubbo-monitor-simple 下 target 目录下获取到 tar.gz 文件
		dubbo 简易注册中心 在 workspace 下 dubbo-simple 下 dubbo-registry-simple 下 target 目录下获取到 tar.gz 文件
	

--------------------
Dubbo-dubbo架构		|
--------------------
	Provider		暴露服务的服务提供方
	Consumer		调用远程服务的消费方
	Registry		服务注册,发现服务的调用中心
	Monitor			统计服务的调用次数和调用时间的监控中心
	Container		服务运行容器


--------------------
Dubbo-调用关系		|
--------------------
	1,服务容器负责启动,加载,运行服务提供者
	2,服务提供者在启动的时候,'向注册中心注册自己提供的服务'
	3,服务消费者在启动的时候,'向注册中心订阅自己所需的服务'
	4,注册中心返回'服务提供者地址列表'给消费者,如果有变更.注册中心会'以长连接的方式,把变更数据推送给消费者'
	5,服务消费者,从服务提供者地址列表中,基于'软负载均衡算法',选一台提供者进行调用,'如果调用失败,则换另一台'
	6,服务消费者和提供者,在内存中累计调用次数和调用时间,'定时每分发送一次统计数据到监控中心'



--------------------
Dubbo-spring约束	|
--------------------
	# 解决dubbo配置文件报错
	# dubbo.xsd 文件
	# Eclipse 步骤
		Windows
		Preferences
		XML
		XML Catalog
		点击 ADD
			Location	: 找到dubbo.xsd文件
			Key type	: Schema location
			Key			: http://code.alibabatech.com/schema/dubbo/dubbo.xsd
		OK

	# 配置文件中
		<beans 
			xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"  
			xsi:schemaLocation="http://code.alibabatech.com/schema/dubbo
								http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
		
		* 添加一个 xmlns
			xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
		* xsi:schemaLocation 中添加两个连接,而且顺序严格,相隔有空格
			http://code.alibabatech.com/schema/dubbo
			http://code.alibabatech.com/schema/dubbo/dubbo.xsd
		

--------------------
Dubbo-默认端口协议	|
--------------------
	dubbo: 20880
	rmi: 1099
	http: 80
	hessian: 80
	webservice: 80
	memcached: 11211
	redis: 6379

--------------------
Dubbo-标签类型		|
--------------------
	<dubbo:service/>
		* 服务提供者暴露服务配置

	<dubbo:reference/>
		* 服务消费者,引用服务配置

	<dubbo:protocol/>
		* 服务提供者协议配置

	<dubbo:registry/>
		* 注册中心配置

	<dubbo:monitor/>
		* 监控中心配置

	<dubbo:application/>
		* 应用信息配置

	<dubbo:module/>
		* 模块信息配置

	<dubbo:provider/>
		* 服务提供者缺省值配置

	<dubbo:consumer/>
		* 服务消费者缺省值配置

	<dubbo:method/>
		* 方法级别配置

	<dubbo:argument/>
		* 方法参数配置

	<dubbo:parameter/>
		* 选项参数配置

--------------------
Dubbo-姿势点		|
--------------------
★	启动时检查(消费端配置)
	* 消费端单个服务启动检查 
		<dubbo:reference check="false" />
	* 消费端所有服务启动检查
		<dubbo:consumer check="false" />
	* 关闭注册中心启动时检查
		<dubbo:registry check="false" />
		

★	集群容错(双向配置)
	* 全局配置
		<dubbo:provider cluster="failover"/>	
		<dubbo:consumer cluster="failover"/>
	* 精确单个服务
		<dubbo:service cluster="failover"/>
		<dubbo:reference cluster="failover"/>
	* 精确到方法
		<dubbo:service>
			<dubbo:method name="getUser" retries="2"></dubbo:method>
		</dubbo:service>
		<dubbo:reference>
			<dubbo:method name="getUser" retries="2"></dubbo:method>
		</dubbo:reference>
	* 容错策略
		failover	:(默认),重试机制,通过:retries 属性来控制重试次数
		failfast	:不重试,调用失败,直接抛出异常
		failsafe	:无视调用失败,不抛出异常
		failback	:失败自动恢复，后台记录失败请求，定时重发。
		forking		:并行调用多个服务器，只要一个成功即返回。通过:forks 属性来控制最大并行数
		broadcast	:广播调用,逐渐调用所有的服务提供者,只要有一个报错.就报错

★	负载均衡(双向配置)
	* 全局配置
		<dubbo:provider loadbalance="random" weight="5"/>
		<dubbo:consumer loadbalance="random"/>
	* 精确到单个服务
		<dubbo:service loadbalance="random" weight="5"/>
		<dubbo:reference loadbalance="random"/>
	* 精确到方法
		<dubbo:service >
			<dubbo:method loadbalance="random" weight="5"/>
		</dubbo:service>
		<dubbo:reference>
			<dubbo:method loadbalance="random"/>
		</dubbo:reference>
	* 负责均衡策略
		random			:(默认)随机策略,可以通过:weight 属性来控制权重,值越大,权重越高
		roundRobin		:轮询,挨着来
		leastActive		:最小活跃调用数
		consistentHash	:一致性hash
	* dubbo有提供负载均衡策略接口,可以自己实现算法

★	线程模型(服务端配置)
	* 可配置标签
		<dubbo:protocol dispather="" threadpool="" threads=""/>
		<dubbo:provider dispather="" threadpool="" threads=""/>
	* 线程模式,通过 dispatcher 属性控制,可选属性有
		all			:(默认),所有消息都派发到线程池去完成.
		direct		:所有消息都不给线程池,当前IO线程完成
		message		:只有请求响应消息派发到线程池，其它连接断开事件，心跳等消息，直接在IO线程上执行。
		execution	:只请求消息派发到线程池，不含响应，响应和其它连接断开事件，心跳等消息，直接在IO线程上执行。
		connection	:在IO线程上，将连接断开事件放入队列，有序逐个执行，其它消息派发到线程池。
	* 线程池类型,通过:threadpool 属性控制,可选属性有
		fixed		:固定大小线程池，启动时建立线程，不关闭，一直持有。(缺省)
		cached		:缓存线程池，空闲一分钟自动删除，需要时重建。
		limited		:可伸缩线程池，但池中的线程数只会增长不会收缩。(为避免收缩时突然来了大流量引起的性能问题)。
	* 线程池,线程数量,通过:threads 属性指定

★	直连提供者(消费者配置)
	* <dubbo:reference id="xxxService" interface="com.alibaba.xxx.XxxService" url="dubbo://localhost:20890" />

★	只订阅(注册中心相关)
	* 仅仅订阅这个注册中心上的服务,而不把本身应用的服务注册到该注册中心
		<dubbo:registry address="10.20.153.10:9090" register="false"/>
		<dubbo:registry address="10.20.153.10:9090?register=false" />

★	只注册(注册中心相关)
	* 仅仅把本身应用的服务注册到这个注册中心,而不通过这个注册中心获取服务
		<dubbo:registry id="qdRegistry" address="10.20.141.150:9090" subscribe="false" />
		<dubbo:registry id="qdRegistry" address="10.20.141.150:9090?subscribe=false" />

★	静态服务

★	多协议(双向配置)
	* 声明出多个协议
		<dubbo:protocol name="dubbo" port="20880" />
		<dubbo:protocol name="rmi" port="1099" />
	* 使用指定协议暴露服务
		<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="demoService" protocol="dubbo" />
		<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="demoService" protocol="rmi" />
	* 使用多个协议暴露服务
		<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="demoService" protocol="dubbo,hessian" />
	* 仅仅调用指定协议的服务
		<dubbo:reference protocol="dubbo"/>		
	
★	多注册中心(双向配置)
	* 声明出多个注册中心(消费端,服务端都需要声明)
		<dubbo:registry id="registry1" address="10.20.141.150:9090" />
		<dubbo:registry id="registry2" address="10.20.141.151:9010" default="false" />
	* 同一个服务,注册到多个注册中心
		<dubbo:service registry="registry1,registry2" />
	* 不同服务,注册到不同注册中心
		<dubbo:service interface="com.alibaba.hello.api.HelloService" registry="registry1" />
		<dubbo:service interface="com.alibaba.hello.api.DemoService" registry="registry2" />
	* 消费端使用,从指定的注册中心获取服务
		<dubbo:reference id="chinaHelloService" registry="registry1" />
		<dubbo:reference id="intlHelloService" registry="registry2" />

★	服务分组(双向配置)
	* 服务提供端,一个服务有N个实现
		<dubbo:service group="feedback" interface="com.xxx.IndexService" />
		<dubbo:service group="member"   interface="com.xxx.IndexService" />
	* 服务消费端,消费时指定 group
		<dubbo:reference group="feedback" interface="com.xxx.IndexService" />
		<dubbo:reference group="member"   interface="com.xxx.IndexService" />
	* 任意调用一个,随便成功一个就好
		<dubbo:reference group="*" interface="com.foo.BarService"  />
	
★	多版本(双向配置)
	* 服务提供端
		<dubbo:service interface="com.foo.BarService" version="1.0.0" />
		<dubbo:service interface="com.foo.BarService" version="2.0.0" />
	* 消费端消费
		<dubbo:reference id="barService" interface="com.foo.BarService" version="1.0.0" />
		<dubbo:reference id="barService" interface="com.foo.BarService" version="2.0.0" />
	* 不区分版本,随便调用一个
		<dubbo:reference id="barService" interface="com.foo.BarService" version="*" />

	分组聚合
	参数验证
	结果缓存
	泛化引用
	泛化实现
	回声测试
	上下文信息
	隐式传参
	异步调用
	本地调用
★	参数回调
	* 单独笔记

★	事件通知
	* 单独笔记

	本地存根
	本地伪装
★	延迟暴露(服务端配置)
	* 全局配置
		<dubbo:provider delay="-1"/>
	* 指定服务
		<dubbo:service delay="5000" />
	* delay 值(毫秒)如果是 -1,则表示 延迟到Spring初始化完成后，再暴露服务：(基于Spring的ContextRefreshedEvent事件触发暴露)

★	并发控制(双向配置)
	* 限制服务提供端并发执行数,通过 executes 属性控制
		* 全局配置
			<dubbo:provider executes="1000"/>
		* 控制到指定服务
			<dubbo:service executes="10" />
		* 控制到方法
			<dubbo:service interface="com.foo.BarService">
				<dubbo:method name="sayHello" executes="10" />
			</dubbo:service>
		
	* 限制服务提供端,'每个客户端'的最大并发执行数(占用连接的请求数),通过 actives 属性控制
		* 全局配置
			<dubbo:provider actives=""/>
			<dubbo:consumer actives=""/>
		* 控制到指定服务
			<dubbo:service interface="com.foo.BarService" actives="10" />
			<dubbo:reference interface="com.foo.BarService" actives="10"/>
		* 控制到指定方法
			<dubbo:service interface="com.foo.BarService">
				<dubbo:method name="sayHello" actives="10" />
			</dubbo:service>
			<dubbo:reference interface="com.foo.BarService">
				<dubbo:method name="sayHello" actives="10" />
			</dubbo:service>
		
★	连接控制(双向配置)
	* 限制服务提供端的连接最大数
		<dubbo:provider protocol="dubbo" accepts="10" />
		<dubbo:protocol name="dubbo" accepts="10" />
	* 制客户端服务使用连接连接数：(如果是长连接，比如Dubbo协议，connections表示该服务对每个提供者建立的长连接数)
		<dubbo:reference interface="com.foo.BarService" connections="10" />
		<dubbo:service interface="com.foo.BarService" connections="10" />

	延迟连接
	* 延迟连接，用于减少长连接数，当有调用发起时，再创建长连接。仅仅对dubbo协议有效
		<dubbo:protocol name="dubbo" lazy="true" />

	粘滞连接
	* 粘滞连接用于有状态服务，尽可能让客户端总是向同一提供者发起调用，除非该提供者挂了，再连另一台。
	* '粘滞连接将自动开启延迟连接，以减少长连接数'
		<dubbo:protocol name="dubbo" sticky="true" />

★	令牌验证(服务端/注册中心配置)
	* token 属性为true的话,会使用UUID来生成token
	* 全局配置
		<dubbo:provider interface="com.foo.BarService" token="true" />
		<dubbo:provider interface="com.foo.BarService" token="123456" />
	* 服务级别
		<dubbo:service interface="com.foo.BarService" token="true" />
		<dubbo:service interface="com.foo.BarService" token="123456" />
	* 协议级别
		<dubbo:protocol name="dubbo" token="true" />
		<dubbo:protocol name="dubbo" token="123456" />
	
	路由规则
	配置规则
	服务降级
	优雅停机

★	主机绑定(服务端配置)
	* 注册的地址如果获取不正确，比如需要注册公网地址
	* 可以在/etc/hosts中加入：机器名 公网IP
		test1 205.182.23.201
	* 在dubbo.xml中加入主机地址的配置
		dubbo:protocol host="http://10.20.160.198/wiki/display/dubbo/205.182.23.201">
	* 在dubbo.properties中加入主机地址的配置
		dubbo.protocol.host=205.182.23.201

★	日志适配(双向配置)
	* 默认自动查找顺序:log4j slf4j jcl jdk
	* 配置输出策略
		* 启动参数
			java -Ddubbo.application.logger=log4j
		* properties文件
			dubbo.application.logger=log4j
		* 全局
			<dubbo:application logger="log4j" />
		

★	访问日志
	* 每次访问都记录日志,日志量异常大.
	* 把访问日志输出到当前的log4日志
		<dubbo:protocol accesslog="true" />
	* 把访问日志输出到指定的文件
		<dubbo:protocol accesslog="http://10.20.160.198/wiki/display/dubbo/foo/bar.log" />
	* 全局配置
		<dubbo:provider accesslog="true"/>
	* 精确到服务
		<dubbo:service interface="" accesslog=""/>
		
★	服务容器
	* 单独笔记

	Reference Config缓存
	分布式事务