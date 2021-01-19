------------------------
Listener				|
------------------------
	# 通过 Scheduler 获取到 ListenerManager 来管理监听器
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        ListenerManager listenerManager = scheduler.getListenerManager();
	
	# Listener也可以通过 quartz.properties 形式配置

------------------------
ListenerManager			|
------------------------
	# 方法
		public void addJobListener(JobListener jobListener);
		public void addJobListener(JobListener jobListener, Matcher<JobKey> matcher);
		public void addJobListener(JobListener jobListener, Matcher<JobKey> ... matchers);
		public void addJobListener(JobListener jobListener, List<Matcher<JobKey>> matchers);

		public boolean addJobListenerMatcher(String listenerName, Matcher<JobKey> matcher);
		public boolean removeJobListenerMatcher(String listenerName, Matcher<JobKey> matcher);
		public boolean setJobListenerMatchers(String listenerName, List<Matcher<JobKey>> matchers);
		public List<Matcher<JobKey>> getJobListenerMatchers(String listenerName);
		public boolean removeJobListener(String name);
		public List<JobListener> getJobListeners();
		public JobListener getJobListener(String name);
			* 操作JobListener

		public void addTriggerListener(TriggerListener triggerListener);
		public void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> matcher);
		public void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> ... matchers);
		public void addTriggerListener(TriggerListener triggerListener, List<Matcher<TriggerKey>> matchers);

		public boolean addTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher);
		public boolean removeTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher);
		public boolean setTriggerListenerMatchers(String listenerName, List<Matcher<TriggerKey>> matchers);
		public List<Matcher<TriggerKey>> getTriggerListenerMatchers( String listenerName);
		public boolean removeTriggerListener(String name);
		public List<TriggerListener> getTriggerListeners();
		public TriggerListener getTriggerListener(String name);
			* 操作TriggerListener

		public void addSchedulerListener(SchedulerListener schedulerListener);
		public boolean removeSchedulerListener(SchedulerListener schedulerListener);
		public List<SchedulerListener> getSchedulerListeners();
			* 操作SchedulerListener
	
	# Matcher, 用于过滤监听的对象: Matcher<T extends Key<?>>
		boolean isMatch(T key);
		public int hashCode();
		public boolean equals(Object obj);
	
	# Matcher 具备很多子类, 可以用于各种条件
		AndMatcher				and 关系
		EverythingMatcher		所有
		KeyMatcher				根据key匹配
		NotMatcher				not 匹配
		OrMatcher				or 匹配
		StringMatcher			
			|-GroupMatcher		根据group匹配
			|-NameMatcher		根据name匹配

------------------------
JobListener				|
------------------------
	# 方法
		String getName();
		void jobToBeExecuted(JobExecutionContext context);
		void jobExecutionVetoed(JobExecutionContext context);
		void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException);

------------------------
TriggerListener			|
------------------------
	# 方法
		String getName();
		void triggerFired(Trigger trigger, JobExecutionContext context);
		boolean vetoJobExecution(Trigger trigger, JobExecutionContext context);
		void triggerMisfired(Trigger trigger);
		void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode);

------------------------
SchedulerListener		|
------------------------
	# 方法
		void jobScheduled(Trigger trigger);
		void jobUnscheduled(TriggerKey triggerKey);
		void triggerFinalized(Trigger trigger);
		void triggerPaused(TriggerKey triggerKey);
		void triggersPaused(String triggerGroup);
		void triggerResumed(TriggerKey triggerKey);
		void triggersResumed(String triggerGroup);
		void jobAdded(JobDetail jobDetail);
		void jobDeleted(JobKey jobKey);
		void jobPaused(JobKey jobKey);
		void jobsPaused(String jobGroup);
		void jobResumed(JobKey jobKey);
		void jobsResumed(String jobGroup);
		void schedulerError(String msg, SchedulerException cause);
		void schedulerInStandbyMode();
		void schedulerStarted();
		void schedulerStarting();
		void schedulerShutdown();
		void schedulerShuttingdown();
		void schedulingDataCleared();
