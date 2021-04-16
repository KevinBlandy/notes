----------------------
Counter
----------------------
	# 计数器接口： interface Counter 

	default void get(Handler<AsyncResult<Long>> resultHandler)
	Future<Long> get();

	default void incrementAndGet(Handler<AsyncResult<Long>> resultHandler)
	Future<Long> incrementAndGet()

	default void getAndIncrement(Handler<AsyncResult<Long>> resultHandler)
	Future<Long> getAndIncrement()

	default void decrementAndGet(Handler<AsyncResult<Long>> resultHandler)
	Future<Long> decrementAndGet()

	default void addAndGet(long value, Handler<AsyncResult<Long>> resultHandler)
	Future<Long> addAndGet(long value)

	default void getAndAdd(long value, Handler<AsyncResult<Long>> resultHandler)
	Future<Long> getAndAdd(long value)

	default void compareAndSet(long expected, long value, Handler<AsyncResult<Boolean>> resultHandler)
	Future<Boolean> compareAndSet(long expected, long value)