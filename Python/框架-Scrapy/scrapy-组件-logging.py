----------------------------
loging						|
----------------------------
	* Scrapy提供了log功能,可以通过 scrapy.log 模块使用
	* log服务必须通过显示调用 scrapy.log.start() 来开启，以捕捉顶层的Scrapy日志消息
		scrapy.log.start(logfile=None, loglevel=None, logstdout=None)
			logfile
				* 指定日志文件
			loglevel
				* 指定日志级别
			logstdout
				* 如果为 True , 所有应用的标准输出(包括错误)将会被记录(logged instead)
				* 例如.调用 print ('hello')，则'hello' 会在Scrapy的log中被显示,如果被忽略,则 LOG_STDOUT 设置会被使用

	* Scrapy提供5层logging级别
		scrapy.log.CRITICAL	严重错误的Log级别

		scrapy.log.ERROR	错误的Log级别 Log level for errors

		scrapy.log.WARNING	警告的Log级别 Log level for warnings

		scrapy.log.INFO		记录信息的Log级别(生产部署时推荐的Log级别)

		scrapy.log.DEBUG	调试信息的Log级别(开发时推荐的Log级别)
	
	* 使用
		from scrapy import log
		log.msg("This is a warning", level=log.WARNING)
	
	* 在spider中添加log的推荐方式是使用Spider的 log() 方法
	* 该方法会自动在调用 scrapy.log.msg() 时赋值 spider 参数,其他的参数则直接传递给 msg() 方法

	* '以上都是被标识为过时(废弃)的了... ...'

----------------------------
loging						|
----------------------------
	import logging
	logging.warning("This is a warning")
	logging.debug()

	logging.log(logging.WARNING, "This is a warning")

	* 在scrapy中使用
		self.logger.warning('警告信息')

