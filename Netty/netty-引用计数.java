-------------------------
引用计数				 |
-------------------------
	# 引用计数
		* 被引用计数包含的对象,能够显示的被垃圾回收,当初始化的时候,如果计数被减少到0则对象会被显示回收
		* 再次访问被回收的这些对象将会抛出异常
		* 如果一个对象实现了ReferenceCounted,并且包含有其他对象也实现来ReferenceCounted,当这个对象计数为0被回收的时候,所包含的对象同样会通过release()释放掉
		* 一个引用对象刚创建时,初始化refCnt值就是1

	# 接口 ReferenceCounted
	# 接口方法
		int refCnt();
			* 返回引用数

		ReferenceCounted retain();
			* 添加一个引用

		ReferenceCounted retain(int increment);
			* 添加N个引用

		ReferenceCounted touch();
		ReferenceCounted touch(Object hint);

		boolean release();
			* 释放一个引用,如果引用等于0,则会被回收

		boolean release(int decrement);
			* 释放n个引用,如果引用等于0,则会被回收


-------------------------
ReferenceCountUtil		 |
-------------------------
	# 对于 ReferenceCounted 的操作工具类
	# 提供了N多的静态方法(几乎跟ReferenceCounted接口一样)
		* 参数都是 Object,并且提供安全的释放方法

	int refCnt(Object msg)

	boolean release(Object msg)
	boolean release(Object msg, int decrement)

	<T> T retain(T msg)
	<T> T retain(T msg, int increment)

	void safeRelease(Object msg)
	void safeRelease(Object msg, int decrement)

	<T> T touch(T msg)
	<T> T touch(T msg, Object hint)



	
