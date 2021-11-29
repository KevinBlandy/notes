------------------------------------
Redis-���ϵ�����					|
------------------------------------
	# �ĵ�
		https://docs.spring.io/spring-data/redis/docs/current/reference/html/#reference
	# ʹ��lettuce
	# ����
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
	
	# �����ļ�(������:org.springframework.boot.autoconfigure.data.redis.RedisProperties)
		# Redis���ݿ�������Ĭ��Ϊ0��
		spring.redis.database=0  
		# Redis��������ַ
		spring.redis.host=192.168.0.58
		# Redis���������Ӷ˿�
		spring.redis.port=6379  
		# Redis��������������(Ĭ��Ϊ��)
		spring.redis.password=  
		# ���ӳ����������(ʹ�ø�ֵ��ʾû������)
		spring.redis.lettuce.pool.max-active=8  
		# ���ӳ���������ȴ�ʱ��(ʹ�ø�ֵ��ʾû������)
		spring.redis.lettuce.pool.max-wait=-1  
		# ���ӳ��е�����������
		spring.redis.lettuce.pool.max-idle=8  
		# ���ӳ��е���С��������
		spring.redis.lettuce.pool.min-idle=0  
		# ���ӳ�ʱʱ��(����)
		spring.redis.timeout=2000

	# ʹ��
		* StringRedisTemplate
			* ��RedisTemplate������
			* һ����������ʹ����
		* ���jedis�ͻ����д���api�����˹����װ,��ͬһ���Ͳ�����װΪoperation�ӿ�
			ValueOperations	��K-V����
			SetOperations	set�������ݲ���
			ZSetOperations	zset�������ݲ���
			HashOperations	���map���͵����ݲ���
			ListOperations	���list���͵����ݲ���
		* demo
			@Autowired
			private StringRedisTemplate stringRedisTemplate;

			stringRedisTemplate.opsForValue();		//��ȡ������k-v��api
			stringRedisTemplate.opsForSet();		//��ȡ����set��api
	
	# �������Ŀͻ���
		ReactiveRedisTemplate
	
	# �ػ�
		* ��Ӧ�������а��� commons-pool2.jar ���Զ����� redis ���ӳ� ��Jedis Lettuce ��֧�֣�
		* �����Ҫ�رճػ���������

		spring.redis.jedis.pool.enabled=false 
		spring.redis.lettuce.pool.enabled=false 
		
			
------------------------------------
Redis-���ϼ�Ⱥ						|
------------------------------------	
	
	

------------------------------------
Redis- scan ���� keys *				|
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
Redis- ����key�ļ���				|
------------------------------------
	# redis���뿪������
		 notify-keyspace-events "Egx"  # ����key�Ĺ����¼�

	# configuration������
		@Configuration
		public class RedisConfiguration {
		 
			@Autowired 
			private RedisConnectionFactory redisConnectionFactory;

			@Autowired
			private ThreadPoolTaskExecutor threadPoolTaskExecutor;
			
			@Bean
			public RedisMessageListenerContainer redisMessageListenerContainer() {
				//������������
				RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
				//�������ӹ���
				redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
				// ����ִ�е��̳߳�
				redisMessageListenerContainer.setTaskExecutor(this.threadPoolTaskExecutor);
				return redisMessageListenerContainer;
			}
			
			// ����key�Ĺ��ڼ���
			@Bean
			public KeyExpiredListener keyExpiredListener() {
				return new KeyExpiredListener(this.redisMessageListenerContainer());
			}
		}
	
	# KeyExpiredListener ������
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
				LOGGER.info("redis key ���ڣ�channel={},key={}", channel, key);
			}
		}

------------------------------------
Redis-��������						|
------------------------------------
	# ���� RedisMessageListenerContainer
		@Configuration
		public class RedisConfiguration {
			
			@Autowired 
			private RedisConnectionFactory redisConnectionFactory;
			
			@Autowired
			private ThreadPoolTaskExecutor threadPoolTaskExecutor;
			
			@Bean
			public RedisMessageListenerContainer redisMessageListenerContainer () {
				RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
				// ���ӹ���
				redisMessageListenerContainer.setConnectionFactory(this.redisConnectionFactory);
				// �̳߳�
				redisMessageListenerContainer.setTaskExecutor(this.threadPoolTaskExecutor);
				// ������
				redisMessageListenerContainer.addMessageListener(new TestMessageListener(), Arrays.asList(new ChannelTopic("test")));
				return redisMessageListenerContainer;
			}
		}
	
	# ����������
		* ʵ�ֽӿ�:MessageListener
			public interface MessageListener {
				void onMessage(Message message, @Nullable byte[] pattern);
			}
		
		* ʵ��
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
	
	# ��ͬ��Topicʵ��
		* Topic�ӿ���2��ʵ��
			public interface Topic {
				String getTopic();	
			}
		
		* ChannelTopic
			* һ�μ���һ��ͨ��

		* PatternTopic
			* ����һ�μ���N��ͨ��
	
	# ������Ϣ, ͨ�� StringRedisTemplate ����
		
		public void convertAndSend(String channel, Object message)

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(id);
		jsonArray.add(oldUser.getId());
		this.stringRedisTemplate.convertAndSend(RedisChannels.EXIPRE_SESSION, jsonArray.toJSONString());

------------------------------------
Redis - �Զ���Template���л�		|
------------------------------------
	# �Զ��� Templete ��,ʵ�� RedisTemplate,����Ϊkey��Value
	# ʵ��������
		- �������ӹ���
		- key�����л���
		- value�����л���
		- ����redis���ݽṹ��k/v���л���

	# �򵥵�fastjson���л�
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
	# ʹ��Redsson��Ϊ�ͻ���
		https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter#spring-boot-starter
		https://github.com/redisson/redisson/blob/master/redisson-spring-boot-starter/README.md

	# Maven
		<dependency>
			 <groupId>org.redisson</groupId>
			 <artifactId>redisson-spring-boot-starter</artifactId>
			 <version>3.11.3</version>
		 </dependency>
		
	# ����
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

	# �޸Ļ����������л���ʽ	
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
						 // �Զ������л���ʽ
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new RedisSerializer<Object>() {
							// ���л�Ϊ JSON
							@Override
							public byte[] serialize(Object o) throws SerializationException {
								return new byte[0];
							}
							// �����л�Ϊ����
							@Override
							public Object deserialize(byte[] bytes) throws SerializationException {
								return null;
							}
						}))
						// ʹ��Jackson�����л������л��������
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
						// ʹ��Fastjson�����кŷ����л��������
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()))
						;
			}
		}
