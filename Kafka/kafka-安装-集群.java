--------------------------------
kafka 集群的安装				|
--------------------------------
	# 设置每个节点的id值(非负,不能重复)
		broker.id=0
	
	# 连接到同一个zookeeper中
		* 如果zookeeper也是集群,可以在server.properties中配置多个地址

		zookeeper.connect=host:2181,host:2181,host:2181
	
	# 如果是伪集群,需要注意端口号和日志目录不能重复
		listeners=PLAINTEXT://:9092
		log.dirs=/tmp/kafka-logs
	
