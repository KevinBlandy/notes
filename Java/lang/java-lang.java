---------------------
					 |
---------------------
	System
	Runtime
	Comparable
	ThreadLocal

---------------------
System				 |
---------------------
	# 类 ——系统类
	# 没有构造函数，不能被实例化。里面的方法全是静态的。
	# 静态方法
		static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) 
			* 从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。 

		long currentTimeMillis() 
			返回以毫秒为单位的当前时间。 

		void exit(int status) 
			终止当前正在运行的 Java 虚拟机。 

		void gc() 
			运行垃圾回收器。 
		


---------------------
Runtime				 |
---------------------
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

---------------------
Comparable<T>		 |
---------------------
	# int compareTo(T o) 
		* 比较此对象与指定对象的顺序。 

--------------------
ThreadLocal			|
--------------------

	这个东西里面其实是一个Map集合
	以当前线程为键!
	每个线程只能取出/设置自己线程的东西！
	Map<Thread,t> map = new HashMap<Thread,t>();
	set();
	get();
	remove();

	通常用在一个类的成员上,多个线程访问它时
	每个线程都有自己的副本。互不干扰
	Spring中,把 Connection 放到了 ThreadLocal 中。
