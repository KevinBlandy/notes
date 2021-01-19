
----------------------------
Scheduler					|
----------------------------
	# 接口方法
		Calendar getCalendar(String calName)
		boolean deleteCalendar(String calName) 
		void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers)
			* 添加/删除一个日期对象

		void addJob(JobDetail jobDetail, boolean replace)
		void addJob(JobDetail jobDetail, boolean replace, boolean storeNonDurableWhileAwaitingScheduling)
			* 添加一个trigger和job关联
			* replace: 
			* storeNonDurableWhileAwaitingScheduling: 

		boolean checkExists(JobKey jobKey) 
		boolean checkExists(TriggerKey triggerKey)

		void clear() 
		
		boolean deleteJob(JobKey jobKey)
		boolean deleteJobs(List<JobKey> jobKeys)

		

		List<String> getCalendarNames()

		SchedulerContext getContext()
			* 获取上下文对象, 就是一个Map结构, String 为Key

		void start()
			* 启动

		void startDelayed(int seconds) 
			* 在指定秒后启动

		boolean isStarted()
			* 判断是否启动

		void standby()
			* 暂停任务的执行, 如果需要继续执行, 执行方法: start();

		boolean isInStandbyMode()
			* 判断当前的状态是否是暂停的

		void shutdown()
		void shutdown(boolean waitForJobsToComplete)
			* 关闭应用, waitForJobsToComplete 控制是否要等待正在执行的任务执行完毕

		boolean isShutdown()
			* 判断是否已经关闭

		SchedulerMetaData getMetaData()
		List<JobExecutionContext> getCurrentlyExecutingJobs()
		void setJobFactory(JobFactory factory)
			* 设置 Job 实例的创建工厂类

		ListenerManager getListenerManager()
			* 返回监听器管理器

		Date scheduleJob(JobDetail jobDetail, Trigger trigger)
		Date scheduleJob(Trigger trigger)
		void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) 
		void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggersForJob, boolean replace)

		boolean unscheduleJob(TriggerKey triggerKey)
		boolean unscheduleJobs(List<TriggerKey> triggerKeys)
			* 移除指定的Trigger以及job

		Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger)

		void addJob(JobDetail jobDetail, boolean replace)
		void addJob(JobDetail jobDetail, boolean replace, boolean storeNonDurableWhileAwaitingScheduling)
			* 添加job到 Scheduler, 以备使用

		boolean deleteJob(JobKey jobKey)
		boolean deleteJobs(List<JobKey> jobKeys)
			* 移除指定的join

		void triggerJob(JobKey jobKey)
		void triggerJob(JobKey jobKey, JobDataMap data)
		void pauseJob(JobKey jobKey)
		void pauseJobs(GroupMatcher<JobKey> matcher)
		void pauseTrigger(TriggerKey triggerKey)
		void pauseTriggers(GroupMatcher<TriggerKey> matcher) 
		void resumeJob(JobKey jobKey)
		void resumeJobs(GroupMatcher<JobKey> matcher)
		void resumeTrigger(TriggerKey triggerKey)
		void resumeTriggers(GroupMatcher<TriggerKey> matcher) 
		void pauseAll()
		void resumeAll()
		List<String> getJobGroupNames() 
		Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher)
		List<? extends Trigger> getTriggersOfJob(JobKey jobKey)
		List<String> getTriggerGroupNames()
		Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher)
		Set<String> getPausedTriggerGroups()
		JobDetail getJobDetail(JobKey jobKey)
		Trigger getTrigger(TriggerKey triggerKey)
		TriggerState getTriggerState(TriggerKey triggerKey)
		void resetTriggerFromErrorState(TriggerKey triggerKey)
		void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers)
		boolean deleteCalendar(String calName)
		Calendar getCalendar(String calName)
		List<String> getCalendarNames()
		boolean interrupt(JobKey jobKey)
		boolean interrupt(String fireInstanceId)
		boolean checkExists(JobKey jobKey)
		boolean checkExists(TriggerKey triggerKey)
		void clear()



----------------------------
SchedulerFactory			|
----------------------------
	# 工厂类接口,  负责创建Scheduler实例对象
		Scheduler getScheduler() throws SchedulerException;
		Scheduler getScheduler(String schedName) throws SchedulerException;
		Collection<Scheduler> getAllSchedulers() throws SchedulerException;

	# 实现类有
		DirectSchedulerFactory
		StdSchedulerFactory
	
	# StdSchedulerFactory 实例方法
		void initialize()
		void initialize(InputStream propertiesStream)
		void initialize(String filename) 
		void initialize(Properties props)
			* 根据配置初始化
	


	
----------------------------
StdSchedulerFactory			|
----------------------------
	# 静态方法
		Scheduler getDefaultScheduler();
			* 创建默认的 Scheduler
			* new StdSchedulerFactory().getScheduler();

		
----------------------------
DirectSchedulerFactory		|
----------------------------