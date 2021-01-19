-------------------------------
MappedByteBuffer				|
-------------------------------
	# 将文件的某一部分或全部映射到内存中, 映射内存缓冲区是个直接缓冲区
	# 它继承:ByteBuffer
	# 通过 FileChannel 的 api 获取
		MappedByteBuffer map(MapMode mode,long position, long size);

		MapMode
			READ_ONLY
				* 试图修改得到的缓冲区将导致抛出 ReadOnlyBufferException
			READ_WRITE
				* 对得到的缓冲区的更改最终将传播到文件
				* 该更改对映射到同一文件的其他程序不一定是可见的
			PRIVATE
				* 对得到的缓冲区的更改不会传播到文件, 并且该更改对映射到同一文件的其他程序也不是可见的
				* 会创建缓冲区已修改部分的专用副本
		
	
	# 方法
		MappedByteBuffer force()
			* 缓冲区是READ_WRITE模式下, 此方法对缓冲区内容的修改强行写入文件

		boolean isLoaded()
			* 如果缓冲区的内容在物理内存中, 则返回真, 否则返回假

		MappedByteBuffer load()
			* load()将缓冲区的内容载入内存, 并返回该缓冲区的引用
		

