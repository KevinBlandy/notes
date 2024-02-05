------------------------
FutureTask<V> 
------------------------
	# FutureTask<V> implements RunnableFuture<V> 
	
	# 构造方法
		public FutureTask(Callable<V> callable)
		public FutureTask(Runnable runnable, V result)
			* Runnable 作为参数，可以用于无返回值的任务场景

				FutureTask<Void> futureTask = new FutureTask<Void>(() -> {
					// TODO 任务
				}, null);
	
	# 实例方法
		public boolean isCancelled()
		public boolean isDone()
		public boolean cancel(boolean mayInterruptIfRunning) 
		public V get() throws InterruptedException, ExecutionException 
		public V get(long timeout, TimeUnit unit)
		public void run()


------------------------
FutureTask<V> 
------------------------
	# 实现超时任务取消

		package app.test;

		import java.util.concurrent.ExecutionException;
		import java.util.concurrent.FutureTask;
		import java.util.concurrent.TimeUnit;
		import java.util.concurrent.TimeoutException;

		public class ApplicationMainTest {

			public static void main(String[] args)  {
				
				FutureTask<Void> futureTask = new FutureTask<Void>(() -> {
					
					while (!Thread.interrupted()) {
						try {
							System.out.println("执行任务");
							Thread.sleep(1000L);
						} catch (InterruptedException e) {
						
							// 句柄中断了线程
							System.out.println("线程中断");
							
							// 中断当前线程
							Thread.currentThread().interrupt();
						}
					}
					System.out.println("执行结束");
					
				}, null);

				// 子线程开始执行
				Thread thread = new Thread(futureTask);
				thread.start();
				
				Void ret = null;
				
				try {
					// 获得执行结果，设置超时时间为 2 秒
					ret = futureTask.get(2, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					System.out.println("中断");
				} catch (ExecutionException e) {
					System.out.println("异常");
				} catch (TimeoutException e) {
					System.out.println("超时");
					
					// 超时了，尝试取消任务，即中断线程
					futureTask.cancel(true);
				}
				
				// 获得结果
				System.out.println(ret);
				// 是否被取消了任务
				System.out.println(futureTask.isCancelled());
			}
		}