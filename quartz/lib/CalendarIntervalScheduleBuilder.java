----------------------------------
CalendarIntervalScheduleBuilder
----------------------------------
	# CalendarIntervalScheduleBuilder
		* public class CalendarIntervalScheduleBuilder extends ScheduleBuilder<CalendarIntervalTrigger>
	
	
	# 静态方法
		public static CalendarIntervalScheduleBuilder calendarIntervalSchedule()
	
	# 实例方法
		protected CalendarIntervalScheduleBuilder()

		public MutableTrigger build()
		public CalendarIntervalScheduleBuilder withInterval(int timeInterval, IntervalUnit unit)

		public CalendarIntervalScheduleBuilder withIntervalInSeconds(int intervalInSeconds)
		public CalendarIntervalScheduleBuilder withIntervalInMinutes(int intervalInMinutes)
		public CalendarIntervalScheduleBuilder withIntervalInHours(int intervalInHours)
		public CalendarIntervalScheduleBuilder withIntervalInDays(int intervalInDays)
		public CalendarIntervalScheduleBuilder withIntervalInWeeks(int intervalInWeeks)
		public CalendarIntervalScheduleBuilder withIntervalInMonths(int intervalInMonths)
		public CalendarIntervalScheduleBuilder withIntervalInYears(int intervalInYears)
			* 每秒/分/时/天/周/月/年，执行一次

		public CalendarIntervalScheduleBuilder withMisfireHandlingInstructionIgnoreMisfires()
		public CalendarIntervalScheduleBuilder withMisfireHandlingInstructionDoNothing()
		public CalendarIntervalScheduleBuilder withMisfireHandlingInstructionFireAndProceed()
		public CalendarIntervalScheduleBuilder inTimeZone(TimeZone timezone)
		public CalendarIntervalScheduleBuilder preserveHourOfDayAcrossDaylightSavings(boolean preserveHourOfDay)
		public CalendarIntervalScheduleBuilder skipDayIfHourDoesNotExist(boolean skipDay)

