------------------------
jinfo					|
------------------------
	# Configuration Info For Java
	# 显示虚拟机的配置信息
		* 实时的查看和调整JVM的参数
	
	# 语法
		jinfo [options] pid
		
		* 如果不添加参数(options), 则打印出该进程相关的所有信息(JVM参数, System.properties 参数)

	# 参数
		-flag [name]
			* 查看指定jvm选项的值
			* 例: 查看 -XX:CICompilerCount 的值
				jinfo -flag CICompilerCount 3900
		
		-flags
			* 查看指定JVM选项的所有值(如果没设置, 就是默认值了)
		
		-sysprops
			* 查看 System.properties 的数据
			* 返回 key=value
	
	# 动态的启动/禁用某些JVM参数
		jinfo -flag [+/-] [name]
	
	# 动态的修改JVM参数
		jinfo -flag [name]=[value]

	
		
