-----------
Cache
-----------
	# 三种基本的缓存回收方式
		* 基于容量回收
		* 定时回收
			* 按照写入时间，最早写入的最先回收
			* 按照访问时间，最早访问的最早回收
		* 基于引用回收

----------------------
CacheBuilder
----------------------
	# 静态方法
		static CacheBuilder<Object, Object> from(CacheBuilderSpec spec) 
		static CacheBuilder<Object, Object> from(String spec)
		static CacheBuilder<Object, Object> newBuilder() 


	# 维护的属性
		public <K1 extends K, V1 extends V> Cache<K1, V1> build()
		public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader)

		public CacheBuilder<K, V> concurrencyLevel(int concurrencyLevel)
			* 设置并发级，并发级别是指可以同时写缓存的线程数

		public CacheBuilder<K, V> expireAfterAccess(java.time.Duration duration)
		public CacheBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit) 
			* 读写读写缓存后多久过期

		public CacheBuilder<K, V> expireAfterWrite(java.time.Duration duration)
		public CacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit)
			* 设置写缓存后多久过期

		public CacheBuilder<K, V> initialCapacity(int initialCapacity) 
			* 设置缓存容器的初始容量

		public CacheBuilder<K, V> maximumSize(long maximumSize)
			* 设置缓存最大容量

		public CacheBuilder<K, V> maximumWeight(long maximumWeight)

		public CacheBuilder<K, V> recordStats()
			* 是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
		
		public CacheBuilder<K, V> refreshAfterWrite(java.time.Duration duration)
		public CacheBuilder<K, V> refreshAfterWrite(long duration, TimeUnit unit)
			* 自动刷新数据时间

		public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> listener) 
			* 设置缓存过期监听

		public CacheBuilder<K, V> softValues()
		public CacheBuilder<K, V> ticker(Ticker ticker)

		public CacheBuilder<K, V> weakKeys()
		public CacheBuilder<K, V> weakValues()

		public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher)


