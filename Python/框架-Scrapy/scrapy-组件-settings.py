--------------------------------
settings						|
--------------------------------
	* 配置模块
--------------------------------
settings-模块配置项				|
--------------------------------

	BOT_NAME = 'webspider'
		* 爬虫名称,也就是项目名称
	
	LOG_FILE = 'my.log'
		* 指定日志输出文件
	
	LOG_LEVEL = 'WARNING'
		* 指定日志输出级别
			'CRITICAL': CRITICAL,
			'FATAL': FATAL,
			'ERROR': ERROR,
			'WARN': WARNING,
			'WARNING': WARNING,
			'INFO': INFO,
			'DEBUG': DEBUG,

	SPIDER_MODULES = ['webspider.spiders']

	NEWSPIDER_MODULE = 'webspider.spiders'


	USER_AGENT = 'webspider (+http://www.yourdomain.com)'

	ROBOTSTXT_OBEY = True
		* 爬虫是否要遵循 robots 协议

	CONCURRENT_REQUESTS = 32
		* 爬虫并发,允许爬虫同时发起的请求,默认:16

	DOWNLOAD_DELAY = 3
		* 下载延迟,单位是秒

	CONCURRENT_REQUESTS_PER_DOMAIN = 16
	CONCURRENT_REQUESTS_PER_IP = 16

	COOKIES_ENABLED = False
		* 是否启用cookie机制,默认开启

	TELNETCONSOLE_ENABLED = False

	DEFAULT_REQUEST_HEADERS = {
	   'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
	   'Accept-Language': 'en',
	}
		* 默认的http请求头,可以添加:'User-Agent',来欺骗服务器

	SPIDER_MIDDLEWARES = {
		'webspider.middlewares.WebspiderSpiderMiddleware': 543,
	}
		* 爬虫中间件,后面的值表示优先级,数值越小,优先级越高

	DOWNLOADER_MIDDLEWARES = {
		'webspider.middlewares.MyCustomDownloaderMiddleware': 543,
	}
		* 下载中间件,后面的值表示优先级,数值越小,优先级越高

	EXTENSIONS = {
		'scrapy.extensions.telnet.TelnetConsole': None,
	}

	ITEM_PIPELINES = {
		'webspider.pipelines.WebspiderPipeline': 300,
	}
		* 管道文件,他们决定了下载好的数据如果做处理,后面的值表示优先级,数值越小,优先级越高

	AUTOTHROTTLE_ENABLED = True
	AUTOTHROTTLE_START_DELAY = 5
	AUTOTHROTTLE_MAX_DELAY = 60
	AUTOTHROTTLE_TARGET_CONCURRENCY = 1.0
	AUTOTHROTTLE_DEBUG = False
	HTTPCACHE_ENABLED = True
	HTTPCACHE_EXPIRATION_SECS = 0
	HTTPCACHE_DIR = 'httpcache'
	HTTPCACHE_IGNORE_HTTP_CODES = []
	HTTPCACHE_STORAGE = 'scrapy.extensions.httpcache.FilesystemCacheStorage'

--------------------------------
settings-在程序中获取配置信息	|
--------------------------------
	from scrapy.utils.project import get_project_settings
	
	# 通过该方法获取指定名称的配置项
	get_project_settings().get('BOT_NAME')

