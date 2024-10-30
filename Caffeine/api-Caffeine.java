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
			* 指定运行异步任务时使用的 executor
			* AsyncCache / LoadingCache.refresh / refreshAfterWrite 执行异步计算时，或执行定期维护时，都会委托给这个 executor 执行
			* 默认使用 ForkJoinPool.commonPool()

		public Caffeine<K, V> scheduler(Scheduler scheduler)
			* 过期事件调度例行维护时使用的 scheduler
			* 默认使用 Scheduler.disabledScheduler()。
			* Java 9以上版本的用户可以选择 Scheduler.systemScheduler() 利用系统范围内的调度线程。

		public Caffeine<K, V> maximumSize(@NonNegative long maximumSize)
			* 最多缓存数量，超过数量后会执行缓存驱逐
			* 缓存将会尝试通过基于就近度和频率的算法来驱逐掉不会再被使用到的元素。

		public Caffeine<K, V> maximumWeight(@NonNegative long maximumWeight)
			* 根据缓存的权重来进行驱逐（权重只是用于确定缓存大小，不会用于决定该缓存是否被驱逐）
			* maximumWeight 与maximumSize 不可以同时使用。

		@CanIgnoreReturnValue
		public <K1 extends K, V1 extends V> Caffeine<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher)
			* 提供一个函数来设定每个条目的权重值是多少

		@CanIgnoreReturnValue
		public Caffeine<K, V> weakKeys() 
			* 用弱引用存储key。如果没有其他地方对该 key 有强引用，那么该缓存就会被垃圾回收器回收。
			* 由于垃圾回收器只依赖于 ID 相等，因此这会导致整个缓存使用 == 相等来比较 key，而不是使用 equals()。

		@CanIgnoreReturnValue
		public Caffeine<K, V> weakValues()
			* 使用弱引用存储value。如果没有其他地方对该 value 有强引用，那么该缓存就会被垃圾回收器回收
			* 由于垃圾回收器只依赖于 ID 相等，因此这会导致整个缓存使用 == 相等来比较 key，而不是使用 equals()。

		@CanIgnoreReturnValue
		public Caffeine<K, V> softValues()
			* 当垃圾收集器需要释放内存时驱逐
			* Caffeine.weakValues()和 Caffeine.softValues() 不可以一起使用。

		@CanIgnoreReturnValue
		public Caffeine<K, V> expireAfterWrite(Duration duration)
		@CanIgnoreReturnValue
		public Caffeine<K, V> expireAfterWrite(@NonNegative long duration, TimeUnit unit)
			* 创建条目或最近一次替换其值后的固定时间结束后，自动从缓存中删除每个条目。

		@CanIgnoreReturnValue
		public Caffeine<K, V> expireAfterAccess(Duration duration)
		@CanIgnoreReturnValue
		public Caffeine<K, V> expireAfterAccess(@NonNegative long duration, TimeUnit unit)
			* 根据最后访问时间驱逐缓存，所有读写操作都会重置这个时间，Cache.asMap 的集合视图操作不会重置访问时间

		@CanIgnoreReturnValue
		public <K1 extends K, V1 extends V> Caffeine<K1, V1> expireAfter(Expiry<? super K1, ? super V1> expiry)
			* 自定义策略，过期时间由 Expiry 实现独自计算。

			* 注意，调用此方法后，请勿继续使用调用此方法的 cachebuilder 引用，而应使用此方法返回的引用（链式调用即可）。
			* 在运行时，这些引用指向同一个实例，但只有返回的引用才具有正确的泛型类型信息，以确保类型安全。

		@SuppressWarnings("unchecked")
		@Nullable Expiry<K, V> getExpiry(boolean isAsync)

		@CanIgnoreReturnValue
		public Caffeine<K, V> refreshAfterWrite(Duration duration)
		@CanIgnoreReturnValue
		public Caffeine<K, V> refreshAfterWrite(@NonNegative long duration, TimeUnit unit) 
			* 定期进行刷新，在写入操作后，多久进行刷新

		@CanIgnoreReturnValue
		public Caffeine<K, V> ticker(Ticker ticker)
			* 


		@CanIgnoreReturnValue
		public <K1 extends K, V1 extends V> Caffeine<K1, V1> evictionListener(RemovalListener<? super K1, ? super V1> evictionListener)
			* 监听器，缓存在被驱逐时触发

		@CanIgnoreReturnValue
		public <K1 extends K, V1 extends V> Caffeine<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> removalListener)
			* 监听器，缓存在被移除时触发

		@CanIgnoreReturnValue
		public Caffeine<K, V> recordStats()
		@CanIgnoreReturnValue
		public Caffeine<K, V> recordStats(Supplier<? extends StatsCounter> statsCounterSupplier)
			* 记录缓存统计

		public <K1 extends K, V1 extends V> Cache<K1, V1> build() 
		public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader)
			* 构建缓存
			* 构建自动加载的缓存，loader 设置同步加载器。通过缓存获取元素的时候，如果元素不存在就会从 loader 中获取后返回，同时加入到缓存

		public <K1 extends K, V1 extends V> AsyncCache<K1, V1> buildAsync()
		public <K1 extends K, V1 extends V> AsyncLoadingCache<K1, V1> buildAsync(CacheLoader<? super K1, V1> loader)
		public <K1 extends K, V1 extends V> AsyncLoadingCache<K1, V1> buildAsync(AsyncCacheLoader<? super K1, V1> loader)
			* 构建异步缓存
			* 构建异步自动加载的缓存，设置同步加载器/异步加载器
			

---------------------
CaffeineSpec
---------------------
	# 缓存配置描述类
	
	# 静态方法
		
		public static CaffeineSpec parse(String specification) 
	
	# 实例方法
		
		 public String toParsableString()
	
	# 可配置的属性
		initialCapacity
		maximumSize
		maximumWeight
		weakKeys
		weakValues
		softValues
			* 使用软引用存储value。当内存满了过后，软引用的对象以将使用最近最少使用(least-recently-used ) 的方式进行垃圾回收。
			* 由于使用软引用是需要等到内存满了才进行回收，所以我们通常建议给缓存配置一个使用内存的最大值。
			* softValues() 将使用身份相等(identity) (==) 而不是equals() 来比较值。

		expireAfterAccess
		expireAfterWrite
		refreshAfterWrite
		recordStats
	
	# 配置格式，逗号分割
		maximumSize=10000,expireAfterWrite=1h,recordStats
