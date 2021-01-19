------------------------
Callable				|
------------------------
	# 是一个接口
	# 创建执行线程的方式三
	# 源码
		@FunctionalInterface
		public interface Callable<V> {
			V call() throws Exception;
		}

	# 与实现 Runnable 区别
		* 有返回值
		* 是一个泛型接口
		* 抛出了异常
		* 需要 FutureTask(Future接口实现) 支持(用于接收运算结果)

	
	# 其实就是线程结束的时候,可以获取到一个返回值
	# Demo	
		public class Main implements Callable<Integer>{
			@Override
			public Integer call() throws Exception {
				Integer sum = 0;
				for(int x = 0; x < 10; x++){
					sum += x;
				}
				return sum;
		}
		public static void main(String[] args) throws Exception{
			//创建 callable 实例
			Main main = new Main();
			//创建 futuretask
			FutureTask<Integer> futureTask = new FutureTask<Integer>(main);
			//启动线程
			new Thread(futureTask).start();
			//得到结果,会一直阻塞,直到程序执行完成
			Integer result = futureTask.get();
			System.out.println(result);
		}
	}
