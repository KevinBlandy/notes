----------------
Queue			|
----------------
	# java5.0增加的两类安全队列
		1,Queue
			
		2,BlockingQueue
	
----------------
Queue			|
----------------
	# 是一个队列接口
	# 用来保存临时数据
	# 提供了很多实现
		ConcurrentLinkedQueue
			* 传统的先进先出的队列
		LinkedList
		PriorityQueue
			* 优先队列
	
	# Queue 的操作都不会阻塞队列
	# 如果元素为空,那么获取操作返回null

----------------
BlockingQueue	|
----------------
	# 扩展了 Queue,提供可阻塞的插入和获取操作
	# 在生产者和消费者模型中,这个很重要