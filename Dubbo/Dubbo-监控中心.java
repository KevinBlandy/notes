----------------------------
Dubbo-监控					|
----------------------------
	# 监控中心的作用
		* 服务消费者和提供者,在内存中统计调用次数.定时每分钟发送一次统计数据到监控中心
		* 为服务的运维提供数据
		
	# 监控中心是可选的(非必须)
		
	# 监控中心可以自定义开发
		

----------------------------
Dubbo-简易监控中心安装		|
----------------------------
	# dubbo-monitor-simple-2.5.3-assembly.tar
		* 可以自己构建编译出来,也可以从网上下载
	
	# 这仅仅是个可运行程序
		bin			//脚本目录
		conf		//配置文件
		lib			//运行依赖
	
	# 修改配置文件
		dubbo.container=log4j,spring,registry,jetty
		dubbo.application.name=simple-monitor
		dubbo.application.owner=
		dubbo.registry.address=zookeeper://192.168.250.157:2181?client=zkClient
			* 修改为zookeeper地址,并且通过?设置参数,client=zkClient
		dubbo.protocol.port=7070
		dubbo.jetty.port=8080
			* web控制台端口
		dubbo.jetty.directory=${user.home}/monitor
		dubbo.charts.directory=${dubbo.jetty.directory}/charts
		dubbo.statistics.directory=${user.home}/monitor/statistics
		dubbo.log4j.file=logs/dubbo-monitor-simple.log
		dubbo.log4j.level=WARN
	
	# 在dubbo中配置监控
		<!-- 监控服务 ,在注册中心自动查找监控服务-->
		<dubbo:monitor protocol="registry"/>
		* 配置在消费提供方
	
	# 启动
		* bin目录下,根据操作系统操作不同的脚本
	
	# 进入管理界面
		http://ip:8080