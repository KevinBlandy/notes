-----------------------
Job
-----------------------
	# 任务执行接口 public interface Job 

		void execute(JobExecutionContext context) throws JobExecutionException;
	
		
	# 需要实现类提供一个无参构造器

	# 无状态的Job
		* 每次执行, 都会创建新的 Job 对象以及 JobDataMap 对象执行
		* 不要存储设置实例属性
	
	# 有状态的Job
		* 在 Job 的实现接口上添加注解: @PersistJobDataAfterExecution
		* 每次执行, 都会创建新的Job对象, 但是不会创建新的 JobDataMap
		* 那么可以通过 JobDataMap 存储一些状态 
		
	# Job实现的属性自动注入
		* 使用默认的JobFactory: 'org.quartz.simpl.PropertySettingJobFactory' 
		* 会在Job对象创建完毕后, 调用其setter方法, 注入在 JobDataMap 中的属性到实例
		
