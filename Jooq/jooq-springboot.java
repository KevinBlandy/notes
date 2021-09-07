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
	
	# ������(JooqProperties)
		spring.jooq.sql-dialect=MySQLDialect
			* ���÷��ԣ�Ĭ�ϣ�Auto-detected by default.
		
		* Ŀǰ����ֻ����һ������
		
	
	# ����Ҫ�ĵط�ע��
		@Autowired
		private DSLContext dslContext;
	
	
	# �Զ������ã�����ʵ�����ýӿ� DefaultConfigurationCustomizer 
		@FunctionalInterface
		public interface DefaultConfigurationCustomizer {
			void customize(DefaultConfiguration configuration);

		}

				
	# �����������õĽӿ�
		ConnectionProvider
		TransactionProvider
		RecordMapperProvider
		RecordListenerProvider
		ExecuteListenerProvider
		VisitListenerProvider