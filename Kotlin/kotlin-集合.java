-----------------
集合			 | 
-----------------
	# 创建集合的函数
		var list = listOf(1,2,3,4)								java.util.Arrays$ArrayList
			* Arrasys.asList()
			* 只读

		var list = arrayListOf(1,2,3,4);						ArrayList

		var set = setOf()
			* Collections.singleton()							java.util.Collections$SingletonSet
			* 只读

		var set = hashSetOf(1,2,3,4);							HashSet
	
		<A,B> mapOf(pairs:Pair<A,B>)							LinkedHashMap
			* 只读

		var map = hashMapOf(1 to "1", 2 to "2", 3 to "3")		HashMap
	
	# list/set/map 支持的一些操作(扩展函数)
		last();
		min();
		max();
	
		withIndex(): Iterable<IndexedValue<T>>
			* list 数据结构的一个api
			* 返回一个迭代器, 迭代对象包含了下标和值
				public data class IndexedValue<out T>(public val index: Int, public val value: T)
		
		filter()
			* 过滤, 返回 true 的将会被留下
				arrayListOf(1,2,3,4,5).filter {it % 2 == 0} // [2, 4]
			* 如果执行对象是 map, 那么参数就是一个: entry
				mapOf("1" to "2").filter { it -> it.key == it.value }
				mapOf("1" to "2").filter { (k,v) -> k == v}
		
		filterValues()
		filterKeys()
			* map的key 和 value 过滤器
		
		filterNotNull()
			* 过滤掉所有空null元素
			* 返回的列表不存在 null 元素
		
		mapValues()
		mapKeys()
			* map 结构的消费函数
				mapOf("1" to "2").mapKeys { println("${it.key},${it.value}") }
				mapOf("1" to "2").mapValues { println("${it.key},${it.value}") }
		
		map()
			* 一个消费函数 ,py/java都有
				arrayListOf(1,2,3,4,5).map {it * 2} // [2, 4, 6, 8, 10]
			
		
		all()
		anly()
			* 判断函数, 返回 boolean
			* 如果所有都符合条件/或者是任何一个符合条件, 返沪 true
			

		find()
			* 返回匹配成功的第一个元素
				listOf(1,4,3,4).find {it % 2 ==0} // 4
		
		count()
			* 统计 ,它也支持过滤, 返回符合条件的数量
				listOf(1,2,3,4).count {it % 2 ==0} // 2
			
		groupBy()
			* 聚合,返回的结果是一个 map<?,List<?>>, 跟 java 的stream一样
			* 把处理结果一样的数据放到一个集合,处理的结果作为key
				var result = listOf("a","bb","ccc","d","ee","fff").groupBy { it.length }
				println(result) // {1=[a, d], 2=[bb, ee], 3=[ccc, fff]}
		
		flatMap()
			* 把结果合并为一个流
			* 先把每个元素做变换, 然后再合并为一个流
				arrayOf(
				Book("Java编程思想", arrayListOf("Kevin","Litch")),
				Book("Python编程思想", arrayListOf("Ruby","xjp")),
				Book("Javascript编程思想", arrayListOf("Zy","Litch")),
				Book("C编程思想", arrayListOf("Kevin","Rocco")))

				.flatMap { it.authors }.forEach {println(it)} // 把所有的作者信息, 都组合成了一个流
		
		
		forEachIndex()
			* 带下标的遍历, 参数是一个 lambda, 有两个参数: index, element
				intArray.forEachIndexed { index, element -> println("$index, $element") }
		

		* 这些函数之间可以链式调用, 跟 Java8的 Stream 一样
	
	# 更为高效的 asSequence 
		* 一个有问题的代码
			arrayOf("1","2").map { it.length }.filter { it >= 1 }

			* 执行到 map 的时候, 会创建一个数组
			* 执行到 filer 的时候, 会创建一个数组

			* 问题在于, 每一次执行都会创建新的数组,如果操作过多, 那么严重影响性能
		
		* 可以先把需要操作的元素序列化, 使用方法: asSequence() ,在最后收集结果
			arrayOf("1","2").asSequence().map { it.length }.filter { it >= 1 }.toList()

			* 在中间不会创建任何的集合, 只有在最后收集的时候才会创建
			* 这个就跟Java的Stream一摸一样的, 如果最后没有执行收集操作, 那么中间的流处理也不会执行
			
		
		* asSequence 就是 Java 的 .stream()
		* 这是一个集合类的扩展函数, 这种东西也被成为惰性求值
	
	# 创建 sequence
		* 之前的 sequence 都是通过集合的 asSequence 来获取,
		* 也可以自己去创建, 类似于py的生成器
			var sequence = generateSequence(0) {it + 1}
			var result = sequence.takeWhile { it <= 100 }.sum()
			println(result) // 5050
			
			* generateSequence() 给定一个开始元素, 以及对元素的操作lamdba
		


	
	# 其他的一些相关函数
		var pari = Pair(v1,v2)
			* 返回一个 pair 对象,一般用于构造 Map 的一个映射
			* 这个对象就俩属性(都是泛型)
				public val first: A
				public val second: B
			
			* 该对象可以被解构赋值
				var (key, value) = Pair("name", "KevinBlandy")
				println("key=$key, value=$value")

	# Kotlin 和  Java 的集合关系
		Iterable	MutableIterable	  : Iterable<T> 
		Collection	MutableCollection : Collection<E>, MutableIterable<E>
		List		MutableList		  : List<E>, MutableCollection<E> 
		Set			MutableSet		  : Set<E>, MutableCollection<E> 

		ArrayList
		HashSet
		
		* 使用  Mutable... 开头的表示可以修改数据的的接口
		* Java类 ArrayList,HashSet 都继承了 Kotlin的可变接口
	
	# 集合创建函数
		+----------------------------------------------------------------------------------------------
		|集合类型	不可变			可变
		|List		listOf			mutableListOf(),arrayListOf()
		|Set		setOf			mutableSetOf(),hashSetOf(),linkedSetOf(),sortedSetOf()
		|Map		mapOf			mutableMapOf(),hashMapOf(),linkedMapOf(),sortedMapOf()
		+----------------------------------------------------------------------------------------------


---------------------
可变集合和只读集合	 | 
---------------------
	# 只读集合接口
		kotlin.collections.Collection
	
	# 可变集合接口(可以添加修改)
		kotlin.collections.MutableCollection

		* 它继承自接口:kotlin.collections.Collection
		* 添加了 add / remove 等方法
	
	# 把集合转换为数组
		toTypedArray(): Array<T>
	
	
	# 基本数据类型的集合
		* 为了表示基本数据类型的数组, Kotlin 提供了N多个独立的类
		* 每种数据类型都对应一个
			IntArray
			BooleanArray
			...
		
		* 这些都会被编译为: int[], char[].... 
		
		* 直接创建, 一般在参数指定数组的长度
		* 因为是基本数据类型, 所以有默认值
			IntArray(size: Int)

		* 构造函数还可以添加一个 lambda 参数, 用于初始化数组成员
		* 会把每个角标, 传递给诶 lambda, 并且把返回值当作数组的元素
			var arr = IntArray(10){i -> i + 1}
			println(arr.joinToString(",")) // 1,2,3,4,5,6,7,8,9,10
					
		
		* 使用工厂函数创建
			intArrayOf(1,2,3,4,6)
		
		
		* 包装类型的数据集合, 可以通过 toXxxArray(), 转换为基本数据类型的集合
		    var array:ArrayList<Int> = arrayListOf(1,2,3)
			var intArray = array.toIntArray()
		
---------------------
数组				 | 
---------------------
	# Kotlin的 Array 就是 数组
		* 支持使用下标来访问元元素
			var list:List<String> = arrayListOf("1","3")
			var value = list[1]

			* 越界会有异常
		
	
	# Array 的构造方法, 可以接收一个数组的大小, 和 lambda 表达式
		* 数组会使用 lmabda 初始化这个数组, 参数就是当前的角标
			var letters = Array(10) { i:Int -> (i + 1).toString()}
			println(letters.joinToString(",")) // 1,2,3,4,5,6,7,8,9,10
