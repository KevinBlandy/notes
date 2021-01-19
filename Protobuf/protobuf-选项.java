-----------------
全局选项
-----------------
	syntax
		* 定义版本号, 必须在第一行
		* 如果不指定, 默认使用版本: 2.x
			syntax = "proto3";
		
	package
		* 定义 proto文件的命名空间
			package foo.bar;
	
-----------------
全局option 选项
-----------------
	java_package
		* 生成的 Java 类拥有的包名
		* 如果没有在.proto 文件中显示地声明 java_package 选项, 默认采用 proto 的包名(在.proto 文件中用 package 关键字定义)作为生成的 Java 类的包名
		* 然而, 通常情况下 proto 包名不是好的 Java 格式的包名, proto 包名一般不会是以 Java 包名所期望的反向域名的格式命名
			option java_package="com.foo";
	
	java_multiple_files
		* 在包级别定义顶级消息, 枚举服务, 生成多个类
			option java_multiple_files = true;

	
	java_outer_classname
		* 这个选项定义了你想要编译生成的 Java 输出类的最外层类名(也是文件名)
		* 如果没有在.proto文件中定义 java_outer_classname 选项, 默认将.proto 文件名转换为驼峰格式的文件名(例如 foo_bar.proto 编译生成 FooBar.java)
	
	optimize_for (该选项影响 C++和 Java 的代码生成)
		* 枚举值:
			SPEED (默认值)
				* protoc 编译器会根据你定义的消息类型生成序列化, 解析和执行其它常规操作的代码, 这些代码是高度优化了的

			CODE_SIZE
				* protoc 编译器会采用共享, 反射技术来实现序列化, 解析和其它操作的代码, 以生成最小的类
				* 因此, 所生成的代码比采用选项 SPEED 的要小得多, 但是操作性能降低了
				* 类的公共成员函数依然是一样的(用 SPEED 优化选项也是如此)
				* 这种模式是最有用的应用情况是: 程序包含很多.proto 文件, 但并不追求所有模块都有极快的速度


			LITE_RUNTIME
				* protoc 编译器生成的类, 仅依赖"lite"运行时库 (libprotobuf-lite , 而不是 libprotobuf)
				* "lite"运行时库比完整库 (约一个数量级小) 小得多, 但省略了某些功能, 如描述符和反射
				* 这对于运行在像手机这样的有空间限制的平台上的应用程序特别有用
				* protoc 编译器仍将为所有方法生成最快速的代码, 这和在 SPEED 模式一样
				* 生成的类将为每一种语言实现 MessageLite 版本的接口, 只提供了完整的 Message 接口的一个子集的实现
			
			option optimize_for = SPEED;
	

-----------------
字段选项
-----------------	
	packed (字段)
		* 个选项 如果被设置为 true ,对于基本数值类型的可重复字段可以获得更紧凑的编码
		* 使用此选项,没有负面影响
		* 然而,在 2.3.0 版本之前是用此选项解析器会忽略被打包的数据。因此,更改现有字段为 packed，会破坏数据传输的兼容性
		* 对于 2.3.0 及以后的版本, 这种改变是安全的并且解析器能够同时接受这两种格式的打包字段的数据, 但要小心, 如果要处理旧的程序使用了旧版本的 protobuf
			repeated int32 samples = 4 [packed=true];


	deprecated (字段)
		* 这个选项如果设置为 true , 表示该字段已被废弃, 不应该在后续的代码中使用它
		* 在大多数语言中这没有任何实际的影响, 在 Java 中，它会变@Deprecated 注释
		* 将来, 其他特定于语言的代码生成器在为被标注为 deprecated 的字段生成操作函数的时候, 编译器在尝试使用该字段的代码时发出警告
		* 如果不希望将来有人使用使用这个字段, 考虑用 reserved 关键字 声明该字段
			int32 old_field = 6 [deprecated=true];
	


-----------------
自定义选项
-----------------
	



