------------------------
java.time				|
------------------------
	# 新的日期API
	# 包结构
		java.time
			|-LocalDate				//本地日期
			|-LocalTime				//本地时间
			|-LocalDateTime			//本地日期时间
			|-Instant				//时间戳
			|-DayOfWeek
			|-Year
			|-Month
		java.time.chrono
		java.time.format
			|-DateTimeFormatter
			|-DateTimeFormatterBuilder
			|-DecimalStyle
		java.time.temporal
			|-Temporal
			|-ChronoField
			|-ChronoUnit
			|-TemporalAdjuster
		java.time.zone
		

------------------------
java.time demos			|
------------------------

// 当天开始,结束
LocalDateTime minDay = LocalDateTime.of(LocalDate.now(),LocalTime.MIN); // LocalDateTime minDay = LocalDate.now().atStartOfDay();
LocalDateTime maxDay = LocalDateTime.of(LocalDate.now(),LocalTime.MAX);

// 本周开始,结束
LocalDateTime minWeak = LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY),LocalTime.MIN);
LocalDateTime maxWeak = LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY),LocalTime.MAX);

// 本月开始,结束
LocalDateTime minMonth = LocalDateTime.of(LocalDate.now().withDayOfMonth(1),LocalTime.MIN);
LocalDateTime maxMonth = LocalDateTime.of(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()),LocalTime.MAX);

// 本年开始,结束
LocalDateTime minYear = LocalDateTime.of(LocalDate.now().withDayOfYear(1),LocalTime.MIN);
LocalDateTime maxYear = LocalDateTime.of(LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear()),LocalTime.MAX);



