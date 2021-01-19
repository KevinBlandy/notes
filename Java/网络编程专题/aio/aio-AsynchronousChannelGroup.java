----------------------------------
AsynchronousChannelGroup			|
-----------------------------------
	# AsynchronousChannelGroup 是一个抽象类,通过静态方法获取实例对象

		AsynchronousChannelGroup withThreadPool(ExecutorService executor);
		AsynchronousChannelGroup withCachedThreadPool(ExecutorService executor,int initialSize);
		AsynchronousChannelGroup withFixedThreadPool(int nThreads,ThreadFactory threadFactory);