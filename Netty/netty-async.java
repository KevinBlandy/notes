----------------------------
异步机制					|
----------------------------
	# jdk的 Future
		* 只能通过手动的方式检查执行结果,并且会阻塞

	# ChannelFuture
		* 通过监听器的方式,以回调的方式来获取执行结果,不需要手动检查,不会阻塞
		* ChannelFutureListener 的回调方法 operationComplete(F future) 是由IO线程去执行的,所以不要在这里执行耗时的操作,可以使用线程池去执行

		
	# ChannelPromise
		* 继承自 ChannelFuture ,Promise
		* 可以由开发者去控制是否成功,还是失败

--------------------------------
ChannelFuture					|
--------------------------------
	# 方法
		Channel channel();
			* 返回关联的channel

		@Override
		ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> listener);
			* 添加一个监听

		@Override
		ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners);
			* 添加多个监听

		@Override
		ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> listener);
			* 移除一个监听

		@Override
		ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners);
			* 移除多个监听

		ChannelFuture sync()
			* 同步,线程阻塞,直到任务完成
		
		Throwable cause()
			* 返回异常信息
		
		boolean isSuccess();
			* 是否操作成功
		
		 boolean isVoid();
	
	# ChannelFutureListener 
		* 空继承了接口:GenericFutureListener
		* 预定义了N个实例
			CLOSE
				* 完成后关闭连接
			CLOSE_ON_FAILURE
				* 如果抛出了异常,关闭连接
			FIRE_EXCEPTION_ON_FAILURE

--------------------------------
ChannelPromise					|
--------------------------------
	# 继承自 ChannelFuture ,Promise
	# 新增加了一个机制:可写

		@Override
	    ChannelPromise setSuccess(Void result);

		@Override
		ChannelPromise setFailure(Throwable cause);

		ChannelPromise setSuccess();

		boolean trySuccess();

		ChannelPromise unvoid();