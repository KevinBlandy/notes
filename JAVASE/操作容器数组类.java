
操作容器的工具类

----------------------------	Collections
	|--这个是工具，跟 Collection(集合框架) 不一样！它里面的所有方法都是静态的
	不需要 new 对象！它是专门用于——对集合 Collection 进行操作的类。
Collections.sort(List<E>); ——'排序'
	|--对指定的集合(List 类型)进行排序操作(自然顺序)。无返回值类型.
	其实可以这么去理解它！ 有时候我们需要存储相同的元素。这时候就必须用到 List 这类容器。
	但是又涉及到排序操作的时候。就可以用这个 Collection 的方法来对容器进行操作。既然要排序
	而我们又不选择 Set 是因为我们要求的，要存储相同元素又跟 Set 类集合框架的特性相冲突。所以，JAVA
	生产了一个类出来。专门就是用于对 List 类型的容器进行排序等一系列的操作！
Collections.sort(List,Comparator);'排序'
	|--根据比较器的顺序来对指定容器进行排序。
Collections.max(List);
	|--返回 指定 List 容器里面默认排序。最大值的一个。返回类型就是该元素的类型。
Collections.max(List,Comparator);
	|--返回 指定 List 容器里面，根据比较器规则最大值的一个元素。
Collections.binarySearch(List,Key,Comparator);
	|--对指定集合 List 中的 Key 元素。进行二分查找(二分条件是，必须是一个有序集合).Comparator 是一个比较器。定义集合排序的规则。
		返回值是该元素在 List 里面的角标(int)。如果元素不存在！则返回。插入点 -1 。
Collections.fill(List,Key);
	|--把 List 里面的所有元素全部替换成 Key 元素。是全部。
Collections.replaceAll(List,老值,新值)
	|--把 List 中的老值。替换成新值！
Collections.reverce(List);
	|--反转元素列表。无返回值类型。直接翻转。
Collections.reverceOrder(List);
	|--返回的是 List 的(默认顺序)比较器。强行，逆转了它！倒序！这个跟上面那个差不多。 new TreeSet<String>(Collections.reverceOrder());
Collections.reverceOrder(比较器);
	|--跟上面的方法重载了。返回的是一个比较器。把比较器的排序规则进行逆反后返回。
Collections.synchronizedList(List);
	|--返回的还是一个 List 线程安全的对象，可以被多线程同时访问。
Collections.synchronizedMap(Map);
	|--同上，只是接收的对象和返回的对象不同而已。
Collections.swap(List,int x,int y);
	|--把 List 容器中的x角标的元素 和y角标的元素。进行交换 
Collections.shuffle(List)
	|--把 List 容器中的元素位置进行随机置换。
Collections.shuffle(List,Random)
	|--把 List 容器中的元素位置按照 Random 规则进行随机置换。
-----------------------------	Arrays
用于操作数组的工具类。里面都是静态方法
Arrays.binarySearch(al[],Key);
	|--通过二分查找法(被查找的数组一定要是有序的数组)。来找到 Key 值所在的角标。返回 int 如果没查到。就返回插入点 -1 。
Arrays.deepEquals(al1[],al2[]);
	|--比较两个数组是否相等。返回 boolean 类！
Arrays.fill(al[],x);   --包含很多重载方法，替换指定值，替换指定长度的值
	|--把x的值分配给al中的每个元素。
Arrays.sort(al[], int x,int y);
	|--对al数组中x角标到y角标的元素进行排序。
Arrays.toString(al[]);
	|--把al变成字符串！
Arrays.asList(al[]);
	|--将指定数组变成 List 集合。
	'注意'这个方法好处就是，可以使用集合的思想或者方法来操作集合。
	但是，不能使用集合的增删方法。因为数组的长度是固定的。如果有增删操作。那么会发生'不支持操作异常'。
	如果 al[] 中的元素是对象。也就是引用型数据。那么在转换成 List 的时候。会直接把al[]数组中的元素转换成 List 的元素
	如果 al[] 中的元素是基本数据类型，那么在转换成 List 的时候。是吧 al[] 整个。作为元素存入 List 。
这个类中包含了。复制，查找，排序，比较等多个方法。参阅API。
-------------------------------------------	'!!!!'
注意混淆
Connection ——操作数据库的类。属于JDBC
Collection ——集合容器类。
Collections ——操作容器的类。
Comparable ——类实现此接口。让自身对象具备可比性
	|--compareTo();
Comparator ——类实现此接口。所创建出来的对象。可以当做。集合容器 TreeMap , TreeSet 的构造参数。
	来让这个集合容器。本身就具备了比较性。就把比较的规则不定义在类上。而直接定义在容器上。类似于刻度尺思维。
	|--compare();
	|--equals();