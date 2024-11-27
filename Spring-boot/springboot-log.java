
-------------------------
logback的配置			 |
-------------------------
	# 设置配置文件地址
	  logging.config=classpath:logback-spring.xml

	  * 不能命名为 logback.xml, 如果使用标准配置位, Spring将无法完全控制日志初始化
	
	# springboot logback默认的配置(spring-boot.jar)
		/org/springframework/boot/logging/logback/defaults.xml
		/org/springframework/boot/logging/logback/console-appender.xml
		/org/springframework/boot/logging/logback/base.xml
		/org/springframework/boot/logging/logback/file-appender.xml

		* 实际使用中，可以直接继承、参考这几个配置


-------------------------
常用的logback的配置文件	 |
-------------------------

	<configuration>

		<include
			resource="org/springframework/boot/logging/logback/defaults.xml" />

		<appender name="console"
			class="ch.qos.logback.core.ConsoleAppender">
			<encoder>
				<pattern>${CONSOLE_LOG_PATTERN}</pattern>
			</encoder>
		</appender>


		<appender name="rollingAppLog"
			class="ch.qos.logback.core.rolling.RollingFileAppender">
			<file>logs/app.log</file>
			<rollingPolicy
				class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
				<maxFileSize>10MB</maxFileSize>
				<fileNamePattern>logs/app-%d{yyyy-MM-dd}-%i.gz</fileNamePattern>
				<maxHistory>60</maxHistory>
				<totalSizeCap>5GB</totalSizeCap>
			</rollingPolicy>
			<encoder class="ch.qos.logback.classic.encoder.JsonEncoder">
				<withFormattedMessage>true</withFormattedMessage>
				<withSequenceNumber>false</withSequenceNumber>
				<withNanoseconds>false</withNanoseconds>
				<withMessage>false</withMessage>
				<withMDC>false</withMDC>
				<withContext>false</withContext>
				<withArguments>false</withArguments>
			</encoder>
		</appender>

		<appender name="rollingErrorLog"
			class="ch.qos.logback.core.rolling.RollingFileAppender">
			<file>logs/error.log</file>
			<rollingPolicy
				class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
				<maxFileSize>10MB</maxFileSize>
				<fileNamePattern>logs/error-%d{yyyy-MM-dd}-%i.gz</fileNamePattern>
				<maxHistory>60</maxHistory>
				<totalSizeCap>5GB</totalSizeCap>
			</rollingPolicy>
			<encoder>
				<pattern>${FILE_LOG_PATTERN}</pattern>
			</encoder>
			<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
				<level>ERROR</level>
			</filter>
		</appender>

		<root level="INFO">
			<appender-ref ref="console" />
			<appender-ref ref="rollingAppLog" />
			<appender-ref ref="rollingErrorLog" />
		</root>
	</configuration>


-------------------------
JSON 输出
-------------------------

	<appender name="rollingAppLog"
		class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>logs/app.log</file>
		<rollingPolicy
			class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
			<maxFileSize>10MB</maxFileSize>
			<fileNamePattern>logs/app-%d{yyyy-MM-dd}-%i.gz</fileNamePattern>
			<maxHistory>60</maxHistory>
			<totalSizeCap>5GB</totalSizeCap>
		</rollingPolicy>
		<!-- 配置 JSON Encoder -->
		<encoder class="ch.qos.logback.classic.encoder.JsonEncoder">
			<withFormattedMessage>true</withFormattedMessage>
			<withSequenceNumber>false</withSequenceNumber>
			<withNanoseconds>false</withNanoseconds>
			<withMessage>false</withMessage>
			<withMDC>false</withMDC>
			<withContext>false</withContext>
			<withArguments>false</withArguments>
		</encoder>
	</appender>

	* 更多的配置信息，可以参考 JsonEncoder 类属性
	* 在日志中输出 key, value 以及格式化信息：
		log.atInfo()
			.addKeyValue("app", "hhh")  // key / value 参数
			.setMessage("你好： {}")	// 格式化消息
			.addArgument("窝嫩叠")		// 格式化参数
			.log();;

		// 输出的日志：

		{"timestamp":1732675274386,"level":"INFO","threadName":"main","loggerName":"demo.DemoApplication","kvpList": [{"app":"hhh"}],"formattedMessage":"你好： 窝嫩叠","throwable":null}

-------------------------
logback多环境的配置		 |
-------------------------
	# 可以在logback.xml中的configuration节点下配置 springProfile 节点
		<springProfile name="staging">

		* 它有一个name属性, 只有在当前profile环境下才会加载该节点下的内容
			spring.profiles.active=staging


	# 支持表达式
		<springProfile name="staging"></springProfile>
			* 只有 spring.profiles.active=staging 才会激活

		<springProfile name="dev | staging"></springProfile>
			* 只有 spring.profiles.active=staging 或着 spring.profiles.active=dev 才会激活

		<springProfile name="!production"></springProfile>

			* 只有不是 spring.profiles.active=production 才会激活
	

	# 演示
		<configuration>
			<conversionRule conversionWord="clr"
				converterClass="org.springframework.boot.logging.logback.ColorConverter" />
			<conversionRule conversionWord="wex"
				converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
			<conversionRule conversionWord="wEx"
				converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
				
			<property name="CONSOLE_LOG_PATTERN"
				value="${CONSOLE_LOG_PATTERN:-%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}" />
			<property name="FILE_LOG_PATTERN"
				value="${FILE_LOG_PATTERN:-%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}" />

			<!-- 控制台输出 -->
			<appender name="console"
				class="ch.qos.logback.core.ConsoleAppender">
				<encoder>
					<pattern>${CONSOLE_LOG_PATTERN}</pattern>
				</encoder>
			</appender>

			<!-- 开发环境 -->
			<springProfile name="dev">
				<root level="DEBUG">
					<appender-ref ref="console" />
				</root>
			</springProfile>
			
			<!-- 生产环境 -->
			<springProfile name="pro">
				<!-- 输出到文件 -->
				<appender name="rolling" class="ch.qos.logback.core.rolling.RollingFileAppender">
					<file>production.log</file>
					<rollingPolicy
						class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
						<maxFileSize>100MB</maxFileSize>
						<fileNamePattern>%d{yyyy-MM-dd}-%i.zip</fileNamePattern>
						<maxHistory>60</maxHistory>
						<totalSizeCap>1GB</totalSizeCap>
					</rollingPolicy>
					<encoder>
						<pattern>${FILE_LOG_PATTERN}</pattern>
					</encoder>
				</appender>
				<root level="DEBUG">
					<appender-ref ref="rolling" />
				</root>
			</springProfile>
		</configuration>
