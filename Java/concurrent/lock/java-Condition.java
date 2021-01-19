
------------------------
Condition				|
------------------------
	# Condition 将 Object 监视器方法(wait,notify 和 notifyAll)分解成截然不同的对象
		* 以便通过将这些对象与任意 Lock 实现组合使用,为每个对象提供多个等待 set (wait-set)

	# 接口方法
		void await()
			*  wait() 阻塞睡眠,等待唤醒
		boolean await(long time, TimeUnit unit)
		long awaitNanos(long nanosTimeout)
		void awaitUninterruptibly()
		boolean awaitUntil(Date deadline) 
		void signal()
			* notify ,唤醒
		void signalAll()
