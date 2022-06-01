---------------------------
ThreadPoolExecutor			|
---------------------------
	# �̳߳ص�ʵ��
	# ���췽��
		ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue)
		ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler)
		ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory)
		ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler)

		* corePoolSize
			* �̳߳ػ������߳�����

		* maximumPoolSize
			* �̳߳�����߳�����

		* keepAliveTime
			* ���� corePoolSize �󴴽����߳�, ���к�Ĵ��ʱ��
			* ����ʱ���ͻᱻ����

		* unit
			*  ���ʱ��ĵ�λ

		* workQueue
			* ����������������
			* ��� workQueue ���޽��, ��ô��Զ���ᴥ�� maximumPoolSize, ��ȻkeepAliveTimeҲ��û��������

		* threadFactory
			* �̳߳ع�����

		* handler
			* ���̳߳��޷�����������ʱ�Ĵ���handler
		
	# ʵ������
		void allowCoreThreadTimeOut(boolean value)
		boolean allowsCoreThreadTimeOut()
			* �Ƿ�������ĵ��߳�Ҳ�ᱻ��ʱ����

		int getCorePoolSize()
			* ��ȡ�����߳�����

		boolean awaitTermination(long timeout, TimeUnit unit)throws InterruptedException;
			* ������ֹͣ���е����񣬲����������ó�ʱʱ��
			* ���ص�boolֵ���Ƿ���ָ��ʱ����ȫ��ֹͣ��
		
		int getActiveCount()
			* ���ػ�Ծ�߳�����

		long getCompletedTaskCount()
			* �̳߳����Ѿ���ɵ�����������С�ڻ��ߵ���getTaskCount()

		long getKeepAliveTime(TimeUnit unit)
		int getLargestPoolSize()
		int getMaximumPoolSize()
		int getPoolSize()
			* �̳߳��̵߳�����������̳߳ز����ٵĻ����̳߳�������̲߳����Զ����٣��������ֻ������

		BlockingQueue<Runnable> getQueue()
		RejectedExecutionHandler getRejectedExecutionHandler()
		long getTaskCount()
			* �̳߳���Ҫִ�����������

		ThreadFactory getThreadFactory()
		boolean isTerminated()
			* ��������е��������񶼴�����Ϻ󷵻� true

		boolean isTerminating() 
		int prestartAllCoreThreads()
		boolean prestartCoreThread()
		void purge()
		boolean remove(Runnable task)
		void setCorePoolSize(int corePoolSize)
		void setKeepAliveTime(long time, TimeUnit unit)
		void setMaximumPoolSize(int maximumPoolSize)
		void setRejectedExecutionHandler(RejectedExecutionHandler handler) 
		void setThreadFactory(ThreadFactory threadFactory)

		boolean isShutdown()
		void shutdown()
			* ��ȵ�����������ɲŻ�ر�

		List<Runnable> shutdownNow()
			* �����ر��̳߳�
			* ������ִ�е�����ȫ������interrupt(),ִֹͣ��
			* �Ի�δ��ʼִ�е�����ȫ��ȡ��,���ҷ��ػ�û��ʼ�������б�

		void execute(Runnable command)
		Future<?> submit(Runnable task)
			* ִ������,submit���Ի�ȡ��ִ�н���ķ���ֵ���׳����쳣

		<T> Future<T> submit(Runnable task, T result)
		<T> Future<T> submit(Callable<T> task)

		<T> T invokeAny(Collection<? extends Callable<T>> tasks,long timeout, TimeUnit unit)
		<T> T invokeAny(Collection<? extends Callable<T>> tasks)
			* ִ�� tasks �е����񣬷��ؽ�������ִ����ϵ��Ǹ����
	
		<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,long timeout, TimeUnit unit)
		<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
			* ִ��task�е��������񣬷��ؽ������
			* ��������ֱ����������ִ�����

		protected void afterExecute(Runnable r, Throwable t) { }
		protected void beforeExecute(Thread t, Runnable r) { }
		protected void terminated() { }
			* ������ִ��ǰ��ִ�к���̳߳عر�ǰִ��һЩ���������м�ء�
			* ����� protected �ķ�������Ҫ�Լ��̳к�д

	
	# �ڲ���
		* ���Ƕ��� ThreadPoolExecutor ��ʵ����,�������̳߳��޷�ִ��������ʱ�����

		AbortPolicy
			* �޷�����ʱ�׳��쳣
			* Դ��
				public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
					throw new RejectedExecutionException("Task " + r.toString() + " rejected from " +  e.toString());
		        }

		CallerRunsPolicy
			* ֱ������������
			* Դ��
				public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
					if (!e.isShutdown()) {
						r.run();
					}
				}

		DiscardOldestPolicy
			* �������������ϵ�����
			* Դ��
				public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
					if (!e.isShutdown()) {
						e.getQueue().poll();
						e.execute(r);
					}
				}

		DiscardPolicy
			* ����������
			* Դ��
				public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
				}

---------------------------
RejectedExecutionHandler   |
---------------------------
	# ThreadPoolExecutor �޷�����������ʱ�Ĵ���Handler �ӿ�
	# ���󷽷�
		void rejectedExecution(Runnable r, ThreadPoolExecutor executor);


---------------------------
�̳߳ز��������		   |
---------------------------
	corePoolSize
		* ������е��߳����� corePoolSize, �򴴽����߳�����������, ��ʹ�̳߳��е������߳��ǿ��е�
		* ����̳߳��е��߳��������ڵ��� corePoolSize ��С�� maximumPoolSize , ��ֻ�е� workQueue ��ʱ�Ŵ����µ��߳�ȥ��������
			
		* ������õ� corePoolSize �� maximumPoolSize ��ͬ, �򴴽����̳߳صĴ�С�ǹ̶���,
		* ��ʱ������������ύ, ��workQueueδ��, ���������workQueue��, �ȴ��п��е��߳�ȥ��workQueue��ȡ���񲢴���

		* ������е��߳��������ڵ��� maximumPoolSize, ��ʱ���workQueue�Ѿ�����, ��ͨ��handler��ָ���Ĳ�������������
	
	maximumPoolSize
		* ����߳���, ���߳��� >= corePoolSize ��ʱ��, ���runnable����workQueue��

	workQueue
		* �ȴ�����, �������ύʱ, ����̳߳��е��߳��������ڵ��� corePoolSize ��ʱ��, �Ѹ������װ��һ�� Worker �������ȴ�����

	1, ���߳���С�� corePoolSize ʱ, �����߳�ִ������

	2, ���߳������ڵ��� corePoolSize ���� workQueue û����ʱ, ���� workQueue ��

	3, �߳������ڵ��� corePoolSize ���ҵ� workQueue ��ʱ, �������½��߳�����, �߳�����ҪС�� maximumPoolSize
		* ������ corePoolSize ���������߳�, �������ʱ�䳬����:keepAliveTime �ͻᱻ����

	4, ���߳��������� maximumPoolSize ���� workQueue ���˵�ʱ��ִ�� handler �� rejectedExecution Ҳ���Ǿܾ�����
	

	