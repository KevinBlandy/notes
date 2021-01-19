-----------------------------
zk锁						 |
-----------------------------
	# 所有的锁都是:	InterProcessLock 子类
		// 尝试获取锁 - 阻塞,直到获取成功
		public void acquire() throws Exception;
		// 尝试获取锁 - 阻塞,直到超时,返回 boolean 表示锁是否获取成功
		public boolean acquire(long time, TimeUnit unit) throws Exception;
		// 释放锁
		public void release() throws Exception;
		boolean isAcquiredInThisProcess();

-----------------------------
重入锁						 |
-----------------------------
	#  实现类
		// 创建锁
		InterProcessMutex interProcessMutex = new InterProcessMutex(client, "/node");
		// 尝试获取锁 - 阻塞,直到获取成功
		interProcessMutex.acquire();
		// 尝试获取锁 - 阻塞,直到超时,返回 boolean 表示锁是否获取成功
		boolean result = interProcessMutex.acquire(1000, TimeUnit.SECONDS);
		// 释放锁
		interProcessMutex.release();
	
-----------------------------
互斥锁						 |
-----------------------------		
	# 该锁不能重入
	# 实现类
		// 创建锁
		InterProcessSemaphoreMutex interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(client,"/lock");
		// 尝试获取锁 - 阻塞,直到获取成功
		interProcessSemaphoreMutex.acquire();
		// 尝试获取锁 - 阻塞,直到超时,返回 boolean 表示锁是否获取成功
		interProcessSemaphoreMutex.acquire(1000, TimeUnit.SECONDS);
		// 释放锁
		interProcessSemaphoreMutex.release();


-----------------------------
读写锁						 |
-----------------------------
	# 该锁可以重入
	# 实现类
		InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, "/node");
		// 获取读锁
		InterProcessMutex readInterProcessMutex = interProcessReadWriteLock.readLock();
		// 获取写锁
		InterProcessMutex writeInterProcessMutex = interProcessReadWriteLock.writeLock();
		readInterProcessMutex.acquire();
		readInterProcessMutex.acquire(100, TimeUnit.SECONDS);
		readInterProcessMutex.release();


-----------------------------
信号量						 |
-----------------------------
	# 线程之间的同步,为了保证同一时刻只有限定数量的线程可以访问到资源
	# 实现 InterProcessSemaphoreV2
		// 一个创建100个租约(同时最多允许100个线程访问)
		InterProcessSemaphoreV2 interProcessSemaphoreV2 = new InterProcessSemaphoreV2(client,"/node", 100);

		// 尝试获取一个租约,如果没有,则阻塞直到获取成功
		Lease lease = interProcessSemaphoreV2.acquire();
		
		// 尝试获取一个租约,超时返回 null
		Lease lease interProcessSemaphoreV2.acquire(1000, TimeUnit.SECONDS);

		// 获取指定数量的租约,如果不足,会阻塞
		Collection<Lease> leases = interProcessSemaphoreV2.acquire(15);
	
		// 返还一个租约
		interProcessSemaphoreV2.returnLease(lease);

		// 返还一堆租约
		interProcessSemaphoreV2.returnAll(leases);

-----------------------------
联合锁						 |
-----------------------------
	# 由多把锁组成的一把锁,必须当所有的锁都加速成功,则加锁成功
	# 实现类
		InterProcessMultiLock(CuratorFramework client, List<String> paths);
			* 默认根据paths新建 InterProcessMutex 锁

		public InterProcessMultiLock(List<InterProcessLock> locks);
			* 使用指定的锁
		
		interProcessMultiLock.acquire();
		interProcessMultiLock.acquire(100, TimeUnit.SECONDS);
		interProcessMultiLock.release();