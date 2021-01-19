------------------------
单例设计模式			|
------------------------
	# 懒加载
	# 线程安全
	# 调用高效

------------------------
常见五种单例设计模式	|
------------------------
	# 饿汉式
		* 线程安全,调用效率高,不能延迟加载
	# 懒汉式
		* 线程安全,调用效率不高,可以延迟加载
	# 双重检锁式
	# 静态内部式
	# 枚举单例

------------------------
常见五种单例设计模式-代码|
------------------------
/**
 * 饿汉式
 * 调用高效,但不是懒加载,线程安全
 */
public class Single1 {
	
	public static final Single1 SINGLE = new Single1();
	
	private Single1() {}
	
	public static Single1 getInstance() {
		return SINGLE;
	}
}

/**
 * 懒汉式
 * 懒加载但是调用不高效,线程安全 
 */
public class Single2 {
	private static Single2 SINGLE_INSTANCE = null;
	private Single2() {}
	/**
	 * 懒汉式,延迟加载
	 * 通过双锁,来防止并发情况下的安全问题
	 * 但是第一次读锁,会带性能问题
	 */
	public static Single2 getInstance() {
		if(SINGLE_INSTANCE == null) {
			Single2 singleDelay;
			synchronized (Single2.class) {
				singleDelay = SINGLE_INSTANCE;
				if(singleDelay == null) {
					synchronized (Single2.class) {
						singleDelay = new Single2();
					}
				}
				SINGLE_INSTANCE = singleDelay;
			}
		}
		return SINGLE_INSTANCE;
	}
}
/**
 * 通过内部静态类,既延迟加载,又是线程安全
 * 线程安全,调用效率高,懒加载
 */
public class Single3 {
	/**
	 * 类被加载的时候,只会初始化静态属性,不会初始化静态类
	 */
	public static class Single3ClassInstnce {
		private static final Single3 SINGLE = new Single3();  
	}
	private Single3() {}
	/**
	 * 只有调用 getInstance() 方法时,才会加载内部静态类(Single3ClassInstnce)
	 * 类的加载是线程安全的
	 * static final 更是保证了实例的唯一性
	 */
	public static Single3 getInstance() {
		return Single3ClassInstnce.SINGLE;
	}
}
/**
 * 通过枚举来控制实例数量,不需多解释
 * 是基于JVM底层实现的,反射和反序列化都不能破解
 */
public enum Single4 {
	INSTANCE;
}


------------------------
单例的漏洞				|
------------------------
	# 反射可以破解单例,可以通过构造器对象,创建多个实例
		* 可以在构造器中抛出异常,来阻止更多对象的创建
	
	# 反序列化可以破解单例
		* 通过钩子程序来破解该方法
			/**
			 * java反序列化的钩子方法
			 * 反序列化的时候,如果定义了该方法,则返回该方法的返回对象
			 * @return
			 * @throws ObjectStreamException
			 */
			private Object readResolve() throws ObjectStreamException{
				return SINGLE;
			}

	# 枚举是绝对安全的