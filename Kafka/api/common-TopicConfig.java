-----------------------
TopicConfig			   |
-----------------------
	# 主题的配置,在broker端都有默认的,如果主题的参数没有设置,那么就会使用broker默认的

	# 主题的配置
		segment.bytes
		segment.ms
		segment.jitter.ms
		segment.index.bytes
		flush.messages
			* 收集到了多少消息,才强制刷入磁盘
			* 默认值:Long.MAX_VALUE,也就是让操作系统决定

		flush.ms
			* 需要等待多久才会将消息强制刷新到磁盘
			* 默认值为 Long.MAX_VALUE ,即让操作系统来决定

		retention.bytes
		retention.ms
		max.message.bytes
		index.interval.bytes
		file.delete.delay.ms
			* 清理文件之前可以等待多少时间,默认为:60000ms 也就是 1分钟

		delete.retention.ms
			* 标识了删除的数据保留多久后物理删除
			* 默认86400000ms 也就是1 天

		min.compaction.lag.ms
		min.cleanable.dirty.ratio
		cleanup.policy
			* 日志清除策略
				delete(默认)
				compcat

		unclean.leader.election.enable
		min.insync.replicas
		compression.type
			* 如果设置 cleanup.policy=compcat
			* 那么可以通过该参数来设置,消息的压缩算法
				uncompressed
				snappy
				lz4
				gzip

		preallocate
		message.format.version
		message.timestamp.type
		message.timestamp.difference.max.ms
		message.downconversion.enable

