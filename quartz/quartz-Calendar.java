--------------------------
Calendar				  |
--------------------------
	# Calendar���ڴ�trigger�ĵ��ȼƻ����ų�ʱ���
		* ��Ҫ����������ִ�е�ʱ��, ���� Calendar �ӿ��ж��Ƿ�Ҫִ��
		* ����: ���Դ���һ��trigger, ÿ�������յ�����9:30ִ��, Ȼ������һ��Calendar, �ų������е���ҵ����

	# �ӿڷ���: public interface Calendar extends java.io.Serializable, java.lang.Cloneable
		int MONTH = 0;

		void setBaseCalendar(Calendar baseCalendar);

		Calendar getBaseCalendar();

		boolean isTimeIncluded(long timeStamp);
			* �ж�ָ����ʱ����Ƿ���ִ��ʱ�䷶Χ��

		long getNextIncludedTime(long timeStamp);
			* ����ʱ���, ��ȡ��һ��ִ�е�ʱ��
			
		String getDescription();
		void setDescription(String description);
		Object clone();
	
	# ����ʾһ������, ���Ա���ӵ� Scheduler ��, �Ӷ���������Triggerʹ��
		* ��ӵ� Scheduler
			void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers)
		
		* TriggerBuilder ����
			TriggerBuilder<T> modifiedByCalendar(String calName)
	
	# ʵ����
		Calendar
			|-BaseCalendar
				|-AnnualCalendar		��
				|-CronCalendar			cron���ʽ
				|-DailyCalendar			��
				|-HolidayCalendar		�ڼ���
				|-MonthlyCalendar		��
				|-WeeklyCalendar		��
	
	
	# Demo
		
		// ����һ����Ϊ��λ��ʱ���
		// ���캯����ʾ���� 10:32 00:00 ��ʼ�� 10:33 00:00 ��ִ��
		DailyCalendar dailyCalendar = new DailyCalendar(10, 32, 0, 0,
    			10, 33, 0, 0);
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		// ��ӵ�scheduler, ����Ϊ: someTime
        scheduler.addCalendar("someTime", dailyCalendar, false, false);

		// ���崥����, ͨ�� modifiedByCalendar ָ�� Calendar ������
        Trigger trigger = TriggerBuilder.newTrigger()
                ...
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))  //1����ִ��һ��, ����ÿ�����ϵ� 10:32 00:00 ��ʼ�� 10:33 00:00 ��ִ��
                .startNow()
                .modifiedByCalendar("someTime")
                .build();


------------------------
AnnualCalendar
------------------------
------------------------
AnnualCalendar
------------------------
------------------------
AnnualCalendar
------------------------
------------------------
AnnualCalendar
------------------------
------------------------
AnnualCalendar
------------------------