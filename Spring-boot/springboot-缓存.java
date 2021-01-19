----------------------------
Spring-boot Cache			|
----------------------------
	# spring 对缓存的支持提供了一个接口
		* org.springframework.cache.CacheManager
		* 一般不会直接和此接口打交道
	
	# spring 支持的 CacheManager(实现)
		SimpleCacheManager
		...
		RedisCacheManager
		...太多了
	
	# spring boot中为我们自动配置了多个 CacheManager 的实现
		* org.springframework.boot.autoconfigure.cache 包下
	
	# 注解
		@CachePut
		@CacheEvict
		@Cacheable
	


		
