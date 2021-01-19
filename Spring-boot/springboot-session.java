----------------------------
session						|
----------------------------
	# 参考
		https://docs.spring.io/spring-session/docs/current/reference/html5/
	
	# 依赖
		<dependency>
			<groupId>org.springframework.session</groupId>
			<artifactId>spring-session-data-redis</artifactId>
		</dependency>
	
	# 注解驱动 @EnableRedisHttpSession (不是必须的)
		int maxInactiveIntervalInSeconds() default MapSession.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS;
			* session的最大存活时间, 默认是秒

		String redisNamespace() default RedisOperationsSessionRepository.DEFAULT_NAMESPACE;
			* redis的存储空间key名称

		RedisFlushMode redisFlushMode() default RedisFlushMode.ON_SAVE;
			* 刷新模式, 枚举
				ON_SAVE
					* 只有当 SessionRepository.save(Session)方法被调用时, 才会将session中的数据同步到redis中.
					* 在web 应用中, 当请求完成响应后, 才开始同步. 也就是说在执行response 之前session数据都是缓存在本地的.

				IMMEDIATE
					* 实时同步session 数据到redis. 
					* 当执行SessionRepository.createSession()时, 会将session数据同步到redis中; 
					* 当对session的attribute进行set/remove 等操作时, 也会同步session中的数据到redis中

		String cleanupCron() default RedisHttpSessionConfiguration.DEFAULT_CLEANUP_CRON;
			* 执行session清理的cron表达式
			* 默认: 0 * * * * * (一分钟执行一次)

	
	# Cookie信息的自定义
		* 还是可以通过: server.servlet.session.cookie 来控制Cookie的名称属性等信息
		* 也可以通过代码, 自动配置: CookieSerializer

		@Bean
		public CookieSerializer cookieSerializer() {
			DefaultCookieSerializer serializer = new DefaultCookieSerializer();
			serializer.setCookieName("JSESSIONID"); 
			serializer.setCookiePath("/"); 
			serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
			return serializer;
		}
			


	# 自定义Session的解析方式
		* Session的解析依赖于一个接口: HttpSessionIdResolver

			CookieHttpSessionIdResolver	使用Cookie(默认)
			HeaderHttpSessionIdResolver	使用Header

			List<String> resolveSessionIds(HttpServletRequest request)
				* 根据请求读取到会话id

			void setSessionId(HttpServletRequest request, HttpServletResponse response,String sessionId)
				* 设置会话id

			void expireSession(HttpServletRequest request, HttpServletResponse response);
				* 过期会话id

		* 通过自定义配置类来实现自定义的解析
			@Bean
			public HttpSessionIdResolver httpSessionIdResolver() {
				return HeaderHttpSessionIdResolver.xAuthToken();  // 使用 X-Auth-Token 解析Cookie
			}
		
		* HeaderHttpSessionIdResolver 有N个工厂方法返回不同的实现

			HeaderHttpSessionIdResolver xAuthToken()
				* 使用: X-Auth-Token 头作为session的id

			HeaderHttpSessionIdResolver authenticationInfo()
				* 使用: Authentication-Info 头作为session的id

		* 也可以自定义请求头, 通过构造方法创建
			HeaderHttpSessionIdResolver(String headerName)

			
	# Redis的key结构
		spring:session:expirations:1570672200000
		spring:session:index:org.springframework.session.FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME:1
		spring:session:sessions:d82bf2bb-deb3-474c-89bb-96c2bfa646c4
		spring:session:sessions:expires:94b2ce1f-053e-4c20-a8b7-f4d69102a114


			
----------------------------
配置						|
----------------------------
# 配置类: RedisSessionProperties

spring:
  session:
    timeout: 1800
		* 会话的超时时间, 单位是秒,默认: 1800
		* 如果不设置, 默认使用: server.servlet.session.timeout
    store-type: REDIS
		* 指定session的存储方式, 枚举
			REDIS
			MONGODB
			JDBC
			HAZELCAST
			NONE
    redis:
      namespace: "spring:session"
      flush-mode: IMMEDIATE
      cleanup-cron: "0 * * * * *"

	
	* 如果使用了注解: @EnableRedisHttpSession, 则该配置失效, 默认使用注解的属性配置
	* 使用配置, 不需要手动添加注解


-------------------------------------
SessionRepository<S extends Session> |
-------------------------------------
	# Session的管理接口
		S createSession();
			* 创建一个
		void save(S session);
			* 存储/创建
		S findById(String id);
			* 根据id检索
		void deleteById(String id);
			* 根据id删除
	
	# 子类实现
		SessionRepository
			|-MapSessionRepository
			|-FindByIndexNameSessionRepository
				|-RedisOperationsSessionRepository
				|-RedissonSessionRepository

--------------------------------
FindByIndexNameSessionRepository|
--------------------------------
	# 通俗的说, 就是可以根据用户获取到它的Session
	# 用户登录后要设置一个数据到Session来存储关联信息
		String username = "username";
		this.session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);

		* 一般可以用ID之类的数据关联, 不一定是name
	
	# 根据用户信息检索用户
		@Autowired
		FindByIndexNameSessionRepository<? extends Session> sessions;

		@RequestMapping("/")
		public String index(Principal principal, Model model) {
			Collection<? extends Session> usersSessions = this.sessions.findByPrincipalName(principal.getName()).values();
			model.addAttribute("sessions", usersSessions);
			return "index";
		}
	
	# FindByIndexNameSessionRepository 的方法和属性

		String PRINCIPAL_NAME_INDEX_NAME = FindByIndexNameSessionRepository.class.getName().concat(".PRINCIPAL_NAME_INDEX_NAME");

		Map<String, S> findByIndexNameAndIndexValue(String indexName, String indexValue);
			* 根据指定的key/value检索session

		default Map<String, S> findByPrincipalName(String principalName) {
			return findByIndexNameAndIndexValue(PRINCIPAL_NAME_INDEX_NAME, principalName);
		}
			
	
	# 原理
		* FindByIndexNameSessionRepository 本质上是提供了一个api, 可有根据key和value检索到session
			findByIndexNameAndIndexValue(key, value)
		
		* 可以借助于这个功能, 可以实现用户和会话的绑定

	

----------------------------
事件监听						|
----------------------------
	# Session的相关spring事件
		SessionCreatedEvent				创建
		SessionDestroyedEvent
			|-SessionExpiredEvent		过期
			|-SessionDeletedEvent		删除(用户主动 invalidate())
		
		* 过期和删除事件, 依赖于REDIS的通知事件
			notify-keyspace-events "Egx"
		
		* 如果使用了: @EnableRedisHttpSession, SessionMessageListener 则会自动完成管理和启用必要的Redis Keyspace事件
		* 在安全的Redis环境中, 一般会禁用config命令 (这意味着Spring Session无法为配置Redis Keyspace事件)
		* 要禁用自动配置, 添加配置
			@Bean
			public static ConfigureRedisAction configureRedisAction() {
				return ConfigureRedisAction.NO_OP;
			}
	
	# 使用Spring的Event监听
		@Component
		public class SpringSessionListener {
			
			private static final Logger LOGGER = LoggerFactory.getLogger(SpringSessionListener.class);
			
			@EventListener(SessionCreatedEvent.class)
			public void sessionCreatedEvent(SessionCreatedEvent sessionCreatedEvent) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("创建了新的session:{}", sessionCreatedEvent.getSessionId());
				}
			}
			
			@EventListener(SessionExpiredEvent.class)
			public void sessionExpiredEvent(SessionExpiredEvent sessionCreatedEvent) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("session到期:{}", sessionCreatedEvent.getSessionId());
				}
			}
			
			@EventListener(SessionDeletedEvent.class)
			public void sessionDeletedEvent(SessionDeletedEvent sessionCreatedEvent) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("删除了session:{}", sessionCreatedEvent.getSessionId());
				}
			}
		}
	
	# 也可以使用 Servlet的监听器
		* 需要添加Bean到ioc, 通过这个bean的构造函数, 添加多个 HttpSessionListener 实现
			SessionEventHttpSessionListenerAdapter
			SessionEventHttpSessionListenerAdapter(List<HttpSessionListener> listeners)
		
		* 但是这个Bean其实已经帮我们自动添加了, 我没再次添加会导致异常
		* 曲线救国, 从IOC里面读取到这个bean, 通过反射, 对私有属性 listeners 添加监听器
			@Configuration
			public class SpringSessionConfiguration {
				
				private static final Logger LOGGER = LoggerFactory.getLogger(SpringSessionConfiguration.class);
				
				@Autowired SessionEventHttpSessionListenerAdapter sessionEventHttpSessionListenerAdapter;
				
				@PostConstruct
				public void addHttpSessionListener() {
					try {
						Field field = SessionEventHttpSessionListenerAdapter.class.getDeclaredField("listeners");
						field.setAccessible(Boolean.TRUE);
						
						@SuppressWarnings("unchecked")
						List<HttpSessionListener> listeners = (List<HttpSessionListener>) field.get(sessionEventHttpSessionListenerAdapter);
						listeners.add(new SessionListener());
						
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("添加SESSION监听器");
						}
						
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				
				
			//	@Bean
			//	public SessionEventHttpSessionListenerAdapter sessionEventHttpSessionListenerAdapter() {
			//		SessionEventHttpSessionListenerAdapter sessionEventHttpSessionListenerAdapter = new SessionEventHttpSessionListenerAdapter(Arrays.asList(new SessionListener()));
			//		return sessionEventHttpSessionListenerAdapter;
			//	}
			}
	
	# BUG
		* 使用 redisson 作为redis的客户端(redisson-spring-boot-starter), 发现不能监听到SESSION的过期事件, 版本号: 3.11.4

---------------------------
动态的设置cookie的实现		|
----------------------------

# 自定义cookie的序列化实现

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.util.StringUtils;

// @Component
public class DynamicCookieMaxAgeCookieSerializer extends DefaultCookieSerializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicCookieMaxAgeCookieSerializer.class);
	
	public static final String COOKIE_MAX_AGE = "cookie.max-age";
	
	@Value("${server.servlet.session.cookie.max-age}")
	private Integer cookieMaxAge;
	
	@Override
	public void writeCookieValue(CookieValue cookieValue) {
		
		// if ("".equals(this.cookieValue) this.cookieMaxAge = 0;
		if (!StringUtils.isEmpty(cookieValue.getCookieValue())) {
			
			HttpServletRequest request = cookieValue.getRequest();
			
			// 从request域读取到cookie的maxAge属性
			Object attribute = request.getAttribute(COOKIE_MAX_AGE);
			if (attribute != null) {
				cookieValue.setCookieMaxAge((int) attribute);
			} else {
				cookieValue.setCookieMaxAge(this.cookieMaxAge);
			}
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("动态设置cooke.max-age={}", cookieValue.getCookieMaxAge());
			}
		}
		
		super.writeCookieValue(cookieValue);
	}
}


# 配置到IOC
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;

import com.video.manager.spring.session.DynamicCookieMaxAgeCookieSerializer;

@Configuration
public class SpringSessionConfiguration {
	
	@Value("${server.servlet.session.cookie.name}")
	private String cookieName;
	
	@Value("${server.servlet.session.cookie.secure}")
	private Boolean cookieSecure;
	
//	@Value("${server.servlet.session.cookie.max-age}")
//	private Integer cookieMaxAge;
	
	@Value("${server.servlet.session.cookie.http-only}")
	private Boolean cookieHttpOnly;

	@Value("${server.servlet.session.cookie.same-site}")
	private String cookieSameSite;
	
	@Bean
	public CookieSerializer cookieSerializer() {
		DynamicCookieMaxAgeCookieSerializer serializer = new DynamicCookieMaxAgeCookieSerializer();
		serializer.setCookieName(this.cookieName);
		// serializer.setCookieMaxAge(this.cookieMaxAge);
		serializer.setSameSite(this.cookieSameSite);
		serializer.setUseHttpOnlyCookie(this.cookieHttpOnly);
		serializer.setUseSecureCookie(this.cookieSecure);
		return serializer;
	}
}



# 登录的逻辑

// 失效旧会话 & 创建新会话
httpSession.invalidate();
httpSession = request.getSession();

if (!remember) {
	// 非“记住我”的设置下，cookie生命周期设置为-1 & session缓存30分钟
	request.setAttribute(DynamicCookieMaxAgeCookieSerializer.COOKIE_MAX_AGE, -1);
	httpSession.setMaxInactiveInterval(60 * 30);
}

