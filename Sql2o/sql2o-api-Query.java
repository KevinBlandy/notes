-------------------------
Query					 |
-------------------------
	Query(Connection connection, String queryText, boolean returnGeneratedKeys) 
	Query(Connection connection, String queryText, String[] columnNames)


	Query addColumnMapping(String columnName, String propertyName)

	Query addParameter(String name, final Boolean value)
	Query addParameter(String name, final boolean value) 
	<T> Query addParameter(String name, Class<T> parameterClass, T value)
	Query addParameter(String name, final Collection<?> values)
	Query addParameter(String name, final InputStream value)
	Query addParameter(String name, final int value)

	Query addParameter(String name, final Integer value)
	Query addParameter(String name, final Long value)

	Query addParameter(String name, final long value)
	Query addParameter(String name, Object value)

	Query addParameter(String name, final Object ... values)
	Query addParameter(String name, final String value)
	Query addParameter(String name, final Time value)
	Query addParameter(String name, final Timestamp value)
	Query addParameter(String name, final UUID value)
	<T> Query addParameter(String name, Class<T> parameterClass, T value)

	Query addToBatch()
	<A> List<A> addToBatchGetKeys(Class<A> klass)

	Query bind(final Object pojo)
	void close()

	<T> List<T> executeAndFetch(Class<T> returnType)
	<T> List<T> executeAndFetch(ResultSetHandler<T> resultSetHandler)
	<T> List<T> executeAndFetch(ResultSetHandlerFactory<T> factory)

	<T> T executeAndFetchFirst(Class<T> returnType)
	<T> T executeAndFetchFirst(ResultSetHandler<T> resultSetHandler)
	<T> T executeAndFetchFirst(ResultSetHandlerFactory<T> resultSetHandlerFactory)

	<T> ResultSetIterable<T> executeAndFetchLazy(final Class<T> returnType)
	<T> ResultSetIterable<T> executeAndFetchLazy(final ResultSetHandler<T> resultSetHandler)
	<T> ResultSetIterable<T> executeAndFetchLazy(final ResultSetHandlerFactory<T> resultSetHandlerFactory)

	Table executeAndFetchTable() 
	LazyTable executeAndFetchTableLazy() 
	Connection executeBatch()

	Object executeScalar()
	<V> V executeScalar(Class<V> returnType)
	<V> V executeScalar(Converter<V> converter)
	<T> List<T> executeScalarList(final Class<T> returnType)
	Connection executeUpdate()
	Map<String, String> getColumnMappings()
	Query setColumnMappings(Map<String, String> mappings)
	Connection getConnection()

	int getCurrentBatchRecords() 
	int getMaxBatchRecords()

	String getName()

	Map<String, List<Integer>> getParamNameToIdxMap()

	Quirks getQuirks()

	ResultSetHandlerFactoryBuilder getResultSetHandlerFactoryBuilder()

	boolean isAutoDeriveColumnNames()

	boolean isCaseSensitive() 

	boolean isExplicitExecuteBatchRequired()

	boolean isThrowOnMappingFailure()

	void logExecution()

	Query setAutoDeriveColumnNames(boolean autoDeriveColumnNames)

	Query setCaseSensitive(boolean caseSensitive)

	Query setColumnMappings(Map<String, String> mappings)

	Query setMaxBatchRecords(int maxBatchRecords)

	Query setName(String name) 

	void setResultSetHandlerFactoryBuilder(ResultSetHandlerFactoryBuilder resultSetHandlerFactoryBuilder)

	Query throwOnMappingFailure(boolean throwOnMappingFailure)

	static Object[] toObjectArray(Object val)

	Query withParams(Object... paramValues)

