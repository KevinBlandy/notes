
-----------------
CacheBuilder
-----------------
	# Cache接口构建Builder
	
	# 静态方法
		public static CacheBuilder<Object, Object> from(CacheBuilderSpec spec)
		public static CacheBuilder<Object, Object> from(String spec)
		public static CacheBuilder<Object, Object> newBuilder()
	
	# 实例方法
		public <K1 extends K, V1 extends V> Cache<K1, V1> build()
		public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader)
		public CacheBuilder<K, V> concurrencyLevel(int concurrencyLevel)
		public CacheBuilder<K, V> expireAfterAccess(java.time.Duration duration)
		public CacheBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit)
		public CacheBuilder<K, V> expireAfterWrite(java.time.Duration duration)
		public CacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit)
		public CacheBuilder<K, V> initialCapacity(int initialCapacity)
		public CacheBuilder<K, V> maximumSize(long maximumSize)
		public CacheBuilder<K, V> maximumWeight(long maximumWeight)
		public CacheBuilder<K, V> recordStats() 
		public CacheBuilder<K, V> refreshAfterWrite(java.time.Duration duration)
		public CacheBuilder<K, V> refreshAfterWrite(long duration, TimeUnit unit)
		public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> listener)
		public CacheBuilder<K, V> softValues()
		public CacheBuilder<K, V> ticker(Ticker ticker)
		public String toString()
		public CacheBuilder<K, V> weakKeys()
		public CacheBuilder<K, V> weakValues()
		public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher)

