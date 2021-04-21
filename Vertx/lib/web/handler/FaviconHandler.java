-----------------------------
FaviconHandler
-----------------------------
	# 网站图标handler： FaviconHandler extends Handler<RoutingContext> 
		* 默认会从classpath目录下加载： favicon.ico 文件


	long DEFAULT_MAX_AGE_SECONDS = 86400;
		* 默认缓存时间，1天
	
	static FaviconHandler create(Vertx vertx) 
	static FaviconHandler create(Vertx vertx, String path)
	static FaviconHandler create(Vertx vertx, String path, long maxAgeSeconds)
	static FaviconHandler create(Vertx vertx, long maxAgeSeconds)
