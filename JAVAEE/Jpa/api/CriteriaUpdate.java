------------------
Criteria更新
------------------
	# 更新查询
		CommonAbstractCriteria
			|-CriteriaUpdate
	
	# 抽象方法
		Root<T> from(Class<T> entityClass);
		Root<T> from(EntityType<T> entity);
		Root<T> getRoot();
		<Y, X extends Y> CriteriaUpdate<T> set( SingularAttribute<? super T, Y> attribute, X value);
		<Y> CriteriaUpdate<T> set(SingularAttribute<? super T, Y> attribute, Expression<? extends Y> value);
		<Y, X extends Y> CriteriaUpdate<T> set(Path<Y> attribute, X value);
		<Y> CriteriaUpdate<T> set(Path<Y> attribute, Expression<? extends Y> value);
		CriteriaUpdate<T> set(String attributeName, Object value);
		CriteriaUpdate<T> where(Expression<Boolean> restriction);
		CriteriaUpdate<T> where(Predicate... restrictions);

		<U> Subquery<U> subquery(Class<U> type);
		Predicate getRestriction();
	

