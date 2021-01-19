------------------------
日志存储				|
------------------------

------------------------
文件目录的布局			|
------------------------
	# 主题逻辑布局
		Topic
			|-Parition-1
				|-Log
					|-LogSgement
						|-.log			日志文件
						|-.index		偏移量索引文件
						|-.timeindex	时间戳索引文件
						|-.txnindex		事务索引文件
						|-.delete
						|-.cleaned
						|-.swap
						|-.snapshot
						|-leader-epoch-checkpoint
						|-other			其他文件
			|-Parition-x
	
		* log以文件夹的形式存在于磁盘上
		* 每个 LogSgement 对应磁盘上的一个日志文件(log)和两个索引文件(index,timeindex)
	
	# 主题物理布局
		Kafka-Log-Dir
			topic-log-0
				00000000000000000000.index
				00000000000000000000.log
				00000000000000000000.timeindex

				00000000000000001111.index
				00000000000000001111.log
				00000000000000001111.timeindex

				00000000000000002222.index
				00000000000000002222.log
				00000000000000002222.timeindex
			
		* 文件夹命名方式为:<主题名称>-<分区号>
			topic-log-0 ,表示 topic-log 主题的 第一个分区 0
		
		* 具有相同名称的,一个日志文件(log)和两个索引文件(index,timeindex)组成了一个LogSgement
		* 往LogSgement中写入数据,到达了一定量后,就会创建新LogSgement
		* 文件名称的数字表示当前LogSgement的基准偏移量(从哪里开始的),由20个数字长度组成,不足前面补充0
			00000000000000000000		第一个Segemnt全是0,表示当前Segement是从0开始写入数据的
			00000000000000000133		第二个Sgement以133结尾,表示当前Segment是从133开始写入数据的,也就是说上一个Segement中有 133 条消息(0 - 132)


	# 日志文件目录
		* broker的配置项 log.dirs 可以配置一个或者多个日志目录
		* 每个日志目录下都有几个文件
			log-start-offset-checkpoint
			recovery-point-offset-checkpoint
			replication-offset-checkpoint
			cleaner-offset-checkpoint
			meta.propertiesrecovery-point-offset-checkpoint

		* 当创建主题的时候,系统会选择分区最少的那个目录来创建
	
------------------------
日志索引				|
------------------------

------------------------
日志清理				|
------------------------
	# 为了控制消息对磁盘的空间占用,需要做清理操作
	# 两种日志清理策略
		* 日志删除,按照一定的策略直接删除不符合条件的日志分段

		* 日志压缩,针对每个消息的key进行整合,对于相同key不同value的消息,只保留最后一个版本
	
	# 设置日志清理策略,broker配置
		log.cleanup.policy

			delete(默认)
				*  使用日志删除策略
			compact
				*  使用压缩策略
		
		* 清理策略还可以配置多个,表示同时支持删除和压缩
		* 必须要开启配置:log.cleaner.enable=true
	
	# 日志的清理策略粗粒度可以控制到主题
		* 主题的配置选项
			cleanup.policy
		
	
	# 配置broker删除任务的检测周期
		log.retention.check.interval.ms=300000

		* 默认 5 分钟检测一次
	
	# 
	
		
	


		
	