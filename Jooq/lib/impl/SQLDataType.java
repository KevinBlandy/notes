---------------------------
SQLDataType
---------------------------
	# SQL数据类型
		public final class SQLDataType 
	
	 public static final DataType<String> VARCHAR = new DefaultDataType<>(null, String.class, "varchar(l)");
	 public static final DataType<String> VARCHAR(int length)

	 public static final DataType<String> CHAR = new DefaultDataType<>(null, String.class, "char(l)");
	 public static final DataType<String> CHAR(int length)

	 public static final DataType<String> LONGVARCHAR = new DefaultDataType<>(null, String.class, "longvarchar(l)");
	 public static final DataType<String> LONGVARCHAR(int length)

	 public static final DataType<String> CLOB = new DefaultDataType<>(null, String.class, "clob");
	 public static final DataType<String> CLOB(int length)

	 public static final DataType<String> NVARCHAR = new DefaultDataType<>(null, String.class, "nvarchar(l)")
	 public static final DataType<String> NVARCHAR(int length)

	 public static final DataType<String> NCHAR = new DefaultDataType<>(null, String.class, "nchar(l)")
	 public static final DataType<String> NCHAR(int length)

	 public static final DataType<String> LONGNVARCHAR = new DefaultDataType<>(null, String.class, "longnvarchar(l)")
	 public static final DataType<String> LONGNVARCHAR(int length)

	 public static final DataType<String> NCLOB = new DefaultDataType<>(null, String.class, "nclob")
	 public static final DataType<String> NCLOB(int length)

	 public static final DataType<Boolean> BOOLEAN = new DefaultDataType<>(null, Boolean.class, "boolean");
	 public static final DataType<Boolean> BIT = new DefaultDataType<>(null, Boolean.class, "bit");
	 public static final DataType<Byte> TINYINT = new DefaultDataType<>(null, Byte.class, "tinyint");
	 public static final DataType<Short> SMALLINT = new DefaultDataType<>(null, Short.class, "smallint");
	 public static final DataType<Integer> INTEGER = new DefaultDataType<>(null, Integer.class, "integer");
	 public static final DataType<Long> BIGINT = new DefaultDataType<>(null, Long.class, "bigint");

	 public static final DataType<BigInteger> DECIMAL_INTEGER = new DefaultDataType<>(null, BigInteger.class, "decimal_integer");
	 public static final DataType<BigInteger> DECIMAL_INTEGER(int precision) 

	 public static final DataType<UByte> TINYINTUNSIGNED = new DefaultDataType<>(null, UByte.class, "tinyint unsigned");
	 public static final DataType<UShort> SMALLINTUNSIGNED = new DefaultDataType<>(null, UShort.class, "smallint unsigned");
	 public static final DataType<UInteger> INTEGERUNSIGNED = new DefaultDataType<>(null, UInteger.class, "integer unsigned");
	 public static final DataType<ULong> BIGINTUNSIGNED = new DefaultDataType<>(null, ULong.class, "bigint unsigned");

	public static final DataType<Double> DOUBLE = new DefaultDataType<>(null, Double.class, "double");
	public static final DataType<Double> FLOAT = new DefaultDataType<>(null, Double.class, "float");
	public static final DataType<Float> REAL = new DefaultDataType<>(null, Float.class, "real");

	public static final DataType<BigDecimal> NUMERIC = new DefaultDataType<>(null, BigDecimal.class, "numeric(p, s)");
	public static final DataType<BigDecimal> NUMERIC(int precision)
	public static final DataType<BigDecimal> NUMERIC(int precision, int scale)

	public static final DataType<BigDecimal> DECIMAL = new DefaultDataType<>(null, BigDecimal.class, "decimal(p, s)")
	public static final DataType<BigDecimal> DECIMAL(int precision)

	public static final DataType<BigDecimal> DECIMAL(int precision, int scale)
	public static final DataType<Date> DATE = new DefaultDataType<>(null, Date.class, "date")

	public static final DataType<Timestamp> TIMESTAMP = new DefaultDataType<>(null, Timestamp.class, "timestamp(p)");
	public static final DataType<Timestamp> TIMESTAMP(int precision)

	public static final DataType<Time> TIME = new DefaultDataType<>(null, Time.class, "time(p)");
	public static final DataType<Time> TIME(int precision)

	public static final DataType<YearToSecond> INTERVAL = new DefaultDataType<>(null, YearToSecond.class, "interval");
	public static final DataType<YearToMonth> INTERVALYEARTOMONTH = new DefaultDataType<>(null, YearToMonth.class, "interval year to month");

	public static final DataType<DayToSecond> INTERVALDAYTOSECOND = new DefaultDataType<>(null, DayToSecond.class, "interval day to second");
	public static final DataType<LocalDate> LOCALDATE = new DefaultDataType<>(null, LocalDate.class, "date");

	public static final DataType<LocalTime> LOCALTIME = new DefaultDataType<>(null, LocalTime.class, "time(p)");
	public static final DataType<LocalTime> LOCALTIME(int precision)

	public static final DataType<LocalDateTime> LOCALDATETIME = new DefaultDataType<>(null, LocalDateTime.class, "timestamp(p)");
	public static final DataType<LocalDateTime> LOCALDATETIME(int precision)

	public static final DataType<OffsetTime> OFFSETTIME = new DefaultDataType<>(null, OffsetTime.class, "time(p) with time zone");
	public static final DataType<OffsetTime> OFFSETTIME(int precision)

	public static final DataType<OffsetDateTime> OFFSETDATETIME = new DefaultDataType<>(null, OffsetDateTime.class, "timestamp(p) with time zone");
	public static final DataType<OffsetDateTime> OFFSETDATETIME(int precision)

	public static final DataType<OffsetTime> TIMEWITHTIMEZONE = OFFSETTIME;

	public static final DataType<OffsetTime> TIMEWITHTIMEZONE(int precision)
	public static final DataType<OffsetDateTime> TIMESTAMPWITHTIMEZONE = OFFSETDATETIME;

	public static final DataType<OffsetDateTime> TIMESTAMPWITHTIMEZONE(int precision)
	public static final DataType<Instant> INSTANT = new DefaultDataType<>(null, Instant.class, "instant");
	public static final DataType<Instant> INSTANT(int precision)
	public static final DataType<byte[]> BINARY = new DefaultDataType<>(null, byte[].class, "binary(l)");
	public static final DataType<byte[]> BINARY(int length)
	public static final DataType<byte[]> VARBINARY = new DefaultDataType<>(null, byte[].class, "varbinary(l)")
	public static final DataType<byte[]> VARBINARY(int length)
	public static final DataType<byte[]> LONGVARBINARY = new DefaultDataType<>(null, byte[].class, "longvarbinary(l)");
	public static final DataType<byte[]> LONGVARBINARY(int length)
	public static final DataType<byte[]> BLOB = new DefaultDataType<>(null, byte[].class, "blob")
	public static final DataType<byte[]> BLOB(int length)
	public static final DataType<Object> OTHER = new DefaultDataType<>(null, Object.class, "other")
	public static final DataType<RowId> ROWID = new DefaultDataType<>(null, RowId.class, "rowid");
	public static final DataType<Record> RECORD = new DefaultDataType<>(null, Record.class, "record");
	public static final DataType<Result<Record>> RESULT = new DefaultDataType<>(null, (Class) Result.class, "result");
	public static final DataType<UUID> UUID = new DefaultDataType<>(null, UUID.class, "uuid");
	public static final DataType<JSON> JSON = new DefaultDataType<>(null, JSON.class, "json");
	public static final DataType<JSONB> JSONB = new DefaultDataType<>(null, JSONB.class, "jsonb");
	public static final DataType<XML> XML = new DefaultDataType<>(null, XML.class, "xml");

