--------------------------------
ChannelHandlerContext			|
--------------------------------
	# interface ChannelHandlerContext extends AttributeMap, ChannelInboundInvoker, ChannelOutboundInvoker
	# 表示客户端的连接

--------------------------------
接口方法						|
--------------------------------

    ChannelFuture write(Object msg);
		* 写入数据到通道,但是会被缓存到缓冲区

    ChannelFuture write(Object msg, ChannelPromise promise);

    ChannelFuture writeAndFlush(Object msg);
		* 写入并且刷出缓冲区
	
    ChannelFuture writeAndFlush(Object msg, ChannelPromise promise);
		* 返回 futrue

	ChannelOutboundInvoker flush();
		* 把缓冲区中的数据刷出
	
	ByteBufAllocator alloc();
		* 得到一个当前的ByteBufAllocator,从而构建一个Buffer
			ByteBuf intBuf = ctx.alloc().buffer(4);

	ChannelHandlerContext fireChannelRead(Object msg)
		* 触发下一个 ChannelInboundHandler 的 channelRead() 方法,并且给定msg参数

	ChannelHandlerContext fireUserEventTriggered(Object evt);
		* 主动触发用户自定义的事件
	
	String name();
		* 获取的是当前 handler 的name(添加到 pipeline 时设置的name属性)

    ChannelHandler handler();
		* 返回的就是当前的handler实例对象

	ChannelProgressivePromise newProgressivePromise();
	ChannelFuture newFailedFuture(Throwable cause);
	ChannelFuture newSucceededFuture();
	ChannelPromise newPromise();
	ChannelPromise voidPromise();