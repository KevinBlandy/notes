--------------------------
TemporalAdjuster
--------------------------
	# һ������ʽ�ӿڣ��������ڵ�����ʱ���������
		
		Temporal adjustInto(Temporal temporal);
		
		* TemporalAdjuster��һ������ʽ�ӿڣ����ڵ������ں�ʱ�������LocalDate��LocalTime��LocalDateTime�ȣ���ֵ��
		* ���ṩ��һ�ַ���ķ�ʽ��ִ�г��������ڵ������������罫���ڵ���Ϊ��һ�������ա�����Ϊ�·ݵ����һ��ȡ�


			LocalDate date = LocalDate.now();
			
			// ��������һ��������
			LocalDate nextFriday = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
			
			// ���������µ����һ��
			LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
			
			// ��������һ�������գ��ų���ĩ��
			LocalDate nextWorkingDay = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
			
			System.out.println("Next Friday: " + nextFriday);
			System.out.println("Last Day of Month: " + lastDayOfMonth);
			System.out.println("Next Working Day: " + nextWorkingDay);

			// --------------

			LocalDateTime date = LocalDateTime.now();

			LocalDateTime monthStart = date.with(TemporalAdjusters.firstDayOfMonth());
			LocalDateTime monthEnd = date.with(TemporalAdjusters.lastDayOfMonth());
			
			// 2024-01-01 22:22:12.818
			System.out.println(monthStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
			// 2024-01-31 22:22:12.818
			System.out.println(monthEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
			



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
