------------------------------
ChannelGroup				  |
------------------------------
	# 类库
		ChannelGroup
			|-DefaultChannelGroup
		ChannelGroupException
		ChannelGroupFuture
		ChannelGroupFutureListener
		ChannelMatcher
		ChannelMatchers
			ALL_MATCHER

	
	# ChannelGroup
		* 是一个接口,实现了 Set 接口
		* 表示一组 Channel
	
	# ChannelGroupFuture 
		* 表示一个执行结果,该结果可能是:写入,查找,关闭等等对于群组的一个操作
	
	# ChannelGroupFutureListener
		* 事件监听回调的接口,继承了GenericFutureListener,空继承而已,没新增或者覆写父类的任何抽象方法
		* 但是限定了回调结果的泛型:GenericFutureListener<ChannelGroupFuture>
			channelGroupFuture.addListener(new ChannelGroupFutureListener() {
				@Override
				public void operationComplete(ChannelGroupFuture future) throws Exception {
					// TODO
				}
			});

	# ChannelMatcher
		* 接口,对于Channel的描述,用于在一个群组中匹配出指定的Channel
		* 只有一个抽象方法
			boolean matches(Channel channel);
	
	# ChannelMatchers
		* 提供了一些创建/操作 ChannelMatcher 实例的静态方法
			ChannelMatcher all()
				* 匹配所有的Channel的 ChannelMatcher
			ChannelMatcher isNot(Channel channel)
			ChannelMatcher is(Channel channel)
			ChannelMatcher isInstanceOf(Class<? extends Channel> clazz)
			ChannelMatcher isNotInstanceOf(Class<? extends Channel> clazz)
			ChannelMatcher isServerChannel()
			ChannelMatcher isNonServerChannel()
			ChannelMatcher invert(ChannelMatcher matcher)
				* 反转给定的matcher
			ChannelMatcher compose(ChannelMatcher... matchers)

------------------------------
DefaultChannelGroup			  |
------------------------------
	# ChannelGroup 接口的唯一实现
	# 构造函数
		DefaultChannelGroup(EventExecutor executor)
		DefaultChannelGroup(EventExecutor executor, boolean stayClosed)
		DefaultChannelGroup(String name, EventExecutor executor)
		DefaultChannelGroup(String name, EventExecutor executor, boolean stayClosed)

		executor
			* 执行器
		name
			* 群组的名称
		stayClosed
			* 如果该值为 true, 那么在当前群组被关闭的时候, 新加入的 channel 会被执行 close
				if (stayClosed && closed) {
					channel.close();
				}
				return added;
					

	# 可以使用 GlobalEventExecutor.INSTANCE 来创建
		ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	# 实例函数(它还实现了Set接口 Set<Channel>)
		ChannelGroupFuture close();
		ChannelGroupFuture close(ChannelMatcher matcher);
			* 关闭群组,不再接收新的channel
			* 还会断开群组中的所有连接

		ChannelGroupFuture disconnect();
		ChannelGroupFuture disconnect(ChannelMatcher matcher);
			* 断开群组中所有channle的连接

		Channel find(ChannelId id);

		ChannelGroup flush();
		ChannelGroup flush(ChannelMatcher matcher);

		String name();

		ChannelGroupFuture newCloseFuture();
		ChannelGroupFuture newCloseFuture(ChannelMatcher matcher);
			* 创建一个新的关闭通知回调
			* 如果Group中有任何channel(或者符合条件的channle)触发了close()的时候,触发该回调

		ChannelGroupFuture write(Object message);
		ChannelGroupFuture write(Object message, ChannelMatcher matcher);
		ChannelGroupFuture write(Object message, ChannelMatcher matcher, boolean voidPromise);

		ChannelGroupFuture writeAndFlush(Object message);
		ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher);
		ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher, boolean voidPromise);
	
	# Channel 关闭,会自动的从 Group 种移除
		@Override
		public boolean add(Channel channel) {
			...
			boolean added = map.putIfAbsent(channel.id(), channel) == null;
			if (added) { // 如果添加成功,则设置channel的close监听，逻辑代码为把自己从group中移除
				channel.closeFuture().addListener(remover);
			}
			...
		}
	
	# 一个 Channel 可以属于多个 Group
	# 如果ServerChannel和channel存在于同一个中ChannelGroup中,则对该组执行的任何请求的I / O操作ServerChannel都会先执行
		* 当一次性关闭服务器时,此规则非常有用

		ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

		 public static void main(String[] args) throws Exception {
			 ServerBootstrap b = new ServerBootstrap(..);

			 // 忽略常规的配置....
			 b.childHandler(new MyHandler());

			// 启动服务器
			 b.getPipeline().addLast("handler", new MyHandler());
			 Channel serverChannel = b.bind(..).sync();
			 allChannels.add(serverChannel);

			 // 等待关闭信令

			 //会先关闭 serverChannel 然后再关闭其他的 channel
			 allChannels.close().awaitUninterruptibly();
		 }

		 public class MyHandler extends ChannelInboundHandlerAdapter {
			  @Override
			 public void channelActive(ChannelHandlerContext ctx) {
				 // closed on shutdown.
				 allChannels.add(ctx.channel());
				 super.channelActive(ctx);
			 }
		 }