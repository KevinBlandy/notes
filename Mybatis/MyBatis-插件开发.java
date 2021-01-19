-----------------------
插件开发				|
-----------------------
	# MyBatis在四大对象的创建过程中,都会有插件进行接入
	# 插件可利用动态代理机制,一层层的包装目标对象,从而实现在目标方法执行进行拦截的效果
	# MyBatis运行在已映射的语句执行过程的某一点进行拦截调用
	# 默认清空下,MyBatis允许使用插件来拦截的方法调用包括
		org.apache.ibatis.executor.Executor
			  int update(MappedStatement ms, Object parameter) throws SQLException;
			  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;
			  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;
			  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;
			  List<BatchResult> flushStatements() throws SQLException;
			  void commit(boolean required) throws SQLException;
			  void rollback(boolean required) throws SQLException;
			  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);
			  boolean isCached(MappedStatement ms, CacheKey key);
			  void clearLocalCache();
			  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);
			  Transaction getTransaction();
			  void close(boolean forceRollback);
			  boolean isClosed();
			  void setExecutorWrapper(Executor executor);

		org.apache.ibatis.executor.parameter.ParameterHandler
			 Object getParameterObject();
			 void setParameters(PreparedStatement ps)throws SQLException;
		
		org.apache.ibatis.cursor.Cursor.ResultSetHandler
			<E> List<E> handleResultSets(Statement stmt) throws SQLException;
			<E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException;
			void handleOutputParameters(CallableStatement cs) throws SQLException;
		
		org.apache.ibatis.executor.statement.StatementHandler
			Statement prepare(Connection connection, Integer transactionTimeout)
			void parameterize(Statement statement)
			void batch(Statement statement)
			int update(Statement statement)
			<E> List<E> query(Statement statement, ResultHandler resultHandler)
			<E> Cursor<E> queryCursor(Statement statement)
			BoundSql getBoundSql();
			ParameterHandler getParameterHandler();
	
	# 每个创建出来的对象都是
			
			

1,映射器的内部组成
	MappedStatement
		sqlSource
			boundSql
				sql
				parameterObject
					* 参数本身
					* 如果参数是基本数据类型,会被转换为引用类型
					* 如果传递的Object,则就是 parameterObject
					* 如果是多个参数,则 parameterObject 就是一个Map<String,Object>
						* #{param1}, #{param2}, #{param3}
					* 如果使用了 @Param 注解,那么map的key就是我们注解标识的值
				parameterMappings
					* 它是一个List,每一个元素都是:parameterMapping
					* 这个对象会描述我们的参数,参数包括属性,名称,表达式,javaType,jdbcType,typeHandler等信息
					* 一般不需要改变,通过它可以实现参数和 SQL的结合
					* 以便,PrparedStatement能够通过它找到parameterObject对象的属性并且设置参数


					
		
2,SqlSession 四大对象
	Executor
		* 执行器
		* 它会调度:StatementHandler,ParameterHandler,ResultHandler,等来执行对应的SQL
		* 它至关重要,是真正执行Java和数据库交互的,存在三种执行器,可以配置
			1,SIMPLE	简易执行器,默认
			2,REUSE		执行器重用预处理语句
			3,BATCH		执行器重用语句和批量跟新,针对批量专用的处理器
		* 是执行SQL的全过程,包括组装参数,组装结果集,和执行SQL过程都可以拦截,很广泛,用得不多
	
	StatementHandler
		* 作用是使用数据库的 Statement(PreparedStatement) 执行操作
		* 实现类
			RoutingStatementHandler
		* 四大对象的核心,承上启下
			prepare()			//预编译
			parameterize()		//设置参数,执行
			query()				//执行SQL
			update()			//执行SQL
		* 执行SQL的过程,可以重写SQL执行过程,比较常用的拦截对象
	
	ParameterHandler
		* 用于SQL对参数的处理
		* 拦截SQL的参数组装,重写组装规则
	
	ResultHandler
		* 是进行最后数据集(ResultSet)的封装返回处理
		* 拦截执行结果,重写组装结果的规则
	

	
