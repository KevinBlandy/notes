----------------------------
注册中心					|
----------------------------
	# 各个注册中心节点的名称要修改
		* 尽量保证集群节点中所有成员的instance.name不一样
		* 命名方式可以带上端口等信息
		* 可以通过本地host文件来做映射

		eureka.instance.name=localhost10086.com
		eureka.instance.name=localhost10087.com
		eureka.instance.name=localhost10088.com

	# 允许注册与发现,默认值就是true,其实不用手动声明
		fetch-registry: true
		register-with-eureka: true
	
	# 注册地址的修改
		* 在 defaultZone 添加上当前节点以外的所有节点成员的注册地址

		eureka.client.service-url.defaultZone=http://localhost10086.com:10086/eureka,http://localhost10088.com:10088/eureka
			* 当前节点为:10087,所以只需要添加 86 和 88 节点

	
	# 配置详情
		server:
		 port: 10088

		spring:
		 application:
		  # 名称为注册中心
		  name: register

		eureka:
		 instance:
		  # 当前节点的主机名
		  hostname: register3
		  # 当前节点的id(在控制台中的名称)
		  instance-id: ${eureka.instance.hostname}:${server.port} 
		 client:
		  service-url:
		   # 其他的注册中心地址
		   defaultZone: http://register1:10086/eureka/,http://register2:10087/eureka/
		
		* 重要的一点就是,每个节点的id不能相同,否则会出现只有一个节点能用的情况
	 
	 # 服务提供者与服务消费者
		* 它们仅仅需要设置集群中的任意节点为defaultZone即可
			service-url:
			 defaultZone: http://register3:10088/eureka/
		
		* 也可以主动的链接所有注册中心集群节点
			service-url:
			 defaultZone: http://register3:10088/eureka/,http://register2:10087/eureka/,http://register1:10086/eureka/
		
		* 注册中心集群中的每个节点,会相互的同步数据