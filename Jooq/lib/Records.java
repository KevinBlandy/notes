--------------------------------
Records
--------------------------------
	# 操作结果集 Record 的工具类
		public final class Records
	
	public static final <E, R extends Record1<E>> Collector<R, ?, E[]> intoArray(E[] a)
	public static final <E, R extends Record1<E>> Collector<R, ?, E[]> intoArray(Class<? extends E> componentType) 
	public static final <E, R extends Record> Collector<R, ?, E[]> intoArray(E[] a, Function<? super R, ? extends E> function)
	public static final <E, R extends Record> Collector<R, ?, E[]> intoArray(Class<? extends E> componentType, Function<? super R, ? extends E> function)
	public static final <E, R extends Record1<E>> Collector<R, ?, List<E>> intoList()
	public static final <E, R extends Record> Collector<R, ?, List<E>> intoList(Function<? super R, ? extends E> function)
	public static final <E, R extends Record1<E>> Collector<R, ?, Set<E>> intoSet()
	public static final <E, R extends Record> Collector<R, ?, Set<E>> intoSet(Function<? super R, ? extends E> function)
	public static final <K, V, R extends Record2<K, V>> Collector<R, ?, Map<K, V>> intoMap()
	public static final <K, R extends Record> Collector<R, ?, Map<K, R>> intoMap(Function<? super R, ? extends K> keyMapper)
	public static final <K, V, R extends Record> Collector<R, ?, Map<K, V>> intoMap(
        Function<? super R, ? extends K> keyMapper,
        Function<? super R, ? extends V> valueMapper
    )
	public static final <K, V, R extends Record2<K, V>> Collector<R, ?, Map<K, List<V>>> intoGroups()
	public static final <K, R extends Record> Collector<R, ?, Map<K, List<R>>> intoGroups(Function<? super R, ? extends K> keyMapper)
	public static final <K, V, R extends Record> Collector<R, ?, Map<K, List<V>>> intoGroups(
        Function<? super R, ? extends K> keyMapper,
        Function<? super R, ? extends V> valueMapper
    )
	public static final <K, R extends Record> Collector<R, ?, Map<K, Result<R>>> intoResultGroups(Function<? super R, ? extends K> keyMapper)
	public static final <K, V extends Record, R extends Record> Collector<R, ?, Map<K, Result<V>>> intoResultGroups(
        Function<? super R, ? extends K> keyMapper,
        Function<? super R, ? extends V> valueMapper
    )
	public static final <T1, R extends Record1<T1>, U> RecordMapper<R, U> mapping(
        Function1<? super T1, ? extends U> function
    )
	public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, U> RecordMapper<R, U> mapping(
        Function22<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? super T21, ? super T22, ? extends U> function
    )
