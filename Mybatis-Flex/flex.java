------------------
Flex
------------------
	# https://mybatis-flex.com/


	# Maven

		<mybatis-flex.version>1.8.7</mybatis-flex.version>

		<dependencyManagement>
			<dependencies>
				<dependency>
					<groupId>com.mybatis-flex</groupId>
					<artifactId>mybatis-flex-dependencies</artifactId>
					<version>${mybatis-flex.version}</version>
					<type>pom</type>
					<scope>import</scope>
				</dependency>
			</dependencies>
		</dependencyManagement>
		
		<!-- https://mvnrepository.com/artifact/com.mybatis-flex/mybatis-flex-spring-boot3-starter -->
		<dependency>
			<groupId>com.mybatis-flex</groupId>
			<artifactId>mybatis-flex-spring-boot3-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>com.mybatis-flex</groupId>
			<artifactId>mybatis-flex-processor</artifactId>
			<scope>provided</scope>
		</dependency>
	

	
