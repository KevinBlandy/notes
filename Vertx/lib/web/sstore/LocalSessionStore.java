---------------------
LocalSessionStore
---------------------
	# 本地Session存储接口： interface LocalSessionStore extends SessionStore

	# 默认使用 vertx.sharedData().getLocalMap() 存储数据

---------------------
static
---------------------
	static LocalSessionStore create(Vertx vertx) 
	static LocalSessionStore create(Vertx vertx, String sessionMapName)
	static LocalSessionStore create(Vertx vertx, String sessionMapName, long reaperInterval)


	long DEFAULT_REAPER_INTERVAL = 1000;
		* 多久扫描一次session，移除过期的会话

	String DEFAULT_SESSION_MAP_NAME = "vertx-web.sessions";
		* sharedData中的map名称
