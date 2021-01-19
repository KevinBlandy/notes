-----------------------------
读写锁						 |
-----------------------------
	# 读写锁
		ReentrantReadWriteLock implements ReadWriteLock, java.io.Serializable {
	
	# 构造方法
		ReentrantReadWriteLock(boolean fair)
			* 构造公平锁
		ReentrantReadWriteLock()
			* 构造非公平锁
	
	
	# 实例方法
		ReentrantReadWriteLock.ReadLock  readLock()
			* 返回读锁

		ReentrantReadWriteLock.WriteLock writeLock()
			* 返回写锁
		
