-------------------------
日志的配置
-------------------------
	# 打印 SQL 语句
		  spring.jpa.show-sql=true

		  * Logger 为 org.hibernate.SQL
		
	# 打印 SQL 参数
		<logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="TRACE"/>


		* hibernate6后，改了

			<logger name="org.hibernate.orm.jdbc.bind" level="TRACE" />
	
	
	# 把 SQL 日志输出到文件
		* 有点玄学，需要专门为 org.hibernate.SQL 配置一个输出到文件的 appender
		* root 中配置的 appender 不会对 org.hibernate.SQL 生效

		<!-- SQL -->
		<logger name="org.hibernate.SQL" level="DEBUG" additivity="false" >
			<appender-ref ref="console" />
			<appender-ref ref="rollingAppLog" />
			<appender-ref ref="rollingErrorLog" />
		</logger>
		<logger name="org.hibernate.orm.jdbc.bind" level="TRACE" additivity="false" >
			<appender-ref ref="console" />
			<appender-ref ref="rollingAppLog" />
			<appender-ref ref="rollingErrorLog" />
		</logger>

		<root level="INFO">
			<appender-ref ref="console" />
			<appender-ref ref="rollingAppLog" />
			<appender-ref ref="rollingErrorLog" />
		</root>