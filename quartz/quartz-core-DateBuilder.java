---------------------
DateBuilder			 |
---------------------
	# 用于构造一个 Date 的builder
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

	# 实例方法
		