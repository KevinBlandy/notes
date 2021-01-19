----------------------------
Spring boot MyBatis			|
----------------------------


----------------------------
Spring boot 第一种方式		|
----------------------------
	# 使用官方提供的jar,进行整合
	1,引入依赖
		
		<!-- spring boot mybatis -->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.2.0</version>
		</dependency>
		
		<!-- mybatis分页插件 -->
		<dependency>
			<groupId>com.github.miemiedev</groupId>
			<artifactId>mybatis-paginator</artifactId>
			<version>1.2.17</version>
		</dependency>
	
	2,在 @SpringBootApplication 类上添加注解来扫描 mapper 接口
		@MapperScan("com.xx");
			* 扫描指定包下的 mapper 接口
			* value				参数是一个数组,可以扫描多个
			* annotationClass	仅仅加载添加了指定注解的类
			* sqlSessionFactoryRef 
				* 创建这些mapper代理对象的时候
				* 使用指定名称的IOC中SqlSessionFactory实例
				* 可以用于多数据源实现的时候

			sqlSessionTemplateRef
				* 创建这些mapper代理对象的时候
				* 使用指定名称的IOC中sqlSessionTemplate实例
				* 可以用于多数据源实现的时候

		@MapperScans
			* 是一个组合注解,value值是多个 @MapperScan
		

	3,在properties中配置参数
		mybatis.mapper-locations[0]=classpath*:com/tedi/**/*mapper.xml																
			* mapper文件地址,可以有多个,支持使用通配符
		mybatis.config-location=classpath:mybatis/mybatis-config.xml
			* mybatis配置文件地址
		-----------------------------------------------------------
		mybatis.mapper-locations[0]=classpath:mapper/**/*-mapper.xml
		mybatis.mapper-locations[1]=classpath:mapper/**/*-mapper-ext.xml
		mybatis.config-location=classpath:mybatis/mybatis-config.xml

	
	

----------------------------
Spring boot 第二种方式		|
----------------------------
	# 使用原始配置spring的方式进行整合
	# spring 是怎么整合的,就怎么整合


----------------------------
mybatis 多数据源			|
----------------------------
	# 第一个数据源和mybatis配置
		@Configuration
		// 指定包下的mapper实例,使用test1SqlSessionTemplate(bean name)
		@MapperScan(basePackages = "com.neo.mapper.test1", sqlSessionTemplateRef  = "test1SqlSessionTemplate")
		public class DataSource1Config {
			
			// 实例化第一个数据源
			@Bean(name = "test1DataSource")
			@ConfigurationProperties(prefix = "spring.datasource.test1")
			@Primary // 使用自动注入的时候,存在多个相同类型实例,且未指定名称,它优先注入
			public DataSource testDataSource() {
				return DataSourceBuilder.create().build();
			}

			// 根据数据源实例化第一个 SqlSessionFactory
			@Bean(name = "test1SqlSessionFactory")
			@Primary // 使用自动注入的时候,存在多个相同类型实例,且未指定名称,它优先注入
			public SqlSessionFactory testSqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {
				SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
				bean.setDataSource(dataSource);
				return bean.getObject();
			}
			
			// 根据数据源实例化第一个 DataSourceTransactionManager
			@Bean(name = "test1TransactionManager")
			@Primary // 使用自动注入的时候,存在多个相同类型实例,且未指定名称,它优先注入
			public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
				return new DataSourceTransactionManager(dataSource);
			}

			// 根据SqlSessionFactory实例化第一个 SqlSessionTemplate
			@Bean(name = "test1SqlSessionTemplate")
			@Primary
			public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
				return new SqlSessionTemplate(sqlSessionFactory);
			}

		}
	
	# 第二个数据源和mybatis配置
		@Configuration
		@MapperScan(basePackages = "com.neo.mapper.test2", sqlSessionTemplateRef  = "test2SqlSessionTemplate")
		public class DataSource2Config {

			@Bean(name = "test2DataSource")
			@ConfigurationProperties(prefix = "spring.datasource.test2")
			public DataSource testDataSource() {
				return DataSourceBuilder.create().build();
			}

			@Bean(name = "test2SqlSessionFactory")
			public SqlSessionFactory testSqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource) throws Exception {
				SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
				bean.setDataSource(dataSource);
				return bean.getObject();
			}

			@Bean(name = "test2TransactionManager")
			public DataSourceTransactionManager testTransactionManager(@Qualifier("test2DataSource") DataSource dataSource) {
				return new DataSourceTransactionManager(dataSource);
			}

			@Bean(name = "test2SqlSessionTemplate")
			public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
				return new SqlSessionTemplate(sqlSessionFactory);
			}

		}

	
	# 注入使用
		@Autowired Mapper1
			
		@Autowired Mapper2
			
