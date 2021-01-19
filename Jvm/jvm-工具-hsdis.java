--------------------
hsdis				|
--------------------
	# Sun 官方推荐的 HotSpot 虚拟机 JIT 编译代码的反汇编插件
		* 它包含在HotSpot的源码中, 但是没有提供编译后的恒旭
		* 它的作用是让 HotSpot 的 -XX:PrintAssembly 指令调用它把动态生成的本地代码还原文件汇编代码输出
		* 还提供了大量有价值的注释
	
	# 需要根据自己的操作系统,CPU类型,从 Project Kenai 网站上下载插件
		* 下载好,复制到目录:
			JDK_HOME/jre/bin/client
			JDK_HOME/jre/bin/server 
		
		* 如果找不到的话, 需要自己根据源代码编译一下
	
		
	
