--------------------
SharedData
--------------------
	# 数据共享接口： interface SharedData 
		


--------------------
抽象
--------------------
	<K, V> void getClusterWideMap(String name, Handler<AsyncResult<AsyncMap<K, V>>> resultHandler);
	<K, V> Future<AsyncMap<K, V>> getClusterWideMap(String name);

	<K, V> void getAsyncMap(String name, Handler<AsyncResult<AsyncMap<K, V>>> resultHandler);
	<K, V> Future<AsyncMap<K, V>> getAsyncMap(String name);
		* 获取异步共享的Map，可以在分布式环境中使用

	<K, V> void getLocalAsyncMap(String name, Handler<AsyncResult<AsyncMap<K, V>>> resultHandler);
	<K, V> Future<AsyncMap<K, V>> getLocalAsyncMap(String name);
	<K, V> LocalMap<K, V> getLocalMap(String name);
		* 获取本地Map，可以是同步Map，也可以是异步Map

	void getLock(String name, Handler<AsyncResult<Lock>> resultHandler);
	Future<Lock> getLock(String name);
		* 获取锁，可以在集群中共享

	void getLockWithTimeout(String name, long timeout, Handler<AsyncResult<Lock>> resultHandler);
	Future<Lock> getLockWithTimeout(String name, long timeout);
		* 获取锁，并且设置超时时间，如果超时，则会除非异常处理

	void getLocalLock(String name, Handler<AsyncResult<Lock>> resultHandler);
	Future<Lock> getLocalLock(String name);
		* 获取本地锁

	void getLocalLockWithTimeout(String name, long timeout, Handler<AsyncResult<Lock>> resultHandler);
	Future<Lock> getLocalLockWithTimeout(String name, long timeout);
		* 获取本地的超时锁

	 void getCounter(String name, Handler<AsyncResult<Counter>> resultHandler);
	 Future<Counter> getCounter(String name);
	 	* 获取异步计数器

	 void getLocalCounter(String name, Handler<AsyncResult<Counter>> resultHandler);
	 Future<Counter> getLocalCounter(String name);
	 	* 获取异步计数器，本地的

	 

