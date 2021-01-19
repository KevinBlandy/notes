---------------------------
java9的新接口
---------------------------
	
	# 静态方法
		public static Optional<ProcessHandle> of(long pid);
		public static ProcessHandle current();

	# 抽象方法
		long pid();
		Optional<ProcessHandle> parent();
		Stream<ProcessHandle> children();
		Stream<ProcessHandle> descendants();
		Info info();
			* 进程相关的信息接口
				Optional<String> command();
				Optional<String> commandLine();
				Optional<String[]> arguments();
				Optional<Instant> startInstant();
				Optional<Duration> totalCpuDuration();
				Optional<String> user();

		CompletableFuture<ProcessHandle> onExit();
		boolean supportsNormalTermination();
		boolean destroy();
		boolean destroyForcibly();
		boolean isAlive();

	