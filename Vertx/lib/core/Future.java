----------------------
Future
----------------------
	# 异步任务接口： interface Future<T> extends io.vertx.core.AsyncResult<T> 



----------------------
Future 静态属性
----------------------
	static <T> Future<T> future(Handler<Promise<T>> handler) 

	static <T> Future<T> succeededFuture()
	static <T> Future<T> succeededFuture(T result)
		* 返回一个成功的Future
		
	static <T> Future<T> failedFuture(Throwable t)
	static <T> Future<T> failedFuture(String failureMessage) 
		* 返回一个失败的Future

	static <T> Future<T> fromCompletionStage(CompletionStage<T> completionStage)
	static <T> Future<T> fromCompletionStage(CompletionStage<T> completionStage, Context context)
		* 包装JDK的CompletionStage为vertx的Future
		* 由于Vert.x的 Future 通常会与Vert.x的代码、库以及客户端等一起使用，为了与Vert.x的线程模型更好地配合， 大部分场景下应使用这个

----------------------
Future 抽象方法
----------------------
	boolean isComplete();
	Future<T> onComplete(Handler<AsyncResult<T>> handler);
	default Future<T> onSuccess(Handler<T> handler)
	default Future<T> onFailure(Handler<Throwable> handler)
	T result();
	Throwable cause();
	boolean succeeded();
	boolean failed();
	default <U> Future<U> flatMap(Function<T, Future<U>> mapper)
	default <U> Future<U> compose(Function<T, Future<U>> mapper)
		* 顺序组合 future
		* 若当前future成功，执行 compose 方法指定的方法，该方法返回新的future；当返回的新future完成时，future组合成功；
		* 若当前future失败，则future组合失败
		
	default Future<T> recover(Function<Throwable, Future<T>> mapper)
	<U> Future<U> compose(Function<T, Future<U>> successMapper, Function<Throwable, Future<U>> failureMapper)
	<U> Future<U> transform(Function<AsyncResult<T>, Future<U>> mapper)
	<U> Future<T> eventually(Function<Void, Future<U>> mapper)
	<U> Future<U> map(Function<T, U> mapper);
	<V> Future<V> map(V value);
	default <V> Future<V> mapEmpty()
	Future<T> otherwise(Function<Throwable, T> mapper);
	Future<T> otherwise(T value);
	default Future<T> otherwiseEmpty()
	default CompletionStage<T> toCompletionStage()
		* 转换vertx的Future为JDK的CompletionStage
	

