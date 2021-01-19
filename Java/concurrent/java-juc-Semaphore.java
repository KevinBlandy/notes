----------------------------
Semaphore					|
----------------------------
	# 主要的作用就是控制访问资源的线程数量

	# 构造函数
		Semaphore(int permits, boolean fair) 
		Semaphore(int permits) 
		
		* permits 资源同时访问线程的最大数量
		* fair 是否是公平锁
	
	# 函数
		void acquire()
			* 获取一个令牌,如果不足,则阻塞
		void acquire(int permits) 
			* 尝试获取N个令牌,如果不足,则阻塞
		void acquireUninterruptibly() 
			* 尝试获取一个令牌
		void acquireUninterruptibly(int permits)
			* 尝试获取N个令牌,可能会抛出中断异常

		int availablePermits()
		int drainPermits()
		int getQueueLength()

		boolean hasQueuedThreads()
		boolean isFair()

		void release()
		void release(int permits)

		boolean tryAcquire()
		boolean tryAcquire(int permits)
		boolean tryAcquire(int permits, long timeout, TimeUnit unit)
		boolean tryAcquire(long timeout, TimeUnit unit)