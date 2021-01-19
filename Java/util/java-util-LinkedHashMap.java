---------------------------
LinkedHashMap				|
---------------------------
	# 继承自 hashMap,实现了 Map 接口
	# 双向链表(头指针,尾指针)实现

	# 构造
		LinkedHashMap(int initialCapacity)
		LinkedHashMap(int initialCapacity, float loadFactor)
		LinkedHashMap(int initialCapacity,float loadFactor,boolean accessOrder)
		LinkedHashMap(Map<? extends K, ? extends V> m)

		initialCapacity
			* 初始容量

		loadFactor
			* 负载因子

		accessOrder
			* 是否安装访问时间进行排序
			* 最近访问的排在链表前面
			* 对元素的访问,会使元素移动到链表顶部

	# 方法
		protected boolean removeEldestEntry(Map.Entry<K,V> eldest);
			* LinkedHashMap自带的判断是否删除最老的元素方法,默认返回false,即不删除老数据
			* 子类重写这个方法,当满足一定条件时删除老数据(链表尾)
		

		
