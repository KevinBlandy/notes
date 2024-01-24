---------------
CacheStats
---------------
	# 缓存统计
		CacheStats
	

	# getter 属性
		hitCount
		missCount
		loadSuccessCount
		loadFailureCount
		totalLoadTime
		evictionCount		// 缓存回收数量
		evictionWeight
	
	# 方法
		public @NonNegative double hitRate()
			* 缓存命中率
		
		public @NonNegative double averageLoadPenalty()
			* 加载新值的平均时间

		public CacheStats minus(CacheStats other)
		public CacheStats plus(CacheStats other)
		public @NonNegative double missRate()
		public @NonNegative long requestCount()
