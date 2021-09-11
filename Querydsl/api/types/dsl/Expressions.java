--------------------------
Expressions
--------------------------
	# 用于访问实体路径
		public final class Expressions 

	# 静态变量
		public static final NumberExpression<Integer> ONE = numberTemplate(Integer.class, "1");

		public static final NumberExpression<Integer> TWO = numberTemplate(Integer.class, "2");

		public static final NumberExpression<Integer> THREE = numberTemplate(Integer.class, "3");

		public static final NumberExpression<Integer> FOUR = numberTemplate(Integer.class, "4");

		public static final NumberExpression<Integer> ZERO = numberTemplate(Integer.class, "0");

		public static final BooleanExpression TRUE = booleanTemplate("true");

		public static final BooleanExpression FALSE = booleanTemplate("false");
	
	# 静态方法
		public static <T> SimplePath<T> path(Class<? extends T> type, String variable) 
		public static <T> SimplePath<T> path(Class<? extends T> type, Path<?> parent, String property)

		public static <T> Expression<T> constant(T value)
		


	
--------------------------
xxxTemplate
--------------------------
	# 有几种
		template
		stringTemplate
		numberTemplate
		enumTemplate
		dslTemplate
		dateTimeTemplate
		dateTemplate
		comparableTemplate
		simpleTemplate
		booleanTemplate
		TimeTemplate
		
		* 它们都有一些类似的重载方法
			BooleanTemplate booleanTemplate(String template, Object... args)
			BooleanTemplate booleanTemplate(String template, List<?> args)
			BooleanTemplate booleanTemplate(Template template, Object... args)
			BooleanTemplate booleanTemplate(Template template, List<?> args)
	

--------------------------
动态的获取查询对象的列
--------------------------
	QPlay play = QPlay.play;
	// 根据查询对象，指定数据类型，获取它的namePath
	StringPath column = Expressions.stringPath(play, "name");
	query.from(play);
	query.where(column.eq("New play") );
