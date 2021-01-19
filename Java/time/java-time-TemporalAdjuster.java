--------------------------
TemporalAdjuster
--------------------------
	# 一个接口，用于日期的计算
		Temporal adjustInto(Temporal temporal);


--------------------------
TemporalAdjusters
--------------------------
	# 提供了N多的实现
	# 静态方法
		TemporalAdjuster ofDateAdjuster(UnaryOperator<LocalDate> dateBasedAdjuster) 
		TemporalAdjuster firstDayOfMonth()
		TemporalAdjuster lastDayOfMonth()
		TemporalAdjuster firstDayOfNextMonth()
		TemporalAdjuster firstDayOfYear()
		TemporalAdjuster lastDayOfYear()
		TemporalAdjuster firstDayOfNextYear()
		TemporalAdjuster firstInMonth(DayOfWeek dayOfWeek)
		TemporalAdjuster lastInMonth(DayOfWeek dayOfWeek)
		TemporalAdjuster dayOfWeekInMonth(int ordinal, DayOfWeek dayOfWeek)

		TemporalAdjuster next(DayOfWeek dayOfWeek)
		TemporalAdjuster nextOrSame(DayOfWeek dayOfWeek)
		TemporalAdjuster previous(DayOfWeek dayOfWeek)
		TemporalAdjuster previousOrSame(DayOfWeek dayOfWeek)
