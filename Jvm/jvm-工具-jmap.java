--------------------
jmap				|
--------------------
	# Memory Map For Java
	# 生成虚拟机的内存转储快照文件(heapdump)
	
	# 生成Java的堆转储快照, 可以通过一些手段
		* 使用:-XX:+HeapDumpOnOutOfMemoryError 在内存溢出的时候, 生成内存快照
		* 在Linux下使用: kill -3 命令'吓唬JVM', 也能拿到快照
	
	# 命令格式
		jmap [options] vmid

	
	# 参数
		-dump
			* 生成Java堆转储快照
			* 该参数有几个子参数, 在'-dump:'后添加, 多子参数, 使用逗号分隔
				-dump:live,format=b,file=D:\\heapdump

				live	该参数表示,仅仅dump出存活的对象
				format	指定格式化??
				file	指定文件的路径

			* 生成快照示例
				jmap -dump:format=b,file=C:\Users\KevinBlandy\Desktop\heapdump 3900
			
			* 如果快照文件已经存在, 则会创建失败
		
		-finalizerinfo
			* 显示在:F-Queue 中等待 Finalier 线程执行 finalize 方法的对象
		
		-heap
			* 显示Java堆详细信息, 如:使用的回器, 参数配置, 分代状态
		
		-histo
			* 显示堆中的对象统计信息, 包括类, 实例数量, 合计容量
			* 该参数有一个子参数
				live	仅仅统计存活的对象
		
		-clstats
			* 以 ClassLoader 为统计口径, 显示内存状态
		
		-F
			* 当JVM进程对 -dump 选项没有响应的时候, 可以使用该选项强制生成 dump 快照
			* 只在 Linux/Solaris平台有效


