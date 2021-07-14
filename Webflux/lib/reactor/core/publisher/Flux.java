--------------------------
Flux
--------------------------
	# 发布者抽象类
		public abstract class Flux<T> implements CorePublisher<T> 
	
	# 基本
		* 返回N个元素
	
--------------------------
static
--------------------------
	
	public static <T> Flux<T> empty()

	public static <T> Flux<T> just(T... data)
	public static <T> Flux<T> just(T data)

	public static <T> Flux<T> from(Publisher<? extends T> source)
	public static <T> Flux<T> fromArray(T[] array)
	public static <T> Flux<T> fromIterable(Iterable<? extends T> it)
	public static <T> Flux<T> fromStream(Stream<? extends T> s)
	public static <T> Flux<T> fromStream(Supplier<Stream<? extends T>> streamSupplier)

	public static <T> Flux<T> generate(Consumer<SynchronousSink<T>> generator) 
	public static <T, S> Flux<T> generate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator)
	public static <T, S> Flux<T> generate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator, Consumer<? super S> stateConsumer)

	public static Flux<Integer> range(int start, int count) 
	
	public static <T> Flux<T> error(Throwable error)
	public static <T> Flux<T> error(Supplier<? extends Throwable> errorSupplier)
	public static <O> Flux<O> error(Throwable throwable, boolean whenRequested)


	
--------------------------
this
--------------------------
	public final Disposable subscribe()
	public final Disposable subscribe(Consumer<? super T> consumer)
	public final Disposable subscribe(@Nullable Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer)
	public final Disposable subscribe(
			@Nullable Consumer<? super T> consumer,
			@Nullable Consumer<? super Throwable> errorConsumer,
			@Nullable Runnable completeConsumer)
	public final Disposable subscribe(
			@Nullable Consumer<? super T> consumer,
			@Nullable Consumer<? super Throwable> errorConsumer,
			@Nullable Runnable completeConsumer,
			@Nullable Consumer<? super Subscription> subscriptionConsumer)
	public final Disposable subscribe(
			@Nullable Consumer<? super T> consumer,
			@Nullable Consumer<? super Throwable> errorConsumer,
			@Nullable Runnable completeConsumer,
			@Nullable Context initialContext)
	public final void subscribe(Subscriber<? super T> actual) 
	public abstract void subscribe(CoreSubscriber<? super T> actual)
		
		* 和订阅者创建关系，开始订阅

	
	public final Flux<T> doFinally(Consumer<SignalType> onFinally)
	public final Flux<T> doOnSubscribe(Consumer<? super Subscription> onSubscribe)
	public final Flux<T> doAfterTerminate(Runnable afterTerminate)
	public final Flux<T> doOnNext(Consumer<? super T> onNext)
	public final Flux<T> doFirst(Runnable onFirst)
	public final Flux<T> doOnCancel(Runnable onCancel)
	public final Flux<T> doOnComplete(Runnable onComplete)
	public final <R> Flux<T> doOnDiscard(final Class<R> type, final Consumer<? super R> discardHook)
	public final Flux<T> doOnEach(Consumer<? super Signal<T>> signalConsumer)
	public final Flux<T> doOnError(Consumer<? super Throwable> onError) 
	public final <E extends Throwable> Flux<T> doOnError(Class<E> exceptionType, final Consumer<? super E> onError)
	public final Flux<T> doOnError(Predicate<? super Throwable> predicate, final Consumer<? super Throwable> onError)
	public final Flux<T> doOnRequest(LongConsumer consumer)
	public final Flux<T> doOnTerminate(Runnable onTerminate)

	public final T blockFirst()
	public final T blockFirst(Duration timeout)
	public final T blockLast()
	public final T blockLast(Duration timeout)

	public final <V> Flux<V> map(Function<? super T, ? extends V> mapper)
		* 对数据进行处理，返回新的数据组成流

	public final <V> Flux<V> mapNotNull(Function <? super T, ? extends V> mapper)

	public final <R> Flux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper)
	public final <V> Flux<V> flatMap(Function<? super T, ? extends Publisher<? extends V>> mapper, int concurrency)
	public final <V> Flux<V> flatMap(Function<? super T, ? extends Publisher<? extends V>> mapper, int concurrency, int prefetch)
	public final <R> Flux<R> flatMap(
			@Nullable Function<? super T, ? extends Publisher<? extends R>> mapperOnNext,
			@Nullable Function<? super Throwable, ? extends Publisher<? extends R>> mapperOnError,
			@Nullable Supplier<? extends Publisher<? extends R>> mapperOnComplete)
		* 跟Map类似，返回的是Publisher类型
	
	public final <V> Flux<V> flatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> mapper, int concurrency, int prefetch)
	public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper)
	public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch)
	public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends
			Publisher<? extends R>> mapper)
	public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends
			Publisher<? extends R>> mapper, int maxConcurrency)
	public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends
			Publisher<? extends R>> mapper, int maxConcurrency, int prefetch)
	public final <R> Flux<R> flatMapSequentialDelayError(Function<? super T, ? extends
			Publisher<? extends R>> mapper, int maxConcurrency, int prefetch)
	public final <K> Mono<Map<K, T>> collectMap(Function<? super T, ? extends K> keyExtractor)
	public final <K, V> Mono<Map<K, V>> collectMap(Function<? super T, ? extends K> keyExtractor,
			Function<? super T, ? extends V> valueExtractor)
	public final <K, V> Mono<Map<K, V>> collectMap(
			final Function<? super T, ? extends K> keyExtractor,
			final Function<? super T, ? extends V> valueExtractor,
			Supplier<Map<K, V>> mapSupplier)
	public final <K> Mono<Map<K, Collection<T>>> collectMultimap(Function<? super T, ? extends K> keyExtractor)
	public final <K, V> Mono<Map<K, Collection<V>>> collectMultimap(Function<? super T, ? extends K> keyExtractor,
			Function<? super T, ? extends V> valueExtractor)
	public final <K, V> Mono<Map<K, Collection<V>>> collectMultimap(
			final Function<? super T, ? extends K> keyExtractor,
			final Function<? super T, ? extends V> valueExtractor,
			Supplier<Map<K, Collection<V>>> mapSupplier)
	public final Flux<T> onErrorMap(Function<? super Throwable, ? extends Throwable> mapper)
	public final <E extends Throwable> Flux<T> onErrorMap(Class<E> type,
			Function<? super E, ? extends Throwable> mapper)
	public final Flux<T> onErrorMap(Predicate<? super Throwable> predicate,
			Function<? super Throwable, ? extends Throwable> mapper)
	public final <V> Flux<V> switchMap(Function<? super T, Publisher<? extends V>> fn) 
	public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> mapper)
	public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<?
			extends V>> mapper, int prefetch)
	public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<?
			extends V>> mapper, boolean delayUntilEnd, int prefetch)
	public final <R> Flux<R> concatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper)
	public final <R> Flux<R> concatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper,
			int prefetch)
	public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper)
	public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch)
	public final <R> Flux<R> flatMapSequentialDelayError(Function<? super T, ? extends
			Publisher<? extends R>> mapper, int maxConcurrency, int prefetch)

	public final Mono<Void> then()
	public final <V> Mono<V> then(Mono<V> other)
	public final Mono<Void> thenEmpty(Publisher<Void> other)
	public final <V> Flux<V> thenMany(Publisher<V> other)

	public final Flux<Timed<T>> timed()

	public final <E> Mono<E> collect(Supplier<E> containerSupplier, BiConsumer<E, ? super T> collector) 
	public final <R, A> Mono<R> collect(Collector<? super T, A, ? extends R> collector)
	public final <K> Mono<Map<K, T>> collectMap(Function<? super T, ? extends K> keyExtractor)
	public final <K, V> Mono<Map<K, V>> collectMap(Function<? super T, ? extends K> keyExtractor,
			Function<? super T, ? extends V> valueExtractor)
	public final <K, V> Mono<Map<K, V>> collectMap(
			final Function<? super T, ? extends K> keyExtractor,
			final Function<? super T, ? extends V> valueExtractor,
			Supplier<Map<K, V>> mapSupplier)
	public final <K> Mono<Map<K, Collection<T>>> collectMultimap(Function<? super T, ? extends K> keyExtractor) 
	public final <K, V> Mono<Map<K, Collection<V>>> collectMultimap(Function<? super T, ? extends K> keyExtractor,
			Function<? super T, ? extends V> valueExtractor)
	public final <K, V> Mono<Map<K, Collection<V>>> collectMultimap(
			final Function<? super T, ? extends K> keyExtractor,
			final Function<? super T, ? extends V> valueExtractor,
			Supplier<Map<K, Collection<V>>> mapSupplier)
	public final Mono<List<T>> collectList()
	public final Mono<List<T>> collectSortedList()
	public final Mono<List<T>> collectSortedList(@Nullable Comparator<? super T> comparator)

	public final <V> Flux<V> concatMap(Function<? super T, ? extends Publisher<? extends V>>
			mapper)
	public final <V> Flux<V> concatMap(Function<? super T, ? extends Publisher<? extends V>>
			mapper, int prefetch)
	public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> mapper) 
	public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<?
			extends V>> mapper, int prefetch)
	public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<?
			extends V>> mapper, boolean delayUntilEnd, int prefetch)
	public final <R> Flux<R> concatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper)
	public final <R> Flux<R> concatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper,
			int prefetch)
	

	public final <T2> Flux<Tuple2<T, T2>> zipWith(Publisher<? extends T2> source2)
	public final <T2, V> Flux<V> zipWith(Publisher<? extends T2> source2,
			final BiFunction<? super T, ? super T2, ? extends V> combinator)
	public final <T2, V> Flux<V> zipWith(Publisher<? extends T2> source2,
			int prefetch,
			BiFunction<? super T, ? super T2, ? extends V> combinator)
	public final <T2> Flux<Tuple2<T, T2>> zipWith(Publisher<? extends T2> source2, int prefetch)
	public final <T2> Flux<Tuple2<T, T2>> zipWithIterable(Iterable<? extends T2> iterable)
	public final <T2, V> Flux<V> zipWithIterable(Iterable<? extends T2> iterable,
			BiFunction<? super T, ? super T2, ? extends V> zipper) 