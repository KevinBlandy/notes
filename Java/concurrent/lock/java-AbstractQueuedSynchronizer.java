----------------------------
AbstractQueuedSynchronizer	|
----------------------------
	# 抽象队列同步器, 传说中的 AQS, 抽象类
		AbstractQueuedSynchronizer extends AbstractOwnableSynchronizer implements java.io.Serializable

	# 构造函数
		protected AbstractQueuedSynchronizer() { }
	
----------------------------
 CLH队列(线程同步队列)		|
----------------------------
	# 队列的Node
		static final class Node {
			static final Node SHARED = new Node();
			static final Node EXCLUSIVE = null;

			static final int CANCELLED =  1;
			static final int SIGNAL    = -1;
			static final int CONDITION = -2;
			static final int PROPAGATE = -3;

			volatile int waitStatus;
				 * 表示节点的状态
					CANCELLED:	值为 1, 表示当前的线程被取消
					SIGNAL:		值为-1, 表示当前节点的后继节点包含的线程需要运行, 也就是unpark
					CONDITION:	值为-2, 表示当前节点在等待condition, 也就是在condition队列中
					PROPAGATE:	值为-3, 表示当前场景下后续的acquireShared能够得以执行
					0(初始值):	表示当前节点在sync队列中, 等待着获取锁

			volatile Node prev;
			volatile Node next;
				* 双向链表的前后节点

			volatile Thread thread;
				* 入队列时的线程

			Node nextWaiter;
				* 存储condition队列中的后继节点

			//是否为共享模式
			final boolean isShared() {
				return nextWaiter == SHARED;
			}

			// 返回前一个节点
			final Node predecessor() throws NullPointerException {
				Node p = prev;
				if (p == null)
					throw new NullPointerException();
				else
					return p;
			}
			//用于建立初始头或SHARED标记
			Node() {}    
			// 使用在addWaiter方法中
			Node(Thread thread, Node mode) {
				this.nextWaiter = mode;
				this.thread = thread;
			}
			// 使用在Condition条件中
			Node(Thread thread, int waitStatus) {
				this.waitStatus = waitStatus;
				this.thread = thread;
			}
		}

	
		* Node 可以包含两个队列: next/prev 双向链表, 和 nextWaiter 单向链表
	
