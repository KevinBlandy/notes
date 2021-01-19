--------------------
日志记录			|
--------------------
	* 0.3 新版功能,Flask 就已经预置了日志系统
		
	* 附带的 logger 是一个标准日志类 Logger ,所以更多信息请查阅 logging 的文档 
	
	from flask import Flask

	app = Flask(__name__)

	app.logger.debug('A value for debugging')

	app.logger.warning('A warning occurred (%d apples)', 42)

	app.logger.error('An error occurred')
