
# 重入
	* Foo线程已经获取了a锁,Foo再次获取a锁,是OK可以成功获取到的,这个就是重入
		class Bar{
			public synchronized void test() {
			}
		}
		public class Foo extends Bar{
			public synchronized void test() {
				/*
					当前线程已经持有了this这把锁,访问父类的方法,如果没有重入机制,那么就会一直阻塞.因为this锁已经被持有了

				*/
				super.test();		
			}
		}
	
	* 重入的实现
		1,为这个锁设置了一个计数器,当有一个线程持有了这个锁的时候,计数器 +1
		2,当线程退出同步代码块的时候,计数器 -1,当计数器为0的时候,这个锁将会被释放
	

# 非原子性的64位操作
	* jvm规定,变量的声明赋值,是一个原子操作,但是存在一个例外
	* 非 volatile 类型的64位数值变量(double & long),jvm允许把64位的读和写操作分为两个32位的读和写
	* 当读取一个非 volatile 的 long 时,读写不在一个线程,那么很可能读到某个值的高32位或者低32位
	* 在多线程环境下,使用共享且可变的 long,double 都是有安全问题的,应该加锁或者使用 volatile 关键字声明


# 同步容器类要遵守同步策略
	* 支持客户端加锁
	* 问题
		public static Object getLast(Vector<String> vector) {
			int index = vector.size();
			return vector.get(index - 1);
		}
		public static Object removeLast(Vector<String> vector) {
			int index = vector.size();
			return vector.remove(index - 1);
		}
		A -> size = 10 -> removeLast()  
		b -> size = 10	-> 失去执行权 ->  getLast();		//异常数组越界
	* 解决,客户端加锁
		public static Object getLast(Vector<String> vector) {
			synchronized (vector){
				int index = vector.size();
				return vector.get(index - 1);
			}
			
		}
		public static Object removeLast(Vector<String> vector) {
			synchronized (vector){
				int index = vector.size();
				return vector.remove(index - 1);
			}
		}
	

# 如果不希望在迭代期间对数据加锁,那么一种替代方法就是克隆容器,并在副本上进行迭代
	* 由于副本封闭在线程内,因此其他线程不会在迭代期间对其进行修改
	* 克隆的过程也是需要加锁的

# 容器类的hashCode和equals,toString(),contains....等等也会间接性的执行迭代操作
	* 这些间接的迭代操作都有可能会抛出 ConcurrentModificationException

# 一些类库
	Queue
		BlockingQueue
			LinkedBlockingDeque
			ArrayBlockingQueue
			PriorityBlockingQueue
			SynchronousQueue
		ConcurrentLinkedQueue
		LinkedList
		PriorityQueue

	Deque
		BlockingDeque
			LinkedBlockingDeque
		ArrayDeque
	
	Map
		ConcurrentHashMap
		ConcurrentSkipListMap
	
	Set
		ConcurrentSkipListSet
		CopyOnWriteArraySet
	
	List
		CopyOnWriteArrayList
	
	AtomicReference
	Semaphore
	CountDownLatch
	FutureTask
	CyclicBarrier
	CompletionService
		ExecutorCompletionService
	Executor
		ExecutorService
		ThreadPoolExecutor
		ScheduledExecutorService
		ScheduledThreadPoolExecutor
		