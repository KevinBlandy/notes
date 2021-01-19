----------------------------
Spring-Boot 入门			|
----------------------------
	
	# 核心功能
		* 让web应用程序可以以 jar 的形式运行
			> 内置 Jetty/Tomcat 等容器
		* 无代码生成和xml配置
			> 不需要xml,也可以完成sprin的所有配置
		* 提供 spring-boot-starter-web 简化maven依赖
			> maven依赖该库,会自动的依赖 spring 的一些组件
		* 自动配置 spring
		* 应用监控
		
	# 学习网站
		https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#getting-started
		http://www.infoq.com/cn/articles/microframeworks1-spring-boot
		http://412887952-qq-com.iteye.com/blog/2344171
		http://i.youku.com/i/UMjM3MTgwNzI4/videos?spm=a2hzp.8244740.0.0&qq-pf-to=pcqq.group
	
	# 父级与WEB依赖 & 插件
			<parent>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-parent</artifactId>
				<version>2.4.1</version>
			</parent>

			<properties>
				<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
				<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
				<java.version>1.8</java.version>

				<skipTests>true</skipTests>
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
				</plugins>
			</build>
	
	# 如果需要自定义 parent模块,但是又想继承springboot的依赖
		<dependencyManagement>
			<dependencies>
				<!-- Spring Boot parent.pom 的父级依赖,该依赖定义了框架依赖的所有版本信息 -->
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-dependencies</artifactId>
					<version>${spring-boot.version}</version>
					<type>pom</type>
					<scope>import</scope>
				</dependency>
			</dependencies>
		</dependencyManagement>

	# 主函数,直接启动代码
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


	# 传统的spring项目
		web.xml
			* 前端控制器,加载servlet.xml
			* Spring 编码filter
			* Spring IOC 监听
			* Druid 监控
		servlet.xml
			* mvc:annotaion
				* 自定义converter
			* interceptor
			* 视图解析器
			* 文件上传支持
		application-context.xml
			* DataSource
			* 持久层工厂
			* 
		

----------------------------
Spring-Boot 模块			|
----------------------------
			spring-boot-starter					核心 POM，包含自动配置支持、日志库和对 YAML 配置文件的支持。
			spring-boot-starter-amqp			通过 spring-rabbit 支持 AMQP。
			spring-boot-starter-aop				包含 spring-aop 和 AspectJ 来支持面向切面编程（AOP）。
			spring-boot-starter-batch			支持 Spring Batch，包含 HSQLDB。
			spring-boot-starter-data-jpa		包含 spring-data-jpa、spring-orm 和 Hibernate 来支持 JPA。
			spring-boot-starter-data-mongodb	包含 spring-data-mongodb 来支持 MongoDB。
			spring-boot-starter-data-rest		通过 spring-data-rest-webmvc 支持以 REST 方式暴露 Spring Data 仓库。
			spring-boot-starter-jdbc			支持使用 JDBC 访问数据库。
			spring-boot-starter-security		包含 spring-security。
			spring-boot-starter-test			包含常用的测试所需的依赖，如 JUnit、Hamcrest、Mockito 和 spring-test 等。
			spring-boot-starter-velocity		支持使用 Velocity 作为模板引擎。
			spring-boot-starter-web				支持 Web 应用开发，包含 Tomcat 和 spring-mvc。
			spring-boot-starter-websocket		支持使用 Tomcat 开发 WebSocket 应用。
			spring-boot-starter-ws				支持 Spring Web Services。
			spring-boot-starter-actuator		添加适用于生产环境的功能，如性能指标和监测等功能。
			spring-boot-starter-remote-shell	添加远程 SSH 支持。
			spring-boot-starter-jetty			使用 Jetty 而不是默认的 Tomcat 作为应用服务器。
			spring-boot-starter-log4j			添加 Log4j 的支持。
			spring-boot-starter-logging			使用 Spring Boot 默认的日志框架 Logback。
			spring-boot-starter-tomcat			使用 Spring Boot 默认的 Tomcat 作为应用服务器。


----------------------------
Spring-Boot 构建可运行jar	|
----------------------------
	# 只要添加一个插件即可
		<build> 
			<plugins>
				<plugin>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-maven-plugin</artifactId>
				</plugin>
			</plugins>
		</build>
	
	# 传统方式
		<!-- 构建配置 -->
		<build>
			<!--
				jar包名
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
								<!-- 指定main函数 -->
								<mainClass>com.kevin.example.springboot.controller.SampleController</mainClass>
								<!-- 打包时 MANIFEST.MF文件不记录的时间戳版本 -->
								<useUniqueVersions>false</useUniqueVersions>
								<addClasspath>true</addClasspath>
								<!-- 依赖包加载目录名称-->
								<classpathPrefix>lib/</classpathPrefix>
							</manifest>
							<manifestEntries>
								<Class-Path>.</Class-Path>
							</manifestEntries>
						</archive>
					</configuration>
				</plugin>
				<!-- 依赖包的处理插件 -->
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
								<!-- 依赖包存放目录 -->
								<outputDirectory>${project.build.directory}/lib</outputDirectory>
							</configuration>
						</execution>
					</executions>
				</plugin>
			</plugins>
		</build>

		