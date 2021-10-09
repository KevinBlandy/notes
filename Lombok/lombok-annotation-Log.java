--------------------
Log
--------------------
	# 支持多种日志系统
	@CommonsLog
	Creates private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(LogExample.class);
	@Flogger
	Creates private static final com.google.common.flogger.FluentLogger log = com.google.common.flogger.FluentLogger.forEnclosingClass();
	@JBossLog
	Creates private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(LogExample.class);
	@Log
	Creates private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LogExample.class.getName());
	@Log4j
	Creates private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogExample.class);
	@Log4j2
	Creates private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(LogExample.class);
	@Slf4j
	Creates private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
	@XSlf4j
	Creates private static final org.slf4j.ext.XLogger log = org.slf4j.ext.XLoggerFactory.getXLogger(LogExample.class);
	@CustomLog
	Creates private static final com.foo.your.Logger log = com.foo.your.LoggerFactory.createYourLogger(LogExample.class);

--------------------
Slf4j
--------------------
	# 生成sl4j的日志常量 
	# Slf4j
		@Retention(RetentionPolicy.SOURCE)
		@Target(ElementType.TYPE)
		public @interface Slf4j {
			String topic() default "";
		}