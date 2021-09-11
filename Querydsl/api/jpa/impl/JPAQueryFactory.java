---------------------------
JPAQueryFactory
---------------------------
	# 查询工厂类
		FilteredClause
			|-SimpleQuery
				|-Query
					|-QueryFactory
						|-JPQLQueryFactory
							|-JPAQueryFactory
	

		DeleteClause<?> delete(EntityPath<?> path);

		<T> JPQLQuery<T> select(Expression<T> expr);
		JPQLQuery<Tuple> select(Expression<?>... exprs);
		<T> JPQLQuery<T> selectDistinct(Expression<T> expr);
		JPQLQuery<Tuple> selectDistinct(Expression<?>... exprs);

		JPQLQuery<Integer> selectOne();
		JPQLQuery<Integer> selectZero();

		<T> JPQLQuery<T> selectFrom(EntityPath<T> from);

		JPQLQuery<?> from(EntityPath<?> from);
		JPQLQuery<?> from(EntityPath<?>... from);

		UpdateClause<?> update(EntityPath<?> path);
	
		
		Q query();
		Q groupBy(Expression<?>... o);
		Q having(Predicate... o);

		Q limit(@Nonnegative long limit);
		Q offset(@Nonnegative long offset);

		Q restrict(QueryModifiers modifiers);

		Q orderBy(OrderSpecifier<?>... o);

		<T> Q set(ParamExpression<T> param, T value);

		Q distinct();

		C where(Predicate... o);


