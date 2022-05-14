-----------------
Clock
-----------------

	# static
		public static Instant now()
		public static Instant now(Clock clock)
		public static Instant ofEpochSecond(long epochSecond)
		public static Instant ofEpochSecond(long epochSecond, long nanoAdjustment)
		public static Instant ofEpochMilli(long epochMilli)
		public static Instant from(TemporalAccessor temporal)
		public static Instant parse(final CharSequence text)
