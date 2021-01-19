-----------------------
arthas				   |
-----------------------
	#  阿里开源的java检测工具
	# 网站
		https://github.com/alibaba/arthas
		https://alibaba.github.io/arthas/

	# 安装
		wget https://alibaba.github.io/arthas/arthas-boot.jar
		
		* 下载该java包就行了
		* 就是用这java库去执行一些指令, 来监控java进程


	# 命名格式
		arthas-boot [-h] [--target-ip <value>] [--telnet-port <value>]
		   [--http-port <value>] [--session-timeout <value>] [--arthas-home <value>]
		   [--use-version <value>] [--repo-mirror <value>] [--versions] [--use-http]
		   [--attach-only] [-c <value>] [-f <value>] [--height <value>] [--width
		   <value>] [-v] [pid]
		
	# 启动监控的方式
		1, 直接执行 java -jar arthas-boot.jar
			* 会列出来所有的java进程
			* 输入进程的序号, 开始检测指定的Java程序
		
		2, 启动时直接指定进程的pid
			* java -jar arthas-boot.jar [pid]


-----------------------
 命令参数			  |
-----------------------
	-h
		* 打印帮助信息