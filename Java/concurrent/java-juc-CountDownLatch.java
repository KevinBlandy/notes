----------------------------
CountDownLatch				|
----------------------------
	# �����ĳЩ�����ʱ��,ֻ������������ȫ�����,��ǰ����Ż�ִ��
	# �� CyclicBarrier ������
		CountDownLatch:һ�����߶���߳�,�ȴ���������߳����ĳ������֮�����ִ��
			* �ص���һ���߳�(����߳�_�ȴ�,��������N���߳������ĳ������֮��,������ֹ,Ҳ���Եȴ�

		CyclicBarrier:����̻߳���ȴ�,ֱ������ͬһ��ͬ����,�ټ���һ��ִ��
			* �ص��Ƕ���߳�,������һ���߳�û�����,���е��̶߳�����ȴ�

	# ����
		CountDownLatch countDownLatch = new CountDownLatch(5);
			* ������ʱ��,��ָ��һ������
	# ����
		countDown();
			* �ڻ����ϼ�1,��ֵΪ0��ʱ��,countDownLatch
		
		await();
			* ����,�ȴ������߳�ִ����,ֱ������ == 0
			* ��ʵ������Ǹ�ѭ�����
		
		boolean await(long timeout, TimeUnit unit)
			* ���ó�ʱʱ��
			* �����������ɺ󷵻أ��򷵻�ֵΪ true
			* ����ǳ�ʱ�󷵻أ��򷵻�ֵΪ false
		
		long getCount()
			* ����count

	# demo
		
		CountDownLatch countDownLatch = new CountDownLatch(5);

		//�������߳�ִ��,�����߳�ִ����Ϻ�,ִ�и�api
		countDownLatch.countDown();

		
		
		//���߳�����,ֱ�� countDownLatch ���� == 0
		countDownLatch.await();