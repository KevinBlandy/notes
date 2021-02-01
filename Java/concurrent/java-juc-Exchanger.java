--------------------------
Exchanger
--------------------------
	# 用于线程间协作的工具类。
		* 用于进行线程间的数据交换。
		* 它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
		* 这两个线程通过exchange方法交换数据，
		* 如果第一个线程先执行exchange()方法，它会一直等待第二个线程也执行exchange方法，
		* 当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。
	
	# 构造方法
		public Exchanger()
	
	# 实例方法
		public V exchange(V x) throws InterruptedException
		public V exchange(V x) throws InterruptedException
		public V exchange(V x, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException 
	

	# Demo
		import java.util.concurrent.Exchanger;
		import java.util.concurrent.TimeUnit;

		public class Main {
			public static void main(String[] args) throws InterruptedException {
				Exchanger<String> exchanger = new Exchanger<>();
				Thread t1 = new Thread(() -> {
					int i = 1;
					while (true) {
						System.out.println("线程1：" + i ++);
						try {
							TimeUnit.SECONDS.sleep(1);
							if (i == 3) {
								// 阻塞，等待和线程2交易
								String value = exchanger.exchange("我是线层1的数据");
								System.out.println("线程1收到了线程2的数据:" + value);
								break;
							}
						} catch (InterruptedException e) {
						}
					}
				});
				t1.start();
				
				Thread t2 = new Thread(() -> {
					int i = 1;
					while (true) {
						System.out.println("线程2：" + i ++);
						try {
							TimeUnit.SECONDS.sleep(1);
							if (i == 2) {
								// 阻塞，等待和线程1交易
								String value = exchanger.exchange("我是线层2的数据");
								System.out.println("线程2收到了线程1的数据:" + value);
								break;
							}
						} catch (InterruptedException e) {
						}
					}
				});
				t2.start();
			}
		}
		
		// ----------- 输出
		线程1：1
		线程2：1
		线程1：2
		线程1收到了线程2的数据:我是线层2的数据
		线程2收到了线程1的数据:我是线层1的数据
