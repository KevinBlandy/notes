------------------------------------
DSLContext
------------------------------------
	# 核心库
		interface DSLContext extends Scope


------------------------------------
DSLContext
------------------------------------
	Configuration configuration();
		* 获取到配置信息
	
	SelectSelectStep<Record1<Integer>> selectCount();
		* 执行count查询
	
	int fetchCount(Select<?> query) throws DataAccessException;
		* 检索 query 的 count查询
	
	boolean fetchExists(Select<?> query)
		* 执行 query 的 exists查询

	<R extends Record> R fetchOne(Table<R> table, Condition condition) throws DataAccessException, TooManyRowsException;
		* 检索 一条记录
	
	int fetchCount(Table<?> table, Collection<? extends Condition> conditions) throws DataAccessException;
		* 查询指定Table的总数量，根据condition
	
	<R extends Record> R newRecord(Table<R> table, Object source);
		* 创建新的表记录对象，使用 source 填充数据
	
	<T> T connectionResult(ConnectionCallable<T> callable);
	void connection(ConnectionRunnable runnable);
		* 获取到JDBC Connection 对象
	
	
	<R extends Record> R newRecord(Table<R> table, Object source);
		* 创建Record对象，使用 source填充
	
