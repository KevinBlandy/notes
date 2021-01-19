----------------------------
流量整形					|
----------------------------
	# 类库
		TrafficCounter
			* 统计的数据
			|-GlobalChannelTrafficCounter

		AbstractTrafficShapingHandler
			* 流量整形handler
			|-ChannelTrafficShapingHandler
			|-GlobalChannelTrafficShapingHandler
			|-GlobalTrafficShapingHandler
	
	
	# 流量整形的原理
		输入 -> 流量洪峰 -> 队列(令牌桶) -> 输出 -> 平滑流量
	
	# AbstractTrafficShapingHandler
		* 所有handler的抽象父类

		AbstractTrafficShapingHandler()
		AbstractTrafficShapingHandler(long checkInterval)
		AbstractTrafficShapingHandler(long writeLimit, long readLimit)
		AbstractTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval)
		AbstractTrafficShapingHandler(long writeLimit, long readLimit)
		AbstractTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval, long maxTime)
		
		writeLimit
			* 限制没秒最多写入多少kb数据
			* 如果设置为0,表示不限制

		readLimit
			* 限制没秒最多读取多少kb数据
			* 如果设置为0,表示不限制

		checkInterval
			* 两次性能计算之间的延迟,默认1s
			* 如果设置为0,表示不统计
			
		maxTime
			* 当流量超过时等待的最大延迟,默认15s

		
----------------------------
ChannelTrafficShapingHandler|
----------------------------
	# 单个 Channel 级别的IO限制
		ChannelTrafficShapingHandler(long checkInterval)
		ChannelTrafficShapingHandler(long writeLimit,long readLimit)
		ChannelTrafficShapingHandler(long writeLimit,long readLimit, long checkInterval)
		ChannelTrafficShapingHandler(long writeLimit, long readLimit,long checkInterval, long maxTime)
	


-----------------------------------
GlobalChannelTrafficShapingHandler |
-----------------------------------
	# 针对于某个进程中的所有 Channel 的IO限制
		GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor)
		GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval)
		GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit)
		GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor,long writeGlobalLimit, long readGlobalLimit,long writeChannelLimit, long readChannelLimit, long checkInterval)
		GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor,long writeGlobalLimit, long readGlobalLimit,long writeChannelLimit, long readChannelLimit, long checkInterval, long maxTime)


-----------------------------------
GlobalTrafficShapingHandler			|
-----------------------------------
	# 全局级别的同时对全局,和单个 Channel 的IO限制(整合了上面俩)
		GlobalTrafficShapingHandler(EventExecutor executor)
		GlobalTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval)
		GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit,long readLimit)
		GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit,long readLimit, long checkInterval)
		GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit,long checkInterval, long maxTime)
