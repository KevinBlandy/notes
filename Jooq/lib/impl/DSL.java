----------------------
DSL
----------------------
	# impl 包的一个静态工具类

	public static DSLContext using(SQLDialect dialect)
	public static DSLContext using(Connection connection, SQLDialect dialect) 
		* 创建 context 实例
	
	public static Condition exists(Select<?> query)
		* 构建一个exist条件语句
			EXISTS ( SELECT 1 AS `one` FROM `jooq`.`admin_role` WHERE `jooq`.`admin_role`.`admin_id` = `jooq`.`admin`.`id` )
	
	public static SelectSelectStep<Record1<Integer>> selectOne()
		* 构建一个 SELECT ONE 查询语句
	
	public static AggregateFunction<Integer> count()
		* 构建一个 SELECT COUNT(*) 语句
	
	public static AggregateFunction<Integer> count(Field<?> field)
		* 根据指定字段，构建一个 SELECT COUNT 语句
	
	public static Table<Record> table(String sql) 
		* 构建一个 table
	
	public static Field<Object> field(String sql)
		* 构建一个 Field
	