------------------------------
springboot
------------------------------
	# starter
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jooq</artifactId>
		</dependency>
	
	# 配置项(JooqProperties)
		spring.jooq.sql-dialect=MySQLDialect
			* 设置方言，默认：Auto-detected by default.
		
	
	# 在需要的地方注入
		@Autowired
		private DSLContext dslContext;
	
	