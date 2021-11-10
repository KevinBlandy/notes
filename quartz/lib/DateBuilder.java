---------------------
DateBuilder			 |
---------------------
	# 用于构造一个时间的builder
	# 常量
		public enum IntervalUnit { MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR }
			* 时间单位
		
		public static final int SUNDAY = 1;
		public static final int MONDAY = 2;
		public static final int TUESDAY = 3;
		public static final int WEDNESDAY = 4;
		public static final int THURSDAY = 5;
		public static final int FRIDAY = 6;
		public static final int SATURDAY = 7;
			* 一周

		public static final int JANUARY = 1;
		public static final int FEBRUARY = 2;
		public static final int MARCH = 3;
		public static final int APRIL = 4;
		public static final int MAY = 5;
		public static final int JUNE = 6;
		public static final int JULY = 7;
		public static final int AUGUST = 8;
		public static final int SEPTEMBER = 9;
		public static final int OCTOBER = 10;
		public static final int NOVEMBER = 11;
		public static final int DECEMBER = 12;
			* 1 - 12月

		public static final long MILLISECONDS_IN_MINUTE = 60l * 1000l;
		public static final long MILLISECONDS_IN_HOUR = 60l * 60l * 1000l;
		public static final long SECONDS_IN_MOST_DAYS = 24l * 60l * 60L;
		public static final long MILLISECONDS_IN_DAY = SECONDS_IN_MOST_DAYS * 1000l;
    
	# 工厂方法
		public static DateBuilder newDate()
		public static DateBuilder newDateInTimezone(TimeZone tz)
		public static DateBuilder newDateInLocale(Locale lc)
		public static DateBuilder newDateInTimeZoneAndLocale(TimeZone tz, Locale lc)

	# 实例方法
		public Date build()
		public DateBuilder atHourOfDay(int atHour) 
		public DateBuilder atMinute(int atMinute)
		public DateBuilder atSecond(int atSecond) 
		public DateBuilder atHourMinuteAndSecond(int atHour, int atMinute, int atSecond)
		public DateBuilder onDay(int onDay)
		public DateBuilder inMonth(int inMonth)
		public DateBuilder inMonthOnDay(int inMonth, int onDay)
		public DateBuilder inYear(int inYear)
		public DateBuilder inTimeZone(TimeZone timezone)
		public DateBuilder inLocale(Locale locale)

		public static Date futureDate(int interval, IntervalUnit unit)
		private static int translate(IntervalUnit unit)
		public static Date tomorrowAt(int hour, int minute, int second)
		public static Date todayAt(int hour, int minute, int second) 
		public static Date dateOf(int hour, int minute, int second)
		public static Date dateOf(int hour, int minute, int second, int dayOfMonth, int month)
		public static Date dateOf(int hour, int minute, int second, int dayOfMonth, int month, int year)
		public static Date evenHourDateAfterNow()
		public static Date evenHourDate(Date date)
		public static Date evenHourDateBefore(Date date)
		public static Date evenMinuteDateAfterNow()
		public static Date evenMinuteDate(Date date)
		public static Date evenMinuteDateBefore(Date date)
		public static Date evenSecondDateAfterNow()
		public static Date evenSecondDate(Date date)
		public static Date evenSecondDateBefore(Date date)
		public static Date nextGivenMinuteDate(Date date, int minuteBase)
		public static Date nextGivenSecondDate(Date date, int secondBase)
		public static Date translateTime(Date date, TimeZone src, TimeZone dest)
		public static void validateDayOfWeek(int dayOfWeek)
		public static void validateHour(int hour)
		public static void validateMinute(int minute)
		public static void validateSecond(int second
		public static void validateDayOfMonth(int day)
		public static void validateMonth(int month)
		public static void validateYear(int year)
