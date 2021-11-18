--------------------------------
ListenerManager
--------------------------------
	# 事件监听器管理器: public interface ListenerManager 

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
	public void addSchedulerListener(SchedulerListener schedulerListener);
	public boolean removeSchedulerListener(SchedulerListener schedulerListener);
	public List<SchedulerListener> getSchedulerListeners();

--------------------------------
JobListener
--------------------------------
	# Job事件监听器接口: public interface JobListener 
	
	# 接口方法
		String getName();
			* 监听器名称

		void jobToBeExecuted(JobExecutionContext context);
		void jobExecutionVetoed(JobExecutionContext context);
		void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException);

	# JobListenerSupport 
		
		* 系统提供的抽象类，只需要复写自己需要的方法
		
		public abstract class JobListenerSupport implements JobListener {
			private final Logger log = LoggerFactory.getLogger(getClass());

			/**
			 * Get the <code>{@link org.slf4j.Logger}</code> for this
			 * class's category.  This should be used by subclasses for logging.
			 */
			protected Logger getLog() {
				return log;
			}
			
			public void jobToBeExecuted(JobExecutionContext context) {
			}
			
			public void jobExecutionVetoed(JobExecutionContext context) {
			}

			public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
			}
		}

--------------------------------
TriggerListener
--------------------------------
	# 触发器事件监听接口: public interface TriggerListener
	
	# 接口方法
		String getName();
		void triggerFired(Trigger trigger, JobExecutionContext context);
		boolean vetoJobExecution(Trigger trigger, JobExecutionContext context);
		void triggerMisfired(Trigger trigger);
		void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode);
	
	# TriggerListenerSupport
		* 系统提供的抽象类，只需要复写自己需要的方法

		public abstract class TriggerListenerSupport implements TriggerListener {
			private final Logger log = LoggerFactory.getLogger(getClass());

			protected Logger getLog() {
				return log;
			}

			public void triggerFired(Trigger trigger, JobExecutionContext context) {
			}

			public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
				return false;
			}

			public void triggerMisfired(Trigger trigger) {
			}

			public void triggerComplete(
				Trigger trigger,
				JobExecutionContext context,
				CompletedExecutionInstruction triggerInstructionCode) {
			}
		}
	

--------------------------------
SchedulerListener
--------------------------------
	# 调度器监听接口: public interface SchedulerListener
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
	
	# SchedulerListenerSupport
		* 系统提供的抽象类，只需要复写自己需要的方法
	
		public abstract class SchedulerListenerSupport implements SchedulerListener {
			private final Logger log = LoggerFactory.getLogger(getClass());

			/**
			 * Get the <code>{@link org.slf4j.Logger}</code> for this
			 * class's category.  This should be used by subclasses for logging.
			 */
			protected Logger getLog() {
				return log;
			}
			public void jobAdded(JobDetail jobDetail) {
			}
			public void jobDeleted(JobKey jobKey) {
			}
			public void jobPaused(JobKey jobKey) {
			}
			public void jobResumed(JobKey jobKey) {
			}
			public void jobScheduled(Trigger trigger) {
			}
			public void jobsPaused(String jobGroup) {
			}
			public void jobsResumed(String jobGroup) {
			}
			public void jobUnscheduled(TriggerKey triggerKey) {
			}
			public void schedulerError(String msg, SchedulerException cause) {
			}
			public void schedulerInStandbyMode() {
			}
			public void schedulerShutdown() {
			}
			public void schedulerShuttingdown() {
			}
			public void schedulerStarted() {
			}
			public void schedulerStarting() {
			}
			public void triggerFinalized(Trigger trigger) {
			}
			public void triggerPaused(TriggerKey triggerKey) {
			}
			public void triggerResumed(TriggerKey triggerKey) {
			}
			public void triggersPaused(String triggerGroup) {
			}
			public void triggersResumed(String triggerGroup) {
			}
			public void schedulingDataCleared() {
			}
		}

--------------------------
Matcher
--------------------------
	# 匹配器接口: public interface Matcher<T extends Key<?>> extends Serializable 
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
	
	# 一些Demo
		scheduler.getListenerManager().addJobListener(myJobListener, jobKeyEquals(jobKey("myJobName", "myJobGroup")));
		scheduler.getListenerManager().addJobListener(myJobListener, jobGroupEquals("myJobGroup"));
		scheduler.getListenerManager().addJobListener(myJobListener, or(jobGroupEquals("myJobGroup"), jobGroupEquals("yourGroup")));
		scheduler.getListenerManager().addJobListener(myJobListener, allJobs());
