-------------------------------
MappedByteBuffer				|
-------------------------------
	# public abstract sealed class MappedByteBuffer extends ByteBuffer permits DirectByteBuffer
		* 将文件的某一部分或全部映射到内存中, 映射内存缓冲区是个直接缓冲区
		* 写的时候，它写的是内存中的字节数组，内容同步到文件上的时机是不确定的，由操作系统决定
		* 不过，只要操作系统不崩溃，操作系统会保证同步到文件上，即使映射这个文件的应用已经退出了
	
	# 文件读写的拷贝
		* 在一般的文件读写中，会有两次数据复制，一次是从硬盘复制到操作系统内核，另一次是从操作系统内核复制到用户态的应用程序。
		* 而在内存映射文件中，一般情况下，只有一次复制，且内存分配在操作系统内核，应用访问的就是操作系统的内核内存空间，这显然要比普通的读写效率更高。
	
	# 可以被多个程序共享
		* 多个程序可以映射同一个文件，映射到同一块内存区域，一个程序对内存的修改，可以让其他程序也看到。
		* 可以用于不同应用程序之间的通信。

	# 局限
		* 不太适合处理小文件，它是按页分配内存的，对于小文件，会浪费空间
		* 映射文件要消耗一定的操作系统资源，初始化比较慢。

	# 通过 FileChannel 的 api 获取
		MappedByteBuffer map(MapMode mode,long position, long size);

			mode，枚举
				READ_ONLY
					* 试图修改得到的缓冲区将导致抛出 ReadOnlyBufferException
					* 这个模式和创建 Channel 的读写权限有关，不能冲突
						// Channel 打开了读写权限
						FileChannel.open(Paths.get("C:\\Users\\Laptop\\Desktop\\map.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE);
				
				READ_WRITE
					* 对得到的缓冲区的更改最终将传播到文件
					* 该更改对映射到同一文件的其他程序不一定是可见的
				
				PRIVATE
					* 私有模式，更改不反映到文件，也不被其他程序看到
			
			position
				* 开始
			size
				* 长度
			
			* 如果映射的区域超过了现有文件的范围，则文件会自动扩展，扩展出的区域字节内容为 0。
		
	# 方法
		public final MappedByteBuffer clear()
		public abstract MappedByteBuffer compact();
		public abstract MappedByteBuffer duplicate();
		public final MappedByteBuffer flip()
		public final MappedByteBuffer force()
		public final MappedByteBuffer force(int index, int length)
			* 在 EAD_WRITE 模式下，强行把修改写入到文件中
		
		public final MappedByteBuffer limit(int newLimit)

		public final boolean isLoaded()
			* 是否已经把内容加载到内存中，只是一个参考值，不一定准确

		public final MappedByteBuffer load()
			* 尽量将文件内容加载到内存中
		
		public final MappedByteBuffer mark()

		public final int position()
		public final MappedByteBuffer position(int newPosition)
			* 获取/修改指针位置

		public final MappedByteBuffer reset()
		public final MappedByteBuffer rewind()
		public abstract MappedByteBuffer slice()
		public abstract MappedByteBuffer slice(int index, int length)
