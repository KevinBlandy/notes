--------------------------------
SchedulerFactory
--------------------------------
	# Scheduler 工厂接口

		Scheduler getScheduler() throws SchedulerException;
		Scheduler getScheduler(String schedName) throws SchedulerException;
		Collection<Scheduler> getAllSchedulers() throws SchedulerException;
	

	
	# 系统提供的2个实现
		DirectSchedulerFactory
		StdSchedulerFactory
