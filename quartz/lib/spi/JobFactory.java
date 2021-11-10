----------------------
JobFactory
----------------------
	# Job实例工场接口: public interface JobFactory
		Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException;
	
	# 系统提供的实现
		AdaptableJobFactory
			|-SpringBeanJobFactory
		SimpleJobFactory
			|-PropertySettingJobFactory
	

	# PropertySettingJobFactory
		* 会自动把 JobDataMap 中的属性值，自动注入到 Jon 实现中的同名属性


----------------------
TriggerFiredBundle
----------------------
	# 描述了Job和Trgger的一些信息
		public JobDetail getJobDetail()
			* 获取到JonDetail

		public OperableTrigger getTrigger() 
		public Calendar getCalendar()
		public boolean isRecovering()
		public Date getFireTime()
		public Date getNextFireTime() 
		public Date getPrevFireTime()
		public Date getScheduledFireTime()