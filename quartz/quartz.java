-----------------------------------
Quartz-����							|
-----------------------------------
	# ���
		* ����OepnSymhony��Դ��֯��job scheduling ������һ����Դ��Ŀ
		* ������ţ�Ƶ��������ϵͳ,����ֱ����SE����EE��Ŀ��ʹ��
		* ���Դ���һ��,����ʮ��,ǧ���ȵȸ��ӵ��������ϵͳ
	
	# ��ַ
		http://www.quartz-scheduler.org
		
		https://www.iocoder.cn/Spring-Boot/Job/?yudao
		https://www.jianshu.com/p/7663f0ed486a
		https://blog.csdn.net/Evankaka/article/details/45540885


	# Maven����
		<dependency>
			<groupId>org.quartz-scheduler</groupId>
			<artifactId>quartz</artifactId>
		</dependency>

		<dependency>
			<groupId>org.quartz-scheduler</groupId>
			<artifactId>quartz-jobs</artifactId>
		</dependency>   

		* jobsģ���ṩ����Ҫ��һЩ����

	# ���Ľӿ�
		Scheduler
			* ���ĵ�����

		Job
			* һ���������

		JobDetail
			* ����������������Job���Լ� JobDataMap �ȵ���Ϣ������ΨһID����
			* ����ͨ�� JobBuilder ���� JobDetail ʵ��
			* һ��JobDetail�����ж��������

		Trigger
			* �����������Թ��� Job �Լ� ���Լ� JobDataMap �ȵ���Ϣ������ΨһID����
			* ���õ�һЩʵ��
				SimpleTrigger
				CronTrigger
				CalendarIntervalTrigger
				DailyTimeIntervalTrigger
		
			* ����ͨ�� TriggerBuilder ����Triggerʵ��
			* Trigger��Ҫ���õ��ȹ��򣬲�ͬ�ĵ��ȹ���ͨ����ͬ��Builder����
			* ��ͬ�ĵ��ȹ��򣬾�����Trigger��ʵ�ֲ�ͬ
			
		* ��ϵͼ

							Scheduler
								��(ע��)
				 -------------------------------
				| ---------------				|
				||JobDetail |Job |		Trigger	|
				| ---------------				|
				 -------------------------------
		
		JobExecutionContext
			* ����ִ�е�������
		
-----------------------------------
Quartz-	Trigger ���������			|
-----------------------------------
	SimpleTrigger
		* �򵥵Ĵ�����
		* ��������ֻ��Ҫִ��һ�λ����ڸ���ʱ�䴥��N����ÿ��ִ���ӳ�һ��ʱ�������
		

	CronTrigger
		* ���ʽ������
		* �Ƚϸ��ӵ��������ϵͳ,����Ҫͨ�����ʽ��ȷ������ĵ���
	

-----------------------------------
Quartz-	Demo						|
-----------------------------------
	# �򵥵Ĵ���ʾ��
		* ֱ�Ӿ�����ͨ��,����Ҫ�̳л���ʵ���κεĽӿڻ�����
		* 

	# ���ʽ����ʾ��



-----------------------------------
Quartz-	����Spring					|
-----------------------------------
	<!-- ��������bean -->
	<bean name="paymentOrderJobDetail" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
		<!-- ָ�������job��(�Զ���̳���:'QuartzJobBean'�����������) -->
		<property name="jobClass" value="com.taotao.store.order.job.PaymentOrderJob" />
		<!-- ָ��job������ -->
		<property name="name" value="paymentOrder" />
		<!-- ָ��job�ķ��� -->
		<property name="group" value="Order" />
		<!-- ��������Ϊtrue�����Ϊfalse����û�л�Ĵ�������֮����ʱ���ڵ�������ɾ��������  -->
		<property name="durability" value="true"/>
		<!-- ָ��spring������key��������趨��job�е�jobmap���ǻ�ȡ����spring������ -->
		<property name="applicationContextJobDataKey" value="applicationContext"/>
	</bean>
	
	<!-- ���崥���� -->
	<bean id="cronTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
		<property name="jobDetail" ref="paymentOrderJobDetail" />
		<!-- ÿһ����ִ��һ�� -->
		<property name="cronExpression" value="0 0/1 * * * ?" />
	</bean>
	
	<!-- ��������� -->
	<bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
	    <property name="triggers">
	        <list>
	            <ref bean="cronTrigger" />
	        </list>
	    </property>
	</bean>

	public class PaymentOrderJob extends QuartzJobBean {

		@Override
		protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
			//��ȡApplicationContext  ,:applicationContext ���������Դ��������Ϣ,��������д��ɶ,�������ɶ
			ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
			//ҵ�����

		}
	}