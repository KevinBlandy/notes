----------------------------------
LinkedBlockingQueue				  |
----------------------------------
	# 底层使用单链表的形式实现Queue

	# 采用两把锁的锁分离技术实现入队出队互不阻塞
			private final ReentrantLock takeLock = new ReentrantLock();
			private final Condition notEmpty = takeLock.newCondition();

			private final ReentrantLock putLock = new ReentrantLock();
			private final Condition notFull = putLock.newCondition();
	
	# 构造函数
		public LinkedBlockingQueue()
		public LinkedBlockingQueue(int capacity)
		LinkedBlockingQueue(Collection<? extends E> c)

		capacity
			* queue的容量大小, 默认:Integer.MAX_VALUE
		
		c
			* 初始化的内容
	

	# 实例函数
		