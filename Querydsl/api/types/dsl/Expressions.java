--------------------------
Expressions
--------------------------
	# ���ڷ���ʵ��·��
		public final class Expressions 

	# ��̬����
		public static final NumberExpression<Integer> ONE = numberTemplate(Integer.class, "1");

		public static final NumberExpression<Integer> TWO = numberTemplate(Integer.class, "2");

		public static final NumberExpression<Integer> THREE = numberTemplate(Integer.class, "3");

		public static final NumberExpression<Integer> FOUR = numberTemplate(Integer.class, "4");

		public static final NumberExpression<Integer> ZERO = numberTemplate(Integer.class, "0");

		public static final BooleanExpression TRUE = booleanTemplate("true");

		public static final BooleanExpression FALSE = booleanTemplate("false");
	
	# ��̬����
		public static <T> SimplePath<T> path(Class<? extends T> type, String variable) 
		public static <T> SimplePath<T> path(Class<? extends T> type, Path<?> parent, String property)

		public static <T> Expression<T> constant(T value)
		


	
--------------------------
xxxTemplate
--------------------------
	# �м���
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
		
		* ���Ƕ���һЩ���Ƶ����ط���
			BooleanTemplate booleanTemplate(String template, Object... args)
			BooleanTemplate booleanTemplate(String template, List<?> args)
			BooleanTemplate booleanTemplate(Template template, Object... args)
			BooleanTemplate booleanTemplate(Template template, List<?> args)
	

--------------------------
��̬�Ļ�ȡ��ѯ�������
--------------------------
	QPlay play = QPlay.play;
	// ���ݲ�ѯ����ָ���������ͣ���ȡ����namePath
	StringPath column = Expressions.stringPath(play, "name");
	query.from(play);
	query.where(column.eq("New play") );
