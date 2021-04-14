--------------------------
DeploymentOptions
--------------------------
	# Verticle的配置类


--------------------------
构造
--------------------------
	public DeploymentOptions()
	public DeploymentOptions(DeploymentOptions other)
	public DeploymentOptions(JsonObject json) 


--------------------------
实例
--------------------------
	public JsonObject getConfig()
	public DeploymentOptions setConfig(JsonObject config)
		* 设置给verticle的配置

	public boolean isWorker()
	public DeploymentOptions setWorker(boolean worker)
		* 设置为 worker verticle

	public String getIsolationGroup() 
	public DeploymentOptions setIsolationGroup(String isolationGroup)

	public boolean isHa()
	public DeploymentOptions setHa(boolean ha)
		* 启用高可用方式（HA）部署。在这种方式下，当
		* 其中一个部署在 Vert.x 实例中的 Verticle 突然挂掉，这个 Verticle 可以在集群环境中的另一个 Vert.x 实例中重新部署


	public List<String> getExtraClasspath()
	public DeploymentOptions setExtraClasspath(List<String> extraClasspath)

	public int getInstances()
	public DeploymentOptions setInstances(int instances) 
		* 启动的示例数量

	public List<String> getIsolatedClasses()
	public DeploymentOptions setIsolatedClasses(List<String> isolatedClasses)

	public String getWorkerPoolName()
	public DeploymentOptions setWorkerPoolName(String workerPoolName)

	public int getWorkerPoolSize()
	public DeploymentOptions setWorkerPoolSize(int workerPoolSize)

	public long getMaxWorkerExecuteTime()
	public DeploymentOptions setMaxWorkerExecuteTime(long maxWorkerExecuteTime) 

	public TimeUnit getMaxWorkerExecuteTimeUnit()
	public DeploymentOptions setMaxWorkerExecuteTimeUnit(TimeUnit maxWorkerExecuteTimeUnit)

	public ClassLoader getClassLoader()
	public DeploymentOptions setClassLoader(ClassLoader classLoader)

	public void checkIsolationNotDefined()
	public JsonObject toJson()




--------------------------
静态
--------------------------
	public static final boolean DEFAULT_WORKER = false;
	public static final boolean DEFAULT_HA = false;
	public static final int DEFAULT_INSTANCES = 1;