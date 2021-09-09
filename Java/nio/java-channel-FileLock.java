--------------------------
FileLock
--------------------------
	# 文件锁抽象类
		abstract class FileLock implements AutoCloseable
	
	# 锁的获取
		FileLock tryLock(long position, long size, boolean shared)
		FileLock fileChannel.tryLock();
			* 该调用不会阻塞,直接获取锁,如果锁不存在,返回null
			* 获取文件锁参数
				position
				size
					* 这俩参数指定要加锁的部分，可以只对文件的部分内容加锁
				shared
					* 指定是否是共享锁。
					* 如果指定为共享锁，则其它进程可读此文件，所有进程均不能写此文件，如果某进程试图对此文件进行写操作，会抛出异常。
			* demo
				FileLock lock = fileChanne.lock();		//获取文件锁
				lock.release();							//释放锁
		
		FileLock lock(long position, long size, boolean shared)throws IOException;
		FileLock fileChannel.lock(); 
			* 同上
			* 该调用会阻塞,直到获取到锁
	
	protected FileLock(FileChannel channel, long position, long size, boolean shared)
	protected FileLock(AsynchronousFileChannel channel, long position, long size, boolean shared)

	public final FileChannel channel()
	public Channel acquiredBy() 
	public final long position()
	public final long size()
	public final boolean isShared()
		* 是否是共享锁

	public final boolean overlaps(long position, long size)
	public abstract boolean isValid();
		* 锁是否有效

	public abstract void release() throws IOException;
	public final void close() throws IOException {
	
