--------------------------
LoggerHandler
--------------------------
	# 请求日志接口： interface LoggerHandler extends Handler<RoutingContext>

	LoggerFormat DEFAULT_FORMAT = LoggerFormat.DEFAULT;
		* 默认的格式化
	
	static LoggerHandler create()
	static LoggerHandler create(LoggerFormat format)
	static LoggerHandler create(boolean immediate, LoggerFormat format) 
		* 创建处理器
			immediate	如果在请求到达时立即进行记录，则为true。
			format		重新制定format

	
	LoggerHandler customFormatter(Function<HttpServerRequest, String> formatter)
		* 格式化日志抽象方法
	
--------------------------
LoggerFormat
--------------------------
	# 格式会枚举
		DEFAULT			默认
		SHORT			简单
		TINY			最小
		CUSTOM			自定义
	

	