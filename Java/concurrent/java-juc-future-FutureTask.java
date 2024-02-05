------------------------
FutureTask<V> 
------------------------
	# FutureTask<V> implements RunnableFuture<V> 
	
	# ���췽��
		public FutureTask(Callable<V> callable)
		public FutureTask(Runnable runnable, V result)
			* Runnable ��Ϊ���������������޷���ֵ�����񳡾�

				FutureTask<Void> futureTask = new FutureTask<Void>(() -> {
					// TODO ����
				}, null);
	
	# ʵ������
		public boolean isCancelled()
		public boolean isDone()
		public boolean cancel(boolean mayInterruptIfRunning) 
		public V get() throws InterruptedException, ExecutionException 
		public V get(long timeout, TimeUnit unit)
		public void run()


------------------------
FutureTask<V> 
------------------------
	# ʵ�ֳ�ʱ����ȡ��

		package app.test;

		import java.util.concurrent.ExecutionException;
		import java.util.concurrent.FutureTask;
		import java.util.concurrent.TimeUnit;
		import java.util.concurrent.TimeoutException;

		public class ApplicationMainTest {

			public static void main(String[] args)  {
				
				FutureTask<Void> futureTask = new FutureTask<Void>(() -> {
					
					while (!Thread.interrupted()) {
						try {
							System.out.println("ִ������");
							Thread.sleep(1000L);
						} catch (InterruptedException e) {
						
							// ����ж����߳�
							System.out.println("�߳��ж�");
							
							// �жϵ�ǰ�߳�
							Thread.currentThread().interrupt();
						}
					}
					System.out.println("ִ�н���");
					
				}, null);

				// ���߳̿�ʼִ��
				Thread thread = new Thread(futureTask);
				thread.start();
				
				Void ret = null;
				
				try {
					// ���ִ�н�������ó�ʱʱ��Ϊ 2 ��
					ret = futureTask.get(2, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					System.out.println("�ж�");
				} catch (ExecutionException e) {
					System.out.println("�쳣");
				} catch (TimeoutException e) {
					System.out.println("��ʱ");
					
					// ��ʱ�ˣ�����ȡ�����񣬼��ж��߳�
					futureTask.cancel(true);
				}
				
				// ��ý��
				System.out.println(ret);
				// �Ƿ�ȡ��������
				System.out.println(futureTask.isCancelled());
			}
		}