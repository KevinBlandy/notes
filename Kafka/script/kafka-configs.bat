-------------------------
kafka-configs.bat		 |
-------------------------
	# kafka-configs.bat 脚本是专门用来对配置进行操作的
		* 这里的操作是指在运行状态下修改原有的配置,如此可以达到动态变更的目的
		* 脚本包含变更配置 alter 和查看配置 describe 这两种指令类型
		* 脚本变更配置的原则一样,增 ,删,改的行为都可以看作变更操作 
		* 脚本不仅可以支持操作主题相关的配置,还可 以支持操作 broker,用户,和客户端这 3 个类型的配置
	
	bin/kafka-configs.bat --zookeeper localhost:2181 --describe --entity-type topics --entity-name topic-config
		
		--entity-type
			* 指定要修改的目标
				topics	修改主题名称
				brokers	指定brokerld值,即 broker 中 broker.id 参数配置的值
				clients 指定 clientld 值,即 KatkaProducer 或 KatkaConsumer 的 client.id 参数配置的值
				users	指定用户名

		--entity-name
			* 指定目标的值