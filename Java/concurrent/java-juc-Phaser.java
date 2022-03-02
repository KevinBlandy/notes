-------------------
Phaser
-------------------
	# JDK17新的同步类
		public class Phaser
	
	# 作用
		* 阶段任务同时阻塞, 同时唤醒

	# 构造函数
		public Phaser()
		public Phaser(int parties)
		public Phaser(Phaser parent)
		public Phaser(Phaser parent, int parties)
	
	# 实例方法
		public int arrive()

		public int arriveAndAwaitAdvance()
			* 到达一个阶段

		public int arriveAndDeregister()
			* 

		public int awaitAdvance(int phase)
		public int awaitAdvanceInterruptibly(int phase) throws InterruptedException
		public int awaitAdvanceInterruptibly(int phase, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException

		public int bulkRegister(int parties)
			* 调整注册任务数量

		public void forceTermination()
		public int getArrivedParties()
			* 获取当前到达的任务数
		
		public Phaser getParent()
			* 获取上级Phaser
		
		public final int getPhase()
			* 获取当前属于第几阶段
		
		public int getRegisteredParties()
			* 获取当前注册的任务数
		
		public Phaser getRoot()
			* 获取ROOT Phaser

		public int getUnarrivedParties()
			* 获取当前未到达的任务数

		public boolean isTerminated()
			* 判断当前阶段器是否被终止

		protected boolean onAdvance(int phase, int registeredParties)
			* 到达一个阶段后的事件方法

		public int register()




		