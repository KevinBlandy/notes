-----------------------
JPAExpressions
-----------------------
	# JPA表达式, 都是静态方法
		<T> JPQLQuery<T> select(Expression<T> expr)
		JPQLQuery<Tuple> select(Expression<?>... exprs)
		<T> JPQLQuery<T> selectDistinct(Expression<T> expr)
		JPQLQuery<Tuple> selectDistinct(Expression<?>... exprs)
		JPQLQuery<Integer> selectZero()
		JPQLQuery<Integer> selectOne()
		<T> JPQLQuery<T> selectFrom(EntityPath<T> expr)
		<A extends Comparable<? super A>> ComparableExpression<A> avg(CollectionExpression<?,A> col)
		<A extends Comparable<? super A>> ComparableExpression<A> max(CollectionExpression<?,A> left)
		<A extends Comparable<? super A>> ComparableExpression<A> min(CollectionExpression<?,A> left)

		StringExpression type(EntityPath<?> path)
	

	