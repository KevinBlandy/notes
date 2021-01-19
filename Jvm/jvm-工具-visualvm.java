---------------------
visualvm			 |
---------------------
	# 它是在 1.6Update中首次发布, 现在是Sun(Oracle)主力推动的多合一故障处理工具
	# 已经从JDK分离出来, 成为独立的项目
		https://visualvm.github.io/
	
	# 它对应用程序的实际性能影响很小, 所以可以直接应用在生产环境中


---------------------
开启 jstatd 远程监控 |
---------------------
	# 在服务端JDK的bin目录下新建  jstatd.all.policy 文件
		* 其实目录可以自己选择
		* 输入代码用于启动JSTATD:

	　　grant codebase "file:${java.home}/../lib/tools.jar" {
	　　　　permission java.security.AllPermission;
	　　};

	# 服务端启动 jstatd 
		jstatd -J-Djava.security.policy=jstatd.all.policy -p 1024 &

		jstatd -J-Djava.security.policy=jstatd.all.policy -J-Djava.rmi.server.hostname=172.26.13.178 -J-Djava.rmi.server.logCalls=true -p 1024 &
		
		-J-Djava.security.policy=jstatd.all.policy 
			* 指定路径
		-J-Djava.rmi.server.logCalls=true
			* 打印日志
		-J-Djava.rmi.server.hostname=192.168.19.114
			* 指定主机名，主机名应该和用 hostname -i 命令执行出来的结果一致


	# 选项
		-nr
			* 当没有找到现有的RMI注册表时, 不会尝试在jstatd进程中创建内部RMI注册表

		-p [port]	
			* 希望找到RMI注册表的端口号, 或者如果未找到, 则在未指定-nr的情况下创建RMI注册表

		-n [rminame]
			* 远程RMI对象在RMI注册表中绑定的名称
			* 默认名称是JStatRemoteHost
			* 如果多个jstatd服务器在同一主机上启动,通过指定该选项,可以使每个服务器导出的RMI对象的名称唯一, 但是,这样做将需要保证在监视客户端的hostid和vmid中存在唯一的服务器名称

		-J [option]
			* 将选项传递给java程序
			* 例如:-J-Xms48m将启动内存设置为48兆字节, -J通过是一个通用的约定执行用Java编写的应用程序的底层VM选项

