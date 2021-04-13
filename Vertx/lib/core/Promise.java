-------------------
Promise
-------------------
	# 异步接口：interface Promise<T> extends Handler<AsyncResult<T>> 
		* 支持链式调用
	
-------------------
抽象
-------------------
	default void handle(AsyncResult<T> asyncResult)

	default void complete(T result)
	default void complete()

	default void fail(Throwable cause)
	default void fail(String message)
	boolean tryComplete(T result)
	default boolean tryComplete()
	boolean tryFail(Throwable cause)
	default boolean tryFail(String message)
	Future<T> future();



-------------------
静态
-------------------
	static <T> Promise<T> promise()
