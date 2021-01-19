----------------------------
ChannelPipeline				|
---------------------------
	# 继承:ChannelInboundInvoker ChannelOutboundInvoker
	# 一个 ChannelPipeline 是用来保存关联到一个 Channel 的ChannelHandler
		* 可以修改 ChannelPipeline 通过动态添加和删除 ChannelHandler
		* ChannelPipeline 有着丰富的API调用动作来回应入站和出站事件
	
	# 内部维护了一个双向链表
		* InBound事件的传递: --->
		* OutBound事件的传递: <---
		* Exception事件的传递: --->

	# 它也可以给客户端响应数据
		ChannelPipeline pipeline = ctx.pipeline();
		pipeline.write(Unpooled.copiedBuffer("netty in action", CharsetUtil.UTF_8));

		* 该api处理消息从 ChannelPipeline 的尾部(右边)开始,也是从头开始进入整个输出执行链
	
	# 它也可以主动的触发事件
		ChannelPipeline pipeline = ctx.pipeline();
		pipeline.fireXxxxx();

		* 从头部(左边)开始执行触发 ChannelInboundHandler 的事件
		* 它继承了接口:
			ChannelInboundInvoker			定义了触发入站事件的fireXxxx 方法
			ChannelOutboundInvoker			定义了触发出站事件的fireXxxx 方法
	
	
	
	# 部分添加handler的api
		ChannelPipeline addFirst(String name, ChannelHandler handler);
			* 添加handler到链表,并且设置名称

		ChannelPipeline addLast(ChannelHandler... handlers);
			* 添加handler到链表
			* 名称默认: 类名#编号(从0开始)
				ServerMessageHandler#0
		
		ChannelPipeline addLast(EventExecutorGroup group, ChannelHandler... handlers);
			* 添加handler到链表,并且设置执行这些handler事件方法的线程池
			
