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
		* Misfire��ԭ��
			1.��job�ﵽ����ʱ��ʱ�������̶߳�������jobռ�ã�û�п����̡߳�
			2.��job��Ҫ������ʱ��㣬scheduler ֹͣ�ˣ�����������ֹͣ�ģ���
			3.jobʹ���� @DisallowConcurrentExecution ע�⣬job���ܲ���ִ�У����ﵽ��һ��jobִ�е��ʱ����һ������û����ɡ�
			4.jobָ���˹�ȥ�Ŀ�ʼִ��ʱ�䣬���統ǰʱ����8��00��00�룬ָ����ʼʱ��Ϊ7��00��00��
		
		* MisFire��ǰ������
			1. job���ﴥ��ʱ��ʱû�б�ִ��
			2. ��ִ�е��ӳ�ʱ�䳬����Quartz���õ� misfireThreshold ��ֵ
		

			* ����ӳ�ִ�е�ʱ��С�ڷ�ֵ����Quartz����Ϊ������misfire������ִ��job��
			* ����ӳ�ִ�е�ʱ����ڻ��ߵ��ڷ�ֵ�����ж�Ϊ misfire��Ȼ��ᰴ��ָ���Ĳ�����ִ�С�
		
		* ����˵��
			* ����һ��job������8��ִ�У�����һЩԭ��job��8��û��ִ�У���Ϊ�������

			1. ��8��00��50��Quartz����Դ��ִ�����job
				* ��ʱ���ӳ�ִ��ʱ����50�룬С��misfireThresholdΪ60��ķ�ֵ����Quartz��Ϊ��jobû�з���misfire������ִ��job��

			2. ��8��10��00��Quartz����Դ��ִ�����job
				* ��ʱ�ӳ�ִ��ʱ����600�룬����misfireThresholdΪ60��ķ�ֵ����Quartz��Ϊ��job������misfire�������ָ����misfire������ִ�С�
		
		Trigger
			public static final int MISFIRE_INSTRUCTION_SMART_POLICY = 0;  // Ĭ��

			public static final int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;
				* �����жϷ�����misfire������ִ�����з�����misfire������Ȼ����ԭ�ƻ�����ִ�С�
				* ���磺10:15������ִ��9:00��10:00������Ȼ��ȴ���һ��������11:00ִ�У���������ԭ�ƻ�ִ�С�

		
		CalendarIntervalTrigger
			public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
				* ����ִ�е�һ������misfire�����񣬺�����������misfire������Ȼ����ԭ�ƻ�����ִ�С�
				* ���磺��10:15����ִ��9:00���񣬺���10:00����Ȼ��ȴ���һ��������11:00ִ�У���������ԭ�ƻ�ִ��
				
			public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;
				* ���з���misfire�����񶼱����ԣ�ֻ�ǰ���ԭ�ƻ�����ִ��
		
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
	