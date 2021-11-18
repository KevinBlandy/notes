-----------------------
Job
-----------------------
	# 任务执行接口 public interface Job 

		void execute(JobExecutionContext context) throws JobExecutionException;
	
		


--------------------------------
JobFactory						|
--------------------------------
	# 创建 Job 实例的工厂类接口
	# 默认使用的工厂实现
		* 仅仅只是调用了 newInstance() 方法
		* 然后从尝试调用实例的JobDataMap中的key的setter方法

	# 工厂接口方法
		Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException;
	
	# 可以从 TriggerFiredBundle 对象获取到一些方法
		private JobDetail job;
		private OperableTrigger trigger;
		private Calendar cal;
		private boolean jobIsRecovering;
		private Date fireTime;
		private Date scheduledFireTime;
		private Date prevFireTime;
		private Date nextFireTime;