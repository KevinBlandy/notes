--------------------
Collector
--------------------
	# 收集器实现

	# 抽象方法
		Supplier<A> supplier();
		BiConsumer<A, T> accumulator();
		BinaryOperator<A> combiner();
		Function<A, R> finisher();
		Set<Characteristics> characteristics();
	
	# 静态方法
		static<T, R> Collector<T, R, R> of(Supplier<R> supplier,
                                              BiConsumer<R, T> accumulator,
                                              BinaryOperator<R> combiner,
                                              Characteristics... characteristics)
		static<T, A, R> Collector<T, A, R> of(Supplier<A> supplier,
                                                 BiConsumer<A, T> accumulator,
                                                 BinaryOperator<A> combiner,
                                                 Function<A, R> finisher,
                                                 Characteristics... characteristics)
	
	# 内部枚举 Characteristics
		CONCURRENT
		UNORDERED
		IDENTITY_FINISH
		

