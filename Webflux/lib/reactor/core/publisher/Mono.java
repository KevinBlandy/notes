------------------------------
Mono
------------------------------
	# 发布者抽象类
		abstract class Mono<T> implements CorePublisher<T> 
	
	# 基本
		* 返回0或者1个元素

---------------------------------------
static
---------------------------------------
	public static <T> Mono<T> empty()

	public static <T> Mono<T> just(T data)
	public static <T> Mono<T> justOrEmpty(@Nullable Optional<? extends T> data)
	public static <T> Mono<T> justOrEmpty(@Nullable T data)

	public static <T> Mono<T> from(Publisher<? extends T> source)
	public static <T> Mono<T> fromCallable(Callable<? extends T> supplier)
	public static <T> Mono<T> fromCompletionStage(CompletionStage<? extends T> completionStage) 
	public static <T> Mono<T> fromCompletionStage(Supplier<? extends CompletionStage<? extends T>> stageSupplier)
	public static <I> Mono<I> fromDirect(Publisher<? extends I> source)
	public static <T> Mono<T> fromFuture(CompletableFuture<? extends T> future) 
	public static <T> Mono<T> fromFuture(Supplier<? extends CompletableFuture<? extends T>> futureSupplier)
	public static <T> Mono<T> fromRunnable(Runnable runnable)
	public static <T> Mono<T> fromSupplier(Supplier<? extends T> supplier) 

	
	public static <T> Mono<T> error(Throwable error)
	public static <T> Mono<T> error(Supplier<? extends Throwable> errorSupplier)

---------------------------------------
this
---------------------------------------
	public final Mono<T> doOnNext(Consumer<? super T> onNext)
	public final Mono<T> doOnSuccess(Consumer<? super T> onSuccess) 

	public final Mono<Void> then()
	public final <V> Mono<V> then(Mono<V> other)
	public final Mono<Void> thenEmpty(Publisher<Void> other)
	public final <V> Mono<V> thenReturn(V value)
	public final Mono<Void> thenEmpty(Publisher<Void> other)
	public final <V> Flux<V> thenMany(Publisher<V> other)
	
	public T block()
	public T block(Duration timeout)
	public Optional<T> blockOptional()
	public Optional<T> blockOptional(Duration timeout)
	
	public final Mono<T> publishOn(Scheduler scheduler) 
	public final <R> Mono<R> publish(Function<? super Mono<T>, ? extends Mono<? extends R>> transform)

	public final Flux<T> flux()
		* 转换为Flux
	

	public final <T2> Mono<Tuple2<T, T2>> zipWith(Mono<? extends T2> other)
	public final <T2, O> Mono<O> zipWith(Mono<? extends T2> other,
			BiFunction<? super T, ? super T2, ? extends O> combinator)
	public final <T2, O> Mono<O> zipWhen(Function<T, Mono<? extends T2>> rightGenerator,
			BiFunction<T, T2, O> combinator)
	public final <T2> Mono<Tuple2<T, T2>> zipWhen(Function<T, Mono<? extends T2>> rightGenerator)
