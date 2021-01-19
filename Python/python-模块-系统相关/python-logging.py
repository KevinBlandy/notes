----------------------
logging					|
----------------------	
	* 日志相关
	* logging 记录器的获取
		1,如果不获取自定义的 Logger,则默认使用的是名为: 'rootLogger' 的日志记录器

	* logger 系统体系
		1,功能职责	
			logger 
				* 日志记录器,负责进行日志记录

			handler
				* 日志处理器,不同的hanlder,会根据不同方式处理日志
				* 一个 logger 可以有多个 handler
			
			Filter
				* 日志过滤器,可以配置一些过滤规则
				* 当过滤器的条件满足,logger 才会执行日志记录
				* 一个 logger 可以有多个 Filter

		2,体系图
			logger(日志记录器)
				|-handler(日志处理器)
					|-formatter(日志格式化模版)
					|-setLevel(日志等级)
				|-filter(日志过滤器)
				|-level(日志等级)


	* handlers
		# 系统预定义Handler
		StreamHandler 
		FileHandler 
		BaseRotatingHandler 
		RotatingFileHandler 
		TimedRotatingFileHandler 
		SocketHandler 
		DatagramHandler
		SMTPHandler 
		SysLogHandler
		NTEventLogHandler 
		MemoryHandler 
		HTTPHandler 
		WatchedFileHandler
		QueueHandler 
		NullHandler 
	

----------------------
logging-内置属性		|
----------------------	
	int DEBUG
		* 10
	int INFO
		* 20
	int WARNING
		* 30
	int ERROR
		* 40
	int CRITICAL
		* 50

----------------------
logging-模块函数		|
----------------------	
	None debug()
	None info()

	None warning()
		* 默认级别
	None error()
	None critical()
		* 日志输出,输出等级由低到高
	
	# Logger
	logging.Logger getLogger(loggername)
		* 获取一个指定名称的日志记录器
		* '多次获取同名的Logger,返回的都只是同一个'
		* 如果不指定名称,则返回名称为 'rootLogger' 的logger
		* 命名参数
			level	# 指定日志级别
	
	# Handler 相关
	logging.Formatter Formatter(format)
		* 获取一个格式化的日志信息的 Formatter,参数指定格式化模版
		* 命名参数
			fmt		# 指定格式化模版
			datefmt	# 指定日期格式化的模版
		
	logging.FileHandler FileHandler(logfile)
		* 获取一个文件Handler,参数指定日志文件地址
		* 命名参数
			encoding	# 指定编码格式
		
	logging.StreamHandler StreamHandler()
		* 获取一个标准IO输出的 Handler
	
	#  Filter
	logging.Filter filter()
		* 获取一个 logger filter
		* 参数指定 logger 的过滤规则
		* 过滤规则,以字符串形式指定,允许执行日志记录的记录器名称,多个,使用 '.'分隔
			filter = logging.Filter('mylogger.child1.child2')
			
----------------------
logging.Logger			|
----------------------	
	None debug()
	None info()

	None warning()
		* 默认级别
	None error()
	None critical()
		* 日志输出,输出等级由低到高

	addHandler(Handler)
		* 添加一个 Handler 到当前日志记录器
	
	addFilter(logging.Filter)
		* 添加一个 filter 到当前日志记录器
		* 只有满足 filter 过滤规则的记录器,才会执行日志记录
		
	None setLevel(level)
		* 设置当前日志记录器的日志级别

	basicConfig()
		* 对当前日志记录器,进行基本的配置
		* 命名参数
			level
				* 设置日志级别,logging 模块具备5大日志级别的常量

			format
				* 格式化日志信息,使用字符串的:字典关键字格式化
				* 关键字
					name		# logger的名称
					asctime		# 日志触发时间
					filename	# Python脚本文件名称
					lineno		# 日志触发行号
					levelname	# 日志级别
					levelno		# 日志级别的数字信息
					pathname	# 日志触发函数的模块,完整路径(有可能为空)
					module		# 日志触发的模块名
					funcName	# 日志触发的函数名
					message		# 日志正文
				* demo
					"时间:%(asctime)s 文件名称:%(filename)s 行号:%(lineno)s 日志级别:%(levelname)s 日志正文:%(message)s"
					时间:2017-06-11 18:17:40 文件名称:Main.py 行号:18 日志级别:CRITICAL 日志正文:critical
			
			datefmt
				* 日期格式化样式
			
			filename
				* 输出到指定的文件
				* 当指定了文件后,日志不会输出到标准输出流(屏幕)
			
			filemode
				* 输出文件的IO模式
					a	# 默认模式,添加
					w	# 写模式

----------------------
logging.handler			|
----------------------	
	setFormat(logging.Formatter)
		* 设置日志信息的 Formatter
	
	setLevel(level)
		* 设置Hanlder的日志等级
		
----------------------
logging-Demo			|
----------------------	
# 获取Logger,单例模式
	import logging
	def get_logger(name,*,file,encoding,**kws):
		logger = None
		def func():
			nonlocal logger
			if(logger):
				return logger
			logger = logging.getLogger(name)
			logger.setLevel(kws['level'] if 'level' in kws else logging.DEBUG)
			
			streamHandler = logging.StreamHandler()
			fileHandler = logging.FileHandler(file,encoding = encoding)
			
			formatter = logging.Formatter(fmt = "时间:%(asctime)s 文件名称:%(filename)s 行号:%(lineno)s 日志级别:%(levelname)s 日志正文:%(message)s",datefmt = "%Y-%m-%d %H:%M:%S")
			
			streamHandler.setFormatter(formatter)
			fileHandler.setFormatter(formatter)
			
			logger.addHandler(streamHandler)
			logger.addHandler(fileHandler)
			print('创建logger')
			return logger
		return func

	get_logger = get_logger("vin",**{'file':"E:\\python.log",'encoding':"UTF--8",'level':logging.DEBUG})

	print(get_logger())
	print(get_logger())
	print(get_logger())
	print(get_logger())
	# 创建logger
	# <Logger vin (DEBUG)>
	# <Logger vin (DEBUG)>
	# <Logger vin (DEBUG)>
	# <Logger vin (DEBUG)>