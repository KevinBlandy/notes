----------------------------
Trigger						|
----------------------------
	# 触发器接口: public interface Trigger extends Serializable, Cloneable, Comparable<Trigger>

	# 接口内部的枚举和常量
		public enum TriggerState { NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED }

		public enum CompletedExecutionInstruction { NOOP, RE_EXECUTE_JOB, SET_TRIGGER_COMPLETE, DELETE_TRIGGER, 
				SET_ALL_JOB_TRIGGERS_COMPLETE, SET_TRIGGER_ERROR, SET_ALL_JOB_TRIGGERS_ERROR }

		public static final int MISFIRE_INSTRUCTION_SMART_POLICY = 0;
		public static final int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;
		public static final int DEFAULT_PRIORITY = 5;

	# 接口方法
		public TriggerKey getKey();
			* 获取TriggerKey

		public JobKey getJobKey();
			* 获取JobKey
		
		public String getDescription();

		public String getCalendarName();

		public JobDataMap getJobDataMap();
		public int getPriority();

		public boolean mayFireAgain();
		public Date getStartTime();

		public Date getEndTime();

		public Date getNextFireTime();

		public Date getPreviousFireTime();

		public Date getFireTimeAfter(Date afterTime);

		public Date getFinalFireTime();

		public int getMisfireInstruction();
		public TriggerBuilder<? extends Trigger> getTriggerBuilder();
		
		public ScheduleBuilder<? extends Trigger> getScheduleBuilder();

		public boolean equals(Object other);
		
		public int compareTo(Trigger other);
	
	# 常用实现子接口
		CalendarIntervalTrigger
		CoreTrigger
		CronTrigger
		DailyTimeIntervalTrigger
		MutableTrigger
			|OperableTrigger
		SimpleTrigger


--------------------------
CalendarIntervalTrigger
--------------------------
	# 定期执行Trigger接口: public interface CalendarIntervalTrigger extends Trigger

	public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
	public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;

	public IntervalUnit getRepeatIntervalUnit();
	public int getRepeatInterval();
	public int getTimesTriggered();
	public TimeZone getTimeZone();
	public boolean isPreserveHourOfDayAcrossDaylightSavings();
	public boolean isSkipDayIfHourDoesNotExist();
	TriggerBuilder<CalendarIntervalTrigger> getTriggerBuilder();



--------------------------
CoreTrigger
--------------------------
	# 核心Trigger接口: public interface CoreTrigger extends Trigger 

	public boolean hasAdditionalProperties();


--------------------------
CronTrigger
--------------------------
	# corn表达式接口: public interface CronTrigger extends Trigger
	
	public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
	public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;

	public String getCronExpression();
	public TimeZone getTimeZone();
	public String getExpressionSummary();
	TriggerBuilder<CronTrigger> getTriggerBuilder();

--------------------------
DailyTimeIntervalTrigger
--------------------------
	# 延迟执行Trigger接口: public interface DailyTimeIntervalTrigger extends Trigger

	public static final int REPEAT_INDEFINITELY = -1;
	public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
	public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;

	public IntervalUnit getRepeatIntervalUnit();
	public int getRepeatCount();
	public int getRepeatInterval();
	public TimeOfDay getStartTimeOfDay();
	public TimeOfDay getEndTimeOfDay();
	public Set<Integer> getDaysOfWeek();
	public int getTimesTriggered();
	public TriggerBuilder<DailyTimeIntervalTrigger> getTriggerBuilder();

--------------------------
MutableTrigger
--------------------------
	# public interface MutableTrigger extends Trigger

    public void setKey(TriggerKey key);
    public void setJobKey(JobKey key);
	public void setDescription(String description);
	public void setCalendarName(String calendarName);
	public void setJobDataMap(JobDataMap jobDataMap);
	public void setPriority(int priority);
	public void setStartTime(Date startTime);
	public void setEndTime(Date endTime);
	public void setMisfireInstruction(int misfireInstruction);
	public Object clone();


--------------------------
OperableTrigger
--------------------------
	# public interface OperableTrigger extends MutableTrigger

	public void triggered(Calendar calendar);
	public Date computeFirstFireTime(Calendar calendar);
	public CompletedExecutionInstruction executionComplete(JobExecutionContext context, JobExecutionException result);
	public void updateAfterMisfire(Calendar cal);
	public void updateWithNewCalendar(Calendar cal, long misfireThreshold);
	public void validate() throws SchedulerException;
	public void setFireInstanceId(String id);
	public String getFireInstanceId();
    public void setNextFireTime(Date nextFireTime);
    public void setPreviousFireTime(Date previousFireTime);

--------------------------
SimpleTrigger
--------------------------
	# 简单的Trigger接口: public interface SimpleTrigger extends Trigger

	public static final int MISFIRE_INSTRUCTION_FIRE_NOW = 1;
	public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT = 2;
	public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT = 3;
	public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT = 4;
	public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT = 5;
	public static final int REPEAT_INDEFINITELY = -1;
	public int getRepeatCount();
	public long getRepeatInterval();
	public int getTimesTriggered();
	TriggerBuilder<SimpleTrigger> getTriggerBuilder();

----------------------------
TriggerBuilder				|
----------------------------
	# Trigger 工厂类
	
	# 静态方法
		static TriggerBuilder<Trigger> newTrigger()
		
	# 实例方法
		T build()
		TriggerBuilder<T> endAt(Date triggerEndTime)
			* 表示trigger失效的时间点

		TriggerBuilder<T> forJob(JobDetail jobDetail)
		TriggerBuilder<T> forJob(JobKey keyOfJobToFire)
		TriggerBuilder<T> forJob(String jobName)
		TriggerBuilder<T> forJob(String jobName, String jobGroup)
			* 关联job, 可以指定类, 或者指定名称(从Scheduler中查询)

		TriggerBuilder<T> modifiedByCalendar(String calName)
			* 根据设置到Scheduler中的 Calendar , 修改执行计划

		TriggerBuilder<T> startAt(Date triggerStartTime)
			* 设置trigger第一次触发的时间

		TriggerBuilder<T> startNow()
			* 就绪后, 立即触发任务

		TriggerBuilder<T> usingJobData(JobDataMap newJobDataMap)
		TriggerBuilder<T> usingJobData(String dataKey, Boolean value)
		TriggerBuilder<T> usingJobData(String dataKey, Double value)
		TriggerBuilder<T> usingJobData(String dataKey, Float value)
		TriggerBuilder<T> usingJobData(String dataKey, Integer value)
		TriggerBuilder<T> usingJobData(String dataKey, Long value)
		TriggerBuilder<T> usingJobData(String dataKey, String value)

		TriggerBuilder<T> withDescription(String triggerDescription)

		TriggerBuilder<T> withIdentity(String name)
		TriggerBuilder<T> withIdentity(String name, String group)
		TriggerBuilder<T> withIdentity(TriggerKey triggerKey)
			* name, 表示trigger唯一的名称, 如果不曾调用, 则会默认生成一个默认的: 6da64b5bd2ee-05f824d5-50e9-438b-b72b-a2350c08ee65
			* group, 表示trigger所属的分组
			
			* 如果没设置 group, 默认为: DEFAULT

		TriggerBuilder<T> withPriority(int triggerPriority)
			* 优先级, 本质上就是设置了线程的优先级
			* 如果没有为trigger设置优先级，trigger使用默认优先级，值为5
			* priority属性的值可以是任意整数，正数、负数都可以
			* 注意：只有同时触发的trigger之间才会比较优先级。


		<SBT extends T> TriggerBuilder<SBT> withSchedule(ScheduleBuilder<SBT> schedBuilder)
			* 创建调度规则，默认: SimpleScheduleBuilder
				return simpleSchedule().withIntervalInMinutes(1).repeatForever();

			* 不同的调度规则，会创建不同的 Trigger 实现
			* 常用的
				CalendarIntervalScheduleBuilder
				CronScheduleBuilder
				DailyTimeIntervalScheduleBuilder
				SimpleScheduleBuilder
				

----------------------------
ScheduleBuilder	
----------------------------
	# 调度接口的工厂抽象类: public abstract class ScheduleBuilder<T extends Trigger> 

		  protected abstract MutableTrigger build();

	# 子类
		CalendarIntervalScheduleBuilder
		CronScheduleBuilder
		DailyTimeIntervalScheduleBuilder
		SimpleScheduleBuilder

----------------------------
TriggerKey
----------------------------
	# Trigger的唯一标识
		public final class TriggerKey extends Key<TriggerKey> 
	
	 public TriggerKey(String name)
	 public TriggerKey(String name, String group) 

	 public static TriggerKey triggerKey(String name)
	 public static TriggerKey triggerKey(String name, String group)
