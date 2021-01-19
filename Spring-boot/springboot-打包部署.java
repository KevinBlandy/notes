-----------------------
Spring boot-打jar包		|
-----------------------
	# 不多说,直接默认就是
	# 注册为 Linux 的服务
		<build> 
			<plugins>
				<plugin>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-maven-plugin</artifactId>
					<configuration>
						<executable>true</executable>
					</configuration>
				</plugin>
			</plugins>
		</build>
		* 很简单,添加一个 configuration 配置即可,然后复制软连接(最好重新命名)到 /etc/init.d 目下.就OK了
		* 项目的日志在: /var/log/{服务名}.log 下
	
-----------------------
Spring boot-打war包		|
-----------------------
	# 当我们新建的spring boot 项目时选择的打包方式是jar,部署的时候我们又希望是war
	# 怎么把jar转换为war? ,如下步骤,看懂了.反过来(war -> jar)也就懂了
	

	1,修改POM文件
		* 该为war包打包方式,并且添加 依赖
			

	2,默认的Servlet容器依赖环境修改
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>

	3,增加 ServletInitializer 类
		public class ServletInitializer extends SpringBootServletInitializer{
			@Override
			protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
				//指定 @SpringBootApplication 所在类
				return application.sources(Main.class);
			}
		}


