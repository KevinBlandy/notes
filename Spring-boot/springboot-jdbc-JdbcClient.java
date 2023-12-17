--------------
JdbcClient
--------------
	# 依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>

	# 静态方法创建实例
		static JdbcClient create(DataSource dataSource)
		static JdbcClient create(JdbcOperations jdbcTemplate)
		static JdbcClient create(NamedParameterJdbcOperations jdbcTemplate)
	
	# 唯一方法，设置 SQL，返回内部接口
		StatementSpec sql(String sql)
	
----------------------------
StatementSpec
----------------------------
	StatementSpec param(@Nullable Object value);
	StatementSpec param(int jdbcIndex, @Nullable Object value);
	StatementSpec param(int jdbcIndex, @Nullable Object value, int sqlType);
	StatementSpec param(String name, @Nullable Object value);
	StatementSpec param(String name, @Nullable Object value, int sqlType);
	StatementSpec params(Object... values);
	StatementSpec params(List<?> values);
	StatementSpec params(Map<String, ?> paramMap);

	StatementSpec paramSource(Object namedParamObject);
	tatementSpec paramSource(SqlParameterSource namedParamSource);

	ResultQuerySpec query();
	<T> MappedQuerySpec<T> query(Class<T> mappedClass);
	<T> MappedQuerySpec<T> query(RowMapper<T> rowMapper);
	void query(RowCallbackHandler rch);
	<T> T query(ResultSetExtractor<T> rse);

	int update();
	int update(KeyHolder generatedKeyHolder);
	int update(KeyHolder generatedKeyHolder, String... keyColumnNames);

----------------------------
ResultQuerySpec
----------------------------
	SqlRowSet rowSet();
	List<Map<String, Object>> listOfRows();
	Map<String, Object> singleRow();
	List<Object> singleColumn();
	Object singleValue()

----------------------------
MappedQuerySpec
----------------------------
	MappedQuerySpec
	List<T> list();
	Set<T> set()
	Optional<T> optional()
	T single()