-----------------------------
Channel 的总结				 |
-----------------------------
	# 处理数据用ChannelInboundHandler
	# 响应数据用ChannelOutboundHandler

-----------------------------
事件						 |
-----------------------------
	# 事件机制
		* ChannelInboundHandler 可以覆写N多的事件方法
		* ChannelOutboundHandler 只有基本的 handler 事件,但也是空实现
		* ChannelHandlerContext 可以主动的调用 fireXxxxx(),触发下一个(右边) ChannelInboundHandler 的事件
		* ChannelPipeline 也可以主动的调用 fireXxxxx(),触发事件,但是它是从第一个 ChannelInboundHandler 开始触发
		
	# 使用 ChannelPipeline 执行响应或者触发事件,都是从头开始
		* 执行它的 write(),会从ChannelPipeline 的右边开始处理消息,也是从头开始进入整个输出执行链
		* 执行它的 fireXxxxx(),都是最先触发左边的 ChannelInboundHandler 事件

-----------------------------
异常						 |
-----------------------------

-----------------------------
数据响应					 |
-----------------------------
		
	# 几种响应数据的方法和区别
		* 调用原始 Channel 的write() api,也会从ChannelPipeline 的最右边开始处理消息,也是从头开始进入整个输出执行链
		* 调用 ChannelHandlerContext 的 write() 方法,会从下一个(左边最近的一个) ChannelOutboundHandler 开始执行
		* ChannelPipeline 也可以使用 write() api,跟第一种情况一样