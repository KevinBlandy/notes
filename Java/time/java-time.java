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
			|-MonthDay
			|-YearMonth
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


// 把指定时区的时间转换为另一个时区的时间
static final ZoneId AfricaLagos = ZoneId.of("Africa/Lagos"); // 尼日尼亚

static final ZoneId AsiaSaigon = ZoneId.of("Asia/Saigon"); // 越南

static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mms:ss.SSS");

public static void main(String[] args) {

	// 本地时间
	LocalDateTime now = LocalDateTime.now();

	System.out.println("本地时间：" + now.format(formatter));
	
	LocalDateTime memberTime = now.atZone(ZoneId.systemDefault())		// 当前时间的时区（源时区）
			.withZoneSameInstant(AfricaLagos) 							// 当前时间在指定时区的时间（目标时区）
			.toLocalDateTime();											// 转换为 LocalDateTime
	
	System.out.println("目标时间：" + memberTime.format(formatter));
}

