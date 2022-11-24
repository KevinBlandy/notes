--------------------
springboot-cache	|
--------------------
	# 参考
		
		https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.caching

		https://blog.csdn.net/u012373815/article/details/54564076
	


	# 依赖
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
	
	# 开启
		@EnableCaching
			* Spring Boot根据下面的顺序去侦测缓存提供者： 
				Generic 
				JCache (JSR-107) 
				EhCache 2.x 
				Hazelcast 
				Infinispan 
				COUCHBASE
				Redis 
				CAFFEINE
				Guava (新版本已经移除)
				Simple
				NONE
			* 除了按顺序侦测外,也可以通过配置属性spring.cache.type 来强制指定,默认是simple类型
				

	# spring 缓存支持,抽象出来了一个 CacheManager和Cache接口
		org.springframework.cache.CacheManager
		org.springframework.cache.Cache
	
	# spring支持的缓存框架
		SimpleCacheManager
			* 直接使用了一个 Collection 来存储
		ConcurrentMapCache
			* 使用 ConcurrentMap 来存储
		EhCacheCacheManager
			* 使用EhCache作为缓存技术
		RedisCacheManager
			* 使用Redis作为缓存技术
	
	# SpringBoot的配置类, 对于不同的cache实现, 有不同的配置
		CacheProperties
	
--------------------
声明式注解			|
--------------------
	@Cacheable
		# 如果存在缓存,直接返回,不存在调用方法获取计算结果,存入缓存
		# 当标记在一个类上时则表示该类所有的方法都是支持缓存的
		# 属性
			@AliasFor("cacheNames")
			String[] value() default {};
			@AliasFor("value")
			String[] cacheNames() default {};
				* 缓存的“区”概念，在Redis中就是key前缀
				* 可以配置多个，也就是会在多个区域中生成缓存信息

			String key() default "";
				* 默认key生成规则
					- 如果没有参数,则使用0作为key 
					- 如果只有一个参数,使用该参数作为key 
						* 在ehcache中,此时,key-type应该是参数类型
					- 如果又多个参数,使用包含所有参数的hashCode作为key

				* 支持使用SpringEL表达式
					@Cacheable(value = "user", key = "#user.id"),使用user的id作为参数
					public User create(User user)

					@Cacheable(cacheNames="books", key="#map['bookid'].toString()")
					public Book findBook(Map<String, Object> map)
				
				* 如果需要手动指定字符串，那么使用单引号
					@Cacheable(value = "user", key = "'name'") // 使用 name 作为key，而不是spel表达式

			String keyGenerator() default "";
				* 指定KEY生成器的实现

			String cacheManager() default "";
				* 指定缓存管理器

			String cacheResolver() default "";
			String condition() default "";
				* 条件

			String unless() default "";
				* 如果方法返回null,也会被认为是一种返回值,null也会被缓存,有些时候这不是我们希望的
				* 通过该属性来控制,禁止缓存null,如果结果为null,那么就不缓存
					@Cacheable(value = "post",unless="#result == null")

			boolean sync() default false;

	@CachePut
		# 不论怎么样,都会把方法返回值放入缓存中
		# CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果,而是每次都会执行该方法,并将执行结果以键值对的形式存入指定的缓存中
		# 属性
			@AliasFor("cacheNames")
			String[] value() default {};

			@AliasFor("value")
			String[] cacheNames() default {};

			String key() default "";
			String keyGenerator() default "";
			String cacheManager() default "";
			String cacheResolver() default "";
			String condition() default "";
			String unless() default "";
	
	@CacheEvict
		# 标注在需要清除缓存元素的方法或类上的
		# 当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作
		# 属性
			@AliasFor("cacheNames")
			String[] value() default {};
			@AliasFor("value")
			String[] cacheNames() default {};
			String key() default "";
			String keyGenerator() default "";
			String cacheManager() default "";
			String cacheResolver() default "";
			String condition() default "";
			boolean allEntries() default false;
				* 表示是否需要清除缓存中的所有元素,默认为false,表示不需要
				* 有的时候需要Cache一下清除所有的元素,这比一个一个清除元素更有效率

			boolean beforeInvocation() default false;
				* 清除操作默认是在对应方法成功执行之后触发的,即方法如果因为抛出异常而未能成功返回时也不会触发清除操作
				* 该属性值为true时，Spring会在调用该方法之前清除缓存中的指定元素

	@Caching
		# 组合注解,把多个注解整合到一个上
		# 属性
			Cacheable[] cacheable() default {};
			CachePut[] put() default {};
			CacheEvict[] evict() default {};
	
	@CacheConfig
		# 缓存配置，在类级别上配置，当前类种的缓存方法使用同一个配置
		# 属性
			String[] cacheNames() default {};
			String keyGenerator() default "";
			String cacheManager() default "";
			String cacheResolver() default "";

	
	# 使用自定义注解
		* Spring允许我们在配置可缓存的方法时使用自定义的注解
		* 前提是自定义的注解上必须使用对应的注解进行标注
			@Target({ElementType.TYPE, ElementType.METHOD})
			@Retention(RetentionPolicy.RUNTIME)
			@Cacheable(value="users")		//(*^__^*) 
			public @interface MyCacheable {

			}


--------------------
使用redis			|
--------------------
	# 开启redis的start(具体看redis的笔记)
		<dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-data-redis</artifactId>
    	</dependency>

	# 配置
		spring.cache.type=redis
		spring.cache.redis.time-to-live=
		spring.cache.redis.cache-null-values=
		spring.cache.redis.key-pre-fix=

	
	# 注解配置
		@Cacheable(value = "name")
			* value 属性,指定了redis 的key名称
		

--------------------
Ehcache3			|
--------------------
	# 依赖
		<dependency>
		    <groupId>org.ehcache</groupId>
		    <artifactId>ehcache</artifactId>
		</dependency>
		<dependency>
		    <groupId>javax.cache</groupId>
		    <artifactId>cache-api</artifactId>
		</dependency>

	# 配置
		spring.cache.type=jcache
		spring.cache.jcache.config=classpath:ehcache/ehcache.xml
		# 如果存在多个jcache的实现,需要在这里指定实现类
		# 实现类应该是 jsr107 规范的实现
		spring.cache.jcache.provider=org.ehcache.jsr107.EhcacheCachingProvider
	
	# 注解配置
		@Cacheable(value = "name")
			* value属性,便是指定了 ehcache.xml 中的 <cache alias="name">
	
	# 注入使用 CacheManager
		@Autowired	
		private CacheManager cacheManager;

		* 注意,该 CacheManager 是:javax.cache.CacheManager 接口
		* api跟 ehcache3 差不多
		* 很显然,实现使用的就是 ehcache3

-----------------------------
CachingConfigurerSupport	 |
-----------------------------
	# 配置接口
		public class CachingConfigurerSupport implements CachingConfigurer {
			@Override
			@Nullable
			public CacheManager cacheManager() {
				return null;
			}

			@Override
			@Nullable
			public CacheResolver cacheResolver() {
				return null;
			}

			@Override
			@Nullable
			public KeyGenerator keyGenerator() {
				return null;
			}

			@Override
			@Nullable
			public CacheErrorHandler errorHandler() {
				return null;
			}

		}

	# 自定义缓存中的一些配置
		* 继承:CachingConfigurerSupport,覆写方法

			import java.lang.reflect.Method;
			import org.springframework.cache.annotation.CachingConfigurerSupport;
			import org.springframework.cache.annotation.EnableCaching;
			import org.springframework.cache.interceptor.KeyGenerator;
			import org.springframework.context.annotation.Configuration;
			/**
			 * 
			 * @author KevinBlandy
			 *
			 */
			@EnableCaching
			@Configuration
			public class RedisCacheConfiguration extends CachingConfigurerSupport{

				//自定义key生成策略
				@Override
				public KeyGenerator keyGenerator() {
					KeyGenerator generator = new KeyGenerator() {
						@Override
						public Object generate(Object target, Method method, Object... params) {
							return target.getClass().getSimpleName() + ":" + method.getName();
						}
					};
					return generator;
				}
			}

	
--------------------
缓存的定制配置
--------------------
	# 使用什么类型的缓存，就去添加什么类型的 CacheConfiguration
		* 例如：Redis，就添加一个类: RedisCacheConfiguration
		* 这种配置类对于IOC来说，都是“互斥”，如果我们不提供，那么系统使用默认的，如果我们提供，则使用我们的
	
	# RedisCacheConfiguration
		public static RedisCacheConfiguration defaultCacheConfig()
			* 使用默认配置，创建一个配置

		public static RedisCacheConfiguration defaultCacheConfig(@Nullable ClassLoader classLoader)

		public RedisCacheConfiguration entryTtl(Duration ttl)
		public RedisCacheConfiguration prefixCacheNameWith(String prefix)
		public RedisCacheConfiguration computePrefixWith(CacheKeyPrefix cacheKeyPrefix) 
		public RedisCacheConfiguration disableCachingNullValues()
		public RedisCacheConfiguration disableKeyPrefix()
		public RedisCacheConfiguration withConversionService(ConversionService conversionService)

		public RedisCacheConfiguration serializeKeysWith(SerializationPair<String> keySerializationPair)
		public RedisCacheConfiguration serializeValuesWith(SerializationPair<?> valueSerializationPair)
			* 设置KEY和Value的序列化器

		public String getKeyPrefixFor(String cacheName)
		public boolean usePrefix()
		public boolean getAllowCacheNullValues()
		public SerializationPair<String> getKeySerializationPair()
		public SerializationPair<Object> getValueSerializationPair()
		public Duration getTtl()
		public ConversionService getConversionService()
		public void addCacheKeyConverter(Converter<?, String> cacheKeyConverter)
		public void configureKeyConverters(Consumer<ConverterRegistry> registryConsumer)
		public static void registerDefaultConverters(ConverterRegistry registry) 
	
	# 序列化为JSON
		import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
		import org.springframework.boot.autoconfigure.cache.CacheProperties;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.data.redis.cache.RedisCacheConfiguration;
		import org.springframework.data.redis.serializer.RedisSerializationContext;


		@Configuration
		public class CustomRedisCacheConfiguration {

			@Bean
			public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){

				RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

				// 先载入配置文件中的配置信息
				CacheProperties.Redis redisProperties = cacheProperties.getRedis();
				if (redisProperties.getTimeToLive() != null) {
					redisCacheConfiguration = redisCacheConfiguration.entryTtl(redisProperties.getTimeToLive());
				}
				if (redisProperties.getKeyPrefix() != null) {
					redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(redisProperties.getKeyPrefix());
				}
				if (!redisProperties.isCacheNullValues()) {
					redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
				}
				if (!redisProperties.isUseKeyPrefix()) {
					redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
				}

				// 再最后设置Value的序列化方式
				redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));

				return redisCacheConfiguration;
			}
		}
	
	# 实现 RedisCacheManagerBuilderCustomizer 接口也可以
	

	# 修改Redis批量删除缓存的方式为:scan （默认为keys）
		import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.data.redis.cache.BatchStrategies;
		import org.springframework.data.redis.cache.RedisCacheWriter;
		import org.springframework.data.redis.connection.RedisConnectionFactory;

		import dev.bitone.common.constant.AppConstant;

		@Configuration
		public class RedisCacheScanConfiguration {

			@Bean
			public RedisCacheManagerBuilderCustomizer RedisCacheManagerBuilderCustomizer(RedisConnectionFactory redisConnectionFactory) {
				return builder -> {
					builder.cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory,
							BatchStrategies.scan(AppConstant.REDIS_SCAN_BATCH_SIZE)));
				};
			}
		}