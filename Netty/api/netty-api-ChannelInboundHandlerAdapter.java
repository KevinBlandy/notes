--------------------------------
ChannelInboundHandlerAdapter	|
--------------------------------
	# class ChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler
	# 服务端实现的读取事件处理类

--------------------------------
方法							|
--------------------------------
	public void channelActive(ChannelHandlerContext ctx)
		* 连接被建立并且准备进行通信时被调用
	
	public void channelRead(ChannelHandlerContext ctx, Object msg)
		* 读取事件

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
		* 异常时调用
	
	public void handlerAdded(ChannelHandlerContext ctx) 
		* handler被添加时调用
	
	public void handlerRemoved(ChannelHandlerContext ctx) 
		* handler被移除时调用
	
	void channelInactive(ChannelHandlerContext ctx) 
		* 非活跃状态时调用

	void channelReadComplete(ChannelHandlerContext ctx)
		* 在读取完成后调用
	
	void handlerAdded(ChannelHandlerContext ctx)
		* 在添加到 ChannelPipeline 调用
	
	void handlerRemoved(ChannelHandlerContext ctx)
		* 从 ChannelPipeline 移除时调用
	
	void userEventTriggered(ChannelHandlerContext ctx, Object evt)
		* 主动触发用户自定义的事时,调用
	
	void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception;
		* 在channel变为可写状态的时候触发
		* 可以使用 ctx.channel().isWritable(); 来判断