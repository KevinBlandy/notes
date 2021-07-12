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
