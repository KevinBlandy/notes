--------------------
ExpressionUtils
--------------------
	# 关系表达式的工具类

	# 静态方法
		<T> Operation<T> operation(Class<? extends T> type, Operator operator, Expression<?>... args) 
		<T> Operation<T> operation(Class<? extends T> type, Operator operator, ImmutableList<Expression<?>> args)


