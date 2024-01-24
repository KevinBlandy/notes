-------------------
AsyncCacheLoader
-------------------
	# 异步缓存加载器接口
		interface AsyncCacheLoader<K, V>
	

	static <K, V> AsyncCacheLoader<K, V> bulk(Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction)
	static <K, V> AsyncCacheLoader<K, V> bulk(BiFunction<? super Set<? extends K>, ? super Executor, ? extends CompletableFuture<? extends Map<? extends K, ? extends V>>> mappingFunction) 

	CompletableFuture<? extends V> asyncLoad(K key, Executor executor) throws Exception;
	CompletableFuture<? extends Map<? extends K, ? extends V>> asyncLoadAll(Set<? extends K> keys, Executor executor)
	CompletableFuture<? extends V> asyncReload(K key, V oldValue, Executor executor) throws Exception

-------------------
CacheLoader
-------------------	
	# 继承了 AsyncCacheLoader 的缓存加载器接口
		interface CacheLoader<K, V> extends AsyncCacheLoader<K, V>
		
	@Nullable
	V load(K key) throws Exception;

	Map<? extends K, ? extends V> loadAll(Set<? extends K> keys) throws Exception

	CompletableFuture<? extends V> asyncLoad(K key, Executor executor) throws Exception

	CompletableFuture<? extends Map<? extends K, ? extends V>> asyncLoadAll

	@Nullable V reload(K key, V oldValue) throws Exception

	CompletableFuture<? extends V> asyncReload(K key, V oldValue, Executor executor) throws Exception

	<K, V> CacheLoader<K, V> bulk(Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction)

