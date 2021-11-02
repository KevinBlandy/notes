--------------------------------
appender
--------------------------------
	# appender����ʵ�ֽӿڣ�ch.qos.logback.core.Appender 
	
	
--------------------------------
���õ�appender
--------------------------------
	# �̳й�ϵ
		OutputStreamAppender
			|-ConsoleAppender
			|-FileAppender
				|-RollingFileAppender

	# OutputStreamAppender
		* ������ṩ������ appender �����Ļ�������������appender�ĸ���
		* ͨ������ֱ��ʵ��һ�� OutputStreamAppender ʵ������Ϊһ����˵ java.io.OutputStream ���Ͳ��ܷ����תΪ String
		* ���ԣ����Ա���appender���ɣ�
			ecoder
				* ����ͨ�����ַ�ʽ���¼�д�� OutputStreamAppender��Encoder �����ڵ������½ڽ���

			immediateFlush
				* Ĭ��ֵΪ true������ˢ�����������ȷ����־�¼�������д�룬���ҿ��Ա�֤һ�����Ӧ��û����ȷ�ر� appender����־�¼�Ҳ���ᶪʧ��
				* ����һ������˵�������������Ϊ false���п��ܻ�ʹ��־��������������(���������)��
				* ���ǣ�����Ϊ false����Ӧ���˳���ʱ��û����ȷ�ر� appender���ᵼ����־�¼�û�б�д����̣����ܻᶪʧ��


	# ConsoleAppender
		<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
			<!-- �����ʽ�� -->
			<encoder>
				<pattern>${CONSOLE_LOG_PATTERN}</pattern>
			</encoder>
			<!-- ����أ�System.out �� System.err��Ĭ��Ϊ System.out -->
			<target>System.out</target>
			<!-- 
				�� windows ʹ�� ANSI ��ɫ���롣�� windows ���������Ϊ true����Ӧ�ý� org.fusesource.jansi:jansi:1.9 ��� jar ���ŵ� classpath �¡�
				���� Unix ʵ�ֵĲ���ϵͳ���� Linux��Max OS X ��Ĭ��֧�� ANSI �Ų�ɫ���롣
			 -->
			<withJansi>true</withJansi>
		</appender>
	
	# FileAppender
		* ����־�¼�������ļ��С�ͨ�� file ��ָ��Ŀ���ļ���������ļ����ڣ����� append ��ֵ��Ҫô����־׷�ӵ��ļ��У�Ҫô���ļ����ضϡ�
			<appender name="file" class="ch.qos.logback.core.FileAppender">
				<!-- 
					Ҫд���ļ������ơ�����ļ������ڣ����½���
					�� windows ƽ̨�ϣ��û��������ǶԷ�б�ܽ���ת�塣���磬c:\temp\test.log ���ᱻ��ȷ��������Ϊ '\t' ��һ��ת���ַ����ᱻ����Ϊһ�� tab �ַ� (\u0009)����ȷ��ֵӦ����c:/temp/test.log ���� c:\\temp\\test.log��
					û��Ĭ��ֵ�� 
				-->
				<file>app.log</file>
				<!-- 
					�Ƿ����ϸ�Ĭ�ϣ�Ĭ��ֵΪ��false
				 -->
				<prudent>true</prudent>
				<!--
					�Ƿ�����־�ļ���׷�ӣ����ǽ׶�
				-->
				<append>true</append>
				<encoder>
					<pattern>${CONSOLE_LOG_PATTERN}</pattern>
				</encoder>
			</appender>
	
	# RollingFileAppender
		* �̳���FileAppender��������ת��־�ļ��Ĺ���

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

	# AsyncAppender
		* �첽��־����������ṩ��־IO����
		<appender name="ASYNC" class="ch.qos.logback.classic.AsyncAppender">
			<!-- 
				������־����ֵ�����������ֵ
				��ᶪ�� TRACT��DEBUG��INFO �������־
				Ĭ��Ϊ: 0 ���������κ���־
			-->
			<discardingThreshold>0</discardingThreshold>
			<!-- ���г��ȡ�Ĭ��ֵΪ 256 -->
			<queueSize>256</queueSize>
			<!-- ָ��Appender -->
			<appender-ref ref="FILE"/>
		</appender>
		
--------------------------------
�Զ��� appender
--------------------------------
	# �̳� AppenderBase ���Ա�д�Լ��� appender

	# �Զ���һ�������־��WebSocket��appender
		import java.nio.charset.StandardCharsets;
		import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
		import ch.qos.logback.classic.spi.ILoggingEvent;
		import ch.qos.logback.core.AppenderBase;

		public class WebsocketAppender extends AppenderBase <ILoggingEvent> {

			// encoder �Ǳ����
			private PatternLayoutEncoder encoder;
			
			@Override
			public void start() {
				// TODO ���Խ�����Դ���
				super.start();
			}
			
			@Override
			public void stop() {
				// TODO ���Խ�����Դ�ͷ�
				super.stop();
			}

			@Override
			protected void append(ILoggingEvent eventObject) {
				// ʹ��encoder������־
				byte[] data = this.encoder.encode(eventObject);
				// ���͵��ͻ���
				String log = new String(data, StandardCharsets.UTF_8);
				System.err.println(log);
			}

			// ����Ҫ�кϷ���getter/setter
			public PatternLayoutEncoder getEncoder() {
				return encoder;
			}
			public void setEncoder(PatternLayoutEncoder encoder) {
				this.encoder = encoder;
			}
		}