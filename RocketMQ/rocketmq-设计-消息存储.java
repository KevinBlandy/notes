-------------------------
消息存储架构			 |
-------------------------
	# 存储目录
		$HOME/store
			|-abort
			|-checkpoint
			|-commitlog
				|-00000000000000000000
			|-config
			|-consumequeue
				|-RMQ_SYS_TRANS_HALF_TOPIC
				|-RMQ_SYS_TRANS_OP_HALF_TOPIC
				|-SCHEDULE_TOPIC_XXXX
			|-index
			|-lock
	
	# commitlog
		* 该目录下存储的就是Producer写入的消息数据以及元数据(就是存储所有数据)的日志文件
		* 单个文件大小默认为 1GB, 文件长度 20 个字符,表示起始的字节偏移量, 左填充0 
			00000000000000000000	 // 表示第一个文件，起始为 0 
			00000000001073741824	 // 表示第二个文件，起始为 1073741824 (1073741824 byte = 1GB)
		
		* 消息顺序的写入到日志文件，写满了后，写入下一个文件

		* Broker单个实例下所有的Topic共用一个日志数据文件（即为CommitLog）来存储
	
	# consumequeue
		* 消息消费队列，引入的目的主要是提高消息消费的性能
		* RocketMQ是基于主题topic的订阅模式，消息消费是针对主题进行的，如果要遍历commitlog文件中根据topic检索消息是非常低效的

		* Consumer可根据ConsumeQueue来查找待消费的消息
		* ConsumeQueue（逻辑消费队列）作为消费消息的索引，保存了指定Topic下的队列消息在CommitLog中的起始物理偏移量offset，消息大小size和消息Tag的HashCode值
		* onsumequeue文件可以看成是基于topic的commitlog索引文件，组织方式如下
			$HOME/store/consumequeue/{topic}/{queueId}/{fileName}

		* consumequeue文件采取定长设计，里面存储一个个的条目
		* 每一个条目共20个字节，分别为8字节的commitlog物理偏移量、4字节的消息长度、8字节tag hashcode
		* 单个文件由30W个条目组成，可以像数组一样随机访问每一个条目，每个ConsumeQueue文件大小约5.72M


	# index
		* IndexFile（索引文件）提供了一种可以通过key或时间区间来查询消息的方法
		* 它存在于
			$HOME/store/index/${indexFile}

		* 文件名 ${indexFile} 是以创建时的时间戳命名的。例如:20190718193445235
		* 固定的单个IndexFile文件大小约为400M，一个IndexFile可以保存 2000W个索引

		* IndexFile的底层存储设计为在文件系统中实现HashMap结构，故rocketmq的索引文件其底层实现为hash索引
	







