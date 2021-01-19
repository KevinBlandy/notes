-------------------------------
ActiveMQ-常见问题&处理			|
--------------------------------
	# 修改WEB管理控制台的端口
		* 修改conf目录中的jetty.xml
			<bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
				 <!-- the default port number for the web console -->
				<property name="host" value="0.0.0.0"/>
				<!-- 修改下面这个数 8161,改为你开心的数字就OK -->
				<property name="port" value="8161"/>
			</bean>
	
	# 修改消息服务器的端口
		* 修改conf/activemq.xml文件,你应该可以看出在哪里修改端口
		<transportConnectors>
			<transportConnector name="openwire" uri="tcp://10.42.220.72:61618"discoveryUri="multicast://default"/>
		</transportConnectors>
	
	# 修改后台管理用户的用户名或者密码
		* 修改conf目录中的jetty-realm.properties文件
			admin: admin, admin
			user: user, user
	

	# 配置消息优先级
		* 修改conf目录下,activemq.xml