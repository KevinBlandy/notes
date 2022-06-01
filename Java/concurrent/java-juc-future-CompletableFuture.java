-------------------------------
CompletableFuture<T>			|
-------------------------------
	# jdk 1.8 提供的对于 Future<V> 的实现
		CompletableFuture<T> implements Future<T>, CompletionStage<T> 

	# 支持以回调的形式去处理执行结果,而不用需要通过阻塞当前线程来获取执行结果

	# 执行器
		* 大多数方法都支持配置自定义的 Executor
		* 如果不配置，那么使用默认的



	# 构造函数
		CompletableFuture()
	
	# 静态工厂函数
		CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
			* 所有任务中，必须所有任务都执行完毕才会返回，不能获取到返回值

		CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)
			* 所有任务中，只要有一个任务完成返回，那么就会返回，可以获取到返回值

		<U> CompletableFuture<U> completedFuture(U value)
			* 返回一个已经是完成状态的 CompletableFuture
		
		<U> CompletableFuture<U> failedFuture(Throwable ex)
			* 返回一个异常状态的CompletableFuture
		
		<U> CompletionStage<U> completedStage(U value)
			* 返回一个已经是完成状态的 CompletionStage

		<U> CompletionStage<U> failedStage(Throwable ex)
			* 返回一个异常状态的CompletionStage

		
		CompletableFuture<Void> runAsync(Runnable runnable)
		CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
			* 异步执行某个任务，返回它的 CompletableFuture
			* 不需要返回值
		
		<U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
		<U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
			* 异步执行某个任务，返回它的 CompletableFuture
			* 需要返回值

		Executor delayedExecutor(long delay, TimeUnit unit, Executor executor)
		Executor delayedExecutor(long delay, TimeUnit unit)
	
	# 实例方法
		boolean cancel(boolean mayInterruptIfRunning)
		boolean complete(T value)

		boolean completeExceptionally(Throwable ex)

		CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> fn)
			* 异常时的补救方法，fn 会把异常信息传入，并且要求一个返回值，作为异常时的结果
		
		T join()
			* 阻塞当前线程，直到任务结束返回结果

		T get() throws InterruptedException, ExecutionException
		T get(long timeout, TimeUnit unit)
			* 获取结果，可以设置超时间，

		T getNow(T valueIfAbsent)
			* 获取结果，如果结果还没计算完成，则返回 T


		int getNumberOfDependents()


		boolean isCancelled()
		boolean isCompletedExceptionally()

		boolean isDone()
		

		void obtrudeException(Throwable ex)
			* 中断任务，设置异常

		void obtrudeValue(T value)

		// 任务桥接 -------------------------------
		<U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)
		<U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) 
		<U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn,Executor executor)
			* 当前任务执行完毕后，会把结果传递给 fn 方法，并且要求这个fn方法返回一个新的 CompletableFuture
				CompletableFuture future = CompletableFuture.completedFuture(10).thenCompose(result -> {
					// 当前任务的结果
					System.out.println("当前任务的结果：" + result);  // 当前任务的结果：10
					// 返回新的 CompletableFuture
					return CompletableFuture.supplyAsync(() -> result * result);
				});
				System.out.println("最终结果是：" + future.get());		// 最终结果是：100

	

		// 任务竞行 -------------------------------
		CompletableFuture<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)
		CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action)
		CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action,Executor executor)
			* 当前任务，和other谁先完成，就执行fn，fn可以获取到最先完成任务的结果，不需要返回新的结果

		<U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn)
		<U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn)
		<U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor)
			* 当前任务，和other谁先完成，就执行fn，fn可以获取到最先完成任务的结果，需要返回新的结果
		
		CompletableFuture<Void> runAfterEither(CompletionStage<?> other, Runnable action)
		CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action)
		CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action, Executor executor)
			* 当前任务，和other谁先完成，就执行fn，fn不能获取到最先完成任务的结果，不需要返回新的结果
			

		// 任务组合 -------------------------------
		<U,V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn)
		<U,V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn)
		<U,V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn, Executor executor)
			* 在当前任务以及 other 都完成以后，会执行 fn，fn的参数就是分别是当前任务，other任务的结果，需要返回新的结果

		<U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action)
		<U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action)
		<U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action, Executor executor)
			* 在当前任务以及 other 都完成以后，会执行 fn，fn的参数就是分别是当前任务，other任务的结果，不需要返回新的结果

		CompletableFuture<Void> runAfterBoth(CompletionStage<?> other,Runnable action)
		CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action)
		CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action,Executor executor)
			* 在当前任务以及 other 都完成以后，就会执行 action。不能获取到结果，不能需要返回新的结果。


		// 任务串行 ---------------------------------
		<U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
		<U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
		<U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
			* 在任务执行完毕后，给定新的任务，这个新的任务：能获取结果，需要有返回值

		CompletableFuture<Void> thenRun(Runnable action)
		CompletableFuture<Void> thenRunAsync(Runnable action)
		CompletableFuture<Void> thenRunAsync(Runnable action,Executor executor)
			* 在任务执行完毕后，给定新的任务，这个新的任务：不能获取结果，不需要有返回值

		CompletableFuture<Void> thenAccept(Consumer<? super T> action)
		CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action)
		CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor)
			* 在任务执行完毕后，给定新的任务，这个新的任务：能获取结果，不需要有返回值

		
		// 执行完成监听 ---------------------------------
		CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> action)
		CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action)
		CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor)
			* 任务执行完毕的回调方法，回调会给2个参数：结果，异常
			* 需要自己通过异常是否为null来判断执行是否成功
		
		<U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) 
		<U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn)
		<U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor)
			* 对结果进行处理，fn 会给2个参数：结果，异常
			* 而且还需要自己返回一个结果，也就是说可以通过这个方法对计算的结果进行修改
		

		CompletableFuture<T> toCompletableFuture()
		CompletableFuture<T> completeOnTimeout(T value, long timeout, TimeUnit unit)
		CompletableFuture<T> orTimeout(long timeout, TimeUnit unit)
			* 或者抛出异常
		
		CompletableFuture<T> completeAsync(Supplier<? extends T> supplier)
		CompletableFuture<T> completeAsync(Supplier<? extends T> supplier, Executor executor)

		CompletionStage<T> minimalCompletionStage()
		CompletableFuture<T> copy()
		Executor defaultExecutor()
		<U> CompletableFuture<U> newIncompleteFuture()

	
	# 一些点

		* 没有指定 Executor 的方法会使用 ForkJoinPool.commonPool() 作为它的线程池执行异步代码
		* 如果指定线程池，则使用指定的线程池运行

		* runAsync方法不支持返回值
		* supplyAsync可以支持返回值

		* whenComplete, 是执行当前任务的线程执行继续执行 whenComplete 的任务
		* whenCompleteAsync, 是执行把 whenCompleteAsync 这个任务继续提交给线程池来进行执行

		* 当一个线程依赖另一个线程时,可以使用 thenApply 方法来把这两个线程串行化

		* handle 是执行任务完成时对结果的处理
		* handle 方法和 thenApply 方法处理方式基本一样
		* 不同的是 handle 是在任务完成后再执行, 还可以处理异常的任务,thenApply 只可以执行正常的任务,任务出现异常则不执行 thenApply 方法

		* thenCombine 会把 两个 CompletionStage 的任务都执行完成后,把两个任务的结果一块交给 thenCombine 来处理
		* 当两个CompletionStage都执行完成后, 把结果一块交给thenAcceptBoth来进行消耗

		* 两个CompletionStage, 谁执行返回的结果快, 就用那个CompletionStage的结果进行下一步的转化操作

	# 线程池的问题
		* 线程池循环引用可能会导致死锁
			public Object doGet() {
			  ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
			  CompletableFuture cf1 = CompletableFuture.supplyAsync(() -> {
			  //do sth
				return CompletableFuture.supplyAsync(() -> {
					System.out.println("child");
					return "child";
				  }, threadPool1).join();//子任务
				}, threadPool1);
			  return cf1.join();
			}
			
			// doGet方法第三行通过supplyAsync向threadPool1请求线程，并且内部子任务又向threadPool1请求线程。
			// threadPool1大小为10，当同一时刻有10个请求到达，则threadPool1被打满，子任务请求线程时进入阻塞队列排队，但是父任务的完成又依赖于子任务，
			// 这时由于子任务得不到线程，父任务无法完成。主线程执行cf1.join()进入阻塞状态，并且永远无法恢复。

			// 为了修复该问题，需要将父任务与子任务做线程池隔离，两个任务请求不同的线程池，避免循环依赖导致的阻塞
		


-------------------------------
CompletableFuture<T>			|
-------------------------------

		CompletableFuture.supplyAsync(() -> 1)		// 第一个任务,线程返回 1
				.thenApply(i -> i + 1)				// 第二个任务,使用第一个任务的返回值作为参数
				.thenApply(i -> i * 2)				// 第三个任务,使用第二个任务的返回值作为参数
				.whenComplete((r, e) -> {
					System.out.println(r); //  最后都执行完毕了,获取到执行的结果
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
			Thread.sleep(1000);			// 模拟耗时操作
		} catch (InterruptedException e) {
			
		}
		return value + ":" + value;
	}
	
	public static Future<String> funcAsync (String value) {
		CompletableFuture<String> completableFuture = new CompletableFuture<String>();
		new Thread(() -> {
			try {
				String retVal = func(value);
				completableFuture.complete(retVal); // 计算完成，设置结果
			} catch (Exception e) {
				completableFuture.completeExceptionally(e); // 计算异常，设置异常
			}
		}).start();
		return completableFuture;
	}
	
	public static void main(String[] args) {
		System.out.println("开始调用方法....");
		Future<String> future = funcAsync("Hello");
		System.out.println("方法调用完毕....");
		System.out.println("等待获取返回值...");
		try {
			System.out.println(future.get());  // 阻塞，等等程序执行完毕
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}