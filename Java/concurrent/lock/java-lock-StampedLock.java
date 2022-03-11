-----------------------------
StampedLock					 |
-----------------------------
	# JDK1.8������, ������ Lock ��ʵ��
		StampedLock implements java.io.Serializable

		* ReadWriteLock, �и����⣺������߳����ڶ�, д�߳���Ҫ�ȴ����߳��ͷ�������ܻ�ȡд��
		* �����Ĺ����в�����д, ����һ�ֱ��۵Ķ���

		* StampedLock��ReadWriteLock���, �Ľ�֮������: ���Ĺ�����Ҳ�����ȡд����д��
		* ����һ��, ���Ƕ������ݾͿ��ܲ�һ��, ����, ��Ҫһ�����Ĵ������ж϶��Ĺ������Ƿ���д��, ���ֶ�����һ���ֹ���

		* ͨ�������Ƕ�д���ụ��, ��д�����һ��version�Ķ���, �����ڰ汾��, �ڶ�֮��Ҫ�жϰ汾���Ƿ�һ��
		* �����һ�µĻ�, �Ϳ�����������, ��Ҫ���뱯�۶���, ���¶�ȡ����, �����߼�����

		* StampedLock�ǲ���������, ������һ���߳��з�����ȡͬһ����

	
	# ���캯��
		public StampedLock()
	
	# ʵ������

		public int getReadLockCount()
		public Lock asReadLock() 
		public boolean isReadLocked() 

		public long readLock()
			* ��ȡ��������

		public long readLockInterruptibly() throws InterruptedException
		public long tryConvertToReadLock(long stamp)
		public long tryReadLock()
		public long tryReadLock(long time, TimeUnit unit) throws InterruptedException
		public void unlockRead(long stamp)
			* ����version�Ƿ����

		
		public Lock asWriteLock()
		public ReadWriteLock asReadWriteLock()
		public boolean isWriteLocked() 
		
		public long tryConvertToOptimisticRead(long stamp)
		public long tryConvertToWriteLock(long stamp)
			

		public long tryOptimisticRead()
			* ��ȡһ���ֹ۶���, ���ص�������һ���汾��
		
		public boolean tryUnlockRead() 
		public boolean tryUnlockWrite()
		public long tryWriteLock()
		public long tryWriteLock(long time, TimeUnit unit) throws InterruptedException
		public void unlock(long stamp)
		public void unlockWrite(long stamp)
			* �ͷ�д��
		public boolean validate(long stamp) 
			* �ж�ָ�����ֹ����汾���Ƿ����

		public long writeLock()
			* ��ȡһ��д������
		
		public long writeLockInterruptibly() throws InterruptedException
	
	# ��̬����
		public static boolean isWriteLockStamp(long stamp) 
		public static boolean isReadLockStamp(long stamp)
		public static boolean isLockStamp(long stamp)
		public static boolean isOptimisticReadStamp(long stamp)

	
	# ������

	# д����
		long stamp = stampedLock.writeLock(); // ��ȡд��
		try {
			/**
				TODO ִ��ԭ�Ӳ���
			*/
		} finally {
			stampedLock.unlockWrite(stamp); // �ͷ�д��
		}
	
	# ������
		long stamp = stampedLock.tryOptimisticRead(); // ���һ���ֹ۶���
		/**
			TODO ִ���߼�����, ��ԭ�Ӳ���, �̲߳���ȫ, 
		*/
		if (!stampedLock.validate(stamp)) {				// ����ֹ۶������Ƿ�������д������
			stamp = stampedLock.readLock();			// ��ȡһ�����۶���
			try {
				/**
					����Ϊ������
					TODO ��Ϊ����ʧЧ, ������Ҫ���¶�ȡ������,ִ��ԭ�Ӳ���
				*/
			} finally {
				stampedLock.unlockRead(stamp);		// �ͷű��۶���
			}
		}

