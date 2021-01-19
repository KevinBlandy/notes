----------------------------
Log4j-入门					|
----------------------------
	# log for (4) java


	log4j2	使用.xml来配置
	log4j1	使用.properties

----------------------------
Log4j-日志级别				|
----------------------------
	fatal(致命的)
	error
	warn
	info
	debug 
	trace(堆栈)

----------------------------
Log4j-Log4j1				|
----------------------------
	1,在日志类中获取到logger对象
		Logger logger = Logger.getLogger(LogDemo.class);
	2,在需要输出的地方,进行不同级别的日志输出
		logger.fatal("致命的错误");
		logger.error("error");
		logger.warn("警告");
		logger.info("INFO");
		logger.debug("DEBUG");
		logger.trace("堆栈");

	3,定义配置文件[常规输出]
		log4j.rootLogger=ERROR,demo
			# 设置日志的级别,以及激活哪些配置好的信息

		log4j.appender.demo=org.apache.log4j.ConsoleAppender
			# 定义一个追加器
			# ConsoleAppender:指定日志要输出到控制台
			# demo:其实是知道名字,可以自己定义

		log4j.appender.demo.Target=System.err
			# 使用上面定义的console的:Target属性
			# 来定义输出的的格式/System.err或者out

		log4j.appender.demo.layout=org.apache.log4j.PatternLayout
			# 使用上面定义的console的:layout属性
			#	来定义布局
			#	PatternLayout:一种布局

		log4j.appender.demo.layout.ConversionPattern=[%-5p][%d{yyy-MM-dd HH:mm:ss}] %C %L %m%n
			# 使用上面定义的console的:layout属性的:ConversionPattern	模式来定义输出格式
	
	4,定义配置文件[输出到文本]
		log4j.appender.demo=org.apache.log4j.FileAppender
			# 定义追加器,是输出到文本的
		
		log4j.appender.demo.File=c:/log.txt
			# 定义日志文件的位置

		log4j.appender.demo.layout=org.apache.log4j.PatternLayout
			# 定义布局

		log4j.appender.demo.layout.ConversionPattern=[%-5p][%d{yyy-MM-dd HH:mm:ss}] %C %L %m%n
			# 定义输出格式
		
		# 这种方式,日志文件会不断的叠加,体积越来越大

	
	5,定义配置文件[滚动日志文件]
		* 也是把日志输出到文件,只是日志会以滚动的形式存储
		* 达到指定大小后,会重新建立文件

		log4j.appender.demo=org.apache.log4j.RollingFileAppender
			# 定义追加器,是滚动输出的

		log4j.appender.demo.File=c:/log.txt
			# 定义日志文件位置

		log4j.appender.demo.MaxFileSize=10KB
			# 定义文件最大大小
		
		log4j.appender.demo.layout=org.apache.log4j.PatternLayout
			# 定义布局
		
		log4j.appender.demo.layout.ConversionPattern=[%-5p][%d{yyy-MM-dd HH:mm:ss}] %C %L %m%n
			# 定义输出格式

----------------------------
Log4j-Log4j2				|
----------------------------
	# 这个版本,是使用xml形式来做为配置文件的
		* 可以以压缩文件的形式来存储日志文件

	# Maven
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
			<version>2.6.2</version>
		</dependency>
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-api</artifactId>
			<version>2.6.2</version>
		</dependency>
	
	# 这个版本的 Logger 获取方法不一样
		private static Logger logger = LogManager.getLogger();
	
	# 至于xml配置,那个只是变了个形式.其他都没怎么变

----------------------------
Log4j-slf4j					|
----------------------------
	#  一般项目直接引入这个就OK了，因为他会自动的依赖LO4J

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
	* 注意都是 slf4j包下类

	# 开发中用DEBUG,生存环境用INFO

	LOGGER.debug("保存新的商品:"+item+"	描述:"+desc);

	LOGGER.debug("保存新的商品:{},描述{}",ItemDTO,desc);
		> "{}",代表占位符,会把后面第二个参数填充到第一个"{}",
		> 第三个参数填充给第二个"{}",
		> 依次类推... ...
	
	# 实际开发中还需要加个判断
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("保存新的商品:"+item+"	描述:"+desc);
		}
		* 因为执行到这里,如果是INFO级别的话,那么不会输出。但是还会去执行语句,浪费性能
		* 所以我们在使用前可以加个判断,是否是 DUBUG级别,是的话才执行日志,不是的话不鸟
		* 因为判断消耗性能比较低
		* 除了	LOGGER.isDebugEnabled() 判断是否是 DEBUG级别外,对于其他的级别判断,都是有的