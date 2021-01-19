-----------------------------
java.lang.Runtime			 |
-----------------------------
	# 没有构造函数，不能被实例化。
	# 它其实是一个单例。有一个静态方法，可以获取它的对象！
	# 该方法是 Runtime runtime = Runtime.getRuntime(); 
	# Runtime 

		Process exec("CMD/shell命名");
			* 可以执行一段当前操作系统的命令。

		long maxMemory() 
			* 返回 Java 虚拟机试图使用的最大内存量。

		long totalMemory() 
			* 返回 Java 虚拟机中的内存总量。 
		
		int availableProcessors();
			* 返回到虚拟机的最大可用的处理器数量,不会小余一个

		void addShutdownHook(Thread hook) 
			* 添加一个关闭回调,收到系统退出指令的时候,会执行该线程
			* 可以添加多个,但是没法保证执行顺序
			* 如果JVM崩溃,可能不会执行
			* 不能在回调中调用 : System.exit(),否则会卡住

	
	# Process
		void prop.destroy();
			* 杀死进程。(只能弄死它启动的程序)

		InputStream prop.getInputStream();
			* 可以获取到cmd的一个输入流,执行了cmd命名后可以读取到cmd显示出来的内容

		InputStream getErrorStream() 
			*  获取子进程的错误流。
			
		InputStream getInputStream()  
			* 获取写入流

		int exitValue();
			* 此 Process 对象表示的子进程的出口值。根据惯例，值 0 表示正常终止。 

		int waitFor();
			* 当期线程会一直阻塞,直到 Process 对象表示的进程已经终