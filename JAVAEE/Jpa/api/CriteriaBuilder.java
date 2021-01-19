--------------------------------------
CriteriaBuilder
--------------------------------------
	# Criteria 检索接口的builder
	# 抽象方法
		CriteriaQuery<Object> createQuery();
		<T> CriteriaQuery<T> createQuery(Class<T> resultClass);

		CriteriaQuery<Tuple> createTupleQuery();

		<T> CriteriaUpdate<T> createCriteriaUpdate(Class<T> targetEntity);
		<T> CriteriaDelete<T> createCriteriaDelete(Class<T> targetEntity);

		<Y> CompoundSelection<Y> construct(Class<Y> resultClass, Selection<?>... selections);
		CompoundSelection<Tuple> tuple(Selection<?>... selections);

		CompoundSelection<Object[]> array(Selection<?>... selections);

		Order asc(Expression<?> x);
		Order desc(Expression<?> x);

		<N extends Number> Expression<Double> avg(Expression<N> x);
		<N extends Number> Expression<N> sum(Expression<N> x);
		Expression<Long> sumAsLong(Expression<Integer> x);
		Expression<Double> sumAsDouble(Expression<Float> x);
		<N extends Number> Expression<N> max(Expression<N> x);
		<N extends Number> Expression<N> min(Expression<N> x);

		<X extends Comparable<? super X>> Expression<X> greatest(Expression<X> x);

		<X extends Comparable<? super X>> Expression<X> least(Expression<X> x);

		Expression<Long> count(Expression<?> x);
		Expression<Long> countDistinct(Expression<?> x);
			* count查询

		Predicate exists(Subquery<?> subquery);

		<Y> Expression<Y> all(Subquery<Y> subquery);
		<Y> Expression<Y> some(Subquery<Y> subquery);
		<Y> Expression<Y> any(Subquery<Y> subquery);

		Predicate and(Expression<Boolean> x, Expression<Boolean> y);
		Predicate and(Predicate... restrictions);
		Predicate or(Expression<Boolean> x, Expression<Boolean> y);
		Predicate or(Predicate... restrictions);
		Predicate not(Expression<Boolean> restriction);
		Predicate conjunction();
		Predicate disjunction();

		Predicate isTrue(Expression<Boolean> x);
		Predicate isFalse(Expression<Boolean> x);
			* boolean 匹配

		Predicate isNull(Expression<?> x);
		Predicate isNotNull(Expression<?> x);
			* null 匹配

		Predicate equal(Expression<?> x, Expression<?> y);
		Predicate equal(Expression<?> x, Object y);
		Predicate notEqual(Expression<?> x, Expression<?> y);
		Predicate notEqual(Expression<?> x, Object y);
			* 精准匹配

		<Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> x, Expression<? extends Y> y);
		<Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> x, Y y);
		<Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> x, Expression<? extends Y> y);
		<Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> x, Y y);
		<Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> x, Expression<? extends Y> y);
		<Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> x, Y y);
		<Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> x, Expression<? extends Y> y);
		<Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> x, Y y);

		<Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> v, Expression<? extends Y> x, Expression<? extends Y> y);
		<Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> v, Y x, Y y);

		Predicate gt(Expression<? extends Number> x, Expression<? extends Number> y);
		Predicate gt(Expression<? extends Number> x, Number y);
		Predicate ge(Expression<? extends Number> x, Expression<? extends Number> y);
		Predicate ge(Expression<? extends Number> x, Number y);
		Predicate lt(Expression<? extends Number> x, Expression<? extends Number> y);
		Predicate lt(Expression<? extends Number> x, Number y);
		Predicate le(Expression<? extends Number> x, Expression<? extends Number> y);
		Predicate le(Expression<? extends Number> x, Number y);
			* 数值关系计算

		<N extends Number> Expression<N> neg(Expression<N> x);
		<N extends Number> Expression<N> abs(Expression<N> x);
		<N extends Number> Expression<N> sum(Expression<? extends N> x, Expression<? extends N> y);
		<N extends Number> Expression<N> sum(Expression<? extends N> x, N y);
		<N extends Number> Expression<N> sum(N x, Expression<? extends N> y);
		<N extends Number> Expression<N> prod(Expression<? extends N> x, Expression<? extends N> y);
		<N extends Number> Expression<N> prod(Expression<? extends N> x, N y);
		<N extends Number> Expression<N> prod(N x, Expression<? extends N> y);
		<N extends Number> Expression<N> diff(Expression<? extends N> x, Expression<? extends N> y);
		<N extends Number> Expression<N> diff(Expression<? extends N> x, N y);
		<N extends Number> Expression<N> diff(N x, Expression<? extends N> y);

		Expression<Number> quot(Expression<? extends Number> x, Expression<? extends Number> y);
		Expression<Number> quot(Expression<? extends Number> x, Number y);
		Expression<Number> quot(Number x, Expression<? extends Number> y);
		Expression<Integer> mod(Expression<Integer> x, Expression<Integer> y);
		Expression<Integer> mod(Expression<Integer> x, Integer y);
		Expression<Integer> mod(Integer x, Expression<Integer> y);
		Expression<Double> sqrt(Expression<? extends Number> x);

		Expression<Long> toLong(Expression<? extends Number> number);
		Expression<Integer> toInteger(Expression<? extends Number> number);
		Expression<Float> toFloat(Expression<? extends Number> number);
		Expression<Double> toDouble(Expression<? extends Number> number);
		Expression<BigDecimal> toBigDecimal(Expression<? extends Number> number);
		Expression<BigInteger> toBigInteger(Expression<? extends Number> number);
		Expression<String> toString(Expression<Character> character);
		<T> Expression<T> literal(T value);
		<T> Expression<T> nullLiteral(Class<T> resultClass);
		<T> ParameterExpression<T> parameter(Class<T> paramClass);
		<T> ParameterExpression<T> parameter(Class<T> paramClass, String name);
		<C extends Collection<?>> Predicate isEmpty(Expression<C> collection);
		<C extends Collection<?>> Predicate isNotEmpty(Expression<C> collection);
		<C extends java.util.Collection<?>> Expression<Integer> size(Expression<C> collection);
		<C extends Collection<?>> Expression<Integer> size(C collection);
		<E, C extends Collection<E>> Predicate isMember(Expression<E> elem, Expression<C> collection);
		<E, C extends Collection<E>> Predicate isMember(E elem, Expression<C> collection);
		<E, C extends Collection<E>> Predicate isNotMember(Expression<E> elem, Expression<C> collection);
		<E, C extends Collection<E>> Predicate isNotMember(E elem, Expression<C> collection);
		<V, M extends Map<?, V>> Expression<Collection<V>> values(M map);
		<K, M extends Map<K, ?>> Expression<Set<K>> keys(M map);

		Predicate like(Expression<String> x, Expression<String> pattern);
		Predicate like(Expression<String> x, String pattern);
		Predicate like(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar);
		Predicate like(Expression<String> x, Expression<String> pattern, char escapeChar);
		Predicate like(Expression<String> x, String pattern, Expression<Character> escapeChar);
		Predicate like(Expression<String> x, String pattern, char escapeChar);
		Predicate notLike(Expression<String> x, Expression<String> pattern);
		Predicate notLike(Expression<String> x, String pattern);
		Predicate notLike(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar);
		Predicate notLike(Expression<String> x, Expression<String> pattern, char escapeChar);
		Predicate notLike(Expression<String> x, String pattern, Expression<Character> escapeChar);
		Predicate notLike(Expression<String> x, String pattern, char escapeChar);
			* LIKE 关系, 需要自己对值添加 %% 符号
				
		Expression<String> concat(Expression<String> x, Expression<String> y);
		Expression<String> concat(Expression<String> x, String y);
		Expression<String> concat(String x, Expression<String> y);
		Expression<String> substring(Expression<String> x, Expression<Integer> from);
		Expression<String> substring(Expression<String> x, int from);
		Expression<String> substring(Expression<String> x, Expression<Integer> from, Expression<Integer> len);
		Expression<String> substring(Expression<String> x, int from, int len);
		Expression<String> trim(Expression<String> x);
		Expression<String> trim(Trimspec ts, Expression<String> x);
		Expression<String> trim(Expression<Character> t, Expression<String> x);
		Expression<String> trim(Trimspec ts, Expression<Character> t, Expression<String> x);
		Expression<String> trim(char t, Expression<String> x);
		Expression<String> trim(Trimspec ts, char t, Expression<String> x);
		Expression<String> lower(Expression<String> x);
		Expression<String> upper(Expression<String> x);
		Expression<Integer> length(Expression<String> x);
		Expression<Integer> locate(Expression<String> x, Expression<String> pattern);
		Expression<Integer> locate(Expression<String> x, String pattern);
		Expression<Integer> locate(Expression<String> x, Expression<String> pattern, Expression<Integer> from);
		Expression<Integer> locate(Expression<String> x, String pattern, int from);
		Expression<java.sql.Date> currentDate();
		Expression<java.sql.Timestamp> currentTimestamp();
		Expression<java.sql.Time> currentTime();

		<T> In<T> in(Expression<? extends T> expression);
			* IN 匹配

		<Y> Expression<Y> coalesce(Expression<? extends Y> x, Expression<? extends Y> y);
		<Y> Expression<Y> coalesce(Expression<? extends Y> x, Y y);
		<Y> Expression<Y> nullif(Expression<Y> x, Expression<?> y);
		<Y> Expression<Y> nullif(Expression<Y> x, Y y);
		<T> Coalesce<T> coalesce();
		<C, R> SimpleCase<C,R> selectCase(Expression<? extends C> expression);
		<R> Case<R> selectCase();
		<T> Expression<T> function(String name, Class<T> type, Expression<?>... args);
		<X, T, V extends T> Join<X, V> treat(Join<X, T> join, Class<V> type);
		<X, T, E extends T> CollectionJoin<X, E> treat(CollectionJoin<X, T> join, Class<E> type);
		<X, T, E extends T> SetJoin<X, E> treat(SetJoin<X, T> join, Class<E> type);
		<X, T, E extends T> ListJoin<X, E> treat(ListJoin<X, T> join, Class<E> type);
		<X, K, T, V extends T> MapJoin<X, K, V> treat(MapJoin<X, K, T> join, Class<V> type);
		<X, T extends X> Path<T> treat(Path<X> path, Class<T> type);
		<X, T extends X> Root<T> treat(Root<X> root, Class<T> type);

