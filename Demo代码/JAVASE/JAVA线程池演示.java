import java.util.*;
import java.util.concurrent.*;
public class Demo
{
	public static void main(String[] args)
	{
		//创建线程池对象,后面的参数是约束。要控制的线程数量
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.submit(new Runnable(){public void run(){}});//匿名内部类实现Runnable接口的子类-线程1
		pool.submit(new Runnable(){public void run(){}});//匿名内部类实现Runnable接口的子类-线程2
		pool.shutdown();//关闭线程池。不接受新的线程以及任务。
	}
}