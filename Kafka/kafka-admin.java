-----------------------------
admin					     |
-----------------------------
	# Maven
		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka_2.12</artifactId>
			<version>2.1.1</version>
		</dependency>
	
	# 包含了 shell 脚本执行命令时实际执行的类
	# 可以直接执行这些类的main方法,用于对kafka的维护
		String commands[] = new String[] {
			"--zookeeper","192.168.2.102:2181",
			"--create",
			"--partitions","1",
			"--replication-factor","1",
			"--topic","topic-demo"
		};
		TopicCommand.main(commands);
	
	# 相关的类库
		AclCommand
			* kafka-acls.sh 脚本使用的类
		
		TopicCommand
			* kafka-topics.sh 脚本使用的类
		
		KafkaAdminClient
			* 一个超级管理的类,可以用于管理broker,topic,acl
