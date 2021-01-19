------------------
core
------------------
	# 3个核心
		Logger
		Appender
		Layouts

------------------
Logger
------------------
	# Logger 当作一个实体，它们的命名是大小写敏感的
	# 命名层次结构
		* 如果一个 logger 的名字加上一个 . 作为另一个 logger 名字的前缀，那么该 logger 就是另一个 logger 的祖先。
		* com.foo 的 logger 是名为 com.foo.Bar 的 logger 的父级
		* java 的 logger 是名为 java.util 的父级，是名为 java.util.Vector 的祖先。
	

	
	# ROOT LOGGER
		* root logger 作为 logger 层次结构的最高层
		* 它是一个特殊的 logger，因为它是每一个层次结构的一部分
			Logger rootLogger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
	
	# 等级继承
		* 不同的等级，TRACE, DEBUG, INFO, WARN, ERROR，定义在：ch.qos.logback.classic.Level 类中
		* 如果一个给定的 logger 没有指定一个层级，那么它就会继承离它最近的一个祖先的层级。
		* 为了确保所有的 logger 都有一个层级，root logger 会有一个默认层级 --- DEBUG
	

	# 方法打印以及基本选择规则
		* 一条的日志的打印级别大于 logger 的有效级别，该条日志才可以被打印出来
		* 这条规则是 logbakc 的核心。各级别的排序为：TRACE < DEBUG < INFO < WARN < ERROR
	
	# logger 的命名
		* logback 不会严格限制 logger 的命名，你完全可以根据自己的喜好来，你开心就好
		* 根据类的全限定名来对 logger 进行命名，是目前最好的方式，没有之一。
	
------------------
Appender
------------------
	# 日志输出目的地叫做 appender。
		* appender 包括console、file、remote socket server、MySQL、PostgreSQL、Oracle 
		* 或者其它的数据库、JMS、remote UNIX Syslog daemons 中
	
	# 一个 logger 可以有多个 appender。
		* 每一个允许输出的日志都会被转发到该 logger 的所有 appender 中去。
	
	# appender 从 logger 的层级结构中去继承叠加性。
		* 如果 root logger 添加了一个 console appender，所有允许输出的日志至少会在控制台打印出来
		* 再给一个叫做 L 的 logger 添加了一个 file appender，那么 L 以及 L 的子级 logger 都可以在文件和控制台打印日志
		
		* logger L 的日志输出语句会遍历 L 和它的子级中所有的 appender。这就是所谓的 appender 叠加性（appender additivity）

		* 可以通过设置 additivity = false 来改写默认的设置（默认设置 additivity = true），这样 appender 将不再具有叠加性。
		* 子Logger 是否继承父级的Logger 的 输出源（appender） 的标志位。

		* 如果 L 的某个上级 logger 为 P，且 P 设置了 additivity = false，那么 L 的日志会在层级在 L 到 P 之间的所有 logger 的 appender，包括 P 本身的 appender 中输出，
		* 但是不会在 P 的上级 appender 中输出。
		
		* root logger 为 logger 层级中的最高层，additivity 对它不适用


------------------
Layout
------------------
	# layout 的作用是将日志格式化

	# PatternLayout 
		* 常用的就是它
		* 能够根据用户指定的格式来格式化日志，类似于 C 语言的 printf 函数。


	# 参数化输出
		Object[] paramArray = {newVal, below, above};
		logger.debug("Value {} was inserted between {} and {}.", paramArray);
