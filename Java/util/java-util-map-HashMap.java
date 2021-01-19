--------------------
HashMap				|
--------------------
	# 存储结构的变化
		* JDK 1.7及以前使用 Hash 表 + 链表实现
		* JDK 1.8及以后使用 Hash 表 + 红黑树实现(优化了查询效率)
		* 当触发了一定的条件后,会把链表转换为红黑树
			* 链表长度大于等于8
			* table数组长度大于等于64(able数组容量比较小时, 键值对节点 hash 的碰撞率可能会比较高, 进而导致链表长度较长, 这个时候应该优先扩容, 而不是立马树化)
	
	# 扩容行为的变化
		 * HashMap扩容阶段重新映射元素时不需要像1.7版本那样重新去一个个计算元素的hash值,
		 * 而是通过hash & oldCap的值来判断, 若为0则索引位置不变, 不为0则新索引=原索引+旧数组长度

		 * 因为使用的是2次幂的扩展(指长度扩为原来2倍), 所以元素的位置要么是在原位置, 要么是在原位置再移动2次幂的位置
		 * 因此,在扩充HashMap的时候,不需要像JDK1.7的实现那样重新计算hash
		 * 只需要看看原来的hash值新增的那个bit是1还是0就好了,是0的话索引没变,是1的话索引变成: 原索引 + oldCap

		 * 这也是长度为2的幂次方的一个好处


	# 插入节点的变化
		* JDK1.7用的是头插法, 而JDK1.8及之后使用的都是尾插法
		* 因为JDK1.7是用单链表进行的纵向延伸, 当采用头插法就是能够提高插入的效率, 但是也会容易出现逆序且环形链表死循环问题
		* 是在JDK1.8之后是因为加入了红黑树使用尾插法, 能够避免出现逆序且链表死循环的问题
	
	# Hash表的尺寸和容量非常的重要
		* 一般来说,Hash表这个容器当有数据要插入时,都会检查容量有没有超过设定的 thredhold,如果超过,需要增大hash表的尺寸
		* 但是这样一来,整个hash表里的无素都需要被重算一遍,这叫rehash,这个成本相当的大
	
	# 底层是数组 + 单向链表(红黑树)
		Node<K,V>[] table;
		class Node {
			final int hash;
			final K key;
			V value;
			Node<K,V> next;
		}

	# 核心的属性
		static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
			* 默认的存储桶数(大小)

		static final int MAXIMUM_CAPACITY = 1 << 30;
			* 最大的存储桶数量

		static final float DEFAULT_LOAD_FACTOR = 0.75f;
			* 默认的负载因子, 决定什么时候扩容
				容器大小 x 负载因子 = 触发扩容的大小

		static final int TREEIFY_THRESHOLD = 8;
			* 用于判断是否需要将链表转换为红黑树的阈值
			* 如果链表的长度大于了该值,大于了该值就会转换为红黑树

		static final int UNTREEIFY_THRESHOLD = 6;
			* 把红黑树转换为链表的阈值

		static final int MIN_TREEIFY_CAPACITY = 64;

		transient Node<K,V>[] table;
		transient Set<Map.Entry<K,V>> entrySet;
		int size;
			* 存储的数据数量

		int modCount;
			* 修改的次数

		float loadFactor;
			* 负载因子
		
		int threshold;
			* 调整Map大小的下一个值

	
	# HashMap 在并发场景下使用时容易出现问题
		* 问题源码
			void transfer(Entry[] newTable, boolean rehash) {
				// 新table的长度
				int newCapacity = newTable.length;
				// 遍历旧table
				for (Entry<K,V> e : table) {
					// 遍历链表
					while(null != e) {
						// 当前节点的下一个节点
						Entry<K,V> next = e.next;
						if (rehash) {
							// 重新计算出节点的hash
							e.hash = null == e.key ? 0 : hash(e.key);
						}
						// 计算出元素所在的节点
						int i = indexFor(e.hash, newCapacity);
						// 把新table中的头节点, 设置为自己的下一个节点
						e.next = newTable[i];
						// 把自己设置为第一个节点(头插法)
						newTable[i] = e;
						// 开始链表下一个遍历
						e = next;
					}
				}
			}

			* 多线程put操作后,get操作导致死循环
			* 据说jdk8修复了这个问题, 扩容时使用尾插法, 保持了原来链表中的顺序
		
		* 尝试复现问题
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < 1000; i++) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						map.put(UUID.randomUUID().toString(), "");
					}
				}).start();
			}
			
			
			* HashMap 扩容的时候会调用 resize() 方法
			* 这里的并发操作容易在一个桶上形成环形链表
				* 当获取一个不存在的 key 时,计算出的 index 正好是环形链表的下标就会出现死循环

		
		* 多线程put非null元素后,get操作得到null值(导致元素丢失,这个问题jdk8没有修复)
		
	
	# 容量只能是2的次方
		* 输入的容量参数, 会被修改为大于等于这个值的2次幂
		    static final int tableSizeFor(int cap) {
				int n = cap - 1;
				n |= n >>> 1;
				n |= n >>> 2;
				n |= n >>> 4;
				n |= n >>> 8;
				n |= n >>> 16;
				return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
			}
			
			* 该算法让最高位的1后面的位全变为1, 最后再让结果n+1, 即得到了2的整数次幂的值了
			* 让cap- 1再赋值给n的目的是另找到的目标值大于或等于原值

			* 例如二进制 1000 十进制数值为8, 如果不对它减1而直接操作, 将得到答案 10000, 即16 显然不是结果
			* 减1后二进制 为111, 再进行操作则会得到原来的数值 1000, 即8, 通过一系列位运算大大提高效率
			
		
			* 好处1
			* 当数组长度为2的幂次方时, 可以使用位运算来计算元素在数组中的下标
			* 只有当数组长度为2的幂次方时, hash &(length-1) 才等价于 hash%length, 使用位运算可以提高效率
			
			* 好处2
			* 增加hash值的随机性，减少hash冲突
			* 如果 length 为 2 的幂次方, 则 length-1 转化为二进制必定是 11111……的形式
			* 这样的话可以使所有位置都能和元素hash值做与运算, 如果是如果 length 不是2的次幂
			* 比如length为15, 则length-1为14, 对应的二进制为1110, 在和hash 做与运算时, 最后一位永远都为0, 浪费空间


		* 计算元素hash所在在的下标: i = (n - 1) & hash
			 if ((p = tab[i = (n - 1) & hash]) == null)
				 tab[i] = newNode(hash, key, value, null);
			
			hash	计算出的hash值
			tab		数组
			i		临时变量, 存储计算出的下标
			n		数组长度

	
	# Hash的计算
		* 源码
			static final int hash(Object key) {
				int h;
				return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
			}