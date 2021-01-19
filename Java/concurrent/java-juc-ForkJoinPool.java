-------------------------------
JDK7的新东西-Fork/Join			|
-------------------------------
	# 分支合并
		* 把大任务分解为N个小任务进行执行,最后合并结果

	# 采用工作窃取模式

		
	# 使用ForkJoin框架,必须先创建一个ForkJoin任务
		* 它提供在任务中执行fork()和join的操作机制,通常不直接继承ForkjoinTask类,只需要直接继承其子类
	
	# 结构体系
		ForkJoinPool
		ForkJoinTask<V>
			* 接口
			|-RecursiveAction
				* 不带返回值的抽象类
				protected abstract void compute();

			|-RecursiveTask<V>
				* 带返回值的抽象类
				protected abstract V compute();
	

-------------------------------
ForkJoinPool					|
-------------------------------
-------------------------------
ForkJoinTask					|
-------------------------------
	ForkJoinTask<V> fork()
	V join()
	V invoke()