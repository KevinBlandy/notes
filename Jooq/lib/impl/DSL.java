----------------------
DSL
----------------------
	# impl 包的一个静态工具类，包含了SQL的大部分函数，操作等
		public class DSL
	
	# 这个DSL提供的方法大都是通用的，它有几个子类，提供了不同数据下的一些特别Api
		MySQLDSL
		PostgresDSL
		SQLiteDSL
		....

	public static DSLContext using(SQLDialect dialect)
	public static DSLContext using(Connection connection, SQLDialect dialect) 
		* 创建 context 实例
		* 方法有很多，可以用各种姿势初始化
	
	public static Asterisk asterisk()
		* 表示检索所有字段: *
			dslContext.select(asterisk()).from(ADMIN).where(ADMIN.ID.eq(UInteger.valueOf(55945))).forUpdate().fetchOneInto(Integer.class);
			// SELECT * FROM `springcloud.io`.`admin` WHERE `springcloud.io`.`admin`.`id` = ? FOR UPDATE
		* 表也具备这个方法，表示检索这个表的所有字段
			ADMIN.asterisk()
			// SELECT `springcloud.io`.`admin`.* FROM
	
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
	
	public static Field<String> concat(String... values)
		* concat 函数
	
