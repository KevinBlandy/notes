-----------------------
Promise
-----------------------
	# 实现了 io.netty.util.concurrent.Future<V> 的接口
		

	# 抽象方法
		// 标记当前Future成功，设置结果，如果设置成功，则通知所有的监听器，如果Future已经成功或者失败，则抛出IllegalStateException
		Promise<V> setSuccess(V result);

		// 标记当前Future成功，设置结果，如果设置成功，则通知所有的监听器并且返回true，否则返回false
		boolean trySuccess(V result);

		// 标记当前Future失败，设置结果为异常实例，如果设置成功，则通知所有的监听器，如果Future已经成功或者失败，则抛出IllegalStateException
		Promise<V> setFailure(Throwable cause);

		// 标记当前Future失败，设置结果为异常实例，如果设置成功，则通知所有的监听器并且返回true，否则返回false
		boolean tryFailure(Throwable cause);
		
		// 标记当前的Promise实例为不可取消，设置成功返回true，否则返回false
		boolean setUncancellable();

		// 下面的方法和io.netty.util.concurrent.Future中的方法基本一致，只是修改了返回类型为Promise

		@Override
		Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);

		@Override
		Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners);

		@Override
		Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener);

		@Override
		Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners);

		@Override
		Promise<V> await() throws InterruptedException;

		@Override
		Promise<V> awaitUninterruptibly();

		@Override
		Promise<V> sync() throws InterruptedException;

		@Override
		Promise<V> syncUninterruptibly();
	
	# 实现类
		* Promise的实现类为 io.netty.util.concurrent.DefaultPromise（其实DefaultPromise还有很多子类，某些实现是为了定制特定的场景做了扩展）
		* DefaultPromise 继承自 io.netty.util.concurrent.AbstractFuture
	
	# 模拟使用场景
		public class PromiseMain {

		public static void main(String[] args) throws Exception {
			String url = "http://xxx.yyy.zzz";
			EventExecutor executor = GlobalEventExecutor.INSTANCE;
			Promise<DownloadResult> promise = new DefaultPromise<>(executor);
			promise.addListener(new DownloadResultListener());
			Thread thread = new Thread(() -> {
				try {
					System.out.println("开始下载资源,url:" + url);
					long start = System.currentTimeMillis();
					// 模拟下载耗时
					Thread.sleep(2000);
					String location = "C:\\xxx\\yyy\\z.md";
					long cost = System.currentTimeMillis() - start;
					System.out.println(String.format("下载资源成功,url:%s,保存到:%s,耗时:%d ms", url, location, cost));
					DownloadResult result = new DownloadResult();
					result.setUrl(url);
					result.setFileDiskLocation(location);
					result.setCost(cost);
					// 通知结果
					promise.setSuccess(result);
				} catch (Exception ignore) {

				}
			}, "Download-Thread");
			thread.start();
			Thread.sleep(Long.MAX_VALUE);
		}

		@Data
		private static class DownloadResult {

			private String url;

			private String fileDiskLocation;

			private long cost;
		}

		private static class DownloadResultListener implements GenericFutureListener<Future<DownloadResult>> {

			@Override
			public void operationComplete(Future<DownloadResult> future) throws Exception {
				if (future.isSuccess()) {
					DownloadResult downloadResult = future.getNow();
					System.out.println(String.format("下载完成通知,url:%s,文件磁盘路径:%s,耗时:%d ms", downloadResult.getUrl(),
							downloadResult.getFileDiskLocation(), downloadResult.getCost()));
				}
			}
		}
	}