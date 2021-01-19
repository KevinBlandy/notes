---------------------
kafka-入门			 |
---------------------
	# 官网
		http://kafka.apache.org
	
	# 中文文档
		http://kafka.apachecn.org/intro.html

	# 参考
		https://blog.csdn.net/lizhitao/article/details/39499283
		http://www.jasongj.com/tags/Kafka/
		https://www.jianshu.com/p/d3e963ff8b70

	
	# 特点
		* 吞吐量高,在廉价的机器上,单机可以支持100w/s消息的读写
		* 消息持久化,所有消息都会被持久化到硬盘,无消息丢失,支持消息重放
		* 完全分布式:Producer,Broker,Consumer都支持水平的扩展
		* 同时满足适应在线流处理和离线批处理
		
		
---------------------
kafka-目录结构		 |
---------------------
	bin
		windows
		kafka-run-class.sh
		kafka-server-start.sh
		kafka-server-stop.sh
	logs(运行时创建的文件夹)
		controller.log
			* KafkaController 运行时日志,默认 TRACE
		kafka-authorizer.log
			* 权限认证相关操作日志,默认 WARN
		kafka-requests.log
			* 网络请求日志,默认 WARN
		kafkaServer-gc.log
			* 运行过程,GC的日志,默认 INFO
		log-cleaner.log
			* 日志清理操作相关统计信息,默认 INFO
		server.log
			* KafkaServer 运行日志,默认 INFO
		state-change.log
			* 分区角色切换等状态转换日志,默认 TRACE
		
	config
		connect-console-sink.properties
		connect-console-source.properties
		connect-distributed.properties
		connect-file-sink.properties
		connect-file-source.properties
		connect-log4j.properties
		connect-standalone.properties
		consumer.properties
		log4j.properties
		producer.properties
		server.properties
		tools-log4j.properties
		trogdor.conf
		zookeeper.properties
	libs
	site-docs
		kafka_2.12-2.1.1-site-docs.tgz


---------------------
kafka 消息协议		 |
---------------------

|offset|length|CRC32|Magic|timestamp|attributes|key length|key|value length|value|

|4字节偏移量|4字节长度|4字节CRC32|1字节魔术变量|8字节时间戳|1字节attributes(枚举)|4字节keylength|key|4字节valuelength|value|
