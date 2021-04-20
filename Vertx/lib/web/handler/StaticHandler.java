--------------------------
StaticHandler
--------------------------
	# 静态资源处理器handler接口： interface StaticHandler extends Handler<RoutingContext> 
	# 添加
		router.route("/static/*").handler(StaticHandler.create());
	
		* 当Vert.x首次在类路径中找到资源时，它会复制它到本地磁盘中
		* 如果要禁止这个机制，可以通过 FileSystemOptions 控制


--------------------------
static
--------------------------
	String DEFAULT_WEB_ROOT = "webroot";
	boolean DEFAULT_FILES_READ_ONLY = true;
	long DEFAULT_MAX_AGE_SECONDS = 86400; // One day
	boolean DEFAULT_CACHING_ENABLED = !WebEnvironment.development();
	boolean DEFAULT_DIRECTORY_LISTING = false;
	String DEFAULT_DIRECTORY_TEMPLATE = "META-INF/vertx/web/vertx-web-directory.html";
	boolean DEFAULT_INCLUDE_HIDDEN = true;
	long DEFAULT_CACHE_ENTRY_TIMEOUT = 30000; // 30 seconds
	String DEFAULT_INDEX_PAGE = "/index.html";
	int DEFAULT_MAX_CACHE_SIZE = 10000;
	boolean DEFAULT_ALWAYS_ASYNC_FS = false;
	boolean DEFAULT_ENABLE_FS_TUNING = true;
	long DEFAULT_MAX_AVG_SERVE_TIME_NS = 1000000; // 1ms
	boolean DEFAULT_RANGE_SUPPORT = true;
	boolean DEFAULT_ROOT_FILESYSTEM_ACCESS = false;
	boolean DEFAULT_SEND_VARY_HEADER = true;

	static StaticHandler create() 
	static StaticHandler create(String root)
	StaticHandler setAllowRootFileSystemAccess(boolean allowRootFileSystemAccess)
	StaticHandler setWebRoot(String webRoot)
		* 设置资源目录

	StaticHandler setFilesReadOnly(boolean readOnly)
		* 如果设置为true，则缓存在内存中的文件最后修改时间，永远不会更新，永远不会去磁盘检查
		* 默认为true
	
	StaticHandler setMaxAgeSeconds(long maxAgeSeconds)
		* 如果开起了客户端缓存，则可以设置浏览器缓存文件的时间，单位是秒
		* 默认是：DEFAULT_MAX_AGE_SECONDS， 1天

	StaticHandler setCachingEnabled(boolean enabled)
		* 启用/禁用客户端缓存
		* 启用缓存处理后，Vert.x-Web将在内存中缓存资源的上次修改日期，这样可以避免每次访问磁盘时都要检查实际的上次修改日期。
		* 缓存中的条目有一个到期时间，在此时间之后，将再次检查磁盘上的文件，并更新缓存中的条目。


	StaticHandler setDirectoryListing(boolean directoryListing)
		* 是否提供目录列表

	StaticHandler setIncludeHidden(boolean includeHidden)
		* 是否暴露隐藏文件

	StaticHandler setCacheEntryTimeout(long timeout)
		* 设置，内存中缓存的文件最后修改时间，失效时间。超过这个时间后，就会去IO磁盘重新读取。

	StaticHandler setIndexPage(String indexPage)
		* 设置默认页面的文件名称，默认： index.html

	StaticHandler setMaxCacheSize(int maxCacheSize)
		* 缓存在内存中的文件最后修改时间，最多缓存多少个？

	StaticHandler setHttp2PushMapping(List<Http2PushMapping> http2PushMappings)
	StaticHandler skipCompressionForMediaTypes(Set<String> mediaTypes)
	StaticHandler skipCompressionForSuffixes(Set<String> fileSuffixes)
	StaticHandler setAlwaysAsyncFS(boolean alwaysAsyncFS)
	StaticHandler setEnableFSTuning(boolean enableFSTuning)
	StaticHandler setMaxAvgServeTimeNs(long maxAvgServeTimeNanoSeconds)
	StaticHandler setDirectoryTemplate(String directoryTemplate)
		* 设置显示目录列表的模板

	StaticHandler setEnableRangeSupport(boolean enableRangeSupport)
	StaticHandler setSendVaryHeader(boolean varyHeader)
	StaticHandler setDefaultContentEncoding(String contentEncoding)
