

----------------------
protobuf-go
----------------------
	# protoc-gen-go
		* 仓库: https://github.com/protocolbuffers/protobuf-go
		* 安装: go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
	
	# 参数
		--go_out=out
			* 指定 Go 文件写入的目录。
			* 编译器会为每个输入的 .proto 文件生成一个源文件。
			* 文件的名称是通过将 .proto 扩展名替换为 .pb.go 生成的。

		--go_opt=paths=source_relative
			* 指定生成的 .pb.go 文件在输出目录中的具体位置
			
			import
				* 默认的认输出模式。
				* 输出文件将放置在一个以 Go 包的 import 路径命名的目录中（例如由 .proto 文件中的 go_package 选项提供的路径）。
				* 例如，文件 protos/buzz.proto 的 Go import 路径为 example.com/project/protos/fizz，则生成的输出文件位于 example.com/project/protos/fizz/buzz.pb.go。
			
			$PREFIX
				* 输出文件将放置在以 Go 包的导入路径命名的目录中（例如由 .proto 文件中的 go_package 选项提供的路径），但输出文件名中会去除指定的目录前缀。
				* 例如，输入文件 protos/buzz.proto 的 Go import 路径为 example.com/project/protos/fizz，且指定 example.com/project 作为模块前缀，则生成的输出文件位于 protos/fizz/buzz.pb.go。
				* 若生成的 Go 包位于模块路径之外，将引发错误。此模式适用于将生成的文件直接输出到 Go 模块中。
				
			source_relative
				* 输出文件将放置在与输入文件相同的相对目录下。例如，输入文件 protos/buzz.proto 将生成位于 protos/buzz.pb.go 的输出文件。

