

--------------------------------
Job								|
--------------------------------
	# 任务的执行接口
		void execute(JobExecutionContext context)   throws JobExecutionException;
	
	# 需要实现类提供一个无参构造器

	# 无状态的Job
		* 每次执行, 都会创建新的 Job 对象以及 JobDataMap 对象执行
		* 不要存储设置实例属性
	
	# 有状态的Job
		* 在 Job 的实现接口上添加注解: @PersistJobDataAfterExecution
		* 每次执行, 都会创建新的Job对象, 但是不会创建新的 JobDataMap
		* 那么可以通过 JobDataMap 存储一些状态 
		
	# Job实现的自动注入
		* 使用默认的JobFactory: 'org.quartz.simpl.PropertySettingJobFactory' 
		* 会在Job对象创建完毕后, 调用其setter方法, 注入在 JobDataMap 中的属性到实例

--------------------------------
JobExecutionContext				|
--------------------------------
	# job执行时的上下文环境

	# 接口方法
		public Scheduler getScheduler();
		public Calendar getCalendar();
		public boolean isRecovering();
			* 如果一个job是可恢复的, 并且在其执行的时候, scheduler发生硬关闭(hard shutdown), 比如运行的进程崩溃了，或者关机了)
			* 则当scheduler重新启动的时候, 该job会被重新执行
			* 此时, 该job的 JobExecutionContext.isRecovering() 返回true


		public TriggerKey getRecoveringTriggerKey() throws IllegalStateException;
		public int getRefireCount();
		public JobDataMap getMergedJobDataMap();
			* 把JobDetail中的JobDataMap 和 Trigger中的JobDataMap 合并后返回
			* 如果存在相同的数据, 则后者会覆盖前者的值

		public JobDetail getJobDetail();
		public Job getJobInstance();
		public Date getFireTime();
		public Date getScheduledFireTime();
		public Date getPreviousFireTime();
		public Date getNextFireTime();
		public String getFireInstanceId();
		public Object getResult();
		public void setResult(Object result);
		public long getJobRunTime();
		public void put(Object key, Object value);
		public Object get(Object key);

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