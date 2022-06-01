------------------------
ReentrantLock			|
------------------------
	# ��������
		*  ʵ���� Lock �ӿ�
			ReentrantLock implements Lock, java.io.Serializable
	
	# ���췽��
		ReentrantLock()
		ReentrantLock(boolean fair)

	# ��ƽ��
		* һ�������ϵ����ǲ���ƽ��,��һ���������߳����ȵõ���,�������߳̾ͺ�õ�����
		* ����ƽ�������ܻ�����������󣬵���Ч��Ҫ�ߵ�
		* ��ƽ������˼����,������ܱ�֤�߳����������ȵõ���,��Ȼ��ƽ�����������������,���ǹ�ƽ�������ܻ�ȷǹ�ƽ����ܶ�
			ReentrantLock fairLock = new ReentrantLock(true);

	# ʵ������
		lock();
			* ���Ի�ȡ��,�߳�����

		lockInterruptibly()  throws InterruptedException;
			* ��ǰ�̻߳�������ȡ��
			* ������̱߳�ִ���� interrupt() ����,���Ҹ��̴߳�������״̬�Ļ�,��ô���̵߳Ĵ˷����ͻ��׳��쳣
			* '������Կ� Thread���жϻ���'
			* ��lock()������
				* lock() ��һֱ����,ֱ����ȡ��
				* lockInterruptibly() Ҳ��һֱ����,ֱ����ȡ��
					* ����,��������˸��̵߳� interrupt() �ķ���,��ô lockInterruptibly() �ͻ��׳��ж��쳣,������д���
					* �÷����������̴߳�������״̬��,�������̴߳�ʹ�÷����׳��쳣,�Ӷ��ж��߳�,���Է�ֹ����֮����������


		newCondition();
			* ����һ��Conditionʵ��

		unlock();
			* �ͷ���

		getHoldCount();
		getQueueLength();
		getWaitQueueLength(null);

		hasQueuedThread(null);
		hasWaiters(null);
		hasQueuedThreads();

		isFair();
			* �Ƿ��ǹ�ƽ��

		isHeldByCurrentThread();
		isLocked();

		boolean tryLock();
			* ���Ի�ȡ��,��������,���������Ƿ���Ի�ȡ

		boolean tryLock(long timeout, TimeUnit unit);
			* ���Ի�ȡ��,�����ʱ,���� false