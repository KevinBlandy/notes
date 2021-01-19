------------------------
java					|		
------------------------
	# jdbc驱动
		<!-- https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc -->
		<dependency>
			<groupId>org.xerial</groupId>
			<artifactId>sqlite-jdbc</artifactId>
			<version>3.23.1</version>
		</dependency>


	# 连接代码
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");
		
		* 如果test.bd不存在,会在项目的根目录下生成一个 test.db 文件
		* 可以使用classpath:test.db,或者文件目录来指定db 文件

	
