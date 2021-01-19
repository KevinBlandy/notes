---------------------------
AtomicInteger				|
---------------------------
	# 原子性的变量
	# 使用CAS(Compare And Swap)算法保证数据的原子性
	# 相同的API(数据类型不同)
		AtomicLong
		AtomicBoolean
		AtomicLong
		AtomicReference

		AtomicIntegerArray
		AtomicLongArray

	# 创建
		AtomicInteger() 
			* 创建具有初始值 0 的新 AtomicInteger 
		AtomicInteger(int initialValue)  
			* 创建指定初始值的 AtomicInteger
	# 方法
		void set(int newValue);
			* 设置新值
		int getAndSet(int newValue);
			* 设置新值,并且返回旧值

		int addAndGet(int delta);
			* 加上指定值,返回相加后的值
		int getAndAdd(int delta);
			* 加上指定的值,返回相加前的原始值

		int decrementAndGet() 
			* 减去1,返回减后的值
		int getAndDecrement();
			* 减去1,返回减前的值
		
		int incrementAndGet(); 
			* 加1,返回加后的值
		int getAndIncrement();
			* 加1,返回加前的值
		