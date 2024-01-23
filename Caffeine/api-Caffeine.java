---------------------------
Caffeine
---------------------------
	# Caffeine 配置类
		public final class Caffeine<K, V> 
	

	# 静态

		public static Caffeine<Object, Object> newBuilder() 
			* 创建 Builder

		public static Caffeine<Object, Object> from(CaffeineSpec spec)
		public static Caffeine<Object, Object> from(String spec)
			* 从配置类/字符串构建
	
	# 实例方法
		public Caffeine<K, V> initialCapacity(@NonNegative int initialCapacity)
		public Caffeine<K, V> executor(Executor executor)
		public Caffeine<K, V> scheduler(Scheduler scheduler)
		public Caffeine<K, V> maximumSize(@NonNegative long maximumSize)
		public Caffeine<K, V> maximumWeight(@NonNegative long maximumWeight)

		@CanIgnoreReturnValue
		public <K1 extends K, V1 extends V> Caffeine<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher)
		@CanIgnoreReturnValue
		public Caffeine<K, V> weakKeys() 
		@CanIgnoreReturnValue
		public Caffeine<K, V> weakValues()
		@CanIgnoreReturnValue
		public Caffeine<K, V> softValues()
		@CanIgnoreReturnValue
		public Caffeine<K, V> expireAfterWrite(Duration duration)
		@CanIgnoreReturnValue
		public Caffeine<K, V> expireAfterWrite(@NonNegative long duration, TimeUnit unit)
		@CanIgnoreReturnValue
		public Caffeine<K, V> expireAfterAccess(Duration duration)
		@CanIgnoreReturnValue
		public Caffeine<K, V> expireAfterAccess(@NonNegative long duration, TimeUnit unit)
		@CanIgnoreReturnValue
		public <K1 extends K, V1 extends V> Caffeine<K1, V1> expireAfter(Expiry<? super K1, ? super V1> expiry)
		@SuppressWarnings("unchecked")
		@Nullable Expiry<K, V> getExpiry(boolean isAsync)
		@CanIgnoreReturnValue
		public Caffeine<K, V> refreshAfterWrite(Duration duration)
		@CanIgnoreReturnValue
		public Caffeine<K, V> refreshAfterWrite(@NonNegative long duration, TimeUnit unit) 
		@CanIgnoreReturnValue
		public Caffeine<K, V> ticker(Ticker ticker)
		@CanIgnoreReturnValue
		public <K1 extends K, V1 extends V> Caffeine<K1, V1> evictionListener(RemovalListener<? super K1, ? super V1> evictionListener)
		@CanIgnoreReturnValue
		public <K1 extends K, V1 extends V> Caffeine<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> removalListener)
		@CanIgnoreReturnValue
		public Caffeine<K, V> recordStats()
		@CanIgnoreReturnValue
		public Caffeine<K, V> recordStats(Supplier<? extends StatsCounter> statsCounterSupplier)

		public <K1 extends K, V1 extends V> Cache<K1, V1> build() 
			* 构建缓存

		public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader)

		public <K1 extends K, V1 extends V> AsyncCache<K1, V1> buildAsync()
		public <K1 extends K, V1 extends V> AsyncLoadingCache<K1, V1> buildAsync(CacheLoader<? super K1, V1> loader)
		public <K1 extends K, V1 extends V> AsyncLoadingCache<K1, V1> buildAsync(AsyncCacheLoader<? super K1, V1> loader)


---------------------
CaffeineSpec
---------------------
	# 缓存配置描述类
	
	# 静态方法
		
		public static CaffeineSpec parse(String specification) 
	
	# 实例方法
		
		 public String toParsableString()
