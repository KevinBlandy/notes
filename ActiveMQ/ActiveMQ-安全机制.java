---------------------------
ActiveMQ-安全机制			|
---------------------------
	# activemq的web管理界面:http://127.0.0.1:8161/admin
		* activemq管理控制台使用Jetty部署
		* 修改密码,需要到相应的配置文件中修改
		./conf/jetty-realm.properties

	# activemq应该设置有安全机制,只有符合认证的用户才能进行发送和获取消息
		./conf/activemq.xml
		* 在123后,添加配置(添加插件)
		