-------------------------
SQL2O					 |
-------------------------
	# Github
		https://github.com/aaberg/sql2o
	
		
	# Maven
		<dependency>
			<groupId>org.sql2o</groupId>
			<artifactId>sql2o</artifactId>
			<version>1.6.0</version>
		</dependency>


	# 构造函数
		Sql2o(String jndiLookup)
		Sql2o(String url, String user, String pass)
		Sql2o(String url, String user, String pass, Quirks quirks)
		Sql2o(DataSource dataSource)
		Sql2o(DataSource dataSource, Quirks quirks)
		
		jndiLookup
			* 从jndi加载数据源
		
		quirks
			* 特殊的一些设置