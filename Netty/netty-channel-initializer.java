----------------------------
ChannelInitializer			|
----------------------------
	# ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter
	# 抽象类,它是一个特殊的 ChannelInboundHandler
	# 提供的抽象方法

		protected abstract void initChannel(C ch) throws Exception;
	
		* 在连接建立的时候,通过调用这个抽象方法来设置各种handler(i/o)
		* 在完成了初始化后(initChannel 方法),它会把自己从 pipeline 里面移除

	# 它被标记为:@Sharable,可以被多个 ServerBoostrap 使用
		* 所以,它的子类实现,必须要注意线程安全的问题
	