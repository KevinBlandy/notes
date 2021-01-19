
		 
			

------------------
代码生成
------------------

	protoc --proto_path=IMPORT_PATH --cpp_out=DST_DIR --java_out=DST_DIR --python_out=DST_DIR --go_out=DST_DIR --ruby_out=DST_DIR --objc_out=DST_DIR --csharp_out=DST_DIR [path/to/file.proto]

	--proto_path
		* 指定protobuf的目录
		* 如果省略, 则使用当前目录, 通过多次传递参数 --proto_path, 可以实现在多个导入目录中按顺序查找

	--java_out
		* 输出Java类的目录, 可以提供一个或多个输出指令, 同时生成N多个语言的代码
		* 输出的目录必须是已经存在的
		* 如果目标文件是 .zip 结尾会自动压缩打包
		* 如果目标文件是 .jar 结尾还会自动根据规范打包为jar包
		* 如果目标文件已经存在, 会自动的删除

	
	path/to/file.proto
		* 指定proto文件
		* 可以提供一个或多个.proto文件作为输入. 多个.proto文件可以一次指定.
	
	