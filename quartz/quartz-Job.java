

--------------------------------
Job								|
--------------------------------
	# �����ִ�нӿ�
		void execute(JobExecutionContext context)   throws JobExecutionException;
	
	# ͨ�� JobBuilder ָ�� jobʵ��

	# ��Ҫʵ�����ṩһ���޲ι�����

	# ��״̬��Job
		* ÿ��ִ��, ���ᴴ���µ� Job �����Լ� JobDataMap ����ִ��
		* ��Ҫ�洢����ʵ������
	
	# ��״̬��Job
		* �� Job ��ʵ���������ע��: @PersistJobDataAfterExecution
		* ÿ��ִ��, ���ᴴ���µ�Job����, ���ǲ��ᴴ���µ� JobDataMap
		* ��ô����ͨ�� JobDataMap �洢һЩ״̬ 
		
	# Jobʵ�ֵ������Զ�ע��
		* ʹ��Ĭ�ϵ�JobFactory: 'org.quartz.simpl.PropertySettingJobFactory' 
		* ����Job���󴴽���Ϻ�, ������setter����, ע���� JobDataMap �е����Ե�ʵ��
		
	# ��ֹͬʱִ��
		* ��һ������ûִ����ϣ��ֵ���ִ��ʱ�䣬�Ƿ�����ͬʱ�ٴ�ִ��
		* ���������Ļ����� Job ��ʵ���������ע��: @DisallowConcurrentExecution

	# Job�ĳ־���
		* ���Job�Ƿǳ־õģ�һ���������κ���������ĻTrigger�����ͻ��Զ���Scheduler��ɾ����
		
		JobBuilder storeDurably()
		JobBuilder storeDurably(boolean jobDurability)
	
	# �����ָ�ִ��
		* ���Job�ǳ־��Եģ���ִ���ڼ䷢���˱�����ϵͳ崻�������ϵͳ�ָ����Ƿ�ָ�ִ��

		JobBuilder requestRecovery()
		JobBuilder requestRecovery(boolean jobShouldRecover)

		* ����ǻָ�ִ�У���ô��job�� JobExecutionContext.isRecovering() ����true