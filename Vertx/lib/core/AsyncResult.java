-----------------------------
AsyncResult
-----------------------------
	# 异步结果接口： public interface AsyncResult<T>

-----------------------------
抽象
-----------------------------
	T result();
	Throwable cause();
	boolean succeeded();
	boolean failed();
	default <U> AsyncResult<U> map(Function<T, U> mapper)
	default <V> AsyncResult<V> map(V value)
	default <V> AsyncResult<V> mapEmpty()
	default AsyncResult<T> otherwise(Function<Throwable, T> mapper)
	default AsyncResult<T> otherwise(T value)
	default AsyncResult<T> otherwiseEmpty()