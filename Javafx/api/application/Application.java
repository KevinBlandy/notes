--------------------
Application			|
--------------------
	# 静态的全局变量
		String STYLESHEET_CASPIAN = "CASPIAN";
		String STYLESHEET_MODENA = "MODENA";

	# 静态方法
		void launch(Class<? extends Application> appClass, String... args)
			* 指定 Application 的继承类, 启动
		
		void launch(String... args)
			* 源码是遍历执行行栈, 找到执行 Application.launch 的类, 找不到就异常
			* 如果该类是已经继承了, Application, 则启动, 否则异常

	# 构造方法
		public Application()
		String getUserAgentStylesheet()
		void setUserAgentStylesheet(String url)

	# 实例方法
		abstract void start(Stage primaryStage)
			* 由子类覆写的启动方法

		void init()
		void stop()
			* 生命周期方法, 在app的启动和关闭之前执行
		
		HostServices getHostServices()
			* 返回 HostServices
		
		Parameters getParameters()
		void notifyPreloader(PreloaderNotification info)

--------------------
Parameters			|
--------------------
	# Application 内部类静态抽象类
		public Parameters()
		List<String> getRaw()
		List<String> getUnnamed()
		Map<String, String> getNamed()


	