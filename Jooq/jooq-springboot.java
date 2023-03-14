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
		<dependency>
			<groupId>com.zaxxer</groupId>
			<artifactId>HikariCP</artifactId>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
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
					.withMapJPAAnnotations(false)					// ������JPAע��
					; 

				//configuration.setExecuteListener(...); // ���ü�����
			}
		}
	

	# Test
		import org.junit.Test;
		import org.junit.runner.RunWith;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.boot.test.context.SpringBootTest;
		import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
		import org.springframework.test.context.junit4.SpringRunner;
		import org.springframework.transaction.annotation.Transactional;

		import io.springcloud.DemoApplication;

		import static org.jooq.impl.DSL.*;

		import org.jooq.DSLContext;

		@RunWith(SpringRunner.class)
		@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
		public class ApplicationTest {
			
			@Autowired
			private DSLContext dslContext;
			
			@Test
			@Transactional
			public void test () {
				String result = this.dslContext.select(inline("hello world").as("result")).from(table(("dual"))).fetchOneInto(String.class);
				System.out.println(result);
			}
		}


				
	# �����������õĽӿ�
		ConnectionProvider
		TransactionProvider
		RecordMapperProvider
		RecordListenerProvider
		ExecuteListenerProvider
		VisitListenerProvider