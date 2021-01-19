---------------------
jhat				 |
---------------------
	# JVM Heap Dump Browser
	
	# 用于分析 heapdump 文件,它会建立一个 HTTP/HTML 服务器, 可以直接在浏览器上查看分析结果
		* 除非真的是没工具查看 dump 文件的解析结果了, 否则不建议使用它
		* 因为一般不会在部署应用的服务器上去解析dump文件, 因为解析工作非常的消费硬件资源, 耗时
		* 它的分析功能也比较的简陋, 一般不会用
	
	# 建议使用的分析器
		Eclipse Memory Analyzer
		IMB HeapAnalyzer
	
	# 命令
		jhat [options] [dump]

		dump: 指定dump文件
	
		* 默认在 7000 端口提供 http 服务
			Reading from heapdump...
			Dump file created Mon May 27 13:39:33 GMT+08:00 2019
			Snapshot read, resolving...
			Resolving 279684 objects...
			Chasing references, expect 55 dots.......................................................
			Eliminating duplicate references.......................................................
			Snapshot resolved.
			Started HTTP server on port 7000
			Server is ready.
	
	# 参数
		-port
			* 指定端口号
	
