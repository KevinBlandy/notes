--------------------------------
Jetty 插件						|
--------------------------------
	<plugin>
		<groupId>org.eclipse.jetty</groupId>
		<artifactId>jetty-maven-plugin</artifactId>
		<version>9.4.14.v20181114</version>
		<configuration>
			<httpConnector>
				<!-- jetty端口 -->
				<port>80</port>
			</httpConnector>
			<webApp>
				<!-- 上下文访问路径 -->
				<contextPath>/foo</contextPath>
			</webApp>
		</configuration>
	</plugin>
	
	# 启动的Maven指令
		jetty:run