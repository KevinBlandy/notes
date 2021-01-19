------------------------
多线程					|
------------------------
	* 进程是由若干线程组成的,一个进程至少有一个线程
	* 由于线程是操作系统直接支持的执行单元,因此,高级语言通常都内置多线程的支持
	* Python也不例外,并且,Python的线程是真正的 Posix Thread,而不是模拟出来的线程
	* Python的标准库提供了两个模:_thread 和 threading
	* _thread 是低级模块,threading 是高级模块,对_thread进行了封装,绝大多数情况下,我们只需要使用 threading 这个高级模块
	* 启动一个线程就是把一个函数传入并创建Thread实例,然后调用start()开始执行
	* 步骤
		1,创建 threading.Thread 实例
			threading.Thread(group=None, target=None, name=None,args=(), kwargs=None, *, daemon=None)
				* 关键字参数
					target	# 目标函数,也就是线程会执行目标函数
					args	# 执行目标函数时,传递的参数,该值是一个 tuple
					name	# 子线程的名称,如果不起名字Python就自动给线程命名为Thread-1,Thread-2……
		
		2,执行实例的 start() 方法,执行线程
		
	* Demo
		import time, threading

		# 新线程执行的代码:
		def loop():
			print('线程 (%s)启动...' % threading.current_thread().name)
			n = 0
			while n < 5:
				n = n + 1
				print('线程 (%s)执行中.. >>> %s' % (threading.current_thread().name, n))
				time.sleep(1)
			print('线程 (%s)执行完毕' % threading.current_thread().name)


		print('进程  (%s) 执行' % threading.current_thread().name)
		t = threading.Thread(target=loop, name='LoopThread')
		t.start()
		t.join()
		print('进程 (%s) 执行完毕' % threading.current_thread().name)

------------------------
面向对象的方式创建多线程|
------------------------
	* 跟Java有点像了
	* 步骤
		1,自定义类,继承  threading.Thread
		2,覆写父类的 run() 方法
		3,创建类实例
		4,调用类实例的 start() 方法,则会新启动线程去执行 run()

	* Demo
		import threading

		class thread(threading.Thread):
			def run(self):
				print(threading.current_thread().name)

		thread_1 = thread()
		thread_2 = thread()
		thread_3 = thread()

		thread_1.start()    # Thread-1
		thread_2.start()    # Thread-2
		thread_3.start()    # Thread-3
    
---------------------------
线程安全问题-threading.Lock	|
---------------------------
	* 多线程和多进程最大的不同在于,多进程中,同一个变量,各自有一份拷贝存在于每个进程中,互不影响
	* 而多线程中,所有变量都由所有线程共享,所以,任何一个变量都可以被任何一个线程修改
	* 因此,线程之间共享数据最大的危险在于多个线程同时改一个变量，把内容给改乱了
	* 创建锁
		lock = threading.Lock()
	* 锁住资源
		lock.acquire()
	* 释放锁
		lock.release()
		
	* Demo
		balance = 0

		lock = threading.Lock()

		def run_thread(n):
			for i in range(100000):
				# 先要获取锁:
				lock.acquire()
				try:
					# 放心地改吧:
					change_it(n)
				finally:
					# 改完了一定要释放锁:
					lock.release()
	
	* 死锁的问题
		... ...
	* demo
		import threading;
		import time
		num = 100
		threads = []
		lock = threading.Lock()
		def func():
			try:
				lock.acquire()
				global num
				temp = num
				time.sleep(0.000001)
				num = temp - 1
			finally:
				lock.release()
		for i in range(100):
			thread = threading.Thread(target=func)
			thread.start()
			threads.append(thread)
		for i in threads:
			i.join()
		print(num)

-----------------------
threading-Event			|
-----------------------
	* python线程的事件用于主线程控制其他线程的执行,线程同步
	* 事件实例主要提供了三个方法 set,wait,clear
	* 事件处理的机制
		全局定义了一个 "Flag"
		如果"Flag"值为 False,那么当程序执行 event.wait 方法时就会阻塞
		如果"Flag"值为 True,那么event.wait 方法时便不再阻塞
	
	* 创建实例
		 lock = threading.Event()
		
	* api
		
		wait()
			* 如果"Flag"值为 False,那么当程序执行 event.wait 方法时就会阻塞
			* 如果"Flag"值为 True,那么event.wait 方法时便不再阻塞

		clear()
			* 将"Flag"设置为 False

		set()
			* 将"Flag"设置为 True

	* 实例方法
		Event.isSet() 
			* 判断标识位是否为Ture

------------------------------
线程安全的容器-threading.local|
------------------------------
	* threading.local	类
	* 跟Java中的 ThreadLocal 一样,线程安全的容器
	* 可以给 local 实例进行赋值/读取值的操作
		thread_local = threading.local()
		thread_local.name = 'Kevin'
		thread_local.age = 23

	* 如果读取 local 实例不存在的属性,会抛出异常
	* 构造函数
		local()


---------------------------------------------
线程池	concurrent.futures.ThreadPoolExecutor|
---------------------------------------------
	* 线程池类:concurrent.futures.ThreadPoolExecutor
	* 构造函数
		ThreadPoolExecutor(max_workers=50)
			* 池中默认创建 cpu 核心数 * 5 个线程
			* 关键字参数
				thread_name_prefix
					* 线程名称的前缀
				max_workers
					* 最大线程数量

	* 实例方法
		 concurrent.futures._base.Future submit(fn, *args, **kwargs)
			* 执行一个任务
			* fn:就是任务函数
			* *args, **kwargs 是运行该函数时,传递的参数
			* 返回值是一个 Future 对象
	
	* 从 Future 获取线程的执行结果
		future = threadPoolExecutor.submit(func)
		# 给 future 添加监听,当线程执行完毕后,就会执行回调函数
		# 会把自己当作第一个参数传入,可以通过其 result(),API可以获取结果
		future.add_done_callback(lambda x: print('线程执行完毕,结果是:%s'%(x.result())))
		
	
	* Demo
		import threading

		from concurrent.futures import ThreadPoolExecutor
		 
		def func():
			print(threading.current_thread().getName())
			return '我就是结果'
		 
		threadPoolExecutor = ThreadPoolExecutor(max_workers=50)

		future = threadPoolExecutor.submit(func)
		future.add_done_callback(lambda x: print('线程执行完毕,结果是:%s'%(x.result())))

------------------------
多线程-总结				|
------------------------
	* Python多线程不适合cpu密集形的任务,适合IO密集型任务

