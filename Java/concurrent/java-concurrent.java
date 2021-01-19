---------------------------
多线程基础					|
---------------------------
	# volatile 关键字
		* 内存可见性,当多个线程操作共享数据的时候,彼此不可见
		* 该关键字可以保证,内存中的数据对于多个线程的操作,都是可见的
		* 该关键字降低效率,但是比 synchronized 效率高点点,算是轻量级的同步的策略
		* synchronized 是互斥锁,volatile 并不能保证变量的原子性
		
	# 原子变量
		
	# CAS算法
		* 使用CAS(Compare And Swap)算法保证数据的原子性
		* 是硬件对于并发访问的支持
		* 该算法包含了三个操作数
			1,内存值 - v
			2,预估值 - a
			3,更新值 - b
		* 只有当 v==a 时, v = b,否则不会做任何操作
	
			

---------------------------
java.util.concurrent		|
---------------------------
	# 面向并发的工具类
	Lock
	ConcurrentHashMap
	CountDownLatch
	CopyOnWriteArrayList
	CopyOnWriteArraySet
	ExecutorService
	AtomicInteger
	Callable
	






