-----------------------
Policy
-----------------------
	# interface Policy<K, V> 

	boolean isRecordingStats();

	@Nullable
	V getIfPresentQuietly(K key);

	@Nullable
	CacheEntry<K, V> getEntryIfPresentQuietly(K key) 

	Map<K, CompletableFuture<V>> refreshes();

	Optional<Eviction<K, V>> eviction();

	Optional<FixedExpiration<K, V>> expireAfterAccess();

	Optional<FixedExpiration<K, V>> expireAfterWrite();

	Optional<VarExpiration<K, V>> expireVariably();

	Optional<FixedRefresh<K, V>> refreshAfterWrite();

	interface Eviction<K, V> 
		* 内部接口

			boolean isWeighted();
			OptionalInt weightOf(K key);
			OptionalLong weightedSize();
			@NonNegative
			long getMaximum();
			void setMaximum(@NonNegative long maximum);
			Map<K, V> coldest(@NonNegative int limit);
			Map<K, V> coldestWeighted(@NonNegative long weightLimit)
			<T> T coldest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction)
			Map<K, V> hottest(@NonNegative int limit);
			Map<K, V> hottestWeighted(@NonNegative long weightLimit)
			<T> T hottest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction)
