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
		import org.jooq.conf.RenderKeywordCase;
		import org.jooq.impl.DefaultConfiguration;
		import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
		import org.springframework.context.annotation.Configuration;

		@Configuration(value = "customJooqConfiguration")
		public class JooqConfiguration implements DefaultConfigurationCustomizer {

			@Override
			public void customize(DefaultConfiguration configuration) {
				configuration.settings()
					.withRenderKeywordCase(RenderKeywordCase.UPPER) // �ؼ��ִ�д
					.withRenderFormatted(true)						// ��ʽ�������SQL
					; 
			}
		}


				
	# �����������õĽӿ�
		ConnectionProvider
		TransactionProvider
		RecordMapperProvider
		RecordListenerProvider
		ExecuteListenerProvider
		VisitListenerProvider