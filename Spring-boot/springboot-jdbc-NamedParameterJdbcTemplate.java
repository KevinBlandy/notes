--------------------------------
NamedParameterJdbcTemplate
--------------------------------
	# 跟 JdbcTemplate 一样, 它是具名参数的 Template
	
		NamedParameterJdbcOperations
			|-NamedParameterJdbcTemplate
	
	# 构造函数
		public NamedParameterJdbcTemplate(DataSource dataSource)
		public NamedParameterJdbcTemplate(JdbcOperations classicJdbcTemplate)

	# 实例方法
		public int[] batchUpdate(String sql, Map<String, ?>[] batchValues)
		public int[] batchUpdate(String sql, SqlParameterSource[] batchArgs)

		public <T> T execute(String sql, Map<String, ?> paramMap, PreparedStatementCallback<T> action)
		public <T> T execute(String sql, SqlParameterSource paramSource, PreparedStatementCallback<T> action)
		public <T> T execute(String sql, PreparedStatementCallback<T> action)

		public int getCacheLimit() 
		public void setCacheLimit(int cacheLimit)

		public JdbcOperations getJdbcOperations()
		public JdbcTemplate getJdbcTemplate()

		public <T> T query(String sql, Map<String, ?> paramMap, ResultSetExtractor<T> rse)
		public void query(String sql, Map<String, ?> paramMap, RowCallbackHandler rch)
		public <T> List<T> query(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper)
		public <T> T query(String sql, SqlParameterSource paramSource, ResultSetExtractor<T> rse)
		public void query(String sql, SqlParameterSource paramSource, RowCallbackHandler rch)
		public <T> List<T> query(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)
		public <T> T query(String sql, ResultSetExtractor<T> rse)
		public void query(String sql, RowCallbackHandler rch)
		public <T> List<T> query(String sql, RowMapper<T> rowMapper)

		public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap)
		public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> elementType)
		public List<Map<String, Object>> queryForList(String sql, SqlParameterSource paramSource)
		public <T> List<T> queryForList(String sql, SqlParameterSource paramSource, Class<T> elementType)
		public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) 
		public Map<String, Object> queryForMap(String sql, SqlParameterSource paramSource)

		public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType)
		public <T> T queryForObject(String sql, Map<String, ?> paramMap, RowMapper<T>rowMapper)
		public <T> T queryForObject(String sql, SqlParameterSource paramSource, Class<T> requiredType)
		public <T> T queryForObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)

		public SqlRowSet queryForRowSet(String sql, Map<String, ?> paramMap)
		public SqlRowSet queryForRowSet(String sql, SqlParameterSource paramSource)
		
		public int update(String sql, Map<String, ?> paramMap)
		public int update(String sql, SqlParameterSource paramSource)
		public int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder)
		public int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder, @Nullable String[] keyColumnNames)

--------------------------------
插入获取自增id
--------------------------------

GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
String sql = "INSERT INTO `user`(`gender`, `name`, `version`) VALUES(:gender, :name, :version);";

Map<String, Object> param = new HashMap<>();
param.put("gender", Gender.BOY.ordinal());
param.put("name", "Rocco");
param.put("version", 0);

int retVal = this.namedParameterJdbcTemplate.update(sql, new SqlParameterSource() {
	@Override
	public boolean hasValue(String paramName) {
		return param.containsKey(paramName);
	}
	@Override
	public Object getValue(String paramName) throws IllegalArgumentException {
		return param.get(paramName);
	}
}, generatedKeyHolder);

Integer id = generatedKeyHolder.getKey().intValue();
System.err.println("受到影响的行数=" + retVal + ", 自增id=" + id);

--------------------------------
根据对象属性更新
--------------------------------
User user = new User("name","password",21,"boy");
SqlParameterSource papramSource = new BeanPropertySqlParameterSource(user);
namedParameterJdbcTemplate.update(sql, papramSource);