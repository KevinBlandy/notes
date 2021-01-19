----------------------------
CountDownLatch				|
----------------------------
	# 在完成某些运算的时候,只有其他的运算全部完成,当前运算才会执行
	# 和 CyclicBarrier 的区别
		CountDownLatch:一个或者多个线程,等待其他多个线程完成某件事情之后才能执行
			* 重点是一个线程(多个线程_等待,而其他的N个线程在完成某件事情之后,可以终止,也可以等待

		CyclicBarrier:多个线程互相等待,直到到达同一个同步点,再继续一起执行
			* 重点是多个线程,在任意一个线程没有完成,所有的线程都必须等待

	# 创建
		CountDownLatch countDownLatch = new CountDownLatch(5);
			* 创建的时候,就指定一个基数
	# 方法
		countDown();
			* 在基数上减1,当值为0的时候,countDownLatch
		
		await();
			* 阻塞,等待其他线程执行完,直到基数 == 0
			* 其实本身就是个循环检测
		
		boolean await(long timeout, TimeUnit unit)
			* 设置超时时间
		
		long getCount()
			* 返回count

	# demo
		
		CountDownLatch countDownLatch = new CountDownLatch(5);

		//启动多线程执行,当有线程执行完毕后,执行该api
		countDownLatch.countDown();

		
		
		//主线程阻塞,直到 countDownLatch 基数 == 0
		countDownLatch.await();