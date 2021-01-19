
--------------------------------
kafka 单机的安装				|
--------------------------------
	# 下载,解压
	# 启动ZKServer
		* kafka自带了一个Zookeeper
			lib
			|-zookeeper-3.4.13.jar
			config
			|-zookeeper.properties
		
		zookeeper-server-start.bat ../../config/zookeeper.properties
	
	# 启动Kafka
		kafka-server-start.bat ../../config/server.properties
		

--------------------------------
kafka 安装问题					|
--------------------------------
	# 错误: 找不到或无法加载主类 Files\Java\jdk1.8.0_171\lib\dt.jar;C:\Program
		* 原因是因为当前的Java环境使用的是jdk而不是jre(网上说的...)
		* 修改文件:kafka-run-class.bat(Linux的话修改 sh文件)
		* 修改 COMMAND 变量值(使用rem注释原来的,然后添加下面的,就是把 %CLASSPATH% 添加了一个双引号)

		rem set COMMAND=%JAVA% %KAFKA_HEAP_OPTS% %KAFKA_JVM_PERFORMANCE_OPTS% %KAFKA_JMX_OPTS% %KAFKA_LOG4J_OPTS% -cp %CLASSPATH% %KAFKA_OPTS% %*
		set COMMAND=%JAVA% %KAFKA_HEAP_OPTS% %KAFKA_JVM_PERFORMANCE_OPTS% %KAFKA_JMX_OPTS% %KAFKA_LOG4J_OPTS% -cp "%CLASSPATH%" %KAFKA_OPTS% %*

