----------------------------
Configuration
----------------------------
	# ������ӿ�
		public interface Configuration extends Serializable 

		* ϵͳ�ṩ������
			AbstractConfiguration
				|-DefaultConfiguration
				|-MockConfiguration
	
	# ��֧�ֵ�������
		org.jooq.SQLDialect 
		org.jooq.conf.Settings
		org.jooq.ParseListenerProvider
		org.jooq.RecordListenerProvider
		org.jooq.RecordMapperProvider

		java.sql.Connection
		java.sql.DataSource

