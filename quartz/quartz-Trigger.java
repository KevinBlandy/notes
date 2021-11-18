---------------------
Trigger
---------------------
	# ���������Ľӿ�: Trigger
		CalendarIntervalTrigger
		CoreTrigger
		CronTrigger
		DailyTimeIntervalTrigger
		MutableTrigger
			|OperableTrigger
		SimpleTrigger

	# ͨ�� TriggerBuilder ����ʵ��
		* builder����Ҫָ��: withSchedule
			<SBT extends T> TriggerBuilder<SBT> withSchedule(ScheduleBuilder<SBT> schedBuilder)

		* ��ָ��withSchedule��Ĭ��: SimpleScheduleBuilder
				return simpleSchedule().withIntervalInMinutes(1).repeatForever();

		* ��ͬ�ĵ��ȹ���(ScheduleBuilder)���ᴴ����ͬ�� Trigger ʵ��
		* ���õ�
			CalendarIntervalScheduleBuilder
			CronScheduleBuilder
			DailyTimeIntervalScheduleBuilder
			SimpleScheduleBuilder
	
	# һ��Trigger���Թ���һ��Job

	# Misfire
		* ������ڵ��ȳ���رգ�������Ϊ Quartz ���̳߳���û�п��õ��߳���ִ����ҵ���־ô�����������������Ĵ���ʱ�䣬��ᷢ��Misfire
		* ��ͬ�Ĵ����������в�ͬ��Misfireָ��ɹ�����ʹ�á�Ĭ������£�����ʹ�á����ܲ��ԡ�ָ��������л��ڴ��������ͺ����õĶ�̬��Ϊ��
		* �����ȳ�������ʱ�����������κ���Misfire�ĳ־ô�������Ȼ��������ǵ������õ�ʧ��ָ�����ÿ����������
		
		Trigger
			public static final int MISFIRE_INSTRUCTION_SMART_POLICY = 0;  // Ĭ��
			public static final int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;
		
		CalendarIntervalTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
			public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;
		
		CronTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
			public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;
		
		DailyTimeIntervalTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
			public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;
		
		SimpleTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_NOW = 1;
			public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT = 2;
			public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT = 3;
			public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT = 4;
			public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT = 5;
	