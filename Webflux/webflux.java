-----------------
webflux			 |
-----------------
	# 地址
		https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html
		https://projectreactor.io/
	

-----------------
webflux	- 构建
-----------------
	# Maven
		<parent>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-parent</artifactId>
			<version>2.3.3.RELEASE</version>
		</parent>
		<properties>
			<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
			<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
			<java.version>11</java.version>
			<skipTests>true</skipTests>
		</properties>

		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
				<scope>test</scope>
			</dependency>
			<dependency>
				<groupId>io.projectreactor</groupId>
				<artifactId>reactor-test</artifactId>
				<scope>test</scope>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-webflux</artifactId>
			</dependency>

		</dependencies>

		<build>
			<finalName>webflux</finalName>
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
	

----------------------------------
webflux	- 响应式的一些核心概念
----------------------------------
	# 核心对象
		* Mono/Flux
		* 都是作为数据流发布对象，Mono发布0或者1个数据，Flux发布N个数据

	# 3种信号
		消费
		错误(终止操作)
		完成(终止操作)

	# 操作符
		map
			* 对每一个元素进行映射, 返回后组成新的流

		flaMap
			* 和map类似, 不同的是对“流中的每个元素”进行映射，返回后组成新的流



----------------------------------
webflux	- 核心组件
----------------------------------
	DispatcherHandler
		* 前端控制器

	HandlerMapping
		* 映射

	HandlerAdapter
		* 处理器执行器

	HandlerResult
		* 处理器结果

	HandlerResultHandler
		* 对处理器结果进行处理
	
	WebExceptionHandler
	WebFilter
	WebHandler
	WebSessionManager
	ServerCodecConfigurer
	LocaleContextResolver
	ForwardedHeaderTransformer


		
