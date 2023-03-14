----------------------
DSL
----------------------
	# impl ����һ����̬�����࣬������SQL�Ĵ󲿷ֺ�����������
		public class DSL
	
	# ���DSL�ṩ�ķ�������ͨ�õģ����м������࣬�ṩ�˲�ͬ�����µ�һЩ�ر�Api
		MySQLDSL
		PostgresDSL
		SQLiteDSL
		....

	public static DSLContext using(SQLDialect dialect)
	public static DSLContext using(Connection connection, SQLDialect dialect) 
		* ���� context ʵ��
		* �����кܶ࣬�����ø������Ƴ�ʼ��
	
	public static Asterisk asterisk()
		* ��ʾ���������ֶ�: *
			dslContext.select(asterisk()).from(ADMIN).where(ADMIN.ID.eq(UInteger.valueOf(55945))).forUpdate().fetchOneInto(Integer.class);
			// SELECT * FROM `springcloud.io`.`admin` WHERE `springcloud.io`.`admin`.`id` = ? FOR UPDATE
		* ��Ҳ�߱������������ʾ���������������ֶ�
			ADMIN.asterisk()
			// SELECT `springcloud.io`.`admin`.* FROM
	
	public static Condition exists(Select<?> query)
		* ����һ��exist�������
			EXISTS ( SELECT 1 AS `one` FROM `jooq`.`admin_role` WHERE `jooq`.`admin_role`.`admin_id` = `jooq`.`admin`.`id` )
	
	public static SelectSelectStep<Record1<Integer>> selectOne()
		* ����һ�� SELECT ONE ��ѯ���
	
	public static AggregateFunction<Integer> count()
		* ����һ�� SELECT COUNT(*) ���
	
	public static AggregateFunction<Integer> count(Field<?> field)
		* ����ָ���ֶΣ�����һ�� SELECT COUNT ���
	
	public static Table<Record> table(String sql) 
		* ����һ�� table
	
	public static Field<Object> field(String sql)
	public static <T> Field<T> field(String sql, Class<T> type, Object... bindings)
		* ����һ�� Field
		* ��N�����غ��������������Զ����ֶΣ��������ã�������{0}ռλ����������
	
	public static Field<String> concat(String... values)
		* concat ����
	
	public static Field<String> currentSchema()
		* ��ȡ��ǰ���ݿ�
	
	public static Field<String> currentSchema()
		* ��ȡ��ǰ�û�
			root@localhost


	// �ַ�����صĺ���
	public static Field<Integer> ascii(String field)
		* ת��Ϊascii��ֵ
	public static Field<String> concat(String... values)
		* �ַ���ƴ��
	public static Field<String> left(String field, int length)
		* �ַ�����ȡ����field�ĵ�һ���ַ���ʼ����ȡlengt���ַ�����
	public static Field<Integer> length(String value)
		* �����ַ�������
	public static Field<String> lower(String value)
		* ת��ΪСд
	public static Field<String> lower(String value)
		* ת��Ϊ��д
	
	// datetime ��صĺ���
	public static Field<Integer> century(Temporal value)
		* ��ʱ���л�ȡ����
	
	public static Field<Date> currentDate()
	public static Field<Time> currentTime()

	public static Field<Timestamp> currentTimestamp() 
	public static Field<Timestamp> currentTimestamp(Field<Integer> precision)
	public static Field<Timestamp> now()
	public static Field<Timestamp> now(Field<Integer> precision)
		* ��ȡ��ǰʱ���
		* ����
			precision	
				* ���þ���
	
	public static Field<LocalDate> currentLocalDate()
	public static Field<LocalTime> currentLocalTime()
	public static Field<LocalDateTime> currentLocalDateTime()
		* ��ȡ����ʱ��

	public static Field<Integer> year(Field<?> field)
	public static Field<Integer> month(Temporal value)
	public static Field<Integer> day(java.util.Date value)
	public static Field<Integer> week(Temporal value) 
		* ��ȡ��������
	
	public static Field<Integer> epoch(java.util.Date value)
		* ��ȡָ��ʱ���� 1970-01-01 00:00:00 UTC ����������
	
	public static Field<Integer> extract(Field<?> field, DatePart datePart)
		* ��ʱ������ȡ��ָ�������ݡ�������ʱ������ɶ�ġ�datePart ��ö��
	
	 public static Field<Date> toDate(String value, String format)
	 	* ʹ�� format ����Ϊ java.sql.Date ����
	public static Field<LocalDate> toLocalDate(String value, String format)
		* ʹ�� format ����Ϊ LocalDate
	public static <T> Field<T> trunc(Field<T> date, DatePart part)
		* Ĭ������£� ������ʱ��ֵ�ض�Ϊĳ��org.jooq.DatePart��DatePart.DAY�ľ��ȡ�
		* MYSQL����֧��

	
	// �������
	public static Condition noCondition()
		* ������
	
	public static True trueCondition()
		* ����һ�� true ��condition
	
	public static False falseCondition()
		* ����һ��false condition
	
	 public static Condition and(Condition left, Condition right)
	 	* and ��ϵ
	
	public static Condition condition(Record record)
		* ��һ�м�¼��װΪcondition
	
	public static Condition condition(Map<Field<?>, ?> map)
		* ��һ�м�¼��װΪcondition
	
	public static Condition condition(Field<Boolean> field)
	public static Condition condition(Boolean value) 
		* ʹ��Boolean����һ������
	
	public static Condition condition(SQL sql)
	public static Condition condition(String sql)
	public static Condition condition(String sql, QueryPart... parts) 
	public static Condition condition(String sql, Object... bindings)
		* ʹ��SQL��������
	
	public static Param<String> inline(String value)
		* ���ַ�������������Ϊ�����ַ���
		* ������filed�����ǻ��Զ�������ţ��Ա�֤SQL�﷨����ȷ�Ժ�SQLע���Ԥ��
			inline("abc'def") renders 'abc''def'
			field("abc'def") renders abc'def
	
