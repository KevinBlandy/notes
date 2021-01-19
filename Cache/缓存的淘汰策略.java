----------------------------
缓存淘汰策略				|
----------------------------

	# LFU
		* Least Frequency Used, 淘汰使用频率最低的
		* 为每个entry维护一个计数器, 每命中一次+1, 淘汰时找最小的

	# LRU
		* Least Recently Used, 淘汰最近命中时间最早 的entry, 即最久没有被使用过的

		* 直接的实现需要为每个entry维护最后一次命中的时刻
		
		* 也有一个更好的实现方法, 维护一个队列
		* 每次命中将entry移到队列头部,淘汰时找队尾即可, 该元素即最久没有使用过的元素

		* 该算法既考虑到了时效性, 又容易实现, 是用的最多的evict策略
	

----------------------------
LFU							|
----------------------------
	#使用JDK提供的实现
		public class LRUCache extends LinkedHashMap {
			private static final int MAX_ENTRIES = 3;

			public LRUCache2(){
				super(MAX_ENTRIES+1, .75F,true);    // 最后一个参数为true, 表示维护的是access order 而非 insertion order
			}
			
			// 再每次执行插入后, 判断该方法, 如果返回 true, 执行删除链表最后的元素
			protected boolean removeEldestEntry(Map.Entry eldest){
				return this.size() > MAX_ENTRIES;
			}
		}