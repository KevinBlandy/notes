-------------------------
Schedulers
-------------------------
	# ³éÏóÀà
		abstract class Schedulers

-------------------------
static
-------------------------
	public static final int DEFAULT_POOL_SIZE = Optional.ofNullable(System.getProperty("reactor.schedulers.defaultPoolSize"))
												.map(Integer::parseInt)
												.orElseGet(() -> Runtime.getRuntime().availableProcessors());
	
	public static final int DEFAULT_BOUNDED_ELASTIC_SIZE = Optional.ofNullable(System.getProperty("reactor.schedulers.defaultBoundedElasticSize"))
												.map(Integer::parseInt)
												.orElseGet(() -> 10 * Runtime.getRuntime().availableProcessors());
	public static final int DEFAULT_BOUNDED_ELASTIC_QUEUESIZE = Optional.ofNullable(System.getProperty("reactor.schedulers.defaultBoundedElasticQueueSize"))
												.map(Integer::parseInt)
												.orElse(100000);

	public static Scheduler fromExecutor(Executor executor)
	public static Scheduler fromExecutor(Executor executor, boolean trampoline)
	public static Scheduler fromExecutorService(ExecutorService executorService)
	public static Scheduler fromExecutorService(ExecutorService executorService, String executorName)
	@Deprecated
	public static Scheduler elastic()
	public static Scheduler boundedElastic()
	public static Scheduler parallel()
	public static Scheduler immediate() 
	@Deprecated
	public static Scheduler newElastic(String name)
	@Deprecated
	public static Scheduler newElastic(String name, int ttlSeconds) 
	@Deprecated
	public static Scheduler newElastic(String name, int ttlSeconds, boolean daemon)
	@Deprecated
	public static Scheduler newElastic(int ttlSeconds, ThreadFactory threadFactory)
	public static Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, String name)
	public static Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, String name, int ttlSeconds)
	public static Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, String name, int ttlSeconds, boolean daemon)
	public static Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, ThreadFactory threadFactory, int ttlSeconds)
	public static Scheduler newParallel(String name)
	public static Scheduler newParallel(String name, int parallelism)
	public static Scheduler newParallel(String name, int parallelism, boolean daemon)
	public static Scheduler newParallel(int parallelism, ThreadFactory threadFactory)
	public static Scheduler newSingle(String name)
	public static Scheduler newSingle(String name, boolean daemon)
	public static Scheduler newSingle(ThreadFactory threadFactory)
	public static void onHandleError(BiConsumer<Thread, ? super Throwable> c)
	public static boolean isInNonBlockingThread() 
	public static boolean isNonBlockingThread(Thread t)
	public static void enableMetrics()
	public static void disableMetrics()
	public static void resetFactory()
	public static Snapshot setFactoryWithSnapshot(Factory newFactory) 
	public static void resetFrom(@Nullable Snapshot snapshot)
	public static void resetOnHandleError()
	public static void setFactory(Factory factoryInstance)
	public static boolean addExecutorServiceDecorator(String key, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> decorator)
	public static void setExecutorServiceDecorator(String key, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> decorator)
	public static BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> removeExecutorServiceDecorator(String key)
	public static ScheduledExecutorService decorateExecutorService(Scheduler owner, ScheduledExecutorService original)
	public static void onScheduleHook(String key, Function<Runnable, Runnable> decorator)
	public static void resetOnScheduleHook(String key)
	public static void resetOnScheduleHooks()
	public static Runnable onSchedule(Runnable runnable)
	public static void shutdownNow()
	public static Scheduler single()
	public static Scheduler single(Scheduler original)


-------------------------
this
-------------------------