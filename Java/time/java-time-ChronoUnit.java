------------------------
ChronoUnit
------------------------
	# ChronoUnit implements TemporalUnit
		
	# 时间单位枚举

		NANOS
		MICROS
		MILLIS
		SECONDS
		MINUTES
		HOURS
		HALF_DAYS
		DAYS
		WEEKS
		MONTHS
		YEARS
		DECADES
		CENTURIES
		MILLENNIA
		ERAS
		FOREVER
	
	# 方法
		Duration getDuration()
		boolean isDurationEstimated()
		boolean isDateBased()
		boolean isTimeBased()
		boolean isSupportedBy(Temporal temporal)
		<R extends Temporal> R addTo(R temporal, long amount)
		long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive)
