----------------------------
Lock						|
----------------------------
	# 一个接口
	# 用于解决多线程的安全问题
	# synchronized 是隐式的锁
	# Lock 是显示的锁
	# 创建
		Lock lock = new ReentrantLock();

----------------------------
Lock-属性					|
----------------------------

----------------------------
Lock-方法					|
----------------------------
	void lock();
		* 上锁，直到锁获取成功
	
	void lockInterruptibly() throws InterruptedException;
		* 在阻塞获取锁的时候，阻塞线程可以被中断

	boolean tryLock();
	boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
		* 尝试获取锁
		* 可以设置超时时间，而且阻塞线程可以被中断

	unlock();
		* 锁释放
	
	Condition newCondition();
