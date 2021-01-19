动态代理:
1,只学一个方法！
	方法的作用 : 在运行时,动态创建一组指定接口的实现类对象(在运行的时候创建实现了一组接口的对象)
	interface A{}
	interface B{}
	Object o =  方法 (new Class[]{A.class,B.class})
	o对象,实现了A和B这两个接口(反正就是o所属的类型实现了A和B这个两个接口)
------------
Object proxObject = Proxy.newProxyInstance(ClassLoader loader,Class[] clazz,InvocationHandler handler);
	ClassLoader--类加载器
	Class[]    --一组Interface成员
	InvocationHandler -- 调用处理器
方法作用:动态创建了实现了Class[]中所有Interface成员的实现类对象.
参数
1,ClassLoader:类加载器
	* 它是用来加载类的,把.class文件加载到内存中,形成Class对象
	JVM内存分为五大区域:堆,栈,方法区,本地方法区,本地寄存器
2,太简单,直接略
3,InvocationHandler
	这哥们儿是一个接口,只有一个方法！
	InvocationHandler h = new InvocationHandler()
	{
		public Object invoke(Object proxy, Method method, Object[] args)throws Throwable 
		{
			return null;
		}
	};
	* 代码对象的所有方法方法都会调用这个invoke();(个别不支持,例如:getClass();)
代码对象实现的所有的接口中的方法,内容都是调用InvocationHandler的invoke方法！ ( 484傻你说？)
------------

InvocationHandler -- 详谈
是一个接口,只有一个 invoke方法.
public Object invoke(Object proxy,Method method,Object[] args)(){}
这个invoke方法在什么时候被调用? -- 正确答案2
1,在代理对象被创建的时候？X
2,在调用代理对象所实现接口中的方法时?√
	proxy :当前对象,也就是代理对象,在调用谁的方法
	megthod:当前被调用的方法,也就是目标方法
	args:实参





2,动态代理的作用
	最终是学习:AOP(面向切面编程/面向方面编程),它与装饰者设计模式有点相似,但是它比装饰者模式更加灵活
	