---------------------------
spring-task xml配置			|
---------------------------
	# 并不需要其他的依赖

	1,xml配置
		xmlns:task="http://www.springframework.org/schema/task"
		http://www.springframework.org/schema/task 
        http://www.springframework.org/schema/task/spring-task-4.3.xsd

		
		<task:executor id="executor" pool-size="5" />
			* executor 线程池，含义和 java.util.concurrent.Executor 是一样的,pool-size的大小官方推荐为5~10

		<task:scheduler id="scheduler" pool-size="5" />
			* scheduler的pool-size是 ScheduledExecutorService 线程池，默认为1

		<task:scheduled-tasks scheduler="scheduler">  
			<task:scheduled ref="reminderProcessor" //目标类
				method="process"					//目标方法
				cron="0 0 12 * * ?"					//cron表达式
				fixed-delay="" 
				fixed-rate="" 
				initial-delay="" 
				trigger=""/>  
		</task:scheduled-tasks>
			* xml形式配置任务类
		
		<task:annotation-driven executor="executor" scheduler="scheduler" />
			* 开启注解驱动,可以识别 @Scheduled 标识方法

	2,在任务Bean(@Component)的方法中添加注解
		 @Scheduled
			String cron() default "";
				* 定时任务表达式
			String zone() default "";
			long fixedDelay() default -1;
			String fixedDelayString() default "";
			long fixedRate() default -1;
			String fixedRateString() default "";
			long initialDelay() default -1;
			String initialDelayString() default "";