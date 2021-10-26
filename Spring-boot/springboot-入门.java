----------------------------
Spring-Boot ����			|
----------------------------
	
	# ���Ĺ���
		* ��webӦ�ó�������� jar ����ʽ����
			> ���� Jetty/Tomcat ������
		* �޴������ɺ�xml����
			> ����Ҫxml,Ҳ�������sprin����������
		* �ṩ spring-boot-starter-web ��maven����
			> maven�����ÿ�,���Զ������� spring ��һЩ���
		* �Զ����� spring
		* Ӧ�ü��
		
	# ѧϰ��վ
		https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#getting-started
		http://www.infoq.com/cn/articles/microframeworks1-spring-boot
		http://412887952-qq-com.iteye.com/blog/2344171
		http://i.youku.com/i/UMjM3MTgwNzI4/videos?spm=a2hzp.8244740.0.0&qq-pf-to=pcqq.group
	
	# ������WEB���� & ��� - ����
			<parent>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-parent</artifactId>
				<version>2.5.6</version>
			</parent>

			<properties>
				<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
				<project.reporting.outputEncoding>UTF-8
				</project.reporting.outputEncoding>
				<encoding>UTF-8</encoding>
				<java.version>17</java.version>
				<maven.compiler.source>17</maven.compiler.source>
				<maven.compiler.target>17</maven.compiler.target>
				
				<skipTests>true</skipTests>
				
				<apt-maven-plugin.version>1.1.3</apt-maven-plugin.version>
				<org.mapstruct.version>1.4.2.Final</org.mapstruct.version>
				<guava.version>31.0.1-jre</guava.version>
				<knife4j-spring-boot-starter.version>3.0.3</knife4j-spring-boot-starter.version>
				<springfox-boot-starter.version>3.0.0</springfox-boot-starter.version>
				<java-jwt.version>3.18.2</java-jwt.version>
				<redisson-spring-boot-starter.version>3.16.3</redisson-spring-boot-starter.version>
			</properties>

			<dependencies>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-test</artifactId>
					<scope>test</scope>
				</dependency>
				<dependency>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
					<scope>test</scope>
				</dependency>

				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-web</artifactId>
					<exclusions>
						<exclusion>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-starter-tomcat</artifactId>
						</exclusion>
					</exclusions>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-undertow</artifactId>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-data-jpa</artifactId>
				</dependency>
				<dependency>
					<groupId>javax.annotation</groupId>
					<artifactId>javax.annotation-api</artifactId>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-validation</artifactId>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-websocket</artifactId>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-data-redis</artifactId>
				</dependency>
				<dependency>
					<groupId>io.lettuce</groupId>
					<artifactId>lettuce-core</artifactId>
				</dependency>
				<dependency>
					<groupId>org.apache.commons</groupId>
					<artifactId>commons-pool2</artifactId>
				</dependency>
				<dependency>
					<groupId>com.auth0</groupId>
					<artifactId>java-jwt</artifactId>
					<version>${java-jwt.version}</version>
				</dependency>
				<dependency>
					<groupId>org.redisson</groupId>
					<artifactId>redisson-spring-boot-starter</artifactId>
					<version>${redisson-spring-boot-starter.version}</version>
				</dependency>
				<dependency>
					<groupId>mysql</groupId>
					<artifactId>mysql-connector-java</artifactId>
				</dependency>
				<dependency>
					<groupId>org.projectlombok</groupId>
					<artifactId>lombok</artifactId>
					<scope>provided</scope>
				</dependency>
				<dependency>
					<groupId>com.querydsl</groupId>
					<artifactId>querydsl-jpa</artifactId>
				</dependency>
				<dependency>
					<groupId>com.querydsl</groupId>
					<artifactId>querydsl-apt</artifactId>
					<scope>provided</scope>
				</dependency>
				<dependency>
					<groupId>org.mapstruct</groupId>
					<artifactId>mapstruct</artifactId>
					<version>${org.mapstruct.version}</version>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-freemarker</artifactId>
				</dependency> 
					<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-mail</artifactId>
				</dependency>
				<dependency>
					<groupId>com.zaxxer</groupId>
					<artifactId>HikariCP</artifactId>
				</dependency>
				<dependency>
					<groupId>com.google.code.gson</groupId>
					<artifactId>gson</artifactId>
				</dependency>
				<dependency>
					<groupId>com.google.guava</groupId>
					<artifactId>guava</artifactId>
					<version>${guava.version}</version>
				</dependency>
				<dependency>
					<groupId>com.github.xiaoymin</groupId>
					<artifactId>knife4j-spring-boot-starter</artifactId>
					<version>${knife4j-spring-boot-starter.version}</version>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-actuator</artifactId>
				</dependency>
				<dependency>
					<groupId>io.springfox</groupId>
					<artifactId>springfox-boot-starter</artifactId>
					<version>${springfox-boot-starter.version}</version>
				</dependency>
			</dependencies>

			<build>
				<plugins>
					<plugin>
						<groupId>org.springframework.boot</groupId>
						<artifactId>spring-boot-maven-plugin</artifactId>
						<configuration>
							<executable>true</executable>
							<includeSystemScope>true</includeSystemScope>
						</configuration>
					</plugin>
					<plugin>
						<groupId>com.mysema.maven</groupId>
						<artifactId>apt-maven-plugin</artifactId>
						<version>${apt-maven-plugin.version}</version>
						<executions>
							<execution>
								<goals>
									<goal>process</goal>
								</goals>
								<configuration>
									<outputDirectory>target/generated-sources/java</outputDirectory>
									<processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
								</configuration>
							</execution>
						</executions>
					</plugin>
					<plugin>
						<groupId>org.apache.maven.plugins</groupId>
						<artifactId>maven-compiler-plugin</artifactId>
						<configuration>
							<parameters>true</parameters>
							<source>${maven.compiler.source}</source>
							<target>${maven.compiler.target}</target>
							<annotationProcessorPaths>
								<path>
									<groupId>org.mapstruct</groupId>
									<artifactId>mapstruct-processor</artifactId>
									<version>${org.mapstruct.version}</version>
								</path>
								<path>
									<groupId>org.projectlombok</groupId>
									<artifactId>lombok</artifactId>
									<version>${lombok.version}</version>
								</path>
							</annotationProcessorPaths>
						</configuration>
					</plugin>
				</plugins>
			</build>
	
	# �����Ҫ�Զ��� parentģ��,��������̳�springboot������
		<dependencyManagement>
			<dependencies>
				<!-- Spring Boot parent.pom �ĸ�������,�����������˿�����������а汾��Ϣ -->
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-dependencies</artifactId>
					<version>${spring-boot.version}</version>
					<type>pom</type>
					<scope>import</scope>
				</dependency>
			</dependencies>
		</dependencyManagement>

	# ������,ֱ����������
		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;
		import org.springframework.boot.web.support.SpringBootServletInitializer;

		@SpringBootApplication
		public class ParkApplication extends SpringBootServletInitializer{

			public static ApplicationContext applicationContext = null;
	
			public static void main(String[] args) {

				String configLocation = String.join(File.separator, "file:${user.home}", "app", "config", "");
				
				System.setProperty(ConfigFileApplicationListener.CONFIG_ADDITIONAL_LOCATION_PROPERTY, configLocation);
				
				SpringApplication springApplication = new SpringApplication(ParkApplication.class);
				springApplication.addListeners(new ApplicationPidFileWriter());
				
				applicationContext = springApplication.run(args);
				
				// SpringApplication.exit(applicationContext, () -> 0);
			}
		}


	# ��ͳ��spring��Ŀ
		web.xml
			* ǰ�˿�����,����servlet.xml
			* Spring ����filter
			* Spring IOC ����
			* Druid ���
		servlet.xml
			* mvc:annotaion
				* �Զ���converter
			* interceptor
			* ��ͼ������
			* �ļ��ϴ�֧��
		application-context.xml
			* DataSource
			* �־ò㹤��
			* 
		

----------------------------
Spring-Boot ģ��			|
----------------------------
			spring-boot-starter					���� POM�������Զ�����֧�֡���־��Ͷ� YAML �����ļ���֧�֡�
			spring-boot-starter-amqp			ͨ�� spring-rabbit ֧�� AMQP��
			spring-boot-starter-aop				���� spring-aop �� AspectJ ��֧�����������̣�AOP����
			spring-boot-starter-batch			֧�� Spring Batch������ HSQLDB��
			spring-boot-starter-data-jpa		���� spring-data-jpa��spring-orm �� Hibernate ��֧�� JPA��
			spring-boot-starter-data-mongodb	���� spring-data-mongodb ��֧�� MongoDB��
			spring-boot-starter-data-rest		ͨ�� spring-data-rest-webmvc ֧���� REST ��ʽ��¶ Spring Data �ֿ⡣
			spring-boot-starter-jdbc			֧��ʹ�� JDBC �������ݿ⡣
			spring-boot-starter-security		���� spring-security��
			spring-boot-starter-test			�������õĲ���������������� JUnit��Hamcrest��Mockito �� spring-test �ȡ�
			spring-boot-starter-velocity		֧��ʹ�� Velocity ��Ϊģ�����档
			spring-boot-starter-web				֧�� Web Ӧ�ÿ��������� Tomcat �� spring-mvc��
			spring-boot-starter-websocket		֧��ʹ�� Tomcat ���� WebSocket Ӧ�á�
			spring-boot-starter-ws				֧�� Spring Web Services��
			spring-boot-starter-actuator		������������������Ĺ��ܣ�������ָ��ͼ��ȹ��ܡ�
			spring-boot-starter-remote-shell	���Զ�� SSH ֧�֡�
			spring-boot-starter-jetty			ʹ�� Jetty ������Ĭ�ϵ� Tomcat ��ΪӦ�÷�������
			spring-boot-starter-log4j			��� Log4j ��֧�֡�
			spring-boot-starter-logging			ʹ�� Spring Boot Ĭ�ϵ���־��� Logback��
			spring-boot-starter-tomcat			ʹ�� Spring Boot Ĭ�ϵ� Tomcat ��ΪӦ�÷�������


----------------------------
Spring-Boot ����������jar	|
----------------------------
	# ֻҪ���һ���������
		<build> 
			<plugins>
				<plugin>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-maven-plugin</artifactId>
				</plugin>
			</plugins>
		</build>
	
	# ��ͳ��ʽ
		<!-- �������� -->
		<build>
			<!--
				jar����
			-->
			<finalName>spring-boot</finalName>
			<plugins>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-jar-plugin</artifactId>
					<configuration>
						<classesDirectory>target/classes/</classesDirectory>
						<archive>
							<manifest>
								<!-- ָ��main���� -->
								<mainClass>com.kevin.example.springboot.controller.SampleController</mainClass>
								<!-- ���ʱ MANIFEST.MF�ļ�����¼��ʱ����汾 -->
								<useUniqueVersions>false</useUniqueVersions>
								<addClasspath>true</addClasspath>
								<!-- ����������Ŀ¼����-->
								<classpathPrefix>lib/</classpathPrefix>
							</manifest>
							<manifestEntries>
								<Class-Path>.</Class-Path>
							</manifestEntries>
						</archive>
					</configuration>
				</plugin>
				<!-- �������Ĵ����� -->
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-dependency-plugin</artifactId>
					<executions>
						<execution>
							<id>copy-dependencies</id>
							<phase>package</phase>
							<goals>
								<goal>copy-dependencies</goal>
							</goals>
							<configuration>
								<type>jar</type>
								<includeTypes>jar</includeTypes>
								<!-- ���������Ŀ¼ -->
								<outputDirectory>${project.build.directory}/lib</outputDirectory>
							</configuration>
						</execution>
					</executions>
				</plugin>
			</plugins>
		</build>

		