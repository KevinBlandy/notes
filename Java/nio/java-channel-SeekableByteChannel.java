---------------------------------
SeekableByteChannel				 |
---------------------------------
	# 具有指针的文件通道,是一个接口
	# SeekableByteChannel 是 FileChannel 的父类
	# SeekableByteChannel
	# 实例获取方式
		SeekableByteChannel newByteChannel(Path path, OpenOption... options);
						* 获取 SeekableByteChannel 管道

		SeekableByteChannel newByteChannel(Path path,Set<? extends OpenOption> options,FileAttribute<?>... attrs);
						* 同上,可以设置属性
	

---------------------------------
SeekableByteChannel-实例方法	 |
---------------------------------
	 long position();
		* 获取指针
	 SeekableByteChannel position(long newPosition);
		* 设置指针位置

	 int read(ByteBuffer dst);
		* 读
	 int write(ByteBuffer src);
		* 写

	 long size();
		* 文件长度

	 SeekableByteChannel truncate(long size);
		* 截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除
