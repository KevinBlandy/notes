-----------------------------
List
-----------------------------
	# 集合的初始化
		var arr = [];				
		List arr = [1, 2, 3, '4'];	
		List arr = new List(2);  
	
		* 在不使用泛型的情况下, 一个list中可以存储不同类型的元素
	
	# List 类的构造方法, 可以初始化一下固定大小的集合
		List(int length)

		* 通过参数指定List的长度, 默认使用 null 填充

	# 数组的长度
		* 通过 length 属性获取到
		* 使用字面量的形式初始化, 数组长度会随着元素的新增而自增
			var arr = [];
			arr.add(1);
			arr.add(2);
			arr.add(3);
			print(arr);		// [1, 2, 3]
		
		* 固定了数组的length (通过构造方法), 不允许使用 add 方法, 只能通过下标去操作元素
			var arr = new List(1);
			arr.add(1);// Unsupported operation: Cannot add to a fixed-length list
			print(arr);
		
		* 数组操作到了下标以外的元素, 会发生越界异常
			var arr = [];
			arr[0] = 1;  // RangeError (index): Invalid value: Valid value range is empty: 0
			print(arr);
			
	
	# 泛型的 List
		* 泛型 list 的定义必须要通过声明 List 属性来定义变量, 不能使用 var 或者 dynamic
			List<String> arr = ['123', '123']; // ok

			List<String> arr = new List(2);
			arr[0] = 1; // Error: A value of type 'int' can't be assigned to a variable of type 'String'.

		* 存储泛型以外的数据, 会抛出异常
		



-----------------------------
List - 静态方法
-----------------------------
	List.filled(int length, E fill, {bool growable = false})
		* 创建一个 List, 固定长度为 length, 使用 fill元素填充
		* growable 参数表示长度是否可以增长, 默认为 false, 也就是固定长度
	
	List.from(Iterable elements, {bool growable = true});
		* 根据迭代元素, 创建 List, 默认固定长度
	
	List.generate(int length, E generator(int index), {bool growable = true})
	List.of(Iterable<E> elements, {bool growable = true})
	List.unmodifiable(Iterable elements)
	

	static List<T> castFrom<S, T>(List<S> source) => CastList<S, T>(source);
	static void copyRange<T>(List<T> target, int at, List<T> source, [int start, int end]);
	static void writeIterable<T>(List<T> target, int at, Iterable<T> source)

-----------------------------
List - 属性
-----------------------------
	first
	last
	length
	reversed
	hashCode
	isEmpty
	isNotEmpty
	iterator
	runtimeType
	single

-----------------------------
List - 实例方法
-----------------------------
	void add(E e);
	void addAll(Iterable<E> iterable);
	Map<int, E> asMap();
	List<R> cast<R>();
	void clear();
	void fillRange(int start, [ int end, [ E fillValue ]);
	Iterable<E> getRange(int start int end)
	int indexOf(E element, [ int start = 0 ]);
	int indexWhere(bool test(E element), [ int start = 0 ]);
	void insert(int index, E element);
	void insertAll(int index, Iterable<E> iterable);
	int lastIndexOf(E element, [ int start ]);
	int lastIndexWhere(bool test(E element), [ int start ]);
	bool remove(Object value);
	E removeAt(int index);
	E removeLast();
	void removeRange(int start int end);
	void removeWhere(bool test(E element));
	void replaceRange(int start, int end, Iterable<E> replacement);
	void retainWhere(bool test(E element));
	void setAll(int index, Iterable<E> iterable);
	void setRange(int start, int end, Iterable<E> iterable, [ int skipCount = 0 ]);
	void shuffle([Random random ]);
	void sort([int compare(E a E b) ]);
	List<E> sublist(int start, [ int end ]);
	bool any(bool test(E element));
	bool contains(Object element);
	E elementAt(int index);
	bool every(bool test(E element));
	Iterable<T> expand<T>(Iterable<T> f(E element));
	E firstWhere(bool test(E element), { E orElse() });
	T fold<T>(T initialValue, T combine(T previousValue, E element));
	Iterable<E> followedBy(Iterable<E> other);
	void forEach(void f(E element));
	String join([String separator = "" ]);
	E lastWhere(bool test(E element), { E orElse() }) ;
	Iterable<T> map<T>(T f(E e))
	dynamic noSuchMethod(Invocation invocation)
	E reduce(E combine(E value E element))
	E singleWhere(bool test(E element), { E orElse() })
	Iterable<E> skip(int count)
	Iterable<E> skipWhile(bool test(E value))
	Iterable<E> take(int count)
	Iterable<E> takeWhile(bool test(E value))
	List<E> toList({bool growable: true })
	Set<E> toSet()
	String toString() 
	Iterable<E> where(bool test(E element))
	Iterable<T> whereType<T>() 


-----------------------------
List - 运算
-----------------------------
	# 相加, 组成新的List
		  var arr1 = [1,2,3];
		  var arr2 = [4,5,6];
		  var arr3 = arr1 + arr2;
		  arr3.add(7);
		  print(arr3); // [1, 2, 3, 4, 5, 6, 7]
		 
	
	# 比较
		var arr1 = [1,2,3];
		var arr2 = [1,2,3];
		print(arr1 == arr2);

		* == 比较的是俩List对象是否是同一个引用
		* 不能比较List中的元素是否相等
	
	# 读取和赋值
		var arr = [1,2,3];
		arr[1];  // 2
		arr[0] = 4; // [4,2,3]
		print(arr);


		