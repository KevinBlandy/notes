--------------------
jps					|
--------------------
	# JVM Process Status Tool
		* 显示系统中所有 HotSpot 虚拟机进程
	
	# 命令格式
		jps [options] [hostid]

		* hostid, 表示RMI注册表中注册的主机名(可以通过RMI协议查询远程JVM的进程状态)

	# 常用参数
		-q
			* 只输出 LVMID, 省略 Main 类名称
		-m
			* 输出JVM启动的时候, 传递给 main 函数的参数
		-l
			* 输出主类的全名, 如果是 jar 包, 则输出 jar 路径
		-v
			* 输出虚拟机启动时的JVM参数
	

		