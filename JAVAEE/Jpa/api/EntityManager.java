------------------------
EntityManager
------------------------
	# 实体对象管理接口
	# 抽象方法
		public void persist(Object entity);
			* 持久化一个对象

		public <T> T merge(T entity);
			* 更新一个对象, 需要先查询出该对象, 再修改

		public void remove(Object entity);
			* 删除一个对象, 需要先使用检索API检索到这个对象, 然后再删除
			* 如果id属性为null, 不会执行SQL语句
			* 如果对象不是先检索出来的, 会有异常: java.lang.IllegalArgumentException: Removing a detached instance xxxx#1

		public <T> T find(Class<T> entityClass, Object primaryKey);
		public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties); 
		public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode);
		public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties);
			* 根据id检索一个对象
				entityManager	目标类型
				primaryKey		id值
				lockMode		加锁类型
				properties		
			
			* find方法, 在被调用的时候, 就会执行SQL检索, 返回对象

		public <T> T getReference(Class<T> entityClass, Object primaryKey);
			* getReference 方法, 返回的是一个代理对象,
			* 它并不会立即去执行SQL, 只有当操作到了对象的属性, 才回去执行检索(延迟加载)


		public void flush();
		public void setFlushMode(FlushModeType flushMode);
		public FlushModeType getFlushMode();

		public void lock(Object entity, LockModeType lockMode);
		public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties);
		public LockModeType getLockMode(Object entity);

		public void refresh(Object entity);
		public void refresh(Object entity, Map<String, Object> properties); 
		public void refresh(Object entity, LockModeType lockMode);
		public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties);

		public void clear();
		public void detach(Object entity);
		public boolean contains(Object entity);
		
		public void setProperty(String propertyName, Object value);
		public Map<String, Object> getProperties();

		public Query createQuery(String qlString);
		public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass);	
			* 使用jpql语句, 创建query查询
				
		public Query createQuery(CriteriaUpdate updateQuery);
		public Query createQuery(CriteriaDelete deleteQuery);
		public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery);



		public Query createNamedQuery(String name);
		public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass);

		public Query createNativeQuery(String sqlString);
		public Query createNativeQuery(String sqlString, Class resultClass);
		public Query createNativeQuery(String sqlString, String resultSetMapping);
			* 创建本地SQL

		public StoredProcedureQuery createNamedStoredProcedureQuery(String name);

		public StoredProcedureQuery createStoredProcedureQuery(String procedureName);
		public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses);
		public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings);

		public void joinTransaction();
		public boolean isJoinedToTransaction();

		public <T> T unwrap(Class<T> cls); 

		public Object getDelegate();
		public void close();
		public boolean isOpen();
		public EntityTransaction getTransaction();
		public EntityManagerFactory getEntityManagerFactory();
		public CriteriaBuilder getCriteriaBuilder();
		public Metamodel getMetamodel();
		public <T> EntityGraph<T> createEntityGraph(Class<T> rootType);
		public EntityGraph<?> createEntityGraph(String graphName);
		public  EntityGraph<?> getEntityGraph(String graphName);
		public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass);
