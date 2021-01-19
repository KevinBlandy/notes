-----------------------------------
AsynchronousServerSocketChannel		|
-----------------------------------
	# 异步,非阻塞服务器端Socket通道
	# AIO
	# AIO提供了两种异步操作的监听机制。
		* 第一种通过返回一个 Future 对象来实现，调用其get();会阻塞到操作完成。
			Future<V>
				boolean cancel(boolean mayInterruptIfRunning);
				boolean isCancelled();
				boolean isDone();
				V get() throws InterruptedException, ExecutionException;
				V get(long timeout, TimeUnit unit)throws InterruptedException, ExecutionException, TimeoutException;

		* 第二种类似于回调函数。在进行异步操作时，传递一个 CompletionHandler 接口实例 当异步操作结束时，会调用 实例的 complete 方法
		* 不会阻塞,OS完成IO后回调
			CompletionHandler<V,A>
				void completed(V result, A attachment);
					* 任务完成的时候执行
				void failed(Throwable exc, A attachment);
					* 任务异常的时候执行
	
-----------------------------------
AsynchronousServerSocketChannel-API|
-----------------------------------
	# 静态方法
		AsynchronousServerSocketChannel	open();	
			* 打开一个异步的Socket通道
		AsynchronousServerSocketChannel	open(AsynchronousChannelGroup group);
			* 使用 AsynchronousChannelGroup 来打开一个新的通道

	# 实例方法
		//===================== 监听
		AsynchronousServerSocketChannel bind(SocketAddress local);
			* 监听指定的端口,返回 this 
			* 如果该参数是 null,则自动会在当前机器寻找空闲的端口进行绑定
			* demo
				AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8888));
		AsynchronousServerSocketChannel	bind(SocketAddress local, int backlog)
			* 同上,并且设置最大连接数量
		
		//===================== 阻塞
		Future<AsynchronousSocketChannel> accept();
			* 该方法会阻塞,直到收到请求
		
		<A> void accept(A attachment,CompletionHandler<AsynchronousSocketChannel,? super A> handler);
			* 传递一个附件 attachment
			* 该在 CompletionHandler 接口的 completed 方法 中可以获取到这个附件
		
		
		//===================== 配置
		<T> AsynchronousServerSocketChannel setOption(SocketOption<T> name, T value);
			* 设置配置
		<T> T getOption(SocketOption<T> name);
			* 获取配置
		Set<SocketOption<?>> supportedOptions();
			* 获取支持的配置

		//===================== 其他
		void close();
			* 关闭

		SocketAddress getLocalAddress()；
			* 获取当前的地址
		
		AsynchronousChannelProvider	provider();
			* 未知
	

----------------------------------
AsynchronousChannelGroup			|
-----------------------------------
	# AsynchronousChannelGroup 是一个抽象类,通过静态方法获取实例对象
		AsynchronousChannelGroup withThreadPool(ExecutorService executor);
		AsynchronousChannelGroup withCachedThreadPool(ExecutorService executor,int initialSize);
		AsynchronousChannelGroup withFixedThreadPool(int nThreads,ThreadFactory threadFactory);