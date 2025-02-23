-----------------------------
ʱ�����
-----------------------------
	# public final class ZoneOffset
        extends ZoneId
        implements TemporalAccessor, TemporalAdjuster, Comparable<ZoneOffset>, Serializable
		
	# ��̬����
		public static final ZoneOffset UTC = ZoneOffset.ofTotalSeconds(0);
		public static final ZoneOffset MIN = ZoneOffset.ofTotalSeconds(-MAX_SECONDS);
		public static final ZoneOffset MAX = ZoneOffset.ofTotalSeconds(MAX_SECONDS);
	

	# ��̬����
		static ZoneOffset from(TemporalAccessor temporal)
		static ZoneOffset of(String offsetId)
			* �����ַ���Ϊʱ��ƫ��
				var offset = ZoneOffset.of("+08:00");
			
		static ZoneOffset ofHours(int hours)
		static ZoneOffset ofHoursMinutes(int hours, int minutes)
		static ZoneOffset ofHoursMinutesSeconds(int hours, int minutes, int seconds)
		static ZoneOffset ofTotalSeconds(int totalSeconds)
	
	# ʵ������
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
