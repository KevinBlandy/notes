--------------------------------
Query
--------------------------------
	# ²éÑ¯½Ó¿Ú
		public interface Query extends Statement, AttachableQueryPart, AutoCloseable
	
	int execute() throws DataAccessException;
	CompletionStage<Integer> executeAsync();
	CompletionStage<Integer> executeAsync(Executor executor);
	boolean isExecutable();
	Query bind(String param, Object value) throws IllegalArgumentException, DataTypeException;
	Query bind(int index, Object value) throws IllegalArgumentException, DataTypeException;
	Query poolable(boolean poolable);
	Query queryTimeout(int seconds);
	Query keepStatement(boolean keepStatement);
	void close() throws DataAccessException;
	void cancel() throws DataAccessException;
