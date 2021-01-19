------------------------
ReentrantLock			|
------------------------
	# 可重入锁
		*  实现了 Lock 接口
			ReentrantLock implements Lock, java.io.Serializable
	
	# 构造方法
		ReentrantLock()
		ReentrantLock(boolean fair)

	# 公平锁
		* 一般意义上的锁是不公平的,不一定先来的线程能先得到锁,后来的线程就后得到锁。
		* 不公平的锁可能会产生饥饿现象
		* 公平锁的意思就是,这个锁能保证线程是先来的先得到锁,虽然公平锁不会产生饥饿现象,但是公平锁的性能会比非公平锁差很多
			ReentrantLock fairLock = new ReentrantLock(true);

	# 实例方法
		lock();
			* 尝试获取锁,线程阻塞

		lockInterruptibly();
			* 当前线程会阻塞获取锁
			* 如果该线程被执行了 interrupt() 方法,并且该线程处于阻塞状态的话,那么该线程的此方法就会抛出异常
			* '详情可以看 Thread的中断机制'
			* 与local()的区别
				* lock() 会一直阻塞,直到获取锁
				* lockInterruptibly() 也会一直阻塞,直到获取锁
					* 但是,如果调用了该线程的 interrupt() 的方法,那么 lockInterruptibly() 就会抛出中断异常,必须进行处理
					* 该方法可以在线程处于阻塞状态下,由其他线程促使该方法抛出异常,从而中断线程,可以防止死锁之类的问题产生


		newCondition();
			* 返回一个Condition实例

		unlock();
			* 释放锁

		getHoldCount();
		getQueueLength();
		getWaitQueueLength(null);

		hasQueuedThread(null);
		hasWaiters(null);
		hasQueuedThreads();

		isFair();
			* 是否是公平锁

		isHeldByCurrentThread();
		isLocked();

		boolean tryLock();
			* 尝试获取锁,不会阻塞,立即返回是否可以获取

		boolean tryLock(long timeout, TimeUnit unit);
			* 尝试获取锁,如果超时,返回 false