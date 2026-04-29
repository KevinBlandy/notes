

----------------------
protobuf-go
----------------------
	# protoc-gen-go
		* 仓库: https://github.com/protocolbuffers/protobuf-go
		* 安装: go install google.golang.org/protobuf/cmd/protoc-gen-go@latest

		protoc --proto_path=proto --go_out. --go_opt=paths=source_relative *.proto
		
	# 要生成 Go 代码，必须要在 proto 中提供 Go 包的导入路径，有两个方式

		* 在调用编译器时，传递一个或多个参数，在命令行中指定 Go 的导入路径

					// 参数
					M${PROTO_FILE}=${GO_IMPORT_PATH}
					// 示例
					protoc --proto_path=src \
						--go_opt=Mprotos/buzz.proto=example.com/project/protos/fizz \
						--go_opt=Mprotos/bar.proto=example.com/project/protos/foo \
						protos/buzz.proto protos/bar.proto
		
		* 在 .proto 文件内部进行声明（推荐）

			option go_package = "github.com/foo/bar;bar";

				* ';' 前面部分是其他 go 模块 import 当前定义时的 Go 代码导入路径。
				* ';' 后面部分是 go 当前定义的 package 名称，如果不提供这部分，编译器会根据 “导入路径” 的最后一个部分自动推断（例如路径是 .../api/user，则包名默认为 user）。

	# 参数
		--go_out=out
			* 指定 Go 文件写入的目录，需要先创建，程序不会自己创建
			* 编译器会为每个输入的 .proto 文件生成一个源文件。
			* 文件的名称是通过将 .proto 扩展名替换为 .pb.go 生成的。

		--go_opt=paths=source_relative
			* 指定生成的 .pb.go 文件在输出目录中的具体位置
				import
					* 默认的认输出模式。最终路径：--go_out + go_package 的路径部分。
						// proto 代码
						option go_package = "./api/foo/bar;zoo";
						// 命令行
						--go_out=gen --go_opt=paths=import


						// 最终文件路径
						gen/api/foo/bar
						// 文件 package
						package zoo;
				
				$PREFIX
					* 同样以 option go_package 来组织目录，但输出文件名中会去除指定的目录前缀。
					* 例如，输入文件 protos/buzz.proto 的 go_package 为 example.com/project/protos/fizz 且指定 example.com/project 作为模块前缀，则生成的输出文件位于 protos/fizz/buzz.pb.go。
					* 若超出模块路径将报错。

				source_relative
					* 最终路径：--go_out + .proto 文件相对于 --proto_path 的路径。
					* 编译器会无视 go_package 里的路径部分（包名称部分仍然生效），只看 .proto 文件本身在哪里。



	

