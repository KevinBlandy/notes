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
		@FunctionalInterface
		public interface DefaultConfigurationCustomizer {
			void customize(DefaultConfiguration configuration);

		}

				
	# 其他可以配置的接口
		ConnectionProvider
		TransactionProvider
		RecordMapperProvider
		RecordListenerProvider
		ExecuteListenerProvider
		VisitListenerProvider