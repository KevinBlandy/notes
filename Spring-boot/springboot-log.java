
-------------------------
logback������			 |
-------------------------
	# ���������ļ���ַ
	  logging.config=classpath:logback-spring.xml

	  * ��������Ϊ logback.xml, ���ʹ�ñ�׼����λ, Spring���޷���ȫ������־��ʼ��
	
	# springboot logbackĬ�ϵ�����(spring-boot.jar)
		/org/springframework/boot/logging/logback/defaults.xml
		/org/springframework/boot/logging/logback/console-appender.xml
		/org/springframework/boot/logging/logback/base.xml
		/org/springframework/boot/logging/logback/file-appender.xml

		* ʵ��ʹ���У�����ֱ�Ӽ̳С��ο��⼸������


-------------------------
���õ�logback�������ļ�	 |
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
JSON ���
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
		<!-- ���� JSON Encoder -->
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

	* �����������Ϣ�����Բο� JsonEncoder ������
	* ����־����� key, value �Լ���ʽ����Ϣ��
		log.atInfo()
			.addKeyValue("app", "hhh")  // key / value ����
			.setMessage("��ã� {}")	// ��ʽ����Ϣ
			.addArgument("���۵�")		// ��ʽ������
			.log();;

		// �������־��

		{"timestamp":1732675274386,"level":"INFO","threadName":"main","loggerName":"demo.DemoApplication","kvpList": [{"app":"hhh"}],"formattedMessage":"��ã� ���۵�","throwable":null}

-------------------------
logback�໷��������		 |
-------------------------
	# ������logback.xml�е�configuration�ڵ������� springProfile �ڵ�
		<springProfile name="staging">

		* ����һ��name����, ֻ���ڵ�ǰprofile�����²Ż���ظýڵ��µ�����
			spring.profiles.active=staging


	# ֧�ֱ��ʽ
		<springProfile name="staging"></springProfile>
			* ֻ�� spring.profiles.active=staging �Żἤ��

		<springProfile name="dev | staging"></springProfile>
			* ֻ�� spring.profiles.active=staging ���� spring.profiles.active=dev �Żἤ��

		<springProfile name="!production"></springProfile>

			* ֻ�в��� spring.profiles.active=production �Żἤ��
	

	# ��ʾ
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

			<!-- ����̨��� -->
			<appender name="console"
				class="ch.qos.logback.core.ConsoleAppender">
				<encoder>
					<pattern>${CONSOLE_LOG_PATTERN}</pattern>
				</encoder>
			</appender>

			<!-- �������� -->
			<springProfile name="dev">
				<root level="DEBUG">
					<appender-ref ref="console" />
				</root>
			</springProfile>
			
			<!-- �������� -->
			<springProfile name="pro">
				<!-- ������ļ� -->
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
