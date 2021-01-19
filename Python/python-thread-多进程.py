----------------------------
Linux/Unix多进程			|
----------------------------
	* 了解操作系统的相关知识。
		* Unix/Linux操作系统提供了一个fork()系统调用,它非常特殊,
		* 普通的函数调用,调用一次,返回一次,但是fork()调用一次,返回两次
		* 因为操作系统自动把当前进程(称为父进程)复制了一份(称为子进程),然后,分别在父进程和子进程内返回
		* 子进程永远返回0,而父进程返回子进程的ID。
		* 这样做的理由是,一个父进程可以fork出很多子进程,所以,父进程要记下每个子进程的ID,而子进程只需要调用getppid()就可以拿到父进程的ID
	
	* os模块封装了常见的系统调用,其中就包括fork,可以在Python程序中轻松创建子进程
	* fork(),仅仅在 Unix/Linux 上有效,Windows没用
		import os
		print('Process (%s) start...' % os.getpid())
		
		pid = os.fork()
		if pid == 0:
			print('我是子进程(%s)我的父进程是(%s).' %(os.getpid(), os.getppid()))
		else:
			print('我是父进(%s)创建了一个子进程(%s).' %(os.getpid(), pid))
		
		
--------------------------------------
跨平台的多进程-multiprocessing.Process|
--------------------------------------
	* 模块:multiprocessing
	*  multiprocessing 模块提供了一个 Process 类来代表一个进程对象
	* 创建步骤
		1,创建 Process 实例对象
			Process()
				* 关键字参数
					target	# 目标函数,也就是多另起线程去执行的函数
					args	# 执行目标函数时,传递的参数,该值是一个 tuple


		2,调用实例的 start(),函数启动线程

	* 其他API
		join()
			* 等待子进程结束后再继续往下运行，通常用于进程间的同步
	
	* Demo
		from multiprocessing import Process
		import os

		# 子进程要执行的函数
		def run_proc(name):
			print('子进程运行 name=:%s 我的ID=:%s ,父级进程的ID=:%s' % (name, os.getpid(),os.getppid()))

		if __name__=='__main__':
			
			print('父进程ID %s' %(os.getpid()))
			p = Process(target=run_proc, args=('test',))
			print('子进程准备开始执行')
			p.start()
			p.join()
			print('子进程执行完毕')
	


----------------------------
面向对象的方式创建多进程	|
----------------------------
	* 跟Java有点像
	* 步骤
		1,自定义类,继承 multiprocessing.Process
		2,覆写 run 方法,在该方法中实现多线程要执行的逻辑
		3,创建类实例对象
		4,执行对象的 start()方法
	* demo
		import multiprocessing
		import os

		class MyProcess(multiprocessing.Process):
			def run(self, *args, **kwargs):
				print(os.getpid())
				print(os.getppid())

		if __name__ == '__main__':
			MyProcess().start()
	
	* 对象预定义属性
		name		:该值为   当前类名-编号(MyProcess-1)
		
----------------------------
进程池-multiprocessing.Pool	|
----------------------------
	* 需要频繁的创建/销毁线程就可以考虑使用线程池
	* 进程池,也需要运行在 if __name__ == '__main__':pass 代码块中
	* multiprocessing 模块的 Pool 对象
	* 创建步骤
		1,创建实例对象
			 Pool(4)
				* 参数 int,指定了初始化的线程数量
				* 默认是 CPU 的核数
				* 关键字参数
					processes	# 最大允许同时执行的进程个数
		
		2,多次调用 apply(func,args) API ,使用线程池中的线程,执行任务
		3,调用 close() 关闭进程池,不在接受新的任务
		4,调用 join () 挂起,必须等进程池中所有进程都执行完毕后,主进程才退出

	* API
		close()
			* 线程池关闭 

		join()
			* 对Pool对象调用 join() 方法会等待所有子进程执行完毕
			* 调用 join() 之前必须先调用 close(),调用 close() 之后就不能继续添加新的Process(任务)了
		
		apply(func,args)
			* 它是阻塞的,只有第一个 apply 执行完成后,才会往下走
			* 关键字参数
				func	# 表示要执行的函数
				args	# 表示执行函数时传递的参数,该值是一个 tuple

		apply_async(func,args,callback)
			* 它是非阻塞的且支持结果返回后进行回调
			* 关键字参数
				func	# 表示要执行的函数
				args	# 表示执行函数时传递的参数,该值是一个 tuple
				callback# 当进程执行完后的回调函数,会把进程返回值传递给该函数的第一个参数,该函数由主进程调用
	
	* demo
		from multiprocessing import Process,Pool
		def out(var):
			print(var)
			return "我执行完毕了"

		if __name__ == '__main__':
			pool = Pool(5)
			for x in range(5):
				pool.apply_async(func=out,args=(x,))
			pool.close()
			pool.join()
			# 0 1 2 3 4
				

----------------------------
子进程						|
----------------------------
	* 很多时候,子进程并不是自身,而是一个外部进程
	* 我们创建了子进程后,还需要控制子进程的输入和输出
	* subprocess 模块可以让我们非常方便地启动一个子进程,然后控制其输入和输出
	* 我更多的感觉,它是用来执行 shell/cmd 命令的


----------------------------------
进程之间通信-multiprocessing.Queue|
----------------------------------
	* Process 之间肯定是需要通信的
	* 操作系统提供了很多机制来实现进程间的通信
	* Python的 multiprocessing 模块包装了底层的机制,提供了 Queue,Pipes 等多种方式来交换数据
	* 其实就是 Queue 承载了数据传递的作用,像是一个 MQ,具有消费者和生产者的角色
	* 这个 Queue,其实是把 生产者的数据通过 pickle 序列化后传递了给了 消费者的 Queue
	* 每个数据,只能随机的被一个进程消费
	* 步骤
		1,创建 Queue 实例对象
			q = Queue()

		2,把该对象,传递给进程函数,进程函数可以通过该对象的API进行读写操作
			pw = Process(target=write, args=(q,))
			pr = Process(target=read, args=(q,))
			
	* Demo
		from multiprocessing import Process, Queue
		import os, time, random

		# 写数据进程执行的代码:
		def write(q):
			print('执行写: %s' % os.getpid())
			for value in ['A', 'B', 'C']:
				print('添加   %s 到  queue...' % value)
				q.put(value)
				time.sleep(random.random())

		# 读数据进程执行的代码:
		def read(q):
			print('执行读: %s' % os.getpid())
			while True:
				value = q.get(True)
				print('读取 %s 从  queue.' % value)

		if __name__=='__main__':
			
			# 父进程创建Queue，并传给各个子进程：
			q = Queue()
			pw = Process(target=write, args=(q,))
			pr = Process(target=read, args=(q,))
			# 启动子进程pw，写入:
			pw.start()
			# 启动子进程pr，读取:
			pr.start()
			# 等待pw结束:
			pw.join()
			# pr 进程里是死循环，无法等待其结束，只能强行终止:
			pr.terminate()

--------------------------
multiprocessing.Pipe		|
--------------------------
	* 其实就是:multiprocessing.connection.PipeConnection
	* 是一个双向的通道,可以生产消息,也可以消费消息
	* 创建
		tuple Pipe()
			* 创建一个管道,返回的是一个 tuple
			* 第一个元素
				<multiprocessing.connection.PipeConnection object at 0x03862170>
			* 第二个元素
				<multiprocessing.connection.PipeConnection object at 0x03862250>)
			* 两个元素,就是水管子的两头,可以把任意一头给别人,别人往里面装水,这边就可以读取到
	
	* 方法(multiprocessing.connection.PipeConnection)
		recv()
			* 读取管道的消息
		
		send()
			* 往管道发送消息
			* 该方法会阻塞进程,如果没有数据会一直阻塞
		
		close()
			* 关闭资源
	
	* demo
		from multiprocessing import Process, Pipe,Manager
		parent,child = Pipe()
		def func(p):
			p.send('你好啊')
			p.close()
		if __name__ == '__main__':
			process = Process(target=func,args=(child,))
			process.start()
			
			print(parent.recv())# 你好啊


--------------------------
multiprocessing.Manager		|
--------------------------
	* multiprocessing 模块中的 Pipe 和 Queue,仅仅实现了进程之间的数据通信,并不是数据共享
	* 它是进程安全的,自己有锁机制
	* Manager模块就可以实现数据的共享,进程修改数据后,另一个进程可以读取数据
	* 创建实例对象
		multiprocessing.managers.SyncManager Manager()
	
	* 对象方法
		 multiprocessing.managers.DictProxy manager.dict()
			* 创建一个可以共享数据的字典

         multiprocessing.managers.ListProxy manager.list()
			* 创建一个可以共享数据的list
		
		close()
			* 关闭资源
	* 操作
		1,创建 Manager 实例对象
		2,使用 实例对象,创建需要共享的'容器'数据类型(dict,list...),的实例
		4,把'容器',传递给多进程的方法,每个进程之间就可以进行数据的共享操作

	* Demo
		from multiprocessing import Process,Manager
		import threading

		def func(d,l):
			d['name'] = 'kevin'
			l.append(threading.current_thread().getName())
			
		if __name__ == '__main__':
			with Manager() as manager:
				d = manager.dict()
				l = manager.list(range(5))
				
				p_arr = []
				
				for i in range(3):
					p = Process(target=func,args=(d,l))
					p.start()
					p_arr.append(p)
				
				for p in p_arr:
					p.join()
					
				print(d)    # {'name': 'kevin'}
				print(l)    # [0, 1, 2, 3, 4, 'MainThread', 'MainThread', 'MainThread']

--------------------------
multiprocessing.Lock		|
--------------------------
	* 进程锁
	* 进程之间的数据相互独立的,这个锁存在的意义是:屏幕共享...N多个进程,都使用同一个屏幕
	* 使用
		1,创建锁实例对象
		2,把锁实例对象传递给进程函数
		3,在子进程中操作锁
	* 创建实例
		Lock()
	* api
		acquire()
			* 上锁
		
		release()
			* 释放锁
	
----------------------------
多进程-总结					|
----------------------------
	* 原生的进程
	* 每个进程之间内存相互独立,也就是说无法通信,通信需要通过:multiprocessing.Queue /multiprocessing.Pipe 来完成
	* 进程指教的数据共享是通过 multiprocessing.Manager 来完成
	* 在 windows 系统中启动多进程(包括进程池),必须在如下代码块中
		if __name__ == '__main__':
			multiprocessing.freeze_support()
			pass
	

