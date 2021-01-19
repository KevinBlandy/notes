------------------------------------
Queue								|
------------------------------------
	# 体系
		Queue(java.util)
			|-Deque
				|-ArrayDeque
				|-ConcurrentLinkedDeque
			|-BlockingQueue
			|-AbstractQueue
				|-PriorityQueue
				|-DelayQueue
				|-SynchronousQueue
				|-PriorityBlockingQueue
				|-LinkedTransferQueue
				|-LinkedBlockingDeque
				|-ArrayBlockingQueue
					* 才用数组结构实现
					* 入队出队采用一把锁,导致入队出队相互阻塞,效率低下

				|-LinkedBlockingQueue
					* 采用单链表的形式实现
					* 采用两把锁的锁分离技术实现入队出队互不阻塞
					* 是有界队列,不传入容量时默认为最大int值

	
	# 常用的一些Queue
		+----------------------+----------------+-----------+-----------------+
		|队列				   |	加锁方式	|	是否有界|	数据结构
		+----------------------+----------------+-----------+-----------------+
		|ArrayBlockingQueue	   |加锁			|有界		|ArrayList		  |
		+----------------------+----------------+-----------+-----------------+
		|LinkedBlockingQueue   |加锁			|无界		|LinkedList		  |
		+----------------------+----------------+-----------+-----------------+
		|ConcurrentLinkedQueue |CAS				|无界		|LinkedList		  |
		+----------------------+----------------+-----------+-----------------+
		|ConcurrentLinkedDeque |CAS				|无界		|				  |
		+----------------------+----------------+-----------+-----------------+
		|LinkedTransferQueue   |CAS				|无界		|LinkedList		  |
		+----------------------+----------------+-----------+-----------------+
		|PriorityBlockingQueue |													
		+----------------------+----------------+-----------+-----------------+
		|DelayQueue            |
		+----------------------+----------------+-----------+-----------------+
		|SynchronousQueue	   |
		+----------------------+----------------+-----------+-----------------+
------------------------------------
Queue								|
------------------------------------
	# Queue 接口, 抽象出了基本的操作
		public interface Queue<E> extends Collection<E> {
			boolean add(E e);

			boolean offer(E e);

			E remove();

			E poll();

			E element();

			E peek();
		}
