--------------------
Cache
--------------------
	# 核心的缓存接口

		public interface Cache<K, V>
	
		@Nullable
		V getIfPresent(K key);


		@PolyNull
		V get(K key, Function<? super K, ? extends @PolyNull V> mappingFunction);

		Map<K, V> getAllPresent(Iterable<? extends K> keys);

		Map<K, V> getAll(Iterable<? extends K> keys, Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction);

		void put(K key, V value);

		void putAll(Map<? extends K, ? extends V> map);

		void invalidate(K key);

		void invalidateAll(Iterable<? extends K> keys);

		void invalidateAll();

		@NonNegative
		long estimatedSize();

		CacheStats stats();

		ConcurrentMap<K, V> asMap();

		void cleanUp();

		Policy<K, V> policy();

