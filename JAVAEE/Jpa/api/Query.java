----------------------
Query
----------------------
	# 检索接口
		List getResultList();
		Stream getResultStream()
			* 查询, 返回集合列表
			
		Object getSingleResult();
			* 查询, 唯一结果集
			* 如果结果有多条, 会抛出异常: javax.persistence.NonUniqueResultException
			* 如果结果为null, 会抛出异常: javax.persistence.NoResultException

		int executeUpdate();
			* 执行修改/删除, 返回受到影响的行数
		
		Query setFirstResult(int startPosition);
		int getFirstResult();
		Query setMaxResults(int maxResult);
		int getMaxResults();
			* FirstResult 表示开始的记录, limit 的第一个参数
			* MaxResults 表示最大的结果记录, limit 的第二个参数
			

		Query setHint(String hintName, Object value);
		Map<String, Object> getHints();

		<T> Query setParameter(Parameter<T> param, T value);
		Query setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType);
		Query setParameter(Parameter<Date> param, Date value, TemporalType temporalType);
		Query setParameter(String name, Object value);
		Query setParameter(String name, Calendar value, TemporalType temporalType);
		Query setParameter(String name, Date value, TemporalType temporalType);
		Query setParameter(int position, Object value);
		Query setParameter(int position, Calendar value, TemporalType temporalType);
		Query setParameter(int position, Date value, TemporalType temporalType);
			* 设置占位参数
			* 参数
				name			指定参数名称
				position		指定参数位置
				Parameter		指定参数位置
				TemporalType	枚举, 指定日期类型: 
					DATE
					TIME
					TIMESTAMP

		Set<Parameter<?>> getParameters();
		Parameter<?> getParameter(String name);
		<T> Parameter<T> getParameter(String name, Class<T> type);
		Parameter<?> getParameter(int position);
		<T> Parameter<T> getParameter(int position, Class<T> type);

		<T> T getParameterValue(Parameter<T> param);
		Object getParameterValue(String name);
		Object getParameterValue(int position);

		boolean isBound(Parameter<?> param);

		Query setFlushMode(FlushModeType flushMode);
		FlushModeType getFlushMode();
			* 刷新模式, 枚举
				COMMIT
				AUTO

		Query setLockMode(LockModeType lockMode);
		LockModeType getLockMode();
			* 设置锁类型
			

		<T> T unwrap(Class<T> cls);
	


----------------------
Query 条件查询
----------------------
	
