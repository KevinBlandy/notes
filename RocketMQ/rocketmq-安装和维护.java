-----------------------
单机安装			   |
-----------------------
	# 下载,解压
	# 配置环境变量
		export ROCKETMQ_HOME=/usr/local/rocketmq/alibaba-rocketmq

	# 启动nameserver
		nohup bin/mqnamesrv &

		* 日志文件在: ~/logs/rocketmqlogs/namesrv.log
	
	# 启动broker
		nohup bin/mqbroker &
			-n 
				* 参数指定namesrv的地址和端口(默认是127.0.0.1:9876)
					-n 127.0.0.1:9876
				* 如果namesrv是集群的话, 可以使用分号(;)分隔
					-n 192.168.1.1:9876;192.161.2:9876
			-c
				* 指定properties配置文件
					-c conf/broker.properties
			
			autoCreateTopicEnable
				* 是否允许客户端自动的创建不存在的Topic, 默认为: false
					autoCreateTopicEnable=true

			
		* 日志文件在: ~/logs/rocketmqlogs/broker.log 
	

-----------------------
服务关闭			   |
-----------------------
	# namesrv 关闭
		./bin/mqshutdown namesrv
	
	# broker关闭
		./bin/mqshutdown broker


-----------------------
常见问题			   |
-----------------------
	# 默认端口
		mqnamesrv <-> client	:9876
		mqbroker <-> mqnamesrv	:10909
		mqbroker <-> client		:10911
	
	# 修改内存大小
		* rocketmq默认配置的jvm内存比较大, 如果机器配置小了, 启动会失败
		* 可以修改脚本, 来修改启动的jvm内存

		* 编辑: runbroker.sh
			JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m"

		* 编辑: runserver.sh
			JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"

	
	# Windows 下启动异常
		* 修改脚本:runbroker.cmd
			set CLASSPATH=.;%BASE_DIR%conf;"%CLASSPATH%"

