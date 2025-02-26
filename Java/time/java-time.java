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
LocalDateTime minMonth = LocalDateTime.of(LocalDate.now().withDayOfMonth(1),LocalTime.MIN);
LocalDateTime maxMonth = LocalDateTime.of(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()),LocalTime.MAX);

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

