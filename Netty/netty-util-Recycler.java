-------------------------
Recycler<T>				 |
-------------------------
	# 抽象类
	# 构造方法
		Recycler()
		Recycler(int maxCapacityPerThread)
		Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor)
		Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor,int ratio, int maxDelayedQueuesPerThread)
	

	# 实例方法
		T get()
		

	# 唯一抽象方法
		abstract T newObject(Handle<T> handle);
	
