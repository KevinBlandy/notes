--------------------------------
JobDetail						|
--------------------------------
	# 接口方法
		public JobKey getKey();
		public String getDescription();
		public Class<? extends Job> getJobClass();
		public JobDataMap getJobDataMap();
		public boolean isDurable();
		public boolean isPersistJobDataAfterExecution();
		public boolean isConcurrentExectionDisallowed();
		public boolean requestsRecovery();
		public Object clone();
		public JobBuilder getJobBuilder();

--------------------------------
JobBuilder						|
--------------------------------
	# 用于构建 JobDetail 的工厂类

	# 静态方法
		static JobBuilder newJob()
		static JobBuilder newJob(Class <? extends Job> jobClass)
	
	# 实例方法
		JobDetail build()
		JobBuilder ofType(Class <? extends Job> jobClazz)
			* 指定Job实现类

		JobBuilder requestRecovery()
		JobBuilder requestRecovery(boolean jobShouldRecover)
			* 如果一个job是可恢复的, 并且在其执行的时候, scheduler发生硬关闭(hard shutdown), 比如运行的进程崩溃了，或者关机了)
			* 则当scheduler重新启动的时候, 该job会被重新执行
			* 默认 true
			* 此时, 该job的 JobExecutionContext.isRecovering() 返回true


		JobBuilder storeDurably()
		JobBuilder storeDurably(boolean jobDurability)
			* 如果一个job是非持久的, 当没有活跃的trigger与之关联的时候, 会被自动地从scheduler中删除
			* 也就是说, 非持久的job的生命期是由trigger的存在与否决定的
			* 默认: true


		JobBuilder setJobData(JobDataMap newJobDataMap)

		JobBuilder usingJobData(JobDataMap newJobDataMap)
		JobBuilder usingJobData(String dataKey, Boolean value)
		JobBuilder usingJobData(String dataKey, Double value)
		JobBuilder usingJobData(String dataKey, Float value)
		JobBuilder usingJobData(String dataKey, Integer value)
		JobBuilder usingJobData(String dataKey, Long value)
		JobBuilder usingJobData(String dataKey, String value)
		
		JobBuilder withDescription(String jobDescription)

		JobBuilder withIdentity(JobKey jobKey)

		JobBuilder withIdentity(String name)
		JobBuilder withIdentity(String name, String group)
			* name, 表示job唯一的名称, 如果不曾调用, 则会默认生成一个默认的: 6da64b5bd2ee-05f824d5-50e9-438b-b72b-a2350c08ee65
			* group, 表示job所属的分组
			
			* 如果没设置 group, 默认为: DEFAULT



--------------------------------
JobKey							|
--------------------------------
	# 该类描述了一个job的name和groups属性, 它继承类: Key
		class JobKey extends Key<JobKey> 

	# 构造方法
		public JobKey(String name) {
		public JobKey(String name, String group) {
	
	# 静态的工厂方法
		public static JobKey jobKey(String name) {
		public static JobKey jobKey(String name, String group) 
	
	# 实例方法(从Key继承)
		String getName()
		String getGroup()
	
	# Key的静态方法
		static String createUniqueName(String group)
			* 创建一个唯的group名称
			* 源码
			    public static String createUniqueName(String group) {
					if(group == null)
						group = DEFAULT_GROUP;
					
					String n1 = UUID.randomUUID().toString();
					String n2 = UUID.nameUUIDFromBytes(group.getBytes()).toString();
					
					return String.format("%s-%s", n2.substring(24), n1);
				}
		
