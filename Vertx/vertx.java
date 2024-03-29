-----------------------------
Vertx
-----------------------------
	# 官方地址
		https://vertx.io/
		https://github.com/eclipse-vertx/vert.x
		https://vertx-china.github.io/
		https://how-to.vertx.io/

		https://vertx.io/docs/apidocs/
	
	# 学习参考
		https://segmentfault.com/a/1190000021036621
		http://vertxchina.github.io/vertx-translation-chinese/

		https://www.bilibili.com/video/BV1Ep4y1Y7Do
	
	# Maven
		<dependency>
			<groupId>io.vertx</groupId>
			<artifactId>vertx-core</artifactId>
			<version>3.9.2</version>
		</dependency>
		
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-compiler-plugin</artifactId>
			<version>3.8.1</version>
			<configuration>
				<compilerArgs>
					<arg>-parameters</arg>
				</compilerArgs>
				<source>1.8</source>
				<target>1.8</target>
				<encoding>UTF-8</encoding>
			</configuration>
		</plugin>

		* 起码JDK8
	

-----------------------------
启动
-----------------------------
	io.vertx.core.Launcher run [verticle]