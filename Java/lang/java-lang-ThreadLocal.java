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

	
	# 静态函数
		public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier)
			* 首次调用Get会调用supplier进行初始化

	# 构造方法
		public ThreadLocal()
	
	# 实例方法
		
		