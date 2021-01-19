
————————————————————————
1,maven依赖				|
————————————————————————	
	* 所有的依赖都是通过坐标来定位的
		GAV
		GroupId
		ArtifactId
		Version
	* 依赖中几个比较重要的概念
	* <dependency>
		  <groupId>junit</groupId>
		  <artifactId>junit</artifactId>
		  <version>4.10</version>
		  <scope>
			provided	//编译和测试的时候会加入jar包,打包的时候不会加入jar包
			test		//仅仅在测试的时候依赖,编译和打包的时候不依赖

			compie		//编译的时候加入jar包,打包的时候也会加进去[默认]
			runtime		//在运行和测试的时候会加入jar,编译的时候不会依赖
		  </scope>
	  </dependency>

		test
			* 在测试范围有效,编译和打包的时候不会用到这个依赖
			* 例如:junit,测试的时候使用到的东西
		compile		//默认
			* 在编译范围有效,在编译和打包时都会把依赖存储进去
		provided
			* 在编译和测试的过程有效,最后生成war包的时候不会加入
			* 例如:servlrt,和jslt包,因为Tomcat会帮我们加
		runtime
			* 在运行的时候依赖,在编译的时候不依赖
			* 例如:MySQL驱动!
	
	* 依赖的传递
		<dependency>
		  <groupId>${project.groupId}</groupId>
		  <artifactId>user-core</artifactId>
		  <version>${project.version}</version>
		  <!-- 排除指定的依赖 -->
		  <exclusions>
			<exclusion>
				<groupId>commons-logging</groupId>
				<artifactId>commons-logging</artifactId>
			</exclusion>
		  </exclusions>
		</dependency>                       
		* 只会传递scope为compile的包
		* 当依赖级别相同的时候,谁先依赖就用谁
		* 当出现包冲突的时候,就使用这个方法来排除

--------------------

	<dependency>
			<groupId></groupId>
			<artifactId></artifactId>
			<version></version>
			<type></type>
			<scope>
				
			</scope>
	</dependency>
	* spoce,控制依赖存在什么时候
	* maven在不同的时候会有不同的classpath
	* scope,就确定了哪些依赖存在于哪些classpath
	1,compile[默认]
		* 编译,测试,运行
	2,provided
		* 编译,测试
		* ServletAPI
	3,runtime
		* 测试,运行
		* JDBC
	4,test
		* 测试
		* Junit
	5,system
		* 编译,测试
		* 跟本地相关
	6,import
		* 导入的范围,它仅使用在dependencyManagerment中,表示,从其他的pom中导入dependecy的配置


传递性的冲突问题
1,A 依赖 B --> 版本是1.0(B的lo4j版本)
2,C 依赖 B --> 版本是1.1
3,D 依赖 A 和 B
	* 这时候,在D中,谁先写.就依赖谁的版本!

------------------------
2,关于选择性依赖		|
------------------------
	* A 依赖 B 
	* B 可以依赖C ,可以依赖D .但是就是不能都依赖
		* 例如:B是一个数据库工具类,要么选择mysql，要么选择Oracle
	需要在依赖中添加一个标签


	<dependency>
		....			mysql的jdbc
		<optionale>true</optinale>
	</dependency>
	<dependency>
		....			Oracle的jdbc
		<optionale>true</optinale>
	</dependency>

	* 此时需要注意的时候,B有了传递性依赖后！！那么A再依赖B!!这俩依赖并不会被传递过去！！！、
	* 所以需要在A中要进行选择,要么自己添加mysql的依赖给b用!要么添加oracle的依赖给b用
	* 通俗的说就是
		* 我有俩选择,但是我不选!谁要依赖我!你给我选择一个吧
	


------------------------
2,本地依赖				|
------------------------
	1,在项目路径下新建 lib 目录(用于存放第三方依赖)
		application
			lib
			src/main/...
			src/test/...

	2,添加依赖
		<dependency>
			<groupId>icbcClient</groupId>
			<artifactId>icbcClient</artifactId>
			<version>1.0</version>
			<scope>system</scope>
			<systemPath>${basedir}/lib/icbcClient-1.0.jar</systemPath> 
		</dependency>

	3,添加资源打包配置
		* <scope>system</scope> 默认不会打包到jar
		<!-- 本地 jar -->
            <resource>
                <directory>${basedir}/lib</directory>
                <targetPath>BOOT-INF/lib/</targetPath>
                <includes>	
                    <include>**/*.jar</include>					**/
                </includes>
            </resource>
            <resource>
                 <directory>src/main/resources</directory>
                 <includes>
                     <include>**/*</include>					**/
                 </includes>
            </resource>
		
		* springboot项目打包本地资源配置
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<!-- 不加这个的话，打包时本地jar打不进去 -->
				<configuration>
					<includeSystemScope>true</includeSystemScope>
				</configuration>
			</plugin>

	
	4,war打包方式的本地lib
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-war-plugin</artifactId>
			<version>3.0.0</version>
			<configuration>
				<webResources>
					<resource>
						<directory>${project.basedir}/src/main/resources/lib/net/pusuo</directory>
						<targetPath>WEB-INF/lib</targetPath>
						<filtering>false</filtering>
						<includes>
							<include>**/*.jar</include>
						</includes>
					</resource>
				</webResources>
			</configuration>
		</plugin>
