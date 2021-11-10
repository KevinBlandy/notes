----------------------------
DailyTimeIntervalScheduleBuilder
----------------------------
	# DailyTimeIntervalScheduleBuilder
		* public class DailyTimeIntervalScheduleBuilder extends ScheduleBuilder<DailyTimeIntervalTrigger>

	# 静态
		public static final Set<Integer> ALL_DAYS_OF_THE_WEEK;
		public static final Set<Integer> MONDAY_THROUGH_FRIDAY;
		public static final Set<Integer> SATURDAY_AND_SUNDAY;

		public static DailyTimeIntervalScheduleBuilder dailyTimeIntervalSchedule()

	# 实例方法
		protected DailyTimeIntervalScheduleBuilder()

		public MutableTrigger build()

		public DailyTimeIntervalScheduleBuilder endingDailyAfterCount(int count)
		public DailyTimeIntervalScheduleBuilder endingDailyAt(TimeOfDay timeOfDay)
		public DailyTimeIntervalScheduleBuilder onDaysOfTheWeek(Integer ... onDaysOfWeek)
		public DailyTimeIntervalScheduleBuilder onDaysOfTheWeek(Set<Integer> onDaysOfWeek)
		public DailyTimeIntervalScheduleBuilder onEveryDay()
		public DailyTimeIntervalScheduleBuilder onMondayThroughFriday()
		public DailyTimeIntervalScheduleBuilder onSaturdayAndSunday()
		public DailyTimeIntervalScheduleBuilder startingDailyAt(TimeOfDay timeOfDay)
		public DailyTimeIntervalScheduleBuilder withInterval(int timeInterval, IntervalUnit unit)
		public DailyTimeIntervalScheduleBuilder withIntervalInHours(int intervalInHours)
		public DailyTimeIntervalScheduleBuilder withIntervalInMinutes(int intervalInMinutes)
		public DailyTimeIntervalScheduleBuilder withIntervalInSeconds(int intervalInSeconds)
		public DailyTimeIntervalScheduleBuilder withMisfireHandlingInstructionDoNothing()
		public DailyTimeIntervalScheduleBuilder withMisfireHandlingInstructionFireAndProceed()
		public DailyTimeIntervalScheduleBuilder withMisfireHandlingInstructionIgnoreMisfires()
		public DailyTimeIntervalScheduleBuilder withRepeatCount(int repeatCount)
	
	# TimeOfDay
		* 一个时间的描述类
		* 主要就是维护了3个变量
			private final int hour;
			private final int minute;
			private final int second;
		
		* 构造方法
			TimeOfDay(int hour, int minute, int second) 