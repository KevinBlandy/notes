--------------------------
Kafka核心概念			  |
--------------------------
	# Broker 
		* 一台Kafka服务器,负责消息的读,写,存
		* 一个集群由多个Broker组成,一个Broker可以容纳多个Topic
		* Broker与Broker之间不存在M/S(主从)的关系

	# Topic
		* 同一个Topic的消息可以分布在一个或者多个节点上
		* 一个Topic包含一个或者多个 Partition
		* 每条消息都属于且仅属于一个Topic
		* Producer发布消息必须要指定发布到哪一个Topic
		* Consumer消费消息,也必须要指定订阅哪个Topic的消息
		* 可以把它理解为一个 Queue
		* topic被删除后,默认还会存储一个礼拜,在此期间还可以进行消费

	# Partition
		* 一个Topic分为多个Partition(创建的时候指定)
		* 一个Partition只分布在一个Broker上
		* 一个Partition物理上对应一个文件夹
		* 一个Partition包含多个Segment,一个Segment对应一个文件
		* Segment是由一个个不可变记录组成
		* 记录只会被append到Segement中,不会被单独的删除或者修改
		* 清除过期日志,直接删除一个或者多个Segment
		* 内部消息强有序,且都有一个递增的序号

	
	# Producer 
		* 消息生产者,负责往broker推送消息
		* 它可以决定往哪个Partition写消息,可以是轮询,或者hash
		* 推送消息的时候如果没有设置key,那么随机选择一个Partition
		* 如果推送消息设置了key,那么会根据hash选择一个Partition
	
	# Consumer
		* 消息消费者,负责从broker拉取消息消费
		* 一个Consumer一次只能从一个Partition中消费数据,并且维护它的offset(使用zookeeper)
		* 每个Consumer都必须属于一个组,如果不指定,那么系统默认生成一个组
	
	# Consumer Group(CG)
		* 消费者群组,一个群组由一个或者多个Consumer组成
		* 这是kafka实现广播和单播的一个手段
		* 一个Topic可以有多个CG,topic的消息会复制(不是真的复制,是概念上的)到所有的CG
		* 但每个CG只会把消息发给该CG中的一个Consumer
		* 实现广播
			* 所有的消费者都独立的成为一个CG
		
		* 实现单播
			* 所有的消费者都在一个CG
		
		* 实质上一个CG,才是一个消费者,可以把这个'消费者'理解为一个集群,集群中的每个Consumer都是一个节点

		* 一个CG中会包含多个Consumer,这样不仅可以提高Topic中消息的并发消费能力,而且还能提高"故障容错"性
		* 如果CG中的某个Consumer失效,那么其消费的Partitions将会有其他Consumer自动接管
		* Kafka的设计原理决定,对于一个Topic,同一个Group中不能有多于Partitions个数的Consumer同时消费
		* 否则将意味着某些Consumer将无法得到消息(处于闲置状态)

		* 一个partition,只能被消费组里的一个消费者消费

	# Partition
		* 为了实现扩展性,一个非常大的Topic可以分布到多个Broker(即服务器)上
		* 一个Topic可以分为多个Partition,每个Partition是一个有序的队列
		* Partition中的每条消息都会被分配一个有序的id(offset)
		* kafka只保证按一个Partition中的顺序将消息发给Consumer,不保证一个Topic的整体(多个Partition间)的顺序
	
	# Offset
		* kafka的存储文件都是按照offset.kafka来命名,用offset做名字的好处是方便查找
		* 例如想找位于2049的位置,只要找到2048.kafka的文件即可
		* 当然 the first offset就是00000000000.kafka
	
	# 数据过期机制
		* Kakfa在消息被消费完毕之后不会删除
		* 它根据时间策略来删除,默认是存储一周

--------------------------
Producer负载均衡和HA机制  |
--------------------------
	* producer根据用户指定的算法,将消息发送到指定的partition
	* 存在多个partiiton,每个partition有自己的replica,每个replica分布在不同的Broker节点上
	* 多个partition需要选取出lead partition,lead partition负责读写,并由zookeeper负责fail over(故障切换)
	* 通过zookeeper管理broker与consumer的动态加入与离开。

--------------------------
Consumer的pull机制		  |
--------------------------
	* 由于kafka broker会持久化数据,broker没有cahce压力,因此,consumer比较适合采取pull的方式消费数据
	* 简化kafka设计,降低了难度
	* Consumer根据消费能力自主控制消息拉取速度
	* consumer根据自身情况自主选择消费模式,例如批量,重复消费,从制定partition或位置(offset)开始消费等

--------------------------
kafak系统扩展性			  |
--------------------------
	* kafka使用zookeeper来实现动态的集群扩展,不需要更改客户端(producer和consumer)的配置
	* broker会在zookeeper注册并保持相关的元数据(topic,partition信息等)更新
	* 而客户端会在zookeeper上注册相关的watcher,一旦zookeeper发生变化,客户端能及时感知并作出相应调整
	* 这样就保证了添加或去除broker时,各broker间仍能自动实现负载均衡

--------------------------
partition 的分配		  |
--------------------------
	* 将所有Broker(假设共n个Broker)和待分配的Partition排序
	* 将第i个Partition分配到第(i % n)个Broker上,这个就是leader
	* 将第i个Partition的第j个Replica分配到第((i + j) % n)个Broker上


--------------------------
Kafka-Replica			  |
--------------------------
	# broker之间replica机制
		* replication策略是基于partition,而不是topic
		* kafka将每个partition数据复制到多个server上,任何一个partition有一个leader和多个follower(可以没有)
		* 备份的个数可以通过broker配置文件来设定.
		* eader处理所有的read-write请求,follower需要和leader保持同步
		* Follower就像一个"consumer"消费消息并保存在本地日志中
		* leader负责跟踪所有的follower状态,如果follower"落后"太多或者失效,leader将会把它从replicas同步列表中删除

		* 当所有的follower都将一条消息保存成功,此消息才被认为是"committed",那么此时consumer才能消费它,
		* 这种同步策略,就要求follower和leader之间必须具有良好的网络环境(同步刷盘)

		* 即使只有一个replicas实例存活,仍然可以保证消息的正常发送和接收,只要zookeeper集群存活即可.(不同于其他分布式存储,比如hbase需要"多数派"存活才行)
		
	# 判定一个follower存活与否的条件有2个
		* follower需要和zookeeper保持良好的链接
		* 它必须能够及时的跟进leader,不能落后太多

		* 如果同时满足上述2个条件,那么leader就认为此follower是"活跃的"
		* 如果一个follower失效(server失效)或者落后太多,
		* leader将会把它从同步列表中移除[备注:如果此replicas落后太多,它将会继续从leader中fetch数据,直到足够"up-to-date",然后再次加入到同步列表中]
		* kafka不会更换replicas宿主,因为"同步列表"中replicas需要足够快,这样才能保证producer发布消息时接受到ACK的延迟较小
	
	# 当leader失效时,需在followers中选取出新的leader
		* 可能此时follower落后于leader,因此需要选择一个"up-to-date"的follower
		* kafka中leader选举并没有采用"投票多数派"的算法
		* 因为这种算法对于"网络稳定性"/"投票参与者数量"等条件有较高的要求,而且kafka集群的设计,还需要容忍N-1个replicas失效
		* 对于kafka而言,每个partition中所有的replicas信息都可以在zookeeper中获得,那么选举leader将是一件非常简单的事情

		* 选择follower时需要兼顾一个问题,就是新leader server上所已经承载的partition leader的个数
		* 如果一个server上有过多的partition leader,意味着此server将承受着更多的IO压力
		* 在选举新leader,需要考虑到"负载均衡",partition leader较少的broker将会更有可能成为新的leader
		
		* 在整几个集群中,只要有一个replicas存活,那么此partition都可以继续接受读写操作

--------------------------
MQ的消息模型			  |
--------------------------
	# 点对点
		* 一条消息,只会被订阅了该Topic的消费者的其中一个消费者消费
		* 点对点
	
	# 发布订阅
		* 一条消息,只要是订阅了该Topic的消费者都会消费
		* 广播

