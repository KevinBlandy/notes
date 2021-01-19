
1,继承 Thread

	public class Main extends Thread {
		@Override
		public void run() {
			//Thread-0
			System.out.print(Thread.currentThread().getName());
		}
		public static void main(String[] args) {
			new Main().start();
		}
	}

2,实现 Runnable

	public class Main implements Runnable {
		@Override
		public void run() {
			//Thread-0
			System.out.print(Thread.currentThread().getName());
		}
		public static void main(String[] args) {
			new Thread(new Main()).start();
		}
	}

3,实现 Callable
	* 能够获取到线程的返回值

	import java.util.concurrent.Callable;
	import java.util.concurrent.ExecutionException;
	import java.util.concurrent.FutureTask;
	public class Main implements Callable<String> {
		@Override
		public String call() throws Exception {
			return Thread.currentThread().getName();
		}
		public static void main(String[] args) throws InterruptedException, ExecutionException {
			//通过 Callable 实例,构造 FutureTask 实例
			FutureTask<String> futureTask = new FutureTask<>(new Main());
			//通过 FutureTask 实例,构造 Thread实例,并且启动线程
			new Thread(futureTask).start();
			//FutureTask 实例的get(),会一直阻塞当前线程,直到FutureTask的线程返回结果
			String result = futureTask.get();
			System.out.println(result);
		}
	}