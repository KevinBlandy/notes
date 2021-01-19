-----------------------------
StampedLock					 |
-----------------------------
	# JDK1.8的新锁, 并不是 Lock 的实现
		StampedLock implements java.io.Serializable

		* ReadWriteLock, 有个问题：如果有线程正在读, 写线程需要等待读线程释放锁后才能获取写锁
		* 即读的过程中不允许写, 这是一种悲观的读锁

		* StampedLock和ReadWriteLock相比, 改进之处在于: 读的过程中也允许获取写锁后写入
		* 这样一来, 我们读的数据就可能不一致, 所以, 需要一点额外的代码来判断读的过程中是否有写入, 这种读锁是一种乐观锁

		* 通俗理解就是读写不会互斥, 读写会产生一个version的东西, 类似于版本号, 在读之后要判断版本号是否一致
		* 如果不一致的话, 就可能是脏数据, 需要进入悲观读锁, 重新读取数据, 进行逻辑操作

		* StampedLock是不可重入锁, 不能在一个线程中反复获取同一个锁

	
	# 构造函数
		public StampedLock()
	
	# 实例方法

		public int getReadLockCount()
		public Lock asReadLock() 
		public boolean isReadLocked() 
		public long readLock()
			* 获取读悲观锁, 和写锁互斥

		public long readLockInterruptibly() throws InterruptedException
		public long tryConvertToReadLock(long stamp)
		public long tryReadLock()
		public long tryReadLock(long time, TimeUnit unit) throws InterruptedException
		public void unlockRead(long stamp)
			* 根据version是否读锁

		
		public Lock asWriteLock()
		public ReadWriteLock asReadWriteLock()
		public boolean isWriteLocked() 
		
		public long tryConvertToOptimisticRead(long stamp)
		
		public long tryConvertToWriteLock(long stamp)
		public long tryOptimisticRead()
			* 获取一个乐观读锁, 返回的类似于一个版本号
		
		public boolean tryUnlockRead() 
		public boolean tryUnlockWrite()
		public long tryWriteLock()
		public long tryWriteLock(long time, TimeUnit unit) throws InterruptedException
		public void unlock(long stamp)
		public void unlockWrite(long stamp)
			* 释放写锁
		public boolean validate(long stamp) 
			* 判断指定的乐观锁版本号是否过期

		public long writeLock()
			* 获取一个写锁
		
		public long writeLockInterruptibly() throws InterruptedException

	
	# 读加锁

	# 写加锁
		long stamp = stampedLock.writeLock(); // 获取写锁
		try {
			/**
				TODO 执行原子操作
			*/
		} finally {
			stampedLock.unlockWrite(stamp); // 释放写锁
		}
	
	# 读加锁
		long stamp = stampedLock.tryOptimisticRead(); // 获得一个乐观读锁
		/**
			TODO 执行逻辑操作, 非原子操作, 线程不安全, 
		*/
		if (!stampedLock.validate(stamp)) {				// 检查乐观读锁后是否有其他写锁发生
			stamp = stampedLock.readLock();			// 获取一个悲观读锁
			try {
				/**
					升级为悲观锁
					TODO 因为数据失效, 可能需要重新读取数据来,执行原子操作
				*/
			} finally {
				stampedLock.unlockRead(stamp);		// 释放悲观读锁
			}
		}

