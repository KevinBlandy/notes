--------------------------------
JobExecutionContext	
--------------------------------
	# job执行时的上下文环境接口: public interface JobExecutionContext

	# 接口方法
		public Scheduler getScheduler();
		public Calendar getCalendar();
		public boolean isRecovering();
			* 是否是恢复执行
			* 如果一个job是可恢复的, 并且在其执行的时候, scheduler发生硬关闭(hard shutdown), 比如运行的进程崩溃了，或者关机了)
			* 则当scheduler重新启动的时候, 该job会被重新执行此时, 该job的 JobExecutionContext.isRecovering() 返回true


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