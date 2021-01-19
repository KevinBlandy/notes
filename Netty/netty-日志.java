---------------------------
日志					   |
---------------------------
	# 日志Handler,它继承:ChannelDuplexHandler
		LoggingHandler
	
	# 构造函数
		LoggingHandler(LogLevel level) 

		* level 日志级别枚举:LogLevel(默认 DEBUG)
			TRACE(InternalLogLevel.TRACE)
			DEBUG(InternalLogLevel.DEBUG) 
			INFO(InternalLogLevel.INFO)
			WARN(InternalLogLevel.WARN)
			ERROR(InternalLogLevel.ERROR)
	
	
	# 只需把日志Handler添加到 pipeline 中


	# 设置netty的loggerFactory来使用不同的日志框架
		* 使用静态方法:InternalLoggerFactory.setDefaultFactory(InternalLoggerFactory defaultFactory)

		InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
	
