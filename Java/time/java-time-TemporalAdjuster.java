--------------------------
TemporalAdjuster
--------------------------
	# һ���ӿڣ��������ڵ�����ʱ���������
		Temporal adjustInto(Temporal temporal);


--------------------------
TemporalAdjusters
--------------------------
	# �ṩ��N���ʵ��
	# ��̬����
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
