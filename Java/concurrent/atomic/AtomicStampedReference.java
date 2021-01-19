------------------------
AtomicStampedReference	|
------------------------
	# jdk1.5后的新CAS类,解决了ABA的问题
	# 构造函数
		 AtomicStampedReference(V initialRef, int initialStamp) 
		
	
	# 实例方法
	
		boolean attemptStamp(V expectedReference, int newStamp)
		boolean compareAndSet(V expectedReference, V newReference,int expectedStamp, int newStamp)
			* 首先检查当前引用是否等于预期引用,并且当前标志是否等于预期标志
			* 如果全部相等,则以原子方式将该引用和该标志的值设置为给定的更新值
			* 解决了CAS中'ABA'的问题
		
		V get(int[] stampHolder)
		V getReference()
		int getStamp()
		void set(V newReference, int newStamp)
		boolean weakCompareAndSet(V expectedReference,V newReference,int expectedStamp,int newStamp)

