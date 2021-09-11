----------------------
Projections
----------------------
	# 映射结果集为自定义对象

	# 静态方法

		<T> ArrayConstructorExpression<T> array(Class<T[]> type, Expression<T>... exprs)

		<T> AppendingFactoryExpression<T> appending(Expression<T> base, Expression<?>... rest)

		<T> QBean<T> bean(Class<? extends T> type, Expression<?>... exprs)
		<T> QBean<T> bean(Path<? extends T> type, Expression<?>... exprs)
		<T> QBean<T> bean(Path<? extends T> type, Map<String, ? extends Expression<?>> bindings)
		<T> QBean<T> bean(Class<? extends T> type, Map<String, ? extends Expression<?>> bindings)

		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Expression<?>... exprs)
		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Class<?>[] paramTypes, Expression<?>... exprs)
		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Class<?>[] paramTypes, ImmutableList<Expression<?>> exprs)

		<T> QBean<T> fields(Class<? extends T> type, Expression<?>... exprs)
		<T> QBean<T> fields(Path<? extends T> type, Expression<?>... exprs)
		<T> QBean<T> fields(Path<? extends T> type, Map<String, ? extends Expression<?>> bindings)
		<T> QBean<T> fields(Class<? extends T> type, Map<String, ? extends Expression<?>> bindings)

		QList list(Expression<?>... args)
		QList list(Expression<?>[]... args)

		QMap map(Expression<?>... exprs)

		QTuple tuple(Expression<?>... exprs)
		QTuple tuple(ImmutableList<Expression<?>> exprs)
		QTuple tuple(Expression<?>[]... exprs)
	


