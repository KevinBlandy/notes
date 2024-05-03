---------------------------
Thread						|
---------------------------
	# ���߳���������
	# ���췽��
		Thread t = new Thread();
		Thread t = new Thread(Runnable r);
	
	# �¹�����̶߳���������parent�߳������пռ�����
		* child�̼̳߳���parent�Ƿ�ΪDaemon�����ȼ��ͼ�����Դ��contextClassLoader�Լ��ɼ̳е�ThreadLocal
		* ͬʱ�������һ��Ψһ��ID����ʶ���child�߳�


---------------------------
Thread-����					|
---------------------------
	# ��̬����
	
	# ʵ������

---------------------------
Thread-����					|
---------------------------
	# ��̬����
		public static Thread currentThread();
			* ���ص�ǰ���̶߳���

		public static void sleep(long l);
			* ��ǰ�߳�ֹͣ l ����
		
		public static Map<Thread, StackTraceElement[]> getAllStackTraces()
			* ��ȡ��JVM�������̵߳�ִ��ջ
		
		public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler eh)
			* ����Ĭ�ϵ��߳���δ�������쳣


	# ʵ������
		
		getName();
			* �����߳�����
		
		void setPriority(int newPriority)
			* �����̵߳����ȼ��� 1-0,�߳����ȼ����Ǿ����߳���Ҫ������ٷ���һЩ��������Դ���߳�����
			* Thread ���ṩ��N��ľ�̬����ֵ
				Thread.MAX_PRIORITY = 10;
				Thread.MIN_PRIORITY = 1;
				Thread.NORM_PRIORITY = 5;
			* ��Щ����ϵͳ��������Զ��߳����ȼ����趨
		
		int getPriority()
			* ��ȡ�̵߳����ȼ�
		
		void interrupt()
			* �ж��߳�
		
		boolean isInterrupted()
			* �߳��Ƿ��ж�
		
		void join();
		void join(long millis)
		void join(long millis, int nanos)
			* ���ø÷������̻߳�һֱ����,ֱ�����߳�(join ������ Thread �߳�)ִ����Ϻ������ִ��
			* Ҳ�������ó�ʱʱ��

		void setDaemon(boolean on)
			* ����Ϊ��ǰ�̵߳��ػ��߳�
			* �����ڵ��� start() ����֮ǰ����
		
		void stop();
			* ����ֹͣ���߳�
		
		boolean isAlive()
			* �ж��߳��Ƿ񻹴��
		
		void setContextClassLoader(ClassLoader cl)
		ClassLoader getContextClassLoader()
			* ����/��ȡ��ǰ�̳߳�����ʹ�õ� classloader

		public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh)
			* ���ڴ����߳�ִ�й����У���Щδ��catch���쳣
			* �����ӿ�
				 void uncaughtException(Thread t, Throwable e);
			


---------------------------
Thread ���жϻ���			|
---------------------------
	# ÿ���̶߳���һ�� "�ж�" ��־,������������
	
	# �߳���sleep��wait(����),join ....
		* ��ʱ�����Ľ��̵��ô˽���(Thread ����)�� interrupt()����,���̻߳ᱻ���Ѳ���Ҫ���� InterruptedException
		* (thread����IO����ʱҲ������������Ϊ,��java thread api)
		* InterruptedException �쳣�����󣬲������޸��̵߳��жϱ�ʶλ����Ҫ�Լ��ֶ��޸ģ��׳��쳣���жϱ�־λ�ᱻ��գ������Ǳ����ã�
	
	# ���߳���������
		* ��ʱ�����Ľ��̵��ô˽���(Thread ����)�� interrupt()����,�����յ�����,���Ǵ��̵߳� "�ж�" �ᱻ����Ϊ true
		* ����ͨ�� isInterrupted() �鿴����������
	
	# ����߳���δ������NEW���������Ѿ�������TERMINATED���������interrupt()����û���κ�Ч�����жϱ�־λҲ���ᱻ���á�
	
	# ʹ�� synchronized �ؼ��ֻ�ȡ���Ĺ����в���Ӧ�ж�����

	# �ܽ�
		interrupt()		ʵ������	���� void		�жϵ��ø÷����ĵ�ǰ�߳�
		interrupted()	��̬����	���� boolean	��⵱ǰ�߳��Ƿ��жϣ����ѱ��жϹ�������ж�״̬
			* ע��, ����һ����̬������ֻ�ܶԵ�ǰ���̽��в���

			* ���Ե�ǰ�߳��Ƿ��ѱ��жϡ�������������̵߳��ж�״̬��
			* ���仰˵������������ô˷������Σ���ڶ��ε��ý����� false�����ǵ�ǰ�߳��ڵ�һ�ε�������ж�״̬�󡢵ڶ��ε��ü���ж�״̬ǰ�ٴ��жϣ���


		isInterrupted()	ʵ������	���� boolean	�����ø÷������߳��Ƿ��жϣ�������жϱ��

	
	# ���ŵ�֪ͨ�߳̽���
		public class MainTest {
			public static void main(String[] args) throws Exception {
				Thread thread = new Thread(() -> {
					while (!Thread.interrupted()) {
						try {

							// TODO ҵ����
							
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// �̱߳��жϣ��ֶ����ñ�ʶλΪ: true
							// ������ֶ����ã��߳��жϱ�ʶ�����޸ģ���Ȼ��false������whileѭ�������˳�
							Thread.currentThread().interrupt();
						}
					}
				});
				
				thread.start();

				Thread.sleep(2000);
				
				thread.interrupt();
			}
		}


---------------------------
Thread ״̬
---------------------------
	NEW
		* �´��������ǻ�û���� start(); ����
	
	RUNNABLE
		* ����״̬��Java�̰߳Ѳ���ϵͳ�еľ���������״̬����ΪRUNNABLE
	
	BLOCKED
		* ����״̬����ʾ�߳���������
	
	WAITING
		* �ȴ�״̬����ʾ�߳̽���ȴ�״̬�������״̬��ʾ��ǰ�߳���Ҫ�ȴ������߳�����һЩ�ض�������֪ͨ/�жϣ�
	
	TIME_WAITING
		* ��ʱ�ȴ������״̬��WAITING��ͬ����������ָ��ʱ�����з��أ����磺Sleep

	TERMINATED
		* ��ֹ״̬���߳��Ѿ�ִ�����
	
---------------------------
Thread �ȴ����ѻ���
---------------------------
	# ���ж��󶼿��Ե����������м�������
		void notify();
		void notifyAll();
			* ����һ��/�����߳�
			* �߳�״̬����: BLOCKED

		void wait() throws InterruptedException
		void wait(long timeout)
		void wait(long timeout, int nanos)
			* ����ȴ�״̬���������ó�ʱʱ��
			* �߳�״̬����: WAITING
	
		
		* ������Щ��������Ҫ��������sync...������е���


	# �ȴ����ѻ���
		* ÿ����������������ĵȴ����У�������һ���ȴ����У���ʾ�������У��ö��������̼߳��Э����
		* ���� wait �ͻ�ѵ�ǰ�̷߳ŵ����������ϲ���������ʾ��ǰ�߳�ִ�в���ȥ�ˣ�����Ҫ�ȴ�һ������������������Լ��ı䲻�ˣ���Ҫ�����̸߳ı䡣

		* ���� wait() ��ʱ�򣬵�ǰ�̻߳� '�ͷų��е���'��Ȼ����������ȴ����У�ֱ����ʱ���Ǳ������߳� notify��
		* �ѵ�ǰ�̷߳��������ȴ����У��ͷŶ������������ȴ����߳�״̬��Ϊ WAITING �� TIMED_WAITING��
		* �ȴ�ʱ�䵽�������̵߳��� notify/notifyAll �������������Ƴ�����ʱ��Ҫ '���¾���������'��
			* ����ܹ���������߳�״̬��Ϊ RUNNABLE������ wait �����з���
			* ���̼߳���������ȴ����У��߳�״̬��Ϊ BLOCKED��ֻ���� '�������Ż�� wait �����з���'��

		* ���� notify ��������������еȴ����̻߳��Ѳ��Ӷ������Ƴ������������ͷŶ�������
		* notify ����������Ǵ�����������ѡһ���̣߳�����Ӷ������Ƴ������ѣ�notifyAll �� notify �������ǣ������Ƴ��������������е��̲߳�ȫ�����ѡ�
		* Ҳ����˵��ֻ���ڰ��� notify �� synchronized �����ִ����󣬵ȴ����̲߳Ż�� wait �����з��ء�
		* notify һ�������һ�е��á�
	
	
	# һ�㹤��ģʽ
		* ������
			synchronized ([lock]) {
				while ([condition������]) {
					[lock].wait();
				}
				// ����ҵ���߼�
			}
		
		* ������
			synchronized ([lock]) {
				// �ı�����
				[lock].notifyAll();
			}
		
	# Demo

		import java.util.ArrayList;
		import java.util.List;


		public class BlockedQueue {
			
			private final List<Object> quque;
			
			private final int len;
			
			public BlockedQueue(int len) {
				this.len = len;
				this.quque = new ArrayList<Object>(len);
			}

			public synchronized Object get () {
				while (this.quque.isEmpty()) {
					try {
						System.out.println("���п��ˣ�����...");
						this.wait();
					} catch (InterruptedException e) {
					}
				}
				
				Object ret = quque.removeLast();
				this.notifyAll();
				return ret;
			}
			
			public synchronized void put (Object val) {
				while (this.quque.size() >= this.len) {
					try {
						System.out.println("�������ˣ�����...");
						this.wait();
					} catch (InterruptedException e) {
					}
				}
				this.quque.addFirst(val);
				this.notifyAll();
			}
		}
