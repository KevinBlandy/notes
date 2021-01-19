----------------------------
queue						|
----------------------------
	* Python自带的消息队列模块
	* 模块中的消息队列类
		Queue
		LifoQueue
		PriorityQueue
		

----------------------------
queue-模块属性				|
----------------------------

----------------------------
queue-模块函数				|
----------------------------
	Queue(maxsize=0)
		* 先进先出
		* 创建队列,maxsize 指定最大的消息个数

	LifoQueue(maxsize=0)
		* 后进后出
		* 创建队列,maxsize 指定最大的消息个数

	PriorityQueue(maxsize=0)
		* 可以设置消息优先级的队列
		* 创建队列,maxsize 指定最大的消息个数

----------------------------
queue.Queue					|
----------------------------
	* 实例API
		None put(item, block=True, timeout=None)
			* 添加数据到消息队列
			* 如果队列已经存满了,该方法会阻塞
			* 默认参数
				block:该值默认是 True,如果是 False,那么在执行put的时候,如果队列满了,则抛出异常不阻塞
		bool full()
			* 该队列是否已经装满了数据
		bool empty()
			* 该队列是否是空队列
		int qsize()
			* 返回消息长度
		item get(block=True, timeout=None)
			* 消费数据,该线程会阻塞,直到收到数据为止
			* 默认参数
				block:该值默认是 True,如果是 False,那么在执行get的时候,如果队列没有数据,则抛出异常不阻塞

		item get_nowait()
			* 消费数据,线程不会阻塞,如果没有数据会抛出异常

----------------------------
queue.LifoQueue				|
----------------------------
	* 实例API
----------------------------
queue.PriorityQueue			|
----------------------------
	* 实例API
		None put(tuple)
			* 参数是一个元组
			* 第一个元素就是 int 类型的权重 ,第二个元素是 item 
		
		tuple get(block=True, timeout=None)
			* 消费数据,该线程会阻塞,直到收到数据为止
			* 权重越小的,就会越先被消费

	
	* demo
		import queue
		q = queue.PriorityQueue()
		q.put((1,'Kevin'))
		q.put((9,'Litch'))
		q.put((5,'Rocco'))
		print(q.get())# (1, 'Kevin')
		print(q.get())# (5, 'Rocco')
		print(q.get())# (9, 'Litch')


