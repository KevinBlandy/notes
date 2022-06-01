-------------------------------
CompletableFuture<T>			|
-------------------------------
	# jdk 1.8 �ṩ�Ķ��� Future<V> ��ʵ��
		CompletableFuture<T> implements Future<T>, CompletionStage<T> 

	# ֧���Իص�����ʽȥ����ִ�н��,��������Ҫͨ��������ǰ�߳�����ȡִ�н��

	# ִ����
		* �����������֧�������Զ���� Executor
		* ��������ã���ôʹ��Ĭ�ϵ�



	# ���캯��
		CompletableFuture()
	
	# ��̬��������
		CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
			* ���������У�������������ִ����ϲŻ᷵�أ����ܻ�ȡ������ֵ

		CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)
			* ���������У�ֻҪ��һ��������ɷ��أ���ô�ͻ᷵�أ����Ի�ȡ������ֵ

		<U> CompletableFuture<U> completedFuture(U value)
			* ����һ���Ѿ������״̬�� CompletableFuture
		
		<U> CompletableFuture<U> failedFuture(Throwable ex)
			* ����һ���쳣״̬��CompletableFuture
		
		<U> CompletionStage<U> completedStage(U value)
			* ����һ���Ѿ������״̬�� CompletionStage

		<U> CompletionStage<U> failedStage(Throwable ex)
			* ����һ���쳣״̬��CompletionStage

		
		CompletableFuture<Void> runAsync(Runnable runnable)
		CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
			* �첽ִ��ĳ�����񣬷������� CompletableFuture
			* ����Ҫ����ֵ
		
		<U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
		<U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
			* �첽ִ��ĳ�����񣬷������� CompletableFuture
			* ��Ҫ����ֵ

		Executor delayedExecutor(long delay, TimeUnit unit, Executor executor)
		Executor delayedExecutor(long delay, TimeUnit unit)
	
	# ʵ������
		boolean cancel(boolean mayInterruptIfRunning)
		boolean complete(T value)

		boolean completeExceptionally(Throwable ex)

		CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> fn)
			* �쳣ʱ�Ĳ��ȷ�����fn ����쳣��Ϣ���룬����Ҫ��һ������ֵ����Ϊ�쳣ʱ�Ľ��
		
		T join()
			* ������ǰ�̣߳�ֱ������������ؽ��

		T get() throws InterruptedException, ExecutionException
		T get(long timeout, TimeUnit unit)
			* ��ȡ������������ó�ʱ�䣬

		T getNow(T valueIfAbsent)
			* ��ȡ�������������û������ɣ��򷵻� T


		int getNumberOfDependents()


		boolean isCancelled()
		boolean isCompletedExceptionally()

		boolean isDone()
		

		void obtrudeException(Throwable ex)
			* �ж����������쳣

		void obtrudeValue(T value)

		// �����Ž� -------------------------------
		<U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)
		<U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) 
		<U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn,Executor executor)
			* ��ǰ����ִ����Ϻ󣬻�ѽ�����ݸ� fn ����������Ҫ�����fn��������һ���µ� CompletableFuture
				CompletableFuture future = CompletableFuture.completedFuture(10).thenCompose(result -> {
					// ��ǰ����Ľ��
					System.out.println("��ǰ����Ľ����" + result);  // ��ǰ����Ľ����10
					// �����µ� CompletableFuture
					return CompletableFuture.supplyAsync(() -> result * result);
				});
				System.out.println("���ս���ǣ�" + future.get());		// ���ս���ǣ�100

	

		// ������ -------------------------------
		CompletableFuture<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)
		CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action)
		CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action,Executor executor)
			* ��ǰ���񣬺�other˭����ɣ���ִ��fn��fn���Ի�ȡ�������������Ľ��������Ҫ�����µĽ��

		<U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn)
		<U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn)
		<U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor)
			* ��ǰ���񣬺�other˭����ɣ���ִ��fn��fn���Ի�ȡ�������������Ľ������Ҫ�����µĽ��
		
		CompletableFuture<Void> runAfterEither(CompletionStage<?> other, Runnable action)
		CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action)
		CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action, Executor executor)
			* ��ǰ���񣬺�other˭����ɣ���ִ��fn��fn���ܻ�ȡ�������������Ľ��������Ҫ�����µĽ��
			

		// ������� -------------------------------
		<U,V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn)
		<U,V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn)
		<U,V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn, Executor executor)
			* �ڵ�ǰ�����Լ� other ������Ժ󣬻�ִ�� fn��fn�Ĳ������Ƿֱ��ǵ�ǰ����other����Ľ������Ҫ�����µĽ��

		<U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action)
		<U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action)
		<U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action, Executor executor)
			* �ڵ�ǰ�����Լ� other ������Ժ󣬻�ִ�� fn��fn�Ĳ������Ƿֱ��ǵ�ǰ����other����Ľ��������Ҫ�����µĽ��

		CompletableFuture<Void> runAfterBoth(CompletionStage<?> other,Runnable action)
		CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action)
		CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action,Executor executor)
			* �ڵ�ǰ�����Լ� other ������Ժ󣬾ͻ�ִ�� action�����ܻ�ȡ�������������Ҫ�����µĽ����


		// ������ ---------------------------------
		<U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
		<U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
		<U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
			* ������ִ����Ϻ󣬸����µ���������µ������ܻ�ȡ�������Ҫ�з���ֵ

		CompletableFuture<Void> thenRun(Runnable action)
		CompletableFuture<Void> thenRunAsync(Runnable action)
		CompletableFuture<Void> thenRunAsync(Runnable action,Executor executor)
			* ������ִ����Ϻ󣬸����µ���������µ����񣺲��ܻ�ȡ���������Ҫ�з���ֵ

		CompletableFuture<Void> thenAccept(Consumer<? super T> action)
		CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action)
		CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor)
			* ������ִ����Ϻ󣬸����µ���������µ������ܻ�ȡ���������Ҫ�з���ֵ

		
		// ִ����ɼ��� ---------------------------------
		CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> action)
		CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action)
		CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor)
			* ����ִ����ϵĻص��������ص����2��������������쳣
			* ��Ҫ�Լ�ͨ���쳣�Ƿ�Ϊnull���ж�ִ���Ƿ�ɹ�
		
		<U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) 
		<U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn)
		<U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor)
			* �Խ�����д���fn ���2��������������쳣
			* ���һ���Ҫ�Լ�����һ�������Ҳ����˵����ͨ����������Լ���Ľ�������޸�
		

		CompletableFuture<T> toCompletableFuture()
		CompletableFuture<T> completeOnTimeout(T value, long timeout, TimeUnit unit)
		CompletableFuture<T> orTimeout(long timeout, TimeUnit unit)
			* �����׳��쳣
		
		CompletableFuture<T> completeAsync(Supplier<? extends T> supplier)
		CompletableFuture<T> completeAsync(Supplier<? extends T> supplier, Executor executor)

		CompletionStage<T> minimalCompletionStage()
		CompletableFuture<T> copy()
		Executor defaultExecutor()
		<U> CompletableFuture<U> newIncompleteFuture()

	
	# һЩ��

		* û��ָ�� Executor �ķ�����ʹ�� ForkJoinPool.commonPool() ��Ϊ�����̳߳�ִ���첽����
		* ���ָ���̳߳أ���ʹ��ָ�����̳߳�����

		* runAsync������֧�ַ���ֵ
		* supplyAsync����֧�ַ���ֵ

		* whenComplete, ��ִ�е�ǰ������߳�ִ�м���ִ�� whenComplete ������
		* whenCompleteAsync, ��ִ�а� whenCompleteAsync �����������ύ���̳߳�������ִ��

		* ��һ���߳�������һ���߳�ʱ,����ʹ�� thenApply ���������������̴߳��л�

		* handle ��ִ���������ʱ�Խ���Ĵ���
		* handle ������ thenApply ��������ʽ����һ��
		* ��ͬ���� handle ����������ɺ���ִ��, �����Դ����쳣������,thenApply ֻ����ִ������������,��������쳣��ִ�� thenApply ����

		* thenCombine ��� ���� CompletionStage ������ִ����ɺ�,����������Ľ��һ�齻�� thenCombine ������
		* ������CompletionStage��ִ����ɺ�, �ѽ��һ�齻��thenAcceptBoth����������

		* ����CompletionStage, ˭ִ�з��صĽ����, �����Ǹ�CompletionStage�Ľ��������һ����ת������

	# �̳߳ص�����
		* �̳߳�ѭ�����ÿ��ܻᵼ������
			public Object doGet() {
			  ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
			  CompletableFuture cf1 = CompletableFuture.supplyAsync(() -> {
			  //do sth
				return CompletableFuture.supplyAsync(() -> {
					System.out.println("child");
					return "child";
				  }, threadPool1).join();//������
				}, threadPool1);
			  return cf1.join();
			}
			
			// doGet����������ͨ��supplyAsync��threadPool1�����̣߳������ڲ�����������threadPool1�����̡߳�
			// threadPool1��СΪ10����ͬһʱ����10�����󵽴��threadPool1�������������������߳�ʱ�������������Ŷӣ����Ǹ�����������������������
			// ��ʱ����������ò����̣߳��������޷���ɡ����߳�ִ��cf1.join()��������״̬��������Զ�޷��ָ���

			// Ϊ���޸������⣬��Ҫ�������������������̳߳ظ��룬������������ͬ���̳߳أ�����ѭ���������µ�����
		


-------------------------------
CompletableFuture<T>			|
-------------------------------

		CompletableFuture.supplyAsync(() -> 1)		// ��һ������,�̷߳��� 1
				.thenApply(i -> i + 1)				// �ڶ�������,ʹ�õ�һ������ķ���ֵ��Ϊ����
				.thenApply(i -> i * 2)				// ����������,ʹ�õڶ�������ķ���ֵ��Ϊ����
				.whenComplete((r, e) -> {
					System.out.println(r); //  ���ִ�������,��ȡ��ִ�еĽ��
				});
		
-------------------------------
Demo1
-------------------------------

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
	
	public static String func (String value) {
		try {
			Thread.sleep(1000);			// ģ���ʱ����
		} catch (InterruptedException e) {
			
		}
		return value + ":" + value;
	}
	
	public static Future<String> funcAsync (String value) {
		CompletableFuture<String> completableFuture = new CompletableFuture<String>();
		new Thread(() -> {
			try {
				String retVal = func(value);
				completableFuture.complete(retVal); // ������ɣ����ý��
			} catch (Exception e) {
				completableFuture.completeExceptionally(e); // �����쳣�������쳣
			}
		}).start();
		return completableFuture;
	}
	
	public static void main(String[] args) {
		System.out.println("��ʼ���÷���....");
		Future<String> future = funcAsync("Hello");
		System.out.println("�����������....");
		System.out.println("�ȴ���ȡ����ֵ...");
		try {
			System.out.println(future.get());  // �������ȵȳ���ִ�����
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}