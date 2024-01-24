------------------
caffeine
------------------
	# Caffeine
		<!-- https://mvnrepository.com/artifact/com.github.ben-manes.caffeine/caffeine -->
		<dependency>
			<groupId>com.github.ben-manes.caffeine</groupId>
			<artifactId>caffeine</artifactId>
			<version>3.1.8</version>
		</dependency>
	

		* https://github.com/ben-manes/caffeine
		* https://github.com/ben-manes/caffeine/wiki/Population-zh-CN
	
	# 类体系解耦
		Cache
			|-LoadingCache
		AsyncCache
			|-AsyncLoadingCache
		

		AsyncCacheLoader
			|-CacheLoader
		
	
	# 一些特性
		* 缓存的删除策略使用的是惰性删除和定时删除。这两个删除策略的时间复杂度都是O(1)。
	
	# 删除术语
		eviction		指受策略影响而被删除
		invalidation	值被调用者手动删除
		removal			值因eviction或invalidation而发生的一种行为 
	
