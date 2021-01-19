动态代理,难度多少有点!不多说,开奖
动态代理的作用:最终是学习AOP,面向切面编程
				OOP--面向对象(只是给你提一下,没啥含义)
一,只有一个方法
1,作用:在运行时,动态创建一组指定接口的实现对象!
	  *在运行的时候啊,创建了一个,实现了一组指定接口的对象
	  *跟装饰者模式相似,不过它比它更灵活
2,简单演示
interface A//第一个接口
{
}
interface B//第二个接口
{
}
Object obj = 方法(new Class[]{A.class,B.class})//动态代理的方法
这个obj,就实现了A/B俩接口!这个A是一个对象,它所属的类型实现了A/B接口
--------------------------------------------
//传统思路
class test implements A B
{
	
}
test t = new test();  //这东西,不能在运行的时候生成,这是我们自个儿先写上去的
--------------------------------------------

二,类介绍
java.lang.reflect.Proxy
1,有一个静态方法

Object proxyObject = Proxy.newProxyInstance(ClassLoader classLoader,Class[] interfaces,InvocationHandler h);
	> 方法作用:动态创建了实现了 interfaces 数组中所有指定接口的实现类对象

三,方法的参数详解
1,ClassLoader
	> 类加载器,它是用来加载类的,把.class文件加载到内存中 ,形成class对象!
	* 传统的创建对象流程
		1,加载类(加载.class文件到内存,执行静态代码块和静态初始化语句)
		2,执行new,创建一个空白对象
		3,调用构造器
		4,子类调用父类构造器
		5,构造器执行过程(执行构造代码块和初始化语句,构造器内容)
	> 在加载,一个类到内存的时候.就需要一个使者, ClassLoader 作为使者,带领相应的.class走向内存.到了内存就变成了 Class 对象!!!
	* .class == Class  [从.class变成了Class对象]
	> Class 对象有个方法: getClassLoader();//就能获取到送你来的那个使者...
	* 动态代理,牛逼的地方就是,它创建的对象,并没有本地的class文件,也就是传说中的动态... ...

2,interfaces
	> 就是一个Class对象的数组,
	> 也就是你要动态实现的接口们!!!
	> this.getClass().getInterfaces();//这个方法就能返回this这个对象所实现的所有接口... ...的数组

3,InvocationHandler
	> 这哥们儿叫做:调用处理器,是一个接口,只有一个方法
	> Object obj = invoke(Object proxy, Method method, Object[] args) 
	> 代理对象的所有方法,都会调用这个接口的invoke()方法,
	> 也不是全部方法,例如: native 修饰的方法,就不会调用这个方法
	> 下面详解这个方法
InvocationHandler h = new InvocationHandler()
{
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable 
	{
		return null;
	}	
};

四,参数的方法详解	(InvocationHandler)
	invoke(Object proxy, Method method, Object[] args) 
	> 代理对象,实现的所有接口中的方法,内容都是调用的这个方法
	> 它是在调用代理对象所实现接口中的方法时创建调用.
	> 传递的那一组接口,里面的所有的方法.代理对象在调用的时候,其实,都是在调用这个方法
	> 除了 native 方法,不管是什么,就算是Object里面的方法,都是调用的这个方法.叼得飞起... ...

1,Object
	> 当前对象,其实就是代理对象
2,Method
	> 当前的被调用的方法(目标方法)
3,Object[]
	> 实参!


补充讲个东西
native 方法修饰符
	表示是本地方法,其实也就底层是C++写的东西!例如:Object 里面的getClass,hashCode...等方法都会有这个修饰词
