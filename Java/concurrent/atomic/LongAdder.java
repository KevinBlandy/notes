-------------------------
LongAdder				 |
-------------------------
	# 这东西JDK8才有的东西, 用于替代:AtomicLong
		* 内部的实现有点类似 ConcurrentHashMap 的分段锁
		* 最好的情况下, 每个线程都有独立的计数器, 这样可以大量减少并发操作
	
	# AtomicLong 的问题
		* 为了实现正确的累加操作, 如果并发量很大的话,cpu会花费大量的时间在试错上面
		* 相当于一个spin(自旋)的操作, 如果并发量小的情况, 这些消耗可以忽略不计
		
	
	# 构造函数
		LongAdder()
	
	# 实例方法
		void add(long x) 
		void decrement()
		double doubleValue()
		float floatValue()
		void increment()
		int intValue()
		longValue()
		reset()
		long sum()
		long sumThenReset()
	
	# 总结
		* 单线程环境, AtomicLong 性能比 LongAdder 好
		* 一些高并发的场景, 比如限流计数器, 建议使用 LongAdder 替换 AtomicLong, 性能可以提升不少
	
	# JDK还提供了一些其他类型的原子自增/自减
		DoubleAdder