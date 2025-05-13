---------------------------
Spring-boot jdbctemplate	|
---------------------------
	# �򵥵�һ��Dao����ʹ���
	# ��Ҫ����
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<exclusions>
				<!-- �ų���Ĭ�ϵ����ӳ� -->
				<exclusion>
					<groupId>org.apache.tomcat</groupId>
					<artifactId>tomcat-jdbc</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<!-- ʹ�ð���Ͱ�Druid���ӳ� -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
			<version>1.0.28</version>
		</dependency>
	
	# �ĵ�
		https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html

	

---------------------------
Spring-boot jdbctemplate	|
---------------------------
	JdbcAccessor,JdbcOperations
		|-JdbcTemplate
	
	# ���췽��
		public JdbcTemplate()
		public JdbcTemplate(DataSource dataSource)
		public JdbcTemplate(DataSource dataSource, boolean lazyInit)

	# ʵ������
		public Map<String, Object> call(CallableStatementCreator csc, List<SqlParameter> declaredParameters)
		public void execute(final String sql)
		public <T> T execute(String callString, CallableStatementCallback<T> action)
		public <T> T execute(String sql, PreparedStatementCallback<T> action)
		public <T> T execute(CallableStatementCreator csc, CallableStatementCallback<T> action)
		public <T> T execute(ConnectionCallback<T> action)
		public <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action)
		public <T> T execute(StatementCallback<T> action)
		
		public void setFetchSize(int fetchSize)
		public int getFetchSize() 

		public void setMaxRows(int maxRows)
		public int getMaxRows()

		public void setQueryTimeout(int queryTimeout)
		public int getQueryTimeout()

		public void setIgnoreWarnings(boolean ignoreWarnings)
		public boolean isIgnoreWarnings()

		public void setResultsMapCaseInsensitive(boolean resultsMapCaseInsensitive)
		public boolean isResultsMapCaseInsensitive()

		public void setSkipResultsProcessing(boolean skipResultsProcessing)
		public boolean isSkipResultsProcessing()

		public void setSkipUndeclaredResults(boolean skipUndeclaredResults)
		public boolean isSkipUndeclaredResults()

		public <T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse)
		public void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch)
		public <T> List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
		public <T> T query(String sql, @Nullable Object[] args, ResultSetExtractor<T> rse)
		public void query(String sql, Object[] args, RowCallbackHandler rch)
		public <T> List<T> query(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
		public <T> T query(String sql, @Nullable PreparedStatementSetter pss, ResultSetExtractor<T> rse)
		public void query(String sql, @Nullable PreparedStatementSetter pss, RowCallbackHandler rch)
		public <T> List<T> query(String sql, @Nullable PreparedStatementSetter pss, RowMapper<T> rowMapper)
		public <T> T query(final String sql, final ResultSetExtractor<T> rse)
		public <T> T query(String sql, ResultSetExtractor<T> rse, @Nullable Object... args)
		public void query(String sql, RowCallbackHandler rch)
		public void query(String sql, RowCallbackHandler rch, @Nullable Object... args)
		public <T> List<T> query(String sql, RowMapper<T> rowMapper)
		public <T> List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
		public <T> T query(PreparedStatementCreator psc, @Nullable final PreparedStatementSetter pss, final ResultSetExtractor<T> rse)
		public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse)
		public void query(PreparedStatementCreator psc, RowCallbackHandler rch)
		public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper)

		public List<Map<String, Object>> queryForList(String sql)
		public <T> List<T> queryForList(String sql, Class<T> elementType)
		public <T> List<T> queryForList(String sql, Class<T> elementType, @Nullable Object... args)
		public List<Map<String, Object>> queryForList(String sql, @Nullable Object... args)
		public List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes)
		public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)
		public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType)

		public Map<String, Object> queryForMap(String sql)
		public Map<String, Object> queryForMap(String sql, @Nullable Object... args)
		public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes)

		public <T> T queryForObject(String sql, Class<T> requiredType)
		public <T> T queryForObject(String sql, Class<T> requiredType, @Nullable Object... args)
		public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)
		public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
		public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType)
		public <T> T queryForObject(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
		public <T> T queryForObject(String sql, RowMapper<T> rowMapper)
		public <T> T queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
		
		public SqlRowSet queryForRowSet(String sql)
		public SqlRowSet queryForRowSet(String sql, @Nullable Object... args)
		public SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes)

		public int update(final String sql)
		public int update(String sql, @Nullable Object... args)
		public int update(String sql, Object[] args, int[] argTypes)
		public int update(String sql, @Nullable PreparedStatementSetter pss)
		public int update(PreparedStatementCreator psc)
		public int update(final PreparedStatementCreator psc, final KeyHolder generatedKeyHolder)

		public <T> int[][] batchUpdate(String sql, final Collection<T> batchArgs, final int batchSize,	final ParameterizedPreparedStatementSetter<T> pss)
		public int[] batchUpdate(String sql, List<Object[]> batchArgs)
		public int[] batchUpdate(String sql, List<Object[]> batchArgs, final int[] argTypes)
		public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss)
		public int[] batchUpdate(final String... sql)
		

---------------------
��ѯ�����װΪ����
---------------------
String sql = "SELECT * FROM users WHERE userName=?";

//��ѯ������ɵĶ����������, ��Ҫ��֤�������Ժ�������һ��, ����ͨ������ AS ����������
RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);

User user = jdbcTemplate.queryForObject(sql, rowMapper, new Object[]{"one"});

---------------------
���е��м�¼
---------------------
String sql = "SELECT COUNT(*) FROM users";
Long l = jdbcTemplate.queryForObject(sql,Long.class);
