---------------------------
Thread						|
---------------------------
	# 多线程启动对象
	# 构造方法
		Thread t = new Thread();
		Thread t = new Thread(Runnable r);

---------------------------
Thread-属性					|
---------------------------
	# 静态属性
	
	# 实例属性

---------------------------
Thread-方法					|
---------------------------
	# 静态方法
		Thread currentThread();
			* 返回当前的线程对象
		sleep(long l);
			* 当前线程停止 l 毫秒
		
		Map<Thread, StackTraceElement[]> getAllStackTraces()
			* 获取到JVM中所有线程的执行栈

	# 实例方法
		
		getName();
			* 返回线程名称
		
		void setPriority(int newPriority)
			* 设置线程的优先级
			* Thread 类提供了N多的静态变量值
		
		int getPriority()
			* 获取线程的优先级
		
		void interrupt()
			* 中断线程
		
		boolean isInterrupted()
			* 线程是否被中断
		
		void join();
			* 调用该方法的线程会一直阻塞,直到该线程(join 方法的 Thread 线程)执行完毕后才往下执行
		
		void setDaemon(boolean on)
			* 设置为当前线程的守护线程
			* 必须在调用 start() 方法之前设置
		
		void stop();
			* 暴力停止该线程
		
		boolean isAlive()
			* 判断线程是否还存活
		
		void setContextClassLoader(ClassLoader cl)
		ClassLoader getContextClassLoader()
			* 设置/获取当前线程程序中使用的 classloader

---------------------------
Thread 的中断机制			|
---------------------------
	# 每个线程都有一个 "中断" 标志,这里分两种情况
	
	# 线程在sleep或wait(阻塞),join ....
		* 此时如果别的进程调用此进程(Thread 对象)的 interrupt()方法,此线程会被唤醒并被要求处理 InterruptedException
		* (thread在做IO操作时也可能有类似行为,见java thread api)
		* 异常发生后,会重置这个标识位为 false
	
	# 此线程在运行中
		* 此时如果别的进程调用此进程(Thread 对象)的 interrupt()方法,不会收到提醒,但是此线程的 "中断" 会被设置为 true
		* 可以通过 isInterrupted() 查看并作出处理

	# 总结
		interrupt()		实例方法	返回 void		中断调用该方法的当前线程
		interrupted()	静态方法	返回 boolean	检测当前线程是否被中断，如已被中断过则清除中断状态
			* 注意, 这是一个静态方法，只能对当前进程进行操作

		isInterrupted()	实例方法	返回 boolean	检测调用该方法的线程是否被中断，不清除中断标记