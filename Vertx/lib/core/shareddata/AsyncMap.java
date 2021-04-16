-----------------------
AsyncMap
-----------------------
	# 异步的Map接口 interface AsyncMap<K, V>

	default void get(K k, Handler<AsyncResult<@Nullable V>> resultHandler) 
	Future<@Nullable V> get(K k);
	default void put(K k, V v, Handler<AsyncResult<Void>> completionHandler)
	Future<Void> put(K k, V v)
	default void put(K k, V v, long ttl, Handler<AsyncResult<Void>> completionHandler)
	Future<Void> put(K k, V v, long ttl)
	default void putIfAbsent(K k, V v, Handler<AsyncResult<@Nullable V>> completionHandler)
	Future<@Nullable V> putIfAbsent(K k, V v)
	default void putIfAbsent(K k, V v, long ttl, Handler<AsyncResult<@Nullable V>> completionHandler)
	Future<@Nullable V> putIfAbsent(K k, V v, long ttl)
	default void remove(K k, Handler<AsyncResult<@Nullable V>> resultHandler)
	Future<@Nullable V> remove(K k)
	default void removeIfPresent(K k, V v, Handler<AsyncResult<Boolean>> resultHandler)
	Future<Boolean> removeIfPresent(K k, V v)
	default void replace(K k, V v, Handler<AsyncResult<@Nullable V>> resultHandler)
	Future<@Nullable V> replace(K k, V v)
	default void replaceIfPresent(K k, V oldValue, V newValue, Handler<AsyncResult<Boolean>> resultHandler)
	Future<Boolean> replaceIfPresent(K k, V oldValue, V newValue)
	default void clear(Handler<AsyncResult<Void>> resultHandler)
	Future<Void> clear()
	default void size(Handler<AsyncResult<Integer>> resultHandler)
	Future<Integer> size()
	default void keys(Handler<AsyncResult<Set<K>>> resultHandler)
	Future<Set<K>> keys()
	default void values(Handler<AsyncResult<List<V>>> resultHandler) 
	Future<List<V>> values()
	default void entries(Handler<AsyncResult<Map<K, V>>> resultHandler)
	Future<Map<K, V>> entries()
