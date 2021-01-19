
-----------------------
缓存的过期				|
-----------------------
	# 通过代码配置
		CacheConfiguration<Long, String> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,ResourcePoolsBuilder.heap(100)) 
		//有效时间20秒
		.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(20))) 
		.build();
	
	# 通过xml配置
		<cache alias="withExpiry">
		  <expiry>
			<ttl unit="seconds">20</ttl> 
		  </expiry>
		  <heap>100</heap>
		</cache>
	
	# Java和XML都提供对三种到期时间的直接支持
		1,没有到期	
			* 这意味着缓存映射永远不会过期

		2,时间到现场	
			* 这意味着缓存映射将在创建后的固定持续时间后过期

		3,时至怠速	
			* 这意味着缓存映射将在上次访问后的固定持续时间后过期
		
		<expiry>
			<class></class>						//自定义的过期设置
			<none/>								//永远不过期
			<ttl unit="seconds">20</ttl>		//指定时间过期
			<tti unit="seconds">20</tti>		//距离最后一次访问后超时过期
		</expiry>

		* 以上三种策略,只能设置一种

-----------------------
自定义到期				|
-----------------------
	# 实现接口
		@Deprecated
		public interface Expiry<K, V>
	
	# xml配置自定义到期实现
		<expiry>
			<class>xx.x.x</class>
		</expiry>
	
	# 代码配置
		CacheConfiguration<Long, String> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,ResourcePoolsBuilder.heap(100))
		.withExpiry(new CustomExpiry()) 
		.build();