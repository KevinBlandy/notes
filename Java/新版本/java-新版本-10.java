----------------------------
java10						|
----------------------------
	# 局部变量推断
	# io工具新增api
		Reader
			long transferTo(Writer out)

		InputStream
			long transferTo(OutputStream out) 
		
		ByteArrayOutputStream
			String toString(Charset charset)
		
		PrintWriter
			PrintWriter(OutputStream out, boolean autoFlush, Charset charset) 
		
		Scanner
			Scanner(InputStream source, Charset charset) 
			Scanner(File source, Charset charset)
			Scanner(ReadableByteChannel source, Charset charset)
			Scanner(Path source, Charset charset)
		
		* 大都是新增了关于设置 '字符编码' 和 'transferTo' 的api
	
	# 集合类新增静态方法
		List
			static <E> List<E> copyOf(Collection<? extends E> coll)
		Set
			static <E> Set<E> copyOf(Collection<? extends E> coll)
		Map
			static <K, V> Map<K, V> copyOf(Map<? extends K, ? extends V> map)
		
		
		* 按照参数的迭代顺序, 进行copy
		* 和 of 一样, 返回的新集合, 不能修改
	
	# 流收集方法新增
		Collectors.toUnmodifiableSet()
		Collectors.toUnmodifiableList():
		Collectors.toUnmodifiableMap(Function, Function):
		Collectors.toUnmodifiableMap(Function, Function, BinaryOperator):

		* 把流收集为不可修改的集合

	# 将 JDK 的多个代码仓库合并到一个储存库中
	# 垃圾收集器接口, 通过引入一个干净的垃圾收集器(GC)接口, 改善不同垃圾收集器的源码隔离性
	# 向 G1 引入并行 Full GC
	# 线程局部管控, 允许停止单个线程, 而不是只能启用或停止所有线程
	# 移除 Native-Header Generation Tool (javah)
	# 额外的 Unicode 语言标签扩展。包括：cu (货币类型)、fw (每周第一天为星期几)、rg (区域覆盖)、tz (时区) 等
	# 在备用内存设备上分配堆内存。允许 HotSpot 虚拟机在备用内存设备上分配 Java 对象堆
	# 基于 Java 的 JIT 编译器（试验版本）
	# 根证书。开源 Java SE Root CA 程序中的根证书
	# 基于时间的版本发布模式。“Feature releases” 版本将包含新特性，“Update releases” 版本仅修复 Bug

