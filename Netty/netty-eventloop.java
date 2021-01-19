------------------------
EventLoop				|
------------------------
	# 类库
		EventLoopGroup 
			* 继承接口:ScheduledExecutorService, Iterable
			|-MultithreadEventLoopGroup
				|-EpollEventLoopGroup
				|-NioEventLoopGroup
			
	
	# EpollEventLoopGroup
		* 由jni驱动的epoll()和非阻塞IO
		* 只能在Linux系统上使用,比NIO传输更快
		* 如果使用epoll(),那么ServerBootstrap的channel要使用:EpollServerSocketChannel

			NioEventLoopGroup		→ EpollEventLoopGroup
			NioEventLoop			→ EpollEventLoop

			NioServerSocketChannel	→ EpollServerSocketChannel
			NioSocketChannel		→ EpollSocketChannel
				
	# 总结
		* 一个EventLoopGroup当中会包含一个或者多个EventLoop
		* 一个EventLoop在它的整个生命周期中都只会与唯一一个Thread进行绑定
		* 所有由EventLoop所处理的各种I/O事件都将在它所关联的那个Thread上进行处理
		* 一个Channel在它的整个生命周期中只会被注册一个EventLoop上
		* 一个EventLoop在运行过程当中,会被分配给一个或者多个Cahnnel
		
		* 在Netty中,Channel的实现一定是线程安全的,基于此,可以存储一个Channel的引用,并且在需要向远程端点发送数据时,通过该引用调用Cahnnel相应的方法
		* 即便当时在多个线程都在同时操作的时候,也不会出现线程安全问题,并且消息一定会按照顺序发送出去
	
		* 在实际开发中,不要把执行时间长的任务放入到EventLoop的执行队列中,因为它会一直阻塞该线程所对应的所有Channel上的其他执行任务
		* 如果需要进行阻塞调用或者是耗时的操作,那可以专门准备一个EventExecutor(业务线程池)
			
			// 实例化线程池
			ExecutorService executorService = Executors.newCachedThreadPool();

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
				// 通过自己定义的线程池异步的去执行任务
				executorService.execute(() -> {
					
				});
			}
		
		* 借助 Netty 提供的向 ChannelPipeline ,添加 ChannelHandler时调用的addLst()方法来传递EventExecutor

			ChannelPipeline addLast(EventExecutorGroup group, String name, ChannelHandler handler);
			ChannelPipeline addLast(EventExecutorGroup group, ChannelHandler... handlers);

			* 通过这种方式,来设置指定Handler中回调方法执行使用的线程池(EventExecutorGroup)
			* EventExecutorGroup 子类也太TM多了吧?


