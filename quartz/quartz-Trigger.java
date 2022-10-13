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
		* Misfire的原因
			1.当job达到触发时间时，所有线程都被其他job占用，没有可用线程。
			2.在job需要触发的时间点，scheduler 停止了（可能是意外停止的）。
			3.job使用了 @DisallowConcurrentExecution 注解，job不能并发执行，当达到下一个job执行点的时候，上一个任务还没有完成。
			4.job指定了过去的开始执行时间，例如当前时间是8点00分00秒，指定开始时间为7点00分00秒
		
		* MisFire的前置条件
			1. job到达触发时间时没有被执行
			2. 被执行的延迟时间超过了Quartz配置的 misfireThreshold 阀值
		

			* 如果延迟执行的时间小于阀值，则Quartz不认为发生了misfire，立即执行job；
			* 如果延迟执行的时间大于或者等于阀值，则被判断为 misfire，然后会按照指定的策略来执行。
		
		* 案例说明
			* 设置一个job在上午8点执行，由于一些原因job在8点没有执行，分为两种情况

			1. 在8点00分50秒Quartz有资源来执行这个job
				* 此时的延迟执行时间是50秒，小于misfireThreshold为60秒的阀值，则Quartz认为该job没有发生misfire，立即执行job。

			2. 在8点10分00秒Quartz有资源来执行这个job
				* 此时延迟执行时间是600秒，大于misfireThreshold为60秒的阀值，则Quartz认为该job发生了misfire，会根据指定的misfire策略来执行。
		
		Trigger
			public static final int MISFIRE_INSTRUCTION_SMART_POLICY = 0;  // 默认

			public static final int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;
				* 不会判断发生了misfire，立即执行所有发生了misfire的任务，然后按照原计划进行执行。
				* 例如：10:15分立即执行9:00和10:00的任务，然后等待下一个任务在11:00执行，后续按照原计划执行。

		
		CalendarIntervalTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
				* 立即执行第一个发生misfire的任务，忽略其他发生misfire的任务，然后按照原计划继续执行。
				* 例如：在10:15立即执行9:00任务，忽略10:00任务，然后等待下一个任务在11:00执行，后续按照原计划执行
				
			public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;
				* 所有发生misfire的任务都被忽略，只是按照原计划继续执行
		
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
	