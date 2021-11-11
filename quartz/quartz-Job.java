

--------------------------------
Job								|
--------------------------------
	# 任务的执行接口
		void execute(JobExecutionContext context)   throws JobExecutionException;
	
	# 通过 JobBuilder 指定 job实现

	# 需要实现类提供一个无参构造器

	# 无状态的Job
		* 每次执行, 都会创建新的 Job 对象以及 JobDataMap 对象执行
		* 不要存储设置实例属性
	
	# 有状态的Job
		* 在 Job 的实现类上添加注解: @PersistJobDataAfterExecution
		* 每次执行, 都会创建新的Job对象, 但是不会创建新的 JobDataMap
		* 那么可以通过 JobDataMap 存储一些状态 
		
	# Job实现的属性自动注入
		* 使用默认的JobFactory: 'org.quartz.simpl.PropertySettingJobFactory' 
		* 会在Job对象创建完毕后, 调用其setter方法, 注入在 JobDataMap 中的属性到实例
		
	# 禁止同时执行
		* 上一个任务还没执行完毕，又到了执行时间，是否允许同时再次执行
		* 如果不允许的话，在 Job 的实现类上添加注解: @DisallowConcurrentExecution

	# Job的持久性
		* 如果Job是非持久的，一旦不再有任何与其关联的活动Trigger，它就会自动从Scheduler中删除。
		
		JobBuilder storeDurably()
		JobBuilder storeDurably(boolean jobDurability)
	
	# 崩溃恢复执行
		* 如果Job是持久性的，在执行期间发生了崩溃（系统宕机），在系统恢复后，是否恢复执行

		JobBuilder requestRecovery()
		JobBuilder requestRecovery(boolean jobShouldRecover)

		* 如果是恢复执行，那么该job的 JobExecutionContext.isRecovering() 返回true