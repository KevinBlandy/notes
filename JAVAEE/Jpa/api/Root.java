--------------------
Root
--------------------
	# Root 定义查询的From子句中能出现的类型
		* AbstractQuery.from方法获得
	
	# 抽象方法
		Set<Join<X, ?>> getJoins();
		boolean isCorrelated();
		From<Z, X> getCorrelationParent();
		<Y> Join<X, Y> join(SingularAttribute<? super X, Y> attribute);
		<Y> Join<X, Y> join(SingularAttribute<? super X, Y> attribute, JoinType jt);
		<Y> CollectionJoin<X, Y> join(CollectionAttribute<? super X, Y> collection);
		<Y> SetJoin<X, Y> join(SetAttribute<? super X, Y> set);
		<Y> ListJoin<X, Y> join(ListAttribute<? super X, Y> list);
		<K, V> MapJoin<X, K, V> join(MapAttribute<? super X, K, V> map);
		<Y> CollectionJoin<X, Y> join(CollectionAttribute<? super X, Y> collection, JoinType jt);
		<Y> SetJoin<X, Y> join(SetAttribute<? super X, Y> set, JoinType jt);
		<Y> ListJoin<X, Y> join(ListAttribute<? super X, Y> list, JoinType jt);
		<K, V> MapJoin<X, K, V> join(MapAttribute<? super X, K, V> map, JoinType jt);
		<X, Y> Join<X, Y> join(String attributeName);
		<X, Y> CollectionJoin<X, Y> joinCollection(String attributeName);
		<X, Y> SetJoin<X, Y> joinSet(String attributeName);	
		<X, Y> ListJoin<X, Y> joinList(String attributeName);
		<X, K, V> MapJoin<X, K, V> joinMap(String attributeName);
		<X, Y> Join<X, Y> join(String attributeName, JoinType jt);
		<X, Y> CollectionJoin<X, Y> joinCollection(String attributeName, JoinType jt);
		<X, Y> SetJoin<X, Y> joinSet(String attributeName, JoinType jt);
		<X, Y> ListJoin<X, Y> joinList(String attributeName, JoinType jt);
		<X, K, V> MapJoin<X, K, V> joinMap(String attributeName, JoinType jt);

		Bindable<X> getModel();
		Path<?> getParentPath();
		<Y> Path<Y> get(SingularAttribute<? super X, Y> attribute);
		<E, C extends java.util.Collection<E>> Expression<C> get(PluralAttribute<X, C, E> collection);
		<K, V, M extends java.util.Map<K, V>> Expression<M> get(MapAttribute<X, K, V> map);
		Expression<Class<? extends X>> type();
		<Y> Path<Y> get(String attributeName);

		java.util.Set<Fetch<X, ?>> getFetches();
		<Y> Fetch<X, Y> fetch(SingularAttribute<? super X, Y> attribute);
		<Y> Fetch<X, Y> fetch(SingularAttribute<? super X, Y> attribute, JoinType jt);
		<Y> Fetch<X, Y> fetch(PluralAttribute<? super X, ?, Y> attribute);
		<Y> Fetch<X, Y> fetch(PluralAttribute<? super X, ?, Y> attribute, JoinType jt);
		<X, Y> Fetch<X, Y> fetch(String attributeName);
		<X, Y> Fetch<X, Y> fetch(String attributeName, JoinType jt);