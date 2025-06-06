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

	
	# 通过 package 来定义命名空间
		* 避免不同模块中的 message 名称冲突

			// bar.proto
			package proto.bar;
			message Member {
			int64 id = 1;
			}

			// 导入 bar 模块
			import "bar.proto";

			message Member {
			  int64  id = 1;
			  // 通过 bar 模块中的 package 来导航其中的 message
			  proto.bar.Member member = 2;
			}
		
		* package 采用点分割
		
		* package 的定义，对不同的语言有不同的影响
			
			Java
				* 除非在 .proto 文件中明确提供 java_package 选项，否则该包将作为 Java 包使用。
			
			Go
				* package 指令被忽略，生成的 .pb.go 文件位于以相应 go_proto_library Bazel 规则命名的包中。
				* 对于开源项目，必须提供 go_package 选项或设置 Bazel -M 标志。