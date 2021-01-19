--------------------------
CriteriaQuery
--------------------------
	# Criteria 查询接口
		CommonAbstractCriteria
			|-AbstractQuery<T>
				|-CriteriaQuery<T>

	# 抽象方法
		CriteriaQuery<T> select(Selection<? extends T> selection);
		CriteriaQuery<T> multiselect(Selection<?>... selections);
		CriteriaQuery<T> multiselect(List<Selection<?>> selectionList);
			* 设置要检索的对象, 或者是部分字段
				criteriaQuery.select(criteriaQuery.from(User.class));
				criteriaQuery.select(criteriaQuery.from(User.class).get("name"));
			

		CriteriaQuery<T> where(Expression<Boolean> restriction);
		CriteriaQuery<T> where(Predicate... restrictions);
			* 条件

		CriteriaQuery<T> groupBy(Expression<?>... grouping);
		CriteriaQuery<T> groupBy(List<Expression<?>> grouping);
		CriteriaQuery<T> having(Expression<Boolean> restriction);
		CriteriaQuery<T> having(Predicate... restrictions);
		CriteriaQuery<T> orderBy(Order... o);
		CriteriaQuery<T> orderBy(List<Order> o);
		CriteriaQuery<T> distinct(boolean distinct);
		List<Order> getOrderList();
		Set<ParameterExpression<?>> getParameters();
		
		<X> Root<X> from(Class<X> entityClass);
		<X> Root<X> from(EntityType<X> entity);

		AbstractQuery<T> where(Expression<Boolean> restriction);
		AbstractQuery<T> where(Predicate... restrictions);
		AbstractQuery<T> groupBy(Expression<?>... grouping);
		AbstractQuery<T> groupBy(List<Expression<?>> grouping);
		AbstractQuery<T> having(Expression<Boolean> restriction);
		AbstractQuery<T> having(Predicate... restrictions);
		AbstractQuery<T> distinct(boolean distinct);
		Set<Root<?>> getRoots();
		Selection<T> getSelection();
		List<Expression<?>> getGroupList();
		Predicate getGroupRestriction();
		boolean isDistinct();
		Class<T> getResultType()

		<U> Subquery<U> subquery(Class<U> type);
		Predicate getRestriction();
			* 返回where对应的谓词, 如果没, 就返回null
		
