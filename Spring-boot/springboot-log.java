
-------------------------
logback的配置			 |
-------------------------
	# 设置配置文件地址
	  logging.config=classpath:logback-spring.xml

	  * 不能命名为 logback.xml, 如果使用标准配置位, Spring将无法完全控制日志初始化
	
	# springboot logback默认的配置
		org/springframework/boot/logging/logback/defaults.xml

-------------------------
常用的logback的配置文件	 |
-------------------------
	<configuration>
		
		<conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
		<conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
		<conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
		
		<!-- 控制台输出的格式 -->
		<property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>

		<!-- 文件输出的格式 -->
		<property name="FILE_LOG_PATTERN" value="${FILE_LOG_PATTERN:-%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
		

		<!-- 窗口输出日志 -->
		<appender name="console" class="ch.qos.logback.core.ConsoleAppender">
			<encoder>
				<pattern>${CONSOLE_LOG_PATTERN}</pattern>
			</encoder>
		</appender>
		
		<!-- 滚动日志 -->
		<appender name="rolling"
			class="ch.qos.logback.core.rolling.RollingFileAppender">
			<!-- 日志文件的目录和名称 -->
			<file>production.log</file>
			<rollingPolicy
				class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
				<!-- 单个日志文件的最大体积，达到该体积后就会进行归档(压缩) -->
				<maxFileSize>100MB</maxFileSize>
				<!-- 单个日志文件超过限定大小后，就会被压缩为zip文件，设置zip文件的名称格式(2019-09-05-0.zip) -->
				<fileNamePattern>%d{yyyy-MM-dd}-%i.zip</fileNamePattern>
				<!-- 保留的存档文件的数量，与上一个 fileNamePattern 有关。 假设定义为6，当fileNamePattern以天为单位时，即保存6天的日志。 
					当以月为单位时，即保存6个月的日志。 旧的日志以异步的方式删除。 -->
				<maxHistory>60</maxHistory>
				<!-- 所有的归档日志的大小。当超过限制时，会删掉旧的归档日志。 -->
				<totalSizeCap>1GB</totalSizeCap>
			</rollingPolicy>
			<encoder>
				<pattern>${PATTERN}</pattern>
			</encoder>
		</appender>

		<root level="DEBUG">
			<appender-ref ref="console" />
			<appender-ref ref="rolling" />
		</root>
	</configuration>

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
