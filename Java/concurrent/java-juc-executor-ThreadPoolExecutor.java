---------------------------
ThreadPoolExecutor			|
---------------------------
	# 线程池的实现
	# 构造方法
		ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue)
		ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler)
		ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory)
		ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler)

		* corePoolSize
			* 线程池基本的线程数量

		* maximumPoolSize
			* 线程池最大线程数量

		* keepAliveTime
			* 超出 corePoolSize 后创建的线程, 空闲后的存活时间
			* 超过时间后就会被回收

		* unit
			*  存活时间的单位

		* workQueue
			* 存放任务的阻塞队列
			* 如果 workQueue 是无界的, 那么永远不会触发 maximumPoolSize, 自然keepAliveTime也就没有了意义

		* threadFactory
			* 线程池工厂类

		* handler
			* 在线程池无法处理新任务时的处理handler
		
	# 实例方法
		void allowCoreThreadTimeOut(boolean value)
		boolean allowsCoreThreadTimeOut()
		int getCorePoolSize()
		boolean awaitTermination(long timeout, TimeUnit unit)

		
		int getActiveCount()
		long getCompletedTaskCount()
		long getKeepAliveTime(TimeUnit unit)
		int getLargestPoolSize()
		int getMaximumPoolSize()
		int getPoolSize()
		BlockingQueue<Runnable> getQueue()
		RejectedExecutionHandler getRejectedExecutionHandler()
		long getTaskCount()
		ThreadFactory getThreadFactory()
		boolean isTerminated()
			* 如果队列中的所有任务都处理完毕后返回 true

		boolean isTerminating() 
		int prestartAllCoreThreads()
		boolean prestartCoreThread()
		void purge()
		boolean remove(Runnable task)
		void setCorePoolSize(int corePoolSize)
		void setKeepAliveTime(long time, TimeUnit unit)
		void setMaximumPoolSize(int maximumPoolSize)
		void setRejectedExecutionHandler(RejectedExecutionHandler handler) 
		void setThreadFactory(ThreadFactory threadFactory)

		boolean isShutdown()
		void shutdown()
			* 会等到所有任务完成才会关闭

		List<Runnable> shutdownNow()
			* 立即关闭线程池
			* 对正在执行的任务全部发出interrupt(),停止执行
			* 对还未开始执行的任务全部取消,并且返回还没开始的任务列表

		void execute(Runnable command)
		Future<?> submit(Runnable task)
			* 执行任务,submit可以获取到执行结果的返回值和抛出的异常

		<T> Future<T> submit(Runnable task, T result)
		<T> Future<T> submit(Callable<T> task)
		<T> T invokeAny(Collection<? extends Callable<T>> tasks,long timeout, TimeUnit unit)
		<T> T invokeAny(Collection<? extends Callable<T>> tasks)
		<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,long timeout, TimeUnit unit)
		<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
	
	# 内部类
		* 他们都是 ThreadPoolExecutor 的实现类,负责处理线程池无法执行新任务时的情况

		AbortPolicy
			* 无法处理时抛出异常
			* 源码
				public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
					throw new RejectedExecutionException("Task " + r.toString() + " rejected from " +  e.toString());
		        }

		CallerRunsPolicy
			* 直接运行新任务
			* 源码
				public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
					if (!e.isShutdown()) {
						r.run();
					}
				}

		DiscardOldestPolicy
			* 丢弃队列中最老的任务
			* 源码
				public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
					if (!e.isShutdown()) {
						e.getQueue().poll();
						e.execute(r);
					}
				}

		DiscardPolicy
			* 丢弃新任务
			* 源码
				public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
				}

---------------------------
RejectedExecutionHandler   |
---------------------------
	# ThreadPoolExecutor 无法处理新任务时的处理Handler 接口
	# 抽象方法
		void rejectedExecution(Runnable r, ThreadPoolExecutor executor);


---------------------------
线程池参数的详解		   |
---------------------------
	corePoolSize
		* 如果运行的线程少于 corePoolSize, 则创建新线程来处理任务, 即使线程池中的其他线程是空闲的
		* 如果线程池中的线程数量大于等于 corePoolSize 且小于 maximumPoolSize , 则只有当 workQueue 满时才创建新的线程去处理任务
			
		* 如果设置的 corePoolSize 和 maximumPoolSize 相同, 则创建的线程池的大小是固定的,
		* 这时如果有新任务提交, 若workQueue未满, 则将请求放入workQueue中, 等待有空闲的线程去从workQueue中取任务并处理

		* 如果运行的线程数量大于等于 maximumPoolSize, 这时如果workQueue已经满了, 则通过handler所指定的策略来处理任务；
	
	maximumPoolSize
		* 最大线程数, 当线程数 >= corePoolSize 的时候, 会把runnable放入workQueue中

	workQueue
		* 等待队列, 当任务提交时, 如果线程池中的线程数量大于等于 corePoolSize 的时候, 把该任务封装成一个 Worker 对象放入等待队列

	1, 当线程数小于 corePoolSize 时, 创建线程执行任务

	2, 当线程数大于等于 corePoolSize 并且 workQueue 没有满时, 放入 workQueue 中

	3, 线程数大于等于 corePoolSize 并且当 workQueue 满时, 新任务新建线程运行, 线程总数要小于 maximumPoolSize
		* 超出了 corePoolSize 而创建的线程, 如果空闲时间超过了:keepAliveTime 就会被回收

	4, 当线程总数等于 maximumPoolSize 并且 workQueue 满了的时候执行 handler 的 rejectedExecution 也就是拒绝策略
	

	