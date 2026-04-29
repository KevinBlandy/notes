------------------
proto
------------------
	# 编译器下载（不同系统）

		https://github.com/protocolbuffers/protobuf/releases

		protoc-31.1-win64.zip
		protoc-31.1-linux-x86_64.zip

		* Protobuf 的工作流程是 “编译器 + 语言插件” 的组合，protoc 是用 C++ 编写的二进制程序，不分语言。
		* protoc 执行的时候会根据参数，在本地 PATH 中寻找对应的插件来执行生成。
		
	# 添加到 PATH

		// 查看版本号
		protoc --version


	# 编译器用法
		
		* 语法
			protoc [OPTION] PROTO_FILES

		* OPTION 选项
		
			-I, --proto_path
				* 指定搜索 proto 文件的根目录，可能会影响到生成的文件的目录
				* propto 文件可能在多级嵌套目录下，可以通过这个属性来指定到底根目录从哪里算？

				* 可以多次指定，系统将按顺序搜索这些目录。如果未指定，则使用当前工作目录。
				* 如果在这些目录中均未找到，将检查 --descriptor_set_in 描述符以查找所需的 proto 文件。

			--version
			-h, --help
			--encode=MESSAGE_TYPE
				* 编码

			--decode=MESSAGE_TYPE
				* 解码
	
			--decode_raw
				* 无 proto 强行解析

			--descriptor_set_in=FILES
				* 从 descriptor 加载 proto，而不是源码

			-oFILE,--descriptor_set_out=FILE
				* 生成 描述文件（FileDescriptorSet）

			--include_imports
				* descriptor 包含依赖

			--include_source_info
				* 保留源码信息（注释、行号）

			--retain_options
				* 保留 option 字段（一般编译时会丢）

			--dependency_out=FILE
				* 输出依赖关系（给 make 用）

			--error_format=FORMAT
				* 控制错误格式（IDE 用）
	
			--fatal_warnings
				* 警告变错误（CI 必备）
	
			--print_free_field_numbers
				* 查看 message 还能用哪些字段号
		
			--enable_codegen_trace
				* 追踪 protoc 生成代码的过程
	
			--direct_dependencies
				* 限制 import 白名单
	
			--option_dependencies
			--notices
			--plugin=EXECUTABLE

			--go_out=OUT_DIR
			--cpp_out=OUT_DIR
			--csharp_out=OUT_DIR
			--java_out=OUT_DIR
			--kotlin_out=OUT_DIR
			--objc_out=OUT_DIR
			--php_out=OUT_DIR
			--pyi_out=OUT_DIR
			--python_out=OUT_DIR
			--rbs_out=OUT_DIR
			--ruby_out=OUT_DIR
			--rust_out=OUT_DIR
				* 调用不同语言插件，生成对应语言的代码


		* PROTO_FILES
			* 要编译的 .proto 文件，可以直接指定文件名称。
			* 也可以使用 @ 来表示文件读取参数（批量构建用）。

				protoc @args.txt

				* 注意，文件内容不能使用引号、通配符、转义序列、命令等。
				* 每行对应一个参数，即使该行包含空格。
		
		
		
		

		
