-------------------------------
Future<V>						|
-------------------------------
	# �첽ִ�н���Ľӿ�
	# ����
		boolean cancel(boolean mayInterruptIfRunning);
			* ȡ��ִ�С�
			* ��������Ѿ�ִ���ˣ���ô����ͨ�� mayInterruptIfRunning �����Ƿ�Ҫ�׳��߳��ж��쳣

		boolean isCancelled();
			* �Ƿ��Ǳ�ȡ��ִ�е�
		
		Throwable exceptionNow()

		V resultNow() 

		State state()
			* ����״̬��ö��

		boolean isDone();
			* �Ƿ�ִ����ϣ�ֻҪ�ǽ��������Ƿ����쳣������������true

		V get() throws InterruptedException, ExecutionException;
			* ��ȡ��ִ�еĽ��,��������ǰ���߳�

		V get(long timeout, TimeUnit unit)throws InterruptedException, ExecutionException, TimeoutException;
			* ��ȡ��ִ�еĽ��,��������ǰ���߳�
			* ��������һ����ʱʱ��,��ʱ���׳� TimeoutException


-------------------------------
State
-------------------------------
	# �ڲ�ö��
		RUNNING,
        SUCCESS,
        FAILED,
        CANCELLED
	
