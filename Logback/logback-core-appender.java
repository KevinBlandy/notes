--------------------------------
appender
--------------------------------
	# appender必须实现接口：ch.qos.logback.core.Appender 
	
	
--------------------------------
常用的appender
--------------------------------
	# 继承关系
		OutputStreamAppender
			|-ConsoleAppender
			|-FileAppender
				|-RollingFileAppender

	# OutputStreamAppender
		* 这个类提供了其它 appender 构建的基础服务。是所有appender的父类
		* 通常不会直接实例一个 OutputStreamAppender 实例。因为一般来说 java.io.OutputStream 类型不能方便的转为 String
		* 属性（可以被子appender集成）
			ecoder
				* 决定通过哪种方式将事件写入 OutputStreamAppender，Encoder 将会在单独的章节介绍

			immediateFlush
				* 默认值为 true。立即刷新输出流可以确保日志事件被立即写入，并且可以保证一旦你的应用没有正确关闭 appender，日志事件也不会丢失。
				* 从另一方面来说，设置这个属性为 false，有可能会使日志的吞吐量翻两番(视情况而定)。
				* 但是，设置为 false，当应用退出的时候没有正确关闭 appender，会导致日志事件没有被写入磁盘，可能会丢失。


	# ConsoleAppender
		<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
			<!-- 输出格式化 -->
			<encoder>
				<pattern>${CONSOLE_LOG_PATTERN}</pattern>
			</encoder>
			<!-- 输出地，System.out 或 System.err。默认为 System.out -->
			<target>System.out</target>
			<!-- 
				在 windows 使用 ANSI 彩色代码。在 windows 上如果设置为 true，你应该将 org.fusesource.jansi:jansi:1.9 这个 jar 包放到 classpath 下。
				基于 Unix 实现的操作系统，像 Linux、Max OS X 都默认支持 ANSI 才彩色代码。
			 -->
			<withJansi>true</withJansi>
		</appender>
	
	# FileAppender
		* 将日志事件输出到文件中。通过 file 来指定目标文件。如果该文件存在，根据 append 的值，要么将日志追加到文件中，要么该文件被截断。
			<appender name="file" class="ch.qos.logback.core.FileAppender">
				<!-- 
					要写入文件的名称。如果文件不存在，则新建。
					在 windows 平台上，用户经常忘记对反斜杠进行转义。例如，c:\temp\test.log 不会被正确解析，因为 '\t' 是一个转义字符，会被解析为一个 tab 字符 (\u0009)。正确的值应该像：c:/temp/test.log 或者 c:\\temp\\test.log。
					没有默认值。 
				-->
				<file>app.log</file>
				<!-- 
					是否开启严格默认，默认值为：false
				 -->
				<prudent>true</prudent>
				<!--
					是否在日志文件后追加，还是阶段
				-->
				<append>true</append>
				<encoder>
					<pattern>${CONSOLE_LOG_PATTERN}</pattern>
				</encoder>
			</appender>
	
	# RollingFileAppender
		* 继承自FileAppender，具有轮转日志文件的功能

		<appender name="rolling" class="ch.qos.logback.core.rolling.RollingFileAppender">
			<file>streamer-app.log</file>
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

	
--------------------------------
自定义 appender
--------------------------------
	# 继承 AppenderBase 可以编写自己的 appender

	# 自定义一个输出日志到WebSocket的appender
		import java.nio.charset.StandardCharsets;
		import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
		import ch.qos.logback.classic.spi.ILoggingEvent;
		import ch.qos.logback.core.AppenderBase;

		public class WebsocketAppender extends AppenderBase <ILoggingEvent> {

			// encoder 是必须的
			private PatternLayoutEncoder encoder;
			
			@Override
			public void start() {
				// TODO 可以进行资源检查
				super.start();
			}
			
			@Override
			public void stop() {
				// TODO 可以进行资源释放
				super.stop();
			}

			@Override
			protected void append(ILoggingEvent eventObject) {
				// 使用encoder解码日志
				byte[] data = this.encoder.encode(eventObject);
				// 推送到客户端
				String log = new String(data, StandardCharsets.UTF_8);
				System.err.println(log);
			}

			// 必须要有合法的getter/setter
			public PatternLayoutEncoder getEncoder() {
				return encoder;
			}
			public void setEncoder(PatternLayoutEncoder encoder) {
				this.encoder = encoder;
			}
		}