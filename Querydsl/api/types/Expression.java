-------------------------------
Expression
-------------------------------
	# ��Queryʵ���ж�����ͨ�����͵ı��ʽ
		interface Expression<T> extends Serializable 
	
	# ���󷽷�
		@Nullable
		<R,C> R accept(Visitor<R,C> v, @Nullable C context);

		Class<? extends T> getType();
	
