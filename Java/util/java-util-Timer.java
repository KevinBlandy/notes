-------------------------
java.util.Timer			 |
-------------------------
	# 定时任务执行器
	# 异步任务

-------------------------
实例方法/字段			|
-------------------------

	schedule(TimerTask task, Date time) ;
		* 在指定的时间执行任务
	
	schedule(TimerTask task, Date firstTime, long period) 
		* 在指定时间(firstTime)开始执行,每隔 period 毫秒重复执行一次
	
	schedule(TimerTask task, long delay) 
        * 安排在指定延迟后执行指定的任务。
	
	schedule(TimerTask task, long delay, long period) 
        * 安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。
	
	scheduleAtFixedRate(TimerTask task, Date firstTime, long period) 
        * 安排指定的任务在指定的时间开始进行重复的固定速率执行。
		
	scheduleAtFixedRate(TimerTask task, long delay, long period) 
        * 安排指定的任务在指定的延迟后开始进行重复的固定速率执行。 

	Timer timer = new Timer();
	timer.schedule(new TimerTask(){
		@Override
		public void run() {
			System.out.println("俩秒打印一次");
		}
	},new Date(),2000);


-------------------------
静态方法/字段			|
-------------------------