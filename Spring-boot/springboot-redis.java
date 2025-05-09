------------------------------------
Redis-整合单机版					|
------------------------------------
	# 文档
		https://docs.spring.io/spring-data/redis/docs/current/reference/html/#reference
	# 使用lettuce
	# 依赖
		<dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-data-redis</artifactId>
    	</dependency>
		<dependency>
		    <groupId>io.lettuce</groupId>
		    <artifactId>lettuce-core</artifactId>
		</dependency>
		<dependency>
		    <groupId>org.apache.commons</groupId>
		    <artifactId>commons-pool2</artifactId>
		</dependency>
	
	# 配置文件(配置类:org.springframework.boot.autoconfigure.data.redis.RedisProperties)
		# Redis数据库索引（默认为0）
		spring.redis.database=0  
		# Redis服务器地址
		spring.redis.host=192.168.0.58
		# Redis服务器连接端口
		spring.redis.port=6379  
		# Redis服务器连接密码(默认为空)
		spring.redis.password=  
		# 连接池最大连接数(使用负值表示没有限制)
		spring.redis.lettuce.pool.max-active=8  
		# 连接池最大阻塞等待时间(使用负值表示没有限制)
		spring.redis.lettuce.pool.max-wait=-1  
		# 连接池中的最大空闲连接
		spring.redis.lettuce.pool.max-idle=8  
		# 连接池中的最小空闲连接
		spring.redis.lettuce.pool.min-idle=0  
		# 连接超时时间(毫秒)
		spring.redis.timeout=2000

	# 使用
		* StringRedisTemplate
			* 是RedisTemplate的子类
			* 一般大多数都是使用它
		* 针对jedis客户端中大量api进行了归类封装,将同一类型操作封装为operation接口
			ValueOperations	简单K-V操作
			SetOperations	set类型数据操作
			ZSetOperations	zset类型数据操作
			HashOperations	针对map类型的数据操作
			ListOperations	针对list类型的数据操作
		* demo
			@Autowired
			private StringRedisTemplate stringRedisTemplate;

			stringRedisTemplate.opsForValue();		//获取操作简单k-v的api
			stringRedisTemplate.opsForSet();		//获取操作set的api
	
	# 非阻塞的客户端
		ReactiveRedisTemplate
	
	# 池化
		* 当应用依赖中包含 commons-pool2.jar 会自动配置 redis 链接池 （Jedis Lettuce 都支持）
		* 如果需要关闭池化可以配置

		spring.redis.jedis.pool.enabled=false 
		spring.redis.lettuce.pool.enabled=false 
		
			
------------------------------------
Redis-整合集群						|
------------------------------------	
	
	

------------------------------------
Redis- scan 代替 keys *				|
------------------------------------
	public void scan(String pattern, Consumer<byte[]> consumer) {
		this.stringRedisTemplate.execute((RedisConnection connection) -> {
			try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
				cursor.forEachRemaining(consumer);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
	}

------------------------------------
Redis- 过期key的监听				|
------------------------------------
	# redis必须开启配置
		 notify-keyspace-events "Egx"  # 监听key的过期事件

	# configuration的配置
		@Configuration
		public class RedisConfiguration {
		 
			@Autowired 
			private RedisConnectionFactory redisConnectionFactory;

			@Autowired
			private ThreadPoolTaskExecutor threadPoolTaskExecutor;
			
			@Bean
			public RedisMessageListenerContainer redisMessageListenerContainer() {
				//创建监听容器
				RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
				//设置连接工厂
				redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
				// 设置执行的线程池
				redisMessageListenerContainer.setTaskExecutor(this.threadPoolTaskExecutor);
				return redisMessageListenerContainer;
			}
			
			// 创建key的过期监听
			@Bean
			public KeyExpiredListener keyExpiredListener() {
				return new KeyExpiredListener(this.redisMessageListenerContainer());
			}
		}
	
	# KeyExpiredListener 监听器
		import java.nio.charset.StandardCharsets;

		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;
		import org.springframework.data.redis.connection.Message;
		import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
		import org.springframework.data.redis.listener.RedisMessageListenerContainer;

		public class KeyExpiredListener extends KeyExpirationEventMessageListener {

			private static final Logger LOGGER = LoggerFactory.getLogger(KeyExpiredListener.class);

			public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
				super(listenerContainer);
			}

			@Override
			protected void doHandleMessage(Message message) {
				String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
				String key = new String(message.getBody(), StandardCharsets.UTF_8);
				LOGGER.info("redis key 过期：channel={},key={}", channel, key);
			}
		}

------------------------------------
Redis-发布订阅						|
------------------------------------
	# 创建 RedisMessageListenerContainer
		@Configuration
		public class RedisConfiguration {
			
			@Autowired 
			private RedisConnectionFactory redisConnectionFactory;
			
			@Autowired
			private ThreadPoolTaskExecutor threadPoolTaskExecutor;
			
			@Bean
			public RedisMessageListenerContainer redisMessageListenerContainer () {
				RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
				// 连接工厂
				redisMessageListenerContainer.setConnectionFactory(this.redisConnectionFactory);
				// 线程池
				redisMessageListenerContainer.setTaskExecutor(this.threadPoolTaskExecutor);
				// 监听器
				redisMessageListenerContainer.addMessageListener(new TestMessageListener(), Arrays.asList(new ChannelTopic("test")));
				return redisMessageListenerContainer;
			}
		}
	
	# 创建监听器
		* 实现接口:MessageListener
			public interface MessageListener {
				void onMessage(Message message, @Nullable byte[] pattern);
			}
		
		* 实现
			public class TestMessageListener implements MessageListener {
				private static final Logger LOGGER = LoggerFactory.getLogger(TestMessageListener.class);
				
				@Override
				public void onMessage(Message message, byte[] pattern) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("redis channel: channel={}, messgae={}, pattern={}", 
								new String(message.getChannel()), new String(message.getBody()), new String(pattern));
					}
				}
			}
	
	# 不同的Topic实现
		* Topic接口有2个实现
			public interface Topic {
				String getTopic();	
			}
		
		* ChannelTopic
			* 一次监听一个通道

		* PatternTopic
			* 可以一次监听N个通道
	
	# 发送消息, 通过 StringRedisTemplate 发送
		
		public void convertAndSend(String channel, Object message)

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(id);
		jsonArray.add(oldUser.getId());
		this.stringRedisTemplate.convertAndSend(RedisChannels.EXIPRE_SESSION, jsonArray.toJSONString());

------------------------------------
Redis - 自定义Template序列化		|
------------------------------------
	# 自定义 Templete 类,实现 RedisTemplate,泛型为key和Value
	# 实例化该类
		- 设置连接工厂
		- key的序列化类
		- value的序列化列
		- 其他redis数据结构的k/v序列化类

	# 简单的fastjson序列化
		public class JsonRedisTemplate extends RedisTemplate<String, JSONObject> {
		}

		@Bean
		public JsonRedisTemplate jsonRedisTemplate() {
			JsonRedisTemplate jsonRedisTemplate = new JsonRedisTemplate();
			jsonRedisTemplate.setConnectionFactory(this.redisConnectionFactory);
			jsonRedisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
			jsonRedisTemplate.setValueSerializer(new RedisSerializer<JSONObject>() {
				@Override
				public byte[] serialize(JSONObject t) throws SerializationException {
					return t.toJSONString().getBytes(StandardCharsets.UTF_8);
				}
				@Override
				public JSONObject deserialize(byte[] bytes) throws SerializationException {
					if(bytes == null) {
						return null;
					}
					return JSON.parseObject(new String(bytes, StandardCharsets.UTF_8));
				}
			});
			// jsonRedisTemplate.setHashKeySerializer(hashKeySerializer);
			// jsonRedisTemplate.setHashValueSerializer();
			return jsonRedisTemplate;
		}

------------------------------------
Redis-Redisson						|
------------------------------------
	# 使用Redsson作为客户端
		https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter#spring-boot-starter
		https://github.com/redisson/redisson/blob/master/redisson-spring-boot-starter/README.md

	# Maven
		<dependency>
			 <groupId>org.redisson</groupId>
			 <artifactId>redisson-spring-boot-starter</artifactId>
			 <version>3.11.3</version>
		 </dependency>
		
	# 配置
		spring.redis.database=
		spring.redis.host=
		spring.redis.port=
		spring.redis.password=
		spring.redis.ssl=
		spring.redis.timeout=
		spring.redis.cluster.nodes=
		spring.redis.sentinel.master=
		spring.redis.sentinel.nodes=

		# Redisson settings

		#path to redisson.yaml or redisson.json
		spring.redis.redisson.config=classpath:redisson.yaml

	# 修改缓存对象的序列化方式	
		import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.data.redis.cache.RedisCacheConfiguration;
		import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
		import org.springframework.data.redis.serializer.RedisSerializationContext;
		import org.springframework.data.redis.serializer.RedisSerializer;
		import org.springframework.data.redis.serializer.SerializationException;

		@Configuration
		public class CustomRedisCacheConfiguration {
			@Bean
			public RedisCacheConfiguration redisCacheConfiguration(){
				return RedisCacheConfiguration.defaultCacheConfig()
						 // 自定义序列化方式
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new RedisSerializer<Object>() {
							// 序列化为 JSON
							@Override
							public byte[] serialize(Object o) throws SerializationException {
								return new byte[0];
							}
							// 反序列化为对象
							@Override
							public Object deserialize(byte[] bytes) throws SerializationException {
								return null;
							}
						}))
						// 使用Jackson来序列化反序列化缓存对象
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
						// 使用Fastjson来序列号反序列化缓存对象
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()))
						;
			}
		}

-----------------------
GenericJackson2JsonRedisSerializer
-----------------------
	# Jackson 实现的通用 JSON 序列化/反序列化
	
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

		import com.fasterxml.jackson.databind.SerializationFeature;
		import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

		@Configuration
		public class GenericJackson2JsonRedisSerializerConfiguration {

			@Bean
			public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {

				GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer("@class");

				serializer = serializer.configure(config -> {
					config.registerModules(new JavaTimeModule());
					config.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				});

				return serializer;
			}
		}


------------------------------------
获取底层连接，实现高级用法
------------------------------------
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Test
	public void test() {
		// 从 ConnectionFactory 获取到连接
		try (RedisConnection connection = this.stringRedisTemplate.getConnectionFactory().getConnection()) {
			// 使用原生的 String 命令
			connection.stringCommands().set("key".getBytes(), "value".getBytes(), Expiration.keepTtl(), SetOption.UPSERT); // Expiration.keepTtl() 意思是保持 ttl
		}
	}


-----------------------
DefaultRedisScript
-----------------------
	# DefaultRedisScript 用于执行 Lua 脚本
		* Redis 采用相同的 Lua 解释器去运行所有命令，可以保证，脚本的执行是原子性的。
		* 作用就类似于加了 MULTI/EXEC
	

