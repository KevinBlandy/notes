--------------------
Cache
--------------------
	# 核心的缓存接口

		public interface Cache<K, V>
	
		@Nullable
		V getIfPresent(K key);
			* 检索元素，元素不存在返回 null

		@PolyNull
		V get(K key, Function<? super K, ? extends @PolyNull V> mappingFunction);
			* 如果缓存不存在则使用 mappingFunction 生成缓存元素,  如果无法生成则返回null

		Map<K, V> getAllPresent(Iterable<? extends K> keys);

		Map<K, V> getAll(Iterable<? extends K> keys, Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction);

		void put(K key, V value);
		void putAll(Map<? extends K, ? extends V> map);
			* 添加元素

		void invalidate(K key);
		void invalidateAll(Iterable<? extends K> keys);
		void invalidateAll();
			* 移除元素

		@NonNegative
		long estimatedSize();

		CacheStats stats();
			* 返回缓存统计

		ConcurrentMap<K, V> asMap();

		void cleanUp();

		Policy<K, V> policy();

--------------------
LoadingCache
--------------------
	# Cache 的实现接口，可以自动加载的缓存

		interface LoadingCache<K, V> extends Cache<K, V>

		V get(K key);

		Map<K, V> getAll(Iterable<? extends K> keys);
			* 批量查找

		@CanIgnoreReturnValue
		CompletableFuture<V> refresh(K key);
		@CanIgnoreReturnValue
		CompletableFuture<Map<K, V>> refreshAll(Iterable<? extends K> keys);
			* 刷新缓存

