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

	