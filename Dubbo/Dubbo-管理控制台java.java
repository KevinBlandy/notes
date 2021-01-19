----------------------------
Dubbo-管理控制台			|
----------------------------
	# dubbo提供了一套在线管理整个服务的功能,管理控制台为阿里内部的阉割版
	# 开源功能包含
		路由规则
		动态配置
		服务降级
		访问控制
		权重调整
		负载均衡
		...
	
----------------------------
Dubbo-管理控制台安装		|
----------------------------
	# 管理控制台可以自己编译,也可以去下载
		dubbo-admin-2.5.3
		* 这东西本身就是个WEB程序,需要运行在容器中
	
	# 修改配置文件
		* ./WEB-INF/dubbo.properties
		dubbo.registry.address=zookeeper://192.168.250.157:2181?client=zkclient
		dubbo.admin.root.password=root
			* 配置 root 用户的密码
		dubbo.admin.guest.password=guest
			* 配置 guest 用户的密码



----------------------------
Dubbo-管理控制台常见问题	|
----------------------------		
	# 在JDK8下,启动 2.5.3 的控制台,会有一个异常.尝试更换JDK7,解决

	# 连接 Zookeeper 集群
		dubbo.registry.address=zookeeper://ip:port?backup=ip:port,ip:port

