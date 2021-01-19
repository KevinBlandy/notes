-----------------------------------
AsynchronousFileChannel				|
-----------------------------------
	# 文件异步读写通道
	# 构造
		* 通过静态方法
		AsynchronousFileChannel open(Path file,Set<? extends OpenOption> options,ExecutorService executor, FileAttribute<?>... attrs);
		AsynchronousFileChannel open(Path file, OpenOption... options)
	
	# 静态方法
		AsynchronousFileChannel open(Path file,Set<? extends OpenOption> options,ExecutorService executor, FileAttribute<?>... attrs);
		AsynchronousFileChannel open(Path file, OpenOption... options)
		
	# 实例方法
		long size();
		AsynchronousFileChannel truncate(long size);
		void force(boolean metaData);
		void lock(A attachment,CompletionHandler<FileLock,? super A> handler);
		void lock(long position,long size, boolean shared,A attachment,CompletionHandler<FileLock,? super A> handler);
		Future<FileLock> lock(long position, long size, boolean shared);
		Future<FileLock> lock();
		FileLock tryLock();

		void read(ByteBuffer dst,long position,A attachment,CompletionHandler<Integer,? super A> handler);
		Future<Integer> read(ByteBuffer dst, long position);

		void write(ByteBuffer src,long position,A attachment,CompletionHandler<Integer,? super A> handler);
		Future<Integer> write(ByteBuffer src, long position);


		