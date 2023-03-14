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
	public static <T> Field<T> field(String sql, Class<T> type, Object... bindings)
		* 构建一个 Field
		* 有N多重载函数，可以用来自定义字段，函数调用（可以用{0}占位符填充参数）
	
	public static Field<String> concat(String... values)
		* concat 函数
	
	public static Field<String> currentSchema()
		* 获取当前数据库
	
	public static Field<String> currentSchema()
		* 获取当前用户
			root@localhost


	// 字符串相关的函数
	public static Field<Integer> ascii(String field)
		* 转换为ascii数值
	public static Field<String> concat(String... values)
		* 字符串拼接
	public static Field<String> left(String field, int length)
		* 字符串截取，从field的第一个字符开始，截取lengt个字符返回
	public static Field<Integer> length(String value)
		* 返回字符串长度
	public static Field<String> lower(String value)
		* 转换为小写
	public static Field<String> lower(String value)
		* 转换为大写
	
	// datetime 相关的函数
	public static Field<Integer> century(Temporal value)
		* 从时间中获取世纪
	
	public static Field<Date> currentDate()
	public static Field<Time> currentTime()

	public static Field<Timestamp> currentTimestamp() 
	public static Field<Timestamp> currentTimestamp(Field<Integer> precision)
	public static Field<Timestamp> now()
	public static Field<Timestamp> now(Field<Integer> precision)
		* 获取当前时间戳
		* 参数
			precision	
				* 设置精度
	
	public static Field<LocalDate> currentLocalDate()
	public static Field<LocalTime> currentLocalTime()
	public static Field<LocalDateTime> currentLocalDateTime()
		* 获取本地时间

	public static Field<Integer> year(Field<?> field)
	public static Field<Integer> month(Temporal value)
	public static Field<Integer> day(java.util.Date value)
	public static Field<Integer> week(Temporal value) 
		* 获取年月日周
	
	public static Field<Integer> epoch(java.util.Date value)
		* 获取指定时间自 1970-01-01 00:00:00 UTC 以来的秒数
	
	public static Field<Integer> extract(Field<?> field, DatePart datePart)
		* 从时间中提取出指定的数据。年月日时分秒周啥的。datePart 是枚举
	
	 public static Field<Date> toDate(String value, String format)
	 	* 使用 format 解析为 java.sql.Date 类型
	public static Field<LocalDate> toLocalDate(String value, String format)
		* 使用 format 解析为 LocalDate
	public static <T> Field<T> trunc(Field<T> date, DatePart part)
		* 默认情况下， 将日期时间值截断为某个org.jooq.DatePart或DatePart.DAY的精度。
		* MYSQL好像不支持

	
	// 条件相关
	public static Condition noCondition()
		* 无条件
	
	public static True trueCondition()
		* 返回一个 true 的condition
	
	public static False falseCondition()
		* 返回一个false condition
	
	 public static Condition and(Condition left, Condition right)
	 	* and 关系
	
	public static Condition condition(Record record)
		* 把一行记录封装为condition
	
	public static Condition condition(Map<Field<?>, ?> map)
		* 把一行记录封装为condition
	
	public static Condition condition(Field<Boolean> field)
	public static Condition condition(Boolean value) 
		* 使用Boolean构建一个条件
	
	public static Condition condition(SQL sql)
	public static Condition condition(String sql)
	public static Condition condition(String sql, QueryPart... parts) 
	public static Condition condition(String sql, Object... bindings)
		* 使用SQL构建条件
	
	public static Param<String> inline(String value)
		* 把字符串参数，包裹为内联字符串
		* 类似于filed，但是会自动添加引号，以保证SQL语法的正确性和SQL注入的预防
			inline("abc'def") renders 'abc''def'
			field("abc'def") renders abc'def
	
