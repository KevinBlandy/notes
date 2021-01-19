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

	
	# PUT 过程
		final V putVal(K key, V value, boolean onlyIfAbsent) {
			if (key == null || value == null) throw new NullPointerException();
			int hash = spread(key.hashCode());
			int binCount = 0;
			for (Node<K,V>[] tab = table;;) { // 1
				Node<K,V> f; int n, i, fh;
				if (tab == null || (n = tab.length) == 0)// 2
					tab = initTable();
				else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {// 3
					if (casTabAt(tab, i, null,
								 new Node<K,V>(hash, key, value, null)))
						break;                   // no lock when adding to empty bin
				}
				else if ((fh = f.hash) == MOVED)// 4
					tab = helpTransfer(tab, f);
				else {
					V oldVal = null;
					synchronized (f) {// 5
						if (tabAt(tab, i) == f) {
							if (fh >= 0) {
								binCount = 1;
								for (Node<K,V> e = f;; ++binCount) {
									K ek;
									if (e.hash == hash &&
										((ek = e.key) == key ||
										 (ek != null && key.equals(ek)))) {
										oldVal = e.val;
										if (!onlyIfAbsent)
											e.val = value;
										break;
									}
									Node<K,V> pred = e;
									if ((e = e.next) == null) {
										pred.next = new Node<K,V>(hash, key,
																  value, null);
										break;
									}
								}
							}
							else if (f instanceof TreeBin) {
								Node<K,V> p;
								binCount = 2;
								if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
															   value)) != null) {
									oldVal = p.val;
									if (!onlyIfAbsent)
										p.val = value;
								}
							}
						}
					}
					if (binCount != 0) {
						if (binCount >= TREEIFY_THRESHOLD) // 6
							treeifyBin(tab, i);
						if (oldVal != null)
							return oldVal;
						break;
					}
				}
			}
			addCount(1L, binCount);
			return null;
		}
			
		1 根据 key 计算出 hashcode 
		2 判断是否需要进行初始化
		3 f 即为当前 key 定位出的 Node,如果为空表示当前位置可以写入数据,利用 CAS 尝试写入,失败则自旋保证成功
		4 如果当前位置的 hashcode == MOVED == -1,则需要进行扩容
		5 如果都不满足,则利用 synchronized 锁写入数据
		6 如果数量大于 TREEIFY_THRESHOLD 则要转换为红黑树
	
	# GET过程
		public V get(Object key) {
			Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
			int h = spread(key.hashCode()); // 1
			if ((tab = table) != null && (n = tab.length) > 0 &&
				(e = tabAt(tab, (n - 1) & h)) != null) {
				if ((eh = e.hash) == h) {
					if ((ek = e.key) == key || (ek != null && key.equals(ek)))
						return e.val;  // 2
				}
				else if (eh < 0)
					return (p = e.find(h, key)) != null ? p.val : null;  
				while ((e = e.next) != null) {
					if (e.hash == h &&
						((ek = e.key) == key || (ek != null && key.equals(ek))))
						return e.val; //3 
				}
			}
			return null;
		}

		1 根据计算出来的 hashcode 寻址,如果就在桶上那么直接返回值
		2 如果是红黑树那就按照树的方式获取值
		3 就不满足那就按照链表的方式遍历获取值
			
	# 静态方法
		<K> KeySetView<K,Boolean> newKeySet()
		<K> KeySetView<K,Boolean> newKeySet(int initialCapacity)
	
		