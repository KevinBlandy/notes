------------------------
Class					|
------------------------
	# 类对象
	
	# 静态方法
		Class<?> forName(String className)
		Class<?> forName(String name, boolean initialize, ClassLoader loader)
			* 加载类
				className/name 类名称
				initialize 是否要初始化, 默认 true
				loader 使用自定义的类加载器执行
			
			
		