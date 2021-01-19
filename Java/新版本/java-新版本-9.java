----------------------------
java9						|
----------------------------
	# 2017-9 月发布
	# 比较重要的新特性

	# 模块化系统
	# jshell
	# 多版本兼容jar
	# 接口的私有方法
	# 钻石操作符的使用升级
		* Java8环境
			Set<String> hashSet = new HashSet<>() {};		// 异常
			Set<String> hashSet = new HashSet<String>() {};	// 正常
		* Java9
			Set<String> hashSet = new HashSet<>() {};		// 正常

	# 语法改进:try 语句
		* 支持在 try() 中直接声明对象
			InputStream foo1 = new FileInputStream("");
			InputStream foo2 = new FileInputStream("");
			try (foo1;foo2){
				
			}
		
		* 如果有多个, 使用分号分隔
		* 里面声明的对象, 必须是 final 的, 不能修改

	# 下划线使用限制
		* java8支持使用 _ 作为标识符, java9会异常
			int _ = 15;
		
	# String 存储结构变更
		* jdk9以后, 底层使用字节数组, 来存储字符串
			private final byte[] value;
		
		* jdk8及其以前是 char[]
		* 一个 char, 占用了俩字节, 使用 byte 后, 会节约内存空间

		* 包括 StringBuilder/StringBuffer 都做了相同的改变
			

	# 便利的集合特性:of
		Set<String> set = Set.of("1", "2", "2");
		List<Integer> list = List.of(1, 2, 3);
		Map<String, String> map1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
		Map<String, String> map2 = Map.ofEntries(Map.entry("k1", "v1"), Map.entry("k2", "v2"));

		* 这种方式创建出来集合是只读的

	# 增强的Stream API()
		* 添加了4个实例方法
			Stream<T> dropWhile(Predicate<? super T> predicate)
				* 从Stream中依次删除满足条件的元素, 直到不满足条件为止结束删除
				
				IntStream.of(12, 4, 3, 6, 8, 9).dropWhile(x -> x % 2 == 0).forEach(System.out::print);
				// 3, 6, 8, 9


			Stream<T> takeWhile(Predicate<? super T> predicate)
				* 从Stream中依次获取满足条件的元素，直到不满足条件为止结束获取

				IntStream.of(12, 4, 3, 6, 8, 9).takeWhile(x -> x % 2 == 0).forEach(System.out::print);
				// 12
				
		
		* 添加了2个静态方法
			static<T> Stream<T> ofNullable(T t)
				* 如果参数是 null,  返回空的流, 不会异常
			
			static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
			static<T> Stream<T> iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)
				* 第一次生成的元素是UnaryOperator对seed执行apply后的返回值
				* 之后所有生成的元素都是UnaryOperator对上一个apply的返回值再执行apply, 不断循环

				seed
					* 种子
				
				hasNext
					* 终止的条件

				UnaryOperator 
					* 一元运算符
					* 继承了 Function<T, T> 
					* 提供唯一的一个唯静态方法
						static <T> UnaryOperator<T> identity() {
							return t -> t;
						}

	# Optional 新增
		* 添加了一些, 没啥用的方法
		
	# 多分辨率图形API
	# 全新的 HTTP 客户端 API
	# Deprecated 相关API
		* 主要删除了 applet 相关的一些api

	# 智能Java编译工具
	# 统一jvm日志系统
	# javadoc的HTML5支持
	# javascript 引擎升级: Nashorn
	# Java的动态编译
	
	# 参考资料
		https://www.ibm.com/developerworks/cn/java/the-new-features-of-Java-9/index.html
	
	
	# JDK的目录变化
		java
		|-bin			可执行文件
		|-conf			配置
		|-include		c/c++ 头文件
		|-jmods			模块（把原来的一个rt.jar拆分为不同的模块.jar）
		|-legal			法律声明
		|-lib			非windows平台的动态链接库
		|-release

	
	# 总的来说, Java9是一个比较大的更新