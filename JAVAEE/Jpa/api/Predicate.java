----------------------
Predicate 
----------------------
	# 描述条件的接口
		TupleElement
			|-Selection
				|-Expression
					|-Predicate
	# 抽象方法
		BooleanOperator getOperator();
			* 枚举
				AND, OR
			
		boolean isNegated();
		List<Expression<Boolean>> getExpressions();
		Predicate not();

		Predicate isNull();
		Predicate isNotNull();
		Predicate in(Object... values);
		Predicate in(Expression<?>... values);
		Predicate in(Collection<?> values);
		Predicate in(Expression<Collection<?>> values);
		<X> Expression<X> as(Class<X> type);

		Selection<X> alias(String name);
		boolean isCompoundSelection();
		List<Selection<?>> getCompoundSelectionItems();

		Class<? extends X> getJavaType();
		String getAlias();

	