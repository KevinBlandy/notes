----------------------------
心跳机制					|
----------------------------
	# IdleStateHandler
		* 它继承自:ChannelDuplexHandler
		* 可以拦截读和写

	# 构造函数
		public IdleStateHandler(boolean observeOutput, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit)
		public IdleStateHandler(int readerIdleTimeSeconds,int writerIdleTimeSeconds,int allIdleTimeSeconds)
		public IdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime,TimeUnit unit)
		
		observeOutput
			* 是否考虑出站时较慢的情况,默认值是 false(不考虑)
			* 这个字段就是用来对付 "客户端接收数据奇慢无比,慢到比空闲时间还多"的极端情况,所以，Netty 默认是关闭这个字段的

		readerIdleTime
			* 读空闲超时时间设定,如果channelRead()方法超过readerIdleTime时间未被调用则会触发超时事件调用userEventTrigger()方法
			* 0 则禁用事件

		writerIdleTime
			* 写空闲超时时间设定,如果write()方法超过writerIdleTime时间未被调用则会触发超时事件调用userEventTrigger()方法
			* 0 则禁用事件

		allIdleTime
			* 读或写空闲时间设定
			* 0 则禁用事件

		unit
			* 时间单位
	
		
	# 触发机制
		* 当超时发生的时候,该handler就会触发一个用户自定义的事件,会给定一个对象:IdleStateEvent
		* 可以通过该对象判断是什么超时事件发生了
		* 该对象是一个枚举对象(非枚举类)
			IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT
				* 第一次发生了读超时, 就是很久没读取到客户的信息了

			IdleStateEvent.READER_IDLE_STATE_EVENT
				* 不是第一次发生了读超时,如果出现该状态,就说明channle目前处于连续读超时(起码2次)
				
			IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT
				* 第一次发生了写超时, 就是很久没往客户端写数据了

			IdleStateEvent.WRITER_IDLE_STATE_EVENT
			IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT
				* 第一次发生, 读写都超时了
			
			IdleStateEvent.ALL_IDLE_STATE_EVENT
	
	# 总结
		* IdleStateHandler心跳检测主要是通过向线程任务队列中添加定时任务,判断channelRead()方法或write()方法是否调用空闲超时
		* 如果超时则触发超时事件执行自定义userEventTrigger()方法

		* Netty通过IdleStateHandler实现最常见的心跳机制不是一种双向心跳的PING-PONG模式,而是客户端发送心跳数据包,服务端接收心跳但不回复
		* 因为如果服务端同时有上千个连接,心跳的回复需要消耗大量网络资源
		* 如果服务端一段时间内内有收到客户端的心跳数据包则认为客户端已经下线,将通道关闭避免资源的浪费
		* 在这种心跳模式下服务端可以感知客户端的存活情况,无论是宕机的正常下线还是网络问题的非正常下线,服务端都能感知到,而客户端不能感知到服务端的非正常下线

		* 要想实现客户端感知服务端的存活情况,需要进行双向的心跳
		* Netty中的channelInactive()方法是通过Socket连接关闭时挥手数据包触发的,因此可以通过channelInactive()方法感知正常的下线情况,但是因为网络异常等非正常下线则无法感知

	
	# 还有两个控制超时的组件
		ReadTimeoutHandler	
			在指定时间内没有接收到任何数据将抛出	ReadTimeoutException

		WriteTimeoutHandler
			在指定时间内有写入数据将抛出 WriteTimeoutException

----------------------------
心跳的建议					|
----------------------------
	# 客户端设置写超时,例如: 5s 内没有write事件,就会尝试往服务端发送一个心跳包
	# 通过这个心跳包的成功发送来检测服务端是否还存活

	# 服务端不用响应这个心跳包,如果连接很多(数十万个),那么响应心跳将是一个非常繁重的工作

	# 服务端可以设置读超时,例如: 5s 内没收到客户端的心跳,表示客户端可能宕机,就可以close()连接


----------------------------
客户端断线重连				|
----------------------------
	TODO
