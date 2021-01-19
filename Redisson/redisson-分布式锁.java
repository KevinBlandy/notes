
------------------------
redisson 分布式锁		|
------------------------
	# 中文文档
		https://github.com/redisson/redisson/wiki/8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8

	# 看门狗机制
		* 如果负责储存这个分布式锁的Redis节点宕机以后,而且这个锁正好处于锁住的状态时,这个锁会出现锁死的状态
		* 为了避免这种情况的发生,Redisson内部提供了一个监控锁的看门狗,它的作用是在Redisson实例被关闭前,不断的延长锁的有效期
		* 默认情况下,看门狗的检查锁的超时时间是30秒钟,也可以通过修改 Config.lockWatchdogTimeout 来另行指定

------------------------
可重入锁(Reentrant Lock)|
------------------------

	# 同步锁的创建
		RLock rLock = redissonClient.getLock("lock");

	# 同步操作
		//加锁,需要手动unlock
		void lock();

		//加锁,10s后自动解锁
		void lock(10, TimeUnit.SECONDS);

		//尝试加锁,如果成功返回true
		boolean rLock.tryLock();

		//尝试加锁,最多等待100s,如果成功上锁(返回true),则10s后自动解锁
		boolean tryLock(100, 10, TimeUnit.SECONDS);
		
		//手动释放锁
		void unlock();
	
	# 异步操作
		Future<Void> lockAsync();
		Future<Void> lockAsync(10, TimeUnit.SECONDS);
		Future<Boolean> rLock.tryLockAsync();
		Future<Void> tryLockAsync(100, TimeUnit.SECONDS);
		Future<Void> rLock.unlockAsync();

	# 其他
		//判断是否可以获取锁
		boolean rLock.isLocked();
		
------------------------
公平锁(Fair Lock)		|
------------------------
	# 跟可重入锁一样
		* 不同的是,它保证了当多个Redisson客户端线程同时请求加锁时,优先分配给先发出请求的线程
	
	# 创建锁
		RLock rLock = redisson.getFairLock("lock");
	
	# 其他几乎同上

------------------------
联锁(MultiLock)			|
------------------------
	# 可以将多个RLock对象关联为一个联锁
		* 每个RLock对象实例可以来自于不同的 redissonClient 实例
	
	# 创建锁
		RLock lock1 = redissonClient.getLock("lock1");
		RLock lock2 = redissonClient.getLock("lock2");
		RLock lock3 = redissonClient.getLock("lock3");

		RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
		// 同时加锁：lock1 lock2 lock3
		// 所有的锁都上锁成功才算成功。
		lock.lock();
		lock.unlock();
	
	# 其他几乎同上

------------------------
红锁(RedLock)			|
------------------------
	# RedissonRedLock对象实现了Redlock介绍的加锁算法,该对象也可以用来将多个RLock对象关联为一个红锁
		* 每个RLock对象实例可以来自于不同的 redissonClient 实例
	
	# 创建锁
		RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
		// 同时加锁：lock1 lock2 lock3
		// 红锁在大部分节点上加锁成功就算成功
		lock.lock();
		lock.unlock();

------------------------
读写锁(ReadWriteLoc)	|
------------------------
	# RReadWriteLock 对象实现了 java.util.concurrent.locks.ReadWriteLock 接口
		* 同时还支持自动过期解锁
		* 该对象允许同时有多个读取锁,但是最多只能有一个写入锁
	
	# 创建锁
		RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock("lock");
		// 获取读锁并上锁
		rReadWriteLock.readLock().lock();
		// 获取写锁并上锁
		rReadWriteLock.writeLock().lock();;

------------------------
信号量(Semaphore)		|
------------------------

------------------------------------------------
可过期性信号量(PermitExpirableSemaphore)		|
------------------------------------------------

------------------------
闭锁(CountDownLatch)	|
------------------------
