--------------------------
Persistence
--------------------------
	# 静态方法
		static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName)
		static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties)
			* 加载 META-INF/persistence.xml, 根据 persistence-unit 创建 EntityManagerFactory

		static void generateSchema(String persistenceUnitName, Map map)
		static PersistenceUtil getPersistenceUtil()
			* 返回一个工具类
			* 该工具类接口就俩方法
				boolean isLoaded(Object entity, String attributeName)
				boolean isLoaded(Object entity);