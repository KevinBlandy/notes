---------------------------------------------
对已存在的 Future 对象设置回调
---------------------------------------------
// 回调执行器
Executor executor = Executors.newFixedThreadPool(10);

FutureTask<String> future = new FutureTask<String>(() -> {
	Thread.sleep(1000L);
	return "Result";
});

// 线程异步执行
Thread thread = new Thread(future);
thread.start();

// 把 Future 对象包装为 ListenableFuture

// 底层 ListenableFuture 实现会通过一个 Executor 去执行包装的 Future 的 get() 阻塞方法，阻塞，直到获取到结果，然后调用 ListenableFuture 的监听器
// 默认的 Executor 为 Executors.newCachedThreadPool，也就是说会为每个 Futrue 分配一个线程来阻塞获取结果，如果 Futrue 爆增的话，可能导致严重的性能问题
// 也有一个重载方法，可以指定阻塞获取结果的 Executor
// ListenableFuture<V> listenInPoolThread(Future<V> future, Executor executor) 

ListenableFuture<String> listenableFuture = JdkFutureAdapters.listenInPoolThread(future);

listenableFuture.addListener(() -> {
	log.info("执行完毕");
}, executor);

// 监听方式1：通过 Futures.addCallback，可以获取到执行结果
Futures.addCallback(listenableFuture, new FutureCallback<String>() {
	@Override
	public void onSuccess(@Nullable String result) {
		log.info("执行完毕：{}", result);
	}
	@Override
	public void onFailure(Throwable t) {
		log.error("执行异常：{}", t.getMessage());
	}
}, executor);


// 对于已已经完成了的 Future 添加监听器会立即执行

---------------------------------------------
包装 Executor 
---------------------------------------------

// Guava为了支持自己的Listerner模式，新建了一种ExecutorService，叫做 ListeningExecutorService，我们可以使用MoreExecutor创建它

// 创建一个由invoke线程执行的线程池
ListeningExecutorService executorService = MoreExecutors.newDirectExecutorService();

// 装饰自定义的线程池返回
ListeningExecutorService executorService1 = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

 // 执行任务，返回 ListenableFuture
ListenableFuture<String> listenableFuture = executorService.submit(() -> {
	Thread.sleep(1000L);
	return "Result";
});

// 添加监听器
listenableFuture.addListener(() -> {
	log.info("执行完毕");
}, executorService);

// 添加监听器
Futures.addCallback(listenableFuture, new FutureCallback<String>() {
	@Override
	public void onSuccess(@Nullable String result) {
		log.info("执行完毕：{}", result);
	}
	@Override
	public void onFailure(Throwable t) {
		log.error("执行异常：{}", t.getMessage());
	}
}, executorService);

System.in.read();
