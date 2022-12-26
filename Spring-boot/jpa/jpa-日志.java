-------------------------
日志的配置
-------------------------
	# 打印jpa sql参数
		<logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="TRACE"/>


		* hibernate6后，改了

			<logger name="org.hibernate.orm.jdbc.bind" level="TRACE" />
	
