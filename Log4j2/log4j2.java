----------------
log4j2
----------------
	# 文档
		https://logging.apache.org/log4j/2.x/
		https://github.com/apache/logging-log4j2
	
	
	# 配合 sl4j 依赖
		<!-- sl4j 门面框架 -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.7.30</version>
		</dependency>
		<!-- sl4j log4j2桥接 -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-slf4j-impl</artifactId>
			<version>2.14.1</version>
		</dependency>
		<!-- log4j API -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-api</artifactId>
			<version>2.14.1</version>
		</dependency>
		<!-- log4j2 实现 -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
			<version>2.14.1</version>
		</dependency>