---------------------------
log
---------------------------
	# 日志后端选择
		* 后端由设置的 vertx.logger-delegate-factory-class-name 系统属性表示，或者是
		* 当在类路径下存在 vertx-default-jul-logging.properties 文件时，则使用JDK logging
		* 或者是类路径中存在以下实现，按照以下优先顺序进行选择
			SLF4J
			Log4J
			Log4J2
		
		* 默认使用JDK日志记录
	
	# 强制设置netty的日志框架为log4j2
		InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);