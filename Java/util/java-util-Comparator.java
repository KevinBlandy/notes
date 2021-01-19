-------------------------
Comparator<T>			 |
-------------------------
	# 是一个函数接口
	# int compare(Object obj) 
		* 判断对象是否相等,一般作为一些集合框架的比较器

		
	# 默认方法
		Comparator<T> thenComparing(Comparator<? super T> other)
		<U> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator)
		<U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor)
		Comparator<T> thenComparingInt(ToIntFunction<? super T> keyExtractor)
		Comparator<T> thenComparingLong(ToLongFunction<? super T> keyExtractor)
		Comparator<T> thenComparingDouble(ToDoubleFunction<? super T> keyExtractor)


	# 提供的静态实现
		static <T extends Comparable<? super T>> Comparator<T> reverseOrder()
			* 逆序排序

		static <T extends Comparable<? super T>> Comparator<T> naturalOrder()
			* 自然排序

		static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator)
		static <T> Comparator<T> nullsLast(Comparator<? super T> comparator)
			* null 元素排在头还是尾

		static <T, U> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator)
		static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor)
		static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor)
		static <T> Comparator<T> comparingLong(ToLongFunction<? super T> keyExtractor)
		static<T> Comparator<T> comparingDouble(ToDoubleFunction<? super T> keyExtractor)
