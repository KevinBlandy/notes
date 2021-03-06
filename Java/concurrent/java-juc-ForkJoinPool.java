-------------------------------
JDK7的新东西-Fork/Join			|
-------------------------------
	# 分支合并
		* 把大任务分解为N个小任务进行执行,最后合并结果

	# 采用工作窃取模式
		* 工作窃取（work-stealing）算法是指某个线程从其他队列里窃取任务来执行。
		* 假如我们需要做一个比较大的任务，可以把这个任务分割为若干互不依赖的子任务，为了减少线程间的竞争，把这些子任务分别放到不同的队列里，并为每个队列创建一个单独的线程来执行队列里的任务，线程和队列一一对应。
		* 比如A线程负责处理A队列里的任务。但是，有的线程会先把自己队列里的任务干完，而其他线程对应的队列里还有任务等待处理。
		* 干完活的线程与其等着，不如去帮其他线程干活，于是它就去其他线程的队列里窃取一个任务来执行。
		* 而在这时它们会访问同一个队列，所以为了减少窃取任务线程和被窃取任务线程之间的竞争，通常会使用双端队列，被窃取任务线程永远从双端队列的头部拿任务执行，而窃取任务的线程永远从双端队列的尾部拿任务执行。
		
		* 工作窃取算法的优点：充分利用线程进行并行计算，减少了线程间的竞争
		* 工作窃取算法的缺点：在某些情况下还是存在竞争，比如双端队列里只有一个任务时。并且该算法会消耗了更多的系统资源，比如创建多个线程和多个双端队列

		
	# 使用ForkJoin框架,必须先创建一个ForkJoin任务
		* 它提供在任务中执行fork()和join的操作机制,通常不直接继承ForkjoinTask类,只需要直接继承其子类
			RecursiveAction
			RecursiveTask
		
	# 使用Fork/Join框架首先要考虑到的是如何分割任务
		
	
	# 结构体系
		ForkJoinTask<V>
			* 接口
			|-RecursiveAction
				* 不带返回值的抽象类
				protected abstract void compute();

			|-RecursiveTask<V>
				* 带返回值的抽象类
				protected abstract V compute();

		ForkJoinPool
			* ForkJoinTask 需要 ForkJoinPool 来执行
	

-------------------------------
ForkJoinPool					|
-------------------------------
-------------------------------
ForkJoinTask					|
-------------------------------
	ForkJoinTask<V> fork()
	V join()
	V invoke()