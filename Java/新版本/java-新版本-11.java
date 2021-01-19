
----------------------------
java11						|
----------------------------
	# 简化启动单个源代码文件的方法
		* 如果名为 HelloWorld.java 的文件包含一个名为 hello.World 的类, 那么:
			java HelloWorld.java
		
		* 相当于
			javac HelloWorld.java
			java -cp . hello.World
	
	# 用于 Lambda 参数的局部变量语法
		* Lambda 表达式中使用局部变量类型推断是 Java 11 引入的唯一与语言相关的特性
		* 11 以后, lambda 表达式形参可以可以使用  var 关键字了

		* 唯一的作用就是, 可以在 var 形参上添加注解

		Function<Integer, Integer> rerval = (@Nullable var x) -> x == null ? 0 : x * x;
	

	# 新增ZGC收集器
	
	# 飞行记录器
		* 启用飞行记录器参数如下
			-XX:StartFlightRecording
	
	# 新增api
		Collection
			<T> T[] toArray(IntFunction<T[]> generator)

			* 集合类, 可以使用这个方法, 来自定义转换逻辑
