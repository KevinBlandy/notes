
------------------------
Condition				|
------------------------
	# Condition �� Object ����������(wait,notify �� notifyAll)�ֽ�ɽ�Ȼ��ͬ�Ķ���
		* �Ա�ͨ������Щ���������� Lock ʵ�����ʹ��,Ϊÿ�������ṩ����ȴ� set (wait-set)

	# �ӿڷ���
		void await()
			*  wait() ����˯��,�ȴ�����
		
		boolean await(long time, TimeUnit unit)
		long awaitNanos(long nanosTimeout)
		void awaitUninterruptibly()
		boolean awaitUntil(Date deadline) 
		void signal()
			* �������һ��
		
		void signalAll()
			* ��������


------------------------
Condition ������������
------------------------