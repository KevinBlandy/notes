----------------------------------
Spring Boot 多数据源
----------------------------------

# 配置信息
	app:
	  datasource:
		# 唯一主库
		master:
		  jdbcUrl: jdbc:mysql://127.0.0.1:3306/demo_master?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8&allowMultiQueries=true
		  username: root
		  password: root

		# 多个从库
		slave:
		  slave1:
			jdbcUrl: jdbc:mysql://127.0.0.1:3306/demo_slave1?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8&allowMultiQueries=true
			username: root
			password: root
		  
		  slave2:
			jdbcUrl: jdbc:mysql://127.0.0.1:3306/demo_slave2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8&allowMultiQueries=true
			username: root
			password: root
		  
		  slave3:
			jdbcUrl: jdbc:mysql://127.0.0.1:3306/demo_slave3?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8&allowMultiQueries=true
			username: root
			password: root

# 配置类

	import java.util.Map;
	import java.util.Objects;
	import java.util.Properties;

	import org.springframework.boot.context.properties.ConfigurationProperties;
	import org.springframework.boot.context.properties.bind.ConstructorBinding;

	@ConfigurationProperties(prefix = "app.datasource")
	public class MasterSlaveDataSourceProperties {
		
		// 主库
		private final Properties master;
		
		// 从库
		private final Map<String, Properties> slave;

		@ConstructorBinding
		public MasterSlaveDataSourceProperties(Properties master, Map<String, Properties> slave) {
			super();
			
			Objects.requireNonNull(master);
			Objects.requireNonNull(slave);
			
			this.master = master;
			this.slave = slave;
		}

		public Properties master() {
			return master;
		}
		
		public Map<String, Properties> slave() {
			return slave;
		}
	}

# 数据源

	import java.util.HashMap;
	import java.util.Map;
	import java.util.Properties;

	import javax.sql.DataSource;

	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;

	import com.zaxxer.hikari.HikariConfig;
	import com.zaxxer.hikari.HikariDataSource;

	@Configuration
	public class MasterSlaveDataSourceConfiguration {

		@Bean
		public DataSource dataSource(MasterSlaveDataSourceProperties properties) {

			// 主数据库
			HikariDataSource master = new HikariDataSource(new HikariConfig(properties.master()));

			// 从数据库
			Map<Object, Object> slave = new HashMap<>();
			
			for (Map.Entry<String,Properties> entry : properties.slave().entrySet()) {
				slave.put(entry.getKey(), new HikariDataSource(new HikariConfig(entry.getValue())));
			}
			
			return null;
		}
	}


