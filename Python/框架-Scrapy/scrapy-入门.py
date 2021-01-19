----------------------------
scrapy-入门					|
----------------------------
	* 学习地址
		http://www.cnblogs.com/-E6-/p/7211025.html
		http://scrapy-chs.readthedocs.io/zh_CN/latest/index.html#
		https://docs.scrapy.org/en/latest/index.html				# 官方文档

	* 这东西使用了 Twistid 异步网络通信框架来处理网络连接(主要对手就是:Tornado)

----------------------------
scrapy-安装					|
----------------------------
	* pip 安装
		pip install scrapy

	* 处理 building 'twisted.test.raiser' extension
		error: Microsoft Visual C++ 14.0 is required. Get it with "Microsoft Visual	C++ Build Tools": http://landinghub.visualstudio.com/visual-cpp-build-tools
		
		1,从网站下载whl文件
			https://www.lfd.uci.edu/~gohlke/pythonlibs/#twisted

			Twisted
				Twisted-17.9.0-cp36-cp36m-win_amd64.whl
				* cp36		表示python版本
				* win_amd64	64位处理器系统
		
		2,执行安装whl
			pip install Twisted-17.9.0-cp36-cp36m-win_amd64.whl
		
		3,再次执行安装scrapy
			pip install  scrapy

----------------------------
scrapy-安装(Linux)			|
----------------------------
	* 各种坑
	* http://blog.csdn.net/u012949658/article/details/55001179


----------------------------
scrapy-架构组件一览			|
----------------------------	
	Scrapy Engine(引擎)
		* 中央大脑,负责组件之间的调度
	Spider
		* 
	Spiders Middlewares(Spider中间件)
		* 是一个可以定义扩这操作引擎和Spider中间通信的功能组件
	ItemPipeline(通道)
		* 它负责处理Spider中获取的Item,并且进行后期处理(分析,过滤,存储)
	Downloader Middlewares(下载中间件)
		* 可以当作是一个可以自定义扩展下载功能的组件
	Downloader(下载器)
		* 负责下载引擎发送的所有Requests请求
		* 病并且把响应结果交还给引擎,由引擎交还给Spider来处理
	Scheduler(调度器)
		* 负责接受引擎发送过来的request请求,并且按照一定的方式整理队列:入队
		* 当引擎需要的时候,还会把request交还给引擎

	* 执行流程
		1,Spiders把请求丢给引擎
		2,引擎把请求丢给Scheduler
		3,Scheduler把请求丢给Dowmloader去执行
		4,Dowmloader把执行的结果返回给Spiders
			* 如果返回的是数据,则丢给ItemPipeline去处理数据
			* 如果返回的是连接,则重复1步骤
		
----------------------------
scrapy-开始四部曲			|
----------------------------
	1,新建项目
		scrapy startproject [name]

		* 创建好的目录结构
			name
				|-name
					|-spiders				# 爬虫存放目录
						|-__init__.,py
					|-__init__.,py	
					|-items.py				# 数据模型定义
					|-middlewares.py		# 
					|-pipelines.py			# 数据处理管道
					|-settings.py			# 配置模块
				|-scrapy.cfg
	
	2,明确目标(编写items.py),明确要抓取的目标
	3,制作爬虫(spiders/xxsipder.py),制作爬虫开始爬取网页
	4,存储内容(pipelines.py),设计管道存储爬取内容

	

		
