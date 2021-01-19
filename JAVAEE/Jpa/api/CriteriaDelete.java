---------------------
Criteria删除
---------------------
	# 删除接口
		CommonAbstractCriteria
			|-CriteriaDelete
	
	# 抽象方法
		Root<T> from(Class<T> entityClass);
		Root<T> from(EntityType<T> entity);
		Root<T> getRoot();
		CriteriaDelete<T> where(Expression<Boolean> restriction);
		CriteriaDelete<T> where(Predicate... restrictions);

		<U> Subquery<U> subquery(Class<U> type);
		Predicate getRestriction();