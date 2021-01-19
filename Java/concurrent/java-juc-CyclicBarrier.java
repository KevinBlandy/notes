------------------------
CyclicBarrier			|
------------------------
	# (循环栅栏)和 CountDownLatch 非常类似,它也可以实现线程间的技术等待
	# 但是它的功能比 CountDownLatch 更加复杂和强大,主要应用场景和 CountDownLatch 类似
		* 可以用于多线程计算数据,最后合并计算结果的应用场景

	# 和 CountDownLatch 的区别
		CountDownLatch:一个或者多个线程,等待其他多个线程完成某件事情之后才能执行
			* 重点是一个线程(多个线程_等待,而其他的N个线程在完成某件事情之后,可以终止,也可以等待
			* 不能重复使用

		CyclicBarrier:多个线程互相等待,直到到达同一个同步点,再继续一起执行
			* 重点是多个线程,在任意一个线程没有完成,所有的线程都必须等待
			* 可以重复使用

	# 构建方法
		CyclicBarrier(int parties)
		CyclicBarrier(int parties, Runnable barrierAction)

		* 需要同步的线程数量:parties
		* barrierAction 线程到达屏障(parties)时,优先执行barrierAction(插队的,而且不会计算parties)
	
	# 实例方法
		int await()
			* 当前线程等待
			* 当有 parties 个线程都处于await() 状态时,大家一起唤醒

		int await(long timeout, TimeUnit unit)

		int getNumberWaiting()
			* 获取正在 await() 的线程数量

		int getParties()

		boolean isBroken()
	
		void reset()
			* 重置
	

------------------------
Demo					|
------------------------
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5,() -> {
			System.out.println("====================紧急执行====================");
		}) ;
		
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		
		for(int x = 0 ; x < 10 ; x++) {
			
			final int numer = x;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			threadPool.execute(() -> {
				try {
					System.out.println("线程:" + numer + " await");
					cyclicBarrier.await();
					System.out.println("线程:" + numer + " notify");
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}); 
		}
		
		threadPool.shutdown();
	}
}
/**
线程:0 await
线程:1 await
线程:2 await
线程:3 await
线程:4 await
====================紧急执行====================
线程:4 notify
线程:0 notify
线程:2 notify
线程:1 notify
线程:3 notify
线程:5 await
线程:6 await
线程:7 await
线程:8 await
线程:9 await
====================紧急执行====================
线程:9 notify
线程:5 notify
线程:7 notify
线程:6 notify
线程:8 notify
**/