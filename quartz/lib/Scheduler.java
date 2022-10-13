---------------------------
Scheduler
---------------------------
	# 调度器核心接口
		public interface Scheduler 
	
	# 方法
		String DEFAULT_GROUP = Key.DEFAULT_GROUP;
		String DEFAULT_RECOVERY_GROUP = "RECOVERING_JOBS";
		String DEFAULT_FAIL_OVER_GROUP = "FAILED_OVER_JOBS";
		String FAILED_JOB_ORIGINAL_TRIGGER_NAME =  "QRTZ_FAILED_JOB_ORIG_TRIGGER_NAME";
		String FAILED_JOB_ORIGINAL_TRIGGER_GROUP =  "QRTZ_FAILED_JOB_ORIG_TRIGGER_GROUP";
		String FAILED_JOB_ORIGINAL_TRIGGER_FIRETIME_IN_MILLISECONDS =  "QRTZ_FAILED_JOB_ORIG_TRIGGER_FIRETIME_IN_MILLISECONDS_AS_STRING";
		String FAILED_JOB_ORIGINAL_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS =  "QRTZ_FAILED_JOB_ORIG_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS_AS_STRING";

		String getSchedulerName() throws SchedulerException;
		String getSchedulerInstanceId() throws SchedulerException;

		SchedulerContext getContext() throws SchedulerException;
			* 获取上下文对象（实现了Map）, 就是一个Map结构, String 为Key
			* 除了继承Map外，没其他属性了。
		
		void start() throws SchedulerException;
		void startDelayed(int seconds) throws SchedulerException;
			* 立即启动/在指定秒后启动

		boolean isStarted() throws SchedulerException;
			* 判断是否启动

		void standby() throws SchedulerException;
			* 暂停任务的执行, 如果需要继续执行, 执行方法: start();

		boolean isInStandbyMode() throws SchedulerException;
			* 判断当前的状态是否是暂停的
		
		void shutdown() throws SchedulerException;
		void shutdown(boolean waitForJobsToComplete)throws SchedulerException;
			 * 关闭应用
			 waitForJobsToComplete 控制是否要等待正在执行的任务执行完毕

		boolean isShutdown() throws SchedulerException;
			* 判断是否已经关闭

		SchedulerMetaData getMetaData() throws SchedulerException;

		List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException;
			* 获取正在执行的任务列表

		void setJobFactory(JobFactory factory) throws SchedulerException;
			* 设置 Job 实例的创建工厂类
		
		ListenerManager getListenerManager()  throws SchedulerException;
			* 返回监听器管理器
		
		Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException;
			* 添加任务，并且开始执行，指定了trigger的job
			* trigger本身，此时不需要指定job

		Date scheduleJob(Trigger trigger) throws SchedulerException;
			* 调度已经存储的作业，必须要在 trigger 中指定job

		void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException;
		void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggersForJob, boolean replace) throws SchedulerException;

		boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException;
		boolean unscheduleJobs(List<TriggerKey> triggerKeys) throws SchedulerException;
			* 取消指定trigger的作业
		
		Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger)  throws SchedulerException;
			* 重新为指定的trigger指定新的Trigger（替换）

		void addJob(JobDetail jobDetail, boolean replace) throws SchedulerException;
		void addJob(JobDetail jobDetail, boolean replace, boolean storeNonDurableWhileAwaitingScheduling) throws SchedulerException;
			* 添加Job，以供后面使用
			* Job必须设置: storeDurably(true), 不然会异常

			replace
				* 如果已经有同名JOB已经存在，是否替换
				* 默认不让有同名Job，会异常

			storeNonDurableWhileAwaitingScheduling
			
		boolean deleteJob(JobKey jobKey) throws SchedulerException;
		boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException;
			* 停止并且删除作业


		void triggerJob(JobKey jobKey) throws SchedulerException;
		void triggerJob(JobKey jobKey, JobDataMap data) throws SchedulerException;
			* 手动触发指定的Job

		void pauseJob(JobKey jobKey) throws SchedulerException;
		void pauseJobs(GroupMatcher<JobKey> matcher) throws SchedulerException;
			* 暂停指定的Job

		void pauseTrigger(TriggerKey triggerKey) throws SchedulerException;
		void pauseTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException;
			* 暂停指定的Trigger

		void resumeJob(JobKey jobKey) throws SchedulerException;
		void resumeJobs(GroupMatcher<JobKey> matcher) throws SchedulerException;
			* 恢复指定的Job

		void resumeTrigger(TriggerKey triggerKey) throws SchedulerException;
		void resumeTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException;
			* 恢复指定的Trigger

		void pauseAll() throws SchedulerException;
		void resumeAll() throws SchedulerException;
			* 暂停/恢复所有

		List<String> getJobGroupNames() throws SchedulerException;
			* 获取所有Job的Group名称

		Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws SchedulerException;
			* 根据匹配器获取到所有的Job

		List<? extends Trigger> getTriggersOfJob(JobKey jobKey) throws SchedulerException;
			* 获取指定Job上的所有的Trigger

		List<String> getTriggerGroupNames() throws SchedulerException;	
			* 获取所有Trigger的Group名称

		Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) throws SchedulerException;
			* 根据Mapcher获取到Trigger

		Set<String> getPausedTriggerGroups() throws SchedulerException;

		JobDetail getJobDetail(JobKey jobKey) throws SchedulerException;
			* 获取JobDetail

		Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException;
			* 获取Trigger

		TriggerState getTriggerState(TriggerKey triggerKey) throws SchedulerException;
			* 获取Trigger状态，枚举

		void resetTriggerFromErrorState(TriggerKey triggerKey) throws SchedulerException;

		void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers) throws SchedulerException;
		boolean deleteCalendar(String calName) throws SchedulerException;
			* 添加/删除一个日期对象
		
		Calendar getCalendar(String calName) throws SchedulerException;
		List<String> getCalendarNames() throws SchedulerException;
		boolean interrupt(JobKey jobKey) throws UnableToInterruptJobException;
		boolean interrupt(String fireInstanceId) throws UnableToInterruptJobException;
		boolean checkExists(JobKey jobKey) throws SchedulerException; 
		boolean checkExists(TriggerKey triggerKey) throws SchedulerException;
		void clear() throws SchedulerException;

