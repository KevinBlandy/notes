----------------------------
Configuration
----------------------------
	# 配置类接口
		public interface Configuration extends Serializable 

		* 系统提供的子类
			AbstractConfiguration
				|-DefaultConfiguration
				|-MockConfiguration
	
	# 它支持的配置项
		org.jooq.SQLDialect 
		org.jooq.conf.Settings
		org.jooq.ParseListenerProvider
		org.jooq.RecordListenerProvider
		org.jooq.RecordMapperProvider

		java.sql.Connection
		java.sql.DataSource

