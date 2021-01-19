------------------------------------
ChannelHandlerContext				|
------------------------------------
	# 当前连接的上下文
		* 它可以主动的把消息往下传递,触发下一个handler的事件

	# 两种响应方式
		* 从 ChannelHandlerContext 获取原始 Channel 执行 write
			处理消息从 ChannelPipeline 的尾部(最右边)开始,也是从头开始进入整个输出执行链
		
		* 直接调执行 ChannelHandlerContext 的 wirte
			写入 ChannelHandlerContext 对象的消息会从 ChannelPipeline 的下一个(左边)ChannelOutboundHandler开始处理,而不是从头开始

		
	
	

		