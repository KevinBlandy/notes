----------------------------
Trigger						|
----------------------------
	# 接口内部的枚举和常量
		public enum TriggerState { NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED }
		public enum CompletedExecutionInstruction { NOOP, RE_EXECUTE_JOB, SET_TRIGGER_COMPLETE, DELETE_TRIGGER, 
			SET_ALL_JOB_TRIGGERS_COMPLETE, SET_TRIGGER_ERROR, SET_ALL_JOB_TRIGGERS_ERROR }
		public static final int MISFIRE_INSTRUCTION_SMART_POLICY = 0;
		public static final int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;
		public static final int DEFAULT_PRIORITY = 5;

	# 接口方法
		public TriggerKey getKey();
		public JobKey getJobKey();
		
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


	# 常用的子接口
		SimpleTrigger
		CronTrigger
		CalendarIntervalTrigger
		DailyTimeIntervalTrigger

	# 错过触发(misfire Instructions)
		
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
			* 创建调度规则

----------------------------
ScheduleBuilder				|
----------------------------
	# 调度接口的工厂接口, 抽象方法
		  protected abstract MutableTrigger build();

	# 子类
		CalendarIntervalScheduleBuilder
		CronScheduleBuilder
		DailyTimeIntervalScheduleBuilder
		SimpleScheduleBuilder


----------------------------
SimpleTrigger				|
----------------------------
	# 简单的触发器, 在具体的时间点执行一次, 或者在具体的时间点执行, 并且以指定的间隔重复执行若干次
	# 通过 SimpleScheduleBuilder 创建
	# 静态工厂方法
		SimpleScheduleBuilder simpleSchedule()

		SimpleScheduleBuilder repeatMinutelyForever()
		SimpleScheduleBuilder repeatMinutelyForever(int minutes)
		SimpleScheduleBuilder repeatSecondlyForever() 
		SimpleScheduleBuilder repeatSecondlyForever(int seconds)
		SimpleScheduleBuilder repeatHourlyForever()
		SimpleScheduleBuilder repeatHourlyForever(int hours)
			* 按照多少时间单位重复执行, 时间单位默认1(秒/分/时)

		SimpleScheduleBuilder repeatMinutelyForTotalCount(int count)
		SimpleScheduleBuilder repeatMinutelyForTotalCount(int count, int minutes)
		SimpleScheduleBuilder repeatSecondlyForTotalCount(int count)
		SimpleScheduleBuilder repeatSecondlyForTotalCount(int count, int seconds)
		SimpleScheduleBuilder repeatHourlyForTotalCount(int count)
		SimpleScheduleBuilder repeatHourlyForTotalCount(int count, int hours)
			* 按照多少时间单位重复执行, 时间单位默认1(秒/分/时)
			* count 限制执行次数

	# 实例方法
		MutableTrigger build()
		SimpleScheduleBuilder withIntervalInMilliseconds(long intervalInMillis)
		SimpleScheduleBuilder withIntervalInMilliseconds(long intervalInMillis)
		SimpleScheduleBuilder withIntervalInSeconds(int intervalInSeconds)
		SimpleScheduleBuilder withIntervalInMinutes(int intervalInMinutes)
		SimpleScheduleBuilder withIntervalInHours(int intervalInHours)
		SimpleScheduleBuilder withRepeatCount(int triggerRepeatCount)
		SimpleScheduleBuilder repeatForever()

		SimpleScheduleBuilder withMisfireHandlingInstructionIgnoreMisfires()
		SimpleScheduleBuilder withMisfireHandlingInstructionFireNow() 
		SimpleScheduleBuilder withMisfireHandlingInstructionNextWithExistingCount()
		SimpleScheduleBuilder withMisfireHandlingInstructionNextWithRemainingCount()
		SimpleScheduleBuilder withMisfireHandlingInstructionNowWithExistingCount()
		SimpleScheduleBuilder withMisfireHandlingInstructionNowWithRemainingCount()
			* Misfire的处理策略
	
	# misfire相关的策略(常量)
		SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW
		SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT
		SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT
		SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT
		SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT



----------------------------
CronTrigger					|
----------------------------
	# Cron表达式的调度
	# 通过 CronScheduleBuilder 构建
	# 静态工厂方法
		CronScheduleBuilder atHourAndMinuteOnGivenDaysOfWeek(int hour, int minute, Integer... daysOfWeek)
		CronScheduleBuilder cronSchedule(String cronExpression)
		CronScheduleBuilder cronSchedule(CronExpression cronExpression)
		CronScheduleBuilder cronScheduleNonvalidatedExpression(String cronExpression)
		CronScheduleBuilder dailyAtHourAndMinute(int hour, int minute)
			* 在每天的指定小时, 指定分钟触发

		CronScheduleBuilder monthlyOnDayAndHourAndMinute(int dayOfMonth, int hour, int minute)
			* 在每个月的指定天, 的指定小时, 指定分钟触发

		CronScheduleBuilder weeklyOnDayAndHourAndMinute(int dayOfWeek, int hour, int minute)
			* 在每个周的指定星期, 的指定小时, 指定分钟触发

	# 实例方法
		CronScheduleBuilder inTimeZone(TimeZone timezone)

		CronScheduleBuilder withMisfireHandlingInstructionDoNothing()
		CronScheduleBuilder withMisfireHandlingInstructionFireAndProceed()
		CronScheduleBuilder withMisfireHandlingInstructionIgnoreMisfires()
			* Misfire的处理策略
	
	# misfire相关的策略(常量)
		CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW
		CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING

----------------------------
DailyTimeIntervalTrigger	|
----------------------------
	# 通过 DailyTimeIntervalScheduleBuilder 构建
	# 静态工厂方法
		DailyTimeIntervalScheduleBuilder dailyTimeIntervalSchedule()

	# 实例方法
		DailyTimeIntervalScheduleBuilder endingDailyAfterCount(int count)
		DailyTimeIntervalScheduleBuilder endingDailyAt(TimeOfDay timeOfDay)
		DailyTimeIntervalScheduleBuilder onDaysOfTheWeek(Integer ... onDaysOfWeek)
		DailyTimeIntervalScheduleBuilder onDaysOfTheWeek(Set<Integer> onDaysOfWeek)
		DailyTimeIntervalScheduleBuilder onEveryDay()
		DailyTimeIntervalScheduleBuilder onMondayThroughFriday()
		DailyTimeIntervalScheduleBuilder onSaturdayAndSunday()
		DailyTimeIntervalScheduleBuilder startingDailyAt(TimeOfDay timeOfDay)
		DailyTimeIntervalScheduleBuilder withInterval(int timeInterval, IntervalUnit unit)
		DailyTimeIntervalScheduleBuilder withIntervalInHours(int intervalInHours)
		DailyTimeIntervalScheduleBuilder withIntervalInMinutes(int intervalInMinutes)
		DailyTimeIntervalScheduleBuilder withIntervalInSeconds(int intervalInSeconds)
		DailyTimeIntervalScheduleBuilder withMisfireHandlingInstructionDoNothing()
		DailyTimeIntervalScheduleBuilder withMisfireHandlingInstructionFireAndProceed()
		DailyTimeIntervalScheduleBuilder withMisfireHandlingInstructionIgnoreMisfires()
		DailyTimeIntervalScheduleBuilder withRepeatCount(int repeatCount)
	
	# TimeOfDay
		* 主要就是维护了3个变量
			private final int hour;
			private final int minute;
			private final int second;
		
		* 构造方法
			TimeOfDay(int hour, int minute, int second) 
	

----------------------------
CalendarIntervalTrigger		|
----------------------------
	# 通过 CalendarIntervalScheduleBuilder 创建
	# 工厂方法
		static CalendarIntervalScheduleBuilder calendarIntervalSchedule()
	# 实例方法
		CalendarIntervalScheduleBuilder inTimeZone(TimeZone timezone)
		CalendarIntervalScheduleBuilder preserveHourOfDayAcrossDaylightSavings(boolean preserveHourOfDay)
		CalendarIntervalScheduleBuilder skipDayIfHourDoesNotExist(boolean skipDay)
		CalendarIntervalScheduleBuilder withInterval(int timeInterval, IntervalUnit unit)
		CalendarIntervalScheduleBuilder withIntervalInDays(int intervalInDays)
		CalendarIntervalScheduleBuilder withIntervalInHours(int intervalInHours)
		CalendarIntervalScheduleBuilder withIntervalInMinutes(int intervalInMinutes)
		CalendarIntervalScheduleBuilder withIntervalInMonths(int intervalInMonths)
		CalendarIntervalScheduleBuilder withIntervalInSeconds(int intervalInSeconds)
		CalendarIntervalScheduleBuilder withIntervalInWeeks(int intervalInWeeks)
		CalendarIntervalScheduleBuilder withIntervalInYears(int intervalInYears)
		CalendarIntervalScheduleBuilder withMisfireHandlingInstructionDoNothing()
		CalendarIntervalScheduleBuilder withMisfireHandlingInstructionFireAndProceed()
		CalendarIntervalScheduleBuilder withMisfireHandlingInstructionIgnoreMisfires()
		
----------------------------
TriggerKey					|
----------------------------
	# 描述trigger的name和grouo属性对象
		
	# 跟JobKey一摸一样, 除了类名不同

