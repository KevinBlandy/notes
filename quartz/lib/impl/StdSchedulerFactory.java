--------------------
StdSchedulerFactory
--------------------
	# Factory的标准实现类

--------------------
static
--------------------
	public static Scheduler getDefaultScheduler() throws SchedulerException
		# 获取默认的
			return new StdSchedulerFactory().getScheduler();

--------------------
this
--------------------

	public StdSchedulerFactory()
	public StdSchedulerFactory(Properties props) throws SchedulerException
	public StdSchedulerFactory(String fileName) throws SchedulerException
		* 创建工场实例，可以指定配置信息
	
	public Logger getLog()
	
	public void initialize() throws SchedulerException
	public void initialize(InputStream propertiesStream)
	public void initialize(String filename) throws SchedulerException
	public void initialize(Properties props) throws SchedulerException 
		* 初始化
	

