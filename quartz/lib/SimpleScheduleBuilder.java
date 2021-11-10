------------------------
SimpleScheduleBuilder
------------------------
	# SimpleScheduleBuilder
		* public class SimpleScheduleBuilder extends ScheduleBuilder<SimpleTrigger>
	
	# static
		public static SimpleScheduleBuilder simpleSchedule()
		public static SimpleScheduleBuilder repeatMinutelyForever()
		public static SimpleScheduleBuilder repeatMinutelyForever(int minutes) 

		public static SimpleScheduleBuilder repeatSecondlyForever()
		public static SimpleScheduleBuilder repeatSecondlyForever(int seconds) 

		public static SimpleScheduleBuilder repeatHourlyForever()
		public static SimpleScheduleBuilder repeatHourlyForever(int hours)

		public static SimpleScheduleBuilder repeatMinutelyForTotalCount(int count)
		public static SimpleScheduleBuilder repeatMinutelyForTotalCount(int count, int minutes)

		public static SimpleScheduleBuilder repeatSecondlyForTotalCount(int count)
		public static SimpleScheduleBuilder repeatSecondlyForTotalCount(int count, int seconds)

		public static SimpleScheduleBuilder repeatHourlyForTotalCount(int count)
		public static SimpleScheduleBuilder repeatHourlyForTotalCount(int count, int hours)
			* 间隔多少时间单位重复执行, 时间单位默认1(秒/分/时)
			* minutes 用于指定重复次数，默认为永远重复

	
	# this
		protected SimpleScheduleBuilder()
		public MutableTrigger build()
		public SimpleScheduleBuilder withIntervalInMilliseconds(long intervalInMillis)
		public SimpleScheduleBuilder withIntervalInSeconds(int intervalInSeconds)
		public SimpleScheduleBuilder withIntervalInMinutes(int intervalInMinutes)
		public SimpleScheduleBuilder withIntervalInHours(int intervalInHours) 
		public SimpleScheduleBuilder withRepeatCount(int triggerRepeatCount)
		public SimpleScheduleBuilder repeatForever()
			* 永远重复

		public SimpleScheduleBuilder withMisfireHandlingInstructionIgnoreMisfires()
		public SimpleScheduleBuilder withMisfireHandlingInstructionFireNow()
		public SimpleScheduleBuilder withMisfireHandlingInstructionNextWithExistingCount()
		public SimpleScheduleBuilder withMisfireHandlingInstructionNextWithRemainingCount()
		public SimpleScheduleBuilder withMisfireHandlingInstructionNowWithExistingCount()
		public SimpleScheduleBuilder withMisfireHandlingInstructionNowWithRemainingCount()
