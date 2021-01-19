---------------
Future
---------------
	# 实现了JDK的 Future
		public interface Future<V> extends java.util.concurrent.Future<V> 
	
	# 抽象方法

		// I/O操作是否执行成功
		boolean isSuccess();

		// 标记是否可以通过下面的cancel(boolean mayInterruptIfRunning)取消I/O操作
		boolean isCancellable();

		// 返回I/O操作的异常实例 - 如果I/O操作本身是成功的，此方法返回null
		Throwable cause();

		// 为当前Future实例添加监听Future操作完成的监听器 - isDone()方法激活之后所有监听器实例会得到回调
		Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);
		Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners);
		
		// 为当前Future移除监听Future操作完成的监听器
		Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener);
		Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners);

		// 同步等待Future完成得到最终结果（成功）或者抛出异常（失败），响应中断
		Future<V> sync() throws InterruptedException;

		// 同步等待Future完成得到最终结果（成功）或者抛出异常（失败），不响应中断
		Future<V> syncUninterruptibly();

		// 等待Future完成，响应中断
		Future<V> await() throws InterruptedException;

		// 等待Future完成，不响应中断
		Future<V> awaitUninterruptibly();

		// 带超时时限的等待Future完成，响应中断
		boolean await(long timeout, TimeUnit unit) throws InterruptedException;
		boolean await(long timeoutMillis) throws InterruptedException;
		
		// 带超时时限的等待Future完成，不响应中断
		boolean awaitUninterruptibly(long timeout, TimeUnit unit);
		boolean awaitUninterruptibly(long timeoutMillis);

		// 非阻塞马上返回Future的结果，如果Future未完成，此方法一定返回null；有些场景下如果Future成功获取到的结果是null则需要二次检查isDone()方法是否为true
		V getNow();

		// 取消当前Future实例的执行，如果取消成功会抛出CancellationException异常
		@Override
		boolean cancel(boolean mayInterruptIfRunning);
	
	* sync()和await()方法类似, 只是sync()会检查异常执行的情况, 一旦发现执行异常马上把异常实例包装抛出, 而await()方法对异常无感知
