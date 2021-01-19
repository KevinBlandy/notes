------------------------
Logger					|
------------------------
	* jdk级别的logger	
	* 关系图谱(跟python差不多)
		logger				日志记录器
			|-handler		处理器
				|formatter	格式化器
			|filter			过滤器

	* 涉及组件
		Logger
			* 日志记录器
		LogRecord
			* 一个日志对象,包含了消息,时间戳,类.等等日志的信息
		Filter
			* 日志过滤器,控制哪些日志要输出
		Formatter
			* 日志格式化,输出自己想要的日志信息
		Handler
			* 日志控制器,用于控制日志的输出

------------------------
Logger-静态方法/属性	|
------------------------
	GLOBAL_LOGGER_NAME = "global"

	Logger getGlobal()
		* 获取全局的一个日志记录器对象

	Logger getAnonymousLogger()
	Logger getAnonymousLogger(String resourceBundleName)
		
	Logger getLogger(String name) 
		* 获取指定名称的日志记录器

	Logger getLogger(String name, String resourceBundleName)
		* 获取指定名称的日志记录器,并且指定资源包(国际化)的名称

------------------------
Logger-实例方法/属性	|
------------------------
	void setLevel(Level newLevel)
		* 设置日记级别,参数就是 Level 实例
		* 包含一下日志级别
			
			Level.SEVERE;
			Level.WARNING;
			Level.INFO;
			Level.CONFIG;
			Level.FINE;
			Level.FINER;
			Level.FINEST;
			Level.ALL;
			Level.OFF;
			
		* 也可以使用 Level 的静态 parse(String name) api来获取指定名称的日志级别实例
	
		void fine("");
        void finer("");
        void finest("");
        void config("");
        void info("");
        void severe("");
        void warning("");
			* 以上api表示不同级别的日志输出
		
		void log(Level level, String msg)
			* 以指定的日志级别输出日志
		
		void log(LogRecord record)
			* 以日志记录器对象的形式记录一个日志
			* LogRecord 属性/方法
				record.getLevel();			//获取日志级别
                record.getMessage();		//获取日志正文
                record.getMillis();			//获取时间戳
                record.getClass();			
				record.getSequenceNumber();	//获取日志的唯一序列号
                record.getParameters();
                record.getLoggerName();
                record.getSourceClassName();
		
		throwing(String sourceClass, String sourceMethod, Throwable thrown)
			* 一般用于在 catch 内部,发生异常的时候进行日志记录

		void setUseParentHandlers(boolean useParentHandlers)
			* 是否使用父级logger的hanlder处理器
		
		void addHandler(Handler handler)
			* 添加一个hanlder到logger
			* handler 默认添加一个 ConsoleHandler ,也就数以 System.err 的形式输出到屏幕
			* 可以通过扩展 Handler/StreamHandler 类来自定义处理器
			* 系统预定义的handler
				FileHandler
					* new FileHandler("c:\\log.xml")
					* 保存日志到指定文件的handler,以xml形式保存
					* FileHandler 实例的方法/属性
						void setEncoding(String encoding)
							* 设置字符集
						void setFormatter(Formatter newFormatter)
							* 设置一个日志格式化formatter,来生成不格式的日志
							* 也可以自己扩展 Formatter 类
							* Formatter 只用实现一个方法,把一个日志记录对象,转换为 String
								@Override
								public String format(LogRecord record) {
									return null;
								}

						void publish(LogRecord record)
							* 添加一个日志记录对象
			
		void setFilter(Filter newFilter) 
			* 设置一个过滤器
			* Filter 是一个接口,只有一个抽象方法,用于判断是否要记录日志
				public boolean isLoggable(LogRecord record);
		

