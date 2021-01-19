----------------------------------
PathChildrenCache				  |
----------------------------------
	# 构造方法
		PathChildrenCache(CuratorFramework client, String path, boolean cacheData)
		PathChildrenCache(CuratorFramework client, String path, boolean cacheData, boolean dataIsCompressed, final ExecutorService executorService)
		PathChildrenCache(CuratorFramework client, String path, boolean cacheData, boolean dataIsCompressed, ThreadFactory threadFactory)
		PathChildrenCache(CuratorFramework client, String path, boolean cacheData, boolean dataIsCompressed, final CloseableExecutorService executorService)
		PathChildrenCache(CuratorFramework client, String path, boolean cacheData, ThreadFactory threadFactory)

		* client
		* path
		* cacheData
		* dataIsCompressed
		* threadFactory
		* executorService
	
	# 实例方法
		void clear()
		void clearAndRefresh()
		void clearDataBytes(String fullPath)
		boolean clearDataBytes(String fullPath, int ifVersion)
		void close() 
		List<ChildData> getCurrentData()
		ChildData getCurrentData(String fullPath)
		void rebuild()
		void rebuildNode(String fullPath)
		void start()
		void start(StartMode mode)
	
	# 内部类
		PathChildrenCache.State(enum)
			LATENT
			STARTED
			CLOSED
		
		StartMode(enum)
			NORMAL
			BUILD_INITIAL_CACHE
			POST_INITIALIZED_EVENT
		
