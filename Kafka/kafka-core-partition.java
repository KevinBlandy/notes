----------------------
Partition			  |
----------------------
	# controller负责执行partition的分配,还有leader的选举

	# 存在多副本的情况下,会尽量把多个副本,分配到不同的broker上
	
	# kafka会为partition选出一个leader,之后所有该partition的请求,实际操作的都是leader,然后再同步到其他的follower
		
	# 如果broker宕机,所有leader在该broker上的partition都会重新选举,选出一个leader
		* 不像分布式文件存储系统那样会自动进行复制保持副本数
	
	# Controller怎么分配partition
		* 将所有Broker(假设共n个Broker)和待分配的Partition排序
		* 将第i个Partition分配到第(i mod n)个Broker上(这个就是leader)
		* 将第i个Partition的第j个Replica分配到第((i + j) mode n)个Broker上

	# Controller怎么选leader
		* controller会在Zookeeper的'/brokers/ids'节点上注册Watch,一旦有broker宕机,它就能知道
		* roker宕机后,controller就会给受到影响的partition选出新leader
		* controller从zk的 '/brokers/topics/[topic]/partitions/[partition]/state'中,读取对应partition的ISR(in-sync replica已同步的副本)列表
		* 从ISR列表中选一个出来做leader
		* 选出leader后,更新zk,然后发送'LeaderAndISRRequest'给受影响的broker,让它们改变知道这事
			* 不是使用zk通知,而是直接给broker发送rpc请求(也许是因为性能问题 )
		* 如果 ISR列表是空,那么会根据配置,随便选一个replica做leader,或者干脆这个partition就是宕机(不救了)
		* 如果ISR列表的有机器,但是也宕机了,那么还可以等ISR的机器活过来
		* 原来宕机的leader恢复后,controller会重新把它设置为leader?????
			
	# 多个副本的同步
		* 服务端处理是follower从leader批量拉取数据来同步
		*  但是具体的可靠性,是由生产者来决定的
		* 生产者生产消息的时候,通过'request.required.acks'参数来设置数据的可靠性
			0
				* 发过去就完事了,不关心broker是否处理成功,可能丢数据
			1
				* 写Leader成功后就返回
				* 其他的replica都是通过fetcher去同步的,所以kafka是异步写,主备切换可能丢数据
			-1
				* 要等到isr里所有机器同步成功,才能返回成功
				* 延时取决于最慢的机器,强一致,不会丢数据
				* 如果ISR少于'min.insync.replicas'指定的数目,那么就会返回不可用
		
		* ISR列表中的机器是会变化的,根据配置'replica.lag.time.max.ms',多久没同步就会从ISR列表中剔除

		* 从ISA中选出leader后,follower会从把自己日志中上一个高水位后面的记录去掉,然后去和leader拿新的数据
			* 新的leader选出来后,follower上面的数据,可能比新leader多,所以要截取
			* 高水位的意思,对于partition和leader,'就是所有ISR中都有的最新一条记录',消费者最多只能读到高水位
		
		* 从leader的角度来说高水位的更新会延迟一轮
			* 例如写入了一条新消息,ISR中的broker都fetch到了,但是ISR中的broker只有在下一轮的fetch中才能告诉leader
			* 正是由于这个高水位延迟一轮,在一些情况下,kafka会出现丢数据和主备数据不一致的情况
			* '0.11开始,使用leader epoch来代替高水位'

		
		
		
