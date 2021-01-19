-------------------------
JDK的工具				 |
-------------------------
	# 在JDK的bin目录下
		
		jps
			* JVM Process Status Tool
			* 显示系统中所有 HotSpot 虚拟机进程

		jstat
			* JVM Statistics Tool
			* 收集 HotSpot 虚拟机各方面的运行数据

		jinfo
			 * Configuration Info For Java
			 * 显示虚拟机的配置信息

		jmap
			* Memory Map For Java
			* 生成虚拟机的内存转储快照文件(heapdump)

		jhat
			* JVM Heap Dump Browser
			* 用于分析 heapdump 文件,它会建立一个 HTTP/HTML 服务器, 可以直接在浏览器上查看分析结果

		jstack
			* Stack Trace for Java
			* 显示虚拟机的线程快照


		* 这些exe主要的功能都是在 tools.jar 中实现的
		* 在 Linux 环境下, 还有直接通过 shell 脚本实现的
	
