-------------------------
��־������
-------------------------
	# ��ӡ SQL ���
		  spring.jpa.show-sql=true

		  * Logger Ϊ org.hibernate.SQL
		
	# ��ӡ SQL ����
		<logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="TRACE"/>


		* hibernate6�󣬸���

			<logger name="org.hibernate.orm.jdbc.bind" level="TRACE" />
	
	
	# �� SQL ��־������ļ�
		* �е���ѧ����Ҫר��Ϊ org.hibernate.SQL ����һ��������ļ��� appender
		* root �����õ� appender ����� org.hibernate.SQL ��Ч

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