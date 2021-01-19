---------------------------
RocketMQ-JVM参数			|
---------------------------	
	# 文件
		./bin/runbroker.sh
		./bin/runserver.sh

	# nameserver 和 broker 的默认值有点大,4GB
		JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g -XX:PermSize=128m -XX:MaxPermSize=320m"
		JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g -XX:PermSize=128m -XX:MaxPermSize=320m"

	# 目前已测,最小能运行内存
		JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m -XX:PermSize=64m -XX:MaxPermSize=128m"

-----------------------------
RocketMQ- broker-x.properties|
-----------------------------
	listenPort 
		* Broker服务监听端口,默认值 10911 

	brokerClusterName=rocketmq-cluster
		* 所属集群的名称
	
	brokerIP1
		* 本地IP,本机IP地址由程序自动识别.但是某些网卡会存在识别错误的情况,此时可以人工配置
	
	brokerName=broker-a
		* broker名称,一个集群中每个borker的名称不能相同,如果是SLAVE,则该值应该和MASTER保持一直

	brokerId=0
		* 该值为0,则表示当前broker是一个master,如果是任意 >0 的数值则表示 Slave

	namesrvAddr=rocketmq-nameserver1:9876;rocketmq-nameserver2:9876
		* nameserver地址,如果有多个则使用分号分割

	defaultTopicQueueNums=4
		* 在发送消息时，自动创建服务器不存在的topic，默认创建的队列数

	autoCreateTopicEnable=true
		* 是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
		* 生存环境,建议通过命令来创建队列.如果是程序自动创建,负载会失效
		* Broker 如果自动创建队列的话,那么Broker收到一个新的队列,就会创建.而以后,该队列的所有消息都会通过这个Broker发送,消息负载失效

	autoCreateSubscriptionGroup=true
		* 是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
	
	rejectTransactionMessage=false
		* 是否拒绝事务消息
	
	fetchNamesrvAddrByAddressServer=false
		* 是否从WEB服务器获取NameServer地址,针对大规模的Broker集群建议使用这种方式

	deleteWhen=04
		* 删除文件时间点，默认凌晨 4点(24H)

	fileReservedTime=48
		* 文件保留时间，默认 48 小时,单位是(H)

	mapedFileSizeCommitLog=1073741824
		* commitLog每个文件的大小默认1G,超过之后,新建文件

	mapedFileSizeConsumeQueue=300000
		* ConsumeQueue每个文件默认存30W条，根据业务情况调整

	destroyMapedFileIntervalForcibly=120000

	redeleteHangedFileInterval=120000

	diskMaxUsedSpaceRatio=88
		* 检测物理文件磁盘空间

	/***	 路径配置开始		**/
	storePathRootDir
		* 存储路径
	
	storePathCommitLog
		* commitLog 存储路径
		* 默认地址 $HOME/store/commitlog

	storePathConsumeQueue
		* 消费队列存储路径存储路径
		* 默认地址 $HOME/store/consumequeue

	storePathIndex
		* 消息索引存储路径
		* 默认地址 $HOME/store/index

	storeCheckpoint
		* checkpoint 文件存储路径
		* 默认地址 $HOME/store/checkpoint

	abortFile
		* abort 文件存储路径
		* 默认地址 $HOME/store/abort
	
	/***	 路径配置结束		**/

	/***	 大小限制配置开始	**/
	maxTransferBytesOnMessageInMemory=262144
		* 单次 Pull 消息(内存)传输的最大字节数

	maxTransferCountOnMessageInMemory=32
		* 单次 Pull 消息(内存)传递的最大条目数
	
	maxTransferBytesOnMessageInDisk=65536
		* 单次 Pull 消息(磁盘)传输的最大字节数

	maxTransferCountOnMessageInDisk
		* 单次 Pull 消息(磁盘)传递的最大条目数
	
	maxMessageSize=65536
		* 限制的单个消息大小
	/***	 大小限制配置结束	**/

	messageIndexEnable=true
		*　是否开启消息索引功能

	messageIndexSafe=false
		* 是否提供安全的消息索引机制,索引保证不丢
	
	haMasterAddress
		* 在SLAVE上执行设置MASTER地址,默认从NameServer上自动获取,也可以手动的配置
	
	brokerRole=ASYNC_MASTER
		* Broker的角色
		* ASYNC_MASTER 异步复制Master
		* SYNC_MASTER 同步双写Master
	
	flushDiskType=ASYNC_FLUSH
		* 刷盘方式
		* ASYNC_FLUSH 异步刷盘
		* SYNC_FLUSH  同步刷盘
	
	cleanFileForciblyEnable=true
		* 磁盘满,且无过期文件清空下,true 表示强制删除文件,false,标记服务不可用,文件不会删除
	



	#flushCommitLogLeastPages=4
	#flushConsumeQueueLeastPages=2
	#flushCommitLogThoroughInterval=10000
	#flushConsumeQueueThoroughInterval=60000

	#
	
	brokerRole=ASYNC_MASTER
		* Broker 的角色
		* ASYNC_MASTER 异步复制Master
		* SYNC_MASTER 同步双写Master
		* SLAVE

	flushDiskType=ASYNC_FLUSH
		* 刷盘方式
		* ASYNC_FLUSH 异步刷盘
		* SYNC_FLUSH 同步刷盘

	checkTransactionMessageEnable=false
		
	sendMessageThreadPoolNums=128
		* 发消息线程池数量

	pullMessageThreadPoolNums=128
		* 拉消息线程池数量





















brokerClusterName=rocketmq-cluster
brokerName=broker-x
brokerId=0
namesrvAddr=rocketmq-nameserver1:9876;rocketmq-nameserver2:9876
defaultTopicQueueNums=4
autoCreateTopicEnable=true
autoCreateSubscriptionGroup=true
listenPort=10911
deleteWhen=04
fileReservedTime=120
mapedFileSizeCommitLog=1073741824
mapedFileSizeConsumeQueue=300000
diskMaxUsedSpaceRatio=88
storePathRootDir=/usr/local/rocketmq/store
storePathCommitLog=/usr/local/rocketmq/store/commitlog
storePathConsumeQueue=/usr/local/rocketmq/store/consumerqueue
storePathIndex=/usr/local/rocketmq/store/index
storeCheckpoint=/usr/local/rocketmq/store/checkpoint
abortFile=/usr/local/rocketmq/store/abort
maxMessageSize=65536
brokerRole=ASYNC_MASTER
flushDiskType=ASYNC_FLUSH

































