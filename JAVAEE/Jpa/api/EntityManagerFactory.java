-----------------------
EntityManagerFactory
-----------------------
	# 实体管理器的工厂类
	# 它的实现应该是线程安全的, 一般来说, 一个application, 只会有一个 EntityManagerFactory
	# 抽象方法
		EntityManager createEntityManager();
		EntityManager createEntityManager(Map map);
		EntityManager createEntityManager(SynchronizationType synchronizationType);
		EntityManager createEntityManager(SynchronizationType synchronizationType, Map map);
			* 获取EntityManager

		CriteriaBuilder getCriteriaBuilder();
			* 获取CriteriaBuilder

		Metamodel getMetamodel();
		boolean isOpen();
		void close();
		Map<String, Object> getProperties();
		Cache getCache();
		PersistenceUnitUtil getPersistenceUnitUtil();
		void addNamedQuery(String name, Query query);
		<T> T unwrap(Class<T> cls);
		<T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph);
	
