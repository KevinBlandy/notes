---------------------------------
SessionHandler
---------------------------------
	# session实现的handler接口：  SessionHandler extends Handler<RoutingContext> 

	# Session存储的实现
		


---------------------------------
this
---------------------------------
	SessionHandler setSessionTimeout(long timeout);
	SessionHandler setNagHttps(boolean nag);
	SessionHandler setCookieSecureFlag(boolean secure);
	SessionHandler setCookieHttpOnlyFlag(boolean httpOnly);
	SessionHandler setSessionCookieName(String sessionCookieName);
	SessionHandler setSessionCookiePath(String sessionCookiePath);
	SessionHandler setMinLength(int minLength);
	SessionHandler setCookieSameSite(CookieSameSite policy);
	SessionHandler setLazySession(boolean lazySession);
	SessionHandler setCookieMaxAge(long cookieMaxAge);
	@Deprecated
	SessionHandler setAuthProvider(AuthProvider authProvider);
	SessionHandler flush(RoutingContext ctx, Handler<AsyncResult<Void>> handler);
	SessionHandler flush(RoutingContext ctx, boolean ignoreStatus, Handler<AsyncResult<Void>> handler);
	default Future<Void> flush(RoutingContext ctx)
	default Future<Void> flush(RoutingContext ctx, boolean ignoreStatus)
	SessionHandler setCookieless(boolean cookieless)

	Session newSession(RoutingContext context)

	Future<Void> setUser(RoutingContext context, User user)
	default SessionHandler setUser(RoutingContext context, User user, Handler<AsyncResult<Void>> handler)

---------------------------------
static
---------------------------------
	String DEFAULT_SESSION_COOKIE_NAME = "vertx-web.session";
		* 默认cookie的名称

	String DEFAULT_SESSION_COOKIE_PATH = "/";
		* 默认cookie的路径

	long DEFAULT_SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes
		* 默认会话失效时间

	boolean DEFAULT_NAG_HTTPS = true;

	boolean DEFAULT_COOKIE_HTTP_ONLY_FLAG = false;
	boolean DEFAULT_COOKIE_SECURE_FLAG = false;
	int DEFAULT_SESSIONID_MIN_LENGTH = 16;
		* sessionId长度

	boolean DEFAULT_LAZY_SESSION = false;
		* 是否延迟创建session

	static SessionHandler create(SessionStore sessionStore)
		* 使用指定的sotre创建SessionHandler