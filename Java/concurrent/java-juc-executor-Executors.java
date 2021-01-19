--------------------
Executors			|
--------------------
	# 创建各种线程池的工厂类
		ExecutorService newFixedThreadPool(int nThreads);		
			* 创建固定大小的线程池,每次提交一个任务就创建一个线程,直到线程达到线程池的最大大小
			* 线程池的大小一旦达到最大值就会保持不变
			* 如果某个线程因为执行异常而结束,那么线程池会补充一个新线程
			* 的面使用的 LinkedBlockingQueue 是一个无边界队列,如果不断的往里加任务,最终会导致内存的不可控
		
		ExecutorService newCachedThreadPool();
			* 创建一个可缓存的线程池,如果线程池的大小超过了处理任务所需要的线程,那么就会回收部分空闲的线程
			* 当任务数增加时,此线程池又添加新线程来处理任务

		ExecutorService newSingleThreadExecutor()
			* 一个单线程的线程池,这个线程池只有一个线程在工作,也就是相当于单线程串行执行所有任务
			* 如果这个唯一的线程因为异常结束,那么会有一个新的线程来替代它
			* 此线程池保证所有任务的执行顺序按照任务的提交顺序执行
		
		ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
			* 创建调度线程池
			* 创建固定大小的线程池,可以延时/重复的执行任务调度
		
		ExecutorService newWorkStealingPool() 
			* jdk1.8新提供的
			* 会根据所需的并行层次来动态创建和关闭线程,通过使用多个队列减少竞争
			* 底层用的 ForkJoinPool 来实现的
			* ForkJoinPool的优势在于,可以充分利用多cpu,多核cpu的优势,把一个任务拆分成多个"小任务"
			* 把多个"小任务"放到多个处理器核心上并行执行,当多个"小任务"执行完成之后,再将这些执行结果合并起来即可
	

	# 五种线程池的使用场景
		newSingleThreadExecutor
			一个单线程的线程池,可以用于需要保证顺序执行的场景,并且只有一个线程在执行

		newFixedThreadPool
			一个固定大小的线程池,可以用于已知并发压力的情况下,对线程数做限制

		newCachedThreadPool
			一个可以无限扩大的线程池,比较适合处理执行时间比较小的任务

		newScheduledThreadPool
			可以延时启动,定时启动的线程池,适用于需要多个后台线程执行周期任务的场景

		newWorkStealingPool
			一个拥有多个任务队列的线程池,可以减少连接数,创建当前可用cpu数量的线程来并行执行
		
