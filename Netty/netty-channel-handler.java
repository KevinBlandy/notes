-----------------------------
ChannelHandler				 |
-----------------------------
	# 提供基本的Handler事件
	# 类库
		ChannelHandler
			|-ChannelHandlerAdapter(抽象)
			|-ChannelInboundHandler(读接口)
			|-ChannelOutboundHandler(写接口)
				|-ChannelDuplexHandler(读写都实现)

	# Handler的生命周期(基本的事件)
		handlerAdded	ChannelHandler 添加到 ChannelPipeline
		handlerRemoved	ChannelHandler 从 ChannelPipeline 移除
		exceptionCaught	ChannelPipeline 执行抛出异常
	
	# 一些其他的类库
		SimpleUserEventChannelHandler
			* 类似于SimpleChannelInboundHandler
			* 该Handler用于处理指定类型(泛型), 的用户事件handler
			public class StringEventHandler extends SimpleUserEventChannelHandler<String> {
				@Override
				 protected void eventReceived(ChannelHandlerContext ctx, String evt) throws Exception {
					 System.out.println(evt);
				 }
			 }

-----------------------------
ChannelInboundHandler		 |
-----------------------------
	# 读取Handler
	# 类库
		|-ChannelInboundHandler
			|-ChannelInboundHandlerAdapter
				* 它实现了 ChannelInboundHandler 的所有方法
				* 默认的作用就是处理消息(事件)并将消息转发到 ChannelPipeline 中的下一个 ChannelHandler

				|-ByteToMessageDecoder
					|-ReplayingDecoder<S> 
					|-LineBasedFrameDecoder
					|-LengthFieldBasedFrameDecoder
					|-DelimiterBasedFrameDecoder
					|-FixedLengthFrameDecoder
					|-SslHandler
				|-MessageToMessageDecoder
					|-StringDecoder
				|-ChannelInitializer
				|-SimpleChannelInboundHandler<I>

	# 事件(接口方法)
		channelRegistered	channel	注册到一个 EventLoop,该状态可以出现多次,可以重复的注册取消注册
		channelActive		channel	变为活跃状态(连接到了远程主机),现在可以接收和发送数据了,该状态只会出现一次
		channelInactive		channel	处于非活跃状态(连接已经关闭),没有连接到远程主机,该状态只会出现一次
		channelUnregistered	channel	已创建但未注册到一个 EventLoop	(或者从EventLoop中移除),该状态可以出现多次,可以重复的注册取消注册

		channelReadComplete			channel 读取完成
		channelRead					channel 可以读取
		userEventTriggered			channel 用户自定义事件
		channelWritabilityChanged	channel 可写状态改变,可以使用 Channel.isWritable()检查
		exceptionCaught				channel 异常事件

	# SimpleChannelInboundHandler<T>
		* 抽象类,需要覆写抽象方法: channelRead0(ChannelHandlerContext ctx, T msg)
		* 自动强制转换类型,并且可以自动的释放buf资源
			 ReferenceCountUtil.release(msg);
	
	# 一般使用的和场景
		ChannelInboundHandlerAdapter	处理其事件或者状态改变
		SimpleChannelInboundHandler		处理消息

-----------------------------
SimpleChannelInboundHandler	 |
-----------------------------
	# 继承自:ChannelInboundHandlerAdapter 的泛型抽象类
	# 构造函数
		SimpleChannelInboundHandler() 
		SimpleChannelInboundHandler(boolean autoRelease)
		SimpleChannelInboundHandler(Class<? extends I> inboundMessageType)
		
		* autoRelease 是否自动释放资源,默认 true
		* inboundMessageType 该处理器会处理的消息类型(必须是泛型或者其子类)
			if (inboundMessageType.isInstance(msg)){
				// 处理
			}
	
	# 提供的可覆写的方法
		abstract void channelRead0(ChannelHandlerContext ctx, I msg)
			* 唯一的抽象类,必须覆写
	
		void channelRead(ChannelHandlerContext ctx, Object msg)
			* 处理读取事件
			* 源码
				@Override
				public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					boolean release = true;
					try {
						if (acceptInboundMessage(msg)) {
							@SuppressWarnings("unchecked")
							I imsg = (I) msg;
							channelRead0(ctx, imsg);
						} else {
							release = false;
							ctx.fireChannelRead(msg);
						}
					} finally {
						if (autoRelease && release) {
							ReferenceCountUtil.release(msg);	// 自动释放资源
						}
					}
				}
		
		boolean acceptInboundMessage(Object msg)
			* 判断当前Handler是否可以处理该消息对象
			* 执行 channelRead()的时候,会调用该方法,该方法默认调用了 TypeParameterMatcher 实例的 match(); 方法
			* 如果返回 true,就进行强制类型转换,并且触抽象方法 channelRead0 
			* 如果返回 false,就会触发下一个Handler的 channelRead 事件
			

	# TypeParameterMatcher
		* 类型匹配器
		* 在SimpleChannelInboundHandler内部维护的一个对象(抽象类)
		* 很多带泛型的编解码器(内部有维护)都是靠它来进行判断是否要进行解码/编码的
		* 提供了静态的工厂方法
			static TypeParameterMatcher find(final Object object, final Class<?> parametrizedSuperclass, final String typeParamName);
			static TypeParameterMatcher get(final Class<?> parameterType)
		
		*  唯一的抽象方法
			abstract boolean match(Object msg);
		
		

-----------------------------
ChannelOutboundHandler		 |
-----------------------------
	# 写入Handler
	# 类库
		|-ChannelOutboundHandler
			|-ChannelOutboundHandlerAdapter
				|-MessageToByteEncoder<I>
				|-MessageToMessageEncoder<I>
					|-LengthFieldPrepender
					|-StringEncoder
	
	# ChannelOutboundHandler 抽象方法
		void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception;
		void connect( ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception;
		void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception;
		void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception;
		void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception;
		void read(ChannelHandlerContext ctx) throws Exception;
		void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception;
		void flush(ChannelHandlerContext ctx) throws Exception;

-----------------------------
ChannelDuplexHandler		 |
-----------------------------
	# 读写Handler
	# 它继承 ChannelInboundHandlerAdapter 实现 ChannelOutboundHandler

-----------------------------
ChannelPromise 机制			 |
-----------------------------
	# ChannelPromise
		* 它继承自ChannelFuture
	
	# 运行
		* 特殊的 ChannelFuture,允许你的 ChannelPromise 及其 操作 成功或失败
		* 所以任何时候调用例如 Channel.write(...) 一个新的 ChannelPromise 将会创建并且通过 ChannelPipeline 传递
		* 这次写操作本身将会返回 ChannelFuture, 这样只允许你得到一次操作完成的通知
		* Netty 本身使用 ChannelPromise 作为返回的 ChannelFuture 的通知,事实上在大多数时候就是 ChannelPromise 自身
		* ChannelPromise 扩展了 ChannelFuture
		* 如前所述,ChannelOutboundHandlerAdapter 提供了一个实现了 ChannelOutboundHandler 所有基本方法的实现的框架。
		* 这些简单事件转发到下一个 ChannelOutboundHandler 管道通过调用 ChannelHandlerContext 相关的等效方法,可以根据需要自己实现想要的方法


-----------------------------
共享的Handler				 |
-----------------------------
	# ChannelHandler 实例如果带有 @Sharable 注解则可以被添加到多个ChannelPipeline
		* 也就是说单个 ChannelHandler 实例可以有多个 ChannelHandlerContext
		* 因此可以调用不同 ChannelHandlerContext 获取同一个 ChannelHandler 
		* 如果添加不带 @Sharable 注解的 ChannelHandler 实例到多个 ChannelPipeline 则会抛出异常
		* 使用 @Sharable 注解后的 ChannelHandler 必须在不同的线程和不同的通道上安全使用
	
	# 使用 @Sharable 注解共享一个 ChannelHandler 在一些需求中还是有很好的作用的
		* 如使用一个 ChannelHandler 来统计连接数或来处理一些全局数据等等

-----------------------------
事件总结					 |
-----------------------------
	void handlerAdded(ChannelHandlerContext ctx) throws Exception;
	void handlerRemoved(ChannelHandlerContext ctx) throws Exception;
		* 添加到pipeline

	void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
		* 异常

	void channelRegistered(ChannelHandlerContext ctx) throws Exception;
	void channelUnregistered(ChannelHandlerContext ctx) throws Exception;
		* 注册/取消注册到EventLoop

	void channelActive(ChannelHandlerContext ctx) throws Exception;
	void channelInactive(ChannelHandlerContext ctx) throws Exception;
		* 连接激活/关闭

	void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception;
		* 数据可读

	void channelReadComplete(ChannelHandlerContext ctx) throws Exception;
		* 数据读取完毕

	void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception;
	void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception;
		* 写队列状态发生了变化

-----------------------------
异常的传播与最佳实践		 |
-----------------------------
	# 异常的传播
		* 异常发生后,会先触发当前handler exceptionCaught() 方法
		* 如果当前节点,主动触发给下一个节点处理
		* 会根据 pipeline 的添加顺序(跟Handler类型无关)触发下一个 handler 的 exceptionCaught() 方法

	# 最佳的异常处理实践
		* 在 pipeline 的最后,添加一个终极的 ExceptionHandler 
		* 继承:ChannelInboundHandlerAdapter/ChannelHandlerAdapter,覆写 exceptionCaught() 方法来处理全局的异常
		* 根据不同的异常类型来做处理


-----------------------------
Handler统计			
-----------------------------
	ResolveAddressHandler 
	SslClientHelloHandler 

-----------------------------
Handler 写队列
-----------------------------
	# ChannelOutboundBuffer
		* Netty为了提高网络的吞吐量，在业务层与socket之间增加了一个ChannelOutboundBuffer
		* ChannelOutboundBuffer内部是一个链表结构
		* 在调用channel.write的时候，所有写出的数据其实并没有写到socket，而是先写到ChannelOutboundBuffer。
		* 当调用channel.flush的时候才真正的向socket写出。
		* 因为这中间有一个buffer，就存在速率匹配了，而且这个buffer还是无界的（链表），也就是你如果没有控制channel.write的速度，会有大量的数据在这个buffer里堆积
		* 如果又碰到socket写不出数据的时候（isActive此时判断无效）或者写得慢的情况。很有可能的结果就是资源耗尽。
		* 而且如果ChannelOutboundBuffer存放的是，DirectByteBuffer，这会让问题更加难排查。

	
	# 高低水位的设置
		serverBootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(1024 * 1024, 8 * 1024 * 1024));

		* 也可以通过conifg设置的方法设置
	
	# 解决 ChannelOutboundBuffer 溢出的步骤有2个
	
	# 1, 启用autoRead机制
		* 当channel不可写时，关闭autoRead
		* autoRead关闭后，不再处理客户端发送的消息，在recv buf满了后，客户端也没法发送数据

			public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
				if (!ctx.channel().isWritable()) {
					Channel channel = ctx.channel();
					ChannelInfo channelInfo = ChannelManager.CHANNEL_CHANNELINFO.get(channel);
					String clientId = "";
					if (channelInfo != null) {
						clientId = channelInfo.getClientId();
					}

					LOGGER.info("channel is unwritable, turn off autoread, clientId:{}", clientId);
					channel.config().setAutoRead(false);
				}
			}
		
		* 当数据可写时开启autoRead
			@Override
			public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception
			{
				Channel channel = ctx.channel();
				ChannelInfo channelInfo = ChannelManager.CHANNEL_CHANNELINFO.get(channel);
				String clientId = "";
				if (channelInfo != null) {
					clientId = channelInfo.getClientId();
				}
				if (channel.isWritable()) {
					LOGGER.info("channel is writable again, turn on autoread, clientId:{}", clientId);
					channel.config().setAutoRead(true);
				}
			}
	
	# 2, 写数据，增加channel.isWritable()的判断
		private void writeBackMessage(ChannelHandlerContext ctx, MqttMessage message) {
			Channel channel = ctx.channel();
			//增加channel.isWritable()的判断
			if (channel.isActive() && channel.isWritable()) {
				ChannelFuture cf = channel.writeAndFlush(message);
				if (cf.isDone() && cf.cause() != null) {
					LOGGER.error("channelWrite error!", cf.cause());
					ctx.close();
				}
			}
		}

		* isWritable可以来控制 ChannelOutboundBuffer ，不让其无限制膨胀。其机制就是利用设置好的channel高低水位来进行判断。