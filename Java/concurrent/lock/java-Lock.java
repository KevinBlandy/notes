----------------------------
Lock						|
----------------------------
	# һ���ӿ�
	# ���ڽ�����̵߳İ�ȫ����
	# synchronized ����ʽ����
	# Lock ����ʾ����
	# ����
		Lock lock = new ReentrantLock();

----------------------------
Lock-����					|
----------------------------

----------------------------
Lock-����					|
----------------------------
	void lock();
		* ������ֱ������ȡ�ɹ�
	
	void lockInterruptibly() throws InterruptedException;
		* ��������ȡ����ʱ�������߳̿��Ա��ж�

	boolean tryLock();
	boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
		* ���Ի�ȡ��
		* �������ó�ʱʱ�䣬���������߳̿��Ա��ж�

	unlock();
		* ���ͷ�
	
	Condition newCondition();
