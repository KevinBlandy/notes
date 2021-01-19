----------------------------
GlobalEventExecutor			|
----------------------------
	# 具备任务队列的单线程事件执行器,其适合用来实行时间短,碎片化的任务
	# 实现了 AbstractScheduledEventExecutor 
	# 
	# 唯一的一个静态实例
		GlobalEventExecutor.INSTANCE