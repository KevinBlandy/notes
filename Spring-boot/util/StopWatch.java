----------------------------
StopWatch					|
----------------------------
	# 可以用来分析任务耗时的工具
		* 线程不安全, 而且应该是测试环境使用, 而不是生产环境

	# 构造函数
		StopWatch()
		StopWatch(String id)

		id
			* 每个实例可以设置一个唯一的id
	
	# 实例函数
		String getId()
		TaskInfo getLastTaskInfo()
			* 获取最后一个执行的任务

		String getLastTaskName()
		long getLastTaskTimeMillis()
		int getTaskCount()
			* 获取执行任务的次数

		TaskInfo[] getTaskInfo()
		long getTotalTimeMillis()
			* 获取所有任务执行的总耗时, 毫秒

		double getTotalTimeSeconds()
			* 获取所有任务执行的总耗时, 秒 

		boolean isRunning()
		String prettyPrint()
		void setKeepTaskList(boolean keepTaskList)
			* 是否保存每个执行过的任务

		String shortSummary()
		void start()
		void start(String taskName)
		void stop() 
		String toString()
	
	# 内部静态类:TaskInfo

		* 封装了每个任务的名称, 以及耗时信息

		TaskInfo(String taskName, long timeMillis)

		 String getTaskName()
			* 获取任务的名称

		 long getTimeMillis()
		 double getTimeSeconds()
			* 当前任务的耗费时间
		
----------------------------
Demo						|
----------------------------

import org.springframework.util.StopWatch;

public class Main {
	public static void main(String[] args) throws Throwable{
		StopWatch stopWatch = new StopWatch("id");

		// 开始第一个任务
		stopWatch.start("task1");
		Thread.sleep(2000L);
		stopWatch.stop();

		// 开始第二个任务
		stopWatch.start("task2");
		Thread.sleep(3000L);
		stopWatch.stop();

		String prettyPrint = stopWatch.prettyPrint();

		System.out.println(prettyPrint);

		System.out.println(stopWatch);

	}
}
/*

StopWatch 'id': running time (millis) = 5000
-----------------------------------------
ms     %     Task name
-----------------------------------------
02000  040%  task1
03000  060%  task2


StopWatch 'id': running time (millis) = 5001; [task1] took 2000 = 40%; [task2] took 3001 = 60%
*/