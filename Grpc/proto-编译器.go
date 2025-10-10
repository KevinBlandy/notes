------------------
proto
------------------

	# 编译器下载（不同系统）

		https://github.com/protocolbuffers/protobuf/releases

		protoc-31.1-win64.zip
		protoc-31.1-linux-x86_64.zip
	
	# 添加到 PATH

		// 查看版本号
		protoc --version
	

	# 语法
		
		protoc [OPTION] PROTO_FILES
		
		--proto_path
			* 指定 proto 文件的根目录，可能会影响到生成的文件的目录
			* propto 文件可能在多级嵌套目录下，可以通过这个属性来指定到底根目录从哪里算？
			* 因为 paths=source_relative 选项，会保留输入文件（propto）相对于 proto_path 的路径结构。



		

		 