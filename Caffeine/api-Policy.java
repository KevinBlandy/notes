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
	
	 interface CacheEntry<K, V> extends Map.Entry<K, V>
	 	* 内部接口
			
			int weight();
			long expiresAt();
			Duration expiresAfter()
			long refreshableAt();
			Duration refreshableAfter()
			long snapshotAt();
		
	interface FixedExpiration<K, V>
		* 内部接口

			OptionalLong ageOf(K key, TimeUnit unit);
			Optional<Duration> ageOf(K key)
			@NonNegative
			long getExpiresAfter(TimeUnit unit);
			Duration getExpiresAfter()
			void setExpiresAfter(@NonNegative long duration, TimeUnit unit);
			void setExpiresAfter(Duration duration)
			Map<K, V> oldest(@NonNegative int limit);
			<T> T oldest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction)
			Map<K, V> youngest(@NonNegative int limit);
			<T> T youngest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction)

	
	interface FixedRefresh<K, V>
		* 内部接口

			OptionalLong ageOf(K key, TimeUnit unit);
			Optional<Duration> ageOf(K key)
			long getRefreshesAfter(TimeUnit unit);
			Duration getRefreshesAfter()
			void setRefreshesAfter(@NonNegative long duration, TimeUnit unit);
			void setRefreshesAfter(Duration duration)
		
	interface VarExpiration<K, V>
		* 内部接口
			OptionalLong getExpiresAfter(K key, TimeUnit unit);
			Optional<Duration> getExpiresAfter(K key)
			void setExpiresAfter(K key, @NonNegative long duration, TimeUnit unit);
			void setExpiresAfter(K key, Duration duration)
			@Nullable V putIfAbsent(K key, V value, @NonNegative long duration, TimeUnit unit);
			@Nullable V putIfAbsent(K key, V value, Duration duration)
			@Nullable V put(K key, V value, @NonNegative long duration, TimeUnit unit);
			@Nullable V put(K key, V value, Duration duration)
			@PolyNull V compute(K key, BiFunction<? super K, ? super V, ? extends @PolyNull V> remappingFunction, Duration duration)
			Map<K, V> oldest(@NonNegative int limit);
			<T> T oldest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction)
			Map<K, V> youngest(@NonNegative int limit);
			<T> T youngest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction)
