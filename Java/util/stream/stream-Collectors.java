-----------------------------
Collectors
-----------------------------
	# 提供了大量的 Collector 实现

	# 静态方法
		static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> collectionFactory)
		static <T> Collector<T, ?, List<T>> toList()
		static <T> Collector<T, ?, List<T>> toUnmodifiableList()
		static <T> Collector<T, ?, Set<T>> toSet()
		static <T> Collector<T, ?, Set<T>> toUnmodifiableSet()
		static Collector<CharSequence, ?, String> joining()
		static Collector<CharSequence, ?, String> joining(CharSequence delimiter)
		static Collector<CharSequence, ?, String> joining(CharSequence delimiter,
                                                             CharSequence prefix,
                                                             CharSequence suffix)
		static <T, U, A, R> Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper, Collector<? super U, A, R> downstream)
		static <T, U, A, R> Collector<T, ?, R> flatMapping(Function<? super T, ? extends Stream<? extends U>> mapper, Collector<? super U, A, R> downstream)
		static <T, A, R> Collector<T, ?, R> filtering(Predicate<? super T> predicate, Collector<? super T, A, R> downstream)
		static<T,A,R,RR> Collector<T,A,RR> collectingAndThen(Collector<T,A,R> downstream, Function<R,RR> finisher)
		static <T> Collector<T, ?, Long> counting()
		static <T> Collector<T, ?, Optional<T>> minBy(Comparator<? super T> comparator)
		static <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator)
		static <T> Collector<T, ?, Integer> summingInt(ToIntFunction<? super T> mapper)
		static <T> Collector<T, ?, Long> summingLong(ToLongFunction<? super T> mapper)
		static <T> Collector<T, ?, Double> summingDouble(ToDoubleFunction<? super T> mapper)
		static <T> Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> mapper)
		static <T> Collector<T, ?, Double> averagingLong(ToLongFunction<? super T> mapper)
		static <T> Collector<T, ?, Double> averagingDouble(ToDoubleFunction<? super T> mapper) 
		static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op)
		static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op)

		static <T, U> Collector<T, ?, U> reducing(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> op)
		static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)
		static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
		static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)
		static <T, K> Collector<T, ?, ConcurrentMap<K, List<T>>> groupingByConcurrent(Function<? super T, ? extends K> classifier)
		static <T, K, A, D> Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
		static <T, K, A, D, M extends ConcurrentMap<K, D>> Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)

		static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate)
		static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream)

		static <T, K, U> Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
		static <T, K, U> Collector<T, ?, Map<K,U>> toUnmodifiableMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
		static <T, K, U> Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction)
		static <T, K, U> Collector<T, ?, Map<K,U>> toUnmodifiableMap(Function<? super T, ? extends K> keyMapper,  Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction)
		static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapFactory)
		static <T, K, U> Collector<T, ?, ConcurrentMap<K,U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
		static <T, K, U> Collector<T, ?, ConcurrentMap<K,U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction)
			* toUnmodifiableMap: 返回的Map不能修改
			* toConcurrentMap: 返回的的是ConcurrentMap
			* 参数
				keyMapper
					* 返回key
				valueMapper
					* 返回value
				mergeFunction
					* KEY出现冲突的时候的处理函数（默认冲突抛出异常）
				mapFactory
					* 返回最终的Map（你的Map你做主）

		static <T, K, U, M extends ConcurrentMap<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapFactory)
		static <T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> mapper)
		static <T> Collector<T, ?, LongSummaryStatistics> summarizingLong(ToLongFunction<? super T> mapper)
		static <T> Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> mapper)

