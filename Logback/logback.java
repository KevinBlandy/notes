------------------------
logback					|
------------------------
	# log4j作者的又一个力作
	# logback当前分成三个模块：
		logback-core
		logback-classic
		logback-access
		* logback-core		是其它两个模块的基础模块。
		* logback-classic	完整实现SLF4J API使你可以很方便地更换成其它日志系统如log4j或JDK14 Logging。是log4j的一个 改良版本。
		* logback-access	访问模块与Servlet容器集成提供通过Http来访问日志的功能。
	
	# 普通项目
		* 依赖
			<dependency>
				<groupId>org.slf4j</groupId>
				<artifactId>slf4j-api</artifactId>
			</dependency>
			<dependency>
				<groupId>ch.qos.logback</groupId>
				<artifactId>logback-core</artifactId>
			</dependency>
			<dependency>
				<groupId>ch.qos.logback</groupId>
				<artifactId>logback-classic</artifactId>
			</dependency>
			<dependency>
				<groupId>ch.qos.logback</groupId>
				<artifactId>logback-access</artifactId>
			</dependency>
		* classpath下添加配置文件: logback.xml

	# spring 项目
		<!-- logger begin-->
		<!-- 该依赖会替换spring的日志实现 -->
		<dependency>
		    <groupId>org.slf4j</groupId>
		    <artifactId>jcl-over-slf4j</artifactId>
		    <version>${slf4j-api.version}</version>
		</dependency>
		
		<dependency>  
		    <groupId>org.logback-extensions</groupId>  
		    <artifactId>logback-ext-spring</artifactId>  
		    <version>${logback-ext-spring.version}</version>  
		</dependency> 
		
		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-classic</artifactId>
			<version>${logback.version}</version>
		</dependency>
		<!-- logger end -->

------------------------
logback	- 配置详解		|
------------------------
	logback.xml 配置
		<configuration>
			<property name="LEVEL" value="DEBUG"/>
			<property name="PATTERN-A" value="%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"/>
			<property name="PATTERN-B" value="[%-5p][%thread][%d{yyy-MM-dd HH:mm:ss}][%C %M] ==> : %m%n"/>

			<appender name="ROOT" class="ch.qos.logback.core.ConsoleAppender">
				<encoder>
					<pattern>${PATTERN-A}</pattern>
					<charset>UTF-8</charset>
				</encoder>
				<target>System.out</target>
			</appender>
			
			<root level="${LEVEL}">
				<appender-ref ref="ROOT"/>
			</root>
		</configuration>

		appender	:通过 class 来确定当前appender是输出到控制台还是输出到文件夹,还是滚动,不同的 class 会有不同的子标签来控制属性
		root		:可以添加一个或者多个 appender
	

	web.xml 配置
		<listener>
			<listener-class>ch.qos.logback.ext.spring.web.LogbackConfigListener</listener-class>
		</listener>
		<context-param>
			<param-name>logbackConfigLocation</param-name>
			<param-value>classpath:logback.xml</param-value>
		</context-param>

${CONSOLE_LOG_PATTERN:-%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}{faint} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- }{magenta} ---{faint} [%15.15t]{faint} %-40.40logger{39}{cyan} :{faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>