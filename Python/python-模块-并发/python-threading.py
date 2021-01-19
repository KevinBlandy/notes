-----------------------
threading				|
-----------------------
	* 多线程模块
	* 模块类
		Thread
		Lock
		RLock
		Event
		local
		BoundedSemaphore
		Condition

-----------------------
threading-属性			|
-----------------------
-----------------------
threading-函数			|
-----------------------

	str current_thread()
		* 返回当前线程的名称

	int	active_count()
		* 返回活跃线程的个数
	
	BoundedSemaphore()
		* 最多允许同时执行多少个线程
	
	main_thread()
		* 返回主线程,正常情况下,主线程就是python 解释器运行时开始的那个线程
	
	int	get_ident()
		* 返回当先线程的索引,这个索引是一个非零的整数

-----------------------
threading-Thread		|
-----------------------
	* 线程类
	* 构造函数
		Thread(group=None, target=None, name=None,args=(), kwargs=None, *, daemon=None)
			* 关键字参数
				target	# 目标函数,也就是线程会执行目标函数
				args	# 执行目标函数时,传递的参数,该值是一个 tuple
				name	# 子线程的名称,如果不起名字Python就自动给线程命名为Thread-1,Thread-2……
	
	* 实例属性
		ident 
			* 获取线程的标识符。线程标识符是一个非零整数，只有在调用了 start() 方法之后该属性才有效,否则它只返回None
		
		name 
			* 获取或设置线程的名称

	* 实例方法
		start() 
			* 启动线程执行
		
		join()
			* 等待当前(self)线程执行完毕后才会(当前进程)往下执行,该方法使得多线程变得无意义
		
		getName()
			* 获取线程的名称

		setName()
			* 设置线程的名称 
		
		is_alive()
			* 判断线程是否为激活状态

		isAlive() 
			* 判断线程是否为激活状态

		setDaemon() 
			* 设置为后台线程或前台线程默认:False
			* 通过一个布尔值设置线程是否为守护线程
			* 必须在执行start()方法之后才可以使用。
			* 如果是后台线程,主线程执行过程中,后台线程也在进行,主线程执行完毕后,后台线程不论成功与否,均停止
			* 如果是前台线程,主线程执行过程中,前台线程也在进行,主线程执行完毕后,等待前台线程也执行完成后,程序停止

		isDaemon() 
			* 判断是否为守护线程


		run() 
			* 线程被cpu调度后自动执行线程对象的run方法
	* 在OOP中,也可以直接继承该类,覆写 run() 方法后,创建实例调用 start(),方法来启动一个线程

-----------------------
threading-Lock			|
-----------------------
	* 线程安全锁
	* 构造函数
		lock = Lock()

	* 实例方法
		lock.acquire()
			* 开始上锁
	
		lock.release()
			* 释放锁

-----------------------
threading-RLock			|
-----------------------
	* 也是属于线程锁,跟 Lock 差不多
	* 传说中的递归锁
	* RLock 允许在同一线程中被多次 acquire() 而Lock却不允许这种情况
	* 如果使用 RLock,那么 acquire() 和 release()必须成对出现,即调用了n次 acquire() 必须调用n次的 release() 才能真正释放所占用的琐


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

-----------------------
threading-local			|
-----------------------
	* 跟Java中的 ThreadLocal 一样,线程安全的容器
	* 可以给 local 实例进行赋值/读取值的操作
		thread_local = threading.local()
		thread_local.name = 'Kevin'
		thread_local.age = 23

	* 如果读取 local 实例不存在的属性,会抛出异常
	* 可以使用 hasattr(obj,attr),函数来判断 local 对象是否具备指定的属性
	* 构造函数
		local()

	* 实例方法
		pass

	* Demo
		import threading

		thread_local = threading.local()

		def func1():
			print(threading.current_thread().name)
			thread_local.name = 'func1'
			print(thread_local.name )

		def func2():
			print(threading.current_thread().name)
			thread_local.name = 'func2'
			print(thread_local.name )

		def func3():
			print(threading.current_thread().name)
			thread_local.name = 'func3'
			print(thread_local.name )

		thread_1 = threading.Thread(target=func1,name = "线程1")
		thread_2 = threading.Thread(target=func2,name = "线程2")
		thread_3 = threading.Thread(target=func3,name = "线程3")

		thread_1.start()
		thread_2.start()
		thread_3.start()


--------------------------
threading-BoundedSemaphore|
--------------------------
	* 信号量,严格来说也是锁
	* 该锁,实现了可以允许指定数量的线程同时操作一个资源
	* 实例构造
		 boundedSemaphore = threading.BoundedSemaphore(num)
			* 参数,表示可以同时执行的线程数量

	* 实例方法
		acquire()
			* 开始上锁
	
		release()
			* 释放锁
		
--------------------------
threading-Condition			|
--------------------------
	* 就有点像Java中的锁一样了
	* 创建
		threading.Condition(obj)
			* 传入一个锁实例
			* 如果不传递锁实例,则会自动创建一个 RLock 锁

	* 实例方法
			wait()
				* 释放锁,并且阻塞当前线程

			notify()
				* 唤醒指定锁的线程

			notifyAll()
				* 唤醒所有的线程
		