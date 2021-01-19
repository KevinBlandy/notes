-----------------------------
spiders						 |
-----------------------------
	* spider 应该出现在 spiders python目录下
	* spider 必须继承自:scrapy.Spider
	* spider的parse要么返回 item,要么返回 Request,意思是要么处理数据,要么继续爬

-----------------------------
spiders-主要的属性和方法	 |
-----------------------------
	name
		* 定义spider名字的字符串
		* 例如,如果spider爬取 mywebsite.com ,该spider通常会被命名为 mywebsite

	allowed_domains
		* 包含了spider允许爬取的域名(domain)的列表,可选

	start_urls
		* 初始URL元祖/列表,当没有制定特定的URL时,spider将从该列表中开始进行爬取
		* 也可以是 

	start_requests(self)
		* 该方法必须返回一个 Request 实例的可迭代对象(iterable),该对象包含了spider用于爬取的第一个Request
		* 其实源码中就是对 start_urls 中的url属性进行迭代,并以 parse 为回调函数生成 Request

	parse(self, response)
		* 当请求url返回网页没有指定回调函数时,默认的Request对象回调函数
		* 用来处理网页返回的response,以及生成Item或者Request对象

	log(self, message[, level, component])
		* 使用 scrapy.log.msg() 方法记录(log)message
		* 更多数据请参见 logging
	
	closed(reason)
		* 当spider关闭时,该函数被调用
		* 该方法提供了一个替代调用 signals.connect()来监听 spider_closed 信号的快捷方式

-----------------------------
基本spiders	定义			 |
-----------------------------
	import scrapy
	class ItcastSpider(scrapy.Spider):

		# 指定爬虫的名称
		name = 'itcast'

		# 允许爬取的域
		allowed_domains = ['itcast.cn']

		# 开始的地址
		start_urls = ['http://itcast.cn/']

		# 爬取结果处理(self可以访问上面的所有属性,以及自定义的属性)
		def parse(self, response):	
			response.url				# 爬取时请求的url
			response.body				# 返回的html
			response.body_as_unicode()	# 返回的html unicode编码
		
		* name,allowed_domains 等属性,都是当前Spider实例对象(可以通过 self 访问)
		
-----------------------------
spiders	重新发起请求		 |
-----------------------------
	* 在爬取过程中,如果从响应中爬取出了连接,需要深度爬取,则返回 scrapy.Request() 实例对象

		def parse(self, response):	
			...
			# 解析出数据,交给pipe处理
			yield item

			...
			# 解析出url,重新发送给调度器,入队列,并且指定回调函数,就是当前函数
			yield scrapy.Request('http://....com',callback=self.parse)
	
	* scrapy.Request(url, callback=None, method='GET', headers=None, body=None,
                 cookies=None, meta=None, encoding='utf-8', priority=0,
                 dont_filter=False, errback=None, flags=None)
		* 第一个参数,就是提取出来的url
		* 关键字参数
			callback
				* 该连响应后,由谁进行处理(回调函数)

-----------------------------
Selectors选择器				 |
-----------------------------
	* response.selector,将获取到一个 response 初始化的类 Selector 的对象
	* 通过使用 response.selector.xpath()或response.selector.css() 来对 response 进行查询
	* Scrapy也提供了一些快捷方式来进行访问
		response.xpath
		response.css()
	
	* Scrapy Selectors 内置 XPath 和 CSS Selector 表达式机制

	* Selector有四个基本的方法,最常用的还是xpath

		xpath()		
			* 传入xpath表达式,返回该表达式所对应的所有节点的selector list列表
		extract()	
			* 序列化该节点为Unicode字符串并返回list
		css()		
			* 传入CSS表达式,返回该表达式所对应的所有节点的selector list列表,语法同 BeautifulSoup4
		re()		
			* 根据传入的正则表达式对数据进行提取,返回Unicode字符串list列表
		extract_first()
			* 从 extract() 结果集中获取第一个元素,不用担心数组越界异常,不存在返回 None
	
------------------------------------
爬虫命令参数						|
------------------------------------
	* 在运行爬虫时,可以通过 -a 选项为爬虫提供命令行参数
		scrapy crawl quotes -o quotes-humor.json -a tag=humor

	* 默认情况下,这些参数将传递给 Spider 的 __init__ 方法并成为爬虫的属性
		 tag = getattr(self, 'tag', None)	# 通过反射获取执行爬虫传递的参数

	
------------------------------------
Response							|
------------------------------------
	* 由框架构建
	* 属性
		url				# 爬取时请求的url
		body			# 返回http响应体

	* 方法
		body_as_unicode()	# 返回的html unicode编码
		urljoin()
		follow()
			* 返回一个 Request 实例对象

------------------------------------
Request								|
------------------------------------
	* 由自己创建,表示一个http请求对象
	* 构造函数
		Request(url, callback=None, method='GET', headers=None, body=None,
                 cookies=None, meta=None, encoding='utf-8', priority=0,
                 dont_filter=False, errback=None, flags=None)
		
		* 第一个参数,就是目标url
		* 关键字参数
			callback
				* 该连响应后,由谁进行处理(回调函数)