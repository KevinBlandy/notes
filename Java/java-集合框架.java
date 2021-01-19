
--------------------
List				|
--------------------
	# 接口
	# 实现类
		ArrayList
			* 数组结构,检索快,增删慢
		LinkedList
			* 链表结构,增删快,检索满
			*
				addFIrst();
					把元素放在容器第一个位置,后来居上
				addLast();
					把元素放在容器最后的位置,后来居后

				getFirst();
				getLast();
					获取元素。但是不删除元素

				removeFirst();
				removeLast();
					获取元素,但是会删除元素,如果集合中没有元素会抛出异常

				在JDK1.6出现了替代方法！

				offerFirst();
				offerLast();
					添加元素。同上
				peekFirst();
				peekLast();
					获取元素,但不删除
				pollFirst();
				pollLast();
					获取元素,删除元素,如果集合中没有元素,不会抛出异常
 	
	获取元素。但是会删除元素。如果集合中没有元素会返回 ——null!
	# 抽象方法
		boolean add(E e) 
			* 向列表的尾部添加指定的元素。 
		void add(int index, E element) 
			* 在列表的指定位置插入指定元素。 
		void clear() ;
			* 清空
		Object[] toArray() 
			* 返回按适当顺序包含列表中的所有元素的数组（从第一个元素到最后一个元素）。 
		<T> T[] toArray(T[] a);
			* 转换为数组,返回的数组就是传递进去的那个数组
		E set(int index, E element) 
			* 用指定元素替换列表中指定位置的元素。 
		int size() 
			* 返回列表中的元素数。 
		ListIterator<E> listIterator() 
			* 返回此列表元素的列表迭代器（按适当顺序）。 
		ListIterator<E> listIterator(int index) 
			* 返回列表中元素的列表迭代器（按适当顺序），从列表的指定位置开始。 
		Iterator<E> iterator() 
			* 返回按适当顺序在列表的元素上进行迭代的迭代器。 
	
--------------------
Set					|
--------------------
	# 接口
	# 实现类
		HashSet
			* Hash表,通过 hasCode 和 equals 来确定对象的唯一性
		TreeSet
			* 二叉树结构,通过 对象实现了 Comparable 的方法来判断对象的唯一和排序
	# 抽象方法

--------------------
Map					|
--------------------
	# 接口
	# 实现类
		HashMap
			* Hash表,通过 hasCode 和 equals 来确定对象的唯一性
			* 与 Hashtable 的区别
				1,Hashtable 不允许有 null,HashMap 允许
				2,Hashtable 是jdk1的东西,HashMap 是jdk2
				3,Hashtable 线程安全,HashMap 线程不安全
		TreeMap
			* 二叉树结构,通过 对象实现了 Comparable 的方法来判断对象的唯一和排序

	# 抽象方法
	

	# JDK8新增方法
			V getOrDefault(Object key,V defaultVlue);
				* 如果 key 存在,则返回 Value,如果不存在,则返回 defaultVlue 
			
				
			V compute(K key,BiFunction<? super K, ? super V, ? extends V> remappingFunction) 
				* 先通过 key 去检索 value,如果存在直接返回
				* 如果value 不存在,则会调用 remappingFunction Lambda 获取 value,并且存入 map,然后返回
			

		
