--------------------
JPAQuery
--------------------
	# JPA检索对象
	
	# 实例方法
		List<T> fetch()
			* 检索所有记录

		T fetchOne() throws NonUniqueResultException
			* 检索一条记录, 如果记录不存在, 或者记录不是唯一的, 会抛出异常
		
		T fetchFirst()
			* 仅仅检索第一条记录, 记录不存在, 返回 null
			* SQL语句: SELECT .... LIMIT 1;

		QueryResults<T> fetchResults()
			* 返回分页信息
			* QueryResults 对象, 里面包含了分页信息
				private final long limit, offset, total;
				private final List<T> results;
		
		long fetchCount()
			* 返回结果集的总记录数量
		
