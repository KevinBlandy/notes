----------------------
DSL
----------------------
	# impl ����һ����̬������

	public static DSLContext using(SQLDialect dialect)
	public static DSLContext using(Connection connection, SQLDialect dialect) 
		* ���� context ʵ��
	
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
	