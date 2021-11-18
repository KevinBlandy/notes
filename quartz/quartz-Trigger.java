---------------------
Trigger
---------------------
	# 触发器核心接口: Trigger
		CalendarIntervalTrigger
		CoreTrigger
		CronTrigger
		DailyTimeIntervalTrigger
		MutableTrigger
			|OperableTrigger
		SimpleTrigger

	# 通过 TriggerBuilder 创建实例
		* builder中需要指定: withSchedule
			<SBT extends T> TriggerBuilder<SBT> withSchedule(ScheduleBuilder<SBT> schedBuilder)

		* 不指定withSchedule，默认: SimpleScheduleBuilder
				return simpleSchedule().withIntervalInMinutes(1).repeatForever();

		* 不同的调度规则(ScheduleBuilder)，会创建不同的 Trigger 实现
		* 常用的
			CalendarIntervalScheduleBuilder
			CronScheduleBuilder
			DailyTimeIntervalScheduleBuilder
			SimpleScheduleBuilder
	
	# 一个Trigger可以关联一个Job

	# Misfire
		* 如果由于调度程序关闭，或者因为 Quartz 的线程池中没有可用的线程来执行作业，持久触发器“错过”了它的触发时间，则会发生Misfire
		* 不同的触发器类型有不同的Misfire指令可供它们使用。默认情况下，它们使用“智能策略”指令——它具有基于触发器类型和配置的动态行为。
		* 当调度程序启动时，它会搜索任何已Misfire的持久触发器，然后根据它们单独配置的失火指令更新每个触发器。
		
		Trigger
			public static final int MISFIRE_INSTRUCTION_SMART_POLICY = 0;  // 默认
			public static final int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;
		
		CalendarIntervalTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
			public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;
		
		CronTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
			public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;
		
		DailyTimeIntervalTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
			public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;
		
		SimpleTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_NOW = 1;
			public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT = 2;
			public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT = 3;
			public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT = 4;
			public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT = 5;
	