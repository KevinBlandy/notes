---------------------------------
包
---------------------------------	
	
	# 通过 import 导入其他包中定义的消息

		// foo.proto
		edition = "2023";
		message Foo {
		  int64  id = 1;
		}

		// demo.proto
		import "foo.proto";

		message Member {
		  int64 id = 1;
		  Foo foo = 2;
		}
	
	# 传递依赖
		
		// bar.foo
		edition = "2023";
		// 传递导出 "foo.proto" 模块
		import public "foo.proto";

		* 通过 public 关键字实现传递导出
		* 当其他模块导入 bar.foo 时，会自动传递依赖 import public 下的模块，非 public 模块的下的内容不能访问。
		* 注意，这在 Java、Kotlin、TypeScript、JavaScript、GCL 以及使用 protobuf 静态反射的 C++ 目标中不可用。

	
	# 模块的查找路径
		* 一般来说，编译会从 -I/--proto_path 参数指定的一组目录中搜索模块。
		* 不指定的话，默认就在当前运行目录下查找。
		* 一般来说，建议把 --proto_path 设置为项目根目录，并为所有模块文件使用完全限定的名称。

