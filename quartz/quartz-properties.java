
-------------------
quartz.properties	|
-------------------
	# �ο�
		http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/
	
	# ����classpath��

	# ����������������Գ�����ʽ������ StdSchedulerFactory ����
		public static final String PROPERTIES_FILE = "org.quartz.properties";
		public static final String PROP_SCHED_INSTANCE_NAME = "org.quartz.scheduler.instanceName";
		public static final String PROP_SCHED_INSTANCE_ID = "org.quartz.scheduler.instanceId";
		public static final String PROP_SCHED_INSTANCE_ID_GENERATOR_PREFIX = "org.quartz.scheduler.instanceIdGenerator";
		...
	
	# ����ͨ�����ʽ��������������ֵ:$@other.property.name
		foo=$@org.quartz.scheduler.instanceName

	
-------------------
������				|
-------------------

# ��Ⱥ��ص�����
	org.quartz.scheduler.instanceName											QuartzScheduler
		* ʵ������, �������ֶ��ʵ��
		* ����Ǽ�Ⱥ, ����Ҫ��֤���нڵ�� instanceName һ��

	org.quartz.scheduler.instanceId												NON_CLUSTERED
		* �ڵ��id, ö��ֵ
			AUTO		�Զ�����
			SYS_PROP	ϵͳ����

	org.quartz.scheduler.instanceIdGenerator.class								org.quartz.simpl.SimpleInstanceIdGenerator
		* �ڵ�id�����ɲ���
		* �� 'org.quartz.scheduler.instanceId=AUTO'��ʱ��, ��������Ч
		* Ĭ��: 'rg.quartz.simpl.SimpleInstanceIdGenerator' ��������������ʱ�������ʵ��ID
		* ��������������ʵ��
			HostnameInstanceIdGenerator
			SimpleInstanceIdGenerator
			SystemPropertyInstanceIdGenerator
		* �����Զ�����ʵ��: InstanceIdGenerator


# �̳߳���ص�����
	org.quartz.threadPool.class													SimpleThreadPool
		* �Զ����̳߳�ThreadPool��ʵ��

	org.quartz.threadPool.threadCount											25
		* �̵߳ĳ�ʼ������

	org.quartz.threadPool.threadPriority										5
		* �̵߳����ȼ�

	org.quartz.scheduler.threadName												instanceName+ '_QuartzSchedulerThread'
		* �߳�����

	org.quartz.scheduler.makeSchedulerThreadDaemon								false
		* ���������߳��Ƿ�Ϊ�ػ��߳�

	org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer			false
		* �����߳��Ƿ�Ҫ�̳г�ʼ���߳�(���ڳ�ʼ��Quartzʵ�����߳�)��������ClassLoader

	org.quartz.scheduler.idleWaitTime											30000
		* �̵߳Ŀ���ʱ��

org.quartz.scheduler.dbFailureRetryInterval									15000
	* ��ʹ��JobStore���ӶϿ�ʱ������֮��ȴ���ʱ��
	* ʹ��RamJobStoreʱ, �ò���û������

org.quartz.scheduler.classLoadHelper.class									org.quartz.simpl.CascadingClassLoadHelper

org.quartz.scheduler.jobFactory.class										org.quartz.simpl.PropertySettingJobFactory
	* ָ�� JobFactory ��ʵ��, �ù����ฺ�𴴽� Job ����
	* Ĭ�� 'org.quartz.simpl.PropertySettingJobFactory' ��ÿ��ִ�м���ִ��ʱ, �������ϵ��� newInstance() ������һ����ʵ��, Ȼ��� JobMap �е�����setter��ʵ����

org.quartz.context.key.SOME_KEY												none
	* ��������ֵ��: SchedulerContext

org.quartz.scheduler.userTransactionURL										'java:comp/UserTransaction'
org.quartz.scheduler.wrapJobExecutionInUserTransaction						false
org.quartz.scheduler.skipUpdateCheck										false
org.quartz.scheduler.batchTriggerAcquisitionMaxCount						1
org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow				0


