-------------------------------
编译插件						|
-------------------------------
	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-compiler-plugin</artifactId>
		<version>3.7.0</version>
		<configuration>
			<!-- 编译参数 -->
			<compilerArgs>
				<arg>-parameters</arg>
			</compilerArgs>
			<source>1.8</source>
			<target>1.8</target>
			<encoding>UTF-8</encoding>
		</configuration>
	</plugin>

-------------------------------
资源处理插件					|
-------------------------------
<dependency>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-resources-plugin</artifactId>
	<version>3.1.0</version>
</dependency>

# 使用maven中的变量,替换掉配置文件的变量
	* 在打包时,使用pom里面的变量来替换配置文件中的变量
	* pom配置
		<foo.name>Kevin</foo.name>
		...
		<configuration>	
			<outputDirectory>target/classes</outputDirectory>							
			<useDefaultDelimiters>false</useDefaultDelimiters>							
			<delimiters>								
				<delimiter>$</delimiter>
			</delimiters>
		</configuration>

	* yum配置
		name: $foo.name$
	
	* 只要是pom里面能使用的变量,都可以替换

-------------------------------
远程部署插件					|
-------------------------------
	1,tomcat配置用户与权限
		* 添加配置
		* ./conf/tomcat-users.xml
			<role rolename="manager-script"/>
			<user username="admin" password="password" roles="manager-script"/>
		* 对于tomcat9来说，不能同时赋予用户manager-script和manager-gui角色。
		* 远程部署,起码需要的 manager-script 角色
	
	2,修改IP限制
		* 修改配置
		* 该配置限定了远程访问manager的IP
		* webapps/manager/META-INF/context.xml
			<Context antiResourceLocking="false" privileged="true" >
				<!--
			  <Valve className="org.apache.catalina.valves.RemoteAddrValve"
					 allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />
				-->
			</Context>
		* 注释掉子标签

	
	3,添加插件
		<plugin>
			<groupId>org.apache.tomcat.maven</groupId>
			<artifactId>tomcat7-maven-plugin</artifactId>
			<version>2.2</version>
			<configuration>
				<url>http://59.110.143.96/manager/text</url>			/*/
				<username>KevinBlandy</username>
				<password>F8575532</password>
				<update>true</update>
				<!-- 注意,该路径不能重复 -->
				<path>/teach</path>	
			</configuration>
		</plugin>
	
	4,命令
		tomcat7:deploy
		tomcat7:redeploy
		tomcat7:undeploy

		* 如果是第一次部署，运行mvn tomcat7:deploy进行自动部署(对于tomcat8,9，也是使用tomcat7命令)，
		* 如果是更新了代码后重新部署更新，运行mvn tomcat7:redeploy，
		* 如果第一次部署使用mvn tomcat7:redeploy，则只会执行上传war文件，服务器不会自动解压部署。
		* 如果路径在tomcat服务器中已存在并且使用mvn tomcat7:deploy命令的话，上面的配置中一定要配置<update>true</update>，不然会报错。
	

	5,解决内存泄漏问题
		* 在manager页面中点击:[find leaks] 摁钮
		* 如果顶部出现了内存泄漏信息,则是发生了内存泄漏
			The following web applications were stopped (reloaded, undeployed), but their
			classes from previous runs are still loaded in memory, thus causing a memory
			leak (use a profiler to confirm):

		* 项目添加依赖
			* Servlet2
				<dependency>
					<groupId>se.jiderhamn.classloader-leak-prevention</groupId>
					<artifactId>classloader-leak-prevention-servlet</artifactId>
					<version>2.2.0</version>
				</dependency>
			* Servlet3
				<dependency>
					<groupId>se.jiderhamn.classloader-leak-prevention</groupId>
					<artifactId>classloader-leak-prevention-servlet3</artifactId>
					<version>2.2.0</version>
				</dependency>


		* 在项目的web.xml中添加一个Listener（必须让此Listener成为web.xml中的第一个Listener，否则不起作用）
			<listener>
				<listener-class>se.jiderhamn.classloader.leak.prevention.ClassLoaderLeakPreventorListener</listener-class>
			</listener>

		*　注意
			1,添加这个Listener后，默认在tomcat关闭5s后jvm会进行内存回收的操作，具体时间设置可在下面的链接中找到，所以，在关闭后的5s内，再次启动tomcat，可能会存在问题，导致启动无效（如果出现tomcat重启后日志显示正常但是服务器不工作的话考虑一下是不是这个问题）。
				* https://github.com/mjiderhamn/classloader-leak-prevention
				<!-- 服务器停止后,等等多久进行垃圾回收 -->
				<context-param>
					<param-name>ClassLoaderLeakPreventor.threadWaitMs</param-name>
					<param-value>1000</param-value>
				</context-param>
			2,这个Listener只解决部署的内存泄漏，其他问题（如jdbc等）产生的内存泄漏还需要自己解决


	# 解除Tomcat后台管理上传部署war包大小限制
		* 编辑:webapps/manager/WEB-INF/web.xml,大约50行处
		* 默认是50MB,修改.改大点。加个0
			<multipart-config>
				<!-- 50MB max -->
				<max-file-size>524288000</max-file-size>
				<max-request-size>524288000</max-request-size>
				<file-size-threshold>0</file-size-threshold>
			</multipart-config>

	# 可以删除ROOT项目



-------------------------------
自动添加版权声明				|
-------------------------------
<plugin>
	<groupId>com.mycila.maven-license-plugin</groupId>
	<artifactId>maven-license-plugin</artifactId>
	<version>${maven-license-plugin.version}</version>
	<configuration>
		<basedir>${basedir}</basedir>
		<header>src/main/resources/etc/header.txt</header>
		<quiet>false</quiet>
		<failIfMissing>true</failIfMissing>
		<aggregate>true</aggregate>
		<strictCheck>true</strictCheck>
		<includes>
			<include>**/src/*/java/**/*.java</include>
			<include>**/src/*/webapp/js/*.js</include>
			<include>**/src/*/webapp/css/*.css</include>
			<include>**/src/*/webapp/scss/*.scss</include>
			<include>**/src/*/resources/*.properties</include>
			<include>**/src/*/webapp/WEB-INF/*.xml</include>
			<include>**/src/*/webapp/*.xml</include>
			<include>gulpfile.js</include>
		</includes>
		<excludes>
			<exclude>**/src/main/java/**/package-info.java</exclude>
			<exclude>**/src/main/java/**/Pangu.java</exclude>
			<exclude>**/src/*/webapp/js/lib/*.js</exclude>
			<exclude>**/src/*/webapp/js/*.min.js</exclude>
			<exclude>**/src/*/webapp/css/*.css</exclude>
		</excludes>

		<useDefaultExcludes>true</useDefaultExcludes>
		<mapping>
			<java>SLASHSTAR_STYLE</java>
			<scss>SLASHSTAR_STYLE</scss>
		</mapping>
		<useDefaultMapping>true</useDefaultMapping>
		<properties>
			<year>2012-2018</year>
			<devTeam>b3log.org &amp; hacpai.com</devTeam>
		</properties>
		<encoding>UTF-8</encoding>
	</configuration>
	<executions>
		<!-- 
		eclipse sucks!!
		<execution>
			<phase>generate-resources</phase>
			<goals>
				<goal>format</goal>
			</goals>
		</execution>
		-->
	</executions>
</plugin>