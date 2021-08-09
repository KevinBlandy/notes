----------------------------
����
----------------------------
	# ֧�ֵĹ���
		�������ƹ���
		�۶Ͻ�������
		ϵͳ��������
		��Դ���ʿ��ƹ��� 
		�ȵ��������
	
		* ���й��򶼿������ڴ�̬�ж�̬�ز�ѯ���޸ģ��޸�֮��������Ч
	
	# ����������׳����쳣
		* ���� BlockException ������
			BlockException 
				|-FlowException			�����쳣
				|-DegradeException		�۶Ͻ����쳣
				|-SystemBlockException	ϵͳ�����쳣
				|-ParamFlowException	�ȵ���������쳣
		
		* �����ж�ָ���쳣�Ƿ��� BlockException ���߼�������
			BlockException.isBlockException(Throwable t);
	
	# block�¼�
		* Sntinel �ṩ������չ�ӿڣ�����ͨ�� StatisticSlotCallbackRegistry �� StatisticSlot ע��ص�����
		
		* StatisticSlotCallbackRegistry ����ص���صķ���
			 public static void clearEntryCallback()
			 public static void clearExitCallback()

			 public static void addEntryCallback(String key, ProcessorSlotEntryCallback<DefaultNode> callback)
			 public static void addExitCallback(String key, ProcessorSlotExitCallback callback)

			 public static ProcessorSlotEntryCallback<DefaultNode> removeEntryCallback(String key)
			 public static Collection<ProcessorSlotEntryCallback<DefaultNode>> getEntryCallbacks()

			 public static ProcessorSlotExitCallback removeExitCallback(String key) 
			 public static Collection<ProcessorSlotExitCallback> getExitCallbacks()
		
		* ProcessorSlotEntryCallback<T>  �ص��ӿ�
			void onPass(Context context, ResourceWrapper resourceWrapper, T param, int count, Object... args) throws Exception;
			void onBlocked(BlockException ex, Context context, ResourceWrapper resourceWrapper, T param, int count, Object... args);
		
		* ProcessorSlotExitCallback �ص��ӿ�
			void onExit(Context context, ResourceWrapper resourceWrapper, int count, Object... args);
		


----------------------------
�������ƹ��� FlowRule
----------------------------
	# FlowRule ����
		resource
			* ��Դ������Դ����������������ö���	

		limitApp
			* ������Եĵ�����Դ
			* Ĭ�ϣ�default���������ֵ�����Դ

		grade
			* ������ֵ���ͣ�QPS ģʽ��1���򲢷��߳���ģʽ��0��
			* Ĭ�ϣ�QPS ģʽ

		count
			* ������ֵ

		strategy
			* ���ù�ϵ�������ԣ�ֱ�ӡ���·������
			* Ĭ�ϣ�������Դ����ֱ�ӣ�
		
		refResource
		controlBehavior
			* ����Ч����ֱ�Ӿܾ�/WarmUp/����+�Ŷӵȴ�������֧�ְ����ù�ϵ����
			* Ĭ�ϣ�ֱ�Ӿܾ�

		warmUpPeriodSec
		maxQueueingTimeMs
		clusterMode
			* �Ƿ�Ⱥ����
			* Ĭ�ϣ���

		clusterConfig
		controller
	
	# ���ع���
		DegradeRuleManager.loadRules(rules);

----------------------------
�۶Ͻ������� DegradeRule
----------------------------
	# DegradeRule ����
		resource
			* ��Դ��������������ö���
		grade
			* �۶ϲ��ԣ�֧�������ñ���/�쳣����/�쳣������
			* Ĭ�ϣ������ñ���
		count
			* �����ñ���ģʽ��Ϊ�������ٽ� RT��������ֵ��Ϊ�����ã����쳣����/�쳣��ģʽ��Ϊ��Ӧ����ֵ
		limitApp
		timeWindow
			* �۶�ʱ������λΪ s
		minRequestAmount
			* �۶ϴ�������С��������������С�ڸ�ֵʱ��ʹ�쳣���ʳ�����ֵҲ�����۶ϣ�1.7.0 ���룩
			* Ĭ�ϣ�5
		slowRatioThreshold
			* �����ñ�����ֵ���������ñ���ģʽ��Ч��1.8.0 ���룩
		statIntervalMs
			* ͳ��ʱ������λΪ ms������ 60*1000 ������Ӽ���1.8.0 ���룩
			* Ĭ�ϣ�1000 ms
		

	# ����
		DegradeRuleManager.loadRules(rules);
	
----------------------------
ϵͳ�������� SystemRule
----------------------------
	# SystemRule ����
		highestSystemLoad
			* load1 ����ֵ�����ڴ�������Ӧ���ƽ׶�
			* Ĭ�ϣ�-1 (����Ч)

		highestCpuUsage
			* ��ǰϵͳ�� CPU ʹ���ʣ�0.0-1.0��	-
			* Ĭ�ϣ�-1 (����Ч)

		qps
			* ���������Դ�� QPS
			* Ĭ�ϣ�-1 (����Ч)

		avgRt
			* �������������ƽ����Ӧʱ��
			* Ĭ�ϣ�-1 (����Ч)

		maxThread
			* �����������󲢷���
			* Ĭ�ϣ�-1 (����Ч)
		

	
	# ����
		SystemRuleManager.loadRules(rules);

----------------------------
���ʿ��ƹ��� AuthorityRule
----------------------------
	# ����
		resource
			* ��Դ��������������ö���
		limitApp
			* ��Ӧ�ĺ�����/����������ͬ origin �� , �ָ����� appA,appB
		strategy
			* ����ģʽ��AUTHORITY_WHITE Ϊ������ģʽ��AUTHORITY_BLACK Ϊ������ģʽ
			* Ĭ�ϣ�������ģʽ

			* ������ģʽ��ֻ��limitApp�ſ��Է���
			* ������ģʽ����limitApp�ſ��Է���
		
	# ����
		AuthorityRuleManager.loadRules((rule);

----------------------------
�ȵ���� ParamFlowRule
----------------------------
	# ��Ҫ�������
		<dependency>
			<groupId>com.alibaba.csp</groupId>
			<artifactId>sentinel-parameter-flow-control</artifactId>
		</dependency>
	
	# ����
		
	# ����
		ParamFlowRuleManager.loadRules(rule);