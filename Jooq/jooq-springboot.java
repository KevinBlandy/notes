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
					.withMapJPAAnnotations(false)					// 不解析JPA注解
					; 

				//configuration.setExecuteListener(...); // 设置监听器
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


				
	# 其他可以配置的接口
		ConnectionProvider
		TransactionProvider
		RecordMapperProvider
		RecordListenerProvider
		ExecuteListenerProvider
		VisitListenerProvider