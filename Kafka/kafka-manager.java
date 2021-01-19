------------------------
kafka-manager			|
------------------------
	# github
		https://github.com/yahoo/kafka-manager

	# 下载
		git clone git@github.com:yahoo/kafka-manager.git
	
	# 编译
		./sbt clean dist
	
		* 会在~路径下创建一个 .sbt目录,编译后的文件存放在该目录相应子目录里
		* 如果提示sbt-launch.jar 下载失败,那么需要自己手动的去下载该依赖,并且上传到指定的目录下
			~/.sbt/launchers/0.13.9/	
	
	# 解压文件:kafka-manager-1.3.3.22.zip
		unzip /kafka-manager/target/universal/kafka-manager-1.3.3.22.zip


	# 配置:conf/application.conf
		kafka-manager.zkhosts="my.zookeeper.host.com:2181"
	
	# 进入bin目录启动
		nohup ./bin/kafka-manager -Dconfig.file=./conf/application.conf & 
	

	# 功能的启用和禁用
		application.features=["KMClusterManagerFeature","KMTopicManagerFeature","KMPreferredReplicaElectionFeature","KMReassignPartitionsFeature"]

		KMClusterManagerFeature
			* 允许从Kafka Manager添加,更新,删除集群

		KMTopicManagerFeature
			* 允许从Kafka群集添加,更新,删除主题

		KMPreferredReplicaElectionFeature
			* 允许为Kafka群集运行首选副本选择

		KMReassignPartitionsFeature
			* 允许生成分区分配和重新分配分区
	
	# 访问端口默认:9000
		* 可以启动时修改
		-Dhttp.port=1024
	
	# 关闭
		* 直接kill掉进程,jps进程名称:ProdServerStart
