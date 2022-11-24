--------------------
springboot-cache	|
--------------------
	# �ο�
		
		https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.caching

		https://blog.csdn.net/u012373815/article/details/54564076
	


	# ����
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
	
	# ����
		@EnableCaching
			* Spring Boot���������˳��ȥ��⻺���ṩ�ߣ� 
				Generic 
				JCache (JSR-107) 
				EhCache 2.x 
				Hazelcast 
				Infinispan 
				COUCHBASE
				Redis 
				CAFFEINE
				Guava (�°汾�Ѿ��Ƴ�)
				Simple
				NONE
			* ���˰�˳�������,Ҳ����ͨ����������spring.cache.type ��ǿ��ָ��,Ĭ����simple����
				

	# spring ����֧��,���������һ�� CacheManager��Cache�ӿ�
		org.springframework.cache.CacheManager
		org.springframework.cache.Cache
	
	# spring֧�ֵĻ�����
		SimpleCacheManager
			* ֱ��ʹ����һ�� Collection ���洢
		ConcurrentMapCache
			* ʹ�� ConcurrentMap ���洢
		EhCacheCacheManager
			* ʹ��EhCache��Ϊ���漼��
		RedisCacheManager
			* ʹ��Redis��Ϊ���漼��
	
	# SpringBoot��������, ���ڲ�ͬ��cacheʵ��, �в�ͬ������
		CacheProperties
	
--------------------
����ʽע��			|
--------------------
	@Cacheable
		# ������ڻ���,ֱ�ӷ���,�����ڵ��÷�����ȡ������,���뻺��
		# �������һ������ʱ���ʾ�������еķ�������֧�ֻ����
		# ����
			@AliasFor("cacheNames")
			String[] value() default {};
			@AliasFor("value")
			String[] cacheNames() default {};
				* ����ġ����������Redis�о���keyǰ׺
				* �������ö����Ҳ���ǻ��ڶ�����������ɻ�����Ϣ

			String key() default "";
				* Ĭ��key���ɹ���
					- ���û�в���,��ʹ��0��Ϊkey 
					- ���ֻ��һ������,ʹ�øò�����Ϊkey 
						* ��ehcache��,��ʱ,key-typeӦ���ǲ�������
					- ����ֶ������,ʹ�ð������в�����hashCode��Ϊkey

				* ֧��ʹ��SpringEL���ʽ
					@Cacheable(value = "user", key = "#user.id"),ʹ��user��id��Ϊ����
					public User create(User user)

					@Cacheable(cacheNames="books", key="#map['bookid'].toString()")
					public Book findBook(Map<String, Object> map)
				
				* �����Ҫ�ֶ�ָ���ַ�������ôʹ�õ�����
					@Cacheable(value = "user", key = "'name'") // ʹ�� name ��Ϊkey��������spel���ʽ

			String keyGenerator() default "";
				* ָ��KEY��������ʵ��

			String cacheManager() default "";
				* ָ�����������

			String cacheResolver() default "";
			String condition() default "";
				* ����

			String unless() default "";
				* �����������null,Ҳ�ᱻ��Ϊ��һ�ַ���ֵ,nullҲ�ᱻ����,��Щʱ���ⲻ������ϣ����
				* ͨ��������������,��ֹ����null,������Ϊnull,��ô�Ͳ�����
					@Cacheable(value = "post",unless="#result == null")

			boolean sync() default false;

	@CachePut
		# ������ô��,����ѷ�������ֵ���뻺����
		# CachePut��ע�ķ�����ִ��ǰ����ȥ��黺�����Ƿ����֮ǰִ�й��Ľ��,����ÿ�ζ���ִ�и÷���,����ִ�н���Լ�ֵ�Ե���ʽ����ָ���Ļ�����
		# ����
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
		# ��ע����Ҫ�������Ԫ�صķ��������ϵ�
		# �������һ������ʱ��ʾ�������еķ�����ִ�ж��ᴥ��������������
		# ����
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
				* ��ʾ�Ƿ���Ҫ��������е�����Ԫ��,Ĭ��Ϊfalse,��ʾ����Ҫ
				* �е�ʱ����ҪCacheһ��������е�Ԫ��,���һ��һ�����Ԫ�ظ���Ч��

			boolean beforeInvocation() default false;
				* �������Ĭ�����ڶ�Ӧ�����ɹ�ִ��֮�󴥷���,�����������Ϊ�׳��쳣��δ�ܳɹ�����ʱҲ���ᴥ���������
				* ������ֵΪtrueʱ��Spring���ڵ��ø÷���֮ǰ��������е�ָ��Ԫ��

	@Caching
		# ���ע��,�Ѷ��ע�����ϵ�һ����
		# ����
			Cacheable[] cacheable() default {};
			CachePut[] put() default {};
			CacheEvict[] evict() default {};
	
	@CacheConfig
		# �������ã����༶�������ã���ǰ���ֵĻ��淽��ʹ��ͬһ������
		# ����
			String[] cacheNames() default {};
			String keyGenerator() default "";
			String cacheManager() default "";
			String cacheResolver() default "";

	
	# ʹ���Զ���ע��
		* Spring�������������ÿɻ���ķ���ʱʹ���Զ����ע��
		* ǰ�����Զ����ע���ϱ���ʹ�ö�Ӧ��ע����б�ע
			@Target({ElementType.TYPE, ElementType.METHOD})
			@Retention(RetentionPolicy.RUNTIME)
			@Cacheable(value="users")		//(*^__^*) 
			public @interface MyCacheable {

			}


--------------------
ʹ��redis			|
--------------------
	# ����redis��start(���忴redis�ıʼ�)
		<dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-data-redis</artifactId>
    	</dependency>

	# ����
		spring.cache.type=redis
		spring.cache.redis.time-to-live=
		spring.cache.redis.cache-null-values=
		spring.cache.redis.key-pre-fix=

	
	# ע������
		@Cacheable(value = "name")
			* value ����,ָ����redis ��key����
		

--------------------
Ehcache3			|
--------------------
	# ����
		<dependency>
		    <groupId>org.ehcache</groupId>
		    <artifactId>ehcache</artifactId>
		</dependency>
		<dependency>
		    <groupId>javax.cache</groupId>
		    <artifactId>cache-api</artifactId>
		</dependency>

	# ����
		spring.cache.type=jcache
		spring.cache.jcache.config=classpath:ehcache/ehcache.xml
		# ������ڶ��jcache��ʵ��,��Ҫ������ָ��ʵ����
		# ʵ����Ӧ���� jsr107 �淶��ʵ��
		spring.cache.jcache.provider=org.ehcache.jsr107.EhcacheCachingProvider
	
	# ע������
		@Cacheable(value = "name")
			* value����,����ָ���� ehcache.xml �е� <cache alias="name">
	
	# ע��ʹ�� CacheManager
		@Autowired	
		private CacheManager cacheManager;

		* ע��,�� CacheManager ��:javax.cache.CacheManager �ӿ�
		* api�� ehcache3 ���
		* ����Ȼ,ʵ��ʹ�õľ��� ehcache3

-----------------------------
CachingConfigurerSupport	 |
-----------------------------
	# ���ýӿ�
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

	# �Զ��建���е�һЩ����
		* �̳�:CachingConfigurerSupport,��д����

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

				//�Զ���key���ɲ���
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
����Ķ�������
--------------------
	# ʹ��ʲô���͵Ļ��棬��ȥ���ʲô���͵� CacheConfiguration
		* ���磺Redis�������һ����: RedisCacheConfiguration
		* �������������IOC��˵�����ǡ����⡱��������ǲ��ṩ����ôϵͳʹ��Ĭ�ϵģ���������ṩ����ʹ�����ǵ�
	
	# RedisCacheConfiguration
		public static RedisCacheConfiguration defaultCacheConfig()
			* ʹ��Ĭ�����ã�����һ������

		public static RedisCacheConfiguration defaultCacheConfig(@Nullable ClassLoader classLoader)

		public RedisCacheConfiguration entryTtl(Duration ttl)
		public RedisCacheConfiguration prefixCacheNameWith(String prefix)
		public RedisCacheConfiguration computePrefixWith(CacheKeyPrefix cacheKeyPrefix) 
		public RedisCacheConfiguration disableCachingNullValues()
		public RedisCacheConfiguration disableKeyPrefix()
		public RedisCacheConfiguration withConversionService(ConversionService conversionService)

		public RedisCacheConfiguration serializeKeysWith(SerializationPair<String> keySerializationPair)
		public RedisCacheConfiguration serializeValuesWith(SerializationPair<?> valueSerializationPair)
			* ����KEY��Value�����л���

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
	
	# ���л�ΪJSON
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

				// �����������ļ��е�������Ϣ
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

				// ���������Value�����л���ʽ
				redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));

				return redisCacheConfiguration;
			}
		}
	
	# ʵ�� RedisCacheManagerBuilderCustomizer �ӿ�Ҳ����
	

	# �޸�Redis����ɾ������ķ�ʽΪ:scan ��Ĭ��Ϊkeys��
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