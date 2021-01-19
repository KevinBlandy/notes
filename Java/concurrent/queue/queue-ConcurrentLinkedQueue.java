--------------------------------
ConcurrentLinkedQueue			|
--------------------------------
	# 使用链表作为其数据的结构的队列

	# 无界非阻塞队列, 并且是线程安全的
		* 使用CAS算法保证线程安全
		
		* 入队出队函数都是操作volatile变量:head，tail, 所以要保证队列线程安全只需要保证对这两个Node操作的可见性和原子性
		* 由于volatile本身保证可见性, 所以只需要在多线程下保证对着两个变量操作的原子性

		* 对于offer操作是在tail后面添加元素, 也就是调用tail.casNext方法, 而这个方法是使用的CAS操作,
		* 只有一个线程会成功, 然后失败的线程会循环一下, 重新获取tail，然后执行casNext方法
		* 对于poll也是这样的
	

	# 构造函数
		public ConcurrentLinkedQueue()
		public ConcurrentLinkedQueue(Collection<? extends E> c) 
	
	# 实例函数
	
	# 注意
		* 调用size()方法的时候, 会遍历一遍链表, 对性能损害较大, 执行很慢, 因此应该尽量的减少使用这个方法, 如果判断是否为空, 最好用isEmpty()方法
		
		* CAS没有加锁所以从调用size函数到返回结果期间有可能增删元素, 导致统计的元素个数不精确

		* 无界的, 一定要注意内存溢出的问题

	

