------------------------------
springboot
------------------------------
	# SpringBoot
		https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.sql.jooq
	

	# starter
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jooq</artifactId>
		</dependency>
	
	# 配置项(JooqProperties)
		spring.jooq.sql-dialect=MySQLDialect
			* 设置方言，默认：Auto-detected by default.
		
		* 目前好像只有这一个配置
		
	
	# 在需要的地方注入
		@Autowired
		private DSLContext dslContext;
	
	
	# 自定义配置，可以实现配置接口 DefaultConfigurationCustomizer 
		import org.jooq.conf.RenderKeywordCase;
		import org.jooq.impl.DefaultConfiguration;
		import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
		import org.springframework.context.annotation.Configuration;

		@Configuration(value = "customJooqConfiguration")
		public class JooqConfiguration implements DefaultConfigurationCustomizer {

			@Override
			public void customize(DefaultConfiguration configuration) {
				configuration.settings()
					.withRenderKeywordCase(RenderKeywordCase.UPPER) // 关键字大写
					.withRenderFormatted(true)						// 格式化输出的SQL
					; 
			}
		}


				
	# 其他可以配置的接口
		ConnectionProvider
		TransactionProvider
		RecordMapperProvider
		RecordListenerProvider
		ExecuteListenerProvider
		VisitListenerProvider