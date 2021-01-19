----------------------------
JAVA8新特性-Stream接口		|
----------------------------
	* Stream 接口(果然是新东西,连Editplus都没标红提示)
	* 它提供串行和并行两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势，使用 fork/join 并行方式来拆分任务和加速处理过程。
	* 通常编写并行代码很难而且容易出错, 但使用 Stream API 无需编写一行多线程的代码，就可以很方便地写出高性能的并发程序
	* Stream 的另外一大特点是，数据源本身可以是无限的。
	* 它并非是数据结构,也不是容器.而是一个过程
	* 流的操作分为两种
	Intermediate 
		* 一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy）
		* 就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
		* 方法
			map (mapToInt, flatMap 等)、 
			filter 
			distinct
			sorted
			peek
			limit
			skip
			parallel
			sequential
			unordered
	Terminal
		* 一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。
		* Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
		* 方法
			forEach
			forEachOrdered
			toArray
			reduce 
			collect
			min
			max
			count
			anyMatch
			allMatch
			noneMatch
			findFirst
			findAny
			iterator
	
	* 如果一个流的操作,没有 Terminal ,则该流中的任何 Intermediate 操作都不会执行

----------------------------
JAVA8新特性-Stream的获得	|
----------------------------
	1,Collection 子类创建
		* 子类直接调用 stream();方法(常用)
		* 子类直接调用 parallelStream();方法,定义在 Collection 接口中的 default 方法

	2,数组创建
		*  Arrays.stream(T[] array);

	4,Stream 静态方法创建
		* Stream<Integer> integerStream = Stream.of(1,2,3,4,5,6);

	5,文件IO创建
		* Stream<String> stream = Files.lines(String path,Charset charser);
		* 该流会自动关闭
		
	# 创建无限流的方法
		* Stream<T> str = Stream.generate(Supplier<T> supplier);
			* 该流每次执行 foreach 都会调用 supplier 的 get 方法,也就是说该构建方法可以创建无限大的流

		* Stream.iterate(1, item -> item + 1).limit(10).forEach(System.out::println);
			* 先获取一个无限长度的正整数集合的Stream，然后取出前10个打印。千万记住使用limit方法，不然会无限打印下去。
	
----------------------------
JAVA8新特性-Stream接口方法	|
----------------------------
	parallel();
		* 启用并行流

	filter			(Predicate<? super T> predicate);
		* 用于过滤,筛选

	map				(Function<? super T, ? extends R> mapper);
		* 对于Stream中包含的元素使用给定的转换函数进行转换操作,把所有的返回值,组成新的流
		* '传入个User对象(User流),提取里面的所有name属性,转换为一个Stream的流'
		* 新生成的Stream只包含转换生成的元素。
	
	flatMap			(Function<? super T, ? extends Stream<? extends R>> mapper);
		* 传入的就是流,
		* 和map类似，不同的是传入的元素,就是一个流
		* 说白了,就是传入的每一个流,然后合并起来成为一个流,返回
		* 注意参数对象 Function 的返回值是 Stream

	mapToInt
	mapToLong
	mapToDouble
		* 这三个都是一样的,计算后的新的流是指定值类型(IntStream,LongStream,DoubleStream)可以免除自动装箱/拆箱的额外消耗；

	sorted			();
		* 也是排序,需要元素自身实现 Comparable 接口
	sorted			(Comparator<? super T> comparator);
		* 排序,自定义 Comparator 来完成排序

	peek			(Consumer<? super T> action);
		* 生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），新Stream每个元素被消费的时候都会执行给定的消费函数；
		* 跟 map 的区别就是,map是创建新的返回,这个不返回直接在原来的上改

	limit			(long maxSize);
		* 返回流前面的几个元素,如果原Stream中包含的元素个数小于N，那就获取其所有的元素
		* 也可以用在 Set 集合的流上,那么截取到的流,是无序的

	skip			(long n);
		* 丢弃(跳过)流前面的几个元素,如果原Stream中包含的元素个数小于N，那么返回空Stream；

	forEach			(Consumer<? super T> action);
		* 遍历,在多核情况下也许会失去顺序
		* 如果你需要多核遍历,那么需要调用 arr.parallelStream().forEach(System.out::println);  'Collection' 接口的默认方法

	forEachOrdered	(Consumer<? super T> action);
		* 同上,但是,'在多核情况下顺序不会有影响'
		
	reduce			(T identity, BinaryOperator<T> accumulator);
		* 第一个参数是初始值,后面 Lambda传递俩值.表示要进行啥操作,得到啥结果
		* int sum = numbers.stream().reduce(0,(a,b) -> {a + b});			//求一组集合中所有数字的和
		* 如果是 parallelStream(); 并行流调用执行的话,传递给 Lambda 表达式 accumulator 的俩变量不能更改状态,而且操作必须满足结合律才可以按任意顺序执行
	
	reduce			();
		* 上面的重载,无初始值
		* 返回 Optional<T>,因没初始值.怕流是空的
		* Optional<Integer> max = numbers.stream().reduce(Integer::max);					//返回数字流中的最大值

	toArray			();
		* 返回 Object[] 数组
	
	findFirst		();
		* 这是一个 termimal 兼 short-circuiting 操作，它总是返回 Stream 的第一个元素，或者空。
	
	findAny
		* 同上,也是返回第一个元素.但是这个牛逼之处在于,'处理并行'.
		* 如果你不介意返回的元素是哪个,可以使用这个

	distinct		();
		* 去除重复,会依赖hashCode和equals
	
	count			();
		* 返回流中元素的个数
	
	min(Comparator com);		
		* 根据比较器,获取最的元素

	max(Comparator com);		
		* 根据比较器,获取最大的元素

	collect(Collectors.toList());
		* 把结果,转换为一个集合
		* 接收一个 Collectors 接口的实现,用于把元素中的内容,进行一个汇总
	
	
	//匹配系列的API
	allMatch
		* 检查是否匹配所有元素,返回 boolean
	anyMatch
		* 检查是否至少匹配一个元素 boolean
	noneMath
		* 检查是否未匹配中任何元素 boolean
	findFirst
		* 返回第一个元素
		* 返回是的类型是 Optional
	findAny
		* 返回当前流中的任意元素
		* 返回是的类型是 Optional
	
----------------------------
JAVA8新特性-基本数据类型的	|
----------------------------
	* 对于基本数值型，目前有三种对应的包装类型 Stream：
		IntStream
		LongStream
		DoubleStream
	* 当然我们也可以用 Stream<Integer>、Stream<Long> >、Stream<Double>，但是拆箱和装箱会很耗时，所以特别为这三种基本数值型提供了对应的 Stream。
	
	* 额外方法
		min();		//获取流中的最小值
		max();		//获取流中的最大值
		sum();		//和值
		average();	//平均值
		boxed();	//转换会 Stream<T> 流

----------------------------
JAVA8新特性-无限流			|
----------------------------
	# 迭代
		Stream.iterate(0, n -> n+2);
		* 0 表示初始值,然后不断的 +2
		* 一般来说说,需要依次生成一系列值的时候就应该用这个
	
	# 生成
		Stream<T> str = Stream.generate(Supplier<T> supplier);
		* 该流每次执行 foreach 都会调用 supplier 的 get 方法,也就是说该构建方法可以创建无限大的流

----------------------------
JAVA8新特性-流的状态		|
----------------------------
	# 无状态
	# 有状态 -> 有界
	# 有状态 -> 无界
	
	
----------------------------
JAVA8新特性-并行流			|
----------------------------
	# 多线程的流
		* 通过 parallelStream(); 可以获取多线程的流
			list.parallelStream().forEach(System.out::println);			//多线程打印,顺序会有影响
		* 流直接调用 parallel(); 方法
			Stream<String> stream = Stream.of("1","2","4").parallel();
	
	# 关闭并行流
		sequential();

----------------------------
JAVA8新特性-总结			|
----------------------------
	# 流的创建,无限流的创建
		* list.stream();		//Collection 方法
		* Stream.of(1,2,3,4,5,6);
		* Stream.generate(Supplier<T> supplier);
		* Arrays.stream(T[] array);

	# 流的操作方式,两种
		* 过程
		* 结果
	
	# 多线程的流
		* 通过 parallelStream(); 可以获取多线程的流
			list.parallelStream().forEach(System.out::println);			//多线程打印,顺序会有影响
		* 流直接调用 parallel(); 方法
			Stream<String> stream = Stream.of("1","2","4").parallel();
	
	# 无限流
		


----------------------------
JAVA8新特性-collector		|
----------------------------
	# 是一个接口,收集器,在流中使用API:collect(Collector collecto); 
	# 预定义的收集器
	Collectors.toList()
		* 把结果转换为一个集合
	Collectors.toSet();
		* 把结果转换为一个Set
	Collectors.toCollection(Supplier collectionFactory);
		* 转换为自定义的 Collection 接口实现
		* 就是可以使用自己创建容器
		* collect(Collectors.toCollection(HashSet::new));
	Collectors.counting();
		* 返回 lang,把结果收集为一个总数
	Collectors.averagingInt();
	Collectors.averagingLong();
	Collectors.averagingDouble();
		* 以上三,都是不同数据类型的平均值
	Collectors.summarizingInt();
	Collectors.summarizingLong();
	Collectors.summarizingDouble();
		* 以上三,都是不同数据类型的和
		* 返回
	
	Collectors.maxBy(Comparator<? super T> comparator);
		* 根据比较器,获取最大值
	
	Collectors.minBy(Comparator<? super T> comparator);
		* 根据比较器,获取最小值
	
	Collectors.groupingBy(Function<? super T, ? extends K> classifier);
		* 分组,返回 Map,Lambda的返回值,会作为Map的key
		* Demo
		 Map<String,List<User>> = users.stream().collect(Collectors.groupingBy(item -> {
			return user.getAge() > 20 ? "中年" : "青年";
		 }));
	
	Collectors.groupingBy(Function function,Collector collector);
		* 先按照 Function 的返回值进行分组
		* 再把分组的结果,进行 collector 收集操作
		* Demo	//先按性别分组,再按年龄分组
			Map<String,Map<String,List<User>>> = users.stream().collect(
			Collectors.groupingBy(User:getSex,	Collectors.groupingBy((user) - > {
				return user.getAge() > 20 ? "中年" : "青年";
			}));
	
	Collectors.joining();
		* 连接操作,字符串连接.没有任何中间符号
		* Demo
			String namesss = users.stream()
				.map(User:getName);
				.collect(Collectors.joining());

	Collectors.joining(String a,String b,String c);
		* 同上,不过会在连接的开头加上 b,结尾加上 c,每个连接点之间加上 a

		
			
		



	