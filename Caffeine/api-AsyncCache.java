----------------------------
AsyncCache
----------------------------
	# 异步缓存接口
		interface AsyncCache<K, V>
	
	
	@Nullable
	CompletableFuture<V> getIfPresent(K key);

	CompletableFuture<V> get(K key, Function<? super K, ? extends V> mappingFunction);

	CompletableFuture<V> get(K key, BiFunction<? super K, ? super Executor, ? extends CompletableFuture<? extends V>> mappingFunction);

	CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys, Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction);

	CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys, BiFunction<? super Set<? extends K>, ? super Executor, ? extends CompletableFuture<? extends Map<? extends K, ? extends V>>> mappingFunction);

	void put(K key, CompletableFuture<? extends V> valueFuture);

	ConcurrentMap<K, CompletableFuture<V>> asMap();

	Cache<K, V> synchronous();


----------------------------
AsyncLoadingCache
----------------------------
	# AsyncCache 接口子接口

		interface AsyncLoadingCache<K, V> extends AsyncCache<K, V> 
	
	CompletableFuture<V> get(K key);
	CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys);
	@Override
	LoadingCache<K, V> synchronous();

	