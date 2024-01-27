--------------------------
TemporalAdjuster
--------------------------
	# 一个函数式接口，用于日期调整（时间调整器）
		
		Temporal adjustInto(Temporal temporal);
		
		* TemporalAdjuster是一个函数式接口，用于调整日期和时间对象（如LocalDate、LocalTime、LocalDateTime等）的值。
		* 它提供了一种方便的方式来执行常见的日期调整操作，例如将日期调整为下一个工作日、调整为月份的最后一天等。


			LocalDate date = LocalDate.now();
			
			// 调整到下一个星期五
			LocalDate nextFriday = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
			
			// 调整到本月的最后一天
			LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
			
			// 调整到下一个工作日（排除周末）
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
