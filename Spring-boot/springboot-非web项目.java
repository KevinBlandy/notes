------------------------------
springboot 非 web项目		  |
------------------------------
	# 在非 web 项目中使用springboot
	# MAVEN
		<parent>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-parent</artifactId>
			<version>2.1.3.RELEASE</version>
		</parent>

		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter</artifactId>
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

		* 父级 pom 还是建议使用 parent
		* 仅仅需要依赖一个 spring-boot-starter 就可以了
	
	# Main 函数
		import java.io.IOException;

		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;
		import org.springframework.context.ConfigurableApplicationContext;

		@SpringBootApplication
		public class Main {

			public static void main(String[] args) throws IOException {

				SpringApplication springApplication = new SpringApplication(Main.class);
				
				ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
				
				System.in.read();
			}
		}

	