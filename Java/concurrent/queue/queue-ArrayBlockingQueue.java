----------------------------------
ArrayBlockingQueue				  |
----------------------------------
	# 底层使用数组的有界队列
		ArrayBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, java.io.Serializable
	
	# 通过一把锁和两个Condition完成阻塞,通知
		final ReentrantLock lock;
		private final Condition notEmpty;
		private final Condition notFull;

	# 构造函数
		ArrayBlockingQueue(int capacity) 
		ArrayBlockingQueue(int capacity, boolean fair) 
		ArrayBlockingQueue(int capacity, boolean fair, Collection<? extends E> c)

		capacity 
			* 构造指定大小的有界队列
		fair
			* 指定为公平或非公平锁 , 默认 false
		c
			* 初始化的数据集合

	
	# 实例函数
		