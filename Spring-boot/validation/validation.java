--------------------
validation			|
--------------------
	# javaEE规范（Bean Validation规范）
		JSR 303	Bean Validation 1.0
		JSR 349	Bean Validation 1.1
		JSR 380	Bean Validation 2.0
			https://beanvalidation.org/news/2017/08/07/bean-validation-2-0-is-a-spec/


	# Maven
		* JavaEE 规范（JSR 303 - ）
			<dependency>
				<groupId>jakarta.validation</groupId>
				<artifactId>jakarta.validation-api</artifactId>
				<version>2.0.2</version>
			</dependency>
	
		* Hibernate实现
			<dependency>
				<groupId>org.hibernate.validator</groupId>
				<artifactId>hibernate-validator</artifactId>
				<version>6.1.5.Final</version>
			</dependency>
		
		* SpringBoot 
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-validation</artifactId>
			</dependency>
	
	# 参考文档
		https://beanvalidation.org/
		https://www.ibm.com/developerworks/cn/java/j-lo-beanvalid/
		https://www.ibm.com/developerworks/cn/java/j-lo-jsr303/
		http://imushan.com/2018/01/18/java/language/%E6%8E%8C%E6%8F%A1Java-Bean-Validation/
		http://hibernate.org/validator/documentation/
	

		
	# 核心组件
		