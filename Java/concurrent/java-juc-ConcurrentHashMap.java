----------------------------
ConcurrentHashMap			|
----------------------------
	# JDK1.8以前使用 Segment 分段锁
		* Segment 继承于 ReentrantLock,不会像 HashTable 那样不管是 put 还是 get 操作都需要做同步处理
		* 理论上 ConcurrentHashMap 支持 CurrencyLevel (Segment 数组数量)的线程并发,每当一个线程占用锁访问一个 Segment 时,不会影响到其他的 Segment
		* 默认有 16 个段(Segment)
		* 每个段中有 16 个元素
		* 但是仍然存在,查询遍历链表效率太低的问题
	

	# JDK1.8后,其中抛弃了原有的 Segment 分段锁,而采用了 CAS(乐观锁) + synchronized 来保证并发安全性
		* Hash冲突的链表在满足一定条件后会转换为红黑树
		* 取消了 ReentrantLock 改为了 synchronized(可以看出在新版的 JDK 中对 synchronized 优化是很到位的)

	
			
	# 静态方法
		public static <K> KeySetView<K,Boolean> newKeySet()
		public static <K> KeySetView<K,Boolean> newKeySet(int initialCapacity) 
			* 返回Set，Set使用的Map就是ConcurrentHashMap
	
	# 实例方法
		public KeySetView<K,V> keySet(V mappedValue) 
			
	
	# 批量操作
		search
			* 为每个元素执行操作，直到操作函数返回一个非null的结果
			* 然后返回这个非null结果，方法结束
				// 找到第一个v大于100的值
				map.search(1, ((k, v) -> v > 100 ? v : null));
		reduce
			* 计算
		
		forEach
			* 遍历操作
		
		* 每个操作都有四个版本
			xxxKeys(searchKeys)
			xxx
			xxxValues
			xxxEntries
		
		* 每个操作都有一个 parallelismThreshold 参数
		* 如果map包含的元素数量超过了这个值，就会并行完成批量操作
		* 使用 Long.MAX_VALUE 保证单线程执行
		* 使用 1 保证多线程执行


