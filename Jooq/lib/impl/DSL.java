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
		* ����һ�� Field
	
	public static Field<String> concat(String... values)
		* concat ����
	
