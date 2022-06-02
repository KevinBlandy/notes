-------------------------
Stream
-------------------------
	
	# ���󷽷�
		Iterator<T> iterator();
		Spliterator<T> spliterator();
		boolean isParallel();
		S sequential();
		S parallel();
		S unordered();
		S onClose(Runnable closeHandler);
		void close();

		Stream<T> filter(Predicate<? super T> predicate);
		<R> Stream<R> map(Function<? super T, ? extends R> mapper);
		IntStream mapToInt(ToIntFunction<? super T> mapper);
		LongStream mapToLong(ToLongFunction<? super T> mapper);
		DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
		<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
		IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
		LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
		DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
		Stream<T> distinct();
		Stream<T> sorted();
		Stream<T> sorted(Comparator<? super T> comparator);
		Stream<T> peek(Consumer<? super T> action);
		Stream<T> limit(long maxSize);
		Stream<T> skip(long n);
		void forEach(Consumer<? super T> action);
		void forEachOrdered(Consumer<? super T> action);
		Object[] toArray();
		<A> A[] toArray(IntFunction<A[]> generator);
		T reduce(T identity, BinaryOperator<T> accumulator);
		Optional<T> reduce(BinaryOperator<T> accumulator);
		<U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
		<R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);
		<R, A> R collect(Collector<? super T, A, R> collector);
		Optional<T> min(Comparator<? super T> comparator);
		Optional<T> max(Comparator<? super T> comparator);
		long count();

		boolean anyMatch(Predicate<? super T> predicate);
		boolean allMatch(Predicate<? super T> predicate);
		boolean noneMatch(Predicate<? super T> predicate);
			* ����ƥ��/����ƥ��/û��ƥ��

		Optional<T> findFirst();
			* ��ȡ��һ��

		Optional<T> findAny();	
			* ��ȡ����һ��

	
	# Ĭ�Ϸ���
		Stream<T> takeWhile(Predicate<? super T> predicate)
		Stream<T> dropWhile(Predicate<? super T> predicate)
		List<T> toList()
			* ֱ��ת��ΪList
				(List<T>) Collections.unmodifiableList(new ArrayList<>(Arrays.asList(this.toArray())));

		<R> Stream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) 
		IntStream mapMultiToInt(BiConsumer<? super T, ? super IntConsumer> mapper)
		LongStream mapMultiToLong(BiConsumer<? super T, ? super LongConsumer> mapper)
		DoubleStream mapMultiToDouble(BiConsumer<? super T, ? super DoubleConsumer> mapper)
			* ������Map���������������consumer���棬ͨ��mapper����N��Ԫ�ص�stream
					List<String> numbers = List.of("1", "2", "3", "4", "5");
					numbers.stream().mapMulti((val, mapper) -> {
						mapper.accept(val);			// ����Ԫ��
						mapper.accept("|");			// ����Ԫ��
					})
					.forEach(System.out::print); // 1|2|3|4|5|
	
	# ��̬����
		static<T> Builder<T> builder()
		static<T> Stream<T> empty()
		static<T> Stream<T> of(T t)
		static<T> Stream<T> ofNullable(T t)
		static<T> Stream<T> of(T... values)
		static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
		static<T> Stream<T> iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)

		static<T> Stream<T> generate(Supplier<? extends T> s)
			* ������	

		static<T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
	


-------------------------
Builder
-------------------------
	# Stream���ڲ��࣬ʵ����Consumer�ӿ�
		void accept(T t);
		Builder<T> add(T t);
		Consumer<T> andThen(Consumer<? super T> after)
		Stream<T> build();
	