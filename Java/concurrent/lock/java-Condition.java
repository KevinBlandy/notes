
------------------------
Condition				|
------------------------
	# Condition 将 Object 监视器方法(wait,notify 和 notifyAll)分解成截然不同的对象
		* 以便通过将这些对象与任意 Lock 实现组合使用,为每个对象提供多个等待 set (wait-set)

		* 与 Object 的 wait 方法一样，调用 await 方法前需要先获取锁，如果没有锁，会抛出异常 IllegalMonitorStateException。


	# 接口方法
		void await() throws InterruptedException;
		boolean await(long time, TimeUnit unit) throws InterruptedException;
		long awaitNanos(long nanosTimeout) throws InterruptedException;
		boolean awaitUntil(Date deadline)  throws InterruptedException;
		
		*  wait() 阻塞睡眠,等待唤醒
		* 这些await方法都是响应中断的，如果发生了中断，会抛出 InterruptedException，但中断标志位会被清空
		
		void awaitUninterruptibly()
			* 不响应中断的等待方法

		void signal()
		void signalAll()
			* 随机唤醒一个/唤醒所有


------------------------
Condition 生产者消费者
------------------------