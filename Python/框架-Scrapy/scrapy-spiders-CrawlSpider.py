----------------------------
CrawlSpider					|
----------------------------
	* scrapy.spiders.CrawlSpider
	* Spider子类,Spider类的设计原则是只爬取start_url列表中的网页
	* 而CrawlSpider类定义了一些规则(rule)来提供跟进link的方便的机制
	* 从爬取的网页中获取link并继续爬取的工作更适合
		
----------------------------
CrawlSpider-特有的属性		|
----------------------------	
	rules
		* 定义 Rule 集合,用于定义提取url的规则
	
-----------------------------
LinkExtractor				 |
-----------------------------
	* 用于从html结构体中提取出连接
	* from scrapy.linkextractors import LinkExtractor
	* 构造函数
		LinkExtractor(allow=(), deny=(), allow_domains=(), deny_domains=(), restrict_xpaths=(),
                 tags=('a', 'area'), attrs=('href',), canonicalize=False,
                 unique=True, process_value=None, deny_extensions=None, restrict_css=(),
                 strip=True)
			allow
				* 表示匹配的规则(解析href连接),是一个或者多个正则表达式(元组)
				* 如果该值为空,则所有的链接都会被提取
			deny
				* 也是一组正则规则,该规则命中的连接,不会被提取
			allow_domains
				* 会被提取的链接域
			deny_domains
				* 不会被提取的连接域
			restrict_xpaths
				* 使用xpath表达式,与allow共同过滤链接

	* 属性 & 方法
		extract_links(response)
			* 传入响应对象,进行link解析,返回 Link 对象集合
	* demo
		def parse(self, response):
			# 创建 extrator 对象(匹配规则)
			extrator = LinkExtractor(allow=('start=\d+',))
			# 通过 extract_links 来解析 response 对象,获取Link对象集合 
			links = extrator.extract_links(response)
			for i in links:
				print(i)	# Link(url='http://hr.tencent.com/position.php?tid=87&start=10#a', text='2', fragment='', nofollow=False)

-----------------------------
Rule						 |
-----------------------------
	* 用于定义怎么去处理匹配到的连接
	* 一个 Rule 里面会包含一个匹配规则(LinkExtractor),程序里面可以包含多个 Rules
	* 如果多个 Rule 匹配了相同的连接,则根据规则在集合中的定义顺序,第一个将会被使用
	* from scrapy.spiders import Rule
	* 构造函数
		Rule(link_extractor, callback=None, cb_kwargs=None, follow=None, process_links=None, process_request=identity)
			link_extractor
				* 表示一个匹配规则(LinkExtractor)
			callback
				* 回调函数名(str),每当从link_extractor中获取到连接的时候,都会作为参数传递给该回调函数
				* 注意,该函数名称不能与'parse'冲突
			follow
				* bool 值,指定了根据该规则提取出来的连接是否要跟进(打开连接,深度提取)
				* 如果 callback = None,该值默认为 True,否则该值为 False
			process_links
				* 以str形式,指定spider中哪个函数将会被调用,从匹配规则(LinkExtractor)中获取到链接列表
				* 就是处理提取到的连接的方法,处理完毕后返回连接列表
	
		
-----------------------------
Spider-demo					 |
-----------------------------
		
	from job import items
	from scrapy.linkextractors import LinkExtractor
	from scrapy.spiders import Rule
	from scrapy.spiders import CrawlSpider

	# 深度链接跟进
	class JobSpider(CrawlSpider):
		name = 'job'
		allowed_domains = ['hr.tencent.com']
		start_urls = ['http://hr.tencent.com/position.php']

		# url提取规则
		rules = [
			# 定义了一个url规则,正则,回调函数,是否要跟进连接
			Rule(LinkExtractor(allow = r'start=\d+'),callback = 'parseLink',follow = True)
		]

		# 定义回调
		def parseLink(self,response):
			trs = response.xpath("//tr[@class='even'] | //tr[@class='odd']")
			for i in trs:
				job = items.JobItem()
				job['name'] = i.xpath("./td/a/text()").extract()
				job['href'] = i.xpath("./td/a/@href").extract()
				job['type_'] = i.xpath('./td[2]/text()').extract()
				job['count'] = i.xpath('./td[3]/text()').extract()
				job['address'] = i.xpath('./td[4]/text()').extract()
				job['time'] = i.xpath('./td[5]/text()').extract()
				yield job