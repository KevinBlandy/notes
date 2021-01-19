
-------------------
quartz.properties	|
-------------------
	# 放在classpath下

	# 配置项的属性名称以常量形式定义在StdSchedulerFactory类中
		public static final String PROPERTIES_FILE = "org.quartz.properties";
		public static final String PROP_SCHED_INSTANCE_NAME = "org.quartz.scheduler.instanceName";
		public static final String PROP_SCHED_INSTANCE_ID = "org.quartz.scheduler.instanceId";
		public static final String PROP_SCHED_INSTANCE_ID_GENERATOR_PREFIX = "org.quartz.scheduler.instanceIdGenerator";
		...
	
-------------------
配置项				|
-------------------

# 集群相关的配置
	org.quartz.scheduler.instanceName											QuartzScheduler
		* 实例名称, 用于区分多个实例
		* 如果是集群, 则需要保证所有节点的 instanceName 一样

	org.quartz.scheduler.instanceId												NON_CLUSTERED
		* 节点的id, 枚举值
			AUTO		自动生成
			SYS_PROP	系统属性

	org.quartz.scheduler.instanceIdGenerator.class								org.quartz.simpl.SimpleInstanceIdGenerator
		* 节点id的生成策略
		* 当 'org.quartz.scheduler.instanceId=AUTO'的时候, 该配置生效
		* 默认: 'rg.quartz.simpl.SimpleInstanceIdGenerator' 它根据主机名和时间戳生成实例ID
		* 可以配置其他的实现
			HostnameInstanceIdGenerator
			SimpleInstanceIdGenerator
			SystemPropertyInstanceIdGenerator
		* 或者自定义类实现: InstanceIdGenerator


# 线程池相关的配置
	org.quartz.threadPool.class													SimpleThreadPool
		* 自定义线程池ThreadPool的实现

	org.quartz.threadPool.threadCount											25
		* 线程的初始化数量

	org.quartz.threadPool.threadPriority										5
		* 线程的优先级

	org.quartz.scheduler.threadName												instanceName+ '_QuartzSchedulerThread'
		* 线程名称

	org.quartz.scheduler.makeSchedulerThreadDaemon								false
		* 设置任务线程是否为守护线程

	org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer			false
		* 任务线程是否要继承初始化线程(用于初始化Quartz实例的线程)的上下文ClassLoader

	org.quartz.scheduler.idleWaitTime											30000
		* 线程的空闲时间

org.quartz.scheduler.dbFailureRetryInterval									15000
	* 在使用JobStore连接断开时在重试之间等待的时间
	* 使用RamJobStore时, 该参数没有意义

org.quartz.scheduler.classLoadHelper.class									org.quartz.simpl.CascadingClassLoadHelper

org.quartz.scheduler.jobFactory.class										org.quartz.simpl.PropertySettingJobFactory
	* 指定 JobFactory 的实现, 该工厂类负责创建 Job 对象
	* 默认 'org.quartz.simpl.PropertySettingJobFactory' 它每次执行即将执行时, 仅在类上调用 newInstance() 来生成一个新实例, 然后把 JobMap 中的数据setter到实例中

org.quartz.context.key.SOME_KEY												none
	* 配置属性值到: SchedulerContext

org.quartz.scheduler.userTransactionURL										'java:comp/UserTransaction'
org.quartz.scheduler.wrapJobExecutionInUserTransaction						false
org.quartz.scheduler.skipUpdateCheck										false
org.quartz.scheduler.batchTriggerAcquisitionMaxCount						1
org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow				0


