------------------------
ChronoUnit
------------------------
	# ChronoUnit implements TemporalUnit
		
	# ʱ�䵥λö��

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
	
	# ����
		Duration getDuration()
		boolean isDurationEstimated()
		boolean isDateBased()
		boolean isTimeBased()
		boolean isSupportedBy(Temporal temporal)

		<R extends Temporal> R addTo(R temporal, long amount)

		long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive)
			* ��ö��Ϊ��λ������������֮��Ĳ��
