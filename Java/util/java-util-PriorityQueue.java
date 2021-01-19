------------------------
PriorityQueue			|
------------------------
	# 优先队列
	# 内部默认是最小堆
		* 最小权重值,会被先出队
	
	# 构造方法
		public PriorityQueue(int initialCapacity) ;
		public PriorityQueue(Comparator<? super E> comparator);

	# 实例方法
		
		poll();	
			* 出队
		E remove()
			* 出队
		peek();	
			* 看一下队首元素
		offer(E e);
			* 入队
		add(E e);	
			* 入队
		addAll(Collection<? extends E> c);
			* 入队所有
		size();
			* 长度
		isEmpty();
			* 是否为空
		contains(Object o);
			* 是否包含
		
	