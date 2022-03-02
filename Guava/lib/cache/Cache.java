-----------------------
Cache
-----------------------
	# 核心缓存接口
		public interface Cache<K, V>
	
	# 方法
		V getIfPresent(@CompatibleWith("K") Object key);
		V get(K key, Callable<? extends V> loader) throws ExecutionException;
		ImmutableMap<K, V> getAllPresent(Iterable<? extends Object> keys);
		void put(K key, V value);
		void putAll(Map<? extends K, ? extends V> m);
		void invalidate(@CompatibleWith("K") Object key);
		void invalidateAll(Iterable<? extends Object> keys);
		void invalidateAll();
		long size();
		CacheStats stats();
		ConcurrentMap<K, V> asMap();
