--------------------------------
springboot 自定义starter		|
--------------------------------

	1,starter.jar 其实就是个空的jar
		* 里面通过 pom 依赖了:autoconfigure自动配置依赖。
		* 通过 META-INF/spring.provides 配置文件来说明依赖
			provides: spring-context-support,mail
	
	2,autoconfigure.jar就是自动配置jar了。
		
---------------------------------------------------
spring-boot-starter-${project}.jar
---------------------------------------------------
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starters</artifactId>
		<version>1.5.10.RELEASE</version>
	</parent>

	
	<artifactId>spring-boot-starter-${project}</artifactId>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>xxxxxxxxxxxxx</groupId>
			<artifactId>spring-boot-autoconfigure-${project}</artifactId>
		</dependency>
	</dependencies>

---------------------------------------------------
	spring-boot-autoconfigure-${project}.jar
---------------------------------------------------

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-autoconfigure</artifactId>
	</dependency>