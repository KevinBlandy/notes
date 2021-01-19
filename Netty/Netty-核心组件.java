------------------------
Netty核心组件			|
------------------------
	# 各个核心组件

------------------------
Bootstrap				|
------------------------
	# 一个Netty程序都是由 Bootstrap开始,它主要的作用是'配置整个Netty程序,串联起各个组件'

	Bootstrap	
		# client
	ServerBootstrap
		# server

------------------------
Channel					|
------------------------
	# 代表了一个Socket连接,或者其他IO操作相关的组件
	# 和EventLoop一起用来参与IO处理

------------------------
Future					|
------------------------
	# 在Netty中所有的IO操作都是异步的，因此，你不能立刻得知消息是否被正确处理
	# 我们可以过一会等它执行完成或者直接注册一个监听
	# 具体的实现就是通过 Future 和 ChannelFutures,他们可以注册一个监听,当操作执行成功或失败时监听会自动触发。
	# 总之，所有的操作都会返回一个ChannelFuture。

------------------------
handler					|
------------------------
	# 为了支持各种协议和处理数据的方式，便诞生了Handler组件。Handler主要用来处理各种事件
		* 连接
		* 数据接收
		* 异常
		* 数据转换等...

	# ChannelHandler
		* 最顶层的接口
		
		ChannelInitializer<C extends Channel>
			# 抽象类,用于管理各种Handler
			# 当一个链接建立时，我们需要知道怎么来接收或者发送数据，当然，我们有各种各样的Handler实现来处理它。
			# ChannelInitializer 便是用来配置这些Handler。
			# 它会提供一个 ChannelPipeline,并把 Handler 加入到 ChannelPipeline
				# 唯一抽象方法
				protected abstract void initChannel(C ch) throws Exception;
				
		ChannelInboundHandler
			# 一个最常用的Handler接口
			# 这个Handler的作用就是处理接收到数据时的事件，也就是说，我们的业务逻辑一般就是写在这个Handler里面
			# ChannelInboundHandler 就是用来处理我们的核心业务逻辑。
			# 一些实现类
				ChannelInboundHandlerAdapter
					# 实现类

		ChannelOutboundHandler
			#　一个最常用的Handler接口
			# 这个Handler的作用就是处理响应客户端数据时的事件
			# 一些实现类
				ChannelOutboundHandlerAdapter
					# 实现类

------------------------
ChannelPipeline			|
------------------------
	# 一个Netty应用基于ChannelPipeline机制，这种机制需要依赖于 EventLoop 和 EventLoopGroup,因为它们三个都和事件或者事件处理相关。
	# ChannelPipeline负责安排Handler的顺序及其执行
	# 该对象获取方式
		SocketChannel socketChannel =  ...;
		ChannelPipeline channelPipeline = socketChannel.pipeline();

	# 方法
		ChannelPipeline addLast(ChannelHandler... handlers);
			* 

------------------------
EventLoops				|
------------------------
	# 目的是为Channel处理IO操作,一个EventLoop可以为多个Channel服务
	# EventLoopGroup 会包含多个 EventLoop
	# 构建方式
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();