
------------------------
Condition				|
------------------------
	# Condition �� Object ����������(wait,notify �� notifyAll)�ֽ�ɽ�Ȼ��ͬ�Ķ���
		* �Ա�ͨ������Щ���������� Lock ʵ�����ʹ��,Ϊÿ�������ṩ����ȴ� set (wait-set)

		* �� Object �� wait ����һ�������� await ����ǰ��Ҫ�Ȼ�ȡ�������û���������׳��쳣 IllegalMonitorStateException��


	# �ӿڷ���
		void await() throws InterruptedException;
		boolean await(long time, TimeUnit unit) throws InterruptedException;
		long awaitNanos(long nanosTimeout) throws InterruptedException;
		boolean awaitUntil(Date deadline)  throws InterruptedException;
		
		*  wait() ����˯��,�ȴ�����
		* ��Щawait����������Ӧ�жϵģ�����������жϣ����׳� InterruptedException�����жϱ�־λ�ᱻ���
		
		void awaitUninterruptibly()
			* ����Ӧ�жϵĵȴ�����

		void signal()
		void signalAll()
			* �������һ��/��������


------------------------
Condition ������������
------------------------