----------------------------
JPQLQuery
----------------------------
	# JPA ²éÑ¯½Ó¿Ú
		FilteredClause
			|-SimpleQuery,Fetchable
				|-FetchableQuery,ExtendedSubQuery
					|-JPQLQuery
		


		JPQLQuery<T> from(EntityPath<?>... sources);
		<P> JPQLQuery<T> from(CollectionExpression<?,P> target, Path<P> alias);
		<P> JPQLQuery<T> innerJoin(EntityPath<P> target);
		<P> JPQLQuery<T> innerJoin(EntityPath<P> target, Path<P> alias);
		<P> JPQLQuery<T> innerJoin(CollectionExpression<?, P> target);
		<P> JPQLQuery<T> innerJoin(CollectionExpression<?,P> target, Path<P> alias);
		<P> JPQLQuery<T> innerJoin(MapExpression<?, P> target);
		<P> JPQLQuery<T> innerJoin(MapExpression<?, P> target, Path<P> alias);
		<P> JPQLQuery<T> join(EntityPath<P> target);
		<P> JPQLQuery<T> join(EntityPath<P> target, Path<P> alias);
		<P> JPQLQuery<T> join(CollectionExpression<?,P> target);
		<P> JPQLQuery<T> join(CollectionExpression<?,P> target, Path<P> alias);
		<P> JPQLQuery<T> join(MapExpression<?, P> target);
		<P> JPQLQuery<T> join(MapExpression<?, P> target, Path<P> alias);
		<P> JPQLQuery<T> leftJoin(EntityPath<P> target);
		<P> JPQLQuery<T> leftJoin(EntityPath<P> target, Path<P> alias);
		<P> JPQLQuery<T> leftJoin(CollectionExpression<?,P> target);
		<P> JPQLQuery<T> leftJoin(CollectionExpression<?,P> target, Path<P> alias);
		<P> JPQLQuery<T> leftJoin(MapExpression<?, P> target);
		<P> JPQLQuery<T> leftJoin(MapExpression<?, P> target, Path<P> alias);
		<P> JPQLQuery<T> rightJoin(EntityPath<P> target);
		<P> JPQLQuery<T> rightJoin(EntityPath<P> target, Path<P> alias);
		<P> JPQLQuery<T> rightJoin(CollectionExpression<?,P> target);
		<P> JPQLQuery<T> rightJoin(CollectionExpression<?,P> target, Path<P> alias);
		<P> JPQLQuery<T> rightJoin(MapExpression<?, P> target);
		<P> JPQLQuery<T> rightJoin(MapExpression<?, P> target, Path<P> alias);
		JPQLQuery<T> on(Predicate... condition);
		JPQLQuery<T> fetchJoin();
		JPQLQuery<T> fetchAll();

		<U> JPQLQuery<U> select(Expression<U> expr);
		JPQLQuery<Tuple> select(Expression<?>... exprs);


