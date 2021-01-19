---------------
TypedQuery
---------------
	# 继承接口: Query, 添加了一个泛型, 用于指定返回的数据类型
		
		List<X> getResultList();

		Stream<X> getResultStream()

		X getSingleResult();

		TypedQuery<X> setMaxResults(int maxResult);
		TypedQuery<X> setFirstResult(int startPosition);
		TypedQuery<X> setHint(String hintName, Object value);

		<T> TypedQuery<X> setParameter(Parameter<T> param, T value);
		TypedQuery<X> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType);
		TypedQuery<X> setParameter(Parameter<Date> param, Date value,  TemporalType temporalType);
		TypedQuery<X> setParameter(String name, Object value);
		TypedQuery<X> setParameter(String name, Calendar value, TemporalType temporalType);
		TypedQuery<X> setParameter(String name, Date value,  TemporalType temporalType);
		TypedQuery<X> setParameter(int position, Object value);
		TypedQuery<X> setParameter(int position, Calendar value,  TemporalType temporalType);
		TypedQuery<X> setParameter(int position, Date value, TemporalType temporalType);

		TypedQuery<X> setFlushMode(FlushModeType flushMode);

		TypedQuery<X> setLockMode(LockModeType lockMode);
