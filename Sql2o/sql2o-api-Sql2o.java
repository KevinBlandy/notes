-----------------------------
Sql2o						 |
-----------------------------
	Connection beginTransaction()
	Connection beginTransaction(ConnectionSource connectionSource)
	Connection beginTransaction(ConnectionSource connectionSource, int isolationLevel)
	Connection beginTransaction(int isolationLevel)

	ConnectionSource getConnectionSource()
	void setConnectionSource(ConnectionSource connectionSource)


	Map<String, String> getDefaultColumnMappings()
	void setDefaultColumnMappings(Map<String, String> defaultColumnMappings)

	Quirks getQuirks()

	boolean isDefaultCaseSensitive()
	void setDefaultCaseSensitive(boolean defaultCaseSensitive)

	Connection open()
	Connection open(ConnectionSource connectionSource)

	void runInTransaction(StatementRunnable runnable)
	void runInTransaction(StatementRunnable runnable, Object argument)
	void runInTransaction(StatementRunnable runnable, Object argument, int isolationLevel)

	<V> V runInTransaction(StatementRunnableWithResult<V> runnableWithResult)
	<V> V runInTransaction(StatementRunnableWithResult<V> runnableWithResult, Object argument)
	<V> V runInTransaction(StatementRunnableWithResult<V> runnableWithResult, Object argument, int isolationLevel)

	void withConnection(StatementRunnable runnable) 
	void withConnection(StatementRunnable runnable, Object argument)
	<V> V withConnection(StatementRunnableWithResult<V> runnable)
	<V> V withConnection(StatementRunnableWithResult<V> runnable, Object argument)
