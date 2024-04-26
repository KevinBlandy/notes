---------------------------------
HashedWheelTimer				 |
---------------------------------
	# io.netty.util.HashedWheelTimer
		* ʱ�����㷨��ʵ��

		public class HashedWheelTimer implements Timer
	
	# ��̬���� 
	    public static final int WORKER_STATE_INIT = 0;
		public static final int WORKER_STATE_STARTED = 1;
		public static final int WORKER_STATE_SHUTDOWN = 2;

	# ���췽��
		public HashedWheelTimer() 
		public HashedWheelTimer(long tickDuration, TimeUnit unit) 
		public HashedWheelTimer(long tickDuration, TimeUnit unit, int ticksPerWheel)
		public HashedWheelTimer(ThreadFactory threadFactory)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection, long maxPendingTimeouts)
		public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection, long maxPendingTimeouts, Executor taskExecutor)

		* threadFactory	�̹߳���
			* ���ڴ���ר���� TimerTask ִ�еĺ�̨�̡߳�

		* tickDuration	ʱ��ÿ�� tick ��ʱ�䣬�൱��ʱ��������ߵ���һ�� slot��
			* Ĭ�ϣ�100
		
		* unit			��ʾ tickDuration ��ʱ�䵥λ
			* Ĭ�ϣ� TimeUnit.MILLISECONDS
		
		* ticksPerWheel	ʱ������һ���ж��ٸ� slot��Ĭ�� 512 ��������� slot Խ�࣬ռ�õ��ڴ�ռ��Խ��

		* leakDetection			�Ƿ����ڴ�й©���
			* ���Ϊ true����Ӧʼ������й©���
			* ���Ϊ false����ֻ�е������̲߳����ػ������߳�ʱ�Ż�����й©��⡣

		* maxPendingTimeouts	�������ȴ�������
			* ��������ʱ���������� newTimeout �󽫵����׳� java.util.concurrent.RejectedExecutionException �쳣��
			* �����ֵΪ 0 �������򲻼ٶ����ȴ���ʱ���ơ�

		* taskExecutor			ִ����
			*  ����ִ�����ύ�Ķ�ʱ�����ִ�������������������ڲ�����Ҫִ����ʱ����رա�
	

	# ʵ������
		public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) 
			* task��Ҫִ�е����񣬺���ʽ�ӿ�
				 void run(Timeout timeout) throws Exception;
				
			* delay �ӳٶ��ʱ��
			* unit ʱ�䵥λ
				
		public long pendingTimeouts()

		public void start()
			* ����ʱ���֣�����Ҫ�������
			* ÿ����������ʱ�򶼻����

		public Set<Timeout> stop()
			* �ͷŸ� Timer ��ȡ��������Դ����ȡ�������ѵ��ȵ���δִ�е�����
			* �����뱾����ȡ����������صľ��

	# Timeout �ӿ�
		Timer timer();
			* ���� Timer, �����Ͼ��� HashedWheelTimer ʵ��

		TimerTask task();
		boolean isExpired();
		boolean isCancelled();
		boolean cancel();



-------------------------
Demo
-------------------------
	# �����÷�
		ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("timer#", 1).factory());
		
		HashedWheelTimer timer = new HashedWheelTimer(Thread.ofVirtual().name("fac#", 1).factory(), 100, TimeUnit.MILLISECONDS, 512, true, -1, executor);
		
		timer.newTimeout(timeout -> {
			log.info("����ִ��");
		}, 1, TimeUnit.SECONDS);
		
		timer.start();
		
		System.in.read();