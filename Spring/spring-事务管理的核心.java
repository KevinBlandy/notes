----------------------------
PlatformTransactionManager	|
----------------------------
	# 事务管理器
	# 一些实现类
		DataSourceTransactionManager(Jdbc/MyBatis/JdbcTmplate)
		HibernateTransactionManager(hibernate)
		JtaTransactionManager(jta)
			|-WebLogicJtaTransactionManager
			|-WebSphereUowTransactionManager

	TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;
		* 该方法根据事务定义信息从事务环境返回一个已经存在的事务,或者创建一个事务

	void commit(TransactionStatus status) throws TransactionException;
		* 根据事务状态提交事务
		* 如果事务被标记为rollbackOnly,则该方法执行回滚操作

	void rollback(TransactionStatus status) throws TransactionException;
		* 回滚事务
		* 当commit方法抛出异常时,该方法将会被隐式调用
	
----------------------------
TransactionStatus			|
----------------------------
	# 继承自接口:SavepointManager
	# 事务具体运行状态

	boolean isNewTransaction();
		* 判断当前事务是否是一个新的事务,如果返回false,则表示当前事务是一个已经存在的事务,或者当前操作未运行在事务环境中

	boolean hasSavepoint();
	void setRollbackOnly();
	boolean isRollbackOnly();
		* 不提交,仅仅执行回滚
		* 将当前事务标记为rollbackOnly,从而通知事务管理器只能回滚该事务

	void flush();
	boolean isCompleted();
		* 判断当前事务是否已经结束（已经提交或回滚）

	Object createSavepoint() throws TransactionException;
		* 创建恢复点
	
	void rollbackToSavepoint(Object savepoint) throws TransactionException;
		* 回滚到恢复点

	void releaseSavepoint(Object savepoint) throws TransactionException;
		* 保存恢复点

----------------------------
TransactionDefinition		|
----------------------------
	# 事务的定义(隔离级别,传播,超时,只读)

	int PROPAGATION_REQUIRED = 0;
	int PROPAGATION_SUPPORTS = 1;
	int PROPAGATION_MANDATORY = 2;
	int PROPAGATION_REQUIRES_NEW = 3;
	int PROPAGATION_NOT_SUPPORTED = 4;
	int PROPAGATION_NEVER = 5;
	int PROPAGATION_NESTED = 6;
		* 事务传播属性

	int ISOLATION_DEFAULT = -1;
		* 使用数据库默认的隔离级别
	int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;
	int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;
	int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;
	int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;
		* 事务的隔离级别

	int TIMEOUT_DEFAULT = -1;
		* 默认的超时

	int getPropagationBehavior();
	int getIsolationLevel();
		* 获取隔离级别和事务传播属性

	int getTimeout();
		* 超时时间

	boolean isReadOnly();
		* 是否只读

	String getName();

----------------------------------
TransactionSynchronizationManager |
----------------------------------
	# 事务的同步管理器(abstract)
		* 事务是和线程紧密相关的
		* 事务同步管理器使用ThreadLocal为每个线程绑定一个数据库连接

		* 比如在一个线程中,当一个Service的方法被执行时,如果这个方法需要一个事务
		* spring会在开启事务时，利用事务同步管理器为该线程绑定一个数据库连接
		* 之后在当前线程的这个事务内,只要需要用到数据库连接,都是从ThreadLocal获取这个之前被绑定的连接
		* 这也是为什么像JdbcTemplate这种单例的Bean能够正常工作在多线程环境中
		* 因为JdbcTemplate在执行sql时也是从事务同步管理器中拿数据库连接的
	
	# 私有的静态
		ThreadLocal<Map<Object, Object>> resources = new NamedThreadLocal<>("Transactional resources");
			* 当前线程对应的connection或session等类型的资源

		ThreadLocal<Set<TransactionSynchronization>> synchronizations = new NamedThreadLocal<>("Transaction synchronizations");
			* 存放当前线程的事务执行过程中的回调操作

		ThreadLocal<String> currentTransactionName = new NamedThreadLocal<>("Current transaction name");
			* 当前线程的事务名称

		ThreadLocal<Boolean> currentTransactionReadOnly = new NamedThreadLocal<>("Current transaction read-only status");
			* 当前线程的事务是否为只读

		ThreadLocal<Integer> currentTransactionIsolationLevel = new NamedThreadLocal<>("Current transaction isolation level");
			* 当前线程的事务隔离级别

		ThreadLocal<Boolean> actualTransactionActive = new NamedThreadLocal<>("Actual transaction active");
			* 当前线程的事务是否被激活

