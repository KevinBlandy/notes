------------------------------
Connection					  |
------------------------------
	void close()

	Sql2o commit()
	Connection commit(boolean closeConnection)

	Sql2o rollback()
	Connection rollback(boolean closeConnection)

	Query createQuery(String queryText)
	Query createQuery(String queryText, boolean returnGeneratedKeys)
	Query createQuery(String queryText, String ... columnNames)
	Query createQueryWithParams(String queryText, Object... paramValues)

	int[] getBatchResult()
	java.sql.Connection getJdbcConnection()
	Object getKey()
	<V> V getKey(Class returnType)
	Object[] getKeys()
	<V> List<V> getKeys(Class<V> returnType)
	int getResult()
	Sql2o getSql2o()
	boolean isRollbackOnClose()
	boolean isRollbackOnException()

	
	Connection setRollbackOnClose(boolean rollbackOnClose)
	Connection setRollbackOnException(boolean rollbackOnException) 