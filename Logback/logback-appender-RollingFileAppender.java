---------------------------------
RollingFileAppender
---------------------------------
	# 根据大小归档成固定数量的日志
		<appender name="fixed" class="ch.qos.logback.core.rolling.RollingFileAppender">
			<file>D:/log/app.log</file>
			<rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
				<!-- 归档的文件格式 -->
				<fileNamePattern>D:/log/app.%i.log.zip</fileNamePattern>
				<!-- 开始和结束的滚动日志的范围 -->
				<minIndex>1</minIndex>
				<maxIndex>3</maxIndex>
			</rollingPolicy>

			<triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
				<!-- 10Kb大小，进行一次归档 -->
				<maxFileSize>10KB</maxFileSize>
			</triggeringPolicy>

			<encoder>
				<pattern>${FILE_LOG_PATTERN}</pattern>
			</encoder>
		</appender>

	# 根据大小，日期进行归档
		<appender name="rolling" class="ch.qos.logback.core.rolling.RollingFileAppender">
			<file>D:/log/app.log</file>
			<encoder>
				<pattern>${FILE_LOG_PATTERN}</pattern>
			</encoder>
			<rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
				<maxFileSize>100KB</maxFileSize>
				<fileNamePattern>D:/log/%d{yyyy-MM-dd}/%d{yyyy-MM-dd}-%i.log.zip</fileNamePattern>
				<maxHistory>50</maxHistory>
				<totalSizeCap>10MB</totalSizeCap>
				<cleanHistoryOnStart>false</cleanHistoryOnStart>
			</rollingPolicy>
		</appender>


---------------------------------
rollingPolicy
---------------------------------
	# FixedWindowRollingPolicy
	# TimeBasedRollingPolicy
	# SizeAndTimeBasedRollingPolicy
		* 按照日期和大小滚动存储
		* 既是 rollingPolicy 又是 triggeringPolicy

		<rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
			<!-- 单个文件最大大小，超出这个大小，文件将会进行一次归档 -->
			<maxFileSize>100KB</maxFileSize>
			<!-- 
				文件的归档名称，可以格式化
				常用的
					按照日归档：D:/log/%d{yyyy-MM-dd}/%d{yyyy-MM-dd}-%i.log.zip		/log/2020-11-04/2020-11-04-4.log.zip
				
				如果 fileNamePattern 以 .gz 或者 .zip 结尾，将会自动压缩
			 -->
			<fileNamePattern>D:/log/%d{yyyy-MM-dd}/%d{yyyy-MM-dd}-%i.log.zip</fileNamePattern>
			<!-- 
				最大存档日志，超出后会异步删除，第一删除条件，跟文件名称挂钩，文件名称是按日归档，则这里就是保存的日志天数。 
			-->
			<maxHistory>50</maxHistory>
			<!-- 所有日志的总大小，超出后会异步删除，第二删除条件 -->
			<totalSizeCap>10MB</totalSizeCap>
			<!-- 如果设置为true，那么在 appender 启动的时候，归档文件将会被删除。 -->
			<cleanHistoryOnStart>false</cleanHistoryOnStart>
		</rollingPolicy>
	
	# SocketAppender and SSLSocketAppender


---------------------------------
triggeringPolicy
---------------------------------
	# TimeBasedRollingPolicy
	# SizeAndTimeBasedRollingPolicy
	# TriggeringPolicyBase
	# SizeBasedTriggeringPolicy
	# DefaultTimeBasedFileNamingAndTriggeringPolicy
	# SizeAndTimeBasedFNATP
