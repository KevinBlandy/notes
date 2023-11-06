------------------------------------
DataSourceProperties
------------------------------------
	# 数据源的配置类

	# 可以直接通过它构造数据源

	    @Bean
		@ConfigurationProperties("spring.datasource.todos") 
		public DataSourceProperties todosDataSourceProperties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties("spring.datasource.todos.hikari")
		public DataSource todosDataSource() {
			return todosDataSourceProperties().initializeDataSourceBuilder()
								.type(HikariDataSource.class)  // 指定数据源类型
								.build();	
		}


		配置文件如下：
		spring.datasource.todos.url=
		spring.datasource.todos.username=
		spring.datasource.todos.password=
		spring.datasource.todos.driverClassName=
		spring.datasource.todos.hikari.connectionTimeout=30000 
		spring.datasource.todos.hikari.idleTimeout=600000 
		spring.datasource.todos.hikari.maxLifetime=1800000 