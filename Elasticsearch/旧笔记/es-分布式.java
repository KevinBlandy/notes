----------------------------------------
ES分布式								|
----------------------------------------
	# 对复杂分布式机制的透明透明隐藏特性
		* 分片,cluster,discovery,shard负载均衡,shard副本,请求路由,集群扩容,shard重分配
	# 垂直扩容与水平扩容
		* 垂直: 加大已有服务器硬盘,配置
		* 水平: 新买服务器,加入节点
		* 一般都是用:水平,垂直会带来瓶颈问题
	# 增减节点时,数据的 rebalance
	# master节点
		* 集群中,总有一个节点是:master 节点,由集群自动选择
		* 它主要是管理ES集群的元数据(索引的创建,删除,索引维护,节点的增加,删除,维护节点的元数据)
		
	# 节点对等的分布式架构
		* 节点对等,每个节点都能接收所有的请求
		* 自动请求路由
		* 响应采集




----------------------------------------
shard & replica机制						|
----------------------------------------
	1,index包含一个或者多个shard
	2,每个shard都是一个最小工作单元
		* 承载部分数据,lucene实例,完整的建立索引和处理请求的能力
	3,增减节点,shard会自动在nodes中负载均衡
	4,primary shard和replica shard,每个document肯定只存在于某一个primary shard以及其对应的replica shard中,不可能存于多个primary shard
	5,replica shard是primary shard的副本,负责容错,以及承担读请求的负载
	6,primary shard的数量,在创建索引的时候就固定了,replica shard的数量可以随时修改
	7,primary shard的默认数量是5,replica默认是1,默认有10个shard,5个primary shard,5个 replica shard
	8,primary shard 不能和自己的replica shard放在同一个节点上(否则节点宕机,primary shard和副本都丢失,起不到容错的作用),但是可以和其他的primary shard和replica shard放在同一个节点上
	


----------------------------------------
单Node环境下的index						|
----------------------------------------
	1,单Node环境下,创建一个Index,有3个Primary shard,3个replica shard
	2,集群的status 是 yellow
	3,只会把三个primary shard分配到仅有的一个node上去,另外3个replica shard无法分配
	4,集群可以正常工作,但是一旦出现节点宕机,数据全部丢失,而且集群不可用,无法执行任何请求

----------------------------------------
2个Node环境下的index					|
----------------------------------------
	1,2个Node环境下,三个primary shard 会部署到一个节点,三个 replica shard会部署到一个节点

----------------------------------------
横向扩容								|
----------------------------------------
	1,primary & replica自动负载均衡
	2,每个node有更少的shard, io/cpu/memory资源给每个shard分配更多,每个shard性能更好

	
----------------------------------------
容错机制								|
----------------------------------------
	1,master选举
	2,replica容错
	3,数据恢复

	master node宕机,自动选举master -> red
	replica 容错:新master 将replica提升为 primary shard -> yellow
	重启宕机node,master copy replica到该node,使用原有的shard并同步宕机后的修改 green

