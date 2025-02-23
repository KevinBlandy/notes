-----------------------------
时差相关
-----------------------------
	# public final class ZoneOffset
        extends ZoneId
        implements TemporalAccessor, TemporalAdjuster, Comparable<ZoneOffset>, Serializable
		
	# 静态变量
		public static final ZoneOffset UTC = ZoneOffset.ofTotalSeconds(0);
		public static final ZoneOffset MIN = ZoneOffset.ofTotalSeconds(-MAX_SECONDS);
		public static final ZoneOffset MAX = ZoneOffset.ofTotalSeconds(MAX_SECONDS);
	

	# 静态方法
		static ZoneOffset from(TemporalAccessor temporal)
		static ZoneOffset of(String offsetId)
			* 解析字符串为时区偏移
				var offset = ZoneOffset.of("+08:00");
			
		static ZoneOffset ofHours(int hours)
		static ZoneOffset ofHoursMinutes(int hours, int minutes)
		static ZoneOffset ofHoursMinutesSeconds(int hours, int minutes, int seconds)
		static ZoneOffset ofTotalSeconds(int totalSeconds)
	
	# 实例方法
		Temporal adjustInto(Temporal temporal)
		int compareTo(ZoneOffset other)
		int get(TemporalField field)
		String getId() 
		long getLong(TemporalField field)
		ZoneRules getRules()
		int getTotalSeconds()
		boolean isSupported(TemporalField field)
		<R> R query(TemporalQuery<R> query)
		ValueRange range(TemporalField field)
