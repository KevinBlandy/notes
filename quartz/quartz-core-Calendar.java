--------------------------
Calendar				  |
--------------------------
	# Calendar用于从trigger的调度计划中排除时间段
		* 主要用于任务在执行的时候, 根据 Calendar 接口判断是否要执行
		* 例如: 可以创建一个trigger, 每个工作日的上午9:30执行, 然后增加一个Calendar, 排除掉所有的商业节日

	# 接口方法(org.quartz.Calendar)
		int MONTH = 0;
		void setBaseCalendar(Calendar baseCalendar);
		Calendar getBaseCalendar();

		boolean isTimeIncluded(long timeStamp);
			* 判断指定的时间戳是否在执行时间范围内

		long getNextIncludedTime(long timeStamp);
			* 根据时间戳, 获取下一个执行的时间
			
		String getDescription();
		void setDescription(String description);
		Object clone();
	
	# 它表示一个日期, 可以被添加到 Scheduler 中, 从而被其他的Trigger使用
		* 添加到 Scheduler
			void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers)
		
		* TriggerBuilder 设置
			TriggerBuilder<T> modifiedByCalendar(String calName)
	
	# 实现类
		AnnualCalendar		年
		CronCalendar		cron表达式
		DailyCalendar		日
		HolidayCalendar		节假日
		MonthlyCalendar		月
		WeeklyCalendar		周
	
	
	# Demo
		
		// 定义一个日为单位的时间段
		// 构造函数表示早上 10:32 00:00 开始到 10:33 00:00 不执行
		DailyCalendar dailyCalendar = new DailyCalendar(10, 32, 0, 0,
    			10, 33, 0, 0);
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		// 添加到scheduler, 命名为: someTime
        scheduler.addCalendar("someTime", dailyCalendar, false, false);

		// 定义触发器, 通过 modifiedByCalendar 指定 Calendar 的名称
        Trigger trigger = TriggerBuilder.newTrigger()
                ...
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))  //1秒钟执行一次, 但是每天早上的 10:32 00:00 开始到 10:33 00:00 不执行
                .startNow()
                .modifiedByCalendar("someTime")
                .build();