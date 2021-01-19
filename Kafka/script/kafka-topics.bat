--------------------------------
kafka-topics.bat				|
--------------------------------
	# 创建主题
		 bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
			--zookeeper
				* 指定zk
			--partitions
				* 分区数量
			--replication-factor
				* 每个分区的副本数量
			--topic
				* 主题名称,一般不要用下划线开头,因为kafka内部的主题使用下划线开头
			--replica-assignment
				* 可以自己控制分区的分配
				* 这种方式根据分区号的数值大小按照从小到大的顺序进行排列,分区与分区之间用逗号","隔开
				* 分区内多个副本用冒号":"隔开
				* 并且在使用该参数创建主题时不需要原本必备的 partitions 和 replication-factor 这两个参数
				* 同一个分区内的副本不能有重复,比如指定了 0:0,1:1 这种,就会报出异常
				
				--replica-assignment 2:0,0:1,1:2,2:1

				2:0 表示第 0 个分区,有两个副本,在broker.id 为 2 和 0 的节点上
				0:1 表示第 1 个分区,有两个副本,在broker.id 为 0 和 1 的节点上
				1:2 表示第 2 个分区,有两个副本,在broker.id 为 1 和 2 的节点上
				2:1 表示第 3 个分区,有两个副本,在broker.id 为 2 和 1 的节点上

			--config
				* 自定义配置,覆盖主题的默认配置
				* 该配置项可以存在多个,表示覆盖多个值
					--config kek=value
					--config cleanup.policy=compact --config max.message.bytes=l000
				* 可以在zookeeper的节点下查看这些数据:
					get /config/topics/[topic-name]

			
			--if-not-exists
				* 如果主题已经存在,不会抛出异常,也不会创建成功
	
	# 可以通过 ZooKeeper 客户端来获取 broker分区副本的分配情况
		get /brokers/topics/[主题名]

		{"version":1,"partitions":{"2":[1,2]}}

		partitions:
			* 表示当前主题的分区"2",有两个副本,分配在了border.id 等于 1 和 2 的节点上
			* json对象的key表示主题的分区编号,value数组表示该分区的副本都分配在哪些broker节点上
				
		
		
	# 查看主题详情
		bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic test
			--zookeeper
				* 指定zk
			--describe
				* 查看详情指令
			--topic
				* 主题名称,如果不指定,则显示出所有的主题详情
				* 也可以同时指定多个主题名称,使用逗号分隔
			--topics-with-overrides
				* 找出所有包含覆盖配置的主题,它只会列出包含了与集群不一样配置的主题
				* 注意使用该参数时只显示原本只使用 describe 指令的第一行信息
			--under-replicated-partitions
				* 找出所有包含失效副本的分区
				* 包含失效副本的分区可能正在进行同步操作,也有可能同步发生异常
			--unavailable-partitions 
				* 查看主题中没有 leader 副本的分区,这些分区己经处于离线状态
				* 对于外界的生产者和消费者来说处于不可用的状态
		
		Topic(主题名):test      PartitionCount(分区数量):1        ReplicationFactor(每个分区副本数):1     Configs(主题的配置信息):
        Topic: test     Partition(分区号): 0    Leader(当前分区Lader副本所在节点的broker.id): 2       Replicas(当前分区所有副本所在节点的broker.id - AR): 2     Isr(当前分区的ISR集合 - ISR): 2
		
		Replicas
			* 当前这个分区都在哪些节点上

	# 查看创建的所有主题信息
		bin/kafka-topics.sh --zookeeper localhost:2181 --list
	
	# 查看创建主题时设置的参数(--config)
		get /config/topics/[主题名]

		{
			"version":1,
			"config":{
				"max.message.bytes":"10000",
				"cleanup.poliy":"compact"
			}
		}

		* config 表示设置的一个或者多个配置项

	# replica分配算法考虑机房(0.10.x)
		* 可以配置一个参数broker.rack说明当前broker在哪个机房
		* 算了,用到的时候再去查吧
	
	# 分区副本的分配
		* 如果创建topic时,指定了--replica-assignment参数时,就根据指定的配置去创建分配分区

		* 未指定 --replica-assignment 参数,则使用内部逻辑去分配分区

		* 使用内部逻辑分配时
		* 如果设置了broker.rack,那么就使用指定机房的分配策略
		* 如果所有节点都没设置 broker.rack 或者使用 disabale-rack-awre 参数来创建主题,那么采用的就是未指定机房的分配策略
	
		
		*  未指定机房的分配策略实现类
			AdminUtilities.assignReplicasToBrokersRackUnaware(已经被弃用了,使用AdminZkClient代替)
		

	# 修改主题
		bin/kafka-topics.sh --zookeeper localhost:2181 --alter --topic [topic-name] --partitions 3
		bin/kafka-topics.sh --zookeeper localhost:2181 --alter --topic [topic-name] --config max.message.bytes=20000
		bin/kafka-topics.sh --zookeeper localhost:2181 --alter --topic [topic-name] --delete-config max.message.bytes

		--zookeeper
			* 指定zk
		--alter
			* 修改主题的指令
		--topic
			* 指定要修改的主题名称
		--if-exists
			* 如果执行修改的主题不存在,那么不会抛出异常
		
		--partitions
			* 修改的参数之一(该参数修改partition数量)
			* 可以有多个参数
		
		--config
			* 修改的主题配置信息
			* 通过该参数指定一个或者多个配置
		
		--delete-config
			* 删除之前覆盖的配置,使其恢复原有的默认值
			* 通过该参数指定要取消覆盖的配置名称
		
		* 目前Kafka只支持增加分区数而不支持减少分区数
		* 而且分区数的修改,可能会导致那些有key的消息,投递的分区号被重置(hash不变,但是partition数量改变了)
		
		* alter 指令其实已经过时了,而且以后会删除,建议使用:kafka-configs.sh 脚本来实现相关功能
	
	# 删除主题
		bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic [主题名称]

		* 主题被删除后,不会立即的从磁盘删除,只是被标记为删除
		* 如果broker配置的参数: delete.topic.enable=false 那么该命名不会有任何效果
		* 如果要删除的主题是 Kafka 的内部主题,那么删除时就会报错

		* 使用 kafka-topics.sh 脚本删除主题的行为本质上只是在 ZooKeeper 中的／admin/delete_topics 路径下创建一个与待删除主题同名的节点
		* 以此标记该主题为待删除的状
		* 与创建主题相同的是,真正删除主题的动作也是由 Kafka 的控制器负责完成的
		* 说白了,可以通过操作zookeeper来完成主题的删除操作