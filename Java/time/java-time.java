------------------------
java.time				|
------------------------
	# �µ�����API
	# ���ṹ
		java.time
			|-LocalDate				//��������
			|-LocalTime				//����ʱ��
			|-LocalDateTime			//��������ʱ��
			|-Instant				//ʱ���
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

// ���쿪ʼ,����
LocalDateTime minDay = LocalDateTime.of(LocalDate.now(),LocalTime.MIN); // LocalDateTime minDay = LocalDate.now().atStartOfDay();
LocalDateTime maxDay = LocalDateTime.of(LocalDate.now(),LocalTime.MAX);

// ���ܿ�ʼ,����
LocalDateTime minWeak = LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY),LocalTime.MIN);
LocalDateTime maxWeak = LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY),LocalTime.MAX);

// ���¿�ʼ,����
LocalDate today = LocalDate.now();
LocalDateTime minMonth = LocalDateTime.of(today.withDayOfMonth(1), LocalTime.MIN);
LocalDateTime maxMonth = LocalDateTime.of(today.withDayOfMonth(today.getMonth().length(Year.isLeap(today.getYear()))),LocalTime.MAX);

// ���꿪ʼ,����
LocalDateTime minYear = LocalDateTime.of(LocalDate.now().withDayOfYear(1),LocalTime.MIN);
LocalDateTime maxYear = LocalDateTime.of(LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear()),LocalTime.MAX);


// ��ָ��ʱ����ʱ��ת��Ϊ��һ��ʱ����ʱ��
static final ZoneId AfricaLagos = ZoneId.of("Africa/Lagos"); // ��������

static final ZoneId AsiaSaigon = ZoneId.of("Asia/Saigon"); // Խ��

static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mms:ss.SSS");

public static void main(String[] args) {

	// ����ʱ��
	LocalDateTime now = LocalDateTime.now();

	System.out.println("����ʱ�䣺" + now.format(formatter));
	
	LocalDateTime memberTime = now.atZone(ZoneId.systemDefault())		// ��ǰʱ���ʱ����Դʱ����
			.withZoneSameInstant(AfricaLagos) 							// ��ǰʱ����ָ��ʱ����ʱ�䣨Ŀ��ʱ����
			.toLocalDateTime();											// ת��Ϊ LocalDateTime
	
	System.out.println("Ŀ��ʱ�䣺" + memberTime.format(formatter));
}



//----------------------
// ����ʱ�����������ڼ���
//----------------------

ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT+0800"));

// ����
now.withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
now.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999).toInstant();

//����
ZonedDateTime yesterday  = now.minusDays(1);
yesterday.withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
yesterday.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999).toInstant();

// ����
now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
now.withDayOfMonth(now.getMonth().length(Year.isLeap(now.getYear()))).withHour(23).withMinute(59).withSecond(59).withNano(999_999_999).toInstant();
