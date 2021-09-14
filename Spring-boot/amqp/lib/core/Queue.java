-------------------------
Queue
-------------------------
	# 抽象的队列对象

	# 方法
		@Deprecated
		public static final String X_QUEUE_MASTER_LOCATOR = "x-queue-master-locator";
		public static final String X_QUEUE_LEADER_LOCATOR = "x-queue-master-locator";

		public Queue(String name)
		public Queue(String name, boolean durable)
		public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete)
		public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments)

		public String getName()
		public boolean isDurable()
		public boolean isExclusive()
		public boolean isAutoDelete()
		public void setActualName(String name)
		public String getActualName()

		@Deprecated
		public final void setMasterLocator(@Nullable String locator)
		public final void setLeaderLocator(@Nullable String locator)
